package pl.robotyka.robotyka.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.robotyka.robotyka.model.RoboticsWorkshop;
import pl.robotyka.robotyka.service.RoboticsWorkshopService;

@Route("mentor/workshops")
@PageTitle("Panel")
@RolesAllowed("MENTOR")
public class MentorRoboticsWorkshopPanelView extends VerticalLayout {
    private final RoboticsWorkshopService service;
    private final Grid<RoboticsWorkshop> grid = new Grid<>(RoboticsWorkshop.class, false);

    public MentorRoboticsWorkshopPanelView(RoboticsWorkshopService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(RoboticsWorkshop::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(RoboticsWorkshop::getCategory).setHeader("Kategoria");
        grid.addColumn(RoboticsWorkshop::getStartTime).setHeader("Start");
        grid.addColumn(RoboticsWorkshop::getEndTime).setHeader("Koniec");
        grid.addColumn(RoboticsWorkshop::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(RoboticsWorkshop::getScore).setHeader("Wynik");
        grid.addColumn(RoboticsWorkshop::getTotalPrice).setHeader("Koszt");
        grid.addComponentColumn(record -> new Button("Usun", event -> {
            service.cancelRecord(record.getId());
            refresh();
        }));
        grid.setWidthFull();
    }

    private void refresh() {
        grid.setItems(service.getAllRecords());
    }
}
