# Umawianie_Wizyt_Fryzjerskich

Projekt zaliczeniowy Spring Boot + Vaadin.

- UI klienta: `/appointments`
- Panel roli wyzszej: `/stylist/appointments`
- Swagger: `/swagger-ui.html`
- REST POST: `/api/appointments`
- Baza PostgreSQL: `umawianie_wizyt_fryzjerskich`
- Loginy: `client/client`, `stylist/stylist`

## Opis algorytmu

Algorytm wybiera najlepszy zasob dla nowego wpisu. Najpierw odrzuca zasoby, ktore nie pasuja do kategorii, nie maja wystarczajacej pojemnosci, jakosci albo maja kolizje czasowa.

Potem liczy wymagany czas:

`ceil(taskUnits / unitsPerHour)`

Na koncu liczy wynik punktowy:

`cena + kara za nadmiar pojemnosci + kara za nadmiar jakosci + korekta priorytetu`

Wygrywa zasob z najmniejszym wynikiem. Dzieki temu system nie wybiera pierwszego wolnego rekordu, tylko najbardziej dopasowany i sensowny kosztowo.
