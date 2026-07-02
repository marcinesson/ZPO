package pl.narzedzia.narzedzia.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.narzedzia.narzedzia.model.ToolRental;
import pl.narzedzia.narzedzia.service.ToolRentalService;

@Route("staff/tools")
@PageTitle("Panel")
@RolesAllowed("STAFF")
public class StaffToolRentalPanelView extends VerticalLayout {
    private final ToolRentalService service;
    private final Grid<ToolRental> grid = new Grid<>(ToolRental.class, false);

    public StaffToolRentalPanelView(ToolRentalService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(ToolRental::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(ToolRental::getCategory).setHeader("Kategoria");
        grid.addColumn(ToolRental::getStartTime).setHeader("Start");
        grid.addColumn(ToolRental::getEndTime).setHeader("Koniec");
        grid.addColumn(ToolRental::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(ToolRental::getScore).setHeader("Wynik");
        grid.addColumn(ToolRental::getTotalPrice).setHeader("Koszt");
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
