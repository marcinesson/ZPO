package pl.fitness.fitness.views;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.fitness.fitness.model.*;
import pl.fitness.fitness.service.ClassEnrollmentService;

@Route("classes") @RouteAlias("") @PageTitle("classes")
@RolesAllowed("CLIENT")
public class FitnessClassView extends VerticalLayout {
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("End");

    public FitnessClassView(ClassEnrollmentService service) {
        Grid<FitnessClass> grid = new Grid<>(FitnessClass.class, false);
        grid.addColumn(FitnessClass::getName).setHeader("Name");
        grid.addColumn(FitnessClass::getCapacity).setHeader("Capacity");
        grid.addColumn(FitnessClass::getPrice).setHeader("Price");
        grid.addColumn(FitnessClass::isAvailable).setHeader("Available");
        grid.addComponentColumn(item -> new Button("Create", event -> {
            try {
                ClassEnrollment record = service.createRecord(item.getId(), startTime.getValue(), endTime.getValue());
                Notification.show("Created id: " + record.getId());
            } catch (RuntimeException exception) {
                Notification.show(exception.getMessage());
            }
        }));
        grid.setItems(service.getAllItems());
        add(new Anchor("enrollments/my", "My records"), new Anchor("me", "Security info"),
            new H2("classes"), startTime, endTime, grid);
    }
}
