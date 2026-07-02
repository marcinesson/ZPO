package pl.korty.korty.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.korty.korty.model.TennisCourtReservation;
import pl.korty.korty.service.TennisCourtReservationService;

@Route("manager/reservations")
@PageTitle("Panel")
@RolesAllowed("MANAGER")
public class ManagerTennisCourtReservationPanelView extends VerticalLayout {
    private final TennisCourtReservationService service;
    private final Grid<TennisCourtReservation> grid = new Grid<>(TennisCourtReservation.class, false);

    public ManagerTennisCourtReservationPanelView(TennisCourtReservationService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(TennisCourtReservation::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(TennisCourtReservation::getCategory).setHeader("Kategoria");
        grid.addColumn(TennisCourtReservation::getStartTime).setHeader("Start");
        grid.addColumn(TennisCourtReservation::getEndTime).setHeader("Koniec");
        grid.addColumn(TennisCourtReservation::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(TennisCourtReservation::getScore).setHeader("Wynik");
        grid.addColumn(TennisCourtReservation::getTotalPrice).setHeader("Koszt");
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
