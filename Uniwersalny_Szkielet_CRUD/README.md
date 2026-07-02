# Uniwersalny szkielet CRUD

## Wymagania

Nazwa projektu: [nazwa systemu]

Baza danych: encje [glowna encja], [encja pomocnicza], [rezerwacja/zamowienie/zapis/zgloszenie].

UI: widok listy dostepnych elementow oraz formularz dodania albo rezerwacji.

Algorytm: sprawdza warunek biznesowy, np. dostepnosc, konflikt terminow, limit miejsc, wybor najlepszego elementu albo obliczenie ceny.

Endpoint Swagger: jeden endpoint POST do wykonania glownej akcji oraz jeden endpoint GET do pobrania danych pomocniczych.

Security: klient widzi tylko swoje dane, admin widzi wszystko i moze dodawac nowe elementy.

Test jednostkowy: sprawdza, czy algorytm dziala poprawnie dla poprawnego przypadku oraz czy rzuca wyjatek przy blednych danych.

## Standardowa kolejnosc robienia projektu

1. Modele
2. Repozytoria
3. Security
4. Data.sql
5. Serwisy
6. Algorytm
7. Test algorytmu
8. Kontroler REST / Swagger
9. UI Vaadin
10. Test reczny

## Standardowe pakiety

- config
- controller
- dto
- model
- repository
- service
- views

## Przykladowe role

- CLIENT
- ADMIN

## Opis algorytmu

Algorytm w tym projekcie waliduje dane wejsciowe, sprawdza dostepnosc zasobu oraz kolizje czasowe, a nastepnie wybiera pasujacy zasob lub tworzy rezerwacje/zapis. Dodatkowo wylicza koszt na podstawie czasu i ceny zasobu. Test jednostkowy sprawdza najwazniejszy przypadek algorytmu, czyli czy system wybiera poprawny zasob albo poprawnie liczy wynik.
