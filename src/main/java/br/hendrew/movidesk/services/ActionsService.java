package br.hendrew.movidesk.services;

import java.util.List;

import br.hendrew.movidesk.entity.Actions;
import br.hendrew.movidesk.entity.Tickets;
import br.hendrew.movidesk.exception.MenssageNotFoundException;

public interface ActionsService {

    Actions getActionsById(long id) throws MenssageNotFoundException;

    List<Actions> getAllActions();

    Actions updateActions(long id, Actions actions) throws MenssageNotFoundException;

    Actions saveActions(Actions actions);

    void deleteActions(long id) throws MenssageNotFoundException;

    long countActions();

    List<Actions> getActionsPage(int pag, int quant) throws MenssageNotFoundException;

    void deleteActionsTickets(Tickets tickets) throws MenssageNotFoundException;

    long countActionsTickets(Tickets tickets);

    
}
