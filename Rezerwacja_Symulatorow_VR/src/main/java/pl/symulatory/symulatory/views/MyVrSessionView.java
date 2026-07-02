package pl.symulatory.symulatory.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.symulatory.symulatory.model.VrSession;
import pl.symulatory.symulatory.service.VrSessionService;

@Route("vr-sessions/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MyVrSessionView extends VerticalLayout {
    public MyVrSessionView(VrSessionService service) {
        Grid<VrSession> grid = new Grid<>(VrSession.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(VrSession::getCategory).setHeader("Kategoria");
        grid.addColumn(VrSession::getRequiredCapacity).setHeader("Pojemnosc");
        grid.addColumn(VrSession::getMinQuality).setHeader("Jakosc");
        grid.addColumn(VrSession::getTaskUnits).setHeader("Jednostki");
        grid.addColumn(VrSession::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(VrSession::getScore).setHeader("Wynik");
        grid.addColumn(VrSession::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("simulators", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
