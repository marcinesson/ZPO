package pl.korepetycje.korepetycje.views;

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
import pl.korepetycje.korepetycje.model.*;
import pl.korepetycje.korepetycje.service.TutoringBookingService;

@Route("tutoring")
@RouteAlias("")
@PageTitle("Korepetytorzy")
@RolesAllowed("CLIENT")
public class TutorSlotView extends VerticalLayout {
    private final TutoringBookingService service;
    private final ComboBox<SubjectType> category = new ComboBox<>("Kategoria");
    private final IntegerField requiredCapacity = new IntegerField("Liczba uczniow");
    private final IntegerField minQuality = new IntegerField("Minimalny poziom nauczyciela");
    private final IntegerField taskUnits = new IntegerField("Liczba tematow");
    private final IntegerField priority = new IntegerField("Priorytet 1-5");
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("Koniec");
    private final Grid<TutorSlot> grid = new Grid<>(TutorSlot.class, false);

    public TutorSlotView(TutoringBookingService service) {
        this.service = service;
        configureForm();
        configureGrid();
        add(new HorizontalLayout(new Anchor("tutoring/my", "Moje wpisy"), new Anchor("me", "Security info")),
                new H2("Korepetytorzy"),
                category, requiredCapacity, minQuality, taskUnits, priority, startTime, endTime,
                new Button("Utworz", event -> createRecord()),
                grid);
        refresh();
    }

    private void configureForm() {
        category.setItems(SubjectType.values());
        category.setValue(SubjectType.MATH);
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
        grid.addColumn(TutorSlot::getName).setHeader("Nazwa");
        grid.addColumn(TutorSlot::getCategory).setHeader("Kategoria");
        grid.addColumn(TutorSlot::getCapacity).setHeader("Pojemnosc");
        grid.addColumn(TutorSlot::getQuality).setHeader("Jakosc");
        grid.addColumn(TutorSlot::getUnitsPerHour).setHeader("Jedn./h");
        grid.addColumn(TutorSlot::getPricePerHour).setHeader("Cena/h");
        grid.addColumn(TutorSlot::isAvailable).setHeader("Dostepny");
        grid.setWidthFull();
    }

    private void createRecord() {
        try {
            TutoringBooking record = service.createRecord(category.getValue(), requiredCapacity.getValue(),
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
