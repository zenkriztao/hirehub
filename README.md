# HireHub - Aplikasi Pencari Kerja 🧑‍💻💼

**HireHub** adalah aplikasi pencari kerja yang dibangun menggunakan **Kotlin** untuk platform **Android**. Aplikasi ini dirancang untuk memudahkan para pencari kerja dalam mencari peluang kerja, melamar pekerjaan, serta memantau status lamaran mereka dengan antarmuka yang intuitif dan mudah digunakan.

Dengan fitur integrasi API untuk mengambil lowongan pekerjaan secara real-time, **HireHub** menawarkan pengalaman pencarian pekerjaan yang efisien dan menyenangkan. Dibangun menggunakan teknologi terkini seperti **Jetpack Compose**, aplikasi ini memberikan pengalaman pengguna yang responsif dan modern.

## 🚀 Fitur Utama

- 🔍 **Pencarian Pekerjaan**: Cari pekerjaan berdasarkan kategori, lokasi, dan perusahaan dengan filter yang mudah digunakan.
- 💼 **Melamar Pekerjaan**: Lamaran dapat diajukan langsung melalui aplikasi dengan integrasi ke sistem aplikasi pekerjaan.
- 📊 **Pantau Lamaran**: Memantau status lamaran pekerjaan yang telah diajukan, dengan notifikasi tentang setiap pembaruan.
- 🌍 **Dukungan Lokasi**: Menampilkan lowongan pekerjaan yang relevan berdasarkan lokasi pengguna.
- 📱 **UI Responsif**: Desain UI yang responsif dan ramah pengguna menggunakan **Jetpack Compose**.
- 🏷️ **Notifikasi**: Pemberitahuan tentang pekerjaan baru yang sesuai dengan kriteria pencarian pengguna.

## ⚙️ Teknologi yang Digunakan

- **Kotlin** 🦸‍♂️: Bahasa pemrograman utama yang digunakan untuk membangun aplikasi.
- **Jetpack Compose** 📲: Toolkit UI modern untuk Android yang memungkinkan pembuatan antarmuka deklaratif.
- **Retrofit** 🔌: Digunakan untuk melakukan panggilan API dan mendapatkan data lowongan pekerjaan.
- **Room Database** 💾: Untuk menyimpan data secara lokal, termasuk lowongan pekerjaan yang sudah dilihat atau dilamar.
- **Firebase** 🔥: Untuk autentikasi pengguna dan penyimpanan data yang aman.
- **Glide** 🖼️: Untuk memuat dan menampilkan gambar perusahaan atau lowongan pekerjaan.

## 🛠️ Persyaratan Sistem

Sebelum menjalankan aplikasi, pastikan perangkat Anda memiliki hal-hal berikut:

- Android Studio (versi terbaru)
- JDK 8 atau yang lebih tinggi
- Kotlin 1.5 atau lebih tinggi

## 📖 Instalasi

### Langkah-langkah Instalasi

1. **Clone repositori**:
   ```bash
   git clone https://github.com/zenkriztao/hirehub.git
````

2. **Buka proyek di Android Studio**:

   * Pilih **Open an existing project** dan buka folder yang telah Anda clone.

3. **Sync Gradle**:

   * Setelah membuka proyek, Android Studio akan menanyakan apakah Anda ingin melakukan **Sync Gradle**. Pilih "Sync Now".

4. **Konfigurasi Firebase**:

   * Ikuti [petunjuk konfigurasi Firebase](https://firebase.google.com/docs/android/setup) untuk menambahkan Firebase ke aplikasi.
   * Salin **google-services.json** ke dalam folder **app** proyek Anda.

5. **Menjalankan Aplikasi**:

   * Klik tombol "Run" di Android Studio untuk menjalankan aplikasi di emulator atau perangkat fisik.

### Konfigurasi API

Jika Anda ingin menggunakan API Anda sendiri untuk mendapatkan lowongan pekerjaan, pastikan untuk mengikuti langkah-langkah berikut:

1. Dapatkan **API Key** dari penyedia API lowongan pekerjaan.
2. Masukkan **API Key** tersebut di `strings.xml`:

   ```xml
   <string name="api_key">YOUR_API_KEY_HERE</string>
   ```

## 🧑‍💻 Cara Berkontribusi

Kami menyambut baik kontribusi dari Anda! Jika Anda ingin berkontribusi pada proyek ini, ikuti langkah-langkah berikut:

1. Fork repositori ini.
2. Buat cabang untuk fitur baru (`git checkout -b feature/your-feature`).
3. Lakukan perubahan yang diinginkan dan commit perubahan (`git commit -am 'Add your feature'`).
4. Push cabang Anda (`git push origin feature/your-feature`).
5. Buat pull request untuk review.

Harap pastikan bahwa kode Anda mengikuti standar **Kotlin** dan **Jetpack Compose** agar dapat diterima dengan mudah.

## 📝 Lisensi

Proyek ini dilisensikan di bawah **MIT License** - lihat file [LICENSE](LICENSE) untuk detail lebih lanjut.

## 📑 Acknowledgements

* **Firebase**: Untuk autentikasi pengguna dan penyimpanan data.
* **Jetpack Compose**: Toolkit UI untuk membangun aplikasi Android modern.
* **Kotlin**: Bahasa pemrograman yang digunakan untuk membangun aplikasi.
* **Retrofit**: Digunakan untuk melakukan panggilan API.
* **Glide**: Digunakan untuk memuat gambar perusahaan atau lowongan pekerjaan.

---

🧑‍💻 **Dibangun oleh** [Zenkriztao](https://github.com/zenkriztao) - Penggemar Kotlin yang bersemangat untuk membuat aplikasi Android yang bermanfaat dan efisien.

