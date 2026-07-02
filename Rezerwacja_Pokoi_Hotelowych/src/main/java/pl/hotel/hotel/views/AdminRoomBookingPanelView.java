package pl.hotel.hotel.views;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.hotel.hotel.model.RoomBooking;
import pl.hotel.hotel.service.RoomBookingService;

@Route("admin/bookings") @PageTitle("Panel")
@RolesAllowed("ADMIN")
public class AdminRoomBookingPanelView extends VerticalLayout {
    public AdminRoomBookingPanelView(RoomBookingService service) {
        Grid<RoomBooking> grid = new Grid<>(RoomBooking.class, false);
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("User");
        grid.addColumn(record -> record.getItem().getName()).setHeader("Item");
        grid.addColumn(RoomBooking::getStartTime).setHeader("Start");
        grid.addColumn(RoomBooking::getEndTime).setHeader("End");
        grid.setItems(service.getAllRecords());
        add(new H2("All records"), grid);
    }
}
