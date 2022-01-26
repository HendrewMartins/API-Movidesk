package br.hendrew.movidesk.validation;

import br.hendrew.movidesk.entity.Actions;
import br.hendrew.movidesk.entity.ActionsAux;
import br.hendrew.movidesk.entity.Tickets;
import br.hendrew.movidesk.entity.TicketsJson;

public class Converte {

    public Tickets converteTickets(TicketsJson ticketjson){
        Tickets tickets = new Tickets();
        tickets.setActionCount(ticketjson.getActionCount());
        tickets.setBaseStatus(ticketjson.getBaseStatus());
        tickets.setCategory(ticketjson.getCategory());
        tickets.setChatTalkTime(ticketjson.getChatTalkTime());
        tickets.setChatWaitingTime(ticketjson.getChatTalkTime());
        tickets.setClosedIn(ticketjson.getClosedIn());
        tickets.setCreatedDate(ticketjson.getCreatedDate());
        tickets.setDataTicket(ticketjson.getDataTicket());
        tickets.setHoraTicket(ticketjson.getHoraTicket());
        tickets.setId(ticketjson.getId());
        tickets.setJustification(ticketjson.getJustification());
        tickets.setLastActionDate(ticketjson.getLastActionDate());
        tickets.setLastUpdate(ticketjson.getLastUpdate());
        tickets.setLifeTimeWorkingTime(ticketjson.getLifeTimeWorkingTime());
        tickets.setOrigin(ticketjson.getOrigin());
        tickets.setOriginEmailAccount(ticketjson.getOriginEmailAccount());
        tickets.setOwner(ticketjson.getOwner());
        tickets.setOwnerTeam(ticketjson.getOwnerTeam());
        tickets.setProtocol(ticketjson.getProtocol());
        tickets.setReopenedIn(ticketjson.getReopenedIn());
        tickets.setResolvedIn(ticketjson.getResolvedIn());
        tickets.setResolvedInFirstCall(ticketjson.getResolvedInFirstCall());
        tickets.setSlaAgreement(ticketjson.getSlaAgreement());
        tickets.setSlaAgreementRule(ticketjson.getSlaAgreementRule());
        tickets.setSlaResponseTime(ticketjson.getSlaResponseTime());
        tickets.setSlaSolutionTime(ticketjson.getSlaSolutionTime());
        tickets.setStatus(ticketjson.getStatus());
        tickets.setStoppedTime(ticketjson.getStoppedTime());
        tickets.setStoppedTimeWorkingTime(ticketjson.getStoppedTimeWorkingTime());
        tickets.setSubject(ticketjson.getSubject());
        tickets.setType(ticketjson.getType());
        tickets.setUrgency(ticketjson.getUrgency());

        return tickets;
    }
    
    public Actions converteActions (ActionsAux actionsaux, Tickets tickets){
        Actions actions = new Actions();

        actions.setCreatedDate(actionsaux.getCreatedDate());
        actions.setDescription(actionsaux.getDescription());
        actions.setHtmlDescription(actionsaux.getHtmlDescription());
        actions.setId(actionsaux.getId());
        actions.setIsDeleted(actionsaux.getIsDeleted());
        actions.setJustification(actionsaux.getJustification());
        actions.setOrigin(actionsaux.getOrigin());
        actions.setStatus(actionsaux.getStatus());
        actions.setTickets(tickets);
        actions.setType(actionsaux.getType());


        return actions;
    }

}
