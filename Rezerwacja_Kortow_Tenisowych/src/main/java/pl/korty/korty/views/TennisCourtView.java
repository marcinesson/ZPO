package pl.korty.korty.views;

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
import pl.korty.korty.model.*;
import pl.korty.korty.service.TennisCourtReservationService;

@Route("courts")
@RouteAlias("")
@PageTitle("Korty tenisowe")
@RolesAllowed("CLIENT")
public class TennisCourtView extends VerticalLayout {
    private final TennisCourtReservationService service;
    private final ComboBox<CourtSurface> category = new ComboBox<>("Kategoria");
    private final IntegerField requiredCapacity = new IntegerField("Liczba graczy");
    private final IntegerField minQuality = new IntegerField("Minimalny standard kortu");
    private final IntegerField taskUnits = new IntegerField("Godziny gry");
    private final IntegerField priority = new IntegerField("Priorytet 1-5");
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("Koniec");
    private final Grid<TennisCourt> grid = new Grid<>(TennisCourt.class, false);

    public TennisCourtView(TennisCourtReservationService service) {
        this.service = service;
        configureForm();
        configureGrid();
        add(new HorizontalLayout(new Anchor("courts/my", "Moje wpisy"), new Anchor("me", "Security info")),
                new H2("Korty tenisowe"),
                category, requiredCapacity, minQuality, taskUnits, priority, startTime, endTime,
                new Button("Utworz", event -> createRecord()),
                grid);
        refresh();
    }

    private void configureForm() {
        category.setItems(CourtSurface.values());
        category.setValue(CourtSurface.CLAY);
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
        grid.addColumn(TennisCourt::getName).setHeader("Nazwa");
        grid.addColumn(TennisCourt::getCategory).setHeader("Kategoria");
        grid.addColumn(TennisCourt::getCapacity).setHeader("Pojemnosc");
        grid.addColumn(TennisCourt::getQuality).setHeader("Jakosc");
        grid.addColumn(TennisCourt::getUnitsPerHour).setHeader("Jedn./h");
        grid.addColumn(TennisCourt::getPricePerHour).setHeader("Cena/h");
        grid.addColumn(TennisCourt::isAvailable).setHeader("Dostepny");
        grid.setWidthFull();
    }

    private void createRecord() {
        try {
            TennisCourtReservation record = service.createRecord(category.getValue(), requiredCapacity.getValue(),
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
