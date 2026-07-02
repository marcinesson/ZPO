package pl.korty.korty.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.korty.korty.model.TennisCourtReservation;
import pl.korty.korty.service.TennisCourtReservationService;

@Route("courts/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MyTennisCourtReservationView extends VerticalLayout {
    public MyTennisCourtReservationView(TennisCourtReservationService service) {
        Grid<TennisCourtReservation> grid = new Grid<>(TennisCourtReservation.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(TennisCourtReservation::getCategory).setHeader("Kategoria");
        grid.addColumn(TennisCourtReservation::getRequiredCapacity).setHeader("Pojemnosc");
        grid.addColumn(TennisCourtReservation::getMinQuality).setHeader("Jakosc");
        grid.addColumn(TennisCourtReservation::getTaskUnits).setHeader("Jednostki");
        grid.addColumn(TennisCourtReservation::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(TennisCourtReservation::getScore).setHeader("Wynik");
        grid.addColumn(TennisCourtReservation::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("courts", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
