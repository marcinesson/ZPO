package pl.pralnia.pralnia.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.pralnia.pralnia.model.LaundryOrder;
import pl.pralnia.pralnia.service.LaundryOrderService;

@Route("worker/orders")
@PageTitle("Panel")
@RolesAllowed("LAUNDRY_WORKER")
public class LaundryWorkerLaundryOrderPanelView extends VerticalLayout {
    private final LaundryOrderService service;
    private final Grid<LaundryOrder> grid = new Grid<>(LaundryOrder.class, false);

    public LaundryWorkerLaundryOrderPanelView(LaundryOrderService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(LaundryOrder::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(LaundryOrder::getCategory).setHeader("Kategoria");
        grid.addColumn(LaundryOrder::getStartTime).setHeader("Start");
        grid.addColumn(LaundryOrder::getEndTime).setHeader("Koniec");
        grid.addColumn(LaundryOrder::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(LaundryOrder::getScore).setHeader("Wynik");
        grid.addColumn(LaundryOrder::getTotalPrice).setHeader("Koszt");
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
