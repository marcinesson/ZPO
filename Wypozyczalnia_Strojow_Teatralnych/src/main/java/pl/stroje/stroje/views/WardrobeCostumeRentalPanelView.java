package pl.stroje.stroje.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.stroje.stroje.model.CostumeRental;
import pl.stroje.stroje.service.CostumeRentalService;

@Route("wardrobe/rentals")
@PageTitle("Panel")
@RolesAllowed("WARDROBE")
public class WardrobeCostumeRentalPanelView extends VerticalLayout {
    private final CostumeRentalService service;
    private final Grid<CostumeRental> grid = new Grid<>(CostumeRental.class, false);

    public WardrobeCostumeRentalPanelView(CostumeRentalService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(CostumeRental::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(CostumeRental::getStartTime).setHeader("Start");
        grid.addColumn(CostumeRental::getEndTime).setHeader("Koniec");
        grid.addColumn(CostumeRental::getTotalPrice).setHeader("Koszt");
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
