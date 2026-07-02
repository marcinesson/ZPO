package pl.korepetycje.korepetycje.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.korepetycje.korepetycje.model.TutoringBooking;
import pl.korepetycje.korepetycje.service.TutoringBookingService;

@Route("tutoring/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MyTutoringBookingView extends VerticalLayout {
    public MyTutoringBookingView(TutoringBookingService service) {
        Grid<TutoringBooking> grid = new Grid<>(TutoringBooking.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(TutoringBooking::getCategory).setHeader("Kategoria");
        grid.addColumn(TutoringBooking::getRequiredCapacity).setHeader("Pojemnosc");
        grid.addColumn(TutoringBooking::getMinQuality).setHeader("Jakosc");
        grid.addColumn(TutoringBooking::getTaskUnits).setHeader("Jednostki");
        grid.addColumn(TutoringBooking::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(TutoringBooking::getScore).setHeader("Wynik");
        grid.addColumn(TutoringBooking::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("tutoring", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
