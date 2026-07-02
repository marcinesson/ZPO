package pl.badania.badania.views;

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
import pl.badania.badania.model.*;
import pl.badania.badania.service.LabTestBookingService;

@Route("tests")
@RouteAlias("")
@PageTitle("Urzadzenia laboratoryjne")
@RolesAllowed("CLIENT")
public class LabDeviceView extends VerticalLayout {
    private final LabTestBookingService service;
    private final ComboBox<TestType> category = new ComboBox<>("Kategoria");
    private final IntegerField requiredCapacity = new IntegerField("Liczba probek");
    private final IntegerField minQuality = new IntegerField("Minimalna dokladnosc");
    private final IntegerField taskUnits = new IntegerField("Punkty zlozonosci");
    private final IntegerField priority = new IntegerField("Priorytet 1-5");
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("Koniec");
    private final Grid<LabDevice> grid = new Grid<>(LabDevice.class, false);

    public LabDeviceView(LabTestBookingService service) {
        this.service = service;
        configureForm();
        configureGrid();
        add(new HorizontalLayout(new Anchor("tests/my", "Moje wpisy"), new Anchor("me", "Security info")),
                new H2("Urzadzenia laboratoryjne"),
                category, requiredCapacity, minQuality, taskUnits, priority, startTime, endTime,
                new Button("Utworz", event -> createRecord()),
                grid);
        refresh();
    }

    private void configureForm() {
        category.setItems(TestType.values());
        category.setValue(TestType.BLOOD);
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
        grid.addColumn(LabDevice::getName).setHeader("Nazwa");
        grid.addColumn(LabDevice::getCategory).setHeader("Kategoria");
        grid.addColumn(LabDevice::getCapacity).setHeader("Pojemnosc");
        grid.addColumn(LabDevice::getQuality).setHeader("Jakosc");
        grid.addColumn(LabDevice::getUnitsPerHour).setHeader("Jedn./h");
        grid.addColumn(LabDevice::getPricePerHour).setHeader("Cena/h");
        grid.addColumn(LabDevice::isAvailable).setHeader("Dostepny");
        grid.setWidthFull();
    }

    private void createRecord() {
        try {
            LabTestBooking record = service.createRecord(category.getValue(), requiredCapacity.getValue(),
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
