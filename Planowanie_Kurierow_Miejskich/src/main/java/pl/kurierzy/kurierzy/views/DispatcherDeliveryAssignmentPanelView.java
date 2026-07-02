package pl.kurierzy.kurierzy.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.kurierzy.kurierzy.model.DeliveryAssignment;
import pl.kurierzy.kurierzy.service.DeliveryAssignmentService;

@Route("dispatcher/deliveries")
@PageTitle("Panel")
@RolesAllowed("DISPATCHER")
public class DispatcherDeliveryAssignmentPanelView extends VerticalLayout {
    private final DeliveryAssignmentService service;
    private final Grid<DeliveryAssignment> grid = new Grid<>(DeliveryAssignment.class, false);

    public DispatcherDeliveryAssignmentPanelView(DeliveryAssignmentService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(DeliveryAssignment::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(DeliveryAssignment::getCategory).setHeader("Kategoria");
        grid.addColumn(DeliveryAssignment::getStartTime).setHeader("Start");
        grid.addColumn(DeliveryAssignment::getEndTime).setHeader("Koniec");
        grid.addColumn(DeliveryAssignment::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(DeliveryAssignment::getScore).setHeader("Wynik");
        grid.addColumn(DeliveryAssignment::getTotalPrice).setHeader("Koszt");
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
