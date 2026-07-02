package pl.stroje.stroje.views;

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
import pl.stroje.stroje.model.*;
import pl.stroje.stroje.service.CostumeRentalService;

@Route("costumes")
@RouteAlias("")
@PageTitle("Stroj")
@RolesAllowed("CLIENT")
public class CostumeView extends VerticalLayout {
    private final CostumeRentalService service;
    private final ComboBox<CostumeType> category = new ComboBox<>("Kategoria");
    private final IntegerField requiredCapacity = new IntegerField("Rozmiar liczbowy");
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("Koniec");
    private final Grid<Costume> grid = new Grid<>(Costume.class, false);

    public CostumeView(CostumeRentalService service) {
        this.service = service;
        configureForm();
        configureGrid();
        add(new HorizontalLayout(new Anchor("costume-rentals/my", "Moje wpisy"), new Anchor("me", "Security info")),
                new H2("Stroj"),
                category, requiredCapacity, startTime, endTime,
                new Button("Utworz", event -> createRecord()),
                grid);
        refresh();
    }

    private void configureForm() {
        category.setItems(CostumeType.values());
        category.setValue(CostumeType.HISTORICAL);
        requiredCapacity.setMin(1);
        requiredCapacity.setValue(1);
        startTime.setValue(LocalDateTime.now().plusDays(1));
        endTime.setValue(LocalDateTime.now().plusDays(1).plusHours(2));
    }

    private void configureGrid() {
        grid.addColumn(Costume::getName).setHeader("Nazwa");
        grid.addColumn(Costume::getCategory).setHeader("Kategoria");
        grid.addColumn(Costume::getCapacity).setHeader("Pojemnosc");
        grid.addColumn(Costume::getPrice).setHeader("Cena/h");
        grid.addColumn(Costume::isAvailable).setHeader("Dostepny");
        grid.setWidthFull();
    }

    private void createRecord() {
        try {
            CostumeRental record = service.createRecord(category.getValue(), requiredCapacity.getValue(),
                    startTime.getValue(), endTime.getValue());
            Notification.show("Utworzono id: " + record.getId() + ", koszt: " + record.getTotalPrice());
            refresh();
        } catch (RuntimeException exception) {
            Notification.show(exception.getMessage());
        }
    }

    private void refresh() {
        grid.setItems(service.getAllResources());
    }
}
