# Biblioteka

## Wymagania

Biblioteka: baza danych - ksiazki, autorzy, egzemplarze oraz wypozyczenia.

UI: lista dostepnych ksiazek oraz opcja wypozyczenia wybranego egzemplarza.

Algorytm: sprawdza, czy egzemplarz ksiazki jest dostepny oraz oblicza kare za przetrzymanie ksiazki po terminie.

Endpoint Swagger: endpoint typu POST do wypozyczenia ksiazki oraz endpoint GET do sprawdzenia dostepnych ksiazek.

Security: bibliotekarz widzi wszystkie wypozyczenia i moze dodawac ksiazki, klient widzi tylko swoje wypozyczenia.

Test jednostkowy: czy algorytm poprawnie liczy kare za spozniony zwrot.

## Glowne encje

- AppUser
- Author
- Book
- BookCopy
- Loan

## Przykladowe role

- CLIENT
- LIBRARIAN

## Opis algorytmu

Algorytm w tym projekcie waliduje dane wejsciowe, sprawdza dostepnosc zasobu oraz kolizje czasowe, a nastepnie wybiera pasujacy zasob lub tworzy rezerwacje/zapis. Dodatkowo wylicza koszt na podstawie czasu i ceny zasobu. Test jednostkowy sprawdza najwazniejszy przypadek algorytmu, czyli czy system wybiera poprawny zasob albo poprawnie liczy wynik.
