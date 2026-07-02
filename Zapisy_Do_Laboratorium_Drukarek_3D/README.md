# Zapisy_Do_Laboratorium_Drukarek_3D

Projekt zaliczeniowy Spring Boot + Vaadin.

- UI klienta: `/prints`
- Panel roli wyzszej: `/technician/prints`
- Swagger: `/swagger-ui.html`
- REST POST: `/api/prints`
- Baza PostgreSQL: `zapisy_do_laboratorium_drukarek_3d`
- Loginy: `client/client`, `technician/technician`

## Opis algorytmu

Algorytm w tym projekcie waliduje dane wejsciowe, sprawdza dostepnosc zasobu oraz kolizje czasowe, a nastepnie wybiera pasujacy zasob lub tworzy rezerwacje/zapis. Dodatkowo wylicza koszt na podstawie czasu i ceny zasobu. Test jednostkowy sprawdza najwazniejszy przypadek algorytmu, czyli czy system wybiera poprawny zasob albo poprawnie liczy wynik.
