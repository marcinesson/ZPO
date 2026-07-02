package pl.opieka.opieka.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.opieka.opieka.model.CareBooking;
import pl.opieka.opieka.service.CareBookingService;

@Route("babysitter/bookings")
@PageTitle("Panel")
@RolesAllowed("BABYSITTER")
public class BabysitterCareBookingPanelView extends VerticalLayout {
    private final CareBookingService service;
    private final Grid<CareBooking> grid = new Grid<>(CareBooking.class, false);

    public BabysitterCareBookingPanelView(CareBookingService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(CareBooking::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(CareBooking::getCategory).setHeader("Kategoria");
        grid.addColumn(CareBooking::getStartTime).setHeader("Start");
        grid.addColumn(CareBooking::getEndTime).setHeader("Koniec");
        grid.addColumn(CareBooking::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(CareBooking::getScore).setHeader("Wynik");
        grid.addColumn(CareBooking::getTotalPrice).setHeader("Koszt");
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
