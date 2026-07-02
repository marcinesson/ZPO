package pl.serwisrowerowy.serwisrowerowy.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.serwisrowerowy.serwisrowerowy.model.BikeRepairOrder;
import pl.serwisrowerowy.serwisrowerowy.service.BikeRepairOrderService;

@Route("mechanic/orders")
@PageTitle("Panel")
@RolesAllowed("MECHANIC")
public class MechanicBikeRepairOrderPanelView extends VerticalLayout {
    private final BikeRepairOrderService service;
    private final Grid<BikeRepairOrder> grid = new Grid<>(BikeRepairOrder.class, false);

    public MechanicBikeRepairOrderPanelView(BikeRepairOrderService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(BikeRepairOrder::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(BikeRepairOrder::getCategory).setHeader("Kategoria");
        grid.addColumn(BikeRepairOrder::getStartTime).setHeader("Start");
        grid.addColumn(BikeRepairOrder::getEndTime).setHeader("Koniec");
        grid.addColumn(BikeRepairOrder::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(BikeRepairOrder::getScore).setHeader("Wynik");
        grid.addColumn(BikeRepairOrder::getTotalPrice).setHeader("Koszt");
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
