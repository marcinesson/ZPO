# Rezerwacja pokoi hotelowych

## Wymagania

Rezerwacja pokoi hotelowych: baza danych - pokoje, ceny, liczba osob, rezerwacje oraz daty pobytu.

UI: lista dostepnych pokoi oraz formularz rezerwacji.

Algorytm: sprawdza dostepnosc pokoju w terminie oraz liczy koszt pobytu, np. ze znizka od 7 dni.

Endpoint Swagger: endpoint typu POST do zlozenia rezerwacji oraz endpoint GET do pobrania dostepnych pokoi w terminie.

Security: klient widzi swoje rezerwacje, admin widzi wszystkie i moze dodawac pokoje.

Test jednostkowy: czy algorytm dobrze sprawdza dostepnosc i liczy cene.

## Glowne encje

- AppUser
- HotelRoom
- RoomBooking

## Przykladowe role

- CLIENT
- ADMIN

## Opis algorytmu

Algorytm w tym projekcie waliduje dane wejsciowe, sprawdza dostepnosc zasobu oraz kolizje czasowe, a nastepnie wybiera pasujacy zasob lub tworzy rezerwacje/zapis. Dodatkowo wylicza koszt na podstawie czasu i ceny zasobu. Test jednostkowy sprawdza najwazniejszy przypadek algorytmu, czyli czy system wybiera poprawny zasob albo poprawnie liczy wynik.
