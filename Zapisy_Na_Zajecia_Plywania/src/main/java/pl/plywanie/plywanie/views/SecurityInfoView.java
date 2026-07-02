package pl.plywanie.plywanie.views;

import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

@Route("me")
@PageTitle("Security info")
public class SecurityInfoView extends VerticalLayout {
    public SecurityInfoView() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String roles = auth.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.joining(", "));
        add(new H2("Security info"),
                new Paragraph("Logged user: " + auth.getName()),
                new Paragraph("Roles: " + roles),
                new Anchor("logout", "Logout"));
    }
}
