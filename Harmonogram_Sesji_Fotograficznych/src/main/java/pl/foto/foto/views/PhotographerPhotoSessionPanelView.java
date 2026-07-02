package pl.foto.foto.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.foto.foto.model.PhotoSession;
import pl.foto.foto.service.PhotoSessionService;

@Route("photographer/sessions")
@PageTitle("Panel")
@RolesAllowed("PHOTOGRAPHER")
public class PhotographerPhotoSessionPanelView extends VerticalLayout {
    private final PhotoSessionService service;
    private final Grid<PhotoSession> grid = new Grid<>(PhotoSession.class, false);

    public PhotographerPhotoSessionPanelView(PhotoSessionService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(PhotoSession::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(PhotoSession::getCategory).setHeader("Kategoria");
        grid.addColumn(PhotoSession::getStartTime).setHeader("Start");
        grid.addColumn(PhotoSession::getEndTime).setHeader("Koniec");
        grid.addColumn(PhotoSession::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(PhotoSession::getScore).setHeader("Wynik");
        grid.addColumn(PhotoSession::getTotalPrice).setHeader("Koszt");
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
