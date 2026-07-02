package pl.stroje.stroje.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.stroje.stroje.model.CostumeRental;
import pl.stroje.stroje.service.CostumeRentalService;

@Route("costume-rentals/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MyCostumeRentalView extends VerticalLayout {
    public MyCostumeRentalView(CostumeRentalService service) {
        Grid<CostumeRental> grid = new Grid<>(CostumeRental.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(CostumeRental::getRequiredCapacity).setHeader("Wymaganie");
        grid.addColumn(CostumeRental::getStartTime).setHeader("Start");
        grid.addColumn(CostumeRental::getEndTime).setHeader("Koniec");
        grid.addColumn(CostumeRental::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("costumes", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
