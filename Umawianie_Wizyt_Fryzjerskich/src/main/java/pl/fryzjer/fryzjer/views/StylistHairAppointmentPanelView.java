package pl.fryzjer.fryzjer.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.fryzjer.fryzjer.model.HairAppointment;
import pl.fryzjer.fryzjer.service.HairAppointmentService;

@Route("stylist/appointments")
@PageTitle("Panel")
@RolesAllowed("STYLIST")
public class StylistHairAppointmentPanelView extends VerticalLayout {
    private final HairAppointmentService service;
    private final Grid<HairAppointment> grid = new Grid<>(HairAppointment.class, false);

    public StylistHairAppointmentPanelView(HairAppointmentService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(HairAppointment::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(HairAppointment::getCategory).setHeader("Kategoria");
        grid.addColumn(HairAppointment::getStartTime).setHeader("Start");
        grid.addColumn(HairAppointment::getEndTime).setHeader("Koniec");
        grid.addColumn(HairAppointment::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(HairAppointment::getScore).setHeader("Wynik");
        grid.addColumn(HairAppointment::getTotalPrice).setHeader("Koszt");
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
