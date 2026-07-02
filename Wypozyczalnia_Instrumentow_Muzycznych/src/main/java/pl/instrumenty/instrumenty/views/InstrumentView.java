package pl.instrumenty.instrumenty.views;

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
import pl.instrumenty.instrumenty.model.*;
import pl.instrumenty.instrumenty.service.InstrumentRentalService;

@Route("instruments")
@RouteAlias("")
@PageTitle("Instrument")
@RolesAllowed("CLIENT")
public class InstrumentView extends VerticalLayout {
    private final InstrumentRentalService service;
    private final ComboBox<InstrumentType> category = new ComboBox<>("Kategoria");
    private final IntegerField requiredCapacity = new IntegerField("Wymagany poziom sprzetu");
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("Koniec");
    private final Grid<Instrument> grid = new Grid<>(Instrument.class, false);

    public InstrumentView(InstrumentRentalService service) {
        this.service = service;
        configureForm();
        configureGrid();
        add(new HorizontalLayout(new Anchor("instrument-rentals/my", "Moje wpisy"), new Anchor("me", "Security info")),
                new H2("Instrument"),
                category, requiredCapacity, startTime, endTime,
                new Button("Utworz", event -> createRecord()),
                grid);
        refresh();
    }

    private void configureForm() {
        category.setItems(InstrumentType.values());
        category.setValue(InstrumentType.GUITAR);
        requiredCapacity.setMin(1);
        requiredCapacity.setValue(1);
        startTime.setValue(LocalDateTime.now().plusDays(1));
        endTime.setValue(LocalDateTime.now().plusDays(1).plusHours(2));
    }

    private void configureGrid() {
        grid.addColumn(Instrument::getName).setHeader("Nazwa");
        grid.addColumn(Instrument::getCategory).setHeader("Kategoria");
        grid.addColumn(Instrument::getCapacity).setHeader("Pojemnosc");
        grid.addColumn(Instrument::getPrice).setHeader("Cena/h");
        grid.addColumn(Instrument::isAvailable).setHeader("Dostepny");
        grid.setWidthFull();
    }

    private void createRecord() {
        try {
            InstrumentRental record = service.createRecord(category.getValue(), requiredCapacity.getValue(),
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
