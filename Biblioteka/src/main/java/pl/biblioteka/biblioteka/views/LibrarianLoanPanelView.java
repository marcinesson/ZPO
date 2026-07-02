package pl.biblioteka.biblioteka.views;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.biblioteka.biblioteka.model.Loan;
import pl.biblioteka.biblioteka.service.LoanService;

@Route("librarian/loans") @PageTitle("Panel")
@RolesAllowed("LIBRARIAN")
public class LibrarianLoanPanelView extends VerticalLayout {
    public LibrarianLoanPanelView(LoanService service) {
        Grid<Loan> grid = new Grid<>(Loan.class, false);
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("User");
        grid.addColumn(record -> record.getItem().getName()).setHeader("Item");
        grid.addColumn(Loan::getStartTime).setHeader("Start");
        grid.addColumn(Loan::getEndTime).setHeader("End");
        grid.setItems(service.getAllRecords());
        add(new H2("All records"), grid);
    }
}
