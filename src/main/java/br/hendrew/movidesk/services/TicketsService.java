package br.hendrew.movidesk.services;

import java.util.List;

import br.hendrew.movidesk.entity.AgenteCategory;
import br.hendrew.movidesk.entity.AgenteJustification;
import br.hendrew.movidesk.entity.AgenteTickets;
import br.hendrew.movidesk.entity.Category;
import br.hendrew.movidesk.entity.CategoryOwner;
import br.hendrew.movidesk.entity.Justification;
import br.hendrew.movidesk.entity.Tickets;
import br.hendrew.movidesk.entity.TicketsAnos;
import br.hendrew.movidesk.entity.TicketsAnosCategory;
import br.hendrew.movidesk.entity.TicketsMesesDias;
import br.hendrew.movidesk.entity.TicketsSituacao;
import br.hendrew.movidesk.entity.TicketsType;
import br.hendrew.movidesk.entity.TicketsUrgency;
import br.hendrew.movidesk.exception.MenssageNotFoundException;

public interface TicketsService {
    
    Tickets getTicketsById(long id) throws MenssageNotFoundException;

    Boolean getTicketsExist(long id);

    //List<Tickets> getTichetsByNome(String nome) throws MenssageNotFoundException;

    List<Tickets> getAllTickets();

    Tickets updateTickets(long id, Tickets tickets) throws MenssageNotFoundException;

    Tickets saveTickets(Tickets tickets);

    void deleteTickets(long id) throws MenssageNotFoundException;

    long countTickets();

    List<Tickets> getTicketsPage(int pag, int quant) throws MenssageNotFoundException;

    List<Tickets> getTicketsbaseStatus(String baseStatus) throws MenssageNotFoundException;

    TicketsSituacao getTicketsbaseStatusSUM() throws MenssageNotFoundException;

    List<Tickets> getTicketsUrgency(String urgency) throws MenssageNotFoundException;

    TicketsUrgency getTicketsUrgencySUM() throws MenssageNotFoundException;

    List<Tickets> getTicketsType(long type) throws MenssageNotFoundException;

    TicketsType getTicketsTypeSUM() throws MenssageNotFoundException;

    List<AgenteTickets> OwnerTickets() throws MenssageNotFoundException;

    List<AgenteCategory> OwnerCategory() throws MenssageNotFoundException;

    Category Category() throws MenssageNotFoundException;

    List<AgenteJustification> ownerJustifications() throws MenssageNotFoundException;

    Justification justification() throws MenssageNotFoundException;

    List<TicketsAnos> ticketsAnos() throws MenssageNotFoundException;

    List<TicketsAnosCategory> ticketsAnosCategory(List<CategoryOwner> categoryowner) throws MenssageNotFoundException;

    List<TicketsMesesDias> ticketsMesesCategory(List<CategoryOwner> categoryowner) throws MenssageNotFoundException;

    TicketsSituacao getTicketsbaseStatusSUMDate(List<CategoryOwner> categoryOwner) throws MenssageNotFoundException;

    TicketsUrgency getTicketsUrgencySUMDate(List<CategoryOwner> categoryOwner) throws MenssageNotFoundException;

    Category CategorySeven(List<CategoryOwner> categoryOwner) throws MenssageNotFoundException;

    Category CategoryDay(List<CategoryOwner> categoryowner) throws MenssageNotFoundException;

    List<AgenteTickets> OwnerTicketsSeven(List<CategoryOwner> categoryOwner) throws MenssageNotFoundException;

    List<TicketsMesesDias> ticketsSevenCategory(List<CategoryOwner> categoryowner) throws MenssageNotFoundException;

    TicketsSituacao getTicketsbaseStatusSUMDay(List<CategoryOwner> categoryOwners) throws MenssageNotFoundException;

    TicketsUrgency getTicketsUrgencySUMDay(List<CategoryOwner> categoryowner) throws MenssageNotFoundException;

    List<AgenteTickets> OwnerTicketsDay(List<CategoryOwner> categoryOwner) throws MenssageNotFoundException;

    List<TicketsMesesDias> ticketsDayCategory(List<CategoryOwner> categoryowner) throws MenssageNotFoundException;

    long countTicketsSemClientCustom();

}
