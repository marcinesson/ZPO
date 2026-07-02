package pl.catering.catering.views;

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
import pl.catering.catering.model.*;
import pl.catering.catering.service.CateringOrderService;

@Route("catering")
@RouteAlias("")
@PageTitle("Ekipy cateringowe")
@RolesAllowed("CLIENT")
public class CateringTeamView extends VerticalLayout {
    private final CateringOrderService service;
    private final ComboBox<DietType> category = new ComboBox<>("Kategoria");
    private final IntegerField requiredCapacity = new IntegerField("Liczba gosci");
    private final IntegerField minQuality = new IntegerField("Minimalny standard");
    private final IntegerField taskUnits = new IntegerField("Liczba dan/porcji");
    private final IntegerField priority = new IntegerField("Priorytet 1-5");
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("Koniec");
    private final Grid<CateringTeam> grid = new Grid<>(CateringTeam.class, false);

    public CateringTeamView(CateringOrderService service) {
        this.service = service;
        configureForm();
        configureGrid();
        add(new HorizontalLayout(new Anchor("catering/my", "Moje wpisy"), new Anchor("me", "Security info")),
                new H2("Ekipy cateringowe"),
                category, requiredCapacity, minQuality, taskUnits, priority, startTime, endTime,
                new Button("Utworz", event -> createRecord()),
                grid);
        refresh();
    }

    private void configureForm() {
        category.setItems(DietType.values());
        category.setValue(DietType.STANDARD);
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
        grid.addColumn(CateringTeam::getName).setHeader("Nazwa");
        grid.addColumn(CateringTeam::getCategory).setHeader("Kategoria");
        grid.addColumn(CateringTeam::getCapacity).setHeader("Pojemnosc");
        grid.addColumn(CateringTeam::getQuality).setHeader("Jakosc");
        grid.addColumn(CateringTeam::getUnitsPerHour).setHeader("Jedn./h");
        grid.addColumn(CateringTeam::getPricePerHour).setHeader("Cena/h");
        grid.addColumn(CateringTeam::isAvailable).setHeader("Dostepny");
        grid.setWidthFull();
    }

    private void createRecord() {
        try {
            CateringOrder record = service.createRecord(category.getValue(), requiredCapacity.getValue(),
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
