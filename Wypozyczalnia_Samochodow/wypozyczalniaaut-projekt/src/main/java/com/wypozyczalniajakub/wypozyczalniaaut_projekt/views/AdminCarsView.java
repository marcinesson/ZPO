package com.wypozyczalniajakub.wypozyczalniaaut_projekt.views;

import java.math.BigDecimal;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.wypozyczalniajakub.wypozyczalniaaut_projekt.model.Car;
import com.wypozyczalniajakub.wypozyczalniaaut_projekt.service.CarService;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.context.SecurityContextHolder;

@Route("admin/cars")
@PageTitle("Admin - auta")
@RolesAllowed("ADMIN")
public class AdminCarsView extends VerticalLayout implements BeforeEnterObserver {
    private final CarService carService;
    private final TextField brand = new TextField("Marka");
    private final TextField carModel = new TextField("Model");
    private final BigDecimalField priceFor24h = new BigDecimalField("Cena za dobe");
    private final Grid<Car> carGrid = new Grid<>(Car.class, false);

    public AdminCarsView(CarService carService) {
        this.carService = carService;

        configureGrid();

        Button addButton = new Button("Dodaj auto", event -> addCar());
        HorizontalLayout form = new HorizontalLayout(brand, carModel, priceFor24h, addButton);
        HorizontalLayout navigation = new HorizontalLayout(
                new Anchor("", "Rezerwacja auta"),
                new Anchor("admin/reservations", "Admin - rezerwacje"));

        add(new H2("Admin - auta"), navigation, form, carGrid);
        refreshCars();
    }

    @SuppressWarnings("null")
    private void configureGrid() {
        carGrid.addColumn(Car::getId).setHeader("ID");
        carGrid.addColumn(Car::getBrand).setHeader("Marka");
        carGrid.addColumn(Car::getCarModel).setHeader("Model");
        carGrid.addColumn(Car::getPriceFor24h).setHeader("Cena za dobe");
        carGrid.addColumn(Car::isAvailable).setHeader("Aktywne");
        carGrid.addComponentColumn(car -> new Button("Usun/wylacz", event -> deleteOrDisable(car))).setHeader("Akcja");
        carGrid.setWidthFull();
    }

    private void addCar() {
        try {
            Car car = new Car();
            car.setBrand(brand.getValue());
            car.setCarModel(carModel.getValue());
            car.setPriceFor24h(priceFor24h.getValue());
            car.setAvailable(true);

            carService.addNewCar(car);
            clearForm();
            refreshCars();
            Notification.show("Auto dodane.", 3000, Notification.Position.TOP_CENTER);
        } catch (RuntimeException exception) {
            Notification.show(exception.getMessage(), 4000, Notification.Position.TOP_CENTER);
        }
    }

    private void deleteOrDisable(Car car) {
        try {
            carService.deleteOrDisableCar(car.getId());
            refreshCars();
            Notification.show("Auto usuniete albo wylaczone.", 3000, Notification.Position.TOP_CENTER);
        } catch (RuntimeException exception) {
            Notification.show(exception.getMessage(), 4000, Notification.Position.TOP_CENTER);
        }
    }

    private void clearForm() {
        brand.clear();
        carModel.clear();
        priceFor24h.setValue(BigDecimal.ZERO);
    }

    private void refreshCars() {
        carGrid.setItems(carService.getAllCars());
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
