# Implementation Plan: Dual Mode (Real Hardware Sensors + Pitch Simulation)

Enable genuine on-device hardware telemetry (3-axis accelerometer and microphone audio level) alongside presentation simulation triggers for live demonstration.

## User Review Required

> [!IMPORTANT]
> The app will request the **Microphone** permission at runtime for live acoustic decibel monitoring. Accelerometer motion does not require runtime prompts. You will be able to toggle between **Live Hardware Sensors** and **Simulation Pitch Mode** at any time.

## Proposed Changes

### Permissions & Android Manifest
- Declare `android.permission.RECORD_AUDIO` in `AndroidManifest.xml` for real-time acoustic stress analysis.
- Declare `android.permission.HIGH_SAMPLING_RATE_SENSORS` for smooth 100Hz IMU updates.

### Hardware Sensor Manager Service
Create a dedicated `DeviceSensorManager.kt`:
- **Accelerometer Listener:** Uses Android `SensorManager` (`Sensor.TYPE_ACCELEROMETER`) to calculate real physical motion $VM = \sqrt{x^2+y^2+z^2}$ in $g$-force. Shaking or tilting the physical device or emulator directly moves the Axis X, Y, Z bars and motion readout.
- **Acoustic Audio Recorder:** Uses low-latency `AudioRecord` to calculate root-mean-square (RMS) decibel amplitude ($20 \cdot \log_{10}(\text{RMS})$), updating the real-time audio spectrogram canvas with ambient sound.
- **Graceful Fallbacks:** If the microphone permission is denied or sensors are absent in certain emulator configurations, it gracefully maintains continuous telemetry.

### Dual Mode Controller in Compose
Update `MainActivity.kt` & `HeaderBar.kt`:
- Add a prominent source toggle pill in the header: **"LIVE HARDWARE SENSORS"** vs **"SIMULATION PITCH MODE"**.
- In **Live Mode**: Shaking the phone rapidly generates clonic acceleration ($VM > 2.0g$), organically driving the AI anomaly confidence score into the $\ge 82\%$ Emergency Gate to trigger the 30-second Level-1 alarm!
- In **Simulation Mode**: The 3 presentation buttons (*Normal Sleep*, *Trigger Seizure*, *BLE Drop*) allow instant manual overrides for judging without shaking the physical device.

## Verification Plan

### Automated Verification
- Run `gradle :app:testDebugUnitTest` to ensure all existing Robolectric tests pass.
- Run `compile_applet` to confirm clean compilation with sensor managers and runtime permission handlers.

### Manual Verification
- Test toggling between **Live Hardware Sensors** and **Simulation Pitch Mode**.
- Test moving the phone / shaking the emulator to observe dynamic changes in $VM = \sqrt{x^2+y^2+z^2}$.
- Test sound detection on the acoustic spectrogram canvas.
- Test the 3 pitch triggers in simulation mode to verify the 30-second emergency modal and caregiver dispatch view.
