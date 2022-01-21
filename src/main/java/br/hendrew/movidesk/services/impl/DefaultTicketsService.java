package br.hendrew.movidesk.services.impl;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.hendrew.movidesk.entity.Owner;
import br.hendrew.movidesk.entity.Tickets;
import br.hendrew.movidesk.exception.MenssageNotFoundException;
import br.hendrew.movidesk.repository.OwnerRepository;
import br.hendrew.movidesk.repository.TicketsRepository;
import br.hendrew.movidesk.services.TicketsService;
import io.quarkus.panache.common.Page;

@ApplicationScoped
public class DefaultTicketsService implements TicketsService {

    private final TicketsRepository ticketsRepository;
    private final OwnerRepository ownerRepository;

    @Inject
    public DefaultTicketsService(TicketsRepository ticketsRepository,
            OwnerRepository ownerRepository) {

        this.ticketsRepository = ticketsRepository;
        this.ownerRepository = ownerRepository;

    }

    @Override
    public Tickets getTicketsById(long id) throws MenssageNotFoundException {
        return ticketsRepository.findByIdOptional(id)
                .orElseThrow(() -> new MenssageNotFoundException("There Tickets doesn't exist"));
    }

    @Override
    public Boolean getTicketsExist(long id) {
        try {
            Tickets tickets = getTicketsById(id);
            if(tickets != null){
                return true; 
            }else{
                return false;
            }    
        } catch (Exception e) {
            return false;
        }
    }
       

    @Override
    public List<Tickets> getAllTickets() {
        return ticketsRepository.listAll();
    }

    

    @Transactional
    @Override
    public Tickets updateTickets(long id, Tickets tickets) throws MenssageNotFoundException {
        Tickets existing = getTicketsById(id);

        existing.setBaseStatus(tickets.getBaseStatus());
        existing.setCategory(tickets.getCategory());
        existing.setCreatedDate(tickets.getCreatedDate());
        existing.setOwner(tickets.getOwner());
        existing.setStatus(tickets.getStatus());
        existing.setSubject(tickets.getSubject());
        existing.setType(tickets.getType());
        existing.setUrgency(tickets.getUrgency());

        return existing;
    }

    @Transactional
    @Override
    public Tickets saveTickets(Tickets tickets) {
        Owner owner = ownerRepository.findByStringId(tickets.getOwner().getId());
        if(owner == null){
            ownerRepository.persistAndFlush(tickets.getOwner());
        }
        ticketsRepository.persistAndFlush(tickets);
        return tickets;
    }

    @Transactional
    @Override
    public void deleteTickets(long id) throws MenssageNotFoundException {
        ticketsRepository.delete(getTicketsById(id));
    }

    @Override
    public long countTickets() {
        return ticketsRepository.count();
    }

    @Override
    public List<Tickets> getTicketsPage(int pag, int quant) throws MenssageNotFoundException {
        List<Tickets> tickets = ticketsRepository.findAll().page(Page.of(pag, quant)).list();
        return tickets;
    }

    @Override
    public List<Tickets> getTicketsbaseStatus(String baseStatus) throws MenssageNotFoundException {
        return ticketsRepository.findBybaseStatus(baseStatus);
    }


}
