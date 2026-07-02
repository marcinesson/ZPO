# Rezerwacja sal konferencyjnych

## Wymagania

Rezerwacja sal konferencyjnych: baza danych - sale, pojemnosc, cena za godzine oraz daty rezerwacji.

UI: widok dostepnych sal oraz formularz rezerwacji sali na konkretny termin.

Algorytm: sprawdza, czy sala jest wolna w podanym przedziale czasu oraz oblicza koszt wynajmu na podstawie liczby godzin.

Endpoint Swagger: endpoint typu POST do utworzenia rezerwacji sali oraz endpoint GET pobierajacy grafik wybranej sali.

Security: admin widzi wszystkie rezerwacje i moze dodawac sale, klient widzi tylko swoje rezerwacje.

Test jednostkowy: czy algorytm poprawnie wykrywa konflikt terminow i dobrze liczy cene.

## Glowne encje

- AppUser
- ConferenceRoom
- RoomReservation

## Przykladowe role

- CLIENT
- ADMIN

## Opis algorytmu

Algorytm w tym projekcie waliduje dane wejsciowe, sprawdza dostepnosc zasobu oraz kolizje czasowe, a nastepnie wybiera pasujacy zasob lub tworzy rezerwacje/zapis. Dodatkowo wylicza koszt na podstawie czasu i ceny zasobu. Test jednostkowy sprawdza najwazniejszy przypadek algorytmu, czyli czy system wybiera poprawny zasob albo poprawnie liczy wynik.
