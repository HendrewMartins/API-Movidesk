package br.hendrew.movidesk.services;

import java.util.List;

import br.hendrew.movidesk.entity.Tickets;
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
}
