package com.example.apicrud.services;

import com.example.apicrud.pojo.models.Car;
import com.example.apicrud.repository.CarRepository;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car add(Car params) {
        return carRepository.save(params);
    }
}
