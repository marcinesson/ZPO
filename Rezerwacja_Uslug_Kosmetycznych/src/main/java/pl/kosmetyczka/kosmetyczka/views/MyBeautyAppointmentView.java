package pl.kosmetyczka.kosmetyczka.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.kosmetyczka.kosmetyczka.model.BeautyAppointment;
import pl.kosmetyczka.kosmetyczka.service.BeautyAppointmentService;

@Route("beauty/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MyBeautyAppointmentView extends VerticalLayout {
    public MyBeautyAppointmentView(BeautyAppointmentService service) {
        Grid<BeautyAppointment> grid = new Grid<>(BeautyAppointment.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(BeautyAppointment::getCategory).setHeader("Kategoria");
        grid.addColumn(BeautyAppointment::getRequiredCapacity).setHeader("Pojemnosc");
        grid.addColumn(BeautyAppointment::getMinQuality).setHeader("Jakosc");
        grid.addColumn(BeautyAppointment::getTaskUnits).setHeader("Jednostki");
        grid.addColumn(BeautyAppointment::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(BeautyAppointment::getScore).setHeader("Wynik");
        grid.addColumn(BeautyAppointment::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("beauty", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
