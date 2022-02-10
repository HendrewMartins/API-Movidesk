package br.hendrew.movidesk.repository;

import javax.enterprise.context.ApplicationScoped;

import br.hendrew.movidesk.entity.Clients;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class ClientsRepository implements PanacheRepository<Clients>{

    public Clients findByStringId(String id_string) {
        return find("id", id_string ).firstResult();
    }
    
}
