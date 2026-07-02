package pl.opieka.opieka.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.opieka.opieka.model.CareBooking;
import pl.opieka.opieka.service.CareBookingService;

@Route("care/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MyCareBookingView extends VerticalLayout {
    public MyCareBookingView(CareBookingService service) {
        Grid<CareBooking> grid = new Grid<>(CareBooking.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(CareBooking::getCategory).setHeader("Kategoria");
        grid.addColumn(CareBooking::getRequiredCapacity).setHeader("Pojemnosc");
        grid.addColumn(CareBooking::getMinQuality).setHeader("Jakosc");
        grid.addColumn(CareBooking::getTaskUnits).setHeader("Jednostki");
        grid.addColumn(CareBooking::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(CareBooking::getScore).setHeader("Wynik");
        grid.addColumn(CareBooking::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("care", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
