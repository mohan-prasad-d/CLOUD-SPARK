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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Watch
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

@Composable
fun ArchitectureDrawer(
  expanded: Boolean,
  onToggle: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(16.dp))
      .background(DeepObsidianSurface)
      .border(1.dp, SlateBorder, RoundedCornerShape(16.dp))
      .padding(14.dp)
      .testTag("specs_drawer_card")
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .clickable { onToggle() },
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
          modifier = Modifier
            .size(32.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(DeepObsidianElevated)
            .border(1.dp, SlateBorder, RoundedCornerShape(8.dp)),
          contentAlignment = Alignment.Center
        ) {
          Icon(Icons.Default.Layers, contentDescription = "Arch", tint = AccentCyan, modifier = Modifier.size(17.dp))
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column {
          Text("3-Tier System Architecture", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 12.5.sp)
          Text("Engineering specifications & edge inference", color = TextSecondary, fontSize = 9.5.sp)
        }
      }

      Icon(
        imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
        contentDescription = "Expand",
        tint = TextSecondary,
        modifier = Modifier.size(20.dp)
      )
    }

    AnimatedVisibility(visible = expanded) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        TierSpecCard(
          tier = "Tier 1: Wearable Device",
          title = "Kotlin Wear OS BLE Service",
          icon = Icons.Default.Watch,
          accentColor = AccentCyan,
          points = listOf(
            "Foreground Android Service with WakeLock protection",
            "Continuous 1Hz PPG Photoplethysmography sensor pipeline",
            "100Hz 3-axis Accelerometer with real-time vector magnitude VM",
            "Low-power BLE GATT server with zero packet drop MTU buffering",
            "On-watch circular haptic feedback during Level-1 countdown"
          )
        )

        TierSpecCard(
          tier = "Tier 2: Mobile Gateway",
          title = "Edge ML Filter & Countdown UI",
          icon = Icons.Default.PhoneAndroid,
          accentColor = SafeEmerald,
          points = listOf(
            "Central receiver listening to BLE GATT streams",
            "Quantized TensorFlow Lite (TFLite) 10-second sliding window model",
            "30-second fail-safe local countdown timer before cloud dispatch",
            "Bypasses Do Not Disturb (DND) with FLAG_AUDIBILITY_ENFORCED",
            "Automatic fall-back to cellular SMS if WiFi drops"
          )
        )

        TierSpecCard(
          tier = "Tier 3: Cloud Dispatch",
          title = "FastAPI & PyTorch Engine",
          icon = Icons.Default.Cloud,
          accentColor = AlertCrimson,
          points = listOf(
            "PyTorch Isolation Forest + Temporal Convolutional Network (TCN)",
            "Supabase PostgreSQL database with PostGIS for geofenced responders",
            "Twilio Voice & SMS webhooks delivering dynamic incident tokens",
            "Automated retraining pipeline on user 'Cancel Alarm' false positives",
            "Sub-second cloud latency (42ms typical REST endpoint)"
          )
        )
      }
    }
  }
}

@Composable
private fun TierSpecCard(
  tier: String,
  title: String,
  icon: ImageVector,
  accentColor: Color,
  points: List<String>
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(10.dp))
      .background(DeepObsidian)
      .border(1.dp, SlateBorder, RoundedCornerShape(10.dp))
      .padding(10.dp)
  ) {
    Column {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = tier, tint = accentColor, modifier = Modifier.size(13.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Text(tier.uppercase(), color = accentColor, fontWeight = FontWeight.Bold, fontSize = 8.5.sp)
      }
      Text(title, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 11.sp, modifier = Modifier.padding(vertical = 2.dp))

      Column(modifier = Modifier.padding(top = 4.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
        points.forEach { pt ->
          Row(verticalAlignment = Alignment.Top) {
            Text("• ", color = accentColor, fontSize = 9.sp)
            Text(pt, color = TextSecondary, fontSize = 9.sp)
          }
        }
      }
    }
  }
}
