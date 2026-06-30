package com.wypozyczalniajakub.wypozyczalniaaut_projekt.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.wypozyczalniajakub.wypozyczalniaaut_projekt.model.Reservation;
import com.wypozyczalniajakub.wypozyczalniaaut_projekt.service.ReservationService;

import jakarta.annotation.security.PermitAll;

@Route("my-reservations")
@PageTitle("Moje rezerwacje")
@PermitAll
public class MyReservationsView extends VerticalLayout {
    private final ReservationService reservationService;
    private final Grid<Reservation> reservationGrid = new Grid<>(Reservation.class, false);

    public MyReservationsView(ReservationService reservationService) {
        this.reservationService = reservationService;

        configureGrid();

        Button refreshButton = new Button("Odswiez", event -> refreshReservations());
        add(new H2("Moje rezerwacje"), new HorizontalLayout(new Anchor("", "Rezerwacja auta"), refreshButton),
                reservationGrid);
        refreshReservations();
    }

    @SuppressWarnings("null")
    private void configureGrid() {
        reservationGrid.addColumn(Reservation::getId).setHeader("ID");
        reservationGrid.addColumn(reservation -> reservation.getCar().getBrand()).setHeader("Marka");
        reservationGrid.addColumn(reservation -> reservation.getCar().getCarModel()).setHeader("Model");
        reservationGrid.addColumn(Reservation::getStartDate).setHeader("Od");
        reservationGrid.addColumn(Reservation::getEndDate).setHeader("Do");
        reservationGrid.addColumn(Reservation::getFinalCost).setHeader("Koszt");
        reservationGrid.setWidthFull();
    }

    private void refreshReservations() {
        reservationGrid.setItems(reservationService.getLoggedInUserReservations());
    }
}
