package com.wypozyczalniajakub.wypozyczalniaaut_projekt.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.wypozyczalniajakub.wypozyczalniaaut_projekt.model.Reservation;
import com.wypozyczalniajakub.wypozyczalniaaut_projekt.service.ReservationService;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.context.SecurityContextHolder;

@Route("admin/reservations")
@PageTitle("Admin - rezerwacje")
@RolesAllowed("ADMIN")
public class AdminReservationsView extends VerticalLayout implements BeforeEnterObserver {
    private final ReservationService reservationService;
    private final Grid<Reservation> reservationGrid = new Grid<>(Reservation.class, false);

    public AdminReservationsView(ReservationService reservationService) {
        this.reservationService = reservationService;

        configureGrid();

        Button refreshButton = new Button("Odswiez", event -> refreshReservations());
        HorizontalLayout navigation = new HorizontalLayout(new Anchor("", "Rezerwacja auta"),
                new Anchor("admin/cars", "Admin - auta"), refreshButton);

        add(new H2("Admin - wszystkie rezerwacje"), navigation, reservationGrid);
        refreshReservations();
    }

    @SuppressWarnings("null")
    private void configureGrid() {
        reservationGrid.addColumn(Reservation::getId).setHeader("ID");
        reservationGrid.addColumn(reservation -> reservation.getUser().getLogin()).setHeader("Klient");
        reservationGrid.addColumn(reservation -> reservation.getCar().getBrand()).setHeader("Marka");
        reservationGrid.addColumn(reservation -> reservation.getCar().getCarModel()).setHeader("Model");
        reservationGrid.addColumn(Reservation::getStartDate).setHeader("Od");
        reservationGrid.addColumn(Reservation::getEndDate).setHeader("Do");
        reservationGrid.addColumn(Reservation::getFinalCost).setHeader("Koszt");
        reservationGrid.setWidthFull();
    }

    private void refreshReservations() {
        reservationGrid.setItems(reservationService.getAllReservations());
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        java.util.Optional.of(isAdmin())
                .filter(Boolean::booleanValue)
                .orElseGet(() -> {
                    event.rerouteTo(CarReservationView.class);
                    return false;
                });
    }

    private boolean isAdmin() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));
    }
}
