package pl.bowling.bowling.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.bowling.bowling.model.LaneBooking;
import pl.bowling.bowling.service.LaneBookingService;

@Route("lane-bookings/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MyLaneBookingView extends VerticalLayout {
    public MyLaneBookingView(LaneBookingService service) {
        Grid<LaneBooking> grid = new Grid<>(LaneBooking.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(LaneBooking::getRequiredCapacity).setHeader("Wymaganie");
        grid.addColumn(LaneBooking::getStartTime).setHeader("Start");
        grid.addColumn(LaneBooking::getEndTime).setHeader("Koniec");
        grid.addColumn(LaneBooking::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("lanes", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
