package br.hendrew.movidesk.services;

import java.util.List;

import br.hendrew.movidesk.entity.Organization;
import br.hendrew.movidesk.exception.MenssageNotFoundException;

public interface OrganizationService {

    Organization getOrganizationById(long id) throws MenssageNotFoundException;

    Organization getOrganizationByStringId(String id) throws MenssageNotFoundException;

    List<Organization> getAllOrganization();

    Organization updateOrganization(String id, Organization organization) throws MenssageNotFoundException;

    Organization saveOrganization(Organization organization);

    void deleteOrganization(String id) throws MenssageNotFoundException;

    long countOrganization();

    List<Organization> getOrganizationPage(int pag, int quant) throws MenssageNotFoundException;
    
}
