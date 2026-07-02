# Rezerwacja_Myjni_Samochodowej

Projekt zaliczeniowy Spring Boot + Vaadin.

- UI klienta: `/washes`
- Panel roli wyzszej: `/manager/washes`
- Swagger: `/swagger-ui.html`
- REST POST: `/api/washes`
- Baza PostgreSQL: `rezerwacja_myjni_samochodowej`
- Loginy: `client/client`, `manager/manager`

## Opis algorytmu

Algorytm wybiera najlepszy zasob dla nowego wpisu. Najpierw odrzuca zasoby, ktore nie pasuja do kategorii, nie maja wystarczajacej pojemnosci, jakosci albo maja kolizje czasowa.

Potem liczy wymagany czas:

`ceil(taskUnits / unitsPerHour)`

Na koncu liczy wynik punktowy:

`cena + kara za nadmiar pojemnosci + kara za nadmiar jakosci + korekta priorytetu`

Wygrywa zasob z najmniejszym wynikiem. Dzieki temu system nie wybiera pierwszego wolnego rekordu, tylko najbardziej dopasowany i sensowny kosztowo.
