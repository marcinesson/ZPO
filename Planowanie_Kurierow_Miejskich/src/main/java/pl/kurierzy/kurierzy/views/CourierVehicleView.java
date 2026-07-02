package pl.kurierzy.kurierzy.views;

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
import pl.kurierzy.kurierzy.model.*;
import pl.kurierzy.kurierzy.service.DeliveryAssignmentService;

@Route("deliveries")
@RouteAlias("")
@PageTitle("Pojazdy kurierskie")
@RolesAllowed("CLIENT")
public class CourierVehicleView extends VerticalLayout {
    private final DeliveryAssignmentService service;
    private final ComboBox<DeliveryZone> category = new ComboBox<>("Kategoria");
    private final IntegerField requiredCapacity = new IntegerField("Waga paczek");
    private final IntegerField minQuality = new IntegerField("Minimalny priorytet pojazdu");
    private final IntegerField taskUnits = new IntegerField("Kilometry trasy");
    private final IntegerField priority = new IntegerField("Priorytet 1-5");
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("Koniec");
    private final Grid<CourierVehicle> grid = new Grid<>(CourierVehicle.class, false);

    public CourierVehicleView(DeliveryAssignmentService service) {
        this.service = service;
        configureForm();
        configureGrid();
        add(new HorizontalLayout(new Anchor("deliveries/my", "Moje wpisy"), new Anchor("me", "Security info")),
                new H2("Pojazdy kurierskie"),
                category, requiredCapacity, minQuality, taskUnits, priority, startTime, endTime,
                new Button("Utworz", event -> createRecord()),
                grid);
        refresh();
    }

    private void configureForm() {
        category.setItems(DeliveryZone.values());
        category.setValue(DeliveryZone.CENTER);
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
        grid.addColumn(CourierVehicle::getName).setHeader("Nazwa");
        grid.addColumn(CourierVehicle::getCategory).setHeader("Kategoria");
        grid.addColumn(CourierVehicle::getCapacity).setHeader("Pojemnosc");
        grid.addColumn(CourierVehicle::getQuality).setHeader("Jakosc");
        grid.addColumn(CourierVehicle::getUnitsPerHour).setHeader("Jedn./h");
        grid.addColumn(CourierVehicle::getPricePerHour).setHeader("Cena/h");
        grid.addColumn(CourierVehicle::isAvailable).setHeader("Dostepny");
        grid.setWidthFull();
    }

    private void createRecord() {
        try {
            DeliveryAssignment record = service.createRecord(category.getValue(), requiredCapacity.getValue(),
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
