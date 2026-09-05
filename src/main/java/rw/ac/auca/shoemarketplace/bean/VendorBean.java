package rw.ac.auca.shoemarketplace.bean;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import rw.ac.auca.shoemarketplace.dao.VendorDAO;
import rw.ac.auca.shoemarketplace.model.Vendor;

import java.io.Serializable;
import java.util.List;

@Named("vendorBean")
@RequestScoped
public class VendorBean implements Serializable {

    private Vendor vendor = new Vendor();
    private Long id;
    private final VendorDAO vendorDAO = new VendorDAO();

    // ---------- CREATE ----------
    public String register() {
        if (!validate()) {
            return null;
        }
        vendorDAO.save(vendor);
        vendor = new Vendor();
        return "VendorList?faces-redirect=true";
    }

    // ---------- READ (load one, for edit page via ?id=) ----------
    public void loadVendor() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (!context.isPostback() && id != null) {
            Vendor loaded = vendorDAO.findById(id);
            if (loaded != null) {
                this.vendor = loaded;
            }
        }
    }

    // ---------- UPDATE ----------
    public String update() {
        vendor.setId(id);
        if (!validate()) {
            return null;
        }
        vendorDAO.update(vendor);
        return "VendorList?faces-redirect=true";
    }

    // ---------- DELETE ----------
    public String delete(Long id) {
        vendorDAO.delete(id);
        return "VendorList?faces-redirect=true";
    }

    // ---------- Custom server-side validation (3rd validation type) ----------
    private boolean validate() {
        FacesContext context = FacesContext.getCurrentInstance();
        boolean valid = true;

        if (vendorDAO.isEmailTaken(vendor.getEmail(), vendor.getId())) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "This email is already registered to another vendor.", null));
            valid = false;
        }

        return valid;
    }

    // ---------- READ (all, for list page) ----------
    public List<Vendor> getAllVendors() {
        return vendorDAO.findAll();
    }

    public Vendor getVendor() { return vendor; }
    public void setVendor(Vendor vendor) { this.vendor = vendor; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}