package pl.serwiskomputerowy.serwis.views;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.serwiskomputerowy.serwis.model.*;
import pl.serwiskomputerowy.serwis.service.RepairTicketService;

@Route("tickets") @RouteAlias("") @PageTitle("tickets")
@RolesAllowed("CLIENT")
public class TechnicianSlotView extends VerticalLayout {
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("End");

    public TechnicianSlotView(RepairTicketService service) {
        Grid<TechnicianSlot> grid = new Grid<>(TechnicianSlot.class, false);
        grid.addColumn(TechnicianSlot::getName).setHeader("Name");
        grid.addColumn(TechnicianSlot::getCapacity).setHeader("Capacity");
        grid.addColumn(TechnicianSlot::getPrice).setHeader("Price");
        grid.addColumn(TechnicianSlot::isAvailable).setHeader("Available");
        grid.addComponentColumn(item -> new Button("Create", event -> {
            try {
                RepairTicket record = service.createRecord(item.getId(), startTime.getValue(), endTime.getValue());
                Notification.show("Created id: " + record.getId());
            } catch (RuntimeException exception) {
                Notification.show(exception.getMessage());
            }
        }));
        grid.setItems(service.getAllItems());
        add(new Anchor("tickets/my", "My records"), new Anchor("me", "Security info"),
            new H2("tickets"), startTime, endTime, grid);
    }
}
