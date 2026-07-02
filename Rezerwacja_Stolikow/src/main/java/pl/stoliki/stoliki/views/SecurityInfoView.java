package pl.stoliki.stoliki.views;
import java.util.stream.Collectors;
import org.springframework.security.core.context.SecurityContextHolder;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
@Route("me") @PageTitle("Security info")
public class SecurityInfoView extends VerticalLayout {
    public SecurityInfoView() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        add(new H2("Security info"),
            new Paragraph("Logged user: " + auth.getName()),
            new Paragraph("Roles: " + auth.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.joining(", "))),
            new Anchor("logout", "Logout"));
    }
}
