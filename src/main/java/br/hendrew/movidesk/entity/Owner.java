package br.hendrew.movidesk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "owner")
public class Owner {

    @Id
    private String id;

    @Column(name = "personType", nullable = false)
    private long personType;

    @Column(name = "profileType", nullable = false)
    private long profileType;

    @Column(name = "businessName", nullable = false)
    private String businessName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "pathPicture", nullable = false)
    private String pathPicture;

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

    @Override
    public String toString() {
        return "Owner [businessName=" + businessName + ", email=" + email + ", id=" + id + ", pathPicture="
                + pathPicture + ", personType=" + personType + ", phone=" + phone + ", profileType=" + profileType
                + "]";
    }

}
