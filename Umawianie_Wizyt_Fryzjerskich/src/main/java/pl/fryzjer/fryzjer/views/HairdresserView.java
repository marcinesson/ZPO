package pl.fryzjer.fryzjer.views;

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
import pl.fryzjer.fryzjer.model.*;
import pl.fryzjer.fryzjer.service.HairAppointmentService;

@Route("appointments")
@RouteAlias("")
@PageTitle("Fryzjerzy")
@RolesAllowed("CLIENT")
public class HairdresserView extends VerticalLayout {
    private final HairAppointmentService service;
    private final ComboBox<HairServiceType> category = new ComboBox<>("Kategoria");
    private final IntegerField requiredCapacity = new IntegerField("Liczba klientow");
    private final IntegerField minQuality = new IntegerField("Minimalny poziom fryzjera");
    private final IntegerField taskUnits = new IntegerField("Punkty uslugi");
    private final IntegerField priority = new IntegerField("Priorytet 1-5");
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("Koniec");
    private final Grid<Hairdresser> grid = new Grid<>(Hairdresser.class, false);

    public HairdresserView(HairAppointmentService service) {
        this.service = service;
        configureForm();
        configureGrid();
        add(new HorizontalLayout(new Anchor("appointments/my", "Moje wpisy"), new Anchor("me", "Security info")),
                new H2("Fryzjerzy"),
                category, requiredCapacity, minQuality, taskUnits, priority, startTime, endTime,
                new Button("Utworz", event -> createRecord()),
                grid);
        refresh();
    }

    private void configureForm() {
        category.setItems(HairServiceType.values());
        category.setValue(HairServiceType.CUT);
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
        grid.addColumn(Hairdresser::getName).setHeader("Nazwa");
        grid.addColumn(Hairdresser::getCategory).setHeader("Kategoria");
        grid.addColumn(Hairdresser::getCapacity).setHeader("Pojemnosc");
        grid.addColumn(Hairdresser::getQuality).setHeader("Jakosc");
        grid.addColumn(Hairdresser::getUnitsPerHour).setHeader("Jedn./h");
        grid.addColumn(Hairdresser::getPricePerHour).setHeader("Cena/h");
        grid.addColumn(Hairdresser::isAvailable).setHeader("Dostepny");
        grid.setWidthFull();
    }

    private void createRecord() {
        try {
            HairAppointment record = service.createRecord(category.getValue(), requiredCapacity.getValue(),
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
