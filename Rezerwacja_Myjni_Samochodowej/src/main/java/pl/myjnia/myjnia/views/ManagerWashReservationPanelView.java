package pl.myjnia.myjnia.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.myjnia.myjnia.model.WashReservation;
import pl.myjnia.myjnia.service.WashReservationService;

@Route("manager/washes")
@PageTitle("Panel")
@RolesAllowed("MANAGER")
public class ManagerWashReservationPanelView extends VerticalLayout {
    private final WashReservationService service;
    private final Grid<WashReservation> grid = new Grid<>(WashReservation.class, false);

    public ManagerWashReservationPanelView(WashReservationService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(WashReservation::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(WashReservation::getCategory).setHeader("Kategoria");
        grid.addColumn(WashReservation::getStartTime).setHeader("Start");
        grid.addColumn(WashReservation::getEndTime).setHeader("Koniec");
        grid.addColumn(WashReservation::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(WashReservation::getScore).setHeader("Wynik");
        grid.addColumn(WashReservation::getTotalPrice).setHeader("Koszt");
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
