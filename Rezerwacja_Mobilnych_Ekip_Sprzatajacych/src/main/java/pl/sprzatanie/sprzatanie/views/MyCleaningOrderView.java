package pl.sprzatanie.sprzatanie.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.sprzatanie.sprzatanie.model.CleaningOrder;
import pl.sprzatanie.sprzatanie.service.CleaningOrderService;

@Route("cleaning/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MyCleaningOrderView extends VerticalLayout {
    public MyCleaningOrderView(CleaningOrderService service) {
        Grid<CleaningOrder> grid = new Grid<>(CleaningOrder.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(CleaningOrder::getCategory).setHeader("Kategoria");
        grid.addColumn(CleaningOrder::getRequiredCapacity).setHeader("Pojemnosc");
        grid.addColumn(CleaningOrder::getMinQuality).setHeader("Jakosc");
        grid.addColumn(CleaningOrder::getTaskUnits).setHeader("Jednostki");
        grid.addColumn(CleaningOrder::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(CleaningOrder::getScore).setHeader("Wynik");
        grid.addColumn(CleaningOrder::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("cleaning", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
