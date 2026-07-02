package pl.serwiskomputerowy.serwis.views;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.serwiskomputerowy.serwis.model.RepairTicket;
import pl.serwiskomputerowy.serwis.service.RepairTicketService;

@Route("tickets/my") @PageTitle("My records")
@RolesAllowed("CLIENT")
public class MyRepairTicketView extends VerticalLayout {
    public MyRepairTicketView(RepairTicketService service) {
        Grid<RepairTicket> grid = new Grid<>(RepairTicket.class, false);
        grid.addColumn(record -> record.getItem().getName()).setHeader("Item");
        grid.addColumn(RepairTicket::getStartTime).setHeader("Start");
        grid.addColumn(RepairTicket::getEndTime).setHeader("End");
        grid.addColumn(RepairTicket::getTotalPrice).setHeader("Price");
        grid.setItems(service.getMyRecords());
        add(new Anchor("tickets", "Back"), new H2("My records"), grid);
    }
}
