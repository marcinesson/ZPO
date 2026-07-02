package pl.robotyka.robotyka.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.robotyka.robotyka.model.RoboticsWorkshop;
import pl.robotyka.robotyka.service.RoboticsWorkshopService;

@Route("robotics/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MyRoboticsWorkshopView extends VerticalLayout {
    public MyRoboticsWorkshopView(RoboticsWorkshopService service) {
        Grid<RoboticsWorkshop> grid = new Grid<>(RoboticsWorkshop.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(RoboticsWorkshop::getCategory).setHeader("Kategoria");
        grid.addColumn(RoboticsWorkshop::getRequiredCapacity).setHeader("Pojemnosc");
        grid.addColumn(RoboticsWorkshop::getMinQuality).setHeader("Jakosc");
        grid.addColumn(RoboticsWorkshop::getTaskUnits).setHeader("Jednostki");
        grid.addColumn(RoboticsWorkshop::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(RoboticsWorkshop::getScore).setHeader("Wynik");
        grid.addColumn(RoboticsWorkshop::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("robotics", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
