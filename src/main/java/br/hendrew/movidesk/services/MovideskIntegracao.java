package br.hendrew.movidesk.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import br.hendrew.movidesk.entity.Clients;
import br.hendrew.movidesk.entity.CustomClients;
import br.hendrew.movidesk.entity.CustomFieldValues;
import br.hendrew.movidesk.entity.Organization;
import br.hendrew.movidesk.entity.Owner;
import br.hendrew.movidesk.entity.Tickets;
import br.hendrew.movidesk.entity.TicketsJson;
import br.hendrew.movidesk.entity.Update;
import br.hendrew.movidesk.exception.MenssageNotFoundException;
import br.hendrew.movidesk.validation.Converte;
import br.hendrew.movidesk.validation.Util;

import org.jboss.logging.Logger;

@ApplicationScoped
public class MovideskIntegracao {
    private static final Logger LOG = Logger.getLogger(MovideskIntegracao.class);
    public long ultimo_id;
    public long erro_id;
    public boolean status_erro = false;
    public boolean localizar = true;
    public long tentativaUpdate = 0;
    public long tentativaStatus = 0;
    public long tentativaCustomClients = 0;

    static String webService = "https://api.movidesk.com/public/v1/tickets?" +
            "token=6fd0a503-b0d1-40fe-91f8-4cc4e3e027f7&$select=id,type,subject,createdDate,"
            + "category,urgency,status,baseStatus,protocol,justification,origin,originEmailAccount,"
            + "ownerTeam,resolvedIn,reopenedIn,closedIn,lastActionDate,actionCount,lastUpdate,"
            + "lifetimeWorkingTime,stoppedTime,stoppedTimeWorkingTime,resolvedInFirstCall,chatTalkTime,"
            + "chatWaitingTime,slaAgreement,slaAgreementRule,slaSolutionTime,slaResponseTime"
            + "&$orderby=id&$expand=owner,clients($expand=organization)";

    static int codigoSucesso = 200;
    private final TicketsService ticketsService;
    private final OwnerService ownerService;
    private final AtualizacaoService atualizacaoService;
    private final CustomClientsService customClientService;
    private final ClientsService clientsService;
    private final OrganizationService organizationService;

    @Inject
    public MovideskIntegracao(TicketsService ticketsService, OwnerService ownerService,
            AtualizacaoService atualizacaoService, CustomClientsService customClientService,
            ClientsService clientsService, OrganizationService organizationService) {
        this.ticketsService = ticketsService;
        this.ownerService = ownerService;
        this.atualizacaoService = atualizacaoService;
        this.customClientService = customClientService;
        this.clientsService = clientsService;
        this.organizationService = organizationService;
    }

    public List<Tickets> buscarTodosTickets() throws Exception {
        Converte convert = new Converte();
        String urlMontada = webService;
        List<Tickets> tickets = new ArrayList<Tickets>();
        Update up = new Update();
        Boolean end = true;
        long quant = 0;
        LOG.info("Quantidade de Registro na base" + ticketsService.countTickets());
        if (ticketsService.countTickets() == 0) {
            try {
                // incluir log no banco
                up.setTipoAtualizacao(0);
                up.setHoraInicioAtualizacao(pegarHora());
                up.setDataInicioAtualizacao(pegarData());
                up = registroIncluirAtualizacao(up);
                while (end) {
                    List<TicketsJson> ticketsjson = new ArrayList<TicketsJson>();
                    if (ownerService.countOwner() == 0) {
                        Owner owner = new Owner();
                        owner.setId("A0207");
                        owner.setBusinessName("Sem Agente");
                        owner.setPersonType(1);
                        owner.setProfileType(3);
                        owner.setEmail("email");
                        owner.setPathPicture("pathpicture");
                        owner.setPhone("phone");
                        ownerService.saveOwner(owner);
                    }
                    if (organizationService.countOrganization() == 0) {
                        Organization organization = new Organization();
                        organization.setId("A0207");
                        organization.setBusinessName("Sem Organization");
                        organization.setPersonType(1);
                        organization.setProfileType(3);
                        organization.setEmail("email");
                        organization.setPathPicture("pathpicture");
                        organization.setPhone("phone");
                        organizationService.saveOrganization(organization);
                    }

                    if (clientsService.countClients() == 0) {
                        Clients clients = new Clients();
                        clients.setId("A0207");
                        clients.setBusinessName("Sem Cliente");
                        clients.setPersonType(1);
                        clients.setProfileType(3);
                        clients.setEmail("email");
                        clients.setPathPicture("pathpicture");
                        clients.setPhone("phone");
                        clients.setOrganization(organizationService.getOrganizationByStringId("A0207"));
                        clients.setIsDeleted("false");
                        clientsService.saveClients(clients);
                    }

                    

                    URL url = new URL(urlMontada + "&$top=1000" + "&$skip=" + quant);
                    HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

                    if (conexao.getResponseCode() != codigoSucesso)
                        throw new RuntimeException("HTTP error code : " + conexao.getResponseCode());

                    BufferedReader resposta = new BufferedReader(new InputStreamReader((conexao.getInputStream())));
                    String jsonEmString = Util.converteJsonEmString(resposta);

                    Type listType = new TypeToken<ArrayList<TicketsJson>>() {
                    }.getType();
                    GsonBuilder builder = new GsonBuilder();
                    builder.setPrettyPrinting();
                    Gson gson = builder.create();
                    ticketsjson = gson.fromJson(jsonEmString, listType);
                    LOG.info("Inicio Importação Total de Registro");
                    for (int i = 0; i < ticketsjson.size(); i++) {
                        Tickets ticketsAux = convert.converteTickets(ticketsjson.get(i));
                        if (tickets.size() == 0) {
                            tickets.add(0, ticketsAux);
                        } else {
                            Integer position = tickets.size();
                            tickets.add(position, ticketsAux);

                        }
                        if (ticketsAux.getOwner() == null) {
                            Owner owner = ownerService.getOwnerByStringId("A0207");
                            ticketsAux.setOwner(owner);
                        }
                        if (ticketsAux.getClients() == null) {
                            Clients clients = clientsService.getClientsByStringId("A0207");
                            ticketsAux.setClients(clients);
                        }
                        if (ticketsAux.getClients().getOrganization() == null) {
                            Clients clients = new Clients();
                            Organization organization = organizationService.getOrganizationByStringId("A0207");
                            clients = ticketsAux.getClients();
                            clients.setOrganization(organization);
                            ticketsAux.setClients(clients);
                        }

                        ticketsService.saveTickets(ticketsAux);
                        LOG.info("Importado Registro = " + ticketsAux.getId());
                        System.out.println();
                    }

                    end = ticketsjson.size() >= 1000;
                    quant = quant + 1000;
                }
                LOG.info("Terminou Importação de Registro Total");
                up.setHoraFimAtualizacao(pegarHora());
                up.setDataFimAtualizacao(pegarData());
                up.setErrorAtualizacao("Não");
                registroAtualizacao(up.getId(), up);
                return tickets;
            } catch (Exception e) {
                up.setHoraFimAtualizacao(pegarHora());
                up.setDataFimAtualizacao(pegarData());
                up.setErrorAtualizacao("Sim");
                registroAtualizacao(up.getId(), up);
                LOG.info(e);
                throw new Exception("ERRO: " + e);
            }
        } else {
            return null;
        }
    }

    public List<Tickets> atualizarTickets() throws Exception {
        Converte convert = new Converte();
        String urlMontada = webService;
        List<Tickets> tickets = new ArrayList<Tickets>();
        Boolean end = true;
        Update up = new Update();
        long quant = 0;
        if (ticketsService.countTickets() > 500) {
            quant = ticketsService.countTickets() - 500;
        }
        LOG.info("Quantidade de Registro Inicio = " + ticketsService.countTickets());
        try {
            // incluir log no banco
            up.setTipoAtualizacao(1);
            up.setHoraInicioAtualizacao(pegarHora());
            up.setDataInicioAtualizacao(pegarData());
            up = registroIncluirAtualizacao(up);

            while (end) {
                List<TicketsJson> ticketsjson = new ArrayList<TicketsJson>();

                if (ownerService.countOwner() == 0) {
                    Owner owner = new Owner();
                    owner.setId("A0207");
                    owner.setBusinessName("Sem Agente");
                    owner.setPersonType(1);
                    owner.setProfileType(3);
                    owner.setEmail("email");
                    owner.setPathPicture("pathpicture");
                    owner.setPhone("phone");
                    ownerService.saveOwner(owner);
                }

                if (organizationService.countOrganization() == 0) {
                    Organization organization = new Organization();
                    organization.setId("A0207");
                    organization.setBusinessName("Sem Organization");
                    organization.setPersonType(1);
                    organization.setProfileType(3);
                    organization.setEmail("email");
                    organization.setPathPicture("pathpicture");
                    organization.setPhone("phone");
                    organizationService.saveOrganization(organization);
                }

                if (clientsService.countClients() == 0) {
                    Clients clients = new Clients();
                    clients.setId("A0207");
                    clients.setBusinessName("Sem Cliente");
                    clients.setPersonType(1);
                    clients.setProfileType(3);
                    clients.setEmail("email");
                    clients.setPathPicture("pathpicture");
                    clients.setPhone("phone");
                    clients.setOrganization(organizationService.getOrganizationByStringId("A0207"));
                    clients.setIsDeleted("false");
                    clientsService.saveClients(clients);
                }


                URL url = new URL(urlMontada + "&$top=1000" + "&$skip=" + quant);
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

                if (conexao.getResponseCode() != codigoSucesso)
                    throw new RuntimeException("HTTP error code : " + conexao.getResponseCode());

                BufferedReader resposta = new BufferedReader(new InputStreamReader((conexao.getInputStream())));
                String jsonEmString = Util.converteJsonEmString(resposta);

                Type listType = new TypeToken<ArrayList<TicketsJson>>() {
                }.getType();
                GsonBuilder builder = new GsonBuilder();
                builder.setPrettyPrinting();
                Gson gson = builder.create();
                ticketsjson = gson.fromJson(jsonEmString, listType);
                LOG.info("Iniciou Importação Parcial");

                for (int i = 0; i < ticketsjson.size(); i++) {
                    Tickets ticketsAux = convert.converteTickets(ticketsjson.get(i));
                    if (tickets.size() == 0) {
                        tickets.add(0, ticketsAux);
                    } else {
                        Integer position = tickets.size();
                        tickets.add(position, ticketsAux);
                    }
                    if (ticketsAux.getOwner() == null) {
                        Owner owner = ownerService.getOwnerByStringId("A0207");
                        ticketsAux.setOwner(owner);
                    }

                    if (ticketsAux.getClients() == null) {
                        Clients clients = clientsService.getClientsByStringId("A0207");
                        ticketsAux.setClients(clients);
                    }

                    if (ticketsAux.getClients().getOrganization() == null) {
                        Clients clients = new Clients();
                        Organization organization = organizationService.getOrganizationByStringId("A0207");
                        clients = ticketsAux.getClients();
                        clients.setOrganization(organization);
                        ticketsAux.setClients(clients);
                    }

                    if (ticketsService.getTicketsExist(ticketsAux.getId())) {
                        ticketsService.updateTickets(ticketsAux.getId(), ticketsAux);
                        // System.out.println(ticketsjson.get(i).getId());
                        LOG.info("Atualizado Registro = " + ticketsAux.getId());
                    } else {
                        ticketsService.saveTickets(ticketsAux);
                        // System.out.println(ticketsjson.get(i).getId());
                        LOG.info("Incluido Registro = " + ticketsAux.getId());
                    }

                }

                end = ticketsjson.size() >= 1000;
                quant = quant + 1000;
            }
            LOG.info("Terminou Importação Parcial de Tickets");
            up.setHoraFimAtualizacao(pegarHora());
            up.setDataFimAtualizacao(pegarData());
            up.setErrorAtualizacao("Não");
            registroAtualizacao(up.getId(), up);
            tentativaUpdate = 0;
            atualizaCustomClients();
            return tickets;
        } catch (Exception e) {
            LOG.info(e);
            up.setHoraFimAtualizacao(pegarHora());
            up.setDataFimAtualizacao(pegarData());
            up.setErrorAtualizacao("Sim");
            registroAtualizacao(up.getId(), up);
            LOG.info("Reiniciando Serviço");
            if (tentativaUpdate < 3) {
                tentativaUpdate++;
                atualizarTickets();
            } else {
                LOG.info("3 tentativas de Atualização");
                tentativaUpdate = 0;
            }
            throw new Exception("ERRO: " + e);
        }
    }

    public List<Tickets> atualizarSituacaoTickets() throws Exception {
        Converte converte = new Converte();
        String urlMontada = webService + "&id=";
        List<Tickets> tickets = new ArrayList<Tickets>();
        String baseStatus = "";
        Update up = new Update();
        try {
            // incluir log no banco
            up.setTipoAtualizacao(2);
            up.setHoraInicioAtualizacao(pegarHora());
            up.setDataInicioAtualizacao(pegarData());
            up = registroIncluirAtualizacao(up);

            LOG.info("Iniciou Atualização de Status:");
            for (int x = 0; x < 3; x++) {
                switch (x) {
                    case 0:
                        baseStatus = "Stopped";
                        break;
                    case 1:
                        baseStatus = "New";
                        break;
                    case 2:
                        baseStatus = "InAttendance";
                        break;
                }
                LOG.info("Atualizando: " + baseStatus);
                List<Tickets> ticketsjson = ticketsService.getTicketsbaseStatus(baseStatus);
                LOG.info("Quantidade de Registro - " + baseStatus + " = " + ticketsjson.size());
                for (int i = 0; i < ticketsjson.size(); i++) {

                    // captura id
                    ultimo_id = ticketsjson.get(i).getId();// System.out.println("Base Local" +
                                                           // ticketsjson.get(i).getId());

                    if (status_erro) {
                        String data = ticketsjson.get(i).getCreatedDate();
                        if (ultimo_id == erro_id) {
                            long ret = validaTickets(erro_id, data);
                            if (ret == 0) {
                                ticketsService.deleteTickets(erro_id);
                                localizar = true;
                                erro_id = 0;
                                status_erro = false;
                                long itemp = i;
                                itemp++;
                                if (itemp < ticketsjson.size()) {
                                    i++;
                                } else {
                                    up.setHoraFimAtualizacao(pegarHora());
                                    up.setDataFimAtualizacao(pegarData());
                                    up.setErrorAtualizacao("Não");
                                    registroAtualizacao(up.getId(), up);
                                    LOG.info("Fim da Atualizada de Status");
                                    return tickets;
                                }
                            } else if (ret == 1) {
                                localizar = true;
                                erro_id = 0;
                                status_erro = false;
                            } else if (ret == 2) {
                                up.setHoraFimAtualizacao(pegarHora());
                                up.setDataFimAtualizacao(pegarData());
                                up.setErrorAtualizacao("Sim");
                                registroAtualizacao(up.getId(), up);
                                LOG.info("Erro na verifação da Atualizada de Status");
                                return tickets;
                            }

                        } else {
                            localizar = false;
                        }
                    } else {
                        localizar = true;
                    }
                    LOG.info("Base Local = " + ticketsjson.get(i).getId());
                    if (localizar) {
                        URL url = new URL(urlMontada + ticketsjson.get(i).getId());
                        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                        if (conexao.getResponseCode() != codigoSucesso)
                            throw new RuntimeException("HTTP error code : " + conexao.getResponseCode());
                        BufferedReader resposta = new BufferedReader(new InputStreamReader((conexao.getInputStream())));

                        String jsonEmString = Util.converteJsonEmString(resposta);

                        GsonBuilder builder = new GsonBuilder();
                        builder.setPrettyPrinting();
                        Gson gson = builder.create();

                        TicketsJson ticketsUpdateJson = gson.fromJson(jsonEmString, TicketsJson.class);
                        Tickets ticketsUpdate = converte.converteTickets(ticketsUpdateJson);
                        if (ticketsService.getTicketsExist(ticketsUpdate.getId())) {
                            if (ticketsUpdate.getOwner() == null) {
                                Owner owner = ownerService.getOwnerByStringId("A0207");
                                ticketsUpdate.setOwner(owner);
                            }

                            if (ticketsUpdate.getClients() == null) {
                                Clients clients = clientsService.getClientsByStringId("A0207");
                                ticketsUpdate.setClients(clients);
                            }
                            if (ticketsUpdate.getClients().getOrganization() == null) {
                                Clients clients = new Clients();
                                Organization organization = organizationService.getOrganizationByStringId("A0207");
                                clients = ticketsUpdate.getClients();
                                clients.setOrganization(organization);
                                ticketsUpdate.setClients(clients);
                            }
                            ticketsService.updateTickets(ticketsUpdate.getId(), ticketsUpdate);
                            LOG.info(baseStatus + " - Base Local Atualizada = " + ticketsUpdate.getId());
                        }

                        // alimenta array de retorno
                        if (tickets.size() == 0) {
                            tickets.add(0, ticketsUpdate);
                        } else {
                            Integer position = tickets.size();
                            tickets.add(position, ticketsUpdate);
                        }
                    }
                }
            }
            up.setHoraFimAtualizacao(pegarHora());
            up.setDataFimAtualizacao(pegarData());
            up.setErrorAtualizacao("Não");
            registroAtualizacao(up.getId(), up);
            LOG.info("Fim da Atualizada de Status");
            tentativaStatus = 0;
            return tickets;
        } catch (Exception e) {
            LOG.info(e);
            up.setHoraFimAtualizacao(pegarHora());
            up.setDataFimAtualizacao(pegarData());
            up.setErrorAtualizacao("Sim");
            registroAtualizacao(up.getId(), up);
            System.out.println(e);
            LOG.info("Reiniciando Serviço");
            erro_id = ultimo_id;
            status_erro = true;
            if (tentativaStatus < 3) {
                tentativaStatus++;
                atualizarSituacaoTickets();
            } else {
                tentativaStatus = 0;
                LOG.info("3 tentativas de Atualização");
            }
            throw new Exception("ERRO: " + e);

        }
    }

    public Update registroIncluirAtualizacao(Update up) {
        return atualizacaoService.saveAtualizacao(up);
    }

    public Update registroAtualizacao(long id, Update up) throws MenssageNotFoundException {
        return atualizacaoService.updateAtualizacao(id, up);
    }

    public Date pegarData() {
        SimpleDateFormat formatadorHora = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date util_Date = new java.util.Date();
        LocalDate current_date = LocalDate.now();
        String data = current_date.getYear() + "-" + current_date.getMonthValue() + "-" + current_date.getDayOfMonth();
        try {
            util_Date = formatadorHora.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return util_Date;
    }

    public Time pegarHora() {
        SimpleDateFormat formatadorTime = new SimpleDateFormat("HH:mm:ss");
        java.util.Date util_time = new java.util.Date();
        LocalDateTime horaagora = LocalDateTime.now();
        String hora = horaagora.getHour() + ":" + horaagora.getMinute() + ":" + horaagora.getSecond();
        try {
            util_time = formatadorTime.parse(hora);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Time time = new Time(util_time.getTime());
        return time;
    }

    public long validaTickets(long id, String data) throws Exception {
        long ret = 0;
        String dataStr = data.substring(0, 10);
        String urlMontada = webService + "&$filter=createdDate%20ge%20" + dataStr
                + "T00:00:00.00z%20and%20createdDate%20le%20"
                + dataStr + "T23:59:59.00z";

        try {
            List<TicketsJson> ticketsjson = new ArrayList<TicketsJson>();

            LOG.info("Iniciou Validação se Existe Tickets:");
            URL url = new URL(urlMontada);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            if (conexao.getResponseCode() != codigoSucesso)
                throw new RuntimeException("HTTP error code : " + conexao.getResponseCode());
            BufferedReader resposta = new BufferedReader(new InputStreamReader((conexao.getInputStream())));

            String jsonEmString = Util.converteJsonEmString(resposta);

            Type listType = new TypeToken<ArrayList<TicketsJson>>() {
            }.getType();

            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            ticketsjson = gson.fromJson(jsonEmString, listType);
            for (int i = 0; i < ticketsjson.size(); i++) {
                if (ticketsjson.get(i).getId() == id) {
                    return 1;
                }
            }
            return ret;
        } catch (Exception e) {
            LOG.info(e);
            return 2;
        }
    }

    public void atualizaCustomClients() throws Exception {
        String urlMontada = "https://api.movidesk.com/public/v1/tickets?" +
                "token=6fd0a503-b0d1-40fe-91f8-4cc4e3e027f7&$select=id&$orderby=id" +
                "&$expand=customFieldValues($expand=items)";// &$top=100&$skip=180000

        Boolean end = true;
        Update up = new Update();
        long quant = 0;
        quant = ticketsService.countTicketsSemClientCustom();
        LOG.info("Quantidade de Registro Inicio = " + quant);

        try {
            // incluir log no banco
            up.setTipoAtualizacao(3);
            up.setHoraInicioAtualizacao(pegarHora());
            up.setDataInicioAtualizacao(pegarData());
            up = registroIncluirAtualizacao(up);

            if (customClientService.countCustomClients() == 0) {
                CustomClients customclients = new CustomClients();
                customclients.setId(1);
                customclients.setCustomFieldItem("Sem Custom Clients");
                customClientService.saveCustomClients(customclients);
            }

            while (end) {
                List<TicketsJson> ticketsjson = new ArrayList<TicketsJson>();

                URL url = new URL(urlMontada + "&$top=1000" + "&$skip=" + quant);
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

                if (conexao.getResponseCode() != codigoSucesso)
                    throw new RuntimeException("HTTP error code : " + conexao.getResponseCode());

                BufferedReader resposta = new BufferedReader(new InputStreamReader((conexao.getInputStream())));
                String jsonEmString = Util.converteJsonEmString(resposta);

                Type listType = new TypeToken<ArrayList<TicketsJson>>() {
                }.getType();
                GsonBuilder builder = new GsonBuilder();
                builder.setPrettyPrinting();
                Gson gson = builder.create();
                ticketsjson = gson.fromJson(jsonEmString, listType);
                LOG.info("Iniciou Importação Custom Clients Parcial");

                for (int i = 0; i < ticketsjson.size(); i++) {
                    Tickets ticketsAux = new Tickets();
                    if (ticketsService.getTicketsExist(ticketsjson.get(i).getId())) {
                        List<CustomFieldValues> customField = new ArrayList<CustomFieldValues>();
                        ticketsAux = ticketsService.getTicketsById(ticketsjson.get(i).getId());
                        customField = ticketsjson.get(i).getCustomFieldItem();
                    
                        if (customField == null) {
                            CustomClients custom = customClientService.getCustomClientsById(1);
                            ticketsAux.setCustomClients(custom);

                        } else if (customField.size() > 0) {
                            for (int a = 0; a < customField.size(); a++) {
                                if (customField.get(a).getCustomFieldId() == 24609) {
                                    CustomClients custom = new CustomClients();
                                    if (customField.get(a).getItems().size() > 0) {
                                        if (customClientService.countCustomClientsItem(
                                                customField.get(a).getItems().get(0).getCustomFieldItem()) > 0) {
                                            custom = customClientService
                                                    .getCustomClientsByString(
                                                            customField.get(a).getItems().get(0).getCustomFieldItem());
                                        } else {
                                            custom.setCustomFieldItem(
                                                    customField.get(a).getItems().get(0).getCustomFieldItem());
                                            custom = customClientService.saveCustomClients(custom);
                                        }

                                    } else {
                                        custom = customClientService.getCustomClientsById(1);
                                    }
                                    ticketsAux.setCustomClients(custom);
                                }
                            }
                        } else {
                            CustomClients custom = customClientService.getCustomClientsById(1);
                            ticketsAux.setCustomClients(custom);
                        }
                        ticketsService.updateTickets(ticketsAux.getId(), ticketsAux);
                        LOG.info("Atualizado Custom Clients Registro = " + ticketsAux.getId());

                    }

                }

                end = ticketsjson.size() >= 1000;
                quant = quant + 1000;
            }
            LOG.info("Terminou Importação Custom Clients de Tickets");
            up.setHoraFimAtualizacao(pegarHora());
            up.setDataFimAtualizacao(pegarData());
            up.setErrorAtualizacao("Não");
            registroAtualizacao(up.getId(), up);
            tentativaCustomClients = 0;
        } catch (Exception e) {
            LOG.info(e);
            up.setHoraFimAtualizacao(pegarHora());
            up.setDataFimAtualizacao(pegarData());
            up.setErrorAtualizacao("Sim");
            registroAtualizacao(up.getId(), up);
            LOG.info("Reiniciando Serviço");
            if (tentativaCustomClients < 3) {
                tentativaCustomClients++;
                atualizaCustomClients();
            } else {
                LOG.info("3 tentativas de Atualização");
                tentativaCustomClients = 0;
            }
            throw new Exception("ERRO: " + e);
        }

    }
}
