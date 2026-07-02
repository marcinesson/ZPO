package pl.stoliki.stoliki.views;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.stoliki.stoliki.model.TableReservation;
import pl.stoliki.stoliki.service.TableReservationService;

@Route("admin/reservations") @PageTitle("Panel")
@RolesAllowed("ADMIN")
public class AdminTableReservationPanelView extends VerticalLayout {
    public AdminTableReservationPanelView(TableReservationService service) {
        Grid<TableReservation> grid = new Grid<>(TableReservation.class, false);
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("User");
        grid.addColumn(record -> record.getItem().getName()).setHeader("Item");
        grid.addColumn(TableReservation::getStartTime).setHeader("Start");
        grid.addColumn(TableReservation::getEndTime).setHeader("End");
        grid.setItems(service.getAllRecords());
        add(new H2("All records"), grid);
    }
}
