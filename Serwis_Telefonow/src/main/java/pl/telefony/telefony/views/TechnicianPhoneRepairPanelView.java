package pl.telefony.telefony.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.telefony.telefony.model.PhoneRepair;
import pl.telefony.telefony.service.PhoneRepairService;

@Route("technician/orders")
@PageTitle("Panel")
@RolesAllowed("TECHNICIAN")
public class TechnicianPhoneRepairPanelView extends VerticalLayout {
    private final PhoneRepairService service;
    private final Grid<PhoneRepair> grid = new Grid<>(PhoneRepair.class, false);

    public TechnicianPhoneRepairPanelView(PhoneRepairService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(PhoneRepair::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(PhoneRepair::getCategory).setHeader("Kategoria");
        grid.addColumn(PhoneRepair::getStartTime).setHeader("Start");
        grid.addColumn(PhoneRepair::getEndTime).setHeader("Koniec");
        grid.addColumn(PhoneRepair::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(PhoneRepair::getScore).setHeader("Wynik");
        grid.addColumn(PhoneRepair::getTotalPrice).setHeader("Koszt");
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
