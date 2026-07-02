package pl.robotyka.robotyka.views;

import java.time.LocalDateTime;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.robotyka.robotyka.model.*;
import pl.robotyka.robotyka.service.RoboticsWorkshopService;

@Route("robotics")
@RouteAlias("")
@PageTitle("Pracownie robotyki")
@RolesAllowed("CLIENT")
public class RoboticsLabView extends VerticalLayout {
    private final RoboticsWorkshopService service;
    private final ComboBox<RobotType> category = new ComboBox<>("Kategoria");
    private final IntegerField requiredCapacity = new IntegerField("Liczba uczestnikow");
    private final IntegerField minQuality = new IntegerField("Minimalny poziom sprzetu");
    private final IntegerField taskUnits = new IntegerField("Liczba zadan");
    private final IntegerField priority = new IntegerField("Priorytet 1-5");
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("Koniec");
    private final Grid<RoboticsLab> grid = new Grid<>(RoboticsLab.class, false);

    public RoboticsLabView(RoboticsWorkshopService service) {
        this.service = service;
        configureForm();
        configureGrid();
        add(new HorizontalLayout(new Anchor("robotics/my", "Moje wpisy"), new Anchor("me", "Security info")),
                new H2("Pracownie robotyki"),
                category, requiredCapacity, minQuality, taskUnits, priority, startTime, endTime,
                new Button("Utworz", event -> createRecord()),
                grid);
        refresh();
    }

    private void configureForm() {
        category.setItems(RobotType.values());
        category.setValue(RobotType.LEGO);
        requiredCapacity.setMin(1);
        requiredCapacity.setValue(1);
        minQuality.setMin(1);
        minQuality.setValue(1);
        taskUnits.setMin(1);
        taskUnits.setValue(5);
        priority.setMin(1);
        priority.setMax(5);
        priority.setValue(3);
        startTime.setValue(LocalDateTime.now().plusDays(1));
        endTime.setValue(LocalDateTime.now().plusDays(1).plusHours(3));
    }

    private void configureGrid() {
        grid.addColumn(RoboticsLab::getName).setHeader("Nazwa");
        grid.addColumn(RoboticsLab::getCategory).setHeader("Kategoria");
        grid.addColumn(RoboticsLab::getCapacity).setHeader("Pojemnosc");
        grid.addColumn(RoboticsLab::getQuality).setHeader("Jakosc");
        grid.addColumn(RoboticsLab::getUnitsPerHour).setHeader("Jedn./h");
        grid.addColumn(RoboticsLab::getPricePerHour).setHeader("Cena/h");
        grid.addColumn(RoboticsLab::isAvailable).setHeader("Dostepny");
        grid.setWidthFull();
    }

    private void createRecord() {
        try {
            RoboticsWorkshop record = service.createRecord(category.getValue(), requiredCapacity.getValue(),
                    minQuality.getValue(), taskUnits.getValue(), priority.getValue(),
                    startTime.getValue(), endTime.getValue());
            Notification.show("Utworzono id: " + record.getId() + ", koszt: " + record.getTotalPrice()
                    + ", wynik: " + record.getScore());
            refresh();
        } catch (RuntimeException exception) {
            Notification.show(exception.getMessage());
        }
    }

    private void refresh() {
        grid.setItems(service.getAllResources());
    }
}
