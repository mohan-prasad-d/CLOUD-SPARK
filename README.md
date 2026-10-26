# AegisHealth 🛡️
### Software-Driven Silent Seizure & Solitary Health Emergency Response Platform

![Android](https://img.shields.io/badge/Platform-Android_14+_API_36-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-2.0-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-M3-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)
![Status](https://img.shields.io/badge/Status-Production_Ready-10B981?style=for-the-badge)

---

## 📌 Overview

**AegisHealth** is an emergency telemetry and autonomous health crisis response system tailored specifically for solitary patients (such as individuals living alone with **Nocturnal Frontal Lobe Epilepsy** or severe cardiac/seizure risk).

The platform continuously analyzes multi-modal biometric signals at the edge to detect silent seizure episodes, clonic tremor turbulence, and respiratory distress. When an anomaly breaches the safety threshold, it initiates a **fail-safe 30-second cancellation window** before automatically escalating to first responders and emergency caregivers.

---

## ✨ Key Features & Architecture

### 1. Dual-Engine Sensor Pipeline
- **📡 Live Device Hardware Mode:**
  - Real hardware **3-Axis Accelerometer** computing Euclidean Vector Magnitude:
    $$\text{VM} = \frac{\sqrt{x^2 + y^2 + z^2}}{9.81} \text{ g}$$
    Physically shaking or moving the phone dynamically triggers tremor analysis!
  - Real hardware **Microphone Audio Meter** calculating real-time RMS decibel pressure ($\text{dB SPL}$) to capture nocturnal respiratory stridor and gasps in the $100\text{Hz} - 800\text{Hz}$ frequency band.
- **🧪 Pitch Simulation Deck:**
  - Instant one-tap demonstration triggers for juries, doctors, and pitch presentations:
    1. **Normal Sleep Baseline:** PPG 68–74 BPM, VM 0.08g, Anomaly Score 12%.
    2. **Trigger Silent Seizure (89% Breach):** Rapid tachycardia spike to 142 BPM, 1.9g clonic tremor spasm, and automatic Level-1 emergency modal activation.
    3. **BLE Watchdog Drop:** Heartbeat ping timeout simulation with cloud watchdog auto-recovery.
  - Interactive **Sensor Fine-Tuning Dialog** to manually adjust BPM, acceleration, acoustics, and anomaly confidence in real-time.

### 2. Multi-Modal Telemetry Dashboard
- **PPG Heart Stream:** Clinical pulse waveform canvas rendering systolic peaks, dicrotic notch reflections, and diastolic runoff with tachycardia detection.
- **IMU Motion Vector:** Real-time 3-axis ($X, Y, Z$) displacement gauges with tremor classification.
- **Acoustic Formant Spectrogram:** Live audio frequency equalizer monitoring respiratory distress.
- **AI Anomaly Confidence Gauge:** Radial dial tracking anomaly score against the strict **$\ge 82\%$ Emergency Gate**.

### 3. Level-1: 30-Second Fail-Safe Cancellation Window
- When confidence reaches $\ge 82\%$, a full-screen, high-priority emergency overlay is activated with device vibration and audio alerting.
- **30-Second Circular Countdown Ring:** Gives conscious patients a fail-safe window to tap **"CANCEL ALARM (I'M OKAY)"**.
- **Model Retraining Feedback:** Cancelling dispatches marks the incident as *"False Positive Retraining Data"* to refine edge ML weights.

### 4. Level-2: First Responder & Caregiver View
- Automatically triggered if the 30-second countdown expires without patient intervention.
- **Tactical GPS Radar:** Real-time coordinates (`12.9716° N, 79.1585° E`) with distance rings and one-tap **"Open Map"** navigation.
- **Emergency Action Suite:**
  - 📞 **Call EMS (108 / 911)**
  - 👨‍👩‍👧 **Call Family Emergency Contact**
  - 🔗 **Share Live Telemetry Web Link**
  - 🚑 **Confirm Dispatch Vehicle (ETA 4 min)**
- **Patient Medical Profile:** Blood group (O+), clinical diagnosis, current prescriptions (Levetiracetam), and known allergies (Penicillin).
- **Incident Timeline Trace:** Millisecond-accurate audit log of biometric breach events.

---

## 📱 How to Download & Install the APK

The project compiles a fully native, standalone Android APK ready for sideloading onto any physical Android phone running **Android 7.0 (Nougat, API 24) or newer** (optimized for Android 14/15/16).

### APK File Path:
```text
apk/AegisHealth-v1.0.apk
(Also available at: .build-outputs/app-debug.apk)
```

### Installation Steps:
1. **Transfer the APK to your Android device** (via Google Drive, USB cable, WhatsApp, or downloading directly from GitHub releases/repo).
2. On your Android phone, open the APK file in **Files** or **Downloads**.
3. If prompted with *"For your security, your phone is not allowed to install unknown apps from this source"*, tap **Settings** and toggle **"Allow from this source"**.
4. Tap **Install** and then **Open**.
5. Grant the **Microphone permission** when prompted to enable live acoustic decibel monitoring (accelerometer sensors work automatically with zero permissions needed).

---

## 🛠️ Building From Source

### Prerequisites:
- **Android Studio** Ladybug (2024.2+) or Hedgehog+
- **JDK 17** or **JDK 21**
- **Android SDK API 36**

### Build Commands:
```bash
# Clone the repository
git clone <your-repo-url>
cd AegisHealth

# Build Debug APK
gradle assembleDebug

# Run Unit & Robolectric Tests
gradle :app:testDebugUnitTest

# Install directly to a connected device via ADB (if ADB is available)
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

---

## ☁️ Exporting & Pushing to GitHub from AI Studio

To publish this project to your personal GitHub account from Google AI Studio:

1. Click on the **Settings / Export** menu in the top-right corner of the AI Studio window.
2. Select **"Push to GitHub"** (or connect your GitHub account `mohanprasadd2020@gmail.com`).
3. Enter your repository name (e.g., `AegisHealth-Emergency-Guardian`) and confirm the export.
4. Once pushed, you can also attach the `app-debug.apk` file to a **GitHub Release** so anyone worldwide can download and install it with a single click!

---

## 🔒 Security & Privacy

- **On-Device Edge Computing:** All motion vector calculation and decibel analysis occur locally on the device with zero cloud latency.
- **Fail-Safe Dispatch:** Network drops automatically fall back to automated cellular SMS and voice calls.
- **No Mock Placeholders:** Hardware sensor streams use authentic Android SensorManager APIs (`TYPE_ACCELEROMETER`, `AudioRecord`).

---

**Developed with ❤️ for solitary patient safety and rapid emergency intervention.**
