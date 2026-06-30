package com.wypozyczalniajakub.wypozyczalniaaut_projekt.views;

import java.time.LocalDate;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.wypozyczalniajakub.wypozyczalniaaut_projekt.model.Car;
import com.wypozyczalniajakub.wypozyczalniaaut_projekt.model.Reservation;
import com.wypozyczalniajakub.wypozyczalniaaut_projekt.service.CarService;
import com.wypozyczalniajakub.wypozyczalniaaut_projekt.service.ReservationService;

import jakarta.annotation.security.PermitAll;

@Route("")
@PageTitle("Rezerwacja auta")
@PermitAll
public class CarReservationView extends VerticalLayout {
    private final CarService carService;
    private final ReservationService reservationService;
    private final DatePicker startDate = new DatePicker("Od");
    private final DatePicker endDate = new DatePicker("Do");
    private final Grid<Car> carGrid = new Grid<>(Car.class, false);

    public CarReservationView(CarService carService, ReservationService reservationService) {
        this.carService = carService;
        this.reservationService = reservationService;

        startDate.setValue(LocalDate.now().plusDays(1));
        endDate.setValue(LocalDate.now().plusDays(1));

        configureGrid();

        Button searchButton = new Button("Szukaj dostepnych aut", event -> refreshCars());
        HorizontalLayout filters = new HorizontalLayout(startDate, endDate, searchButton);
        HorizontalLayout navigation = new HorizontalLayout(
                new Anchor("my-reservations", "Moje rezerwacje"));

        add(new H2("Wypozyczalnia samochodow"), navigation, filters, carGrid);
        refreshCars();
    }

    @SuppressWarnings("null")
    private void configureGrid() {
        carGrid.addColumn(Car::getId).setHeader("ID");
        carGrid.addColumn(Car::getBrand).setHeader("Marka");
        carGrid.addColumn(Car::getCarModel).setHeader("Model");
        carGrid.addColumn(Car::getPriceFor24h).setHeader("Cena za dobe");
        carGrid.addComponentColumn(car -> new Button("Rezerwuj", event -> reserveCar(car))).setHeader("Akcja");
        carGrid.setWidthFull();
    }

    private void refreshCars() {
        carGrid.setItems(carService.getAvailableCars(startDate.getValue(), endDate.getValue()));
    }

    private void reserveCar(Car car) {
        try {
            Reservation reservation = reservationService.createReservation(car.getId(), startDate.getValue(), endDate.getValue());
            Notification.show("Rezerwacja utworzona. ID: " + reservation.getId()
                    + ", koszt: " + reservation.getFinalCost(), 4000, Notification.Position.TOP_CENTER);
            refreshCars();
        } catch (RuntimeException exception) {
            Notification.show(exception.getMessage(), 4000, Notification.Position.TOP_CENTER);
        }
    }
}
