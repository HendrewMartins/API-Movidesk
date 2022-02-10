package br.hendrew.movidesk.services;

import java.util.List;

import br.hendrew.movidesk.entity.CategoryOwner;
import br.hendrew.movidesk.exception.MenssageNotFoundException;

public interface CategoryOwnerService {
    
    CategoryOwner getCategoryOwnerById(long id) throws MenssageNotFoundException;

    CategoryOwner getCategoryOwnerByString(String desCategory) throws MenssageNotFoundException;

    List<CategoryOwner> getAllCategoryOwner();

    CategoryOwner updateCategoryOwner(long id, CategoryOwner CategoryOwner) throws MenssageNotFoundException;

    CategoryOwner saveCategoryOwner(CategoryOwner categoryOwner);

    void deleteCategoryOwner(long id) throws MenssageNotFoundException;

    long countCategoryOwner();

    long countCategoryOwnerItem(String categoryOwner);

    List<CategoryOwner> getCategoryOwnerPage(int pag, int quant) throws MenssageNotFoundException;

    List<CategoryOwner> getCategoryOwnerByDesCategory(String nome) throws MenssageNotFoundException;
    
}
