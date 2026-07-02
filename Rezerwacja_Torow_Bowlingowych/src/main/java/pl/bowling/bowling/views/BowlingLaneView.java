package pl.bowling.bowling.views;

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
import pl.bowling.bowling.model.*;
import pl.bowling.bowling.service.LaneBookingService;

@Route("lanes")
@RouteAlias("")
@PageTitle("Tor")
@RolesAllowed("CLIENT")
public class BowlingLaneView extends VerticalLayout {
    private final LaneBookingService service;
    private final ComboBox<LaneType> category = new ComboBox<>("Kategoria");
    private final IntegerField requiredCapacity = new IntegerField("Liczba graczy");
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("Koniec");
    private final Grid<BowlingLane> grid = new Grid<>(BowlingLane.class, false);

    public BowlingLaneView(LaneBookingService service) {
        this.service = service;
        configureForm();
        configureGrid();
        add(new HorizontalLayout(new Anchor("lane-bookings/my", "Moje wpisy"), new Anchor("me", "Security info")),
                new H2("Tor"),
                category, requiredCapacity, startTime, endTime,
                new Button("Utworz", event -> createRecord()),
                grid);
        refresh();
    }

    private void configureForm() {
        category.setItems(LaneType.values());
        category.setValue(LaneType.STANDARD);
        requiredCapacity.setMin(1);
        requiredCapacity.setValue(1);
        startTime.setValue(LocalDateTime.now().plusDays(1));
        endTime.setValue(LocalDateTime.now().plusDays(1).plusHours(2));
    }

    private void configureGrid() {
        grid.addColumn(BowlingLane::getName).setHeader("Nazwa");
        grid.addColumn(BowlingLane::getCategory).setHeader("Kategoria");
        grid.addColumn(BowlingLane::getCapacity).setHeader("Pojemnosc");
        grid.addColumn(BowlingLane::getPrice).setHeader("Cena/h");
        grid.addColumn(BowlingLane::isAvailable).setHeader("Dostepny");
        grid.setWidthFull();
    }

    private void createRecord() {
        try {
            LaneBooking record = service.createRecord(category.getValue(), requiredCapacity.getValue(),
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
