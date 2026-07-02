# Rezerwacja_Symulatorow_VR

Projekt zaliczeniowy Spring Boot + Vaadin.

- UI klienta: `/simulators`
- Panel roli wyzszej: `/instructor/sessions`
- Swagger: `/swagger-ui.html`
- REST POST: `/api/simulators`
- Baza PostgreSQL: `rezerwacja_symulatorow_vr`
- Loginy: `client/client`, `instructor/instructor`


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
