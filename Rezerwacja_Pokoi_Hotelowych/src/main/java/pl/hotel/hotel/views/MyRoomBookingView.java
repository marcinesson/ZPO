package pl.hotel.hotel.views;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.hotel.hotel.model.RoomBooking;
import pl.hotel.hotel.service.RoomBookingService;

@Route("bookings/my") @PageTitle("My records")
@RolesAllowed("CLIENT")
public class MyRoomBookingView extends VerticalLayout {
    public MyRoomBookingView(RoomBookingService service) {
        Grid<RoomBooking> grid = new Grid<>(RoomBooking.class, false);
        grid.addColumn(record -> record.getItem().getName()).setHeader("Item");
        grid.addColumn(RoomBooking::getStartTime).setHeader("Start");
        grid.addColumn(RoomBooking::getEndTime).setHeader("End");
        grid.addColumn(RoomBooking::getTotalPrice).setHeader("Price");
        grid.setItems(service.getMyRecords());
        add(new Anchor("rooms", "Back"), new H2("My records"), grid);
    }
}
