package pl.narzedzia.narzedzia.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.narzedzia.narzedzia.model.ToolRental;
import pl.narzedzia.narzedzia.service.ToolRentalService;

@Route("tools/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MyToolRentalView extends VerticalLayout {
    public MyToolRentalView(ToolRentalService service) {
        Grid<ToolRental> grid = new Grid<>(ToolRental.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(ToolRental::getCategory).setHeader("Kategoria");
        grid.addColumn(ToolRental::getRequiredCapacity).setHeader("Pojemnosc");
        grid.addColumn(ToolRental::getMinQuality).setHeader("Jakosc");
        grid.addColumn(ToolRental::getTaskUnits).setHeader("Jednostki");
        grid.addColumn(ToolRental::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(ToolRental::getScore).setHeader("Wynik");
        grid.addColumn(ToolRental::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("tools", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
