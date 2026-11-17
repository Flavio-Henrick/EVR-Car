package br.com.evrcar.controller;

import br.com.evrcar.dto.CarRequest;
import br.com.evrcar.model.Car;
import br.com.evrcar.service.CarService;
import br.com.evrcar.service.PhotoStorageService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CarController {
    private final CarService cars;
    private final PhotoStorageService photos;
    public CarController(CarService cars, PhotoStorageService photos){this.cars=cars;this.photos=photos;}

    @GetMapping("/cars") public List<Car> publicList(){return cars.publicList();}
    @GetMapping("/cars/{id}") public Car get(@PathVariable Long id){return cars.getPublic(id);}

    @GetMapping("/admin/cars") public List<Car> adminList(){return cars.adminList();}
    @PostMapping("/admin/cars") public Car create(@Valid @RequestBody CarRequest r){return cars.create(r);}
    @PutMapping("/admin/cars/{id}") public Car update(@PathVariable Long id,@Valid @RequestBody CarRequest r){return cars.update(id,r);}
    @DeleteMapping("/admin/cars/{id}") public Map<String,String> delete(@PathVariable Long id){cars.delete(id);return Map.of("message","Veículo excluído");}

    @PostMapping(value="/admin/cars/{id}/photos", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    public Car upload(@PathVariable Long id,@RequestPart("file") MultipartFile file){
        return cars.addPhoto(id,photos.upload(file));
    }
    @DeleteMapping("/admin/cars/{id}/photos/{index}")
    public Car removePhoto(@PathVariable Long id,@PathVariable int index){return cars.removePhoto(id,index);}
}
