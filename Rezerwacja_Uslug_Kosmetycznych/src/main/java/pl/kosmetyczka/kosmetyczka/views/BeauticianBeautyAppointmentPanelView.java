package pl.kosmetyczka.kosmetyczka.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.kosmetyczka.kosmetyczka.model.BeautyAppointment;
import pl.kosmetyczka.kosmetyczka.service.BeautyAppointmentService;

@Route("beautician/appointments")
@PageTitle("Panel")
@RolesAllowed("BEAUTICIAN")
public class BeauticianBeautyAppointmentPanelView extends VerticalLayout {
    private final BeautyAppointmentService service;
    private final Grid<BeautyAppointment> grid = new Grid<>(BeautyAppointment.class, false);

    public BeauticianBeautyAppointmentPanelView(BeautyAppointmentService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(BeautyAppointment::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(BeautyAppointment::getCategory).setHeader("Kategoria");
        grid.addColumn(BeautyAppointment::getStartTime).setHeader("Start");
        grid.addColumn(BeautyAppointment::getEndTime).setHeader("Koniec");
        grid.addColumn(BeautyAppointment::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(BeautyAppointment::getScore).setHeader("Wynik");
        grid.addColumn(BeautyAppointment::getTotalPrice).setHeader("Koszt");
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
