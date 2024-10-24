-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 02 Cze 2024, 20:11
-- Wersja serwera: 10.4.25-MariaDB
-- Wersja PHP: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `baza_pojazdow`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `motory`
--

CREATE TABLE `motory` (
  `idPojazdu` int(11) DEFAULT NULL,
  `pojemnoscSilnika` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `motory`
--

INSERT INTO `motory` (`idPojazdu`, `pojemnoscSilnika`) VALUES
(2, 1000);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `osobowe`
--

CREATE TABLE `osobowe` (
  `idPojazdu` int(11) DEFAULT NULL,
  `liczbaDrzwi` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `osobowe`
--

INSERT INTO `osobowe` (`idPojazdu`, `liczbaDrzwi`) VALUES
(1, 3),
(3, 5);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `pojazdy`
--

CREATE TABLE `pojazdy` (
  `idPojazdu` int(11) NOT NULL,
  `marka` varchar(50) DEFAULT NULL,
  `model` varchar(50) DEFAULT NULL,
  `rokProdukcji` int(11) DEFAULT NULL,
  `typ` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `pojazdy`
--

INSERT INTO `pojazdy` (`idPojazdu`, `marka`, `model`, `rokProdukcji`, `typ`) VALUES
(1, 'Opel', 'Corsa', 2000, 'Osobowy'),
(2, 'Yamaha', 'R1', 2015, 'Motor'),
(3, 'Audi', 'A6', 2011, 'Osobowy');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `login` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `users`
--

INSERT INTO `users` (`id`, `login`, `password`) VALUES
(1, 'admin', 'admin123');

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `motory`
--
ALTER TABLE `motory`
  ADD KEY `idPojazdu` (`idPojazdu`);

--
-- Indeksy dla tabeli `osobowe`
--
ALTER TABLE `osobowe`
  ADD KEY `idPojazdu` (`idPojazdu`);

--
-- Indeksy dla tabeli `pojazdy`
--
ALTER TABLE `pojazdy`
  ADD PRIMARY KEY (`idPojazdu`);

--
-- Indeksy dla tabeli `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT dla zrzuconych tabel
--

--
-- AUTO_INCREMENT dla tabeli `pojazdy`
--
ALTER TABLE `pojazdy`
  MODIFY `idPojazdu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT dla tabeli `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `motory`
--
ALTER TABLE `motory`
  ADD CONSTRAINT `motory_ibfk_1` FOREIGN KEY (`idPojazdu`) REFERENCES `pojazdy` (`idPojazdu`);

--
-- Ograniczenia dla tabeli `osobowe`
--
ALTER TABLE `osobowe`
  ADD CONSTRAINT `osobowe_ibfk_1` FOREIGN KEY (`idPojazdu`) REFERENCES `pojazdy` (`idPojazdu`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
