package com.example.apicrud.controllers;

import com.example.apicrud.pojo.models.Car;
import com.example.apicrud.repository.CarRepository;
import com.example.apicrud.response.CommonResponse;
import com.example.apicrud.response.CommonResponseGenerator;
import com.example.apicrud.services.CarService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/api")

public class CarController {

    private final CarRepository carRepository;
    private final CarService carService;
    private final CommonResponseGenerator commonResponseGenerator;

    public CarController(CarRepository carRepository, CarService carService, CommonResponseGenerator commonResponseGenerator) {
        this.carRepository = carRepository;
        this.carService = carService;
        this.commonResponseGenerator = commonResponseGenerator;
    }

    @GetMapping("/check")
    public String checkApi(){
        return "Hello Worlds";
    }

    @PostMapping("/")
    public CommonResponse add(@RequestBody Car params){
        try {
            Car car = carService.add(params);
            return commonResponseGenerator.successResponse(car, "Success");
        }catch (Exception e){
            return commonResponseGenerator.failedResponse(e.getMessage());
        }
    }

    @GetMapping("/")
    public List<Car> getAll(){
        return carRepository.findAll();
    }

    @GetMapping("/id")
    public CommonResponse<Car> getById(@RequestParam int id){
        try {
             return commonResponseGenerator.successResponse(carRepository.findById(id).get(),"succes");
        }catch (Exception e){
            return commonResponseGenerator.failedResponse(e.getMessage());
        }
    }

    @PutMapping("/update")
    public Car update(@RequestBody Car params){
        return carRepository.save(params);
    }

    @DeleteMapping("/delete")
    public Car delete(@RequestParam int id){
        Car car = carRepository.findById(id).get();
        carRepository.deleteById(id);
        return car;
    }
}
