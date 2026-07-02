package pl.kosmetyczka.kosmetyczka.views;

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
import pl.kosmetyczka.kosmetyczka.model.*;
import pl.kosmetyczka.kosmetyczka.service.BeautyAppointmentService;

@Route("beauty")
@RouteAlias("")
@PageTitle("Stanowiska kosmetyczne")
@RolesAllowed("CLIENT")
public class BeautyStationView extends VerticalLayout {
    private final BeautyAppointmentService service;
    private final ComboBox<BeautyServiceType> category = new ComboBox<>("Kategoria");
    private final IntegerField requiredCapacity = new IntegerField("Liczba klientow");
    private final IntegerField minQuality = new IntegerField("Minimalny poziom uslugi");
    private final IntegerField taskUnits = new IntegerField("Punkty zabiegu");
    private final IntegerField priority = new IntegerField("Priorytet 1-5");
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("Koniec");
    private final Grid<BeautyStation> grid = new Grid<>(BeautyStation.class, false);

    public BeautyStationView(BeautyAppointmentService service) {
        this.service = service;
        configureForm();
        configureGrid();
        add(new HorizontalLayout(new Anchor("beauty/my", "Moje wpisy"), new Anchor("me", "Security info")),
                new H2("Stanowiska kosmetyczne"),
                category, requiredCapacity, minQuality, taskUnits, priority, startTime, endTime,
                new Button("Utworz", event -> createRecord()),
                grid);
        refresh();
    }

    private void configureForm() {
        category.setItems(BeautyServiceType.values());
        category.setValue(BeautyServiceType.NAILS);
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
        grid.addColumn(BeautyStation::getName).setHeader("Nazwa");
        grid.addColumn(BeautyStation::getCategory).setHeader("Kategoria");
        grid.addColumn(BeautyStation::getCapacity).setHeader("Pojemnosc");
        grid.addColumn(BeautyStation::getQuality).setHeader("Jakosc");
        grid.addColumn(BeautyStation::getUnitsPerHour).setHeader("Jedn./h");
        grid.addColumn(BeautyStation::getPricePerHour).setHeader("Cena/h");
        grid.addColumn(BeautyStation::isAvailable).setHeader("Dostepny");
        grid.setWidthFull();
    }

    private void createRecord() {
        try {
            BeautyAppointment record = service.createRecord(category.getValue(), requiredCapacity.getValue(),
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
