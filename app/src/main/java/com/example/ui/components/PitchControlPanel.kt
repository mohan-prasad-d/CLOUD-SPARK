package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.SimulationMode
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

@Composable
fun PitchControlPanel(
  state: TelemetryState,
  onTriggerSim: (SimulationMode) -> Unit,
  onManualUpdate: (bpm: Float, vm: Float, audio: Float, conf: Float) -> Unit,
  modifier: Modifier = Modifier
) {
  var showSlidersDialog by remember { mutableStateOf(false) }

  Column(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(16.dp))
      .background(DeepObsidianSurface)
      .border(1.dp, SlateBorder, RoundedCornerShape(16.dp))
      .padding(14.dp)
  ) {
    // Header & Custom Tuning Button
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column {
        Text(
          text = "SIMULATION CONTROL DECK",
          color = TextPrimary,
          fontWeight = FontWeight.Bold,
          fontSize = 11.5.sp,
          letterSpacing = 0.5.sp
        )
        Text(
          text = "Interactive presentation triggers & threshold validation",
          color = TextSecondary,
          fontSize = 9.5.sp
        )
      }

      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(8.dp))
          .background(DeepObsidianElevated)
          .border(1.dp, SlateBorder, RoundedCornerShape(8.dp))
          .clickable { showSlidersDialog = true }
          .padding(horizontal = 9.dp, vertical = 5.dp)
          .testTag("sliders_modal_btn"),
        contentAlignment = Alignment.Center
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.Tune,
            contentDescription = "Tune",
            tint = AccentCyan,
            modifier = Modifier.size(13.dp)
          )
          Spacer(modifier = Modifier.width(5.dp))
          Text(
            text = "Fine-Tune",
            color = TextPrimary,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(12.dp))

    // 3 Equal-Sized, Refined Simulation Action Cards
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      // 1. Normal Sleep Baseline
      Box(
        modifier = Modifier
          .weight(1f)
          .clip(RoundedCornerShape(10.dp))
          .background(DeepObsidianElevated)
          .border(
            1.dp,
            if (state.simMode == SimulationMode.NORMAL) SafeEmerald.copy(alpha = 0.6f) else SlateBorder,
            RoundedCornerShape(10.dp)
          )
          .clickable { onTriggerSim(SimulationMode.NORMAL) }
          .padding(vertical = 10.dp, horizontal = 8.dp)
          .testTag("btn_sim_normal"),
        contentAlignment = Alignment.Center
      ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Nightlight,
              contentDescription = "Normal",
              tint = SafeEmerald,
              modifier = Modifier.size(13.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "Normal",
              color = TextPrimary,
              fontSize = 10.5.sp,
              fontWeight = FontWeight.Bold
            )
          }
          Spacer(modifier = Modifier.height(3.dp))
          Text(
            text = "72 BPM • 0.08g",
            color = TextSecondary,
            fontSize = 8.5.sp,
            fontFamily = FontFamily.Monospace
          )
          Text(
            text = "Score: 12%",
            color = SafeEmerald,
            fontSize = 8.sp,
            fontWeight = FontWeight.SemiBold
          )
        }
      }

      // 2. Trigger Silent Seizure (89% Breach)
      Box(
        modifier = Modifier
          .weight(1f)
          .clip(RoundedCornerShape(10.dp))
          .background(DeepObsidianElevated)
          .border(
            1.dp,
            if (state.simMode == SimulationMode.SEIZURE) AlertCrimson else SlateBorder,
            RoundedCornerShape(10.dp)
          )
          .clickable { onTriggerSim(SimulationMode.SEIZURE) }
          .padding(vertical = 10.dp, horizontal = 8.dp)
          .testTag("btn_sim_seizure"),
        contentAlignment = Alignment.Center
      ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Warning,
              contentDescription = "Seizure",
              tint = AlertCrimson,
              modifier = Modifier.size(13.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "Seizure",
              color = TextPrimary,
              fontSize = 10.5.sp,
              fontWeight = FontWeight.Bold
            )
          }
          Spacer(modifier = Modifier.height(3.dp))
          Text(
            text = "142 BPM • 1.9g",
            color = TextSecondary,
            fontSize = 8.5.sp,
            fontFamily = FontFamily.Monospace
          )
          Text(
            text = "Score: 89% (Gate)",
            color = AlertCrimson,
            fontSize = 8.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }

      // 3. BLE Watchdog Drop
      Box(
        modifier = Modifier
          .weight(1f)
          .clip(RoundedCornerShape(10.dp))
          .background(DeepObsidianElevated)
          .border(
            1.dp,
            if (state.simMode == SimulationMode.WATCHDOG_LOSS) WarningAmber else SlateBorder,
            RoundedCornerShape(10.dp)
          )
          .clickable { onTriggerSim(SimulationMode.WATCHDOG_LOSS) }
          .padding(vertical = 10.dp, horizontal = 8.dp)
          .testTag("btn_sim_watchdog"),
        contentAlignment = Alignment.Center
      ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.WifiOff,
              contentDescription = "Watchdog",
              tint = WarningAmber,
              modifier = Modifier.size(13.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "BLE Loss",
              color = TextPrimary,
              fontSize = 10.5.sp,
              fontWeight = FontWeight.Bold
            )
          }
          Spacer(modifier = Modifier.height(3.dp))
          Text(
            text = "Heartbeat Drop",
            color = TextSecondary,
            fontSize = 8.5.sp,
            fontFamily = FontFamily.Monospace
          )
          Text(
            text = "Watchdog Ping",
            color = WarningAmber,
            fontSize = 8.sp,
            fontWeight = FontWeight.SemiBold
          )
        }
      }
    }

    // Watchdog Alert Banner (Subdued & Clinical)
    AnimatedVisibility(visible = !state.bleConnected) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 10.dp)
          .clip(RoundedCornerShape(8.dp))
          .background(DeepObsidianElevated)
          .border(1.dp, WarningAmber.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
          .padding(10.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.Warning,
              contentDescription = "Warning",
              tint = WarningAmber,
              modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "WATCHDOG ALERT: BLE packet timeout. Auto-reconnecting...",
              color = WarningAmber,
              fontSize = 9.5.sp,
              fontWeight = FontWeight.Medium
            )
          }

          Text(
            text = "Reconnect",
            color = TextPrimary,
            fontSize = 9.5.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
              .clickable { onTriggerSim(SimulationMode.NORMAL) }
              .padding(start = 8.dp)
          )
        }
      }
    }
  }

  // Fine-Tune Sliders Dialog
  if (showSlidersDialog) {
    Dialog(onDismissRequest = { showSlidersDialog = false }) {
      var localBpm by remember { mutableStateOf(state.bpm) }
      var localVm by remember { mutableStateOf(state.motionVm) }
      var localAudio by remember { mutableStateOf(state.audioDb) }
      var localConf by remember { mutableStateOf(state.confidence) }

      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(18.dp))
          .background(DeepObsidianSurface)
          .border(1.dp, SlateBorder, RoundedCornerShape(18.dp))
          .padding(18.dp)
      ) {
        Column {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = "Manual Sensor Tuning",
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
              )
              Text(
                text = "Adjust clinical thresholds in real-time",
                color = TextSecondary,
                fontSize = 10.sp
              )
            }
            IconButton(
              onClick = { showSlidersDialog = false },
              modifier = Modifier.size(30.dp)
            ) {
              Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = TextSecondary,
                modifier = Modifier.size(18.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          // BPM Slider
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text("Simulated Heart Rate (PPG):", color = TextSecondary, fontSize = 11.sp)
            Text(
              "${localBpm.toInt()} BPM",
              color = AccentCyan,
              fontFamily = FontFamily.Monospace,
              fontWeight = FontWeight.Bold,
              fontSize = 11.sp
            )
          }
          Slider(
            value = localBpm,
            onValueChange = { localBpm = it },
            valueRange = 50f..180f,
            colors = SliderDefaults.colors(
              thumbColor = AccentCyan,
              activeTrackColor = AccentCyan,
              inactiveTrackColor = SlateBorder
            )
          )

          // Motion VM Slider
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text("Motion Vector Magnitude (VM):", color = TextSecondary, fontSize = 11.sp)
            Text(
              "${"%.2f".format(localVm)} g",
              color = AccentCyan,
              fontFamily = FontFamily.Monospace,
              fontWeight = FontWeight.Bold,
              fontSize = 11.sp
            )
          }
          Slider(
            value = localVm,
            onValueChange = { localVm = it },
            valueRange = 0f..3.5f,
            colors = SliderDefaults.colors(
              thumbColor = AccentCyan,
              activeTrackColor = AccentCyan,
              inactiveTrackColor = SlateBorder
            )
          )

          // Audio dB Slider
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text("Acoustic Amplitude (dB):", color = TextSecondary, fontSize = 11.sp)
            Text(
              "${localAudio.toInt()} dB SPL",
              color = AccentCyan,
              fontFamily = FontFamily.Monospace,
              fontWeight = FontWeight.Bold,
              fontSize = 11.sp
            )
          }
          Slider(
            value = localAudio,
            onValueChange = { localAudio = it },
            valueRange = 20f..90f,
            colors = SliderDefaults.colors(
              thumbColor = AccentCyan,
              activeTrackColor = AccentCyan,
              inactiveTrackColor = SlateBorder
            )
          )

          // Anomaly Confidence Slider
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text("AI Anomaly Confidence:", color = TextSecondary, fontSize = 11.sp)
            Text(
              "${localConf.toInt()}%",
              color = if (localConf >= 82f) AlertCrimson else AccentCyan,
              fontFamily = FontFamily.Monospace,
              fontWeight = FontWeight.Bold,
              fontSize = 11.sp
            )
          }
          Slider(
            value = localConf,
            onValueChange = { localConf = it },
            valueRange = 0f..100f,
            colors = SliderDefaults.colors(
              thumbColor = if (localConf >= 82f) AlertCrimson else AccentCyan,
              activeTrackColor = if (localConf >= 82f) AlertCrimson else AccentCyan,
              inactiveTrackColor = SlateBorder
            )
          )

          Spacer(modifier = Modifier.height(14.dp))

          Button(
            onClick = {
              onManualUpdate(localBpm, localVm, localAudio, localConf)
              showSlidersDialog = false
            },
            colors = ButtonDefaults.buttonColors(containerColor = AccentCyan),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth().height(46.dp)
          ) {
            Text("Apply Parameters", color = DeepObsidian, fontWeight = FontWeight.Bold, fontSize = 13.sp)
          }
        }
      }
    }
  }
}
