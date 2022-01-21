package br.hendrew.movidesk.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tickets")
public class Tickets {

    @Id
	//@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    
    @Column(name = "baseStatus", nullable = false)
    private String baseStatus;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "urgency", nullable = false)
    private String urgency;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "createdDate", nullable = false)
    private String createdDate;

    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "type", nullable = false)
    private long type;
    
    @ManyToOne
    @JoinColumn(name = "owner", nullable = false)
    private Owner owner;
    
    public Tickets() {
    }

    public String getBaseStatus() {
        return baseStatus;
    }
    public void setBaseStatus(String baseStatus) {
        this.baseStatus = baseStatus;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getUrgency() {
        return urgency;
    }
    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public long getType() {
        return type;
    }
    public void setType(long type) {
        this.type = type;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public Owner getOwner() {
        return owner;
    }
    public void setOwner(Owner owner) {
        this.owner = owner;
    }
    @Override
    public String toString() {
        return "Tickets [baseStatus=" + baseStatus + ", category=" + category + ", createdDate=" + createdDate + ", id="
                + id + ", owner=" + owner + ", status=" + status + ", subject=" + subject + ", type=" + type
                + ", urgency=" + urgency + "]";
    }  

    
}
