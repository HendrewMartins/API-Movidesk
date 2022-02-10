package br.hendrew.movidesk.services.impl;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.hendrew.movidesk.entity.CategoryOwner;
import br.hendrew.movidesk.exception.MenssageNotFoundException;
import br.hendrew.movidesk.repository.CategoryOwnerRepository;
import br.hendrew.movidesk.services.CategoryOwnerService;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;

@ApplicationScoped
public class DefaultCategoryOwnerService implements CategoryOwnerService {

    private final CategoryOwnerRepository categoryOwnerRepository;

    @Inject
    public DefaultCategoryOwnerService(CategoryOwnerRepository categoryOwnerRepository) {
        this.categoryOwnerRepository = categoryOwnerRepository;

    }

    @Override
    public CategoryOwner getCategoryOwnerById(long id) throws MenssageNotFoundException {
        return categoryOwnerRepository.findByIdOptional(id)
        .orElseThrow(() -> new MenssageNotFoundException("There CategoryOwner doesn't exist"));
    }

    @Override
    public CategoryOwner getCategoryOwnerByString(String desCategory) throws MenssageNotFoundException {
        return categoryOwnerRepository.findByStringId(desCategory);
    }

    @Override
    public List<CategoryOwner> getAllCategoryOwner() {
        return categoryOwnerRepository.listAll(Sort.ascending("descategoria"));

    }

    @Transactional
    @Override
    public CategoryOwner updateCategoryOwner(long id, CategoryOwner categoryOwner) throws MenssageNotFoundException {
        CategoryOwner existing = getCategoryOwnerById(id);

        existing.setDesCategoria(categoryOwner.getDesCategoria());
        return existing;
    }

    @Transactional
    @Override
    public CategoryOwner saveCategoryOwner(CategoryOwner categoryOwner) {
        categoryOwnerRepository.persistAndFlush(categoryOwner);
        return categoryOwner;
    }

    @Transactional
    @Override
    public void deleteCategoryOwner(long id) throws MenssageNotFoundException {
        categoryOwnerRepository.delete(getCategoryOwnerById(id));
        
    }

    @Override
    public long countCategoryOwner() {
        return categoryOwnerRepository.count();
    }

    @Override
    public long countCategoryOwnerItem(String desCategory) {
        return categoryOwnerRepository.count("desCategory",desCategory);
    }

    @Override
    public List<CategoryOwner> getCategoryOwnerPage(int pag, int quant) throws MenssageNotFoundException {
        List<CategoryOwner> clients = categoryOwnerRepository.findAll().page(Page.of(pag, quant)).list();
        return clients;
    }

    @Override
    public List<CategoryOwner> getCategoryOwnerByDesCategory(String desc) throws MenssageNotFoundException {
        return categoryOwnerRepository.findByDesCategoria(desc);
    }
    
}
