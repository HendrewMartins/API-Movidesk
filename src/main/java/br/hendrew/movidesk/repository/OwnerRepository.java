package br.hendrew.movidesk.repository;

import javax.enterprise.context.ApplicationScoped;

import br.hendrew.movidesk.entity.Owner;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class OwnerRepository implements PanacheRepository<Owner>{
    public Owner findByStringId(String id_string) {
        return find("id", id_string ).firstResult();
    }
}
