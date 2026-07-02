package pl.studio.studio.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.studio.studio.model.StudioSession;
import pl.studio.studio.service.StudioSessionService;

@Route("studio-sessions/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MyStudioSessionView extends VerticalLayout {
    public MyStudioSessionView(StudioSessionService service) {
        Grid<StudioSession> grid = new Grid<>(StudioSession.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(StudioSession::getRequiredCapacity).setHeader("Wymaganie");
        grid.addColumn(StudioSession::getStartTime).setHeader("Start");
        grid.addColumn(StudioSession::getEndTime).setHeader("Koniec");
        grid.addColumn(StudioSession::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("studios", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
