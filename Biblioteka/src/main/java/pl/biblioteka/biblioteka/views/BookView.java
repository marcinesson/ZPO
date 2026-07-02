package pl.biblioteka.biblioteka.views;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.biblioteka.biblioteka.model.*;
import pl.biblioteka.biblioteka.service.LoanService;

@Route("books") @RouteAlias("") @PageTitle("books")
@RolesAllowed("CLIENT")
public class BookView extends VerticalLayout {
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("End");

    public BookView(LoanService service) {
        Grid<Book> grid = new Grid<>(Book.class, false);
        grid.addColumn(Book::getName).setHeader("Name");
        grid.addColumn(Book::getCapacity).setHeader("Capacity");
        grid.addColumn(Book::getPrice).setHeader("Price");
        grid.addColumn(Book::isAvailable).setHeader("Available");
        grid.addComponentColumn(item -> new Button("Create", event -> {
            try {
                Loan record = service.createRecord(item.getId(), startTime.getValue(), endTime.getValue());
                Notification.show("Created id: " + record.getId());
            } catch (RuntimeException exception) {
                Notification.show(exception.getMessage());
            }
        }));
        grid.setItems(service.getAllItems());
        add(new Anchor("loans/my", "My records"), new Anchor("me", "Security info"),
            new H2("books"), startTime, endTime, grid);
    }
}
