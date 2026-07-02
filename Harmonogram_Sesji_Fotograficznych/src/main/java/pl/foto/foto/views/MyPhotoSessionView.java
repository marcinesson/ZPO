package pl.foto.foto.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.foto.foto.model.PhotoSession;
import pl.foto.foto.service.PhotoSessionService;

@Route("photos/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MyPhotoSessionView extends VerticalLayout {
    public MyPhotoSessionView(PhotoSessionService service) {
        Grid<PhotoSession> grid = new Grid<>(PhotoSession.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(PhotoSession::getCategory).setHeader("Kategoria");
        grid.addColumn(PhotoSession::getRequiredCapacity).setHeader("Pojemnosc");
        grid.addColumn(PhotoSession::getMinQuality).setHeader("Jakosc");
        grid.addColumn(PhotoSession::getTaskUnits).setHeader("Jednostki");
        grid.addColumn(PhotoSession::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(PhotoSession::getScore).setHeader("Wynik");
        grid.addColumn(PhotoSession::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("photos", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
