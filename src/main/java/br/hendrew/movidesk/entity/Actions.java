package br.hendrew.movidesk.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "actions")
public class Actions implements Serializable{

    @Id
	//@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Id
    @ManyToOne
    @JoinColumn(name = "tickets")
    private Tickets tickets;
    
    @Column(name = "type", nullable = true, columnDefinition = "int default 0")
    private long type;

    @Column(name = "origin", nullable = true, columnDefinition = "int default 0")
    private long origin;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "htmlDescription", nullable = true)
    private String htmlDescription;

    @Column(name = "status", nullable = true)
    private String status;

    @Column(name = "justification", nullable = true)
    private String justification;

    @Column(name = "createdDate", nullable = true)
    private String createdDate;

    @Column(name = "isDeleted", nullable = false)
    private Boolean isDeleted;

    public Actions() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Tickets getTickets() {
        return tickets;
    }

    public void setTickets(Tickets tickets) {
        this.tickets = tickets;
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
        return "Actions [createdDate=" + createdDate + ", description=" + description + ", htmlDescription="
                + htmlDescription + ", id=" + id + ", isDeleted=" + isDeleted + ", justification=" + justification
                + ", origin=" + origin + ", status=" + status + ", tickets=" + tickets + ", type=" + type + "]";
    }

    
}
