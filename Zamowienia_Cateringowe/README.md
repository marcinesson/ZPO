# Zamowienia_Cateringowe

Projekt zaliczeniowy Spring Boot + Vaadin.

- UI klienta: `/catering`
- Panel roli wyzszej: `/kitchen/orders`
- Swagger: `/swagger-ui.html`
- REST POST: `/api/catering`
- Baza PostgreSQL: `zamowienia_cateringowe`
- Loginy: `client/client`, `kitchen/kitchen`

## Opis algorytmu

Algorytm wybiera najlepszy zasob dla nowego wpisu, ale nie bierze po prostu pierwszego wolnego rekordu z bazy.

Sprawdza:

1. Czy dane sa poprawne: kategoria, pojemnosc, minimalna jakosc, liczba jednostek pracy, priorytet i zakres dat.
2. Czy zasob pasuje do kategorii.
3. Czy zasob ma wystarczajaca pojemnosc i jakosc.
4. Czy zasob nie ma kolizji czasowej z istniejaca rezerwacja/zapisem.
5. Ile godzin potrzeba na wykonanie zadania: `ceil(taskUnits / unitsPerHour)`.
6. Czy wybrany przedzial czasu jest wystarczajaco dlugi.
7. Dla kazdego pasujacego zasobu liczy wynik punktowy: cena + kara za nadmiar pojemnosci + kara za nadmiar jakosci + korekta priorytetu.

Wygrywa zasob z najmniejszym wynikiem punktowym. Dzieki temu algorytm potrafi wybrac tanszy i lepiej dopasowany zasob, a nie tylko najwiekszy albo pierwszy znaleziony.
