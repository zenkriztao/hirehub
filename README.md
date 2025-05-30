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

Tentu, berikut adalah versi perbaikan untuk file `README.md` Anda dengan penyesuaian tata bahasa dan format agar lebih jelas dan rapi:

---

# HireHub - Aplikasi Pencarian Lowongan Kerja

Aplikasi untuk mencari lowongan pekerjaan dengan menggunakan API pekerjaan dan Firebase untuk autentikasi pengguna.

## Langkah-langkah Instalasi

### 1. Clone Repositori

Untuk memulai, clone repositori ini ke dalam direktori lokal Anda:

```bash
git clone https://github.com/zenkriztao/hirehub.git
```

### 2. Buka Proyek di Android Studio

1. Buka **Android Studio**.
2. Pilih **Open an existing project**.
3. Pilih folder tempat Anda meng-clone repositori ini.

### 3. Sync Gradle

Setelah membuka proyek, Android Studio akan menanyakan apakah Anda ingin melakukan **Sync Gradle**. Pilih "Sync Now" untuk memastikan semua dependensi diunduh dengan benar.

### 4. Konfigurasi Firebase

1. Ikuti petunjuk di [Firebase Setup for Android](https://firebase.google.com/docs/android/setup) untuk menambahkan Firebase ke aplikasi.
2. Unduh **google-services.json** dan salin file tersebut ke dalam folder **app** di proyek Anda.

### 5. Menjalankan Aplikasi

Klik tombol "Run" di Android Studio untuk menjalankan aplikasi di emulator atau perangkat fisik.

---

## Konfigurasi API

Jika Anda ingin menggunakan API Anda sendiri untuk mendapatkan lowongan pekerjaan, pastikan untuk mengikuti langkah-langkah berikut:

1. Dapatkan **API Key** dari penyedia API lowongan pekerjaan.
2. Masukkan **API Key** Anda di file `strings.xml`:

   ```xml
   <string name="api_key">YOUR_API_KEY_HERE</string>
   ```

---

## 🧑‍💻 Cara Berkontribusi

Kami menyambut baik kontribusi dari Anda! Jika Anda ingin berkontribusi pada proyek ini, ikuti langkah-langkah berikut:

1. Fork repositori ini.
2. Buat cabang baru untuk fitur yang ingin Anda tambahkan (`git checkout -b feature/fitur-anda`).
3. Lakukan perubahan yang diinginkan dan commit perubahan (`git commit -am 'Add fitur-anda'`).
4. Push cabang Anda ke repositori Anda (`git push origin feature/fitur-anda`).
5. Buat pull request untuk review.

Harap pastikan kode Anda mengikuti standar **Kotlin** dan **Jetpack Compose** agar dapat diterima dengan mudah.

---

## 📝 Lisensi

Proyek ini dilisensikan di bawah **MIT License**. Lihat file [LICENSE](LICENSE) untuk detail lebih lanjut.

---

## 📑 Acknowledgements

* **Firebase**: Digunakan untuk autentikasi pengguna dan penyimpanan data.
* **Jetpack Compose**: Toolkit UI untuk membangun aplikasi Android modern.
* **Kotlin**: Bahasa pemrograman yang digunakan untuk membangun aplikasi.
* **Retrofit**: Digunakan untuk melakukan panggilan API.
* **Glide**: Digunakan untuk memuat gambar perusahaan atau lowongan pekerjaan.

---

🧑‍💻 **Dibangun oleh** [Zenkriztao](https://github.com/zenkriztao) - Penggemar Kotlin yang bersemangat untuk membuat aplikasi Android yang bermanfaat dan efisien.

