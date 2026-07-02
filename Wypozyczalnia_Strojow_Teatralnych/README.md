# Wypozyczalnia_Strojow_Teatralnych

Projekt zaliczeniowy Spring Boot + Vaadin.

- UI klienta: `/costumes`
- Panel roli wyzszej: `/wardrobe/rentals`
- Swagger: `/swagger-ui.html`
- REST POST: `/api/costumes`
- Baza PostgreSQL: `wypozyczalnia_strojow_teatralnych`
- Loginy: `client/client`, `wardrobe/wardrobe`

## Opis algorytmu

Algorytm w tym projekcie waliduje dane wejsciowe, sprawdza dostepnosc zasobu oraz kolizje czasowe, a nastepnie wybiera pasujacy zasob lub tworzy rezerwacje/zapis. Dodatkowo wylicza koszt na podstawie czasu i ceny zasobu. Test jednostkowy sprawdza najwazniejszy przypadek algorytmu, czyli czy system wybiera poprawny zasob albo poprawnie liczy wynik.
