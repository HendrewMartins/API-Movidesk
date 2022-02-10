package br.hendrew.movidesk.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import br.hendrew.movidesk.entity.CategoryOwner;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

@ApplicationScoped
public class CategoryOwnerRepository implements PanacheRepository<CategoryOwner>{
    public CategoryOwner findByStringId(String desCategory) {
        return find("descategoria", desCategory ).firstResult();
    }

    public List<CategoryOwner> findByDesCategoria(String desc) {
        return find("lower(descategoria) like lower(:descategoria)", Parameters.with("descategoria", "%" + desc + "%")).list();
    }
}
