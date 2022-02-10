package br.hendrew.movidesk.services;

import java.util.List;

import br.hendrew.movidesk.entity.CustomClients;
import br.hendrew.movidesk.exception.MenssageNotFoundException;

public interface CustomClientsService {


    CustomClients getCustomClientsById(long id) throws MenssageNotFoundException;

    CustomClients getCustomClientsByString(String customFieldItem) throws MenssageNotFoundException;

    List<CustomClients> getAllCustomClients();

    CustomClients updateCustomClients(long id, CustomClients customClients) throws MenssageNotFoundException;

    CustomClients saveCustomClients(CustomClients customClients);

    void deleteCustomClients(long id) throws MenssageNotFoundException;

    long countCustomClients();

    long countCustomClientsItem(String customFieldItem);

    List<CustomClients> getCustomClientsPage(int pag, int quant) throws MenssageNotFoundException;
}
