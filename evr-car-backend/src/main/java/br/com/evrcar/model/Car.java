package br.com.evrcar.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cars")
public class Car {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false) private String brand;
    @Column(nullable=false) private String model;
    private Integer yearModel;
    private Integer yearManufacture;
    private Integer mileage;
    private String fuel;
    private String transmission;
    private String color;
    @Column(precision=12, scale=2, nullable=false) private BigDecimal price;
    @Column(length=3000) private String description;
    @Enumerated(EnumType.STRING) @Column(nullable=false) private Status status = Status.AVAILABLE;
    @ElementCollection
    @CollectionTable(name="car_photos", joinColumns=@JoinColumn(name="car_id"))
    @Column(name="photo_url", length=1000)
    private List<String> photos = new ArrayList<>();
    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();

    @PreUpdate void preUpdate() { updatedAt = Instant.now(); }

    public enum Status { AVAILABLE, RESERVED, SOLD }

    public Long getId(){return id;} public void setId(Long v){id=v;}
    public String getBrand(){return brand;} public void setBrand(String v){brand=v;}
    public String getModel(){return model;} public void setModel(String v){model=v;}
    public Integer getYearModel(){return yearModel;} public void setYearModel(Integer v){yearModel=v;}
    public Integer getYearManufacture(){return yearManufacture;} public void setYearManufacture(Integer v){yearManufacture=v;}
    public Integer getMileage(){return mileage;} public void setMileage(Integer v){mileage=v;}
    public String getFuel(){return fuel;} public void setFuel(String v){fuel=v;}
    public String getTransmission(){return transmission;} public void setTransmission(String v){transmission=v;}
    public String getColor(){return color;} public void setColor(String v){color=v;}
    public BigDecimal getPrice(){return price;} public void setPrice(BigDecimal v){price=v;}
    public String getDescription(){return description;} public void setDescription(String v){description=v;}
    public Status getStatus(){return status;} public void setStatus(Status v){status=v;}
    public List<String> getPhotos(){return photos;} public void setPhotos(List<String> v){photos=v;}
    public Instant getCreatedAt(){return createdAt;} public Instant getUpdatedAt(){return updatedAt;}
}
