package br.hendrew.movidesk.services.impl;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.hendrew.movidesk.entity.Clients;
import br.hendrew.movidesk.exception.MenssageNotFoundException;
import br.hendrew.movidesk.repository.ClientsRepository;
import br.hendrew.movidesk.services.ClientsService;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;

@ApplicationScoped
public class DefaultClientsService implements ClientsService{

    private final ClientsRepository clientsRepository;

    @Inject
    public DefaultClientsService(ClientsRepository clientsRepository) {
        this.clientsRepository = clientsRepository;

    }

    @Override
    public Clients getClientsById(long id) throws MenssageNotFoundException {
        return clientsRepository.findByIdOptional(id)
                .orElseThrow(() -> new MenssageNotFoundException("There clients doesn't exist"));
    }

    @Override
    public Clients getClientsByStringId(String id) throws MenssageNotFoundException {
        return clientsRepository.findByStringId(id);
    }

    @Override
    public List<Clients> getAllClients() {
        return clientsRepository.listAll(Sort.ascending("businessname"));
    }

    @Transactional
    @Override
    public Clients updateClients(String id, Clients Clients) throws MenssageNotFoundException {
        Clients existing = getClientsByStringId(id);

        existing.setBusinessName(Clients.getBusinessName());
        existing.setAddress(Clients.getAddress());
        existing.setBairro(Clients.getBairro());
        existing.setCep(Clients.getCep());
        existing.setCity(Clients.getCity());
        existing.setComplement(Clients.getComplement());
        existing.setEmail(Clients.getEmail());
        existing.setIsDeleted(Clients.getIsDeleted());
        existing.setNumber(Clients.getNumber());
        existing.setOrganization(Clients.getOrganization());
        existing.setPathPicture(Clients.getPathPicture());
        existing.setPersonType(Clients.getPersonType());
        existing.setPhone(Clients.getPhone());
        existing.setProfileType(Clients.getProfileType());
        existing.setReference(Clients.getReference());

        return existing;
    }

    @Transactional
    @Override
    public Clients saveClients(Clients clients) {
        clientsRepository.persistAndFlush(clients);
        return clients;
    }

    @Transactional
    @Override
    public void deleteClients(String id) throws MenssageNotFoundException {
        clientsRepository.delete(getClientsByStringId(id));
    }

    @Override
    public long countClients() {
        return clientsRepository.count();
    }

    @Override
    public List<Clients> getClientsPage(int pag, int quant) throws MenssageNotFoundException {
        List<Clients> clients = clientsRepository.findAll().page(Page.of(pag, quant)).list();
        return clients;
    }
}

