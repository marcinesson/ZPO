package pl.catering.catering.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.catering.catering.model.CateringOrder;
import pl.catering.catering.service.CateringOrderService;

@Route("kitchen/orders")
@PageTitle("Panel")
@RolesAllowed("KITCHEN_MANAGER")
public class KitchenManagerCateringOrderPanelView extends VerticalLayout {
    private final CateringOrderService service;
    private final Grid<CateringOrder> grid = new Grid<>(CateringOrder.class, false);

    public KitchenManagerCateringOrderPanelView(CateringOrderService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(CateringOrder::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(CateringOrder::getCategory).setHeader("Kategoria");
        grid.addColumn(CateringOrder::getStartTime).setHeader("Start");
        grid.addColumn(CateringOrder::getEndTime).setHeader("Koniec");
        grid.addColumn(CateringOrder::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(CateringOrder::getScore).setHeader("Wynik");
        grid.addColumn(CateringOrder::getTotalPrice).setHeader("Koszt");
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
