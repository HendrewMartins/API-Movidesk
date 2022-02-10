package br.hendrew.movidesk.services.impl;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.hendrew.movidesk.entity.Organization;
import br.hendrew.movidesk.exception.MenssageNotFoundException;
import br.hendrew.movidesk.repository.OrganizationRepository;
import br.hendrew.movidesk.services.OrganizationService;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;

@ApplicationScoped
public class DefaultOrganizationService implements OrganizationService {

    private final OrganizationRepository organizationRepository;

    @Inject
    public DefaultOrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;

    }

    @Override
    public Organization getOrganizationById(long id) throws MenssageNotFoundException {
        return organizationRepository.findByIdOptional(id)
        .orElseThrow(() -> new MenssageNotFoundException("There clients doesn't exist"));
    }

    @Override
    public Organization getOrganizationByStringId(String id) throws MenssageNotFoundException {
        return organizationRepository.findByStringId(id);

    }

    @Override
    public List<Organization> getAllOrganization() {
        return organizationRepository.listAll(Sort.ascending("businessname"));
    }

    @Transactional
    @Override
    public Organization updateOrganization(String id, Organization organization) throws MenssageNotFoundException {
        Organization existing = getOrganizationByStringId(id);

        existing.setBusinessName(organization.getBusinessName());
        existing.setEmail(organization.getEmail());
        existing.setPathPicture(organization.getPathPicture());
        existing.setPersonType(organization.getPersonType());
        existing.setPhone(organization.getPhone());
        existing.setProfileType(organization.getProfileType());
       
        return existing;

    }

    @Transactional
    @Override
    public Organization saveOrganization(Organization organization) {
        organizationRepository.persistAndFlush(organization);
        return organization;
    }

    @Transactional
    @Override
    public void deleteOrganization(String id) throws MenssageNotFoundException {
        organizationRepository.delete(getOrganizationByStringId(id));
        
    }

    @Override
    public long countOrganization() {
        return organizationRepository.count();
    }

    @Override
    public List<Organization> getOrganizationPage(int pag, int quant) throws MenssageNotFoundException {
        List<Organization> organization = organizationRepository.findAll().page(Page.of(pag, quant)).list();
        return organization;
    }
    
}
