package pl.fitness.fitness.views;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.fitness.fitness.model.ClassEnrollment;
import pl.fitness.fitness.service.ClassEnrollmentService;

@Route("trainer/classes") @PageTitle("Panel")
@RolesAllowed({"TRAINER", "ADMIN"})
public class TrainerClassEnrollmentPanelView extends VerticalLayout {
    public TrainerClassEnrollmentPanelView(ClassEnrollmentService service) {
        Grid<ClassEnrollment> grid = new Grid<>(ClassEnrollment.class, false);
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("User");
        grid.addColumn(record -> record.getItem().getName()).setHeader("Item");
        grid.addColumn(ClassEnrollment::getStartTime).setHeader("Start");
        grid.addColumn(ClassEnrollment::getEndTime).setHeader("End");
        grid.setItems(service.getAllRecords());
        add(new H2("All records"), grid);
    }
}
