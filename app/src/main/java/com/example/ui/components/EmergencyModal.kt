package com.example.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.model.TelemetryState
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
fun EmergencyModal(
  state: TelemetryState,
  onCancel: () -> Unit,
  modifier: Modifier = Modifier
) {
  val infiniteTransition = rememberInfiniteTransition(label = "flash")
  val borderAlpha by infiniteTransition.animateFloat(
    initialValue = 0.35f,
    targetValue = 0.9f,
    animationSpec = infiniteRepeatable(
      animation = tween(500),
      repeatMode = RepeatMode.Reverse
    ),
    label = "flash_alpha"
  )

  Dialog(
    onDismissRequest = {},
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(DeepObsidian.copy(alpha = 0.95f))
        .border(4.dp, AlertCrimson.copy(alpha = borderAlpha))
        .padding(20.dp),
      contentAlignment = Alignment.Center
    ) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(22.dp))
          .background(DeepObsidianSurface)
          .border(1.5.dp, AlertCrimson, RoundedCornerShape(22.dp))
          .padding(22.dp)
          .testTag("emergency_modal_card")
      ) {
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center
        ) {
          // Warning Icon Badge
          Box(
            modifier = Modifier
              .size(50.dp)
              .clip(RoundedCornerShape(12.dp))
              .background(DeepObsidianElevated)
              .border(1.5.dp, AlertCrimson.copy(alpha = 0.6f), RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Warning,
              contentDescription = "Warning",
              tint = AlertCrimson,
              modifier = Modifier.size(28.dp)
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(50))
              .background(AlertCrimson.copy(alpha = 0.15f))
              .border(0.5.dp, AlertCrimson.copy(alpha = 0.5f), RoundedCornerShape(50))
              .padding(horizontal = 10.dp, vertical = 2.dp)
          ) {
            Text(
              text = "PRIORITY-1 CRITICAL BREACH",
              color = AlertCrimson,
              fontWeight = FontWeight.Bold,
              fontSize = 8.5.sp,
              letterSpacing = 0.8.sp
            )
          }

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = "SEIZURE / HEALTH CRASH DETECTED",
            color = TextPrimary,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 17.sp,
            textAlign = TextAlign.Center
          )

          Spacer(modifier = Modifier.height(4.dp))

          Text(
            text = "Telemetry confirms clonic spasms & tachycardia (Score: ${state.confidence.toInt()}%)",
            color = TextSecondary,
            fontSize = 11.sp,
            textAlign = TextAlign.Center
          )

          Spacer(modifier = Modifier.height(16.dp))

          // 30-Second Countdown Clock Ring
          Box(
            modifier = Modifier.size(124.dp),
            contentAlignment = Alignment.Center
          ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
              val strokeWidth = 9.dp.toPx()
              val radius = (size.minDimension - strokeWidth) / 2f

              // Background track
              drawCircle(
                color = SlateBorder,
                radius = radius,
                style = Stroke(width = strokeWidth)
              )

              // Progress sweep
              val progress = state.countdownRemaining / 30f
              val sweepAngle = progress * 360f

              drawArc(
                color = AlertCrimson,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
              )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text(
                text = "${state.countdownRemaining}",
                color = TextPrimary,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Black,
                fontSize = 40.sp,
                letterSpacing = (-1.5).sp
              )
              Text(
                text = "SECONDS LEFT",
                color = TextSecondary,
                fontSize = 8.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Audio/Vibration Notice
          Row(
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .background(DeepObsidianElevated)
              .border(0.5.dp, SlateBorder, RoundedCornerShape(8.dp))
              .padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.VolumeUp,
              contentDescription = "Alarm",
              tint = WarningAmber,
              modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Watch Vibration & Alarm Ringing",
              color = TextSecondary,
              fontSize = 9.5.sp,
              fontFamily = FontFamily.Monospace
            )
          }

          Spacer(modifier = Modifier.height(18.dp))

          // PRIMARY ACTION BUTTON: CANCEL ALARM (I'M OKAY)
          Button(
            onClick = onCancel,
            modifier = Modifier
              .fillMaxWidth()
              .height(50.dp)
              .testTag("cancel_alarm_btn"),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SafeEmerald)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Cancel",
                tint = DeepObsidian,
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "CANCEL ALARM (I'M OKAY)",
                color = DeepObsidian,
                fontWeight = FontWeight.Bold,
                fontSize = 13.5.sp
              )
            }
          }

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = "Cancels dispatch, resets score, and logs false positive retraining data.",
            color = TextSecondary,
            fontSize = 9.sp,
            textAlign = TextAlign.Center
          )
        }
      }
    }
  }
}
