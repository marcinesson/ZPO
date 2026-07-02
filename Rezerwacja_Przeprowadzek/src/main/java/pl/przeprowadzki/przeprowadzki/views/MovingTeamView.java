package pl.przeprowadzki.przeprowadzki.views;

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
import pl.przeprowadzki.przeprowadzki.model.*;
import pl.przeprowadzki.przeprowadzki.service.MovingOrderService;

@Route("moving")
@RouteAlias("")
@PageTitle("Ekipy przeprowadzkowe")
@RolesAllowed("CLIENT")
public class MovingTeamView extends VerticalLayout {
    private final MovingOrderService service;
    private final ComboBox<MoveType> category = new ComboBox<>("Kategoria");
    private final IntegerField requiredCapacity = new IntegerField("Liczba kartonow");
    private final IntegerField minQuality = new IntegerField("Minimalny poziom ekipy");
    private final IntegerField taskUnits = new IntegerField("Punkty przeprowadzki");
    private final IntegerField priority = new IntegerField("Priorytet 1-5");
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("Koniec");
    private final Grid<MovingTeam> grid = new Grid<>(MovingTeam.class, false);

    public MovingTeamView(MovingOrderService service) {
        this.service = service;
        configureForm();
        configureGrid();
        add(new HorizontalLayout(new Anchor("moving/my", "Moje wpisy"), new Anchor("me", "Security info")),
                new H2("Ekipy przeprowadzkowe"),
                category, requiredCapacity, minQuality, taskUnits, priority, startTime, endTime,
                new Button("Utworz", event -> createRecord()),
                grid);
        refresh();
    }

    private void configureForm() {
        category.setItems(MoveType.values());
        category.setValue(MoveType.SMALL);
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
        grid.addColumn(MovingTeam::getName).setHeader("Nazwa");
        grid.addColumn(MovingTeam::getCategory).setHeader("Kategoria");
        grid.addColumn(MovingTeam::getCapacity).setHeader("Pojemnosc");
        grid.addColumn(MovingTeam::getQuality).setHeader("Jakosc");
        grid.addColumn(MovingTeam::getUnitsPerHour).setHeader("Jedn./h");
        grid.addColumn(MovingTeam::getPricePerHour).setHeader("Cena/h");
        grid.addColumn(MovingTeam::isAvailable).setHeader("Dostepny");
        grid.setWidthFull();
    }

    private void createRecord() {
        try {
            MovingOrder record = service.createRecord(category.getValue(), requiredCapacity.getValue(),
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
