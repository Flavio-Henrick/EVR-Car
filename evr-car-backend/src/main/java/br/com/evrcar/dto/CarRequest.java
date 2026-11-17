package br.com.evrcar.dto;

import br.com.evrcar.model.Car;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

public record CarRequest(
    @NotBlank String brand,
    @NotBlank String model,
    @Min(1900) @Max(2100) Integer yearModel,
    @Min(1900) @Max(2100) Integer yearManufacture,
    @Min(0) Integer mileage,
    String fuel, String transmission, String color,
    @NotNull @DecimalMin("0.01") BigDecimal price,
    String description,
    Car.Status status,
    List<String> photos
) {}
