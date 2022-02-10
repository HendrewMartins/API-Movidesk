package br.hendrew.movidesk.repository;

import javax.enterprise.context.ApplicationScoped;

import br.hendrew.movidesk.entity.Organization;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class OrganizationRepository implements PanacheRepository<Organization> {

    public Organization findByStringId(String id_string) {
        return find("id", id_string ).firstResult();
    }
    
}
