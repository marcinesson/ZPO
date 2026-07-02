# Rezerwacja stolikow w restauracji

## Wymagania

Rezerwacja stolikow w restauracji: baza danych - stoliki, liczba miejsc, rezerwacje oraz daty i godziny.

UI: widok dostepnych stolikow oraz formularz rezerwacji.

Algorytm: znajduje najmniejszy wolny stolik, ktory pomiesci dana liczbe osob, oraz sprawdza kolizje godzin.

Endpoint Swagger: endpoint typu POST do utworzenia rezerwacji stolika oraz endpoint GET do pobrania grafiku stolika.

Security: klient widzi swoje rezerwacje, admin widzi wszystkie i moze dodawac stoliki.

Test jednostkowy: czy algorytm poprawnie dobiera najmniejszy pasujacy stolik.

## Glowne encje

- AppUser
- RestaurantTable
- TableReservation

## Przykladowe role

- CLIENT
- ADMIN

## Opis algorytmu

Algorytm w tym projekcie waliduje dane wejsciowe, sprawdza dostepnosc zasobu oraz kolizje czasowe, a nastepnie wybiera pasujacy zasob lub tworzy rezerwacje/zapis. Dodatkowo wylicza koszt na podstawie czasu i ceny zasobu. Test jednostkowy sprawdza najwazniejszy przypadek algorytmu, czyli czy system wybiera poprawny zasob albo poprawnie liczy wynik.
