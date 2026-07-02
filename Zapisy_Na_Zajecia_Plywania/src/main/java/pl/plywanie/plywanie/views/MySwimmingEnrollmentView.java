package pl.plywanie.plywanie.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.plywanie.plywanie.model.SwimmingEnrollment;
import pl.plywanie.plywanie.service.SwimmingEnrollmentService;

@Route("swimming/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MySwimmingEnrollmentView extends VerticalLayout {
    public MySwimmingEnrollmentView(SwimmingEnrollmentService service) {
        Grid<SwimmingEnrollment> grid = new Grid<>(SwimmingEnrollment.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(SwimmingEnrollment::getCategory).setHeader("Kategoria");
        grid.addColumn(SwimmingEnrollment::getRequiredCapacity).setHeader("Pojemnosc");
        grid.addColumn(SwimmingEnrollment::getMinQuality).setHeader("Jakosc");
        grid.addColumn(SwimmingEnrollment::getTaskUnits).setHeader("Jednostki");
        grid.addColumn(SwimmingEnrollment::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(SwimmingEnrollment::getScore).setHeader("Wynik");
        grid.addColumn(SwimmingEnrollment::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("swimming", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
