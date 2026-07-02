package pl.drukarki.drukarki.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.drukarki.drukarki.model.PrintJob;
import pl.drukarki.drukarki.service.PrintJobService;

@Route("print-jobs/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MyPrintJobView extends VerticalLayout {
    public MyPrintJobView(PrintJobService service) {
        Grid<PrintJob> grid = new Grid<>(PrintJob.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(PrintJob::getRequiredCapacity).setHeader("Wymaganie");
        grid.addColumn(PrintJob::getStartTime).setHeader("Start");
        grid.addColumn(PrintJob::getEndTime).setHeader("Koniec");
        grid.addColumn(PrintJob::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("prints", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
