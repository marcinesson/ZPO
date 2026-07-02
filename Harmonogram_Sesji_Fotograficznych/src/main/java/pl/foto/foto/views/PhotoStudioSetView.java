package pl.foto.foto.views;

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
import pl.foto.foto.model.*;
import pl.foto.foto.service.PhotoSessionService;

@Route("photos")
@RouteAlias("")
@PageTitle("Zestawy studyjne")
@RolesAllowed("CLIENT")
public class PhotoStudioSetView extends VerticalLayout {
    private final PhotoSessionService service;
    private final ComboBox<SessionType> category = new ComboBox<>("Kategoria");
    private final IntegerField requiredCapacity = new IntegerField("Liczba osob/produktow");
    private final IntegerField minQuality = new IntegerField("Minimalna jakosc lamp");
    private final IntegerField taskUnits = new IntegerField("Liczba ujec");
    private final IntegerField priority = new IntegerField("Priorytet 1-5");
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("Koniec");
    private final Grid<PhotoStudioSet> grid = new Grid<>(PhotoStudioSet.class, false);

    public PhotoStudioSetView(PhotoSessionService service) {
        this.service = service;
        configureForm();
        configureGrid();
        add(new HorizontalLayout(new Anchor("photos/my", "Moje wpisy"), new Anchor("me", "Security info")),
                new H2("Zestawy studyjne"),
                category, requiredCapacity, minQuality, taskUnits, priority, startTime, endTime,
                new Button("Utworz", event -> createRecord()),
                grid);
        refresh();
    }

    private void configureForm() {
        category.setItems(SessionType.values());
        category.setValue(SessionType.PRODUCT);
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
        grid.addColumn(PhotoStudioSet::getName).setHeader("Nazwa");
        grid.addColumn(PhotoStudioSet::getCategory).setHeader("Kategoria");
        grid.addColumn(PhotoStudioSet::getCapacity).setHeader("Pojemnosc");
        grid.addColumn(PhotoStudioSet::getQuality).setHeader("Jakosc");
        grid.addColumn(PhotoStudioSet::getUnitsPerHour).setHeader("Jedn./h");
        grid.addColumn(PhotoStudioSet::getPricePerHour).setHeader("Cena/h");
        grid.addColumn(PhotoStudioSet::isAvailable).setHeader("Dostepny");
        grid.setWidthFull();
    }

    private void createRecord() {
        try {
            PhotoSession record = service.createRecord(category.getValue(), requiredCapacity.getValue(),
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
