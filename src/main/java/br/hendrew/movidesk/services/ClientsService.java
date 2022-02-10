package br.hendrew.movidesk.services;

import java.util.List;

import br.hendrew.movidesk.entity.Clients;
import br.hendrew.movidesk.exception.MenssageNotFoundException;

public interface ClientsService {

    Clients getClientsById(long id) throws MenssageNotFoundException;

    Clients getClientsByStringId(String id) throws MenssageNotFoundException;

    List<Clients> getAllClients();

    Clients updateClients(String id, Clients clients) throws MenssageNotFoundException;

    Clients saveClients(Clients clients);

    void deleteClients(String id) throws MenssageNotFoundException;

    long countClients();

    List<Clients> getClientsPage(int pag, int quant) throws MenssageNotFoundException;
    
}
