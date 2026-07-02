package pl.serwisrowerowy.serwisrowerowy.views;

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
import pl.serwisrowerowy.serwisrowerowy.model.*;
import pl.serwisrowerowy.serwisrowerowy.service.BikeRepairOrderService;

@Route("repairs")
@RouteAlias("")
@PageTitle("Mechanicy rowerowi")
@RolesAllowed("CLIENT")
public class BikeMechanicView extends VerticalLayout {
    private final BikeRepairOrderService service;
    private final ComboBox<RepairType> category = new ComboBox<>("Kategoria");
    private final IntegerField requiredCapacity = new IntegerField("Liczba rowerow");
    private final IntegerField minQuality = new IntegerField("Minimalny poziom mechanika");
    private final IntegerField taskUnits = new IntegerField("Punkty naprawy");
    private final IntegerField priority = new IntegerField("Priorytet 1-5");
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("Koniec");
    private final Grid<BikeMechanic> grid = new Grid<>(BikeMechanic.class, false);

    public BikeMechanicView(BikeRepairOrderService service) {
        this.service = service;
        configureForm();
        configureGrid();
        add(new HorizontalLayout(new Anchor("repairs/my", "Moje wpisy"), new Anchor("me", "Security info")),
                new H2("Mechanicy rowerowi"),
                category, requiredCapacity, minQuality, taskUnits, priority, startTime, endTime,
                new Button("Utworz", event -> createRecord()),
                grid);
        refresh();
    }

    private void configureForm() {
        category.setItems(RepairType.values());
        category.setValue(RepairType.BRAKES);
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
        grid.addColumn(BikeMechanic::getName).setHeader("Nazwa");
        grid.addColumn(BikeMechanic::getCategory).setHeader("Kategoria");
        grid.addColumn(BikeMechanic::getCapacity).setHeader("Pojemnosc");
        grid.addColumn(BikeMechanic::getQuality).setHeader("Jakosc");
        grid.addColumn(BikeMechanic::getUnitsPerHour).setHeader("Jedn./h");
        grid.addColumn(BikeMechanic::getPricePerHour).setHeader("Cena/h");
        grid.addColumn(BikeMechanic::isAvailable).setHeader("Dostepny");
        grid.setWidthFull();
    }

    private void createRecord() {
        try {
            BikeRepairOrder record = service.createRecord(category.getValue(), requiredCapacity.getValue(),
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
