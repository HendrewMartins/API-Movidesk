package br.hendrew.movidesk.services.impl;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.hendrew.movidesk.entity.CustomClients;
import br.hendrew.movidesk.exception.MenssageNotFoundException;
import br.hendrew.movidesk.repository.CustomClientsRepository;
import br.hendrew.movidesk.services.CustomClientsService;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;

@ApplicationScoped
public class DefaultCustomClientsService implements CustomClientsService{

    
    private final CustomClientsRepository customClientsRepository;

    @Inject
    public DefaultCustomClientsService(CustomClientsRepository customClientsRepository) {
        this.customClientsRepository = customClientsRepository;

    }

    @Override
    public CustomClients getCustomClientsById(long id) throws MenssageNotFoundException {
        return customClientsRepository.findByIdOptional(id)
        .orElseThrow(() -> new MenssageNotFoundException("There Owner doesn't exist"));
    }

    @Override
    public CustomClients getCustomClientsByString(String customFieldItem) throws MenssageNotFoundException {
        return customClientsRepository.findByStringId(customFieldItem);
    }

    @Override
    public List<CustomClients> getAllCustomClients() {
        return customClientsRepository.listAll(Sort.ascending("customFieldItem"));
    }

    @Transactional
    @Override
    public CustomClients updateCustomClients(long id, CustomClients customClients) throws MenssageNotFoundException {
        CustomClients existing = getCustomClientsById(id);

        existing.setCustomFieldItem(customClients.getCustomFieldItem());
        return existing;
    }

    @Transactional
    @Override
    public CustomClients saveCustomClients(CustomClients customClients) {
        customClientsRepository.persistAndFlush(customClients);
        return customClients;
    }

    @Transactional
    @Override
    public void deleteCustomClients(long id) throws MenssageNotFoundException {
        customClientsRepository.delete(getCustomClientsById(id));
        
    }

    @Override
    public long countCustomClients() {
        return customClientsRepository.count();
    }

    @Override
    public long countCustomClientsItem(String customFieldItem) {
        return customClientsRepository.count("customfielditem",customFieldItem);
    }

    @Override
    public List<CustomClients> getCustomClientsPage(int pag, int quant) throws MenssageNotFoundException {
        List<CustomClients> clients = customClientsRepository.findAll().page(Page.of(pag, quant)).list();
        return clients;
    }
    
}
