package pl.narzedzia.narzedzia.views;

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
import pl.narzedzia.narzedzia.model.*;
import pl.narzedzia.narzedzia.service.ToolRentalService;

@Route("tools")
@RouteAlias("")
@PageTitle("Zestawy narzedzi")
@RolesAllowed("CLIENT")
public class ToolSetView extends VerticalLayout {
    private final ToolRentalService service;
    private final ComboBox<ToolType> category = new ComboBox<>("Kategoria");
    private final IntegerField requiredCapacity = new IntegerField("Liczba zestawow");
    private final IntegerField minQuality = new IntegerField("Minimalny stan narzedzi");
    private final IntegerField taskUnits = new IntegerField("Dni wypozyczenia");
    private final IntegerField priority = new IntegerField("Priorytet 1-5");
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("Koniec");
    private final Grid<ToolSet> grid = new Grid<>(ToolSet.class, false);

    public ToolSetView(ToolRentalService service) {
        this.service = service;
        configureForm();
        configureGrid();
        add(new HorizontalLayout(new Anchor("tools/my", "Moje wpisy"), new Anchor("me", "Security info")),
                new H2("Zestawy narzedzi"),
                category, requiredCapacity, minQuality, taskUnits, priority, startTime, endTime,
                new Button("Utworz", event -> createRecord()),
                grid);
        refresh();
    }

    private void configureForm() {
        category.setItems(ToolType.values());
        category.setValue(ToolType.DRILL);
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
        grid.addColumn(ToolSet::getName).setHeader("Nazwa");
        grid.addColumn(ToolSet::getCategory).setHeader("Kategoria");
        grid.addColumn(ToolSet::getCapacity).setHeader("Pojemnosc");
        grid.addColumn(ToolSet::getQuality).setHeader("Jakosc");
        grid.addColumn(ToolSet::getUnitsPerHour).setHeader("Jedn./h");
        grid.addColumn(ToolSet::getPricePerHour).setHeader("Cena/h");
        grid.addColumn(ToolSet::isAvailable).setHeader("Dostepny");
        grid.setWidthFull();
    }

    private void createRecord() {
        try {
            ToolRental record = service.createRecord(category.getValue(), requiredCapacity.getValue(),
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
