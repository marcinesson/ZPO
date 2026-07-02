package pl.badania.badania.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.badania.badania.model.LabTestBooking;
import pl.badania.badania.service.LabTestBookingService;

@Route("lab/tests")
@PageTitle("Panel")
@RolesAllowed("LAB_TECH")
public class LabTechLabTestBookingPanelView extends VerticalLayout {
    private final LabTestBookingService service;
    private final Grid<LabTestBooking> grid = new Grid<>(LabTestBooking.class, false);

    public LabTechLabTestBookingPanelView(LabTestBookingService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(LabTestBooking::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(LabTestBooking::getCategory).setHeader("Kategoria");
        grid.addColumn(LabTestBooking::getStartTime).setHeader("Start");
        grid.addColumn(LabTestBooking::getEndTime).setHeader("Koniec");
        grid.addColumn(LabTestBooking::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(LabTestBooking::getScore).setHeader("Wynik");
        grid.addColumn(LabTestBooking::getTotalPrice).setHeader("Koszt");
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
