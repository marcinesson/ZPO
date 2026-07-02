package pl.biblioteka.biblioteka.views;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.biblioteka.biblioteka.model.Loan;
import pl.biblioteka.biblioteka.service.LoanService;

@Route("loans/my") @PageTitle("My records")
@RolesAllowed("CLIENT")
public class MyLoanView extends VerticalLayout {
    public MyLoanView(LoanService service) {
        Grid<Loan> grid = new Grid<>(Loan.class, false);
        grid.addColumn(record -> record.getItem().getName()).setHeader("Item");
        grid.addColumn(Loan::getStartTime).setHeader("Start");
        grid.addColumn(Loan::getEndTime).setHeader("End");
        grid.addColumn(Loan::getTotalPrice).setHeader("Price");
        grid.setItems(service.getMyRecords());
        add(new Anchor("books", "Back"), new H2("My records"), grid);
    }
}
