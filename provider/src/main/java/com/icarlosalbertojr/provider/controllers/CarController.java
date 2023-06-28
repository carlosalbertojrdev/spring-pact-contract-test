package com.icarlosalbertojr.provider.controllers;

import com.icarlosalbertojr.provider.models.Car;
import com.icarlosalbertojr.provider.repositories.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarRepository carRepository;

    @PostMapping
    public void createNew(@RequestBody Car car) {
        carRepository.saveOrUpdate(car);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        carRepository.deleteById(id);
    }

    @GetMapping
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @GetMapping("/{id}")
    public Car findById(@PathVariable Integer id) {
        return carRepository.findById(id);
    }

}
