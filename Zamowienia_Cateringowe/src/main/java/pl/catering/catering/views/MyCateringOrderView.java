package pl.catering.catering.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.catering.catering.model.CateringOrder;
import pl.catering.catering.service.CateringOrderService;

@Route("catering/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MyCateringOrderView extends VerticalLayout {
    public MyCateringOrderView(CateringOrderService service) {
        Grid<CateringOrder> grid = new Grid<>(CateringOrder.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(CateringOrder::getCategory).setHeader("Kategoria");
        grid.addColumn(CateringOrder::getRequiredCapacity).setHeader("Pojemnosc");
        grid.addColumn(CateringOrder::getMinQuality).setHeader("Jakosc");
        grid.addColumn(CateringOrder::getTaskUnits).setHeader("Jednostki");
        grid.addColumn(CateringOrder::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(CateringOrder::getScore).setHeader("Wynik");
        grid.addColumn(CateringOrder::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("catering", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
