package pl.sprzatanie.sprzatanie.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.sprzatanie.sprzatanie.model.CleaningOrder;
import pl.sprzatanie.sprzatanie.service.CleaningOrderService;

@Route("coordinator/orders")
@PageTitle("Panel")
@RolesAllowed("COORDINATOR")
public class CoordinatorCleaningOrderPanelView extends VerticalLayout {
    private final CleaningOrderService service;
    private final Grid<CleaningOrder> grid = new Grid<>(CleaningOrder.class, false);

    public CoordinatorCleaningOrderPanelView(CleaningOrderService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(CleaningOrder::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(CleaningOrder::getCategory).setHeader("Kategoria");
        grid.addColumn(CleaningOrder::getStartTime).setHeader("Start");
        grid.addColumn(CleaningOrder::getEndTime).setHeader("Koniec");
        grid.addColumn(CleaningOrder::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(CleaningOrder::getScore).setHeader("Wynik");
        grid.addColumn(CleaningOrder::getTotalPrice).setHeader("Koszt");
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
