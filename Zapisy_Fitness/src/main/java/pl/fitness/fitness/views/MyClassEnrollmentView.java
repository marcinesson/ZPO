package pl.fitness.fitness.views;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.fitness.fitness.model.ClassEnrollment;
import pl.fitness.fitness.service.ClassEnrollmentService;

@Route("enrollments/my") @PageTitle("My records")
@RolesAllowed("CLIENT")
public class MyClassEnrollmentView extends VerticalLayout {
    public MyClassEnrollmentView(ClassEnrollmentService service) {
        Grid<ClassEnrollment> grid = new Grid<>(ClassEnrollment.class, false);
        grid.addColumn(record -> record.getItem().getName()).setHeader("Item");
        grid.addColumn(ClassEnrollment::getStartTime).setHeader("Start");
        grid.addColumn(ClassEnrollment::getEndTime).setHeader("End");
        grid.addColumn(ClassEnrollment::getTotalPrice).setHeader("Price");
        grid.setItems(service.getMyRecords());
        add(new Anchor("classes", "Back"), new H2("My records"), grid);
    }
}
