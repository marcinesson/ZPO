package pl.drony.drony.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.drony.drony.model.DroneReservation;
import pl.drony.drony.service.DroneReservationService;

@Route("drone-reservations/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MyDroneReservationView extends VerticalLayout {
    public MyDroneReservationView(DroneReservationService service) {
        Grid<DroneReservation> grid = new Grid<>(DroneReservation.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(DroneReservation::getRequiredCapacity).setHeader("Wymaganie");
        grid.addColumn(DroneReservation::getStartTime).setHeader("Start");
        grid.addColumn(DroneReservation::getEndTime).setHeader("Koniec");
        grid.addColumn(DroneReservation::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("drones", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
