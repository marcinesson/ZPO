# Rezerwacja_Studio_Nagraniowego

Projekt zaliczeniowy Spring Boot + Vaadin.

- UI klienta: `/studios`
- Panel roli wyzszej: `/engineer/sessions`
- Swagger: `/swagger-ui.html`
- REST POST: `/api/studios`
- Baza PostgreSQL: `rezerwacja_studio_nagraniowego`
- Loginy: `client/client`, `engineer/engineer`

## Opis algorytmu

Algorytm w tym projekcie waliduje dane wejsciowe, sprawdza dostepnosc zasobu oraz kolizje czasowe, a nastepnie wybiera pasujacy zasob lub tworzy rezerwacje/zapis. Dodatkowo wylicza koszt na podstawie czasu i ceny zasobu. Test jednostkowy sprawdza najwazniejszy przypadek algorytmu, czyli czy system wybiera poprawny zasob albo poprawnie liczy wynik.
