package br.hendrew.movidesk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "clients")
public class Clients {
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

    @Column(name = "isDeleted", nullable = true)
    private String isDeleted;
   
    @Column(name = "address", nullable = true)
    private String address;
   
    @Column(name = "complement", nullable = true)
    private String complement;
   
    @Column(name = "cep", nullable = true)
    private String cep;
   
    @Column(name = "city", nullable = true)
    private String city;
   
    @Column(name = "bairro", nullable = true)
    private String bairro;
   
    @Column(name = "number", nullable = true)
    private String number;
   
    @Column(name = "reference", nullable = true)
    private String reference;
   
    @ManyToOne
    @JoinColumn(name = "organization", nullable = true)
    private Organization organization;


    public Clients() {
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

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
    
    @Override
    public String toString() {
        return "Clients [address=" + address + ", bairro=" + bairro + ", businessName=" + businessName + ", cep=" + cep
                + ", city=" + city + ", complement=" + complement + ", email=" + email + ", id=" + id + ", isDeleted="
                + isDeleted + ", number=" + number + ", organization=" + organization + ", pathPicture=" + pathPicture
                + ", personType=" + personType + ", phone=" + phone + ", profileType=" + profileType + ", reference="
                + reference + "]";
    }
    
}
