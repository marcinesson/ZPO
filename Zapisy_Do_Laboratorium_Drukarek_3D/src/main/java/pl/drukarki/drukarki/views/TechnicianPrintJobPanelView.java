package pl.drukarki.drukarki.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.drukarki.drukarki.model.PrintJob;
import pl.drukarki.drukarki.service.PrintJobService;

@Route("technician/prints")
@PageTitle("Panel")
@RolesAllowed("TECHNICIAN")
public class TechnicianPrintJobPanelView extends VerticalLayout {
    private final PrintJobService service;
    private final Grid<PrintJob> grid = new Grid<>(PrintJob.class, false);

    public TechnicianPrintJobPanelView(PrintJobService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(PrintJob::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(PrintJob::getStartTime).setHeader("Start");
        grid.addColumn(PrintJob::getEndTime).setHeader("Koniec");
        grid.addColumn(PrintJob::getTotalPrice).setHeader("Koszt");
        grid.addComponentColumn(record -> new Button("Usun", event -> {
            service.cancelRecord(record.getId());
            refresh();
        }));
        grid.setWidthFull();
    }

    private void refresh() {
        grid.setItems(service.getAllRecords());
    }
}
