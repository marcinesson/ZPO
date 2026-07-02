package pl.przeprowadzki.przeprowadzki.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.przeprowadzki.przeprowadzki.model.MovingOrder;
import pl.przeprowadzki.przeprowadzki.service.MovingOrderService;

@Route("moving/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MyMovingOrderView extends VerticalLayout {
    public MyMovingOrderView(MovingOrderService service) {
        Grid<MovingOrder> grid = new Grid<>(MovingOrder.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(MovingOrder::getCategory).setHeader("Kategoria");
        grid.addColumn(MovingOrder::getRequiredCapacity).setHeader("Pojemnosc");
        grid.addColumn(MovingOrder::getMinQuality).setHeader("Jakosc");
        grid.addColumn(MovingOrder::getTaskUnits).setHeader("Jednostki");
        grid.addColumn(MovingOrder::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(MovingOrder::getScore).setHeader("Wynik");
        grid.addColumn(MovingOrder::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("moving", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
