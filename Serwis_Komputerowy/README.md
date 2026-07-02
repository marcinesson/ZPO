# System serwisu komputerowego

## Wymagania

System serwisu komputerowego: baza danych - zgloszenia, urzadzenia, technicy oraz status naprawy.

UI: formularz dodania zgloszenia oraz widok statusu naprawy.

Algorytm: przypisuje zgloszenie do technika z najmniejsza liczba aktywnych napraw.

Endpoint Swagger: endpoint typu POST do dodania zgloszenia oraz endpoint GET do pobrania zgloszen danego technika.

Security: klient widzi swoje zgloszenia, technik widzi przypisane naprawy, admin widzi wszystko.

Test jednostkowy: czy algorytm poprawnie wybiera najmniej obciazonego technika.

## Glowne encje

- AppUser
- Device
- RepairTicket
- RepairStatus

## Przykladowe role

- CLIENT
- TECHNICIAN
- ADMIN

## Opis algorytmu

Algorytm w tym projekcie waliduje dane wejsciowe, sprawdza dostepnosc zasobu oraz kolizje czasowe, a nastepnie wybiera pasujacy zasob lub tworzy rezerwacje/zapis. Dodatkowo wylicza koszt na podstawie czasu i ceny zasobu. Test jednostkowy sprawdza najwazniejszy przypadek algorytmu, czyli czy system wybiera poprawny zasob albo poprawnie liczy wynik.
