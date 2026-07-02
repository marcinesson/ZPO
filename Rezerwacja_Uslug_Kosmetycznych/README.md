# Rezerwacja_Uslug_Kosmetycznych

Projekt zaliczeniowy Spring Boot + Vaadin.

- UI klienta: `/beauty`
- Panel roli wyzszej: `/beautician/appointments`
- Swagger: `/swagger-ui.html`
- REST POST: `/api/beauty`
- Baza PostgreSQL: `rezerwacja_uslug_kosmetycznych`
- Loginy: `client/client`, `beautician/beautician`

## Opis algorytmu

Algorytm wybiera najlepszy zasob dla nowego wpisu. Najpierw odrzuca zasoby, ktore nie pasuja do kategorii, nie maja wystarczajacej pojemnosci, jakosci albo maja kolizje czasowa.

Potem liczy wymagany czas:

`ceil(taskUnits / unitsPerHour)`

Na koncu liczy wynik punktowy:

`cena + kara za nadmiar pojemnosci + kara za nadmiar jakosci + korekta priorytetu`

Wygrywa zasob z najmniejszym wynikiem. Dzieki temu system nie wybiera pierwszego wolnego rekordu, tylko najbardziej dopasowany i sensowny kosztowo.
