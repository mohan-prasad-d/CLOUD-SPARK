package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CompassCalibration
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.TelemetryState
import com.example.ui.theme.AccentCyan
import com.example.ui.theme.AlertCrimson
import com.example.ui.theme.DeepObsidian
import com.example.ui.theme.DeepObsidianElevated
import com.example.ui.theme.DeepObsidianSurface
import com.example.ui.theme.SafeEmerald
import com.example.ui.theme.SlateBorder
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.WarningAmber
import kotlin.math.sin

@Composable
fun PatientProfileBanner(state: TelemetryState, modifier: Modifier = Modifier) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(16.dp))
      .background(DeepObsidianSurface)
      .border(1.dp, SlateBorder, RoundedCornerShape(16.dp))
      .padding(14.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
          modifier = Modifier
            .size(42.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(DeepObsidianElevated)
            .border(1.dp, SlateBorder, RoundedCornerShape(12.dp)),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "MP",
            color = AccentCyan,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
          )
          Box(
            modifier = Modifier
              .align(Alignment.BottomEnd)
              .padding(3.dp)
              .size(8.dp)
              .clip(CircleShape)
              .background(SafeEmerald)
              .border(1.5.dp, DeepObsidianSurface, CircleShape)
          )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = "Mohan Prasad D",
              color = TextPrimary,
              fontWeight = FontWeight.Bold,
              fontSize = 14.5.sp
            )
            Spacer(modifier = Modifier.width(6.dp))
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(DeepObsidianElevated)
                .border(0.5.dp, SlateBorder, RoundedCornerShape(4.dp))
                .padding(horizontal = 5.dp, vertical = 1.dp)
            ) {
              Text("Age: 20", color = TextSecondary, fontSize = 9.sp)
            }
            Spacer(modifier = Modifier.width(4.dp))
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(DeepObsidianElevated)
                .border(0.5.dp, SlateBorder, RoundedCornerShape(4.dp))
                .padding(horizontal = 5.dp, vertical = 1.dp)
            ) {
              Text("#AEGIS-9921", color = AccentCyan, fontFamily = FontFamily.Monospace, fontSize = 9.sp)
            }
          }

          Text(
            text = "Nocturnal Frontal Lobe Epilepsy • Solitary Patient",
            color = TextSecondary,
            fontSize = 10.sp
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    // Vitals Metrics Row (3 Aligned Cards)
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      Box(
        modifier = Modifier
          .weight(1f)
          .clip(RoundedCornerShape(8.dp))
          .background(DeepObsidianElevated)
          .border(1.dp, SlateBorder, RoundedCornerShape(8.dp))
          .padding(8.dp)
      ) {
        Column {
          Text("BASELINE HRV", color = TextSecondary, fontSize = 8.sp, fontWeight = FontWeight.Bold)
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            "${state.rmssd} ms",
            color = if (state.rmssd < 20) AlertCrimson else SafeEmerald,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
          )
        }
      }

      Box(
        modifier = Modifier
          .weight(1f)
          .clip(RoundedCornerShape(8.dp))
          .background(DeepObsidianElevated)
          .border(1.dp, SlateBorder, RoundedCornerShape(8.dp))
          .padding(8.dp)
      ) {
        Column {
          Text("TEMP / GSR", color = TextSecondary, fontSize = 8.sp, fontWeight = FontWeight.Bold)
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            "36.6°C / 1.4µS",
            color = TextPrimary,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
          )
        }
      }

      Box(
        modifier = Modifier
          .weight(1f)
          .clip(RoundedCornerShape(8.dp))
          .background(DeepObsidianElevated)
          .border(1.dp, SlateBorder, RoundedCornerShape(8.dp))
          .padding(8.dp)
      ) {
        Column {
          Text("SLEEP STAGE", color = TextSecondary, fontSize = 8.sp, fontWeight = FontWeight.Bold)
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            state.sleepPhase,
            color = AccentCyan,
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp
          )
        }
      }
    }
  }
}

@Composable
fun CardPpgStream(state: TelemetryState, modifier: Modifier = Modifier) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(16.dp))
      .background(DeepObsidianSurface)
      .border(1.dp, SlateBorder, RoundedCornerShape(16.dp))
      .padding(14.dp)
      .testTag("card_ppg_stream")
  ) {
    Column {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(28.dp)
              .clip(RoundedCornerShape(7.dp))
              .background(DeepObsidianElevated)
              .border(1.dp, SlateBorder, RoundedCornerShape(7.dp)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Favorite,
              contentDescription = "Heart",
              tint = if (state.bpm > 100) AlertCrimson else AccentCyan,
              modifier = Modifier.size(15.dp)
            )
          }
          Spacer(modifier = Modifier.width(8.dp))
          Text("PPG Heart Stream", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 12.5.sp)
        }

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(if (state.bpm > 100) AlertCrimson.copy(alpha = 0.15f) else SafeEmerald.copy(alpha = 0.12f))
            .border(
              0.5.dp,
              if (state.bpm > 100) AlertCrimson.copy(alpha = 0.4f) else SafeEmerald.copy(alpha = 0.3f),
              RoundedCornerShape(50)
            )
            .padding(horizontal = 7.dp, vertical = 2.dp)
        ) {
          Text(
            text = if (state.bpm > 100) "TACHYCARDIA" else "Resting Sinus",
            color = if (state.bpm > 100) AlertCrimson else SafeEmerald,
            fontSize = 8.5.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Row(verticalAlignment = Alignment.Bottom) {
        Text(
          text = "${state.bpm.toInt()}",
          color = TextPrimary,
          fontFamily = FontFamily.Monospace,
          fontWeight = FontWeight.ExtraBold,
          fontSize = 30.sp
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text("BPM", color = TextSecondary, fontSize = 11.sp, modifier = Modifier.padding(bottom = 4.dp))
      }

      Text(
        text = if (state.bpm > 100) "Ictal tachycardia spike detected" else "Baseline: 68 - 76 BPM (Sleep)",
        color = if (state.bpm > 100) AlertCrimson else TextSecondary,
        fontSize = 9.5.sp
      )

      Spacer(modifier = Modifier.height(10.dp))

      // Native Canvas PPG Pulse Waveform
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(82.dp)
          .clip(RoundedCornerShape(8.dp))
          .background(DeepObsidian)
          .border(1.dp, SlateBorder, RoundedCornerShape(8.dp))
      ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
          val w = size.width
          val h = size.height

          // Subtle clinical grid lines
          for (gx in 1..8) {
            val x = gx * (w / 9f)
            drawLine(SlateBorder.copy(alpha = 0.5f), Offset(x, 0f), Offset(x, h), strokeWidth = 1f)
          }
          for (gy in 1..3) {
            val y = gy * (h / 4f)
            drawLine(SlateBorder.copy(alpha = 0.5f), Offset(0f, y), Offset(w, y), strokeWidth = 1f)
          }

          val strokeColor = if (state.bpm > 100) AlertCrimson else AccentCyan
          val path = Path()
          val fillPath = Path()

          val cycleCount = if (state.bpm > 100) 5.2f else 3.2f
          val cycleWidth = w / cycleCount
          val baselineY = h * 0.78f
          val maxPeakY = h * 0.16f
          val waveHeight = baselineY - maxPeakY

          val steps = 220
          var lastPointY = baselineY
          for (i in 0..steps) {
            val x = (i.toFloat() / steps) * w
            val phase = (x % cycleWidth) / cycleWidth

            val pulseHeight = when {
              phase < 0.22f -> {
                val t = phase / 0.22f
                sin(t * Math.PI.toFloat() * 0.5f)
              }
              phase < 0.42f -> {
                val t = (phase - 0.22f) / 0.20f
                1.0f - (t * 0.65f)
              }
              phase < 0.56f -> {
                val t = (phase - 0.42f) / 0.14f
                0.35f + (sin(t * Math.PI.toFloat()) * 0.22f)
              }
              else -> {
                val t = (phase - 0.56f) / 0.44f
                0.35f * (1.0f - t)
              }
            }

            val y = baselineY - (pulseHeight * waveHeight)
            lastPointY = y

            if (i == 0) {
              path.moveTo(x, y)
              fillPath.moveTo(x, h)
              fillPath.lineTo(x, y)
            } else {
              path.lineTo(x, y)
              fillPath.lineTo(x, y)
            }
          }

          fillPath.lineTo(w, h)
          fillPath.close()

          // Subtle gradient wash
          drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
              listOf(strokeColor.copy(alpha = 0.25f), Color.Transparent)
            )
          )

          // Waveform line stroke
          drawPath(
            path = path,
            color = strokeColor,
            style = Stroke(width = 2.2f, cap = StrokeCap.Round)
          )

          // Pulse lead beacon
          drawCircle(color = strokeColor, radius = 3.5f, center = Offset(w - 2f, lastPointY))
        }
      }
    }
  }
}

@Composable
fun CardMotionVector(state: TelemetryState, modifier: Modifier = Modifier) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(16.dp))
      .background(DeepObsidianSurface)
      .border(1.dp, SlateBorder, RoundedCornerShape(16.dp))
      .padding(14.dp)
      .testTag("card_motion_vector")
  ) {
    Column {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(28.dp)
              .clip(RoundedCornerShape(7.dp))
              .background(DeepObsidianElevated)
              .border(1.dp, SlateBorder, RoundedCornerShape(7.dp)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.CompassCalibration,
              contentDescription = "IMU",
              tint = AccentCyan,
              modifier = Modifier.size(15.dp)
            )
          }
          Spacer(modifier = Modifier.width(8.dp))
          Text("Motion Vector", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 12.5.sp)
        }

        Text("100Hz IMU", color = TextSecondary, fontSize = 9.sp, fontFamily = FontFamily.Monospace)
      }

      Spacer(modifier = Modifier.height(10.dp))

      Row(verticalAlignment = Alignment.Bottom) {
        Text(
          text = "%.2f".format(state.motionVm),
          color = TextPrimary,
          fontFamily = FontFamily.Monospace,
          fontWeight = FontWeight.ExtraBold,
          fontSize = 30.sp
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text("g", color = TextSecondary, fontSize = 11.sp, modifier = Modifier.padding(bottom = 4.dp))
      }

      Text(
        text = if (state.motionVm > 1.0f) "Clonic Tremor Spasm (Turbulence)" else "Sleep Restfulness (Quiet)",
        color = if (state.motionVm > 1.0f) AlertCrimson else SafeEmerald,
        fontSize = 9.5.sp,
        fontWeight = FontWeight.Medium
      )

      Spacer(modifier = Modifier.height(10.dp))

      // 3 Axis Progress Bars
      Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        AxisProgressBar("Axis X", state.motionX, (state.motionX / 1.5f).coerceIn(0.10f, 1f))
        AxisProgressBar("Axis Y", state.motionY, (state.motionY / 1.5f).coerceIn(0.12f, 1f))
        AxisProgressBar("Axis Z", state.motionZ, (state.motionZ / 1.5f).coerceIn(0.15f, 1f))
      }
    }
  }
}

@Composable
private fun AxisProgressBar(label: String, value: Float, progress: Float) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(label, color = TextSecondary, fontSize = 9.5.sp)
    Box(
      modifier = Modifier
        .width(110.dp)
        .height(5.dp)
        .clip(RoundedCornerShape(50))
        .background(DeepObsidian)
        .border(0.5.dp, SlateBorder, RoundedCornerShape(50))
    ) {
      Box(
        modifier = Modifier
          .fillMaxHeight()
          .fillMaxWidth(progress)
          .clip(RoundedCornerShape(50))
          .background(AccentCyan)
      )
    }
    Text(
      "${"%.2f".format(value)}g",
      color = TextPrimary,
      fontFamily = FontFamily.Monospace,
      fontSize = 9.5.sp
    )
  }
}

@Composable
fun CardAcousticMonitor(state: TelemetryState, modifier: Modifier = Modifier) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(16.dp))
      .background(DeepObsidianSurface)
      .border(1.dp, SlateBorder, RoundedCornerShape(16.dp))
      .padding(14.dp)
      .testTag("card_acoustic_monitor")
  ) {
    Column {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(28.dp)
              .clip(RoundedCornerShape(7.dp))
              .background(DeepObsidianElevated)
              .border(1.dp, SlateBorder, RoundedCornerShape(7.dp)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Mic,
              contentDescription = "Mic",
              tint = AccentCyan,
              modifier = Modifier.size(15.dp)
            )
          }
          Spacer(modifier = Modifier.width(8.dp))
          Text("Acoustic Stress", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 12.5.sp)
        }

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(if (state.audioDb > 50) AlertCrimson.copy(alpha = 0.15f) else SafeEmerald.copy(alpha = 0.12f))
            .border(
              0.5.dp,
              if (state.audioDb > 50) AlertCrimson.copy(alpha = 0.4f) else SafeEmerald.copy(alpha = 0.3f),
              RoundedCornerShape(50)
            )
            .padding(horizontal = 7.dp, vertical = 2.dp)
        ) {
          Text(
            text = if (state.audioDb > 50) "GASP DETECTED" else "Quiet Ambient",
            color = if (state.audioDb > 50) AlertCrimson else SafeEmerald,
            fontSize = 8.5.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Row(verticalAlignment = Alignment.Bottom) {
        Text(
          text = "${state.audioDb.toInt()}",
          color = TextPrimary,
          fontFamily = FontFamily.Monospace,
          fontWeight = FontWeight.ExtraBold,
          fontSize = 30.sp
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text("dB SPL", color = TextSecondary, fontSize = 11.sp, modifier = Modifier.padding(bottom = 4.dp))
      }

      Text(
        text = "100Hz – 800Hz Formant Band",
        color = TextSecondary,
        fontSize = 9.5.sp
      )

      Spacer(modifier = Modifier.height(10.dp))

      // Spectrogram Canvas
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(72.dp)
          .clip(RoundedCornerShape(8.dp))
          .background(DeepObsidian)
          .border(1.dp, SlateBorder, RoundedCornerShape(8.dp))
          .padding(4.dp)
      ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
          val bars = 20
          val barWidth = size.width / bars
          val isSeizure = state.audioDb > 50

          for (i in 0 until bars) {
            val base = if (isSeizure && i in 7..14) {
              size.height * 0.85f
            } else {
              (state.audioDb / 80f) * size.height * (0.3f + ((i % 5) * 0.12f))
            }.coerceIn(5f, size.height)

            val x = i * barWidth
            val y = size.height - base

            drawRect(
              color = if (isSeizure) AlertCrimson else AccentCyan,
              topLeft = Offset(x + 2f, y),
              size = Size((barWidth - 4f).coerceAtLeast(2f), base)
            )
          }
        }
      }
    }
  }
}

@Composable
fun CardConfidenceGauge(state: TelemetryState, modifier: Modifier = Modifier) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(16.dp))
      .background(DeepObsidianSurface)
      .border(1.dp, SlateBorder, RoundedCornerShape(16.dp))
      .padding(14.dp)
      .testTag("card_confidence_gauge")
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(28.dp)
              .clip(RoundedCornerShape(7.dp))
              .background(DeepObsidianElevated)
              .border(1.dp, SlateBorder, RoundedCornerShape(7.dp)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Speed,
              contentDescription = "Gauge",
              tint = AccentCyan,
              modifier = Modifier.size(15.dp)
            )
          }
          Spacer(modifier = Modifier.width(8.dp))
          Text("AI Confidence", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 12.5.sp)
        }

        Text(
          "Gate: ≥82%",
          color = if (state.confidence >= 82f) AlertCrimson else TextSecondary,
          fontSize = 9.sp,
          fontWeight = FontWeight.Bold,
          fontFamily = FontFamily.Monospace
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Radial Gauge Canvas
      Box(
        modifier = Modifier.size(88.dp),
        contentAlignment = Alignment.Center
      ) {
        val conf = state.confidence
        Canvas(modifier = Modifier.fillMaxSize()) {
          val strokeWidth = 7.dp.toPx()
          val radius = (size.minDimension - strokeWidth) / 2f

          // Track
          drawCircle(
            color = SlateBorder,
            radius = radius,
            style = Stroke(width = strokeWidth)
          )

          // 82% threshold marker
          drawArc(
            color = AlertCrimson.copy(alpha = 0.5f),
            startAngle = -90f + (0.82f * 360f),
            sweepAngle = 4f,
            useCenter = false,
            style = Stroke(width = strokeWidth + 4f, cap = StrokeCap.Round)
          )

          // Active progress arc
          val sweep = (conf / 100f) * 360f
          val strokeColor = if (conf >= 82f) AlertCrimson else if (conf >= 50f) WarningAmber else AccentCyan
          drawArc(
            color = strokeColor,
            startAngle = -90f,
            sweepAngle = sweep,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
          )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Text(
            text = "${state.confidence.toInt()}%",
            color = TextPrimary,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 18.sp
          )
          Text(
            text = "ANOMALY",
            color = TextSecondary,
            fontSize = 7.5.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(8.dp))
          .background(DeepObsidianElevated)
          .border(1.dp, SlateBorder, RoundedCornerShape(8.dp))
          .padding(6.dp),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = if (state.confidence >= 82f) "CRITICAL BREACH" else "Sub-Threshold Safe",
          color = if (state.confidence >= 82f) AlertCrimson else SafeEmerald,
          fontWeight = FontWeight.Bold,
          fontSize = 9.5.sp
        )
      }
    }
  }
}

@Composable
fun TelemetryLogsBox(state: TelemetryState, modifier: Modifier = Modifier) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(16.dp))
      .background(DeepObsidianSurface)
      .border(1.dp, SlateBorder, RoundedCornerShape(16.dp))
      .padding(14.dp)
  ) {
    Text(
      text = "MULTI-MODAL EDGE INFERENCE STREAM",
      color = TextPrimary,
      fontWeight = FontWeight.Bold,
      fontSize = 10.sp,
      letterSpacing = 0.5.sp
    )
    Spacer(modifier = Modifier.height(8.dp))

    LazyColumn(
      modifier = Modifier
        .fillMaxWidth()
        .height(90.dp)
        .clip(RoundedCornerShape(8.dp))
        .background(DeepObsidian)
        .border(1.dp, SlateBorder, RoundedCornerShape(8.dp))
        .padding(8.dp),
      verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
      items(state.logs) { log ->
        Text(
          text = log,
          color = if (log.contains("CRITICAL")) AlertCrimson else if (log.contains("CANCEL")) SafeEmerald else TextSecondary,
          fontSize = 9.sp,
          fontFamily = FontFamily.Monospace
        )
      }
    }
  }
}
