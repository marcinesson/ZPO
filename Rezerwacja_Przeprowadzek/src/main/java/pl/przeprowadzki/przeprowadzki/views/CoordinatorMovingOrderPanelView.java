package pl.przeprowadzki.przeprowadzki.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.przeprowadzki.przeprowadzki.model.MovingOrder;
import pl.przeprowadzki.przeprowadzki.service.MovingOrderService;

@Route("coordinator/orders")
@PageTitle("Panel")
@RolesAllowed("COORDINATOR")
public class CoordinatorMovingOrderPanelView extends VerticalLayout {
    private final MovingOrderService service;
    private final Grid<MovingOrder> grid = new Grid<>(MovingOrder.class, false);

    public CoordinatorMovingOrderPanelView(MovingOrderService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(MovingOrder::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(MovingOrder::getCategory).setHeader("Kategoria");
        grid.addColumn(MovingOrder::getStartTime).setHeader("Start");
        grid.addColumn(MovingOrder::getEndTime).setHeader("Koniec");
        grid.addColumn(MovingOrder::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(MovingOrder::getScore).setHeader("Wynik");
        grid.addColumn(MovingOrder::getTotalPrice).setHeader("Koszt");
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
