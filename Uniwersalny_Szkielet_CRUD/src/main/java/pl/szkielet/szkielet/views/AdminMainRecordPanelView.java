package pl.szkielet.szkielet.views;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.szkielet.szkielet.model.MainRecord;
import pl.szkielet.szkielet.service.MainRecordService;

@Route("admin/records") @PageTitle("Panel")
@RolesAllowed("ADMIN")
public class AdminMainRecordPanelView extends VerticalLayout {
    public AdminMainRecordPanelView(MainRecordService service) {
        Grid<MainRecord> grid = new Grid<>(MainRecord.class, false);
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("User");
        grid.addColumn(record -> record.getItem().getName()).setHeader("Item");
        grid.addColumn(MainRecord::getStartTime).setHeader("Start");
        grid.addColumn(MainRecord::getEndTime).setHeader("End");
        grid.setItems(service.getAllRecords());
        add(new H2("All records"), grid);
    }
}
