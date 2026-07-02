package pl.sprzatanie.sprzatanie.views;

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
import pl.sprzatanie.sprzatanie.model.*;
import pl.sprzatanie.sprzatanie.service.CleaningOrderService;

@Route("cleaning")
@RouteAlias("")
@PageTitle("Ekipy sprzatajace")
@RolesAllowed("CLIENT")
public class CleaningTeamView extends VerticalLayout {
    private final CleaningOrderService service;
    private final ComboBox<CleaningType> category = new ComboBox<>("Kategoria");
    private final IntegerField requiredCapacity = new IntegerField("Metraz");
    private final IntegerField minQuality = new IntegerField("Minimalny standard");
    private final IntegerField taskUnits = new IntegerField("Punkty zabrudzenia");
    private final IntegerField priority = new IntegerField("Priorytet 1-5");
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("Koniec");
    private final Grid<CleaningTeam> grid = new Grid<>(CleaningTeam.class, false);

    public CleaningTeamView(CleaningOrderService service) {
        this.service = service;
        configureForm();
        configureGrid();
        add(new HorizontalLayout(new Anchor("cleaning/my", "Moje wpisy"), new Anchor("me", "Security info")),
                new H2("Ekipy sprzatajace"),
                category, requiredCapacity, minQuality, taskUnits, priority, startTime, endTime,
                new Button("Utworz", event -> createRecord()),
                grid);
        refresh();
    }

    private void configureForm() {
        category.setItems(CleaningType.values());
        category.setValue(CleaningType.OFFICE);
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
        grid.addColumn(CleaningTeam::getName).setHeader("Nazwa");
        grid.addColumn(CleaningTeam::getCategory).setHeader("Kategoria");
        grid.addColumn(CleaningTeam::getCapacity).setHeader("Pojemnosc");
        grid.addColumn(CleaningTeam::getQuality).setHeader("Jakosc");
        grid.addColumn(CleaningTeam::getUnitsPerHour).setHeader("Jedn./h");
        grid.addColumn(CleaningTeam::getPricePerHour).setHeader("Cena/h");
        grid.addColumn(CleaningTeam::isAvailable).setHeader("Dostepny");
        grid.setWidthFull();
    }

    private void createRecord() {
        try {
            CleaningOrder record = service.createRecord(category.getValue(), requiredCapacity.getValue(),
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
