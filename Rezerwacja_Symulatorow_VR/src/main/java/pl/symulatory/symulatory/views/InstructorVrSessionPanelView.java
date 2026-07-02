package pl.symulatory.symulatory.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.symulatory.symulatory.model.VrSession;
import pl.symulatory.symulatory.service.VrSessionService;

@Route("instructor/sessions")
@PageTitle("Panel")
@RolesAllowed("INSTRUCTOR")
public class InstructorVrSessionPanelView extends VerticalLayout {
    private final VrSessionService service;
    private final Grid<VrSession> grid = new Grid<>(VrSession.class, false);

    public InstructorVrSessionPanelView(VrSessionService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(VrSession::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(VrSession::getCategory).setHeader("Kategoria");
        grid.addColumn(VrSession::getStartTime).setHeader("Start");
        grid.addColumn(VrSession::getEndTime).setHeader("Koniec");
        grid.addColumn(VrSession::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(VrSession::getScore).setHeader("Wynik");
        grid.addColumn(VrSession::getTotalPrice).setHeader("Koszt");
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
