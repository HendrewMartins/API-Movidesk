package br.hendrew.movidesk.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import br.hendrew.movidesk.entity.Actions;
import br.hendrew.movidesk.entity.Tickets;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class ActionRepository implements PanacheRepository<Actions>{

    public List<Actions> findByTickets(Tickets tickets) {
        return find("tickets", tickets ).list();
    }

    public long countTickets(Tickets tickets) {
        return find("tickets", tickets ).count();
    }
    
}
