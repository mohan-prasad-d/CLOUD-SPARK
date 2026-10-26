package com.example.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ActiveView
import com.example.model.DataSourceMode
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
fun HeaderBar(
  state: TelemetryState,
  onSwitchView: (ActiveView) -> Unit,
  onToggleSourceMode: () -> Unit,
  onToggleSpecs: () -> Unit,
  modifier: Modifier = Modifier
) {
  val infiniteTransition = rememberInfiniteTransition(label = "pulse")
  val pulseAlpha by infiniteTransition.animateFloat(
    initialValue = 0.5f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(
      animation = tween(900),
      repeatMode = RepeatMode.Reverse
    ),
    label = "alpha"
  )

  Column(
    modifier = modifier
      .fillMaxWidth()
      .background(DeepObsidian)
      .border(1.dp, SlateBorder, RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
      .padding(horizontal = 14.dp, vertical = 12.dp)
  ) {
    // 1. Brand & Top Controls Row
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
          modifier = Modifier
            .size(38.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(DeepObsidianSurface)
            .border(1.dp, SlateBorder, RoundedCornerShape(10.dp)),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Shield,
            contentDescription = "Aegis Shield",
            tint = AccentCyan,
            modifier = Modifier.size(20.dp)
          )
          Box(
            modifier = Modifier
              .align(Alignment.TopEnd)
              .padding(4.dp)
              .size(7.dp)
              .clip(CircleShape)
              .background(SafeEmerald.copy(alpha = pulseAlpha))
          )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = "AegisHealth",
              color = TextPrimary,
              fontWeight = FontWeight.Bold,
              fontSize = 17.sp,
              letterSpacing = (-0.4).sp
            )
            Spacer(modifier = Modifier.width(6.dp))
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(SafeEmerald.copy(alpha = 0.12f))
                .border(0.5.dp, SafeEmerald.copy(alpha = 0.3f), RoundedCornerShape(4.dp))
                .padding(horizontal = 5.dp, vertical = 1.dp)
            ) {
              Text(
                text = "ACTIVE",
                color = SafeEmerald,
                fontWeight = FontWeight.Bold,
                fontSize = 8.5.sp,
                letterSpacing = 0.5.sp
              )
            }
          }
          Text(
            text = "Silent Seizure & Emergency Response Platform",
            color = TextSecondary,
            fontSize = 10.sp
          )
        }
      }

      IconButton(
        onClick = onToggleSpecs,
        modifier = Modifier
          .size(36.dp)
          .clip(RoundedCornerShape(8.dp))
          .background(DeepObsidianSurface)
          .border(1.dp, SlateBorder, RoundedCornerShape(8.dp))
          .testTag("specs_toggle_btn")
      ) {
        Icon(
          imageVector = Icons.Default.Code,
          contentDescription = "Architecture Specs",
          tint = TextSecondary,
          modifier = Modifier.size(17.dp)
        )
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    // 2. Dual Data Source Selector (Sleek Segmented Pill)
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(10.dp))
        .background(DeepObsidianSurface)
        .border(1.dp, SlateBorder, RoundedCornerShape(10.dp))
        .padding(3.dp)
        .testTag("toggle_sensor_mode_btn")
    ) {
      val isLive = state.sourceMode == DataSourceMode.LIVE_SENSORS

      // Tab 1: Live Hardware
      Box(
        modifier = Modifier
          .weight(1f)
          .clip(RoundedCornerShape(7.dp))
          .background(if (isLive) DeepObsidianElevated else Color.Transparent)
          .border(
            width = if (isLive) 1.dp else 0.dp,
            color = if (isLive) AccentCyan.copy(alpha = 0.35f) else Color.Transparent,
            shape = RoundedCornerShape(7.dp)
          )
          .clickable { if (!isLive) onToggleSourceMode() }
          .padding(vertical = 6.dp),
        contentAlignment = Alignment.Center
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.Sensors,
            contentDescription = "Sensors",
            tint = if (isLive) AccentCyan else TextMuted,
            modifier = Modifier.size(13.dp)
          )
          Spacer(modifier = Modifier.width(5.dp))
          Text(
            text = "Live Device Sensors",
            color = if (isLive) TextPrimary else TextSecondary,
            fontWeight = if (isLive) FontWeight.Bold else FontWeight.Normal,
            fontSize = 10.sp
          )
        }
      }

      // Tab 2: Simulation Deck
      Box(
        modifier = Modifier
          .weight(1f)
          .clip(RoundedCornerShape(7.dp))
          .background(if (!isLive) DeepObsidianElevated else Color.Transparent)
          .border(
            width = if (!isLive) 1.dp else 0.dp,
            color = if (!isLive) AccentCyan.copy(alpha = 0.35f) else Color.Transparent,
            shape = RoundedCornerShape(7.dp)
          )
          .clickable { if (isLive) onToggleSourceMode() }
          .padding(vertical = 6.dp),
        contentAlignment = Alignment.Center
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.Tune,
            contentDescription = "Simulate",
            tint = if (!isLive) AccentCyan else TextMuted,
            modifier = Modifier.size(13.dp)
          )
          Spacer(modifier = Modifier.width(5.dp))
          Text(
            text = "Pitch Simulation Deck",
            color = if (!isLive) TextPrimary else TextSecondary,
            fontWeight = if (!isLive) FontWeight.Bold else FontWeight.Normal,
            fontSize = 10.sp
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    // 3. Technical Telemetry Node Badges
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(6.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      // BLE Status
      Row(
        modifier = Modifier
          .weight(1f)
          .clip(RoundedCornerShape(7.dp))
          .background(DeepObsidianSurface)
          .border(1.dp, SlateBorder, RoundedCornerShape(7.dp))
          .padding(horizontal = 8.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
      ) {
        Icon(
          imageVector = Icons.Default.Bluetooth,
          contentDescription = "BLE",
          tint = if (state.bleConnected) AccentCyan else AlertCrimson,
          modifier = Modifier.size(12.dp)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
          text = if (state.bleConnected) "BLE: 0.8s" else "BLE: Lost",
          color = if (state.bleConnected) TextPrimary else AlertCrimson,
          fontSize = 9.5.sp,
          fontFamily = FontFamily.Monospace,
          fontWeight = FontWeight.Medium
        )
      }

      // Cloud Inference
      Row(
        modifier = Modifier
          .weight(1f)
          .clip(RoundedCornerShape(7.dp))
          .background(DeepObsidianSurface)
          .border(1.dp, SlateBorder, RoundedCornerShape(7.dp))
          .padding(horizontal = 8.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
      ) {
        Icon(
          imageVector = Icons.Default.Memory,
          contentDescription = "Cloud",
          tint = TextSecondary,
          modifier = Modifier.size(12.dp)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
          text = "PyTorch v2.1",
          color = TextSecondary,
          fontSize = 9.5.sp,
          fontFamily = FontFamily.Monospace
        )
      }

      // Battery
      Row(
        modifier = Modifier
          .clip(RoundedCornerShape(7.dp))
          .background(DeepObsidianSurface)
          .border(1.dp, SlateBorder, RoundedCornerShape(7.dp))
          .padding(horizontal = 8.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          imageVector = Icons.Default.BatteryChargingFull,
          contentDescription = "Battery",
          tint = SafeEmerald,
          modifier = Modifier.size(12.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
          text = "88%",
          color = TextPrimary,
          fontSize = 9.5.sp,
          fontFamily = FontFamily.Monospace
        )
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    // 4. Primary View Navigation Tabs (Executive Segmented Switcher)
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(10.dp))
        .background(DeepObsidianSurface)
        .border(1.dp, SlateBorder, RoundedCornerShape(10.dp))
        .padding(3.dp)
    ) {
      val isPatient = state.view == ActiveView.PATIENT_MONITOR

      Box(
        modifier = Modifier
          .weight(1f)
          .clip(RoundedCornerShape(8.dp))
          .background(if (isPatient) DeepObsidianElevated else Color.Transparent)
          .border(
            width = if (isPatient) 1.dp else 0.dp,
            color = if (isPatient) SlateBorder else Color.Transparent,
            shape = RoundedCornerShape(8.dp)
          )
          .clickable { onSwitchView(ActiveView.PATIENT_MONITOR) }
          .padding(vertical = 7.dp)
          .testTag("nav_patient_btn"),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = "Patient Telemetry",
          color = if (isPatient) TextPrimary else TextSecondary,
          fontWeight = if (isPatient) FontWeight.Bold else FontWeight.Normal,
          fontSize = 11.sp
        )
      }

      Box(
        modifier = Modifier
          .weight(1f)
          .clip(RoundedCornerShape(8.dp))
          .background(if (!isPatient) DeepObsidianElevated else Color.Transparent)
          .border(
            width = if (!isPatient) 1.dp else 0.dp,
            color = if (!isPatient) SlateBorder else Color.Transparent,
            shape = RoundedCornerShape(8.dp)
          )
          .clickable { onSwitchView(ActiveView.FIRST_RESPONDER) }
          .padding(vertical = 7.dp)
          .testTag("nav_responder_btn"),
        contentAlignment = Alignment.Center
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = "First Responder View",
            color = if (!isPatient) TextPrimary else TextSecondary,
            fontWeight = if (!isPatient) FontWeight.Bold else FontWeight.Normal,
            fontSize = 11.sp
          )
          if (state.confidence >= 82f) {
            Spacer(modifier = Modifier.width(5.dp))
            Box(
              modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(AlertCrimson)
            )
          }
        }
      }
    }
  }
}
