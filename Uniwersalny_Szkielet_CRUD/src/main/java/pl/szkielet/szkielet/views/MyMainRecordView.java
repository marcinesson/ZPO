package pl.szkielet.szkielet.views;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.szkielet.szkielet.model.MainRecord;
import pl.szkielet.szkielet.service.MainRecordService;

@Route("records/my") @PageTitle("My records")
@RolesAllowed("CLIENT")
public class MyMainRecordView extends VerticalLayout {
    public MyMainRecordView(MainRecordService service) {
        Grid<MainRecord> grid = new Grid<>(MainRecord.class, false);
        grid.addColumn(record -> record.getItem().getName()).setHeader("Item");
        grid.addColumn(MainRecord::getStartTime).setHeader("Start");
        grid.addColumn(MainRecord::getEndTime).setHeader("End");
        grid.addColumn(MainRecord::getTotalPrice).setHeader("Price");
        grid.setItems(service.getMyRecords());
        add(new Anchor("items", "Back"), new H2("My records"), grid);
    }
}
