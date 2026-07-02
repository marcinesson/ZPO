package pl.sprzetsportowy.sprzetsportowy.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.sprzetsportowy.sprzetsportowy.model.EquipmentRental;
import pl.sprzetsportowy.sprzetsportowy.service.EquipmentRentalService;

@Route("staff/rentals")
@PageTitle("Panel")
@RolesAllowed("STAFF")
public class StaffEquipmentRentalPanelView extends VerticalLayout {
    private final EquipmentRentalService service;
    private final Grid<EquipmentRental> grid = new Grid<>(EquipmentRental.class, false);

    public StaffEquipmentRentalPanelView(EquipmentRentalService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(EquipmentRental::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(EquipmentRental::getCategory).setHeader("Kategoria");
        grid.addColumn(EquipmentRental::getStartTime).setHeader("Start");
        grid.addColumn(EquipmentRental::getEndTime).setHeader("Koniec");
        grid.addColumn(EquipmentRental::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(EquipmentRental::getScore).setHeader("Wynik");
        grid.addColumn(EquipmentRental::getTotalPrice).setHeader("Koszt");
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
