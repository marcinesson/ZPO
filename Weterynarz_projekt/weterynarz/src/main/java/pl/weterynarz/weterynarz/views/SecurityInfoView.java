package pl.weterynarz.weterynarz.views;

import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("me")
@PageTitle("Security info")
public class SecurityInfoView extends VerticalLayout {
    public SecurityInfoView() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String login = authentication.getName();
        String roles = authentication.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.joining(", "));

        add(
                new H2("Security info"),
                new Paragraph("Logged user: " + login),
                new Paragraph("Roles: " + roles));
    }
}
