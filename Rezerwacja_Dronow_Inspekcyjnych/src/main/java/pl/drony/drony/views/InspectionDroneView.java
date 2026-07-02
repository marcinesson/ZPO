package pl.drony.drony.views;

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
import pl.drony.drony.model.*;
import pl.drony.drony.service.DroneReservationService;

@Route("drones")
@RouteAlias("")
@PageTitle("Dron")
@RolesAllowed("CLIENT")
public class InspectionDroneView extends VerticalLayout {
    private final DroneReservationService service;
    private final ComboBox<DroneType> category = new ComboBox<>("Kategoria");
    private final IntegerField requiredCapacity = new IntegerField("Minimalny udzwig / klasa");
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("Koniec");
    private final Grid<InspectionDrone> grid = new Grid<>(InspectionDrone.class, false);

    public InspectionDroneView(DroneReservationService service) {
        this.service = service;
        configureForm();
        configureGrid();
        add(new HorizontalLayout(new Anchor("drone-reservations/my", "Moje wpisy"), new Anchor("me", "Security info")),
                new H2("Dron"),
                category, requiredCapacity, startTime, endTime,
                new Button("Utworz", event -> createRecord()),
                grid);
        refresh();
    }

    private void configureForm() {
        category.setItems(DroneType.values());
        category.setValue(DroneType.PHOTO);
        requiredCapacity.setMin(1);
        requiredCapacity.setValue(1);
        startTime.setValue(LocalDateTime.now().plusDays(1));
        endTime.setValue(LocalDateTime.now().plusDays(1).plusHours(2));
    }

    private void configureGrid() {
        grid.addColumn(InspectionDrone::getName).setHeader("Nazwa");
        grid.addColumn(InspectionDrone::getCategory).setHeader("Kategoria");
        grid.addColumn(InspectionDrone::getCapacity).setHeader("Pojemnosc");
        grid.addColumn(InspectionDrone::getPrice).setHeader("Cena/h");
        grid.addColumn(InspectionDrone::isAvailable).setHeader("Dostepny");
        grid.setWidthFull();
    }

    private void createRecord() {
        try {
            DroneReservation record = service.createRecord(category.getValue(), requiredCapacity.getValue(),
                    startTime.getValue(), endTime.getValue());
            Notification.show("Utworzono id: " + record.getId() + ", koszt: " + record.getTotalPrice());
            refresh();
        } catch (RuntimeException exception) {
            Notification.show(exception.getMessage());
        }
    }

    private void refresh() {
        grid.setItems(service.getAllResources());
    }
}
