package br.hendrew.movidesk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customclients")
public class CustomClients {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "customFieldItem", nullable = true)
    private String customFieldItem;

    public CustomClients() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCustomFieldItem() {
        return customFieldItem;
    }

    public void setCustomFieldItem(String customFieldItem) {
        this.customFieldItem = customFieldItem;
    }

    @Override
    public String toString() {
        return "CustomClients [customFieldItem=" + customFieldItem + ", id=" + id + "]";
    }

    

}
