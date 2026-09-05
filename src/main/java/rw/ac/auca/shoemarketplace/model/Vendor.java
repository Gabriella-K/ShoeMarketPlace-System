package rw.ac.auca.shoemarketplace.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vendors")
public class Vendor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Shop name is required.")
    @Size(min = 2, max = 100, message = "Shop name must be between 2 and 100 characters.")
    @Column(name = "shop_name", nullable = false, length = 100)
    private String shopName;

    @NotBlank(message = "Phone number is required.")
    @Pattern(regexp = "^(\\+250|0)7[0-9]{8}$", message = "Enter a valid Rwandan phone number (e.g. 0788123456).")
    @Column(nullable = false, length = 20)
    private String phone;

    @NotBlank(message = "Email is required.")
    @Email(message = "Enter a valid email address.")
    @Column(nullable = false, length = 100)
    private String email;

    @NotBlank(message = "Location is required.")
    @Size(max = 100)
    @Column(length = 100)
    private String location;

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Shoe> shoes = new ArrayList<>();

    public Vendor() {}

    public Vendor(String shopName, String phone, String email, String location) {
        this.shopName = shopName;
        this.phone = phone;
        this.email = email;
        this.location = location;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getShopName() { return shopName; }
    public void setShopName(String shopName) { this.shopName = shopName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public List<Shoe> getShoes() { return shoes; }
    public void setShoes(List<Shoe> shoes) { this.shoes = shoes; }
}