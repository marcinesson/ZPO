package pl.kurierzy.kurierzy.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.kurierzy.kurierzy.model.DeliveryAssignment;
import pl.kurierzy.kurierzy.service.DeliveryAssignmentService;

@Route("deliveries/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MyDeliveryAssignmentView extends VerticalLayout {
    public MyDeliveryAssignmentView(DeliveryAssignmentService service) {
        Grid<DeliveryAssignment> grid = new Grid<>(DeliveryAssignment.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(DeliveryAssignment::getCategory).setHeader("Kategoria");
        grid.addColumn(DeliveryAssignment::getRequiredCapacity).setHeader("Pojemnosc");
        grid.addColumn(DeliveryAssignment::getMinQuality).setHeader("Jakosc");
        grid.addColumn(DeliveryAssignment::getTaskUnits).setHeader("Jednostki");
        grid.addColumn(DeliveryAssignment::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(DeliveryAssignment::getScore).setHeader("Wynik");
        grid.addColumn(DeliveryAssignment::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("deliveries", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
