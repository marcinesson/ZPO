package pl.telefony.telefony.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.telefony.telefony.model.PhoneRepair;
import pl.telefony.telefony.service.PhoneRepairService;

@Route("phone-repairs/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MyPhoneRepairView extends VerticalLayout {
    public MyPhoneRepairView(PhoneRepairService service) {
        Grid<PhoneRepair> grid = new Grid<>(PhoneRepair.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(PhoneRepair::getCategory).setHeader("Kategoria");
        grid.addColumn(PhoneRepair::getRequiredCapacity).setHeader("Pojemnosc");
        grid.addColumn(PhoneRepair::getMinQuality).setHeader("Jakosc");
        grid.addColumn(PhoneRepair::getTaskUnits).setHeader("Jednostki");
        grid.addColumn(PhoneRepair::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(PhoneRepair::getScore).setHeader("Wynik");
        grid.addColumn(PhoneRepair::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("phone-repairs", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
