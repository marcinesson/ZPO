package pl.fryzjer.fryzjer.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.fryzjer.fryzjer.model.HairAppointment;
import pl.fryzjer.fryzjer.service.HairAppointmentService;

@Route("appointments/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MyHairAppointmentView extends VerticalLayout {
    public MyHairAppointmentView(HairAppointmentService service) {
        Grid<HairAppointment> grid = new Grid<>(HairAppointment.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(HairAppointment::getCategory).setHeader("Kategoria");
        grid.addColumn(HairAppointment::getRequiredCapacity).setHeader("Pojemnosc");
        grid.addColumn(HairAppointment::getMinQuality).setHeader("Jakosc");
        grid.addColumn(HairAppointment::getTaskUnits).setHeader("Jednostki");
        grid.addColumn(HairAppointment::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(HairAppointment::getScore).setHeader("Wynik");
        grid.addColumn(HairAppointment::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("appointments", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
