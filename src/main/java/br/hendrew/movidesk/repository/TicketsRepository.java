package br.hendrew.movidesk.repository;


import java.util.List;

import javax.enterprise.context.ApplicationScoped;


import br.hendrew.movidesk.entity.Tickets;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class TicketsRepository implements PanacheRepository<Tickets>{
    public List<Tickets> findBybaseStatus(String basestatus) {
        return find("basestatus", basestatus ).list();
    }
}





