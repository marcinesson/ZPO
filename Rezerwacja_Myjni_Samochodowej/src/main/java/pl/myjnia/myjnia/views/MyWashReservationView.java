package pl.myjnia.myjnia.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.myjnia.myjnia.model.WashReservation;
import pl.myjnia.myjnia.service.WashReservationService;

@Route("washes/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MyWashReservationView extends VerticalLayout {
    public MyWashReservationView(WashReservationService service) {
        Grid<WashReservation> grid = new Grid<>(WashReservation.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(WashReservation::getCategory).setHeader("Kategoria");
        grid.addColumn(WashReservation::getRequiredCapacity).setHeader("Pojemnosc");
        grid.addColumn(WashReservation::getMinQuality).setHeader("Jakosc");
        grid.addColumn(WashReservation::getTaskUnits).setHeader("Jednostki");
        grid.addColumn(WashReservation::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(WashReservation::getScore).setHeader("Wynik");
        grid.addColumn(WashReservation::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("washes", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
