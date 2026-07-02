package pl.serwiskomputerowy.serwis.views;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.serwiskomputerowy.serwis.model.RepairTicket;
import pl.serwiskomputerowy.serwis.service.RepairTicketService;

@Route("technician/tickets") @PageTitle("Panel")
@RolesAllowed({"TECHNICIAN", "ADMIN"})
public class TechnicianRepairTicketPanelView extends VerticalLayout {
    public TechnicianRepairTicketPanelView(RepairTicketService service) {
        Grid<RepairTicket> grid = new Grid<>(RepairTicket.class, false);
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("User");
        grid.addColumn(record -> record.getItem().getName()).setHeader("Item");
        grid.addColumn(RepairTicket::getStartTime).setHeader("Start");
        grid.addColumn(RepairTicket::getEndTime).setHeader("End");
        grid.setItems(service.getAllRecords());
        add(new H2("All records"), grid);
    }
}
