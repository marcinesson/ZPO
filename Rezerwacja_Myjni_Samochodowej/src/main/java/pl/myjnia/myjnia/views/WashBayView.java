package pl.myjnia.myjnia.views;

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
import pl.myjnia.myjnia.model.*;
import pl.myjnia.myjnia.service.WashReservationService;

@Route("washes")
@RouteAlias("")
@PageTitle("Stanowiska myjni")
@RolesAllowed("CLIENT")
public class WashBayView extends VerticalLayout {
    private final WashReservationService service;
    private final ComboBox<WashType> category = new ComboBox<>("Kategoria");
    private final IntegerField requiredCapacity = new IntegerField("Liczba aut");
    private final IntegerField minQuality = new IntegerField("Minimalny standard myjni");
    private final IntegerField taskUnits = new IntegerField("Punkty mycia");
    private final IntegerField priority = new IntegerField("Priorytet 1-5");
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("Koniec");
    private final Grid<WashBay> grid = new Grid<>(WashBay.class, false);

    public WashBayView(WashReservationService service) {
        this.service = service;
        configureForm();
        configureGrid();
        add(new HorizontalLayout(new Anchor("washes/my", "Moje wpisy"), new Anchor("me", "Security info")),
                new H2("Stanowiska myjni"),
                category, requiredCapacity, minQuality, taskUnits, priority, startTime, endTime,
                new Button("Utworz", event -> createRecord()),
                grid);
        refresh();
    }

    private void configureForm() {
        category.setItems(WashType.values());
        category.setValue(WashType.BASIC);
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
        grid.addColumn(WashBay::getName).setHeader("Nazwa");
        grid.addColumn(WashBay::getCategory).setHeader("Kategoria");
        grid.addColumn(WashBay::getCapacity).setHeader("Pojemnosc");
        grid.addColumn(WashBay::getQuality).setHeader("Jakosc");
        grid.addColumn(WashBay::getUnitsPerHour).setHeader("Jedn./h");
        grid.addColumn(WashBay::getPricePerHour).setHeader("Cena/h");
        grid.addColumn(WashBay::isAvailable).setHeader("Dostepny");
        grid.setWidthFull();
    }

    private void createRecord() {
        try {
            WashReservation record = service.createRecord(category.getValue(), requiredCapacity.getValue(),
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
