package com.icarlosalbertojr.provider.repositories;

import com.icarlosalbertojr.provider.models.Car;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CarRepository {

    private Map<Integer, Car> cars = new HashMap<>();

    public void saveOrUpdate(Car car) {
        cars.put(car.id(), car);
    }

    public void deleteById(Integer id) {
        cars.remove(id);
    }

    public Car findById(Integer id) {
        return cars.get(id);
    }

    public List<Car> findAll() {
        return cars.entrySet()
                .stream()
                .map(entry -> entry.getValue())
                .toList();
    }

}
