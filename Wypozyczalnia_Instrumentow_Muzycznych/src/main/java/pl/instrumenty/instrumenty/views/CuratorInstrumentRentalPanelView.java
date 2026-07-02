package pl.instrumenty.instrumenty.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.instrumenty.instrumenty.model.InstrumentRental;
import pl.instrumenty.instrumenty.service.InstrumentRentalService;

@Route("curator/rentals")
@PageTitle("Panel")
@RolesAllowed("CURATOR")
public class CuratorInstrumentRentalPanelView extends VerticalLayout {
    private final InstrumentRentalService service;
    private final Grid<InstrumentRental> grid = new Grid<>(InstrumentRental.class, false);

    public CuratorInstrumentRentalPanelView(InstrumentRentalService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(InstrumentRental::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(InstrumentRental::getStartTime).setHeader("Start");
        grid.addColumn(InstrumentRental::getEndTime).setHeader("Koniec");
        grid.addColumn(InstrumentRental::getTotalPrice).setHeader("Koszt");
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
