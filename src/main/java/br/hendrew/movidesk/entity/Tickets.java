package br.hendrew.movidesk.entity;

public class Tickets {

    private String baseStatus;
    private String status;
    private String urgency;
    private String category;
    private String createdDate;
    private String subject;
    private long type;
    private long id;
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
