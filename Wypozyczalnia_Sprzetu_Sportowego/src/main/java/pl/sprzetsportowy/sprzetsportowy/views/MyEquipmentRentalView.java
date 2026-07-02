package pl.sprzetsportowy.sprzetsportowy.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.sprzetsportowy.sprzetsportowy.model.EquipmentRental;
import pl.sprzetsportowy.sprzetsportowy.service.EquipmentRentalService;

@Route("equipment/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MyEquipmentRentalView extends VerticalLayout {
    public MyEquipmentRentalView(EquipmentRentalService service) {
        Grid<EquipmentRental> grid = new Grid<>(EquipmentRental.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(EquipmentRental::getCategory).setHeader("Kategoria");
        grid.addColumn(EquipmentRental::getRequiredCapacity).setHeader("Pojemnosc");
        grid.addColumn(EquipmentRental::getMinQuality).setHeader("Jakosc");
        grid.addColumn(EquipmentRental::getTaskUnits).setHeader("Jednostki");
        grid.addColumn(EquipmentRental::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(EquipmentRental::getScore).setHeader("Wynik");
        grid.addColumn(EquipmentRental::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("equipment", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
