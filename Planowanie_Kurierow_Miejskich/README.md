# Planowanie_Kurierow_Miejskich

Projekt zaliczeniowy Spring Boot + Vaadin.

- UI klienta: `/deliveries`
- Panel roli wyzszej: `/dispatcher/deliveries`
- Swagger: `/swagger-ui.html`
- REST POST: `/api/deliveries`
- Baza PostgreSQL: `planowanie_kurierow_miejskich`
- Loginy: `client/client`, `dispatcher/dispatcher`


## Opis algorytmu

Algorytm wybiera najlepszy zasob dla nowego wpisu. Sprawdza kolejno:

1. Czy podano poprawne dane: kategorie, wymaganie pojemnosci, minimalna jakosc, liczbe jednostek pracy, priorytet oraz zakres dat.
2. Czy zasob jest dostepny, ma odpowiednia kategorie, wystarczajaca pojemnosc oraz minimalna jakosc.
3. Czy zasob nie ma kolizji czasowej z istniejacym wpisem w tym samym przedziale.
4. Ile godzin potrzeba na wykonanie zadania: `ceil(taskUnits / unitsPerHour)`.
5. Czy wybrany przedzial czasu jest wystarczajaco dlugi na wykonanie zadania.
6. Dla kazdego pasujacego zasobu liczy wynik punktowy:
   `cena za wymagane godziny + kara za nadmiar pojemnosci + kara za nadmiar jakosci + kara/bonus za priorytet`.

Wygrywa zasob z najmniejszym wynikiem punktowym. Dzieki temu aplikacja nie bierze pierwszego wolnego zasobu, tylko najbardziej sensowny kosztowo i organizacyjnie.
