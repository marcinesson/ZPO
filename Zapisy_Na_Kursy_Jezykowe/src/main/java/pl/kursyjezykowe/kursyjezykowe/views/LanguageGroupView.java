package pl.kursyjezykowe.kursyjezykowe.views;

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
import pl.kursyjezykowe.kursyjezykowe.model.*;
import pl.kursyjezykowe.kursyjezykowe.service.CourseEnrollmentService;

@Route("courses")
@RouteAlias("")
@PageTitle("Grupy jezykowe")
@RolesAllowed("CLIENT")
public class LanguageGroupView extends VerticalLayout {
    private final CourseEnrollmentService service;
    private final ComboBox<LanguageType> category = new ComboBox<>("Kategoria");
    private final IntegerField requiredCapacity = new IntegerField("Liczba miejsc");
    private final IntegerField minQuality = new IntegerField("Minimalny poziom grupy");
    private final IntegerField taskUnits = new IntegerField("Liczba lekcji");
    private final IntegerField priority = new IntegerField("Priorytet 1-5");
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("Koniec");
    private final Grid<LanguageGroup> grid = new Grid<>(LanguageGroup.class, false);

    public LanguageGroupView(CourseEnrollmentService service) {
        this.service = service;
        configureForm();
        configureGrid();
        add(new HorizontalLayout(new Anchor("courses/my", "Moje wpisy"), new Anchor("me", "Security info")),
                new H2("Grupy jezykowe"),
                category, requiredCapacity, minQuality, taskUnits, priority, startTime, endTime,
                new Button("Utworz", event -> createRecord()),
                grid);
        refresh();
    }

    private void configureForm() {
        category.setItems(LanguageType.values());
        category.setValue(LanguageType.ENGLISH);
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
        grid.addColumn(LanguageGroup::getName).setHeader("Nazwa");
        grid.addColumn(LanguageGroup::getCategory).setHeader("Kategoria");
        grid.addColumn(LanguageGroup::getCapacity).setHeader("Pojemnosc");
        grid.addColumn(LanguageGroup::getQuality).setHeader("Jakosc");
        grid.addColumn(LanguageGroup::getUnitsPerHour).setHeader("Jedn./h");
        grid.addColumn(LanguageGroup::getPricePerHour).setHeader("Cena/h");
        grid.addColumn(LanguageGroup::isAvailable).setHeader("Dostepny");
        grid.setWidthFull();
    }

    private void createRecord() {
        try {
            CourseEnrollment record = service.createRecord(category.getValue(), requiredCapacity.getValue(),
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
