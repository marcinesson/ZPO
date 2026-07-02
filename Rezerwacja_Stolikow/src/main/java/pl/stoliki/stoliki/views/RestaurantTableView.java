package pl.stoliki.stoliki.views;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.stoliki.stoliki.model.*;
import pl.stoliki.stoliki.service.TableReservationService;

@Route("tables") @RouteAlias("") @PageTitle("tables")
@RolesAllowed("CLIENT")
public class RestaurantTableView extends VerticalLayout {
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("End");

    public RestaurantTableView(TableReservationService service) {
        Grid<RestaurantTable> grid = new Grid<>(RestaurantTable.class, false);
        grid.addColumn(RestaurantTable::getName).setHeader("Name");
        grid.addColumn(RestaurantTable::getCapacity).setHeader("Capacity");
        grid.addColumn(RestaurantTable::getPrice).setHeader("Price");
        grid.addColumn(RestaurantTable::isAvailable).setHeader("Available");
        grid.addComponentColumn(item -> new Button("Create", event -> {
            try {
                TableReservation record = service.createRecord(item.getId(), startTime.getValue(), endTime.getValue());
                Notification.show("Created id: " + record.getId());
            } catch (RuntimeException exception) {
                Notification.show(exception.getMessage());
            }
        }));
        grid.setItems(service.getAllItems());
        add(new Anchor("reservations/my", "My records"), new Anchor("me", "Security info"),
            new H2("tables"), startTime, endTime, grid);
    }
}
