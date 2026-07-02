package pl.symulatory.symulatory.views;

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
import pl.symulatory.symulatory.model.*;
import pl.symulatory.symulatory.service.VrSessionService;

@Route("simulators")
@RouteAlias("")
@PageTitle("Symulatory VR")
@RolesAllowed("CLIENT")
public class VrSimulatorView extends VerticalLayout {
    private final VrSessionService service;
    private final ComboBox<VrScenario> category = new ComboBox<>("Kategoria");
    private final IntegerField requiredCapacity = new IntegerField("Liczba osob");
    private final IntegerField minQuality = new IntegerField("Minimalny realizm");
    private final IntegerField taskUnits = new IntegerField("Scenariusze do przejscia");
    private final IntegerField priority = new IntegerField("Priorytet 1-5");
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("Koniec");
    private final Grid<VrSimulator> grid = new Grid<>(VrSimulator.class, false);

    public VrSimulatorView(VrSessionService service) {
        this.service = service;
        configureForm();
        configureGrid();
        add(new HorizontalLayout(new Anchor("vr-sessions/my", "Moje wpisy"), new Anchor("me", "Security info")),
                new H2("Symulatory VR"),
                category, requiredCapacity, minQuality, taskUnits, priority, startTime, endTime,
                new Button("Utworz", event -> createRecord()),
                grid);
        refresh();
    }

    private void configureForm() {
        category.setItems(VrScenario.values());
        category.setValue(VrScenario.RACING);
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
        grid.addColumn(VrSimulator::getName).setHeader("Nazwa");
        grid.addColumn(VrSimulator::getCategory).setHeader("Kategoria");
        grid.addColumn(VrSimulator::getCapacity).setHeader("Pojemnosc");
        grid.addColumn(VrSimulator::getQuality).setHeader("Jakosc");
        grid.addColumn(VrSimulator::getUnitsPerHour).setHeader("Jedn./h");
        grid.addColumn(VrSimulator::getPricePerHour).setHeader("Cena/h");
        grid.addColumn(VrSimulator::isAvailable).setHeader("Dostepny");
        grid.setWidthFull();
    }

    private void createRecord() {
        try {
            VrSession record = service.createRecord(category.getValue(), requiredCapacity.getValue(),
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
