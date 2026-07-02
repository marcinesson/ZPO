package pl.opieka.opieka.views;

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
import pl.opieka.opieka.model.*;
import pl.opieka.opieka.service.CareBookingService;

@Route("care")
@RouteAlias("")
@PageTitle("Opiekunowie")
@RolesAllowed("CLIENT")
public class BabysitterView extends VerticalLayout {
    private final CareBookingService service;
    private final ComboBox<CareType> category = new ComboBox<>("Kategoria");
    private final IntegerField requiredCapacity = new IntegerField("Liczba dzieci");
    private final IntegerField minQuality = new IntegerField("Minimalne doswiadczenie");
    private final IntegerField taskUnits = new IntegerField("Punkty opieki");
    private final IntegerField priority = new IntegerField("Priorytet 1-5");
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("Koniec");
    private final Grid<Babysitter> grid = new Grid<>(Babysitter.class, false);

    public BabysitterView(CareBookingService service) {
        this.service = service;
        configureForm();
        configureGrid();
        add(new HorizontalLayout(new Anchor("care/my", "Moje wpisy"), new Anchor("me", "Security info")),
                new H2("Opiekunowie"),
                category, requiredCapacity, minQuality, taskUnits, priority, startTime, endTime,
                new Button("Utworz", event -> createRecord()),
                grid);
        refresh();
    }

    private void configureForm() {
        category.setItems(CareType.values());
        category.setValue(CareType.EVENING);
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
        grid.addColumn(Babysitter::getName).setHeader("Nazwa");
        grid.addColumn(Babysitter::getCategory).setHeader("Kategoria");
        grid.addColumn(Babysitter::getCapacity).setHeader("Pojemnosc");
        grid.addColumn(Babysitter::getQuality).setHeader("Jakosc");
        grid.addColumn(Babysitter::getUnitsPerHour).setHeader("Jedn./h");
        grid.addColumn(Babysitter::getPricePerHour).setHeader("Cena/h");
        grid.addColumn(Babysitter::isAvailable).setHeader("Dostepny");
        grid.setWidthFull();
    }

    private void createRecord() {
        try {
            CareBooking record = service.createRecord(category.getValue(), requiredCapacity.getValue(),
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
