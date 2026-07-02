package pl.hotel.hotel.views;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.hotel.hotel.model.*;
import pl.hotel.hotel.service.RoomBookingService;

@Route("rooms") @RouteAlias("") @PageTitle("rooms")
@RolesAllowed("CLIENT")
public class HotelRoomView extends VerticalLayout {
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("End");

    public HotelRoomView(RoomBookingService service) {
        Grid<HotelRoom> grid = new Grid<>(HotelRoom.class, false);
        grid.addColumn(HotelRoom::getName).setHeader("Name");
        grid.addColumn(HotelRoom::getCapacity).setHeader("Capacity");
        grid.addColumn(HotelRoom::getPrice).setHeader("Price");
        grid.addColumn(HotelRoom::isAvailable).setHeader("Available");
        grid.addComponentColumn(item -> new Button("Create", event -> {
            try {
                RoomBooking record = service.createRecord(item.getId(), startTime.getValue(), endTime.getValue());
                Notification.show("Created id: " + record.getId());
            } catch (RuntimeException exception) {
                Notification.show(exception.getMessage());
            }
        }));
        grid.setItems(service.getAllItems());
        add(new Anchor("bookings/my", "My records"), new Anchor("me", "Security info"),
            new H2("rooms"), startTime, endTime, grid);
    }
}
