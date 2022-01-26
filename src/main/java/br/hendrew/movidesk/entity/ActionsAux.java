package br.hendrew.movidesk.entity;

public class ActionsAux {

    private long id;
    private long type;
    private long origin;
    private String description;
    private String htmlDescription;
    private String status;
    private String justification;
    private String createdDate;
    private Boolean isDeleted;

    public ActionsAux() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

    public long getOrigin() {
        return origin;
    }

    public void setOrigin(long origin) {
        this.origin = origin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHtmlDescription() {
        return htmlDescription;
    }

    public void setHtmlDescription(String htmlDescription) {
        this.htmlDescription = htmlDescription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "ActionsAux [createdDate=" + createdDate + ", description=" + description + ", htmlDescription="
                + htmlDescription + ", id=" + id + ", isDeleted=" + isDeleted + ", justification=" + justification
                + ", origin=" + origin + ", status=" + status + ", type=" + type + "]";
    }

    
    
    
}
