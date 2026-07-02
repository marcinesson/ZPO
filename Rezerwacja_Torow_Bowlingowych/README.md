# Rezerwacja_Torow_Bowlingowych

Projekt zaliczeniowy Spring Boot + Vaadin.

- UI klienta: `/lanes`
- Panel roli wyzszej: `/manager/bookings`
- Swagger: `/swagger-ui.html`
- REST POST: `/api/lanes`
- Baza PostgreSQL: `rezerwacja_torow_bowlingowych`
- Loginy: `client/client`, `manager/manager`

## Opis algorytmu

Algorytm w tym projekcie waliduje dane wejsciowe, sprawdza dostepnosc zasobu oraz kolizje czasowe, a nastepnie wybiera pasujacy zasob lub tworzy rezerwacje/zapis. Dodatkowo wylicza koszt na podstawie czasu i ceny zasobu. Test jednostkowy sprawdza najwazniejszy przypadek algorytmu, czyli czy system wybiera poprawny zasob albo poprawnie liczy wynik.
