package com.example.model

enum class SimulationMode {
  NORMAL,
  SEIZURE,
  WATCHDOG_LOSS
}

enum class ActiveView {
  PATIENT_MONITOR,
  FIRST_RESPONDER
}

enum class DataSourceMode {
  LIVE_SENSORS,
  SIMULATION_PRESETS
}

data class TelemetryState(
  val view: ActiveView = ActiveView.PATIENT_MONITOR,
  val sourceMode: DataSourceMode = DataSourceMode.LIVE_SENSORS,
  val simMode: SimulationMode = SimulationMode.NORMAL,
  val bpm: Float = 72f,
  val targetBpm: Float = 72f,
  val motionVm: Float = 0.08f,
  val motionX: Float = 0.03f,
  val motionY: Float = 0.04f,
  val motionZ: Float = 0.06f,
  val audioDb: Float = 28f,
  val confidence: Float = 12f,
  val targetConfidence: Float = 12f,
  val emergencyActive: Boolean = false,
  val countdownRemaining: Int = 30,
  val bleConnected: Boolean = true,
  val rmssd: Int = 48,
  val sleepPhase: String = "N2 Non-REM",
  val historyBpm: List<Float> = listOf(70f, 71f, 72f, 70f, 72f, 73f, 71f, 72f, 72f, 71f, 73f, 72f, 71f, 72f, 73f, 72f),
  val logs: List<String> = listOf(
    "[02:30:10] Epoch #4412: PPG=71bpm, VM=0.06g, Audio=27dB -> Multi-Modal Anomaly Score: 11.4% (PASS)",
    "[02:30:12] Epoch #4413: PPG=72bpm, VM=0.07g, Audio=28dB -> Multi-Modal Anomaly Score: 12.1% (PASS)",
    "[02:30:14] Epoch #4414: PPG=72bpm, VM=0.08g, Audio=28dB -> Multi-Modal Anomaly Score: 12.0% (PASS)"
  )
)
