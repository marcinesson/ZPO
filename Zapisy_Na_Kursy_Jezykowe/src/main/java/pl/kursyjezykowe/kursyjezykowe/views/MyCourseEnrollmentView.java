package pl.kursyjezykowe.kursyjezykowe.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.kursyjezykowe.kursyjezykowe.model.CourseEnrollment;
import pl.kursyjezykowe.kursyjezykowe.service.CourseEnrollmentService;

@Route("courses/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MyCourseEnrollmentView extends VerticalLayout {
    public MyCourseEnrollmentView(CourseEnrollmentService service) {
        Grid<CourseEnrollment> grid = new Grid<>(CourseEnrollment.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(CourseEnrollment::getCategory).setHeader("Kategoria");
        grid.addColumn(CourseEnrollment::getRequiredCapacity).setHeader("Pojemnosc");
        grid.addColumn(CourseEnrollment::getMinQuality).setHeader("Jakosc");
        grid.addColumn(CourseEnrollment::getTaskUnits).setHeader("Jednostki");
        grid.addColumn(CourseEnrollment::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(CourseEnrollment::getScore).setHeader("Wynik");
        grid.addColumn(CourseEnrollment::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("courses", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
