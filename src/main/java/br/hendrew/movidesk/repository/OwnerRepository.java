package br.hendrew.movidesk.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import br.hendrew.movidesk.entity.CategoryOwner;
import br.hendrew.movidesk.entity.Owner;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class OwnerRepository implements PanacheRepository<Owner>{
    public Owner findByStringId(String id_string) {
        return find("id", id_string ).firstResult();
    }

    public List<Owner> findByCategoryOwner(CategoryOwner categoryOwner) {
        return find("categoryOwner", categoryOwner).list();
    }
}
