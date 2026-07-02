# System zapisow na zajecia fitness

## Wymagania

System zapisow na zajecia fitness: baza danych - zajecia, trenerzy, limity miejsc oraz zapisy uzytkownikow.

UI: widok dostepnych zajec oraz opcja zapisania sie.

Algorytm: sprawdza, czy na zajeciach sa wolne miejsca i blokuje zapis po przekroczeniu limitu.

Endpoint Swagger: endpoint typu POST do zapisu na zajecia oraz endpoint GET do pobrania listy uczestnikow zajec.

Security: klient widzi swoje zapisy, trener widzi uczestnikow swoich zajec, admin widzi wszystko.

Test jednostkowy: czy algorytm poprawnie blokuje zapis, gdy limit miejsc zostal osiagniety.

## Glowne encje

- AppUser
- Trainer
- FitnessClass
- ClassEnrollment

## Przykladowe role

- CLIENT
- TRAINER
- ADMIN

## Opis algorytmu

Algorytm w tym projekcie waliduje dane wejsciowe, sprawdza dostepnosc zasobu oraz kolizje czasowe, a nastepnie wybiera pasujacy zasob lub tworzy rezerwacje/zapis. Dodatkowo wylicza koszt na podstawie czasu i ceny zasobu. Test jednostkowy sprawdza najwazniejszy przypadek algorytmu, czyli czy system wybiera poprawny zasob albo poprawnie liczy wynik.
