package com.example.apicrud.controllers;

import com.example.apicrud.entities.CarEntity;
import com.example.apicrud.repository.CarRepository;
import com.example.apicrud.response.CommonResponse;
import com.example.apicrud.response.CommonResponseGenerator;
import com.example.apicrud.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/api")

public class CarController {

    @Autowired
    CarRepository carRepository;

    @Autowired
    CarService carService;

    @Autowired
    CommonResponseGenerator commonResponseGenerator;

    @GetMapping("/check")
    public String checkApi(){
        return "Hello World";
    }

    @PostMapping("/")
    public CommonResponse add(@RequestBody CarEntity params){
        try {
            CarEntity car = carService.add(params);
            return commonResponseGenerator.successResponse(car, "Success");
        }catch (Exception e){
            return commonResponseGenerator.failedResponse(e.getMessage());
        }
    }

    @GetMapping("/")
    public List<CarEntity> getAll(){
        return carRepository.findAll();
    }

    @GetMapping("/id")
    public CommonResponse<CarEntity> getById(@RequestParam int id){
        try {
             return commonResponseGenerator.successResponse(carRepository.findById(id).get(),"succes");
        }catch (Exception e){
            return commonResponseGenerator.failedResponse(e.getMessage());
        }
    }

    @PutMapping("/update")
    public CarEntity update(@RequestBody CarEntity params){
        return carRepository.save(params);
    }

    @DeleteMapping("/delete")
    public CarEntity delete(@RequestParam int id){
        CarEntity car = carRepository.findById(id).get();
        carRepository.deleteById(id);
        return car;
    }
}
