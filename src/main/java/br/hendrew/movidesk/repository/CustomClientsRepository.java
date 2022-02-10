package br.hendrew.movidesk.repository;

import javax.enterprise.context.ApplicationScoped;

import br.hendrew.movidesk.entity.CustomClients;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class CustomClientsRepository implements PanacheRepository<CustomClients>{
    
    public CustomClients findByStringId(String customFieldItem) {
        return find("customFieldItem", customFieldItem ).firstResult();
    }
}
