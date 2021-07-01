package com.example.apicrud.services;

import com.example.apicrud.entities.CarEntity;
import com.example.apicrud.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl implements CarService{

    @Autowired
    CarRepository carRepository;

    @Override
    public CarEntity add(CarEntity params) {
        return carRepository.save(params);
    }
}
