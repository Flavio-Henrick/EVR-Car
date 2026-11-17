package br.com.evrcar.repository;

import br.com.evrcar.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByStatus(Car.Status status);
}
