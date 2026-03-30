# 🦷 DentaCore — Dental ERP for Android

> A modern, offline-first dental clinic management app built with Java & Android Studio. Manage patients, build prescriptions, track billing, record voice notes, and view revenue analytics — all stored locally on-device.

---

## ✨ Features

- **Patient Records** — Store full patient profiles including blood group, allergies, and medical history
- **FDI Dental Chart** — Select from all 32 teeth (FDI notation) per visit; history tracked across prescriptions
- **Prescription Builder** — Add medications with dosage, frequency, duration, and instructions
- **PDF Export & Share** — Generate branded prescription PDFs using Android's native `PdfDocument` API; share via WhatsApp, email, etc.
- **Voice Notes** — Record and playback doctor notes per patient using `MediaRecorder`
- **Billing Tracker** — Track total billed, amount paid, and outstanding balance per patient and per visit
- **Analytics Dashboard** — 6-month revenue bar chart and procedure breakdown pie chart (MPAndroidChart)
- **Live Search** — Real-time patient search by name or phone number
- **Fully Offline** — All data stored locally using Room (SQLite); no internet required

---

## 🛠 Tech Stack

| Layer | Technology |
|---|---|
| Language | Java |
| UI | XML Layouts, ConstraintLayout, Material Components 1.9.0 |
| Database | Room 2.5.2 (SQLite) + LiveData |
| Architecture | Activity-based, DAO pattern, Repository |
| Charts | MPAndroidChart 3.1.0 |
| PDF | Android native `android.graphics.pdf.PdfDocument` |
| Voice | `MediaRecorder` / `MediaPlayer` |
| File Sharing | `FileProvider` + `Intent.ACTION_SEND` |
| Serialization | Gson 2.10.1 (Room type converters) |
| Lifecycle | AndroidX Lifecycle 2.6.2 |

---

## 📁 Project Structure

```
app/src/main/
├── java/com/dentacore/
│   ├── activities/
│   │   ├── SplashActivity.java
│   │   ├── MainActivity.java          # Patient list + stats
│   │   ├── AddPatientActivity.java    # Add / edit patient
│   │   ├── PatientDetailActivity.java # Profile, voice notes, history
│   │   ├── PrescriptionActivity.java  # Rx builder + billing + PDF
│   │   └── AnalyticsActivity.java     # Revenue charts
│   ├── adapters/
│   │   ├── PatientAdapter.java
│   │   └── PrescriptionAdapter.java
│   ├── database/
│   │   ├── AppDatabase.java           # Room singleton
│   │   ├── PatientDao.java
│   │   ├── PrescriptionDao.java
│   │   ├── Converters.java            # Gson type converters
│   │   └── ProcedureCount.java        # Query result POJO
│   ├── models/
│   │   ├── Patient.java
│   │   ├── Prescription.java
│   │   └── Medication.java
│   └── utils/
│       └── PdfGenerator.java          # Native PDF generation
├── res/
│   ├── layout/                        # All XML layouts
│   ├── drawable/                      # Shapes, buttons, backgrounds
│   ├── values/                        # colors.xml, themes.xml, strings.xml
│   ├── mipmap-*/                      # Launcher icons
│   └── xml/file_paths.xml             # FileProvider paths
└── AndroidManifest.xml
```

---

## 🚀 Getting Started

### Prerequisites

- Android Studio **Hedgehog (2023.1.1)** or newer
- JDK 8+
- Android SDK 26+

### Setup

```bash
# 1. Clone the repository
git clone https://github.com/YOUR_USERNAME/DentaCore.git

# 2. Open in Android Studio
#    File → Open → select the DentaCore/ folder

# 3. Let Gradle sync (downloads ~30MB of dependencies)

# 4. Run on a device or emulator (API 26+)
```

> **No API keys or internet connection required.** The app is fully self-contained.

---

## 📦 Dependencies

```groovy
// app/build.gradle
implementation 'androidx.appcompat:appcompat:1.6.1'
implementation 'com.google.android.material:material:1.9.0'
implementation 'androidx.recyclerview:recyclerview:1.3.2'
implementation 'androidx.room:room-runtime:2.5.2'
annotationProcessor 'androidx.room:room-compiler:2.5.2'
implementation 'androidx.lifecycle:lifecycle-viewmodel:2.6.2'
implementation 'androidx.lifecycle:lifecycle-livedata:2.6.2'
implementation 'com.github.PhilJay:MPAndroidChart:v3.1.0'  // via JitPack
implementation 'com.google.code.gson:gson:2.10.1'
```

JitPack is required for MPAndroidChart — it's already configured in `settings.gradle`.

---

## 📋 Permissions

```xml
<uses-permission android:name="android.permission.RECORD_AUDIO" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"
    android:maxSdkVersion="28"/>
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"
    android:maxSdkVersion="32"/>
```

`RECORD_AUDIO` is requested at runtime when the user taps "Record Voice Note".

---

## 🗃 Database Schema

### `patients` table
| Column | Type | Description |
|---|---|---|
| `id` | INTEGER PK | Auto-generated |
| `name`, `phone`, `email` | TEXT | Contact info |
| `dob`, `gender`, `bloodGroup` | TEXT | Medical info |
| `allergies`, `medicalHistory` | TEXT | Clinical notes |
| `affectedTeeth` | TEXT (JSON) | List of FDI tooth numbers |
| `totalBilled`, `totalPaid` | REAL | Aggregate billing |
| `voiceNoteFilePath` | TEXT | Path to `.3gp` recording |
| `createdAt`, `updatedAt` | INTEGER | Unix timestamps |

### `prescriptions` table
| Column | Type | Description |
|---|---|---|
| `id` | INTEGER PK | Auto-generated |
| `patientId` | INTEGER FK | Links to patient |
| `diagnosis`, `notes` | TEXT | Clinical details |
| `medications` | TEXT (JSON) | List of `Medication` objects |
| `treatedTeeth` | TEXT (JSON) | FDI tooth numbers for this visit |
| `billAmount`, `paidAmount` | REAL | Visit billing |
| `paymentMode` | TEXT | Cash / UPI / Card / Insurance |
| `procedureType` | TEXT | Used for analytics grouping |
| `createdAt` | INTEGER | Unix timestamp |

---

## 📄 PDF Prescription

Prescriptions are exported as PDF files using Android's built-in `android.graphics.pdf.PdfDocument` — no external library required. The generated PDF includes:

- Clinic name and doctor name header
- Patient info (name, phone, blood group, allergies)
- Diagnosis and treated teeth
- Medication table (name, dosage, frequency, duration, instructions)
- Notes / follow-up instructions
- Billing summary (total, paid, balance, payment mode)

PDFs are saved to `getExternalFilesDir()` and shared via Android's standard share sheet (WhatsApp, Gmail, Drive, etc.).

---

## 📊 Analytics

The analytics screen queries the Room database directly and renders:

- **Summary cards** — total patients, total collected, total billed, outstanding balance
- **Bar chart** — revenue per month for the last 6 months (MPAndroidChart)
- **Pie chart** — visit count grouped by `procedureType` (Extraction, Filling, Root Canal, etc.)

---

## 🔧 Known Issues & Fixes

| Issue | Fix Applied |
|---|---|
| `NoClassDefFoundError: ReportFragment$ActivityInitializationListener` | Pinned all lifecycle deps to `2.6.2` via `resolutionStrategy { force ... }` |
| App crash on launch (missing `@mipmap/ic_launcher`) | Added PNG icons for all mipmap densities + adaptive icon XML |
| `DarkDialog` style crash | Fixed parent to `ThemeOverlay.MaterialComponents.Dialog` |

---

## 🤝 Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you'd like to change.

1. Fork the repo
2. Create your branch: `git checkout -b feature/your-feature`
3. Commit: `git commit -m "Add your feature"`
4. Push: `git push origin feature/your-feature`
5. Open a Pull Request

---



