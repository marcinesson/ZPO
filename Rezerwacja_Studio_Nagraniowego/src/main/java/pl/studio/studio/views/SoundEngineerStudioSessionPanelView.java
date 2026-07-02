package pl.studio.studio.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.studio.studio.model.StudioSession;
import pl.studio.studio.service.StudioSessionService;

@Route("engineer/sessions")
@PageTitle("Panel")
@RolesAllowed("SOUND_ENGINEER")
public class SoundEngineerStudioSessionPanelView extends VerticalLayout {
    private final StudioSessionService service;
    private final Grid<StudioSession> grid = new Grid<>(StudioSession.class, false);

    public SoundEngineerStudioSessionPanelView(StudioSessionService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(StudioSession::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(StudioSession::getStartTime).setHeader("Start");
        grid.addColumn(StudioSession::getEndTime).setHeader("Koniec");
        grid.addColumn(StudioSession::getTotalPrice).setHeader("Koszt");
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
