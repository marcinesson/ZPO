package pl.weterynarz.weterynarz.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import jakarta.annotation.security.RolesAllowed;
import pl.weterynarz.weterynarz.model.AppUser;
import pl.weterynarz.weterynarz.model.Appointment;
import pl.weterynarz.weterynarz.service.AppointmentService;

@Route("appointments")
@RouteAlias("")
@PageTitle("Appointments")
@RolesAllowed("CLIENT")
public class CreateAppointmentsView extends VerticalLayout {
    private final AppointmentService appointmentService;
    private final DateTimePicker startTime = new DateTimePicker("Appointment date");
    private final Select<AppUser> vetSelect = new Select<>();

    public CreateAppointmentsView(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;

        vetSelect.setLabel("Vet");
        vetSelect.setItems(appointmentService.getAllVets());
        vetSelect.setItemLabelGenerator(AppUser::getLogin);

        Button createButton = new Button("Make an appointment", event -> createAppointment());

        add(
            new H2("Make an appointment"),
            new Anchor("appointments/my", "My visits"),
            vetSelect,
            startTime,
            createButton
        );
    }

    private void createAppointment() {
    try {
            Appointment appointment = appointmentService.createAppointment(
                    vetSelect.getValue().getId(),
                    startTime.getValue()      
            ); 
            Notification.show("Appointment madewith an ID: " + appointment.getId());
        } catch (RuntimeException exception) {
            Notification.show(exception.getMessage());
        }
    }
}
