package pl.kursyjezykowe.kursyjezykowe.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.kursyjezykowe.kursyjezykowe.model.CourseEnrollment;
import pl.kursyjezykowe.kursyjezykowe.service.CourseEnrollmentService;

@Route("teacher/enrollments")
@PageTitle("Panel")
@RolesAllowed("TEACHER")
public class TeacherCourseEnrollmentPanelView extends VerticalLayout {
    private final CourseEnrollmentService service;
    private final Grid<CourseEnrollment> grid = new Grid<>(CourseEnrollment.class, false);

    public TeacherCourseEnrollmentPanelView(CourseEnrollmentService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(CourseEnrollment::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(CourseEnrollment::getCategory).setHeader("Kategoria");
        grid.addColumn(CourseEnrollment::getStartTime).setHeader("Start");
        grid.addColumn(CourseEnrollment::getEndTime).setHeader("Koniec");
        grid.addColumn(CourseEnrollment::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(CourseEnrollment::getScore).setHeader("Wynik");
        grid.addColumn(CourseEnrollment::getTotalPrice).setHeader("Koszt");
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
