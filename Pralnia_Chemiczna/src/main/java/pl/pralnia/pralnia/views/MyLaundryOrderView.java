package pl.pralnia.pralnia.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.pralnia.pralnia.model.LaundryOrder;
import pl.pralnia.pralnia.service.LaundryOrderService;

@Route("laundry/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MyLaundryOrderView extends VerticalLayout {
    public MyLaundryOrderView(LaundryOrderService service) {
        Grid<LaundryOrder> grid = new Grid<>(LaundryOrder.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(LaundryOrder::getCategory).setHeader("Kategoria");
        grid.addColumn(LaundryOrder::getRequiredCapacity).setHeader("Pojemnosc");
        grid.addColumn(LaundryOrder::getMinQuality).setHeader("Jakosc");
        grid.addColumn(LaundryOrder::getTaskUnits).setHeader("Jednostki");
        grid.addColumn(LaundryOrder::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(LaundryOrder::getScore).setHeader("Wynik");
        grid.addColumn(LaundryOrder::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("laundry", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
