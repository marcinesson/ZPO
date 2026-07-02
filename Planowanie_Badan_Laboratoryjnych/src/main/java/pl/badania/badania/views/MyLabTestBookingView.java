package pl.badania.badania.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.badania.badania.model.LabTestBooking;
import pl.badania.badania.service.LabTestBookingService;

@Route("tests/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MyLabTestBookingView extends VerticalLayout {
    public MyLabTestBookingView(LabTestBookingService service) {
        Grid<LabTestBooking> grid = new Grid<>(LabTestBooking.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(LabTestBooking::getCategory).setHeader("Kategoria");
        grid.addColumn(LabTestBooking::getRequiredCapacity).setHeader("Pojemnosc");
        grid.addColumn(LabTestBooking::getMinQuality).setHeader("Jakosc");
        grid.addColumn(LabTestBooking::getTaskUnits).setHeader("Jednostki");
        grid.addColumn(LabTestBooking::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(LabTestBooking::getScore).setHeader("Wynik");
        grid.addColumn(LabTestBooking::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("tests", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
