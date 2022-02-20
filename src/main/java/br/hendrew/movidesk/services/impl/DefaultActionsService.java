package br.hendrew.movidesk.services.impl;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.hendrew.movidesk.entity.Actions;
import br.hendrew.movidesk.entity.Tickets;
import br.hendrew.movidesk.exception.MenssageNotFoundException;
import br.hendrew.movidesk.repository.ActionRepository;
import br.hendrew.movidesk.services.ActionsService;
import io.quarkus.panache.common.Page;

@ApplicationScoped
public class DefaultActionsService implements ActionsService{
  
    
    private final ActionRepository actionRepository;

    @Inject
    public DefaultActionsService(ActionRepository actionRepository) {
        this.actionRepository = actionRepository;

    }

    @Override
    public Actions getActionsById(long id) throws MenssageNotFoundException {
        return actionRepository.findByIdOptional(id)
                .orElseThrow(() -> new MenssageNotFoundException("There clients doesn't exist"));
    }


    public List<Actions> getActionsByTickets(Tickets tickets) {
        return actionRepository.findByTickets(tickets);
    }

    @Override
    public List<Actions> getAllActions() {
        return actionRepository.listAll();
    }

    @Transactional
    @Override
    public Actions updateActions(long id, Actions actions) throws MenssageNotFoundException {
        Actions existing = getActionsById(id);

        existing.setCreatedDate(actions.getCreatedDate());
        existing.setDescription(actions.getDescription());
        existing.setHtmlDescription(actions.getHtmlDescription());
        existing.setIsDeleted(actions.getIsDeleted());
        existing.setJustification(actions.getJustification());
        existing.setOrigin(actions.getOrigin());
        existing.setStatus(actions.getStatus());
        existing.setTickets(actions.getTickets());
        existing.setType(actions.getType());
        

        return existing;
    }

    @Transactional
    @Override
    public Actions saveActions(Actions actions) {
        actionRepository.persistAndFlush(actions);
        return actions;
    }

    @Transactional
    @Override
    public void deleteActions(long id) throws MenssageNotFoundException {
        actionRepository.delete(getActionsById(id));
    }

    @Transactional
    @Override
    public void deleteActionsTickets(Tickets tickets) throws MenssageNotFoundException {
        if(countActionsTickets(tickets) > 0 ){
            List<Actions> list = getActionsByTickets(tickets);
            for(int x=0; x < list.size(); x++){
                actionRepository.delete(list.get(x));
            }   
        }
       
    }

    @Override
    public long countActions() {
        return actionRepository.count();
    }

    @Override
    public long countActionsTickets(Tickets tickets) {
        return actionRepository.countTickets( tickets);
    }

    @Override
    public List<Actions> getActionsPage(int pag, int quant) throws MenssageNotFoundException {
        List<Actions> actions = actionRepository.findAll().page(Page.of(pag, quant)).list();
        return actions;
    }
    
}
