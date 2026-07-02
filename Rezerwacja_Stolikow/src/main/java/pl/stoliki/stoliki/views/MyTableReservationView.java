package pl.stoliki.stoliki.views;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.stoliki.stoliki.model.TableReservation;
import pl.stoliki.stoliki.service.TableReservationService;

@Route("reservations/my") @PageTitle("My records")
@RolesAllowed("CLIENT")
public class MyTableReservationView extends VerticalLayout {
    public MyTableReservationView(TableReservationService service) {
        Grid<TableReservation> grid = new Grid<>(TableReservation.class, false);
        grid.addColumn(record -> record.getItem().getName()).setHeader("Item");
        grid.addColumn(TableReservation::getStartTime).setHeader("Start");
        grid.addColumn(TableReservation::getEndTime).setHeader("End");
        grid.addColumn(TableReservation::getTotalPrice).setHeader("Price");
        grid.setItems(service.getMyRecords());
        add(new Anchor("tables", "Back"), new H2("My records"), grid);
    }
}
