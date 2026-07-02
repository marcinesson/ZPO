package pl.plywanie.plywanie.views;

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
import pl.plywanie.plywanie.model.*;
import pl.plywanie.plywanie.service.SwimmingEnrollmentService;

@Route("swimming")
@RouteAlias("")
@PageTitle("Grupy plywackie")
@RolesAllowed("CLIENT")
public class SwimmingGroupView extends VerticalLayout {
    private final SwimmingEnrollmentService service;
    private final ComboBox<SwimmingLevel> category = new ComboBox<>("Kategoria");
    private final IntegerField requiredCapacity = new IntegerField("Liczba miejsc");
    private final IntegerField minQuality = new IntegerField("Minimalny poziom instruktora");
    private final IntegerField taskUnits = new IntegerField("Liczba zajec");
    private final IntegerField priority = new IntegerField("Priorytet 1-5");
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("Koniec");
    private final Grid<SwimmingGroup> grid = new Grid<>(SwimmingGroup.class, false);

    public SwimmingGroupView(SwimmingEnrollmentService service) {
        this.service = service;
        configureForm();
        configureGrid();
        add(new HorizontalLayout(new Anchor("swimming/my", "Moje wpisy"), new Anchor("me", "Security info")),
                new H2("Grupy plywackie"),
                category, requiredCapacity, minQuality, taskUnits, priority, startTime, endTime,
                new Button("Utworz", event -> createRecord()),
                grid);
        refresh();
    }

    private void configureForm() {
        category.setItems(SwimmingLevel.values());
        category.setValue(SwimmingLevel.BEGINNER);
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
        grid.addColumn(SwimmingGroup::getName).setHeader("Nazwa");
        grid.addColumn(SwimmingGroup::getCategory).setHeader("Kategoria");
        grid.addColumn(SwimmingGroup::getCapacity).setHeader("Pojemnosc");
        grid.addColumn(SwimmingGroup::getQuality).setHeader("Jakosc");
        grid.addColumn(SwimmingGroup::getUnitsPerHour).setHeader("Jedn./h");
        grid.addColumn(SwimmingGroup::getPricePerHour).setHeader("Cena/h");
        grid.addColumn(SwimmingGroup::isAvailable).setHeader("Dostepny");
        grid.setWidthFull();
    }

    private void createRecord() {
        try {
            SwimmingEnrollment record = service.createRecord(category.getValue(), requiredCapacity.getValue(),
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
