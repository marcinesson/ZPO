package pl.agd.agd.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.agd.agd.model.ApplianceRepair;
import pl.agd.agd.service.ApplianceRepairService;

@Route("repairs/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MyApplianceRepairView extends VerticalLayout {
    public MyApplianceRepairView(ApplianceRepairService service) {
        Grid<ApplianceRepair> grid = new Grid<>(ApplianceRepair.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(ApplianceRepair::getCategory).setHeader("Kategoria");
        grid.addColumn(ApplianceRepair::getRequiredCapacity).setHeader("Pojemnosc");
        grid.addColumn(ApplianceRepair::getMinQuality).setHeader("Jakosc");
        grid.addColumn(ApplianceRepair::getTaskUnits).setHeader("Jednostki");
        grid.addColumn(ApplianceRepair::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(ApplianceRepair::getScore).setHeader("Wynik");
        grid.addColumn(ApplianceRepair::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("repairs", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
