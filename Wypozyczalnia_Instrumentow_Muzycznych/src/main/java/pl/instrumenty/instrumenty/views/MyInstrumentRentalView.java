package pl.instrumenty.instrumenty.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.instrumenty.instrumenty.model.InstrumentRental;
import pl.instrumenty.instrumenty.service.InstrumentRentalService;

@Route("instrument-rentals/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MyInstrumentRentalView extends VerticalLayout {
    public MyInstrumentRentalView(InstrumentRentalService service) {
        Grid<InstrumentRental> grid = new Grid<>(InstrumentRental.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zasob");
        grid.addColumn(InstrumentRental::getRequiredCapacity).setHeader("Wymaganie");
        grid.addColumn(InstrumentRental::getStartTime).setHeader("Start");
        grid.addColumn(InstrumentRental::getEndTime).setHeader("Koniec");
        grid.addColumn(InstrumentRental::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("instruments", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
