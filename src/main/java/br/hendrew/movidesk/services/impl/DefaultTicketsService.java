package br.hendrew.movidesk.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.hendrew.movidesk.entity.AgenteTickets;
import br.hendrew.movidesk.entity.Owner;
import br.hendrew.movidesk.entity.Tickets;
import br.hendrew.movidesk.entity.TicketsSituacao;
import br.hendrew.movidesk.entity.TicketsType;
import br.hendrew.movidesk.entity.TicketsUrgency;
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

    @Override
    public List<Tickets> getTicketsUrgency(String urgency) throws MenssageNotFoundException {
        return ticketsRepository.findByUrgency(urgency);
    }

    @Override
    public TicketsSituacao getTicketsbaseStatusSUM() throws MenssageNotFoundException{
        TicketsSituacao sum = new TicketsSituacao();
        List<Tickets> listTickets = new ArrayList<Tickets>();

        listTickets = ticketsRepository.findBybaseStatus("Canceled");
        sum.setCanceled(listTickets.size());

        listTickets = ticketsRepository.findBybaseStatus("Closed");
        sum.setClosed(listTickets.size());

        listTickets = ticketsRepository.findBybaseStatus("InAttendance");
        sum.setInAttendance(listTickets.size());

        listTickets = ticketsRepository.findBybaseStatus("New");
        sum.setNewReg(listTickets.size());

        listTickets = ticketsRepository.findBybaseStatus("Resolved");
        sum.setResolved(listTickets.size());

        listTickets = ticketsRepository.findBybaseStatus("Stopped");
        sum.setStopped(listTickets.size());
        return sum;
    }

    @Override
    public TicketsUrgency getTicketsUrgencySUM() throws MenssageNotFoundException{
        TicketsUrgency sum = new TicketsUrgency();
        List<Tickets> listTickets = new ArrayList<Tickets>();

        listTickets = ticketsRepository.findByUrgency("2 - Alta");
        sum.setAlta(listTickets.size());

        listTickets = ticketsRepository.findByUrgency("4 - Baixa");
        sum.setBaixa(listTickets.size());

        listTickets = ticketsRepository.findByUrgency("3 - MÃ©dia");
        sum.setMedia(listTickets.size());

        listTickets = ticketsRepository.findByUrgencyIsNull("New");
        sum.setNulo(listTickets.size());

        listTickets = ticketsRepository.findByUrgency("1 - Urgente");
        sum.setUrgente(listTickets.size());

        return sum;
    }

    @Override
    public List<Tickets> getTicketsType(long type) throws MenssageNotFoundException {
        return ticketsRepository.findBytype(type);
    }

    @Override
    public TicketsType getTicketsTypeSUM() throws MenssageNotFoundException{
        TicketsType sum = new TicketsType();
        List<Tickets> listTickets = new ArrayList<Tickets>();

        listTickets = ticketsRepository.findBytype(1);
        sum.setInterno(listTickets.size());

        listTickets = ticketsRepository.findBytype(2);
        sum.setExterno(listTickets.size());

        return sum;
    }

    @Override
    public List<AgenteTickets> OwnerTickets() throws MenssageNotFoundException{
        List<AgenteTickets> agente = new ArrayList<AgenteTickets>(); 
        List<Owner> owner = ownerRepository.listAll();
        int quantOwner = 0;
        for(int i = 0; i < owner.size(); i++){
            List<Tickets> ticketsInAttedance = ticketsRepository.findByOwnerInAttedance(owner.get(i));
            List<Tickets> ticketsNew = ticketsRepository.findByOwnerNew(owner.get(i));
            List<Tickets> ticketsSttoped = ticketsRepository.findByOwnerStopped(owner.get(i));

            if((ticketsInAttedance.size() > 0) ||
               (ticketsNew.size() > 0) ||
               (ticketsSttoped.size() > 0)){

                   AgenteTickets agenteTemp = new AgenteTickets();
                   agenteTemp.idAgente = owner.get(i).getId();
                   agenteTemp.businessName = owner.get(i).getBusinessName();
                   agenteTemp.quantTicketsInAttendance = ticketsInAttedance.size();
                   agenteTemp.quantTicketsNew = ticketsNew.size();
                   agenteTemp.quantTicketsStopped = ticketsSttoped.size();

                   agente.add(quantOwner,agenteTemp);

                   quantOwner++;
            }
        }
        
        return agente;
    }
}

