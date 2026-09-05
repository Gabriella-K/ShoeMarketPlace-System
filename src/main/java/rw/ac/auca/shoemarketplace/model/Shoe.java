package rw.ac.auca.shoemarketplace.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

@Entity
@Table(name = "shoes")
public class Shoe implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Brand is required.")
    @Size(min = 2, max = 50, message = "Brand must be between 2 and 50 characters.")
    @Column(nullable = false, length = 50)
    private String brand;

    @NotNull(message = "Size is required.")
    @Min(value = 30, message = "Size must be at least 30.")
    @Max(value = 50, message = "Size must be at most 50.")
    @Column(nullable = false)
    private Integer size;

    @NotNull(message = "Price is required.")
    @Positive(message = "Price must be greater than 0.")
    @Column(nullable = false)
    private Double price;

    @NotNull(message = "Condition is required.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Condition condition;

    @NotNull(message = "A vendor must be assigned to this shoe.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    public Shoe() {}

    public Shoe(String brand, Integer size, Double price, Condition condition, Vendor vendor) {
        this.brand = brand;
        this.size = size;
        this.price = price;
        this.condition = condition;
        this.vendor = vendor;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public Integer getSize() { return size; }
    public void setSize(Integer size) { this.size = size; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Condition getCondition() { return condition; }
    public void setCondition(Condition condition) { this.condition = condition; }

    public Vendor getVendor() { return vendor; }
    public void setVendor(Vendor vendor) { this.vendor = vendor; }
}