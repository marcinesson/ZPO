package pl.agd.agd.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.agd.agd.model.ApplianceRepair;
import pl.agd.agd.service.ApplianceRepairService;

@Route("technician/repairs")
@PageTitle("Panel")
@RolesAllowed("TECHNICIAN")
public class TechnicianApplianceRepairPanelView extends VerticalLayout {
    private final ApplianceRepairService service;
    private final Grid<ApplianceRepair> grid = new Grid<>(ApplianceRepair.class, false);

    public TechnicianApplianceRepairPanelView(ApplianceRepairService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(ApplianceRepair::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(ApplianceRepair::getCategory).setHeader("Kategoria");
        grid.addColumn(ApplianceRepair::getStartTime).setHeader("Start");
        grid.addColumn(ApplianceRepair::getEndTime).setHeader("Koniec");
        grid.addColumn(ApplianceRepair::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(ApplianceRepair::getScore).setHeader("Wynik");
        grid.addColumn(ApplianceRepair::getTotalPrice).setHeader("Koszt");
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
