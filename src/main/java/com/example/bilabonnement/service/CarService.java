package com.example.bilabonnement.service;

import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.repository.CarRepository;

public class CarService {

  private CarRepository carRepository;

  public CarService() {
    this.carRepository = new CarRepository();
  }

  public Car findCarByID(int carID) {
    for (Car car: carRepository.getFleet()) {
      if (car.getCarID() == carID) {
        return car;
      }
    }
    return null;
  }

  public CarRepository getCarRepository() {
    return carRepository;
  }
}
