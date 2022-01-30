package br.hendrew.movidesk.services.impl;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
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
import br.hendrew.movidesk.entity.TicketsAnos;
import br.hendrew.movidesk.entity.TicketsAnosCategory;
import br.hendrew.movidesk.entity.TicketsMesesDias;
import br.hendrew.movidesk.entity.TicketsSituacao;
import br.hendrew.movidesk.entity.TicketsType;
import br.hendrew.movidesk.entity.TicketsUrgency;
import br.hendrew.movidesk.exception.MenssageNotFoundException;
import br.hendrew.movidesk.repository.OwnerRepository;
import br.hendrew.movidesk.repository.TicketsRepository;
import br.hendrew.movidesk.services.TicketsService;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;

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

        long valuehoras = Long.valueOf(timeStr.substring(0, 2));
        long valueminutos = Long.valueOf(timeStr.substring(3, 5));
        long valuesegundos = Long.valueOf(timeStr.substring(6, 8));

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

        if (valuehoras <= 3) {
            if (valuehoras != 3) {
                Calendar dt = Calendar.getInstance();
                dt.setTime(util_Date);
                dt.add(Calendar.DAY_OF_MONTH, -1);
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                dataStr = df.format(dt.getTime());

                try {
                    util_Date = formatadorHora.parse(dataStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                if (valueminutos < 1) {
                    if (valuesegundos < 1) {
                        Calendar dt = Calendar.getInstance();
                        dt.setTime(util_Date);
                        dt.add(Calendar.DAY_OF_MONTH, -1);
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        dataStr = df.format(dt.getTime());
                        try {
                            util_Date = formatadorHora.parse(dataStr);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }

        Calendar d = Calendar.getInstance();
        d.setTime(util_time);
        d.add(Calendar.HOUR, -3);
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        timeStr = df.format(d.getTime());

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

        long valuehoras = Long.valueOf(timeStr.substring(0, 2));
        long valueminutos = Long.valueOf(timeStr.substring(3, 5));
        long valuesegundos = Long.valueOf(timeStr.substring(6, 8));


        

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

        if (valuehoras <= 3) {
            if (valuehoras != 3) {
                Calendar dt = Calendar.getInstance();
                dt.setTime(util_Date);
                dt.add(Calendar.DAY_OF_MONTH, -1);
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                dataStr = df.format(dt.getTime());

                try {
                    util_Date = formatadorHora.parse(dataStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                if (valueminutos < 1) {
                    if (valuesegundos < 1) {
                        Calendar dt = Calendar.getInstance();
                        dt.setTime(util_Date);
                        dt.add(Calendar.DAY_OF_MONTH, -1);
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        dataStr = df.format(dt.getTime());
                        try {
                            util_Date = formatadorHora.parse(dataStr);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }

        Calendar d = Calendar.getInstance();
        d.setTime(util_time);
        d.add(Calendar.HOUR, -3);
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        timeStr = df.format(d.getTime());

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

        List<Owner> owner = ownerRepository.listAll(Sort.ascending("businessname"));

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
                agenteTemp.setIdAgente(owner.get(i).getId());
                agenteTemp.setBusinessName(owner.get(i).getBusinessName());
                agenteTemp.setQuantTicketsInAttendance(quantInAttendance);
                agenteTemp.setQuantTicketsNew(quantNew);
                agenteTemp.setQuantTicketsStopped(quantStopped);
                agenteTemp.setQuantTicketsCanceled(0);
                agenteTemp.setQuantTicketsResolved(0);
                agenteTemp.setQuantTicketsClosed(0);

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

        List<Owner> owner = ownerRepository.listAll(Sort.ascending("businessname"));
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
                agenteTemp.setIdAgente(owner.get(i).getId());
                agenteTemp.setBusinessName(owner.get(i).getBusinessName());
                agenteTemp.setCustomizacao(quantCustom);
                agenteTemp.setDuvida(quantDuvida);
                agenteTemp.setFalha(quantFalha);
                agenteTemp.setHomologacao(quantHomolo);
                agenteTemp.setImplantacao(quantImplan);
                agenteTemp.setLicitacao(quantLicita);
                agenteTemp.setSemCategoria(quantSCateg);
                agenteTemp.setSolicitacaoServico(quantServic);
                agenteTemp.setSolicitacaoTreinamento(quantTreina);
                agenteTemp.setSugestao(quantSugest);
                agenteTemp.setTreinamentoOnline(quantOnline);

                agente.add(quantOwner, agenteTemp);

                quantOwner++;
            }
        }

        return agente;
    }

    @Override
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

        cat.setCustomizacao(ticketsCustomizacao.size());
        cat.setDuvida(ticketsDuvida.size());
        cat.setFalha(ticketsFalha.size());
        cat.setHomologacao(ticketsHomologacao.size());
        cat.setImplantacao(ticketsImplantacao.size());
        cat.setLicitacao(ticketsLicitacao.size());
        cat.setSemCategoria(ticketsSemCategoria.size());
        cat.setSolicitacaoServico(ticketsServico.size());
        cat.setSugestao(ticketsSugestao.size());
        cat.setSolicitacaoTreinamento(ticketsTreinamento.size());
        cat.setTreinamentoOnline(ticketsTreinamentoRemoto.size());

        return cat;
    }

    @Override
    public List<AgenteJustification> ownerJustifications() throws MenssageNotFoundException {
        List<AgenteJustification> agentJusti = new ArrayList<AgenteJustification>();

        int quantOwner = 0;

        List<Owner> owner = ownerRepository.listAll(Sort.ascending("businessname"));

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

            for (int x = 0; x < ticketsparalisado.size(); x++) {
                if (owner.get(i) == ticketsparalisado.get(x).getOwner()) {
                    quantparalisado++;
                }
            }

            for (int x = 0; x < ticketssemjustification.size(); x++) {
                if (owner.get(i) == ticketssemjustification.get(x).getOwner()) {
                    quantsemjustification++;
                }
            }

            for (int x = 0; x < ticketsaguarDesenv.size(); x++) {
                if (owner.get(i) == ticketsaguarDesenv.get(x).getOwner()) {
                    quantaguarDesenv++;
                }
            }

            for (int x = 0; x < ticketsemAnalise.size(); x++) {
                if (owner.get(i) == ticketsemAnalise.get(x).getOwner()) {
                    quantemAnalise++;
                }
            }

            for (int x = 0; x < ticketsaguardEscla.size(); x++) {
                if (owner.get(i) == ticketsaguardEscla.get(x).getOwner()) {
                    quantaguardEscla++;
                }
            }

            for (int x = 0; x < ticketsresponClien.size(); x++) {
                if (owner.get(i) == ticketsresponClien.get(x).getOwner()) {
                    quantresponClien++;
                }
            }
            for (int x = 0; x < ticketsemVerificacao.size(); x++) {
                if (owner.get(i) == ticketsemVerificacao.get(x).getOwner()) {
                    quantemVerificacao++;
                }
            }

            for (int x = 0; x < ticketstreinaAgendad.size(); x++) {
                if (owner.get(i) == ticketstreinaAgendad.get(x).getOwner()) {
                    quanttreinaAgendad++;
                }
            }

            for (int x = 0; x < ticketsaprovado.size(); x++) {
                if (owner.get(i) == ticketsaprovado.get(x).getOwner()) {
                    quantaprovado++;
                }
            }

            for (int x = 0; x < ticketsemValidSupor.size(); x++) {
                if (owner.get(i) == ticketsemValidSupor.get(x).getOwner()) {
                    quantemValidSupor++;
                }
            }

            for (int x = 0; x < ticketsaguardAtualiz.size(); x++) {
                if (owner.get(i) == ticketsaguardAtualiz.get(x).getOwner()) {
                    quantaguardAtualiz++;
                }
            }

            for (int x = 0; x < ticketsaguardAprovac.size(); x++) {
                if (owner.get(i) == ticketsaguardAprovac.get(x).getOwner()) {
                    quantaguardAprovac++;
                }
            }

            for (int x = 0; x < ticketsaguardInfra.size(); x++) {
                if (owner.get(i) == ticketsaguardInfra.get(x).getOwner()) {
                    quantaguardInfra++;
                }
            }

            for (int x = 0; x < ticketsaguardAnalis.size(); x++) {
                if (owner.get(i) == ticketsaguardAnalis.get(x).getOwner()) {
                    quantaguardAnalis++;
                }
            }

            for (int x = 0; x < ticketsemTestes.size(); x++) {
                if (owner.get(i) == ticketsemTestes.get(x).getOwner()) {
                    quantemTestes++;
                }
            }

            for (int x = 0; x < ticketsaguardImpacto.size(); x++) {
                if (owner.get(i) == ticketsaguardImpacto.get(x).getOwner()) {
                    quantaguardImpacto++;
                }
            }
            for (int x = 0; x < ticketsaguardEnVersao.size(); x++) {
                if (owner.get(i) == ticketsaguardEnVersao.get(x).getOwner()) {
                    quantaguardEnVersao++;
                }
            }
            for (int x = 0; x < ticketsimpaAvaliado.size(); x++) {
                if (owner.get(i) == ticketsimpaAvaliado.get(x).getOwner()) {
                    quantimpaAvaliado++;
                }
            }
            for (int x = 0; x < ticketsaguardOrcament.size(); x++) {
                if (owner.get(i) == ticketsaguardOrcament.get(x).getOwner()) {
                    quantaguardOrcament++;
                }
            }
            for (int x = 0; x < ticketsemVerificaInter.size(); x++) {
                if (owner.get(i) == ticketsemVerificaInter.get(x).getOwner()) {
                    quantemVerificaInter++;
                }
            }
            for (int x = 0; x < ticketsaguardCompilac.size(); x++) {
                if (owner.get(i) == ticketsaguardCompilac.get(x).getOwner()) {
                    quantaguardCompilac++;
                }
            }
            for (int x = 0; x < ticketsemDesenvolviment.size(); x++) {
                if (owner.get(i) == ticketsemDesenvolviment.get(x).getOwner()) {
                    quantemDesenvolviment++;
                }
            }
            for (int x = 0; x < ticketsaguardFaturame.size(); x++) {
                if (owner.get(i) == ticketsaguardFaturame.get(x).getOwner()) {
                    quantaguardFaturame++;
                }
            }

            for (int x = 0; x < ticketsaguardInfParCli.size(); x++) {
                if (owner.get(i) == ticketsaguardInfParCli.get(x).getOwner()) {
                    quantaguardInfParCli++;
                }
            }

            for (int x = 0; x < ticketsdesenvolvido.size(); x++) {
                if (owner.get(i) == ticketsdesenvolvido.get(x).getOwner()) {
                    quantdesenvolvido++;
                }
            }

            for (int x = 0; x < ticketsaguarAprovPreAnali.size(); x++) {
                if (owner.get(i) == ticketsaguarAprovPreAnali.get(x).getOwner()) {
                    quantaguarAprovPreAnali++;
                }
            }

            for (int x = 0; x < ticketsaguarEsclaSup.size(); x++) {
                if (owner.get(i) == ticketsaguarEsclaSup.get(x).getOwner()) {
                    quantaguarEsclaSup++;
                }
            }

            for (int x = 0; x < ticketsaguarEsclaSup.size(); x++) {
                if (owner.get(i) == ticketsaguarEsclaSup.get(x).getOwner()) {
                    quantaguarEsclaSup++;
                }
            }

            for (int x = 0; x < ticketsaguarValidParc.size(); x++) {
                if (owner.get(i) == ticketsaguarValidParc.get(x).getOwner()) {
                    quantaguarValidParc++;
                }
            }

            for (int x = 0; x < ticketsagendVistTecn.size(); x++) {
                if (owner.get(i) == ticketsagendVistTecn.get(x).getOwner()) {
                    quantagendVistTecn++;
                }
            }

            for (int x = 0; x < ticketspreAnalAprov.size(); x++) {
                if (owner.get(i) == ticketspreAnalAprov.get(x).getOwner()) {
                    quantpreAnalAprov++;
                }
            }

            for (int x = 0; x < ticketsenvAnalise.size(); x++) {
                if (owner.get(i) == ticketsenvAnalise.get(x).getOwner()) {
                    quantenvAnalise++;
                }
            }

            for (int x = 0; x < ticketsaguarRetClien.size(); x++) {
                if (owner.get(i) == ticketsaguarRetClien.get(x).getOwner()) {
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
                agenteTemp.setIdAgente(owner.get(i).getId());
                agenteTemp.setBusinessName(owner.get(i).getBusinessName());
                agenteTemp.setParalisado(quantparalisado);
                agenteTemp.setSemjustification(quantsemjustification);
                agenteTemp.setAguarDesenv(quantaguarDesenv);
                agenteTemp.setEmAnalise(quantemAnalise);
                agenteTemp.setAguardEscla(quantaguardEscla);
                agenteTemp.setResponClien(quantresponClien);
                agenteTemp.setEmVerificacao(quantemVerificacao);
                agenteTemp.setTreinaAgendad(quanttreinaAgendad);
                agenteTemp.setAprovado(quantaprovado);
                agenteTemp.setEmValidSupor(quantemValidSupor);
                agenteTemp.setAguardAtualiz(quantaguardAtualiz);
                agenteTemp.setAguardAprovac(quantaguardAprovac);
                agenteTemp.setAguardInfra(quantaguardInfra);
                agenteTemp.setAguardAnalis(quantaguardAnalis);
                agenteTemp.setEmTestes(quantemTestes);
                agenteTemp.setAguardImpacto(quantaguardImpacto);
                agenteTemp.setAguardEnVersao(quantaguardEnVersao);
                agenteTemp.setImpaAvaliado(quantimpaAvaliado);
                agenteTemp.setAguardOrcament(quantaguardOrcament);
                agenteTemp.setEmVerificaInter(quantemVerificaInter);
                agenteTemp.setAguardCompilac(quantaguardCompilac);
                agenteTemp.setEmDesenvolviment(quantemDesenvolviment);
                agenteTemp.setAguardFaturame(quantaguardFaturame);
                agenteTemp.setAguardInfParCli(quantaguardInfParCli);
                agenteTemp.setDesenvolvido(quantdesenvolvido);
                agenteTemp.setAguarAprovPreAnali(quantaguarAprovPreAnali);
                agenteTemp.setAguarEsclaSup(quantaguarEsclaSup);
                agenteTemp.setAguarValidParc(quantaguarValidParc);
                agenteTemp.setAgendVistTecn(quantagendVistTecn);
                agenteTemp.setPreAnalAprov(quantpreAnalAprov);
                agenteTemp.setEnvAnalise(quantenvAnalise);
                agenteTemp.setAguarRetClien(quantaguarRetClien);

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

        justification.setParalisado(ticketsparalisado.size());
        justification.setSemjustification(ticketssemjustification.size());
        justification.setAguarDesenv(ticketsaguarDesenv.size());
        justification.setEmAnalise(ticketsemAnalise.size());
        justification.setAguardEscla(ticketsaguardEscla.size());
        justification.setResponClien(ticketsresponClien.size());
        justification.setEmVerificacao(ticketsemVerificacao.size());
        justification.setTreinaAgendad(ticketstreinaAgendad.size());
        justification.setAprovado(ticketsaprovado.size());
        justification.setEmValidSupor(ticketsemValidSupor.size());
        justification.setAguardAtualiz(ticketsaguardAtualiz.size());
        justification.setAguardAprovac(ticketsaguardAprovac.size());
        justification.setAguardInfra(ticketsaguardInfra.size());
        justification.setAguardAnalis(ticketsaguardAnalis.size());
        justification.setEmTestes(ticketsemTestes.size());
        justification.setAguardImpacto(ticketsaguardImpacto.size());
        justification.setAguardEnVersao(ticketsaguardEnVersao.size());
        justification.setImpaAvaliado(ticketsimpaAvaliado.size());
        justification.setAguardOrcament(ticketsaguardOrcament.size());
        justification.setEmVerificaInter(ticketsemVerificaInter.size());
        justification.setAguardCompilac(ticketsaguardCompilac.size());
        justification.setEmDesenvolviment(ticketsemDesenvolviment.size());
        justification.setAguardFaturame(ticketsaguardFaturame.size());
        justification.setAguardInfParCli(ticketsaguardInfParCli.size());
        justification.setDesenvolvido(ticketsdesenvolvido.size());
        justification.setAguarAprovPreAnali(ticketsaguarAprovPreAnali.size());
        justification.setAguarEsclaSup(ticketsaguarEsclaSup.size());
        justification.setAguarValidParc(ticketsaguarValidParc.size());
        justification.setAgendVistTecn(ticketsagendVistTecn.size());
        justification.setPreAnalAprov(ticketspreAnalAprov.size());
        justification.setEnvAnalise(ticketsenvAnalise.size());
        justification.setAguarRetClien(ticketsaguarRetClien.size());

        return justification;
    }

    @Override
    public List<TicketsAnos> ticketsAnos() throws MenssageNotFoundException {
        SimpleDateFormat formatadorDia = new SimpleDateFormat("yyyy-MM-dd");
        List<TicketsAnos> anos = new ArrayList<TicketsAnos>();
        LocalDate current_date = LocalDate.now();
        int anoAtual = current_date.getYear();
        int quant = 0;

        for (int x = 2013; x <= anoAtual; x++) {
            TicketsAnos anosAux = new TicketsAnos();
            anosAux.setAno(x);
            java.util.Date date_Inicio = new java.util.Date();
            java.util.Date date_Fim = new java.util.Date();

            try {
                date_Inicio = formatadorDia.parse(x + "-01-01");
                date_Fim = formatadorDia.parse(x + "-12-31");
            } catch (ParseException e) {
                e.printStackTrace();
            }

            anosAux.setQuantidade(ticketsRepository.findByTicketsDateCount(date_Inicio, date_Fim));

            anos.add(quant, anosAux);
            quant++;
        }
        return anos;
    }

    @Override
    public List<TicketsAnosCategory> ticketsAnosCategory() throws MenssageNotFoundException {
        SimpleDateFormat formatadorDia = new SimpleDateFormat("yyyy-MM-dd");
        List<TicketsAnosCategory> anos = new ArrayList<TicketsAnosCategory>();
        LocalDate current_date = LocalDate.now();
        int anoAtual = current_date.getYear();
        int quant = 0;

        for (int x = 2013; x <= anoAtual; x++) {
            TicketsAnosCategory anosAux = new TicketsAnosCategory();
            anosAux.setAnos(x);
            java.util.Date date_Inicio = new java.util.Date();
            java.util.Date date_Fim = new java.util.Date();

            try {
                date_Inicio = formatadorDia.parse(x + "-01-01");
                date_Fim = formatadorDia.parse(x + "-12-31");
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Category category = new Category();
            category.setCustomizacao(0);
            category.setDuvida(0);
            category.setFalha(0);
            category.setHomologacao(0);
            category.setImplantacao(0);
            category.setLicitacao(0);
            category.setSemCategoria(0);
            category.setSolicitacaoServico(0);
            category.setSolicitacaoTreinamento(0);
            category.setSugestao(0);
            category.setTreinamentoOnline(0);

            List<Tickets> tickets = ticketsRepository.findByTicketsDate(date_Inicio, date_Fim);

            for (int i = 0; i < tickets.size(); i++) {
                if (tickets.get(i).getCategory() == null) {
                    category.setSemCategoria(category.getSemCategoria() + 1);
                } else if (tickets.get(i).getCategory().equals("CustomizaÃ§Ã£o")) {
                    category.setCustomizacao(category.getCustomizacao() + 1);
                } else if (tickets.get(i).getCategory().equals("DÃºvida")) {
                    category.setDuvida(category.getDuvida() + 1);
                } else if (tickets.get(i).getCategory().equals("Falha")) {
                    category.setFalha(category.getFalha() + 1);
                } else if (tickets.get(i).getCategory().equals("HomologaÃ§Ã£o")) {
                    category.setHomologacao(category.getHomologacao() + 1);
                } else if (tickets.get(i).getCategory().equals("SolicitaÃ§Ã£o de ImplantaÃ§Ã£o")) {
                    category.setImplantacao(category.getImplantacao() + 1);
                } else if (tickets.get(i).getCategory().equals("LicitaÃ§Ã£o")) {
                    category.setLicitacao(category.getLicitacao() + 1);
                } else

                if (tickets.get(i).getCategory().equals("SolicitaÃ§Ã£o de ServiÃ§o")) {
                    category.setSolicitacaoServico(category.getSolicitacaoServico() + 1);
                } else if (tickets.get(i).getCategory().equals("SugestÃ£o")) {
                    category.setSugestao(category.getSugestao() + 1);
                } else if (tickets.get(i).getCategory().equals("SolicitaÃ§Ã£o de Treinamento")) {
                    category.setSolicitacaoTreinamento(category.getSolicitacaoTreinamento() + 1);
                } else if (tickets.get(i).getCategory().equals("SolicitaÃ§Ã£o de Treinamento Online (Remoto)")) {
                    category.setTreinamentoOnline(category.getTreinamentoOnline() + 1);
                }
            }
            anosAux.setCategory(category);

            anos.add(quant, anosAux);
            quant++;
        }
        return anos;
    }

    @Override
    public List<TicketsMesesDias> ticketsMesesCategory() throws MenssageNotFoundException {
        List<TicketsMesesDias> meses = new ArrayList<TicketsMesesDias>();
        SimpleDateFormat formatadorDia = new SimpleDateFormat("yyyy-MM-dd");
        boolean end = true;

        LocalDate current_date = LocalDate.now();
        LocalDate data_anterior = current_date.minusMonths(12);
        int anoAtual = current_date.getYear();
        int mesAtual = current_date.getMonthValue();
        int ano = data_anterior.getYear();
        int mes = data_anterior.getMonthValue();
        int diafinal = 1;
        int quant = 0;
        while (end) {
            TicketsMesesDias mesesAux = new TicketsMesesDias();
            mesesAux.setMesesDia(retornaMes(mes) + "/" + ano);
            java.util.Date date_Inicio = new java.util.Date();
            java.util.Date date_Fim = new java.util.Date();
            diafinal = retornaUltimoDiaMes(mes);

            try {
                date_Inicio = formatadorDia.parse(ano + "-" + mes + "-01");
                date_Fim = formatadorDia.parse(ano + "-" + mes + "-" + diafinal);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Category category = new Category();
            category.setCustomizacao(0);
            category.setDuvida(0);
            category.setFalha(0);
            category.setHomologacao(0);
            category.setImplantacao(0);
            category.setLicitacao(0);
            category.setSemCategoria(0);
            category.setSolicitacaoServico(0);
            category.setSolicitacaoTreinamento(0);
            category.setSugestao(0);
            category.setTreinamentoOnline(0);

            List<Tickets> tickets = ticketsRepository.findByTicketsDate(date_Inicio, date_Fim);
            mesesAux.setQuantidade(tickets.size());
            for (int i = 0; i < tickets.size(); i++) {
                if (tickets.get(i).getCategory() == null) {
                    category.setSemCategoria(category.getSemCategoria() + 1);
                } else if (tickets.get(i).getCategory().equals("CustomizaÃ§Ã£o")) {
                    category.setCustomizacao(category.getCustomizacao() + 1);
                } else if (tickets.get(i).getCategory().equals("DÃºvida")) {
                    category.setDuvida(category.getDuvida() + 1);
                } else if (tickets.get(i).getCategory().equals("Falha")) {
                    category.setFalha(category.getFalha() + 1);
                } else if (tickets.get(i).getCategory().equals("HomologaÃ§Ã£o")) {
                    category.setHomologacao(category.getHomologacao() + 1);
                } else if (tickets.get(i).getCategory().equals("SolicitaÃ§Ã£o de ImplantaÃ§Ã£o")) {
                    category.setImplantacao(category.getImplantacao() + 1);
                } else if (tickets.get(i).getCategory().equals("LicitaÃ§Ã£o")) {
                    category.setLicitacao(category.getLicitacao() + 1);
                } else

                if (tickets.get(i).getCategory().equals("SolicitaÃ§Ã£o de ServiÃ§o")) {
                    category.setSolicitacaoServico(category.getSolicitacaoServico() + 1);
                } else if (tickets.get(i).getCategory().equals("SugestÃ£o")) {
                    category.setSugestao(category.getSugestao() + 1);
                } else if (tickets.get(i).getCategory().equals("SolicitaÃ§Ã£o de Treinamento")) {
                    category.setSolicitacaoTreinamento(category.getSolicitacaoTreinamento() + 1);
                } else if (tickets.get(i).getCategory().equals("SolicitaÃ§Ã£o de Treinamento Online (Remoto)")) {
                    category.setTreinamentoOnline(category.getTreinamentoOnline() + 1);
                }
            }
            mesesAux.setCategory(category);
            meses.add(quant, mesesAux);
            quant++;
            if ((anoAtual == ano) && (mesAtual == mes)) {
                end = false;
            } else {
                if (mes < 12) {
                    mes++;
                } else {
                    mes = 1;
                    ano++;
                }
            }
        }
        return meses;
    }

    public String retornaMes(int mes) {
        String meses = "";
        switch (mes) {
            case 1:
                return "Janeiro";
            case 2:
                return "Fevereiro";
            case 3:
                return "Março";
            case 4:
                return "Abril";
            case 5:
                return "Maio";
            case 6:
                return "Junho";
            case 7:
                return "Julho";
            case 8:
                return "Agosto";
            case 9:
                return "Setembro";
            case 10:
                return "Outubro";
            case 11:
                return "Novembro";
            case 12:
                return "Dezembro";
        }
        return meses;
    }

    public int retornaUltimoDiaMes(int mes) {

        switch (mes) {
            case 1:
                return 31;

            case 2:
                return 28;

            case 3:
                return 31;

            case 4:
                return 30;

            case 5:
                return 31;

            case 6:
                return 30;

            case 7:
                return 31;

            case 8:
                return 31;

            case 9:
                return 30;

            case 10:
                return 31;

            case 11:
                return 30;

            case 12:
                return 31;

        }
        return 1;
    }

    @Override
    public TicketsSituacao getTicketsbaseStatusSUMDate() throws MenssageNotFoundException {
        SimpleDateFormat formatadorDia = new SimpleDateFormat("yyyy-MM-dd");
        TicketsSituacao sum = new TicketsSituacao();

        LocalDate current_date = LocalDate.now();
        LocalDate data_inicio = current_date.minusDays(7);
        int anoAtual = current_date.getYear();
        int mesAtual = current_date.getMonthValue();
        int diaAtual = current_date.getDayOfMonth();
        int ano_anterior = data_inicio.getYear();
        int mes_anterior = data_inicio.getMonthValue();
        int dia_anterior = data_inicio.getDayOfMonth();

        java.util.Date date_Inicio = new java.util.Date();
        java.util.Date date_Fim = new java.util.Date();

        try {
            date_Inicio = formatadorDia.parse(ano_anterior + "-" + mes_anterior + "-" + dia_anterior);
            date_Fim = formatadorDia.parse(anoAtual + "-" + mesAtual + "-" + diaAtual);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<Tickets> listTickets = new ArrayList<Tickets>();

        listTickets = ticketsRepository.findBybaseStatusDate("Canceled", date_Inicio, date_Fim);
        sum.setCanceled(listTickets.size());

        listTickets = ticketsRepository.findBybaseStatusDate("Closed", date_Inicio, date_Fim);
        sum.setClosed(listTickets.size());

        listTickets = ticketsRepository.findBybaseStatusDate("InAttendance", date_Inicio, date_Fim);
        sum.setInAttendance(listTickets.size());

        listTickets = ticketsRepository.findBybaseStatusDate("New", date_Inicio, date_Fim);
        sum.setNewReg(listTickets.size());

        listTickets = ticketsRepository.findBybaseStatusDate("Resolved", date_Inicio, date_Fim);
        sum.setResolved(listTickets.size());

        listTickets = ticketsRepository.findBybaseStatusDate("Stopped", date_Inicio, date_Fim);
        sum.setStopped(listTickets.size());
        return sum;
    }

    @Override
    public TicketsUrgency getTicketsUrgencySUMDate() throws MenssageNotFoundException {
        SimpleDateFormat formatadorDia = new SimpleDateFormat("yyyy-MM-dd");
        TicketsUrgency sum = new TicketsUrgency();
        List<Tickets> listTickets = new ArrayList<Tickets>();

        LocalDate current_date = LocalDate.now();
        LocalDate data_inicio = current_date.minusDays(7);
        int anoAtual = current_date.getYear();
        int mesAtual = current_date.getMonthValue();
        int diaAtual = current_date.getDayOfMonth();
        int ano_anterior = data_inicio.getYear();
        int mes_anterior = data_inicio.getMonthValue();
        int dia_anterior = data_inicio.getDayOfMonth();

        java.util.Date date_Inicio = new java.util.Date();
        java.util.Date date_Fim = new java.util.Date();

        try {
            date_Inicio = formatadorDia.parse(ano_anterior + "-" + mes_anterior + "-" + dia_anterior);
            date_Fim = formatadorDia.parse(anoAtual + "-" + mesAtual + "-" + diaAtual);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        listTickets = ticketsRepository.findByUrgencyDate("2 - Alta", date_Inicio, date_Fim);
        sum.setAlta(listTickets.size());

        listTickets = ticketsRepository.findByUrgencyDate("4 - Baixa", date_Inicio, date_Fim);
        sum.setBaixa(listTickets.size());

        listTickets = ticketsRepository.findByUrgencyDate("3 - MÃ©dia", date_Inicio, date_Fim);
        sum.setMedia(listTickets.size());

        listTickets = ticketsRepository.findByUrgencyIsNullDate(date_Inicio, date_Fim);
        sum.setNulo(listTickets.size());

        listTickets = ticketsRepository.findByUrgencyDate("1 - Urgente", date_Inicio, date_Fim);
        sum.setUrgente(listTickets.size());

        return sum;
    }

    @Override
    public Category CategorySeven() throws MenssageNotFoundException {
        SimpleDateFormat formatadorDia = new SimpleDateFormat("yyyy-MM-dd");
        Category cat = new Category();

        LocalDate current_date = LocalDate.now();
        LocalDate data_inicio = current_date.minusDays(7);
        int anoAtual = current_date.getYear();
        int mesAtual = current_date.getMonthValue();
        int diaAtual = current_date.getDayOfMonth();
        int ano_anterior = data_inicio.getYear();
        int mes_anterior = data_inicio.getMonthValue();
        int dia_anterior = data_inicio.getDayOfMonth();

        java.util.Date date_Inicio = new java.util.Date();
        java.util.Date date_Fim = new java.util.Date();

        try {
            date_Inicio = formatadorDia.parse(ano_anterior + "-" + mes_anterior + "-" + dia_anterior);
            date_Fim = formatadorDia.parse(anoAtual + "-" + mesAtual + "-" + diaAtual);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<Tickets> ticketsCustomizacao = ticketsRepository.findByCategoryDate("CustomizaÃ§Ã£o", date_Inicio,
                date_Fim);
        List<Tickets> ticketsDuvida = ticketsRepository.findByCategoryDate("DÃºvida", date_Inicio, date_Fim);
        List<Tickets> ticketsFalha = ticketsRepository.findByCategoryDate("Falha", date_Inicio, date_Fim);
        List<Tickets> ticketsHomologacao = ticketsRepository.findByCategoryDate("HomologaÃ§Ã£o", date_Inicio, date_Fim);
        List<Tickets> ticketsImplantacao = ticketsRepository.findByCategoryDate("SolicitaÃ§Ã£o de ImplantaÃ§Ã£o",
                date_Inicio, date_Fim);
        List<Tickets> ticketsLicitacao = ticketsRepository.findByCategoryDate("LicitaÃ§Ã£o", date_Inicio, date_Fim);
        List<Tickets> ticketsSemCategoria = ticketsRepository.findBySemCategoryDate(date_Inicio, date_Fim);
        List<Tickets> ticketsServico = ticketsRepository.findByCategoryDate("SolicitaÃ§Ã£o de ServiÃ§o", date_Inicio,
                date_Fim);
        List<Tickets> ticketsSugestao = ticketsRepository.findByCategoryDate("SugestÃ£o", date_Inicio, date_Fim);
        List<Tickets> ticketsTreinamento = ticketsRepository.findByCategoryDate("SolicitaÃ§Ã£o de Treinamento",
                date_Inicio, date_Fim);
        List<Tickets> ticketsTreinamentoRemoto = ticketsRepository
                .findByCategoryDate("SolicitaÃ§Ã£o de Treinamento Online (Remoto)", date_Inicio, date_Fim);

        cat.setCustomizacao(ticketsCustomizacao.size());
        cat.setDuvida(ticketsDuvida.size());
        cat.setFalha(ticketsFalha.size());
        cat.setHomologacao(ticketsHomologacao.size());
        cat.setImplantacao(ticketsImplantacao.size());
        cat.setLicitacao(ticketsLicitacao.size());
        cat.setSemCategoria(ticketsSemCategoria.size());
        cat.setSolicitacaoServico(ticketsServico.size());
        cat.setSugestao(ticketsSugestao.size());
        cat.setSolicitacaoTreinamento(ticketsTreinamento.size());
        cat.setTreinamentoOnline(ticketsTreinamentoRemoto.size());

        return cat;
    }

    @Override
    public List<AgenteTickets> OwnerTicketsSeven() throws MenssageNotFoundException {
        SimpleDateFormat formatadorDia = new SimpleDateFormat("yyyy-MM-dd");
        List<AgenteTickets> agente = new ArrayList<AgenteTickets>();

        List<Owner> owner = ownerRepository.listAll(Sort.ascending("businessname"));

        LocalDate current_date = LocalDate.now();
        LocalDate data_inicio = current_date.minusDays(7);
        int anoAtual = current_date.getYear();
        int mesAtual = current_date.getMonthValue();
        int diaAtual = current_date.getDayOfMonth();
        int ano_anterior = data_inicio.getYear();
        int mes_anterior = data_inicio.getMonthValue();
        int dia_anterior = data_inicio.getDayOfMonth();

        java.util.Date date_Inicio = new java.util.Date();
        java.util.Date date_Fim = new java.util.Date();

        try {
            date_Inicio = formatadorDia.parse(ano_anterior + "-" + mes_anterior + "-" + dia_anterior);
            date_Fim = formatadorDia.parse(anoAtual + "-" + mesAtual + "-" + diaAtual);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<Tickets> ticketsInAttendance = ticketsRepository.findBybaseStatusDate("InAttendance", date_Inicio,
                date_Fim);
        List<Tickets> ticketsNew = ticketsRepository.findBybaseStatusDate("New", date_Inicio, date_Fim);
        List<Tickets> ticketsStopped = ticketsRepository.findBybaseStatusDate("Stopped", date_Inicio, date_Fim);
        List<Tickets> ticketsCanceled = ticketsRepository.findBybaseStatusDate("Canceled", date_Inicio, date_Fim);
        List<Tickets> ticketsResolved = ticketsRepository.findBybaseStatusDate("Resolved", date_Inicio, date_Fim);
        List<Tickets> ticketsClosed = ticketsRepository.findBybaseStatusDate("Closed", date_Inicio, date_Fim);

        int quantOwner = 0;

        for (int i = 0; i < owner.size(); i++) {

            long quantInAttendance = 0;
            long quantNew = 0;
            long quantStopped = 0;
            long quantCanceled = 0;
            long quantResolved = 0;
            long quantClosed = 0;

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

            for (int x = 0; x < ticketsCanceled.size(); x++) {
                if (owner.get(i) == ticketsCanceled.get(x).getOwner()) {
                    quantCanceled++;
                }
            }

            for (int x = 0; x < ticketsResolved.size(); x++) {
                if (owner.get(i) == ticketsResolved.get(x).getOwner()) {
                    quantResolved++;
                }
            }

            for (int x = 0; x < ticketsClosed.size(); x++) {
                if (owner.get(i) == ticketsClosed.get(x).getOwner()) {
                    quantClosed++;
                }
            }

            if ((quantInAttendance > 0) || (quantNew > 0) || (quantStopped > 0)) {

                AgenteTickets agenteTemp = new AgenteTickets();
                agenteTemp.setIdAgente(owner.get(i).getId());
                agenteTemp.setBusinessName(owner.get(i).getBusinessName());
                agenteTemp.setQuantTicketsInAttendance(quantInAttendance);
                agenteTemp.setQuantTicketsNew(quantNew);
                agenteTemp.setQuantTicketsStopped(quantStopped);
                agenteTemp.setQuantTicketsCanceled(quantCanceled);
                agenteTemp.setQuantTicketsResolved(quantResolved);
                agenteTemp.setQuantTicketsClosed(quantClosed);

                agente.add(quantOwner, agenteTemp);

                quantOwner++;
            }
        }

        return agente;
    }

    @Override
    public List<TicketsMesesDias> ticketsSevenCategory() throws MenssageNotFoundException {
        List<TicketsMesesDias> meses = new ArrayList<TicketsMesesDias>();
        SimpleDateFormat formatadorDia = new SimpleDateFormat("yyyy-MM-dd");

        LocalDate current_date = LocalDate.now();

        for (int x = 0; x <= 7; x++) {
            TicketsMesesDias mesesAux = new TicketsMesesDias();

            LocalDate data_inicio = current_date.minusDays((7 - x));
            int ano_anterior = data_inicio.getYear();
            int mes_anterior = data_inicio.getMonthValue();
            int dia_anterior = data_inicio.getDayOfMonth();

            mesesAux.setMesesDia(dia_anterior + "/" + retornaMes(mes_anterior));
            java.util.Date date_Inicio = new java.util.Date();
            java.util.Date date_Fim = new java.util.Date();

            try {
                date_Inicio = formatadorDia.parse(ano_anterior + "-" + mes_anterior + "-" + dia_anterior);
                date_Fim = formatadorDia.parse(ano_anterior + "-" + mes_anterior + "-" + dia_anterior);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Category category = new Category();
            category.setCustomizacao(0);
            category.setDuvida(0);
            category.setFalha(0);
            category.setHomologacao(0);
            category.setImplantacao(0);
            category.setLicitacao(0);
            category.setSemCategoria(0);
            category.setSolicitacaoServico(0);
            category.setSolicitacaoTreinamento(0);
            category.setSugestao(0);
            category.setTreinamentoOnline(0);

            List<Tickets> tickets = ticketsRepository.findByTicketsDate(date_Inicio, date_Fim);
            mesesAux.setQuantidade(tickets.size());
            for (int i = 0; i < tickets.size(); i++) {
                if (tickets.get(i).getCategory() == null) {
                    category.setSemCategoria(category.getSemCategoria() + 1);
                } else if (tickets.get(i).getCategory().equals("CustomizaÃ§Ã£o")) {
                    category.setCustomizacao(category.getCustomizacao() + 1);
                } else if (tickets.get(i).getCategory().equals("DÃºvida")) {
                    category.setDuvida(category.getDuvida() + 1);
                } else if (tickets.get(i).getCategory().equals("Falha")) {
                    category.setFalha(category.getFalha() + 1);
                } else if (tickets.get(i).getCategory().equals("HomologaÃ§Ã£o")) {
                    category.setHomologacao(category.getHomologacao() + 1);
                } else if (tickets.get(i).getCategory().equals("SolicitaÃ§Ã£o de ImplantaÃ§Ã£o")) {
                    category.setImplantacao(category.getImplantacao() + 1);
                } else if (tickets.get(i).getCategory().equals("LicitaÃ§Ã£o")) {
                    category.setLicitacao(category.getLicitacao() + 1);
                } else

                if (tickets.get(i).getCategory().equals("SolicitaÃ§Ã£o de ServiÃ§o")) {
                    category.setSolicitacaoServico(category.getSolicitacaoServico() + 1);
                } else if (tickets.get(i).getCategory().equals("SugestÃ£o")) {
                    category.setSugestao(category.getSugestao() + 1);
                } else if (tickets.get(i).getCategory().equals("SolicitaÃ§Ã£o de Treinamento")) {
                    category.setSolicitacaoTreinamento(category.getSolicitacaoTreinamento() + 1);
                } else if (tickets.get(i).getCategory().equals("SolicitaÃ§Ã£o de Treinamento Online (Remoto)")) {
                    category.setTreinamentoOnline(category.getTreinamentoOnline() + 1);
                }
            }
            mesesAux.setCategory(category);
            meses.add(x, mesesAux);
        }
        return meses;
    }

    @Override
    public Category CategoryDay() throws MenssageNotFoundException {
        SimpleDateFormat formatadorDia = new SimpleDateFormat("yyyy-MM-dd");
        Category cat = new Category();

        LocalDate current_date = LocalDate.now();
        int anoAtual = current_date.getYear();
        int mesAtual = current_date.getMonthValue();
        int diaAtual = current_date.getDayOfMonth();

        java.util.Date date_Fim = new java.util.Date();

        try {
            date_Fim = formatadorDia.parse(anoAtual + "-" + mesAtual + "-" + diaAtual);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<Tickets> ticketsCustomizacao = ticketsRepository.findByCategoryDate("CustomizaÃ§Ã£o", date_Fim, date_Fim);
        List<Tickets> ticketsDuvida = ticketsRepository.findByCategoryDate("DÃºvida", date_Fim, date_Fim);
        List<Tickets> ticketsFalha = ticketsRepository.findByCategoryDate("Falha", date_Fim, date_Fim);
        List<Tickets> ticketsHomologacao = ticketsRepository.findByCategoryDate("HomologaÃ§Ã£o", date_Fim, date_Fim);
        List<Tickets> ticketsImplantacao = ticketsRepository.findByCategoryDate("SolicitaÃ§Ã£o de ImplantaÃ§Ã£o",
                date_Fim, date_Fim);
        List<Tickets> ticketsLicitacao = ticketsRepository.findByCategoryDate("LicitaÃ§Ã£o", date_Fim, date_Fim);
        List<Tickets> ticketsSemCategoria = ticketsRepository.findBySemCategoryDate(date_Fim, date_Fim);
        List<Tickets> ticketsServico = ticketsRepository.findByCategoryDate("SolicitaÃ§Ã£o de ServiÃ§o", date_Fim,
                date_Fim);
        List<Tickets> ticketsSugestao = ticketsRepository.findByCategoryDate("SugestÃ£o", date_Fim, date_Fim);
        List<Tickets> ticketsTreinamento = ticketsRepository.findByCategoryDate("SolicitaÃ§Ã£o de Treinamento",
                date_Fim, date_Fim);
        List<Tickets> ticketsTreinamentoRemoto = ticketsRepository
                .findByCategoryDate("SolicitaÃ§Ã£o de Treinamento Online (Remoto)", date_Fim, date_Fim);

        cat.setCustomizacao(ticketsCustomizacao.size());
        cat.setDuvida(ticketsDuvida.size());
        cat.setFalha(ticketsFalha.size());
        cat.setHomologacao(ticketsHomologacao.size());
        cat.setImplantacao(ticketsImplantacao.size());
        cat.setLicitacao(ticketsLicitacao.size());
        cat.setSemCategoria(ticketsSemCategoria.size());
        cat.setSolicitacaoServico(ticketsServico.size());
        cat.setSugestao(ticketsSugestao.size());
        cat.setSolicitacaoTreinamento(ticketsTreinamento.size());
        cat.setTreinamentoOnline(ticketsTreinamentoRemoto.size());

        return cat;
    }

    @Override
    public TicketsSituacao getTicketsbaseStatusSUMDay() throws MenssageNotFoundException {
        SimpleDateFormat formatadorDia = new SimpleDateFormat("yyyy-MM-dd");
        TicketsSituacao sum = new TicketsSituacao();

        LocalDate current_date = LocalDate.now();
        int anoAtual = current_date.getYear();
        int mesAtual = current_date.getMonthValue();
        int diaAtual = current_date.getDayOfMonth();

        java.util.Date date_Fim = new java.util.Date();

        try {
            date_Fim = formatadorDia.parse(anoAtual + "-" + mesAtual + "-" + diaAtual);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<Tickets> listTickets = new ArrayList<Tickets>();

        listTickets = ticketsRepository.findBybaseStatusDate("Canceled", date_Fim, date_Fim);
        sum.setCanceled(listTickets.size());

        listTickets = ticketsRepository.findBybaseStatusDate("Closed", date_Fim, date_Fim);
        sum.setClosed(listTickets.size());

        listTickets = ticketsRepository.findBybaseStatusDate("InAttendance", date_Fim, date_Fim);
        sum.setInAttendance(listTickets.size());

        listTickets = ticketsRepository.findBybaseStatusDate("New", date_Fim, date_Fim);
        sum.setNewReg(listTickets.size());

        listTickets = ticketsRepository.findBybaseStatusDate("Resolved", date_Fim, date_Fim);
        sum.setResolved(listTickets.size());

        listTickets = ticketsRepository.findBybaseStatusDate("Stopped", date_Fim, date_Fim);
        sum.setStopped(listTickets.size());
        return sum;
    }

    @Override
    public TicketsUrgency getTicketsUrgencySUMDay() throws MenssageNotFoundException {
        SimpleDateFormat formatadorDia = new SimpleDateFormat("yyyy-MM-dd");
        TicketsUrgency sum = new TicketsUrgency();
        List<Tickets> listTickets = new ArrayList<Tickets>();

        LocalDate current_date = LocalDate.now();
        int anoAtual = current_date.getYear();
        int mesAtual = current_date.getMonthValue();
        int diaAtual = current_date.getDayOfMonth();

        java.util.Date date_Fim = new java.util.Date();

        try {
            date_Fim = formatadorDia.parse(anoAtual + "-" + mesAtual + "-" + diaAtual);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        listTickets = ticketsRepository.findByUrgencyDate("2 - Alta", date_Fim, date_Fim);
        sum.setAlta(listTickets.size());

        listTickets = ticketsRepository.findByUrgencyDate("4 - Baixa", date_Fim, date_Fim);
        sum.setBaixa(listTickets.size());

        listTickets = ticketsRepository.findByUrgencyDate("3 - MÃ©dia", date_Fim, date_Fim);
        sum.setMedia(listTickets.size());

        listTickets = ticketsRepository.findByUrgencyIsNullDate(date_Fim, date_Fim);
        sum.setNulo(listTickets.size());

        listTickets = ticketsRepository.findByUrgencyDate("1 - Urgente", date_Fim, date_Fim);
        sum.setUrgente(listTickets.size());

        return sum;
    }

    @Override
    public List<AgenteTickets> OwnerTicketsDay() throws MenssageNotFoundException {
        SimpleDateFormat formatadorDia = new SimpleDateFormat("yyyy-MM-dd");
        List<AgenteTickets> agente = new ArrayList<AgenteTickets>();

        List<Owner> owner = ownerRepository.listAll(Sort.ascending("businessname"));

        LocalDate current_date = LocalDate.now();
        int anoAtual = current_date.getYear();
        int mesAtual = current_date.getMonthValue();
        int diaAtual = current_date.getDayOfMonth();

        java.util.Date date_Fim = new java.util.Date();

        try {
            date_Fim = formatadorDia.parse(anoAtual + "-" + mesAtual + "-" + diaAtual);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<Tickets> ticketsInAttendance = ticketsRepository.findBybaseStatusDate("InAttendance", date_Fim, date_Fim);
        List<Tickets> ticketsNew = ticketsRepository.findBybaseStatusDate("New", date_Fim, date_Fim);
        List<Tickets> ticketsStopped = ticketsRepository.findBybaseStatusDate("Stopped", date_Fim, date_Fim);
        List<Tickets> ticketsCanceled = ticketsRepository.findBybaseStatusDate("Canceled", date_Fim, date_Fim);
        List<Tickets> ticketsResolved = ticketsRepository.findBybaseStatusDate("Resolved", date_Fim, date_Fim);
        List<Tickets> ticketsClosed = ticketsRepository.findBybaseStatusDate("Closed", date_Fim, date_Fim);

        int quantOwner = 0;

        for (int i = 0; i < owner.size(); i++) {

            long quantInAttendance = 0;
            long quantNew = 0;
            long quantStopped = 0;
            long quantCanceled = 0;
            long quantResolved = 0;
            long quantClosed = 0;

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

            for (int x = 0; x < ticketsCanceled.size(); x++) {
                if (owner.get(i) == ticketsCanceled.get(x).getOwner()) {
                    quantCanceled++;
                }
            }

            for (int x = 0; x < ticketsResolved.size(); x++) {
                if (owner.get(i) == ticketsResolved.get(x).getOwner()) {
                    quantResolved++;
                }
            }

            for (int x = 0; x < ticketsClosed.size(); x++) {
                if (owner.get(i) == ticketsClosed.get(x).getOwner()) {
                    quantClosed++;
                }
            }

            if ((quantInAttendance > 0) || (quantNew > 0) || (quantStopped > 0)) {

                AgenteTickets agenteTemp = new AgenteTickets();
                agenteTemp.setIdAgente(owner.get(i).getId());
                agenteTemp.setBusinessName(owner.get(i).getBusinessName());
                agenteTemp.setQuantTicketsInAttendance(quantInAttendance);
                agenteTemp.setQuantTicketsNew(quantNew);
                agenteTemp.setQuantTicketsStopped(quantStopped);
                agenteTemp.setQuantTicketsCanceled(quantCanceled);
                agenteTemp.setQuantTicketsResolved(quantResolved);
                agenteTemp.setQuantTicketsClosed(quantClosed);

                agente.add(quantOwner, agenteTemp);

                quantOwner++;
            }
        }

        return agente;
    }

    @Override
    public List<TicketsMesesDias> ticketsDayCategory() throws MenssageNotFoundException {
        List<TicketsMesesDias> meses = new ArrayList<TicketsMesesDias>();
        SimpleDateFormat formatadorDia = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatadorTime = new SimpleDateFormat("HH:mm:ss");
        int quant = 0;
        LocalDate current_date = LocalDate.now();

        for (int x = 0; x <= 23; x++) {
            TicketsMesesDias mesesAux = new TicketsMesesDias();

            int ano_anterior = current_date.getYear();
            int mes_anterior = current_date.getMonthValue();
            int dia_anterior = current_date.getDayOfMonth();
            String horainicial = "";
            if (x < 10) {
                horainicial = "0" + x;
            } else {
                horainicial = "" + x;
            }

            mesesAux.setMesesDia(horainicial + ":00:00");
            java.util.Date date_Fim = new java.util.Date();
            java.util.Date time_inicio = new java.util.Date();
            java.util.Date time_fim = new java.util.Date();

            try {
                time_inicio = formatadorTime.parse(horainicial + ":00:00");
                time_fim = formatadorTime.parse(horainicial + ":59:59");
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Time tm_inicio = new Time(time_inicio.getTime());
            Time tm_fim = new Time(time_fim.getTime());

            try {
                date_Fim = formatadorDia.parse(ano_anterior + "-" + mes_anterior + "-" + dia_anterior);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Category category = new Category();
            category.setCustomizacao(0);
            category.setDuvida(0);
            category.setFalha(0);
            category.setHomologacao(0);
            category.setImplantacao(0);
            category.setLicitacao(0);
            category.setSemCategoria(0);
            category.setSolicitacaoServico(0);
            category.setSolicitacaoTreinamento(0);
            category.setSugestao(0);
            category.setTreinamentoOnline(0);

            List<Tickets> tickets = ticketsRepository.findByTicketsDateTime(date_Fim, date_Fim, tm_inicio, tm_fim);
            mesesAux.setQuantidade(tickets.size());
            for (int i = 0; i < tickets.size(); i++) {
                if (tickets.get(i).getCategory() == null) {
                    category.setSemCategoria(category.getSemCategoria() + 1);
                } else if (tickets.get(i).getCategory().equals("CustomizaÃ§Ã£o")) {
                    category.setCustomizacao(category.getCustomizacao() + 1);
                } else if (tickets.get(i).getCategory().equals("DÃºvida")) {
                    category.setDuvida(category.getDuvida() + 1);
                } else if (tickets.get(i).getCategory().equals("Falha")) {
                    category.setFalha(category.getFalha() + 1);
                } else if (tickets.get(i).getCategory().equals("HomologaÃ§Ã£o")) {
                    category.setHomologacao(category.getHomologacao() + 1);
                } else if (tickets.get(i).getCategory().equals("SolicitaÃ§Ã£o de ImplantaÃ§Ã£o")) {
                    category.setImplantacao(category.getImplantacao() + 1);
                } else if (tickets.get(i).getCategory().equals("LicitaÃ§Ã£o")) {
                    category.setLicitacao(category.getLicitacao() + 1);
                } else

                if (tickets.get(i).getCategory().equals("SolicitaÃ§Ã£o de ServiÃ§o")) {
                    category.setSolicitacaoServico(category.getSolicitacaoServico() + 1);
                } else if (tickets.get(i).getCategory().equals("SugestÃ£o")) {
                    category.setSugestao(category.getSugestao() + 1);
                } else if (tickets.get(i).getCategory().equals("SolicitaÃ§Ã£o de Treinamento")) {
                    category.setSolicitacaoTreinamento(category.getSolicitacaoTreinamento() + 1);
                } else if (tickets.get(i).getCategory().equals("SolicitaÃ§Ã£o de Treinamento Online (Remoto)")) {
                    category.setTreinamentoOnline(category.getTreinamentoOnline() + 1);
                }
            }
            if ((category.getCustomizacao() > 0) || (category.getDuvida() > 0) || (category.getFalha() > 0) ||
                    (category.getHomologacao() > 0) || (category.getImplantacao() > 0) || (category.getLicitacao() > 0)
                    ||
                    (category.getSemCategoria() > 0) || (category.getSolicitacaoServico() > 0)
                    || (category.getSolicitacaoTreinamento() > 0) ||
                    (category.getSugestao() > 0) || (category.getTreinamentoOnline() > 0)) {

                mesesAux.setCategory(category);
                meses.add(quant, mesesAux);
                quant++;
            }
        }
        return meses;
    }
}
