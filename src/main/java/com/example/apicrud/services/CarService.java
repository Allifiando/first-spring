package com.example.apicrud.services;

import com.example.apicrud.pojo.models.Car;
import com.example.apicrud.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    @Autowired
    CarRepository carRepository;

    public Car add(Car params) {
        return carRepository.save(params);
    }
}
