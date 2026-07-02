package pl.bowling.bowling.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.bowling.bowling.model.LaneBooking;
import pl.bowling.bowling.service.LaneBookingService;

@Route("manager/bookings")
@PageTitle("Panel")
@RolesAllowed("MANAGER")
public class ManagerLaneBookingPanelView extends VerticalLayout {
    private final LaneBookingService service;
    private final Grid<LaneBooking> grid = new Grid<>(LaneBooking.class, false);

    public ManagerLaneBookingPanelView(LaneBookingService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(LaneBooking::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(LaneBooking::getStartTime).setHeader("Start");
        grid.addColumn(LaneBooking::getEndTime).setHeader("Koniec");
        grid.addColumn(LaneBooking::getTotalPrice).setHeader("Koszt");
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
