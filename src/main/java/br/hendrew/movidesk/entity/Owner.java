package br.hendrew.movidesk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "owner")
public class Owner {

    @Id
    private String id;

    @Column(name = "personType", nullable = true)
    private long personType;

    @Column(name = "profileType", nullable = true)
    private long profileType;

    @Column(name = "businessName", nullable = true)
    private String businessName;

    @Column(name = "email", nullable = true)
    private String email;

    @Column(name = "phone", nullable = true)
    private String phone;

    @Column(name = "pathPicture", nullable = true)
    private String pathPicture;

    @ManyToOne
    @JoinColumn(name = "categoryowner", nullable = true)
    private CategoryOwner categoryOwner;

    public Owner(String id, long personType, long profileType, String businessName, String email, String phone,
            String pathPicture, CategoryOwner categoryOwner) {
        this.id = id;
        this.personType = personType;
        this.profileType = profileType;
        this.businessName = businessName;
        this.email = email;
        this.phone = phone;
        this.pathPicture = pathPicture;
        this.categoryOwner = categoryOwner;
    }

    public Owner() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getPersonType() {
        return personType;
    }

    public void setPersonType(long personType) {
        this.personType = personType;
    }

    public long getProfileType() {
        return profileType;
    }

    public void setProfileType(long profileType) {
        this.profileType = profileType;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPathPicture() {
        return pathPicture;
    }

    public void setPathPicture(String pathPicture) {
        this.pathPicture = pathPicture;
    }

    

    public CategoryOwner getCategoryOwner() {
        return categoryOwner;
    }

    public void setCategoryOwner(CategoryOwner categoryOwner) {
        this.categoryOwner = categoryOwner;
    }

    @Override
    public String toString() {
        return "Owner [businessName=" + businessName + ", categoryOwner=" + categoryOwner + ", email=" + email + ", id="
                + id + ", pathPicture=" + pathPicture + ", personType=" + personType + ", phone=" + phone
                + ", profileType=" + profileType + "]";
    }

}
