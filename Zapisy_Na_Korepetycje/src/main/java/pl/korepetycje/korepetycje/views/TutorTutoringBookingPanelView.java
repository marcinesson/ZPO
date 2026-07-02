package pl.korepetycje.korepetycje.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.korepetycje.korepetycje.model.TutoringBooking;
import pl.korepetycje.korepetycje.service.TutoringBookingService;

@Route("tutor/bookings")
@PageTitle("Panel")
@RolesAllowed("TUTOR")
public class TutorTutoringBookingPanelView extends VerticalLayout {
    private final TutoringBookingService service;
    private final Grid<TutoringBooking> grid = new Grid<>(TutoringBooking.class, false);

    public TutorTutoringBookingPanelView(TutoringBookingService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(TutoringBooking::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(TutoringBooking::getCategory).setHeader("Kategoria");
        grid.addColumn(TutoringBooking::getStartTime).setHeader("Start");
        grid.addColumn(TutoringBooking::getEndTime).setHeader("Koniec");
        grid.addColumn(TutoringBooking::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(TutoringBooking::getScore).setHeader("Wynik");
        grid.addColumn(TutoringBooking::getTotalPrice).setHeader("Koszt");
        grid.addComponentColumn(record -> new Button("Usun", event -> {
            service.cancelRecord(record.getId());
            refresh();
        }));
        grid.setWidthFull();
    }

    private void refresh() {
        grid.setItems(service.getAllRecords());
    }
}
