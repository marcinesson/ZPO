package pl.szkielet.szkielet.views;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.szkielet.szkielet.model.*;
import pl.szkielet.szkielet.service.MainRecordService;

@Route("items") @RouteAlias("") @PageTitle("items")
@RolesAllowed("CLIENT")
public class MainItemView extends VerticalLayout {
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("End");

    public MainItemView(MainRecordService service) {
        Grid<MainItem> grid = new Grid<>(MainItem.class, false);
        grid.addColumn(MainItem::getName).setHeader("Name");
        grid.addColumn(MainItem::getCapacity).setHeader("Capacity");
        grid.addColumn(MainItem::getPrice).setHeader("Price");
        grid.addColumn(MainItem::isAvailable).setHeader("Available");
        grid.addComponentColumn(item -> new Button("Create", event -> {
            try {
                MainRecord record = service.createRecord(item.getId(), startTime.getValue(), endTime.getValue());
                Notification.show("Created id: " + record.getId());
            } catch (RuntimeException exception) {
                Notification.show(exception.getMessage());
            }
        }));
        grid.setItems(service.getAllItems());
        add(new Anchor("records/my", "My records"), new Anchor("me", "Security info"),
            new H2("items"), startTime, endTime, grid);
    }
}
