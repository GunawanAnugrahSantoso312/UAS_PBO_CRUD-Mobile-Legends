-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 02 Jul 2025 pada 00.49
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ml_hero_db`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `tm_hero`
--

CREATE TABLE `tm_hero` (
  `id_hero` int(11) NOT NULL COMMENT 'ID unik tiap hero',
  `nama_hero` varchar(100) NOT NULL COMMENT 'Nama lengkap hero',
  `kategori` enum('MAGE','ASSASIN','FIGHTER','TANK','MARKSMAN','SUPPORT') NOT NULL COMMENT 'Tipe role hero',
  `gender` enum('MALE','FEMALE') NOT NULL COMMENT 'Jenis kelamin hero'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `tm_hero`
--
ALTER TABLE `tm_hero`
  ADD PRIMARY KEY (`id_hero`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
