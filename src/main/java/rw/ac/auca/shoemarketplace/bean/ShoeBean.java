package rw.ac.auca.shoemarketplace.bean;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import rw.ac.auca.shoemarketplace.dao.ShoeDAO;
import rw.ac.auca.shoemarketplace.dao.VendorDAO;
import rw.ac.auca.shoemarketplace.model.Condition;
import rw.ac.auca.shoemarketplace.model.Shoe;
import rw.ac.auca.shoemarketplace.model.Vendor;

import java.io.Serializable;
import java.util.List;

@Named("shoeBean")
@RequestScoped
public class ShoeBean implements Serializable {

    private Shoe shoe = new Shoe();
    private Long id;
    private Long selectedVendorId;

    private final ShoeDAO shoeDAO = new ShoeDAO();
    private final VendorDAO vendorDAO = new VendorDAO();

    // ---------- CREATE ----------
    public String register() {
        Vendor vendor = vendorDAO.findById(selectedVendorId);
        shoe.setVendor(vendor);
        shoeDAO.save(shoe);
        shoe = new Shoe();
        selectedVendorId = null;
        return "ShoeList?faces-redirect=true";
    }

    // ---------- READ (load one, for edit page via ?id=) ----------
    public void loadShoe() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (!context.isPostback() && id != null) {
            Shoe loaded = shoeDAO.findById(id);
            if (loaded != null) {
                this.shoe = loaded;
                this.selectedVendorId = loaded.getVendor().getId();
            }
        }
    }

    // ---------- UPDATE ----------
    public String update() {
        shoe.setId(id);
        Vendor vendor = vendorDAO.findById(selectedVendorId);
        shoe.setVendor(vendor);
        shoeDAO.update(shoe);
        return "ShoeList?faces-redirect=true";
    }

    // ---------- DELETE ----------
    public String delete(Long id) {
        shoeDAO.delete(id);
        return "ShoeList?faces-redirect=true";
    }

    // ---------- READ (all, for list page) ----------
    public List<Shoe> getAllShoes() {
        return shoeDAO.findAll();
    }

    // ---------- Vendor dropdown options for the form ----------
    public List<Vendor> getAllVendors() {
        return vendorDAO.findAll();
    }

    // ---------- Condition dropdown options for the form ----------
    public Condition[] getConditions() {
        return Condition.values();
    }

    public Shoe getShoe() { return shoe; }
    public void setShoe(Shoe shoe) { this.shoe = shoe; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getSelectedVendorId() { return selectedVendorId; }
    public void setSelectedVendorId(Long selectedVendorId) { this.selectedVendorId = selectedVendorId; }
}