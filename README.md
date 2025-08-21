# Vehicle Database – projekt Java

Aplikacja umożliwia zarządzanie bazą pojazdów w relacyjnej bazie danych **MySQL**. System napisany jest w **Java**. Interfejs pozwala przeglądać, filtrować, dodawać i edytować pojazdy oraz ich szczegóły.

---

## Zawartość projektu

- **Kod źródłowy** – katalog `src`
- **Dokumentacja** – pliki: `Dokumentacja.docx`, `Dokumentacja.pdf`
- **Diagramy** – zobacz poniżej
- **Baza danych** – plik `baza_pojazdow.sql`
- **Pliki graficzne** – podgląd poniżej

---

## Podgląd wybranych ekranów i diagramów

### Diagram systemu
![Diagram](images/Diagram.png)

### Uproszczony diagram
![Uproszczony diagram](images/UproszczonyDiagram.png)

### Diagram Gantta
![Diagram Gantta](images/DiagramGantta.png)

### Historia commitów
![Historia commitów](images/HistoriaCommitow.png)

### Ekran wczytywania
![Ekran wczytywania](images/EkranWczytywania.png)

### Ekran ładowania
![Ekran ładowania](images/EkranLadowania.png)

### Ekran główny aplikacji
![Główne okno](images/GlowneOkno.png)

### Formularz dodawania pojazdu
![Dodawanie pojazdu](images/Dodawanie.png)

### Formularz importu danych
![Importowanie](images/Importowanie.png)

---

## Pliki projektu

- **`src/`** – kod źródłowy aplikacji
- **`baza_pojazdow.sql`** – definicja i przykładowe dane bazy
- **`Dokumentacja.docx` oraz `Dokumentacja.pdf`** – instrukcja, opis, diagramy
- **`projekt.iml`** – plik konfiguracyjny projektu (IntelliJ)
- **Pozostałe pliki .png** – zrzuty ekranu i diagramy

---

## Instrukcja uruchomienia (XAMPP/MySQL)

1. **Uruchom XAMPP i wystartuj serwer MySQL**  
   (Panel XAMPP → Start przy MySQL)

2. **Stwórz bazę danych w phpMyAdmin**  
   - Wejdź na [localhost/phpmyadmin](http://localhost/phpmyadmin)
   - Utwórz nową bazę o nazwie np. `baza_pojazdow`
   - Zaimportuj plik `baza_pojazdow.sql` przez zakładkę "Import"

3. **Skonfiguruj połączenie z bazą**  
   - Ustaw dane dostępowe w pliku konfiguracyjnym aplikacji (np. w kodzie lub pliku `.properties`):  
     ```
     jdbc:mysql://localhost:3306/baza_pojazdow
     user: root
     password: [domyślnie puste lub własne hasło]
     ```
   - Upewnij się, że sterownik JDBC dla MySQL jest dodany do projektu (np. w `lib` lub przez Gradle/Maven)

4. **Zbuduj projekt**  
   Otwórz projekt w IntelliJ IDEA lub innym IDE obsługującym Java/Gradle/Maven.

5. **Uruchom aplikację**  
   W IDE lub przez konsolę.

---

## Główne funkcje

- Przeglądanie, filtrowanie i wyszukiwanie pojazdów
- Dodawanie, edycja, usuwanie pojazdów i szczegółów
- Eksport/import danych (np. do/z pliku)
- Walidacja danych (liczba drzwi, pojemność silnika itp.)
- Obsługa błędów połączenia z bazą

---

## Przykładowa struktura bazy

- Tabela **User** – użytkownicy
- Tabela **Pojazd** – pojazdy
- Tabela **Osobowy** – szczegóły pojazdów osobowych
- Tabela **Motor** – szczegóły motocykli

---

## Autor

- kacperhalaj

---

> Szczegóły implementacji, diagramy oraz instrukcje znajdziesz w pliku `Dokumentacja.pdf` lub `Dokumentacja.docx`.  
> Dokumentacja graficzna – patrz zrzuty ekranu powyżej.
