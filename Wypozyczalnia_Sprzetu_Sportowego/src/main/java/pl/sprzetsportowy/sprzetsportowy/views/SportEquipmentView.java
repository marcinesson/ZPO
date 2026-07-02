package pl.sprzetsportowy.sprzetsportowy.views;

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
import pl.sprzetsportowy.sprzetsportowy.model.*;
import pl.sprzetsportowy.sprzetsportowy.service.EquipmentRentalService;

@Route("equipment")
@RouteAlias("")
@PageTitle("Sprzet sportowy")
@RolesAllowed("CLIENT")
public class SportEquipmentView extends VerticalLayout {
    private final EquipmentRentalService service;
    private final ComboBox<SportType> category = new ComboBox<>("Kategoria");
    private final IntegerField requiredCapacity = new IntegerField("Rozmiar/liczba osob");
    private final IntegerField minQuality = new IntegerField("Minimalny stan sprzetu");
    private final IntegerField taskUnits = new IntegerField("Liczba dni wypozyczenia");
    private final IntegerField priority = new IntegerField("Priorytet 1-5");
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("Koniec");
    private final Grid<SportEquipment> grid = new Grid<>(SportEquipment.class, false);

    public SportEquipmentView(EquipmentRentalService service) {
        this.service = service;
        configureForm();
        configureGrid();
        add(new HorizontalLayout(new Anchor("equipment/my", "Moje wpisy"), new Anchor("me", "Security info")),
                new H2("Sprzet sportowy"),
                category, requiredCapacity, minQuality, taskUnits, priority, startTime, endTime,
                new Button("Utworz", event -> createRecord()),
                grid);
        refresh();
    }

    private void configureForm() {
        category.setItems(SportType.values());
        category.setValue(SportType.SKI);
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
        grid.addColumn(SportEquipment::getName).setHeader("Nazwa");
        grid.addColumn(SportEquipment::getCategory).setHeader("Kategoria");
        grid.addColumn(SportEquipment::getCapacity).setHeader("Pojemnosc");
        grid.addColumn(SportEquipment::getQuality).setHeader("Jakosc");
        grid.addColumn(SportEquipment::getUnitsPerHour).setHeader("Jedn./h");
        grid.addColumn(SportEquipment::getPricePerHour).setHeader("Cena/h");
        grid.addColumn(SportEquipment::isAvailable).setHeader("Dostepny");
        grid.setWidthFull();
    }

    private void createRecord() {
        try {
            EquipmentRental record = service.createRecord(category.getValue(), requiredCapacity.getValue(),
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
