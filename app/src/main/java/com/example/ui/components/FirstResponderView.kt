package com.example.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
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

@Composable
fun FirstResponderView(
  state: TelemetryState,
  onBackToMonitor: () -> Unit,
  onShowToast: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  var isDispatched by remember { mutableStateOf(false) }

  val infiniteTransition = rememberInfiniteTransition(label = "beacon")
  val beaconRadius by infiniteTransition.animateFloat(
    initialValue = 10f,
    targetValue = 42f,
    animationSpec = infiniteRepeatable(
      animation = tween(1500),
      repeatMode = RepeatMode.Restart
    ),
    label = "radius"
  )
  val beaconAlpha by infiniteTransition.animateFloat(
    initialValue = 0.7f,
    targetValue = 0f,
    animationSpec = infiniteRepeatable(
      animation = tween(1500),
      repeatMode = RepeatMode.Restart
    ),
    label = "alpha"
  )

  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(bottom = 20.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    // 1. Header Banner
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .background(DeepObsidianSurface)
        .border(1.dp, AlertCrimson.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
        .padding(14.dp)
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
                .size(34.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(DeepObsidianElevated)
                .border(1.dp, AlertCrimson.copy(alpha = 0.4f), RoundedCornerShape(8.dp)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Emergency,
                contentDescription = "Alert",
                tint = AlertCrimson,
                modifier = Modifier.size(18.dp)
              )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
              Text(
                text = "LEVEL-2 ESCALATION ACTIVE",
                color = AlertCrimson,
                fontWeight = FontWeight.Bold,
                fontSize = 11.5.sp
              )
              Text(
                text = "REF: #AEGIS-CRASH-7719",
                color = TextSecondary,
                fontFamily = FontFamily.Monospace,
                fontSize = 9.sp
              )
            }
          }

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .background(DeepObsidianElevated)
              .border(1.dp, SlateBorder, RoundedCornerShape(8.dp))
              .clickable { onBackToMonitor() }
              .padding(horizontal = 9.dp, vertical = 6.dp)
              .testTag("back_to_monitor_btn")
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = TextSecondary,
                modifier = Modifier.size(13.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "Monitor",
                color = TextPrimary,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold
              )
            }
          }
        }
      }
    }

    // 2. Patient Identity & Critical Medical Summary Card
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .background(DeepObsidianSurface)
        .border(1.dp, SlateBorder, RoundedCornerShape(16.dp))
        .padding(14.dp)
        .testTag("responder_patient_card")
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
                .size(38.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(DeepObsidianElevated)
                .border(1.dp, SlateBorder, RoundedCornerShape(10.dp)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Patient",
                tint = AccentCyan,
                modifier = Modifier.size(18.dp)
              )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = "Mohan Prasad D",
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
              )
              Text(
                text = "Age: 20 • Male • Solitary Resident",
                color = TextSecondary,
                fontSize = 10.sp
              )
            }
          }

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(DeepObsidianElevated)
              .border(0.5.dp, SlateBorder, RoundedCornerShape(6.dp))
              .padding(horizontal = 7.dp, vertical = 3.dp)
          ) {
            Text(
              text = "Blood: O+",
              color = AccentCyan,
              fontWeight = FontWeight.Bold,
              fontSize = 9.5.sp
            )
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Clinical Condition Tags
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(6.dp)
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
              Text("PRIMARY DIAGNOSIS", color = TextSecondary, fontSize = 7.5.sp, fontWeight = FontWeight.Bold)
              Text("Frontal Lobe Epilepsy", color = TextPrimary, fontSize = 9.5.sp, fontWeight = FontWeight.SemiBold)
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
              Text("MEDICATION", color = TextSecondary, fontSize = 7.5.sp, fontWeight = FontWeight.Bold)
              Text("Levetiracetam 500mg", color = TextPrimary, fontSize = 9.5.sp, fontWeight = FontWeight.SemiBold)
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
              Text("ALLERGIES", color = TextSecondary, fontSize = 7.5.sp, fontWeight = FontWeight.Bold)
              Text("Penicillin", color = AlertCrimson, fontSize = 9.5.sp, fontWeight = FontWeight.SemiBold)
            }
          }
        }
      }
    }

    // 3. Tactical Radar Map & GPS Coordinates
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .background(DeepObsidianSurface)
        .border(1.dp, SlateBorder, RoundedCornerShape(16.dp))
        .padding(14.dp)
        .testTag("responder_gps_map_card")
    ) {
      Column {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.LocationOn,
              contentDescription = "GPS",
              tint = AlertCrimson,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Live GPS Emergency Beacon",
              color = TextPrimary,
              fontWeight = FontWeight.Bold,
              fontSize = 12.5.sp
            )
          }

          Text(
            text = "Accurate: ±3m",
            color = SafeEmerald,
            fontFamily = FontFamily.Monospace,
            fontSize = 9.sp,
            fontWeight = FontWeight.Medium
          )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Tactical Radar Screen Canvas
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(DeepObsidian)
            .border(1.dp, SlateBorder, RoundedCornerShape(12.dp))
        ) {
          Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            val cx = w / 2f
            val cy = h / 2f

            // Distance Concentric Rings
            drawCircle(SlateBorder.copy(alpha = 0.5f), radius = 25f, center = Offset(cx, cy), style = Stroke(1f))
            drawCircle(SlateBorder.copy(alpha = 0.5f), radius = 55f, center = Offset(cx, cy), style = Stroke(1f))
            drawCircle(SlateBorder.copy(alpha = 0.5f), radius = 95f, center = Offset(cx, cy), style = Stroke(1f))

            // Crosshairs
            drawLine(SlateBorder.copy(alpha = 0.5f), Offset(cx, 0f), Offset(cx, h), strokeWidth = 1f)
            drawLine(SlateBorder.copy(alpha = 0.5f), Offset(0f, cy), Offset(w, cy), strokeWidth = 1f)

            // Animated Beacon Ripple
            drawCircle(
              color = AlertCrimson.copy(alpha = beaconAlpha),
              radius = beaconRadius,
              center = Offset(cx, cy),
              style = Stroke(2f)
            )

            // Patient Location Pin (Center)
            drawCircle(color = AlertCrimson, radius = 6f, center = Offset(cx, cy))
            drawCircle(color = Color.White, radius = 2.5f, center = Offset(cx, cy))
          }

          // Tactical Coordinate Overlay
          Column(
            modifier = Modifier
              .align(Alignment.BottomStart)
              .padding(10.dp)
              .clip(RoundedCornerShape(6.dp))
              .background(DeepObsidianSurface.copy(alpha = 0.9f))
              .border(0.5.dp, SlateBorder, RoundedCornerShape(6.dp))
              .padding(horizontal = 8.dp, vertical = 4.dp)
          ) {
            Text(
              text = "12.9716° N, 79.1585° E",
              color = TextPrimary,
              fontFamily = FontFamily.Monospace,
              fontWeight = FontWeight.Bold,
              fontSize = 9.sp
            )
            Text(
              text = "Solitary Residence, Apt 302, Chennai",
              color = TextSecondary,
              fontSize = 8.5.sp
            )
          }

          // Open Maps Button
          Box(
            modifier = Modifier
              .align(Alignment.BottomEnd)
              .padding(10.dp)
              .clip(RoundedCornerShape(6.dp))
              .background(DeepObsidianElevated)
              .border(0.5.dp, SlateBorder, RoundedCornerShape(6.dp))
              .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:12.9716,79.1585?q=12.9716,79.1585(Patient+Mohan+Prasad)"))
                try {
                  context.startActivity(intent)
                } catch (_: Exception) {
                  onShowToast("Map application not available on this device.")
                }
              }
              .padding(horizontal = 8.dp, vertical = 5.dp)
              .testTag("open_maps_btn")
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Navigation,
                contentDescription = "Navigate",
                tint = AccentCyan,
                modifier = Modifier.size(11.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text("Open Map", color = TextPrimary, fontSize = 9.sp, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }

    // 4. Quick Emergency Dispatch Actions (Uniform 2x2 Grid)
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .background(DeepObsidianSurface)
        .border(1.dp, SlateBorder, RoundedCornerShape(16.dp))
        .padding(14.dp)
    ) {
      Column {
        Text(
          text = "EMERGENCY PROTOCOL ACTIONS",
          color = TextPrimary,
          fontWeight = FontWeight.Bold,
          fontSize = 11.sp,
          letterSpacing = 0.5.sp
        )
        Spacer(modifier = Modifier.height(10.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          // Call 911 / EMS Button
          Button(
            onClick = {
              val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:108"))
              try {
                context.startActivity(intent)
              } catch (_: Exception) {
                onShowToast("Dialer not available on emulator.")
              }
            },
            colors = ButtonDefaults.buttonColors(containerColor = AlertCrimson),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
              .weight(1f)
              .height(46.dp)
              .testTag("call_ems_btn")
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.LocalHospital, contentDescription = "EMS", tint = Color.White, modifier = Modifier.size(15.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("Call EMS (108)", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 11.sp)
            }
          }

          // Call Family Contact Button
          Button(
            onClick = {
              val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:+919876543210"))
              try {
                context.startActivity(intent)
              } catch (_: Exception) {
                onShowToast("Dialer not available on emulator.")
              }
            },
            colors = ButtonDefaults.buttonColors(containerColor = DeepObsidianElevated),
            border = androidx.compose.foundation.BorderStroke(1.dp, SlateBorder),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
              .weight(1f)
              .height(46.dp)
              .testTag("call_family_btn")
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.Call, contentDescription = "Family", tint = AccentCyan, modifier = Modifier.size(15.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("Call Family", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 11.sp)
            }
          }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          // Share Live Link
          Button(
            onClick = {
              val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(
                  Intent.EXTRA_TEXT,
                  "EMERGENCY: Seizure breach detected for Mohan Prasad D. Live telemetry: https://aegishealth.internal/resp/AEGIS-9921"
                )
                type = "text/plain"
              }
              try {
                context.startActivity(Intent.createChooser(sendIntent, "Share Emergency Telemetry"))
              } catch (_: Exception) {
                onShowToast("Telemetry link copied.")
              }
            },
            colors = ButtonDefaults.buttonColors(containerColor = DeepObsidianElevated),
            border = androidx.compose.foundation.BorderStroke(1.dp, SlateBorder),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
              .weight(1f)
              .height(44.dp)
              .testTag("share_link_btn")
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.Share, contentDescription = "Share", tint = TextSecondary, modifier = Modifier.size(14.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("Share Telemetry", color = TextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 10.5.sp)
            }
          }

          // Confirm Dispatch
          Button(
            onClick = {
              isDispatched = true
              onShowToast("Emergency dispatch confirmed. Units en route (ETA: 4 mins).")
            },
            colors = ButtonDefaults.buttonColors(containerColor = if (isDispatched) SafeEmerald else DeepObsidianElevated),
            border = androidx.compose.foundation.BorderStroke(1.dp, if (isDispatched) SafeEmerald else SlateBorder),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
              .weight(1f)
              .height(44.dp)
              .testTag("confirm_dispatch_btn")
          ) {
            Text(
              text = if (isDispatched) "Dispatched (ETA 4m)" else "Confirm Dispatch",
              color = if (isDispatched) DeepObsidian else TextPrimary,
              fontWeight = FontWeight.Bold,
              fontSize = 10.5.sp
            )
          }
        }
      }
    }

    // 5. Incident Chronological Trace
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .background(DeepObsidianSurface)
        .border(1.dp, SlateBorder, RoundedCornerShape(16.dp))
        .padding(14.dp)
    ) {
      Column {
        Text(
          text = "INCIDENT TIMELINE TRACE",
          color = TextPrimary,
          fontWeight = FontWeight.Bold,
          fontSize = 11.sp,
          letterSpacing = 0.5.sp
        )
        Spacer(modifier = Modifier.height(10.dp))

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          TimelineItem("T-00:32", "PPG spike to 142 BPM with 1.9g clonic tremor spasm", AlertCrimson)
          TimelineItem("T-00:30", "Confidence breached 82% threshold (89% recorded)", AlertCrimson)
          TimelineItem("T-00:00", "Level-1 30s cancellation window expired without user cancel", WarningAmber)
          TimelineItem("T+00:01", "Level-2 First Responder escalation dispatched automatically", SafeEmerald)
        }
      }
    }
  }
}

@Composable
private fun TimelineItem(time: String, desc: String, color: Color) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Box(
      modifier = Modifier
        .size(6.dp)
        .clip(CircleShape)
        .background(color)
    )
    Spacer(modifier = Modifier.width(8.dp))
    Text(
      text = time,
      color = TextSecondary,
      fontFamily = FontFamily.Monospace,
      fontSize = 9.sp,
      fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.width(8.dp))
    Text(
      text = desc,
      color = TextPrimary,
      fontSize = 9.5.sp
    )
  }
}
