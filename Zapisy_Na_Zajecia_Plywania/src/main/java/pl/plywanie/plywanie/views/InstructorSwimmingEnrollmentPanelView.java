package pl.plywanie.plywanie.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.plywanie.plywanie.model.SwimmingEnrollment;
import pl.plywanie.plywanie.service.SwimmingEnrollmentService;

@Route("instructor/enrollments")
@PageTitle("Panel")
@RolesAllowed("INSTRUCTOR")
public class InstructorSwimmingEnrollmentPanelView extends VerticalLayout {
    private final SwimmingEnrollmentService service;
    private final Grid<SwimmingEnrollment> grid = new Grid<>(SwimmingEnrollment.class, false);

    public InstructorSwimmingEnrollmentPanelView(SwimmingEnrollmentService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(SwimmingEnrollment::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(SwimmingEnrollment::getCategory).setHeader("Kategoria");
        grid.addColumn(SwimmingEnrollment::getStartTime).setHeader("Start");
        grid.addColumn(SwimmingEnrollment::getEndTime).setHeader("Koniec");
        grid.addColumn(SwimmingEnrollment::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(SwimmingEnrollment::getScore).setHeader("Wynik");
        grid.addColumn(SwimmingEnrollment::getTotalPrice).setHeader("Koszt");
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
