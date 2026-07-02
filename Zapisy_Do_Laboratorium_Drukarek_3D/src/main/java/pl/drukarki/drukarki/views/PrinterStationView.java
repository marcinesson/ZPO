package pl.drukarki.drukarki.views;

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
import pl.drukarki.drukarki.model.*;
import pl.drukarki.drukarki.service.PrintJobService;

@Route("prints")
@RouteAlias("")
@PageTitle("Stanowisko drukarki")
@RolesAllowed("CLIENT")
public class PrinterStationView extends VerticalLayout {
    private final PrintJobService service;
    private final ComboBox<MaterialType> category = new ComboBox<>("Kategoria");
    private final IntegerField requiredCapacity = new IntegerField("Minimalna wielkosc wydruku");
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("Koniec");
    private final Grid<PrinterStation> grid = new Grid<>(PrinterStation.class, false);

    public PrinterStationView(PrintJobService service) {
        this.service = service;
        configureForm();
        configureGrid();
        add(new HorizontalLayout(new Anchor("print-jobs/my", "Moje wpisy"), new Anchor("me", "Security info")),
                new H2("Stanowisko drukarki"),
                category, requiredCapacity, startTime, endTime,
                new Button("Utworz", event -> createRecord()),
                grid);
        refresh();
    }

    private void configureForm() {
        category.setItems(MaterialType.values());
        category.setValue(MaterialType.PLA);
        requiredCapacity.setMin(1);
        requiredCapacity.setValue(1);
        startTime.setValue(LocalDateTime.now().plusDays(1));
        endTime.setValue(LocalDateTime.now().plusDays(1).plusHours(2));
    }

    private void configureGrid() {
        grid.addColumn(PrinterStation::getName).setHeader("Nazwa");
        grid.addColumn(PrinterStation::getCategory).setHeader("Kategoria");
        grid.addColumn(PrinterStation::getCapacity).setHeader("Pojemnosc");
        grid.addColumn(PrinterStation::getPrice).setHeader("Cena/h");
        grid.addColumn(PrinterStation::isAvailable).setHeader("Dostepny");
        grid.setWidthFull();
    }

    private void createRecord() {
        try {
            PrintJob record = service.createRecord(category.getValue(), requiredCapacity.getValue(),
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
