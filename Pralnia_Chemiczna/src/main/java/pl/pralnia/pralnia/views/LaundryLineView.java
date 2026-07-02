package pl.pralnia.pralnia.views;

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
import pl.pralnia.pralnia.model.*;
import pl.pralnia.pralnia.service.LaundryOrderService;

@Route("laundry")
@RouteAlias("")
@PageTitle("Linie pralnicze")
@RolesAllowed("CLIENT")
public class LaundryLineView extends VerticalLayout {
    private final LaundryOrderService service;
    private final ComboBox<LaundryType> category = new ComboBox<>("Kategoria");
    private final IntegerField requiredCapacity = new IntegerField("Liczba rzeczy");
    private final IntegerField minQuality = new IntegerField("Minimalny standard czyszczenia");
    private final IntegerField taskUnits = new IntegerField("Punkty prania");
    private final IntegerField priority = new IntegerField("Priorytet 1-5");
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("Koniec");
    private final Grid<LaundryLine> grid = new Grid<>(LaundryLine.class, false);

    public LaundryLineView(LaundryOrderService service) {
        this.service = service;
        configureForm();
        configureGrid();
        add(new HorizontalLayout(new Anchor("laundry/my", "Moje wpisy"), new Anchor("me", "Security info")),
                new H2("Linie pralnicze"),
                category, requiredCapacity, minQuality, taskUnits, priority, startTime, endTime,
                new Button("Utworz", event -> createRecord()),
                grid);
        refresh();
    }

    private void configureForm() {
        category.setItems(LaundryType.values());
        category.setValue(LaundryType.SHIRTS);
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
        grid.addColumn(LaundryLine::getName).setHeader("Nazwa");
        grid.addColumn(LaundryLine::getCategory).setHeader("Kategoria");
        grid.addColumn(LaundryLine::getCapacity).setHeader("Pojemnosc");
        grid.addColumn(LaundryLine::getQuality).setHeader("Jakosc");
        grid.addColumn(LaundryLine::getUnitsPerHour).setHeader("Jedn./h");
        grid.addColumn(LaundryLine::getPricePerHour).setHeader("Cena/h");
        grid.addColumn(LaundryLine::isAvailable).setHeader("Dostepny");
        grid.setWidthFull();
    }

    private void createRecord() {
        try {
            LaundryOrder record = service.createRecord(category.getValue(), requiredCapacity.getValue(),
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
