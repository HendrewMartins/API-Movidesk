package br.hendrew.movidesk.services.impl;

import br.hendrew.movidesk.services.OwnerService;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.hendrew.movidesk.entity.Owner;
import br.hendrew.movidesk.exception.MenssageNotFoundException;
import br.hendrew.movidesk.repository.OwnerRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;

@ApplicationScoped
public class DefaultOwnerService implements OwnerService {

    private final OwnerRepository ownerRepository;

    @Inject
    public DefaultOwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;

    }

    @Override
    public Owner getOwnerById(long id) throws MenssageNotFoundException {
        return ownerRepository.findByIdOptional(id)
                .orElseThrow(() -> new MenssageNotFoundException("There Owner doesn't exist"));
    }

    @Override
    public Owner getOwnerByStringId(String id) throws MenssageNotFoundException {
        return ownerRepository.findByStringId(id);
    }

    @Override
    public List<Owner> getAllOwner() {
        return ownerRepository.listAll(Sort.ascending("businessname"));
    }

    @Transactional
    @Override
    public Owner updateOwner(String id, Owner owner) throws MenssageNotFoundException {
        Owner existing = getOwnerByStringId(id);

        existing.setBusinessName(owner.getBusinessName());
        existing.setEmail(owner.getEmail());
        existing.setPathPicture(owner.getPathPicture());
        existing.setPersonType(owner.getPersonType());
        existing.setPhone(owner.getPhone());
        existing.setProfileType(owner.getProfileType());
        existing.setCategoryOwner(owner.getCategoryOwner());

        return existing;
    }

    @Transactional
    @Override
    public Owner saveOwner(Owner owner) {
        ownerRepository.persistAndFlush(owner);
        return owner;
    }

    @Transactional
    @Override
    public void deleteOwner(String id) throws MenssageNotFoundException {
        ownerRepository.delete(getOwnerByStringId(id));
    }

    @Override
    public long countOwner() {
        return ownerRepository.count();
    }

    @Override
    public List<Owner> getOwnerPage(int pag, int quant) throws MenssageNotFoundException {
        List<Owner> owner = ownerRepository.findAll(Sort.ascending("businessname")).page(Page.of(pag, quant)).list();
        return owner;
    }

}
