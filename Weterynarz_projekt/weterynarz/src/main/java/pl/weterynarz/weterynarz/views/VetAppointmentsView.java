package pl.weterynarz.weterynarz.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;
import pl.weterynarz.weterynarz.model.Appointment;
import pl.weterynarz.weterynarz.service.AppointmentService;

@Route("vet/appointments-panel")
@PageTitle("Vet appointments")
@RolesAllowed("VET")
public class VetAppointmentsView extends VerticalLayout {
    private final AppointmentService appointmentService;
    private final Grid<Appointment> appointmentGrid = new Grid<>(Appointment.class, false);

    public VetAppointmentsView(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;

        configureGrid();
        add(new H2("My vet schedule"), appointmentGrid);
        refreshAppointments();
    }

    private void configureGrid() {
        appointmentGrid.addColumn(appointment -> appointment.getUser().getLogin()).setHeader("Client");
        appointmentGrid.addColumn(Appointment::getStartTime).setHeader("Start");
        appointmentGrid.addColumn(Appointment::getEndTime).setHeader("End");
        appointmentGrid.setWidthFull();
    }

    private void refreshAppointments() {
        appointmentGrid.setItems(appointmentService.getLoggedInVetAppointments());
    }
}
