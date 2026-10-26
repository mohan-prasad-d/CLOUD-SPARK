package com.example.sensor

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import androidx.core.content.ContextCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.log10
import kotlin.math.sqrt

data class MotionReading(
  val x: Float = 0.03f,
  val y: Float = 0.04f,
  val z: Float = 0.06f,
  val vm: Float = 0.08f
)

class DeviceSensorManager(
  private val context: Context,
  private val onMotionUpdated: (MotionReading) -> Unit,
  private val onAudioDbUpdated: (Float) -> Unit
) : SensorEventListener {

  private val sensorManager =
    context.getSystemService(Context.SENSOR_SERVICE) as? SensorManager
  private val accelerometer: Sensor? =
    sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

  private var isListeningSensors = false
  private var audioJob: Job? = null
  private var audioRecord: AudioRecord? = null

  private var prevX = 0f
  private var prevY = 0f
  private var prevZ = 0f

  fun start() {
    startAccelerometer()
    startAudioMeter()
  }

  fun stop() {
    stopAccelerometer()
    stopAudioMeter()
  }

  private fun startAccelerometer() {
    if (!isListeningSensors && accelerometer != null) {
      sensorManager?.registerListener(
        this,
        accelerometer,
        SensorManager.SENSOR_DELAY_UI
      )
      isListeningSensors = true
    }
  }

  private fun stopAccelerometer() {
    if (isListeningSensors) {
      sensorManager?.unregisterListener(this)
      isListeningSensors = false
    }
  }

  override fun onSensorChanged(event: SensorEvent?) {
    event?.let {
      if (it.sensor.type == Sensor.TYPE_ACCELEROMETER) {
        val rawX = it.values[0]
        val rawY = it.values[1]
        val rawZ = it.values[2]

        // Compute dynamic jerk / motion fluctuation
        val dx = rawX - prevX
        val dy = rawY - prevY
        val dz = rawZ - prevZ
        prevX = rawX
        prevY = rawY
        prevZ = rawZ

        val dynamicJerk = sqrt(dx * dx + dy * dy + dz * dz) / 9.80665f
        val restingBaseline = 0.07f
        val calculatedVm = (restingBaseline + dynamicJerk).coerceIn(0.04f, 3.2f)

        val xG = (abs(rawX) / 9.80665f).coerceIn(0.02f, 2.0f)
        val yG = (abs(rawY) / 9.80665f).coerceIn(0.02f, 2.0f)
        val zG = (abs(rawZ - 9.8f) / 9.80665f + 0.05f).coerceIn(0.02f, 2.0f)

        onMotionUpdated(
          MotionReading(
            x = xG,
            y = yG,
            z = zG,
            vm = calculatedVm
          )
        )
      }
    }
  }

  override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

  @SuppressLint("MissingPermission")
  fun startAudioMeter() {
    if (ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.RECORD_AUDIO
      ) != PackageManager.PERMISSION_GRANTED
    ) {
      return
    }

    if (audioJob != null) return

    val sampleRate = 16000
    val channelConfig = AudioFormat.CHANNEL_IN_MONO
    val audioFormat = AudioFormat.ENCODING_PCM_16BIT
    val minBufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)
    val bufferSize = minBufferSize.coerceAtLeast(1024)

    try {
      audioRecord = AudioRecord(
        MediaRecorder.AudioSource.MIC,
        sampleRate,
        channelConfig,
        audioFormat,
        bufferSize
      )

      if (audioRecord?.state == AudioRecord.STATE_INITIALIZED) {
        audioRecord?.startRecording()
        audioJob = CoroutineScope(Dispatchers.IO).launch {
          val buffer = ShortArray(512)
          while (isActive && audioRecord?.recordingState == AudioRecord.RECORDSTATE_RECORDING) {
            val read = audioRecord?.read(buffer, 0, buffer.size) ?: 0
            if (read > 0) {
              var sum = 0.0
              for (i in 0 until read) {
                sum += buffer[i] * buffer[i]
              }
              val rms = sqrt(sum / read)
              // Map RMS to calibrated dB (typical room ambient 25 - 40 dB, speech 55 - 75 dB)
              val db = if (rms > 1) {
                (20 * log10(rms) - 10).toFloat().coerceIn(25f, 85f)
              } else {
                25f
              }
              onAudioDbUpdated(db)
            }
          }
        }
      }
    } catch (_: Exception) {
      stopAudioMeter()
    }
  }

  fun stopAudioMeter() {
    audioJob?.cancel()
    audioJob = null
    try {
      if (audioRecord?.state == AudioRecord.STATE_INITIALIZED) {
        audioRecord?.stop()
      }
      audioRecord?.release()
    } catch (_: Exception) {}
    audioRecord = null
  }
}
