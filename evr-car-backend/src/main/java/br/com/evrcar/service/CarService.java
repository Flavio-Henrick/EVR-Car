package br.com.evrcar.service;

import br.com.evrcar.dto.CarRequest;
import br.com.evrcar.model.Car;
import br.com.evrcar.repository.CarRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class CarService {
    private final CarRepository repo;
    public CarService(CarRepository repo){this.repo=repo;}

    public List<Car> publicList() { return repo.findByStatus(Car.Status.AVAILABLE); }
    public List<Car> adminList() { return repo.findAll(); }
    public Car getPublic(Long id) {
        Car car = get(id);
        if (car.getStatus() == Car.Status.SOLD) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return car;
    }
    public Car create(CarRequest r) { Car c=new Car(); apply(c,r); return repo.save(c); }
    public Car update(Long id, CarRequest r) { Car c=get(id); apply(c,r); return repo.save(c); }
    public void delete(Long id) { repo.delete(get(id)); }
    public Car addPhoto(Long id, String url) { Car c=get(id); c.getPhotos().add(url); return repo.save(c); }
    public Car removePhoto(Long id, int index) {
        Car c=get(id);
        if(index<0 || index>=c.getPhotos().size()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Foto não encontrada");
        c.getPhotos().remove(index); return repo.save(c);
    }
    private Car get(Long id) { return repo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Veículo não encontrado")); }
    private void apply(Car c, CarRequest r) {
        c.setBrand(r.brand()); c.setModel(r.model()); c.setYearModel(r.yearModel());
        c.setYearManufacture(r.yearManufacture()); c.setMileage(r.mileage());
        c.setFuel(r.fuel()); c.setTransmission(r.transmission()); c.setColor(r.color());
        c.setPrice(r.price()); c.setDescription(r.description());
        c.setStatus(r.status()==null?Car.Status.AVAILABLE:r.status());
        if(r.photos()!=null) c.setPhotos(r.photos());
    }
}
