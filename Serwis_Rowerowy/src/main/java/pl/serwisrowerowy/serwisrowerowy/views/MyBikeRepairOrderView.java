package pl.serwisrowerowy.serwisrowerowy.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.serwisrowerowy.serwisrowerowy.model.BikeRepairOrder;
import pl.serwisrowerowy.serwisrowerowy.service.BikeRepairOrderService;

@Route("repairs/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MyBikeRepairOrderView extends VerticalLayout {
    public MyBikeRepairOrderView(BikeRepairOrderService service) {
        Grid<BikeRepairOrder> grid = new Grid<>(BikeRepairOrder.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(BikeRepairOrder::getCategory).setHeader("Kategoria");
        grid.addColumn(BikeRepairOrder::getRequiredCapacity).setHeader("Pojemnosc");
        grid.addColumn(BikeRepairOrder::getMinQuality).setHeader("Jakosc");
        grid.addColumn(BikeRepairOrder::getTaskUnits).setHeader("Jednostki");
        grid.addColumn(BikeRepairOrder::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(BikeRepairOrder::getScore).setHeader("Wynik");
        grid.addColumn(BikeRepairOrder::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("repairs", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
