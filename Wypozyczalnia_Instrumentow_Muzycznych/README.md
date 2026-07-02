# Wypozyczalnia_Instrumentow_Muzycznych

Projekt zaliczeniowy Spring Boot + Vaadin.

- UI klienta: `/instruments`
- Panel roli wyzszej: `/curator/rentals`
- Swagger: `/swagger-ui.html`
- REST POST: `/api/instruments`
- Baza PostgreSQL: `wypozyczalnia_instrumentow_muzycznych`
- Loginy: `client/client`, `curator/curator`

## Opis algorytmu

Algorytm w tym projekcie waliduje dane wejsciowe, sprawdza dostepnosc zasobu oraz kolizje czasowe, a nastepnie wybiera pasujacy zasob lub tworzy rezerwacje/zapis. Dodatkowo wylicza koszt na podstawie czasu i ceny zasobu. Test jednostkowy sprawdza najwazniejszy przypadek algorytmu, czyli czy system wybiera poprawny zasob albo poprawnie liczy wynik.
