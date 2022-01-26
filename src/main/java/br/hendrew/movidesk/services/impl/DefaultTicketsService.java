package br.hendrew.movidesk.services.impl;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.hendrew.movidesk.entity.AgenteCategory;
import br.hendrew.movidesk.entity.AgenteJustification;
import br.hendrew.movidesk.entity.AgenteTickets;
import br.hendrew.movidesk.entity.Category;
import br.hendrew.movidesk.entity.Justification;
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
            if (tickets != null) {
                return true;
            } else {
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
        SimpleDateFormat formatadorHora = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatadorTime = new SimpleDateFormat("HH:mm:ss");
        java.util.Date util_Date = new java.util.Date();
        java.util.Date util_time = new java.util.Date();

        String dataStr;
        String timeStr;

        Tickets existing = getTicketsById(id);

        existing.setBaseStatus(tickets.getBaseStatus());
        existing.setCategory(tickets.getCategory());
        existing.setCreatedDate(tickets.getCreatedDate());
        existing.setOwner(tickets.getOwner());
        existing.setStatus(tickets.getStatus());
        existing.setSubject(tickets.getSubject());
        existing.setType(tickets.getType());
        existing.setActionCount(tickets.getActionCount());
        existing.setChatTalkTime(tickets.getChatTalkTime());
        existing.setChatWaitingTime(tickets.getChatWaitingTime());
        existing.setClosedIn(tickets.getClosedIn());
        existing.setJustification(tickets.getJustification());
        existing.setLastActionDate(tickets.getLastActionDate());
        existing.setLastUpdate(tickets.getLastUpdate());
        existing.setLifeTimeWorkingTime(tickets.getLifeTimeWorkingTime());
        existing.setOrigin(tickets.getOrigin());
        existing.setOriginEmailAccount(tickets.getOriginEmailAccount());
        existing.setOwnerTeam(tickets.getOwnerTeam());
        existing.setProtocol(tickets.getProtocol());
        existing.setReopenedIn(tickets.getReopenedIn());
        existing.setResolvedIn(tickets.getResolvedIn());
        existing.setResolvedInFirstCall(tickets.getResolvedInFirstCall());
        existing.setSlaAgreement(tickets.getSlaAgreement());
        existing.setSlaAgreementRule(tickets.getSlaAgreementRule());
        existing.setSlaResponseTime(tickets.getSlaResponseTime());
        existing.setSlaSolutionTime(tickets.getSlaSolutionTime());
        existing.setStoppedTime(tickets.getStoppedTime());
        existing.setStoppedTimeWorkingTime(tickets.getStoppedTimeWorkingTime());

        // obtem data e hora em string
        dataStr = tickets.getCreatedDate().substring(0, 10);
        timeStr = tickets.getCreatedDate().substring(11, 19);

        // converte string em data
        try {
            util_Date = formatadorHora.parse(dataStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // converte string em hora
        try {
            util_time = formatadorTime.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Time time = new Time(util_time.getTime());

        existing.setDataTicket(util_Date);
        existing.setHoraTicket(time);
        return existing;
    }

    @Transactional
    @Override
    public Tickets saveTickets(Tickets tickets) {
        SimpleDateFormat formatadorHora = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatadorTime = new SimpleDateFormat("HH:mm:ss");
        java.util.Date util_Date = new java.util.Date();
        java.util.Date util_time = new java.util.Date();

        String dataStr;
        String timeStr;

        // valida se existe agente
        Owner owner = ownerRepository.findByStringId(tickets.getOwner().getId());
        if (owner == null) {
            ownerRepository.persistAndFlush(tickets.getOwner());
        }

        // obtem data e hora em string
        dataStr = tickets.getCreatedDate().substring(0, 10);
        timeStr = tickets.getCreatedDate().substring(11, 19);

        // converte string em data
        try {
            util_Date = formatadorHora.parse(dataStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // converte string em hora
        try {
            util_time = formatadorTime.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Time time = new Time(util_time.getTime());

        tickets.setDataTicket(util_Date);
        tickets.setHoraTicket(time);
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
    public TicketsSituacao getTicketsbaseStatusSUM() throws MenssageNotFoundException {
        TicketsSituacao sum = new TicketsSituacao();
        List<Tickets> listTickets = new ArrayList<Tickets>();

        // listTickets = ticketsRepository.findBybaseStatus("Canceled");
        // sum.setCanceled(listTickets.size());
        sum.setCanceled(0);

        // listTickets = ticketsRepository.findBybaseStatus("Closed");
        // sum.setClosed(listTickets.size());
        sum.setClosed(0);

        listTickets = ticketsRepository.findBybaseStatus("InAttendance");
        sum.setInAttendance(listTickets.size());

        listTickets = ticketsRepository.findBybaseStatus("New");
        sum.setNewReg(listTickets.size());

        // listTickets = ticketsRepository.findBybaseStatus("Resolved");
        // sum.setResolved(listTickets.size());
        sum.setResolved(0);

        listTickets = ticketsRepository.findBybaseStatus("Stopped");
        sum.setStopped(listTickets.size());
        return sum;
    }

    @Override
    public TicketsUrgency getTicketsUrgencySUM() throws MenssageNotFoundException {
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
    public TicketsType getTicketsTypeSUM() throws MenssageNotFoundException {
        TicketsType sum = new TicketsType();
        List<Tickets> listTickets = new ArrayList<Tickets>();

        listTickets = ticketsRepository.findBytype(1);
        sum.setInterno(listTickets.size());

        listTickets = ticketsRepository.findBytype(2);
        sum.setExterno(listTickets.size());

        return sum;
    }

    @Override
    public List<AgenteTickets> OwnerTickets() throws MenssageNotFoundException {
        List<AgenteTickets> agente = new ArrayList<AgenteTickets>();

        List<Owner> owner = ownerRepository.listAll();

        List<Tickets> ticketsInAttendance = ticketsRepository.findBybaseStatus("InAttendance");
        List<Tickets> ticketsNew = ticketsRepository.findBybaseStatus("New");
        List<Tickets> ticketsStopped = ticketsRepository.findBybaseStatus("Stopped");

        int quantOwner = 0;

        for (int i = 0; i < owner.size(); i++) {

            long quantInAttendance = 0;
            long quantNew = 0;
            long quantStopped = 0;

            for (int x = 0; x < ticketsInAttendance.size(); x++) {
                if (owner.get(i) == ticketsInAttendance.get(x).getOwner()) {
                    quantInAttendance++;
                }
            }

            for (int x = 0; x < ticketsNew.size(); x++) {
                if (owner.get(i) == ticketsNew.get(x).getOwner()) {
                    quantNew++;
                }
            }

            for (int x = 0; x < ticketsStopped.size(); x++) {
                if (owner.get(i) == ticketsStopped.get(x).getOwner()) {
                    quantStopped++;
                }
            }

            if ((quantInAttendance > 0) || (quantNew > 0) || (quantStopped > 0)) {

                AgenteTickets agenteTemp = new AgenteTickets();
                agenteTemp.idAgente = owner.get(i).getId();
                agenteTemp.businessName = owner.get(i).getBusinessName();
                agenteTemp.quantTicketsInAttendance = quantInAttendance;
                agenteTemp.quantTicketsNew = quantNew;
                agenteTemp.quantTicketsStopped = quantStopped;

                agente.add(quantOwner, agenteTemp);

                quantOwner++;
            }
        }

        return agente;
    }

    @Override
    public List<AgenteCategory> OwnerCategory() throws MenssageNotFoundException {
        List<AgenteCategory> agente = new ArrayList<AgenteCategory>();
        int quantOwner = 0;

        List<Owner> owner = ownerRepository.listAll();
        List<Tickets> ticketsCustomizacao = ticketsRepository.findByCategory("CustomizaÃ§Ã£o");
        List<Tickets> ticketsDuvida = ticketsRepository.findByCategory("DÃºvida");
        List<Tickets> ticketsFalha = ticketsRepository.findByCategory("Falha");
        List<Tickets> ticketsHomologacao = ticketsRepository.findByCategory("HomologaÃ§Ã£o");
        List<Tickets> ticketsImplantacao = ticketsRepository.findByCategory("SolicitaÃ§Ã£o de ImplantaÃ§Ã£o");
        List<Tickets> ticketsLicitacao = ticketsRepository.findByCategory("LicitaÃ§Ã£o");
        List<Tickets> ticketsSemCategoria = ticketsRepository.findBySemCategoria("New");
        List<Tickets> ticketsServico = ticketsRepository.findByCategory("SolicitaÃ§Ã£o de ServiÃ§o");
        List<Tickets> ticketsSugestao = ticketsRepository.findByCategory("SugestÃ£o");
        List<Tickets> ticketsTreinamento = ticketsRepository.findByCategory("SolicitaÃ§Ã£o de Treinamento");
        List<Tickets> ticketsTreinamentoRemoto = ticketsRepository
                .findByCategory("SolicitaÃ§Ã£o de Treinamento Online (Remoto)");

        for (int i = 0; i < owner.size(); i++) {

            long quantCustom = 0;
            long quantDuvida = 0;
            long quantFalha = 0;
            long quantHomolo = 0;
            long quantImplan = 0;
            long quantLicita = 0;
            long quantSCateg = 0;
            long quantServic = 0;
            long quantSugest = 0;
            long quantTreina = 0;
            long quantOnline = 0;

            for (int x = 0; x < ticketsCustomizacao.size(); x++) {
                if (owner.get(i) == ticketsCustomizacao.get(x).getOwner()) {
                    quantCustom++;
                }
            }
            for (int x = 0; x < ticketsDuvida.size(); x++) {
                if (owner.get(i) == ticketsDuvida.get(x).getOwner()) {
                    quantDuvida++;
                }
            }
            for (int x = 0; x < ticketsFalha.size(); x++) {
                if (owner.get(i) == ticketsFalha.get(x).getOwner()) {
                    quantFalha++;
                }
            }
            for (int x = 0; x < ticketsHomologacao.size(); x++) {
                if (owner.get(i) == ticketsHomologacao.get(x).getOwner()) {
                    quantHomolo++;
                }
            }
            for (int x = 0; x < ticketsImplantacao.size(); x++) {
                if (owner.get(i) == ticketsImplantacao.get(x).getOwner()) {
                    quantImplan++;
                }
            }
            for (int x = 0; x < ticketsLicitacao.size(); x++) {
                if (owner.get(i) == ticketsLicitacao.get(x).getOwner()) {
                    quantLicita++;
                }
            }
            for (int x = 0; x < ticketsSemCategoria.size(); x++) {
                if (owner.get(i) == ticketsSemCategoria.get(x).getOwner()) {
                    quantSCateg++;
                }
            }
            for (int x = 0; x < ticketsServico.size(); x++) {
                if (owner.get(i) == ticketsServico.get(x).getOwner()) {
                    quantServic++;
                }
            }
            for (int x = 0; x < ticketsSugestao.size(); x++) {
                if (owner.get(i) == ticketsSugestao.get(x).getOwner()) {
                    quantSugest++;
                }
            }
            for (int x = 0; x < ticketsTreinamento.size(); x++) {
                if (owner.get(i) == ticketsTreinamento.get(x).getOwner()) {
                    quantTreina++;
                }
            }
            for (int x = 0; x < ticketsTreinamentoRemoto.size(); x++) {
                if (owner.get(i) == ticketsTreinamentoRemoto.get(x).getOwner()) {
                    quantOnline++;
                }
            }

            if ((quantCustom > 0) || (quantDuvida > 0) || (quantFalha > 0) ||
                    (quantHomolo > 0) || (quantImplan > 0) || (quantLicita > 0) ||
                    (quantSCateg > 0) || (quantServic > 0) || (quantSugest > 0) ||
                    (quantTreina > 0) || (quantOnline > 0)) {

                AgenteCategory agenteTemp = new AgenteCategory();
                agenteTemp.idAgente = owner.get(i).getId();
                agenteTemp.businessName = owner.get(i).getBusinessName();
                agenteTemp.customizacao = quantCustom;
                agenteTemp.duvida = quantDuvida;
                agenteTemp.falha = quantFalha;
                agenteTemp.homologacao = quantHomolo;
                agenteTemp.implantacao = quantImplan;
                agenteTemp.licitacao = quantLicita;
                agenteTemp.semCategoria = quantSCateg;
                agenteTemp.solicitacaoServico = quantServic;
                agenteTemp.solicitacaoTreinamento = quantTreina;
                agenteTemp.sugestao = quantSugest;
                agenteTemp.treinamentoOnline = quantOnline;

                agente.add(quantOwner, agenteTemp);

                quantOwner++;
            }
        }

        return agente;
    }

    public Category Category() throws MenssageNotFoundException {
        Category cat = new Category();

        List<Tickets> ticketsCustomizacao = ticketsRepository.findByCategory("CustomizaÃ§Ã£o");
        List<Tickets> ticketsDuvida = ticketsRepository.findByCategory("DÃºvida");
        List<Tickets> ticketsFalha = ticketsRepository.findByCategory("Falha");
        List<Tickets> ticketsHomologacao = ticketsRepository.findByCategory("HomologaÃ§Ã£o");
        List<Tickets> ticketsImplantacao = ticketsRepository.findByCategory("SolicitaÃ§Ã£o de ImplantaÃ§Ã£o");
        List<Tickets> ticketsLicitacao = ticketsRepository.findByCategory("LicitaÃ§Ã£o");
        List<Tickets> ticketsSemCategoria = ticketsRepository.findBySemCategoria("New");
        List<Tickets> ticketsServico = ticketsRepository.findByCategory("SolicitaÃ§Ã£o de ServiÃ§o");
        List<Tickets> ticketsSugestao = ticketsRepository.findByCategory("SugestÃ£o");
        List<Tickets> ticketsTreinamento = ticketsRepository.findByCategory("SolicitaÃ§Ã£o de Treinamento");
        List<Tickets> ticketsTreinamentoRemoto = ticketsRepository
                .findByCategory("SolicitaÃ§Ã£o de Treinamento Online (Remoto)");

        cat.customizacao = ticketsCustomizacao.size();
        cat.duvida = ticketsDuvida.size();
        cat.falha = ticketsFalha.size();
        cat.homologacao = ticketsHomologacao.size();
        cat.implantacao = ticketsImplantacao.size();
        cat.licitacao = ticketsLicitacao.size();
        cat.semCategoria = ticketsSemCategoria.size();
        cat.solicitacaoServico = ticketsServico.size();
        cat.sugestao = ticketsSugestao.size();
        cat.solicitacaoTreinamento = ticketsTreinamento.size();
        cat.treinamentoOnline = ticketsTreinamentoRemoto.size();

        return cat;
    }

    @Override
    public List<AgenteJustification> ownerJustifications() throws MenssageNotFoundException {
        List<AgenteJustification> agentJusti = new ArrayList<AgenteJustification>();

        int quantOwner = 0;

        List<Owner> owner = ownerRepository.listAll();

        List<Tickets> ticketsparalisado = ticketsRepository.findByJustification("Paralisado");
        List<Tickets> ticketssemjustification = ticketsRepository.findBySemJustification("New");
        List<Tickets> ticketsaguarDesenv = ticketsRepository.findByJustification("Aguardando Desenvolvimento");
        List<Tickets> ticketsemAnalise = ticketsRepository.findByJustification("Em anÃ¡lise");
        List<Tickets> ticketsaguardEscla = ticketsRepository.findByJustification("Aguardando Esclarecimentos");
        List<Tickets> ticketsresponClien = ticketsRepository.findByJustification("Respondido pelo Cliente");
        List<Tickets> ticketsemVerificacao = ticketsRepository.findByJustification("Em VerificaÃ§Ã£o");
        List<Tickets> ticketstreinaAgendad = ticketsRepository.findByJustification("Treinamento Agendado");
        List<Tickets> ticketsaprovado = ticketsRepository.findByJustification("Aprovado");
        List<Tickets> ticketsemValidSupor = ticketsRepository.findByJustification("Em validaÃ§Ã£o com Suporte");
        List<Tickets> ticketsaguardAtualiz = ticketsRepository.findByJustification("Aguardando AtualizaÃ§Ã£o");
        List<Tickets> ticketsaguardAprovac = ticketsRepository.findByJustification("Aguardando AprovaÃ§Ã£o");
        List<Tickets> ticketsaguardInfra = ticketsRepository.findByJustification("Aguardando Infraestrutura");
        List<Tickets> ticketsaguardAnalis = ticketsRepository.findByJustification("Aguardando AnÃ¡lise");
        List<Tickets> ticketsemTestes = ticketsRepository.findByJustification("Em testes");
        List<Tickets> ticketsaguardImpacto = ticketsRepository.findByJustification("Aguardando anÃ¡lise de impacto");
        List<Tickets> ticketsaguardEnVersao = ticketsRepository.findByJustification("Aguardando encaixe em VersÃ£o");
        List<Tickets> ticketsimpaAvaliado = ticketsRepository.findByJustification("Impacto avaliado");
        List<Tickets> ticketsaguardOrcament = ticketsRepository.findByJustification("Aguardando OrÃ§amento");
        List<Tickets> ticketsemVerificaInter = ticketsRepository
                .findByJustification("Em verificaÃ§Ã£o com equipe interna");
        List<Tickets> ticketsaguardCompilac = ticketsRepository.findByJustification("Aguardando compilaÃ§Ã£o");
        List<Tickets> ticketsemDesenvolviment = ticketsRepository.findByJustification("Em Desenvolvimento");
        List<Tickets> ticketsaguardFaturame = ticketsRepository.findByJustification("Aguardando Faturamento");
        List<Tickets> ticketsaguardInfParCli = ticketsRepository
                .findByJustification("Aguardando InformaÃ§Ãµes de Clientes e Parceiros");
        List<Tickets> ticketsdesenvolvido = ticketsRepository.findByJustification("Desenvolvido");
        List<Tickets> ticketsaguarAprovPreAnali = ticketsRepository
                .findByJustification("Aguardando aprovaÃ§Ã£o da prÃ© anÃ¡lise");
        List<Tickets> ticketsaguarEsclaSup = ticketsRepository
                .findByJustification("Aguardando Esclarecimentos do Suporte");
        List<Tickets> ticketsaguarValidParc = ticketsRepository
                .findByJustification("Aguardando validaÃ§Ã£o de parceiro/IDS");
        List<Tickets> ticketsagendVistTecn = ticketsRepository.findByJustification("Agendada Visita TÃ©cnica");
        List<Tickets> ticketspreAnalAprov = ticketsRepository.findByJustification("PrÃ© anÃ¡lise aprovada");
        List<Tickets> ticketsenvAnalise = ticketsRepository.findByJustification("Enviado para anÃ¡lise");
        List<Tickets> ticketsaguarRetClien = ticketsRepository.findByJustification("Aguardando retorno do Cliente");

        for (int i = 0; i < owner.size(); i++) {
            long quantparalisado = 0;
            long quantsemjustification = 0;
            long quantaguarDesenv = 0;
            long quantemAnalise = 0;
            long quantaguardEscla = 0;
            long quantresponClien = 0;
            long quantemVerificacao = 0;
            long quanttreinaAgendad = 0;
            long quantaprovado = 0;
            long quantemValidSupor = 0;
            long quantaguardAtualiz = 0;
            long quantaguardAprovac = 0;
            long quantaguardInfra = 0;
            long quantaguardAnalis = 0;
            long quantemTestes = 0;
            long quantaguardImpacto = 0;
            long quantaguardEnVersao = 0;
            long quantimpaAvaliado = 0;
            long quantaguardOrcament = 0;
            long quantemVerificaInter = 0;
            long quantaguardCompilac = 0;
            long quantemDesenvolviment = 0;
            long quantaguardFaturame = 0;
            long quantaguardInfParCli = 0;
            long quantdesenvolvido = 0;
            long quantaguarAprovPreAnali = 0;
            long quantaguarEsclaSup = 0;
            long quantaguarValidParc = 0;
            long quantagendVistTecn = 0;
            long quantpreAnalAprov = 0;
            long quantenvAnalise = 0;
            long quantaguarRetClien = 0;

            for(int x = 0; x < ticketsparalisado.size(); x++){
                if(owner.get(i) == ticketsparalisado.get(x).getOwner()){
                    quantparalisado++;
                }
            }

            for(int x = 0; x < ticketssemjustification.size(); x++){
                if(owner.get(i) == ticketssemjustification.get(x).getOwner()){
                    quantsemjustification++;
                }
            }

            for(int x = 0; x < ticketsaguarDesenv.size(); x++){
                if(owner.get(i) == ticketsaguarDesenv.get(x).getOwner()){
                    quantaguarDesenv++;
                }
            }

            for(int x = 0; x < ticketsemAnalise.size(); x++){
                if(owner.get(i) == ticketsemAnalise.get(x).getOwner()){
                    quantemAnalise++;
                }
            }
            
            
            for(int x = 0; x < ticketsaguardEscla.size(); x++){
                if(owner.get(i) == ticketsaguardEscla.get(x).getOwner()){
                    quantaguardEscla++;
                }
            }

            for(int x = 0; x < ticketsresponClien.size(); x++){
                if(owner.get(i) == ticketsresponClien.get(x).getOwner()){
                    quantresponClien++;
                }
            }
            for(int x = 0; x < ticketsemVerificacao.size(); x++){
                if(owner.get(i) == ticketsemVerificacao.get(x).getOwner()){
                    quantemVerificacao++;
                }
            }

            for(int x = 0; x < ticketstreinaAgendad.size(); x++){
                if(owner.get(i) == ticketstreinaAgendad.get(x).getOwner()){
                    quanttreinaAgendad++;
                }
            }

            for(int x = 0; x < ticketsaprovado.size(); x++){
                if(owner.get(i) == ticketsaprovado.get(x).getOwner()){
                    quantaprovado++;
                }
            }

            for(int x = 0; x < ticketsemValidSupor.size(); x++){
                if(owner.get(i) == ticketsemValidSupor.get(x).getOwner()){
                    quantemValidSupor++;
                }
            }

            for(int x = 0; x < ticketsaguardAtualiz.size(); x++){
                if(owner.get(i) == ticketsaguardAtualiz.get(x).getOwner()){
                    quantaguardAtualiz++;
                }
            }

            for(int x = 0; x < ticketsaguardAprovac.size(); x++){
                if(owner.get(i) == ticketsaguardAprovac.get(x).getOwner()){
                    quantaguardAprovac++;
                }
            }
            
            for(int x = 0; x < ticketsaguardInfra.size(); x++){
                if(owner.get(i) == ticketsaguardInfra.get(x).getOwner()){
                    quantaguardInfra++;
                }
            }

            for(int x = 0; x < ticketsaguardAnalis.size(); x++){
                if(owner.get(i) == ticketsaguardAnalis.get(x).getOwner()){
                    quantaguardAnalis++;
                }
            }
           
            for(int x = 0; x < ticketsemTestes.size(); x++){
                if(owner.get(i) == ticketsemTestes.get(x).getOwner()){
                    quantemTestes++;
                }
            }

            for(int x = 0; x < ticketsaguardImpacto.size(); x++){
                if(owner.get(i) == ticketsaguardImpacto.get(x).getOwner()){
                    quantaguardImpacto++;
                }
            }
            for(int x = 0; x < ticketsaguardEnVersao.size(); x++){
                if(owner.get(i) == ticketsaguardEnVersao.get(x).getOwner()){
                    quantaguardEnVersao++;
                }
            }
            for(int x = 0; x < ticketsimpaAvaliado.size(); x++){
                if(owner.get(i) == ticketsimpaAvaliado.get(x).getOwner()){
                    quantimpaAvaliado++;
                }
            }
            for(int x = 0; x < ticketsaguardOrcament.size(); x++){
                if(owner.get(i) == ticketsaguardOrcament.get(x).getOwner()){
                    quantaguardOrcament++;
                }
            }
            for(int x = 0; x < ticketsemVerificaInter.size(); x++){
                if(owner.get(i) == ticketsemVerificaInter.get(x).getOwner()){
                    quantemVerificaInter++;
                }
            }
            for(int x = 0; x < ticketsaguardCompilac.size(); x++){
                if(owner.get(i) == ticketsaguardCompilac.get(x).getOwner()){
                    quantaguardCompilac++;
                }
            }
            for(int x = 0; x < ticketsemDesenvolviment.size(); x++){
                if(owner.get(i) == ticketsemDesenvolviment.get(x).getOwner()){
                    quantemDesenvolviment++;
                }
            }
            for(int x = 0; x < ticketsaguardFaturame.size(); x++){
                if(owner.get(i) == ticketsaguardFaturame.get(x).getOwner()){
                    quantaguardFaturame++;
                }
            }

            for(int x = 0; x < ticketsaguardInfParCli.size(); x++){
                if(owner.get(i) == ticketsaguardInfParCli.get(x).getOwner()){
                    quantaguardInfParCli++;
                }
            }

            for(int x = 0; x < ticketsdesenvolvido.size(); x++){
                if(owner.get(i) == ticketsdesenvolvido.get(x).getOwner()){
                    quantdesenvolvido++;
                }
            }

            for(int x = 0; x < ticketsaguarAprovPreAnali.size(); x++){
                if(owner.get(i) == ticketsaguarAprovPreAnali.get(x).getOwner()){
                    quantaguarAprovPreAnali++;
                }
            }
            
            for(int x = 0; x < ticketsaguarEsclaSup.size(); x++){
                if(owner.get(i) == ticketsaguarEsclaSup.get(x).getOwner()){
                    quantaguarEsclaSup++;
                }
            }

            for(int x = 0; x < ticketsaguarEsclaSup.size(); x++){
                if(owner.get(i) == ticketsaguarEsclaSup.get(x).getOwner()){
                    quantaguarEsclaSup++;
                }
            }

            for(int x = 0; x < ticketsaguarValidParc.size(); x++){
                if(owner.get(i) == ticketsaguarValidParc.get(x).getOwner()){
                    quantaguarValidParc++;
                }
            }

            for(int x = 0; x < ticketsagendVistTecn.size(); x++){
                if(owner.get(i) == ticketsagendVistTecn.get(x).getOwner()){
                    quantagendVistTecn++;
                }
            }

            for(int x = 0; x < ticketspreAnalAprov.size(); x++){
                if(owner.get(i) == ticketspreAnalAprov.get(x).getOwner()){
                    quantpreAnalAprov++;
                }
            }

            for(int x = 0; x < ticketsenvAnalise.size(); x++){
                if(owner.get(i) == ticketsenvAnalise.get(x).getOwner()){
                    quantenvAnalise++;
                }
            }

            for(int x = 0; x < ticketsaguarRetClien.size(); x++){
                if(owner.get(i) == ticketsaguarRetClien.get(x).getOwner()){
                    quantaguarRetClien++;
                }
            }

            

            if ((quantparalisado > 0) || (quantsemjustification > 0) || (quantaguarDesenv > 0) ||
                    (quantemAnalise > 0) || (quantaguardEscla > 0) || (quantresponClien > 0) ||
                    (quantemVerificacao > 0) || (quanttreinaAgendad > 0) || (quantaprovado > 0) ||
                    (quantemValidSupor > 0) || (quantaguardAtualiz > 0) || (quantaguardAprovac > 0) ||
                    (quantaguardInfra > 0) || (quantaguardAnalis > 0) || (quantemTestes > 0) ||
                    (quantaguardImpacto > 0) || (quantaguardEnVersao > 0) || (quantimpaAvaliado > 0) ||
                    (quantaguardOrcament > 0) || (quantemVerificaInter > 0) || (quantaguardCompilac > 0) ||
                    (quantemDesenvolviment > 0) || (quantaguardFaturame > 0) || (quantaguardInfParCli > 0) ||
                    (quantdesenvolvido > 0) || (quantaguarAprovPreAnali > 0) || (quantaguarEsclaSup > 0) ||
                    (quantaguarValidParc > 0) || (quantagendVistTecn > 0) || (quantpreAnalAprov > 0)
                    || (quantenvAnalise > 0) ||
                    (quantaguarRetClien > 0)) {

                AgenteJustification agenteTemp = new AgenteJustification();
                agenteTemp.idAgente = owner.get(i).getId();
                agenteTemp.businessName = owner.get(i).getBusinessName();
                agenteTemp.paralisado = quantparalisado;
                agenteTemp.semjustification = quantsemjustification;
                agenteTemp.aguarDesenv = quantaguarDesenv;
                agenteTemp.emAnalise = quantemAnalise;
                agenteTemp.aguardEscla = quantaguardEscla;
                agenteTemp.responClien = quantresponClien;
                agenteTemp.emVerificacao = quantemVerificacao;
                agenteTemp.treinaAgendad = quanttreinaAgendad;
                agenteTemp.aprovado = quantaprovado;
                agenteTemp.emValidSupor = quantemValidSupor;
                agenteTemp.aguardAtualiz = quantaguardAtualiz;
                agenteTemp.aguardAprovac = quantaguardAprovac;
                agenteTemp.aguardInfra = quantaguardInfra;
                agenteTemp.aguardAnalis = quantaguardAnalis;
                agenteTemp.emTestes = quantemTestes;
                agenteTemp.aguardImpacto = quantaguardImpacto;
                agenteTemp.aguardEnVersao = quantaguardEnVersao;
                agenteTemp.impaAvaliado = quantimpaAvaliado;
                agenteTemp.aguardOrcament = quantaguardOrcament;
                agenteTemp.emVerificaInter = quantemVerificaInter;
                agenteTemp.aguardCompilac = quantaguardCompilac;
                agenteTemp.emDesenvolviment = quantemDesenvolviment;
                agenteTemp.aguardFaturame = quantaguardFaturame;
                agenteTemp.aguardInfParCli = quantaguardInfParCli;
                agenteTemp.desenvolvido = quantdesenvolvido;
                agenteTemp.aguarAprovPreAnali = quantaguarAprovPreAnali;
                agenteTemp.aguarEsclaSup = quantaguarEsclaSup;
                agenteTemp.aguarValidParc = quantaguarValidParc;
                agenteTemp.agendVistTecn = quantagendVistTecn;
                agenteTemp.preAnalAprov = quantpreAnalAprov;
                agenteTemp.envAnalise = quantenvAnalise;
                agenteTemp.aguarRetClien = quantaguarRetClien;

                agentJusti.add(quantOwner, agenteTemp);
                quantOwner++;

            }
        }

        return agentJusti;
    }

    @Override
    public Justification justification() throws MenssageNotFoundException {
        Justification justification = new Justification();

        List<Tickets> ticketsparalisado = ticketsRepository.findByJustification("Paralisado");
        List<Tickets> ticketssemjustification = ticketsRepository.findBySemJustification("New");
        List<Tickets> ticketsaguarDesenv = ticketsRepository.findByJustification("Aguardando Desenvolvimento");
        List<Tickets> ticketsemAnalise = ticketsRepository.findByJustification("Em anÃ¡lise");
        List<Tickets> ticketsaguardEscla = ticketsRepository.findByJustification("Aguardando Esclarecimentos");
        List<Tickets> ticketsresponClien = ticketsRepository.findByJustification("Respondido pelo Cliente");
        List<Tickets> ticketsemVerificacao = ticketsRepository.findByJustification("Em VerificaÃ§Ã£o");
        List<Tickets> ticketstreinaAgendad = ticketsRepository.findByJustification("Treinamento Agendado");
        List<Tickets> ticketsaprovado = ticketsRepository.findByJustification("Aprovado");
        List<Tickets> ticketsemValidSupor = ticketsRepository.findByJustification("Em validaÃ§Ã£o com Suporte");
        List<Tickets> ticketsaguardAtualiz = ticketsRepository.findByJustification("Aguardando AtualizaÃ§Ã£o");
        List<Tickets> ticketsaguardAprovac = ticketsRepository.findByJustification("Aguardando AprovaÃ§Ã£o");
        List<Tickets> ticketsaguardInfra = ticketsRepository.findByJustification("Aguardando Infraestrutura");
        List<Tickets> ticketsaguardAnalis = ticketsRepository.findByJustification("Aguardando AnÃ¡lise");
        List<Tickets> ticketsemTestes = ticketsRepository.findByJustification("Em testes");
        List<Tickets> ticketsaguardImpacto = ticketsRepository.findByJustification("Aguardando anÃ¡lise de impacto");
        List<Tickets> ticketsaguardEnVersao = ticketsRepository.findByJustification("Aguardando encaixe em VersÃ£o");
        List<Tickets> ticketsimpaAvaliado = ticketsRepository.findByJustification("Impacto avaliado");
        List<Tickets> ticketsaguardOrcament = ticketsRepository.findByJustification("Aguardando OrÃ§amento");
        List<Tickets> ticketsemVerificaInter = ticketsRepository
                .findByJustification("Em verificaÃ§Ã£o com equipe interna");
        List<Tickets> ticketsaguardCompilac = ticketsRepository.findByJustification("Aguardando compilaÃ§Ã£o");
        List<Tickets> ticketsemDesenvolviment = ticketsRepository.findByJustification("Em Desenvolvimento");
        List<Tickets> ticketsaguardFaturame = ticketsRepository.findByJustification("Aguardando Faturamento");
        List<Tickets> ticketsaguardInfParCli = ticketsRepository
                .findByJustification("Aguardando InformaÃ§Ãµes de Clientes e Parceiros");
        List<Tickets> ticketsdesenvolvido = ticketsRepository.findByJustification("Desenvolvido");
        List<Tickets> ticketsaguarAprovPreAnali = ticketsRepository
                .findByJustification("Aguardando aprovaÃ§Ã£o da prÃ© anÃ¡lise");
        List<Tickets> ticketsaguarEsclaSup = ticketsRepository
                .findByJustification("Aguardando Esclarecimentos do Suporte");
        List<Tickets> ticketsaguarValidParc = ticketsRepository
                .findByJustification("Aguardando validaÃ§Ã£o de parceiro/IDS");
        List<Tickets> ticketsagendVistTecn = ticketsRepository.findByJustification("Agendada Visita TÃ©cnica");
        List<Tickets> ticketspreAnalAprov = ticketsRepository.findByJustification("PrÃ© anÃ¡lise aprovada");
        List<Tickets> ticketsenvAnalise = ticketsRepository.findByJustification("Enviado para anÃ¡lise");
        List<Tickets> ticketsaguarRetClien = ticketsRepository.findByJustification("Aguardando retorno do Cliente");

        justification.paralisado = ticketsparalisado.size();
        justification.semjustification = ticketssemjustification.size();
        justification.aguarDesenv = ticketsaguarDesenv.size();
        justification.emAnalise = ticketsemAnalise.size();
        justification.aguardEscla = ticketsaguardEscla.size();
        justification.responClien = ticketsresponClien.size();
        justification.emVerificacao = ticketsemVerificacao.size();
        justification.treinaAgendad = ticketstreinaAgendad.size();
        justification.aprovado = ticketsaprovado.size();
        justification.emValidSupor = ticketsemValidSupor.size();
        justification.aguardAtualiz = ticketsaguardAtualiz.size();
        justification.aguardAprovac = ticketsaguardAprovac.size();
        justification.aguardInfra = ticketsaguardInfra.size();
        justification.aguardAnalis = ticketsaguardAnalis.size();
        justification.emTestes = ticketsemTestes.size();
        justification.aguardImpacto = ticketsaguardImpacto.size();
        justification.aguardEnVersao = ticketsaguardEnVersao.size();
        justification.impaAvaliado = ticketsimpaAvaliado.size();
        justification.aguardOrcament = ticketsaguardOrcament.size();
        justification.emVerificaInter = ticketsemVerificaInter.size();
        justification.aguardCompilac = ticketsaguardCompilac.size();
        justification.emDesenvolviment = ticketsemDesenvolviment.size();
        justification.aguardFaturame = ticketsaguardFaturame.size();
        justification.aguardInfParCli = ticketsaguardInfParCli.size();
        justification.desenvolvido = ticketsdesenvolvido.size();
        justification.aguarAprovPreAnali = ticketsaguarAprovPreAnali.size();
        justification.aguarEsclaSup = ticketsaguarEsclaSup.size();
        justification.aguarValidParc = ticketsaguarValidParc.size();
        justification.agendVistTecn = ticketsagendVistTecn.size();
        justification.preAnalAprov = ticketspreAnalAprov.size();
        justification.envAnalise = ticketsenvAnalise.size();
        justification.aguarRetClien = ticketsaguarRetClien.size();

        return justification;
    }
}
