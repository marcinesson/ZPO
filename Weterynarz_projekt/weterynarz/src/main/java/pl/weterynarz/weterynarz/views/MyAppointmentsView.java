package pl.weterynarz.weterynarz.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;
import pl.weterynarz.weterynarz.model.Appointment;
import pl.weterynarz.weterynarz.service.AppointmentService;

@Route("appointments/my")
@PageTitle("My appointments")
@RolesAllowed("CLIENT")
public class MyAppointmentsView extends VerticalLayout {
    private final AppointmentService appointmentService;
    private final Grid<Appointment> appointmentGrid = new Grid<>(Appointment.class, false);

    public MyAppointmentsView(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;

        configureGrid();
        add(new Anchor("appointments", "Make appointment"), new H2("My appointments"), appointmentGrid);
        refreshAppointments();
    }

    private void configureGrid() {
        appointmentGrid.addColumn(appointment -> appointment.getVet().getLogin()).setHeader("Vet");
        appointmentGrid.addColumn(Appointment::getStartTime).setHeader("Start");
        appointmentGrid.addColumn(Appointment::getEndTime).setHeader("End");
        appointmentGrid.addComponentColumn(appointment -> new Button("Cancel",
                event -> cancelAppointment(appointment.getId())));
        appointmentGrid.setWidthFull();
    }

    private void cancelAppointment(Long appointmentId) {
        try {
            appointmentService.cancelAppointment(appointmentId);
            Notification.show("Appointment cancelled.");
            refreshAppointments();
        } catch (RuntimeException exception) {
            Notification.show(exception.getMessage());
        }
    }

    private void refreshAppointments() {
        appointmentGrid.setItems(appointmentService.getLoggedInUserAppointments());
    }
}
