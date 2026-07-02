package pl.studio.studio.views;

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
import pl.studio.studio.model.*;
import pl.studio.studio.service.StudioSessionService;

@Route("studios")
@RouteAlias("")
@PageTitle("Studio")
@RolesAllowed("CLIENT")
public class StudioRoomView extends VerticalLayout {
    private final StudioSessionService service;
    private final ComboBox<StudioType> category = new ComboBox<>("Kategoria");
    private final IntegerField requiredCapacity = new IntegerField("Liczba osob");
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("Koniec");
    private final Grid<StudioRoom> grid = new Grid<>(StudioRoom.class, false);

    public StudioRoomView(StudioSessionService service) {
        this.service = service;
        configureForm();
        configureGrid();
        add(new HorizontalLayout(new Anchor("studio-sessions/my", "Moje wpisy"), new Anchor("me", "Security info")),
                new H2("Studio"),
                category, requiredCapacity, startTime, endTime,
                new Button("Utworz", event -> createRecord()),
                grid);
        refresh();
    }

    private void configureForm() {
        category.setItems(StudioType.values());
        category.setValue(StudioType.PODCAST);
        requiredCapacity.setMin(1);
        requiredCapacity.setValue(1);
        startTime.setValue(LocalDateTime.now().plusDays(1));
        endTime.setValue(LocalDateTime.now().plusDays(1).plusHours(2));
    }

    private void configureGrid() {
        grid.addColumn(StudioRoom::getName).setHeader("Nazwa");
        grid.addColumn(StudioRoom::getCategory).setHeader("Kategoria");
        grid.addColumn(StudioRoom::getCapacity).setHeader("Pojemnosc");
        grid.addColumn(StudioRoom::getPrice).setHeader("Cena/h");
        grid.addColumn(StudioRoom::isAvailable).setHeader("Dostepny");
        grid.setWidthFull();
    }

    private void createRecord() {
        try {
            StudioSession record = service.createRecord(category.getValue(), requiredCapacity.getValue(),
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
