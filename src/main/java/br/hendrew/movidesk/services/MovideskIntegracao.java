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

import br.hendrew.movidesk.entity.Owner;
import br.hendrew.movidesk.entity.Tickets;
import br.hendrew.movidesk.entity.Update;
import br.hendrew.movidesk.exception.MenssageNotFoundException;
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

    static String webService = "https://api.movidesk.com/public/v1/tickets?" +
            "token=6fd0a503-b0d1-40fe-91f8-4cc4e3e027f7&$select=id,type,subject,createdDate,"
            + "category,urgency,status,baseStatus,protocol,justification,origin,originEmailAccount,"
            + "ownerTeam,resolvedIn,reopenedIn,closedIn,lastActionDate,actionCount,lastUpdate,"
            + "lifetimeWorkingTime,stoppedTime,stoppedTimeWorkingTime,resolvedInFirstCall,chatTalkTime,"
            + "chatWaitingTime,slaAgreement,slaAgreementRule,slaSolutionTime,slaResponseTime"
            + "&$orderby=id&$expand=owner";

    static int codigoSucesso = 200;
    private final TicketsService ticketsService;
    private final OwnerService ownerService;
    private final AtualizacaoService atualizacaoService;

    @Inject
    public MovideskIntegracao(TicketsService ticketsService, OwnerService ownerService,
            AtualizacaoService atualizacaoService) {
        this.ticketsService = ticketsService;
        this.ownerService = ownerService;
        this.atualizacaoService = atualizacaoService;
    }

    public List<Tickets> buscarTodosTickets() throws Exception {
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
                    List<Tickets> ticketsAux = new ArrayList<Tickets>();
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

                    URL url = new URL(urlMontada + "&$top=1000" + "&$skip=" + quant);
                    HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

                    if (conexao.getResponseCode() != codigoSucesso)
                        throw new RuntimeException("HTTP error code : " + conexao.getResponseCode());

                    BufferedReader resposta = new BufferedReader(new InputStreamReader((conexao.getInputStream())));
                    String jsonEmString = Util.converteJsonEmString(resposta);

                    Type listType = new TypeToken<ArrayList<Tickets>>() {
                    }.getType();
                    GsonBuilder builder = new GsonBuilder();
                    builder.setPrettyPrinting();
                    Gson gson = builder.create();
                    ticketsAux = gson.fromJson(jsonEmString, listType);
                    LOG.info("Inicio Importação Total de Registro");
                    for (int i = 0; i < ticketsAux.size(); i++) {
                        if (tickets.size() == 0) {
                            tickets.add(0, ticketsAux.get(i));
                        } else {
                            Integer position = tickets.size();
                            tickets.add(position, ticketsAux.get(i));

                        }
                        if (ticketsAux.get(i).getOwner() == null) {
                            Owner owner = ownerService.getOwnerByStringId("A0207");
                            ticketsAux.get(i).setOwner(owner);
                        }
                        ticketsService.saveTickets(ticketsAux.get(i));
                        LOG.info("Importado Registro = " + ticketsAux.get(i).getId());
                        System.out.println();
                    }

                    end = ticketsAux.size() >= 1000;
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
                List<Tickets> ticketsAux = new ArrayList<Tickets>();

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

                URL url = new URL(urlMontada + "&$top=1000" + "&$skip=" + quant);
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

                if (conexao.getResponseCode() != codigoSucesso)
                    throw new RuntimeException("HTTP error code : " + conexao.getResponseCode());

                BufferedReader resposta = new BufferedReader(new InputStreamReader((conexao.getInputStream())));
                String jsonEmString = Util.converteJsonEmString(resposta);

                Type listType = new TypeToken<ArrayList<Tickets>>() {
                }.getType();
                GsonBuilder builder = new GsonBuilder();
                builder.setPrettyPrinting();
                Gson gson = builder.create();
                ticketsAux = gson.fromJson(jsonEmString, listType);
                LOG.info("Iniciou Importação Parcial");
                for (int i = 0; i < ticketsAux.size(); i++) {
                    if (tickets.size() == 0) {
                        tickets.add(0, ticketsAux.get(i));
                    } else {
                        Integer position = tickets.size();
                        tickets.add(position, ticketsAux.get(i));
                    }
                    if (ticketsAux.get(i).getOwner() == null) {
                        Owner owner = ownerService.getOwnerByStringId("A0207");
                        ticketsAux.get(i).setOwner(owner);
                    }

                    if (ticketsAux.get(i).getActionCount() >= 0) {
                    } else {

                    }

                    if (ticketsService.getTicketsExist(ticketsAux.get(i).getId())) {
                        ticketsService.updateTickets(ticketsAux.get(i).getId(), ticketsAux.get(i));
                        // System.out.println(ticketsAux.get(i).getId());
                        LOG.info("Atualizado Registro = " + ticketsAux.get(i).getId());
                    } else {
                        ticketsService.saveTickets(ticketsAux.get(i));
                        // System.out.println(ticketsAux.get(i).getId());
                        LOG.info("Incluido Registro = " + ticketsAux.get(i).getId());
                    }

                }

                end = ticketsAux.size() >= 1000;
                quant = quant + 1000;
            }
            LOG.info("Terminou Importação Parcial de Tickets");
            up.setHoraFimAtualizacao(pegarHora());
            up.setDataFimAtualizacao(pegarData());
            up.setErrorAtualizacao("Não");
            registroAtualizacao(up.getId(), up);
            tentativaUpdate = 0;
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
                List<Tickets> ticketsAux = ticketsService.getTicketsbaseStatus(baseStatus);
                LOG.info("Quantidade de Registro - " + baseStatus + " = " + ticketsAux.size());
                for (int i = 0; i < ticketsAux.size(); i++) {

                    // captura id
                    ultimo_id = ticketsAux.get(i).getId();// System.out.println("Base Local" +
                                                          // ticketsAux.get(i).getId());

                    if (status_erro) {
                        String data = ticketsAux.get(i).getCreatedDate();
                        if (ultimo_id == erro_id) {
                            long ret = validaTickets(erro_id, data);
                            if (ret == 0) {
                                ticketsService.deleteTickets(erro_id);
                                localizar = true;
                                erro_id = 0;
                                status_erro = false;
                                long itemp = i;
                                itemp++;
                                if (itemp < ticketsAux.size()) {
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
                    LOG.info("Base Local = " + ticketsAux.get(i).getId());
                    if (localizar) {
                        URL url = new URL(urlMontada + ticketsAux.get(i).getId());
                        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                        if (conexao.getResponseCode() != codigoSucesso)
                            throw new RuntimeException("HTTP error code : " + conexao.getResponseCode());
                        BufferedReader resposta = new BufferedReader(new InputStreamReader((conexao.getInputStream())));

                        String jsonEmString = Util.converteJsonEmString(resposta);

                        GsonBuilder builder = new GsonBuilder();
                        builder.setPrettyPrinting();
                        Gson gson = builder.create();

                        Tickets ticketsUpdate = gson.fromJson(jsonEmString, Tickets.class);
                        if (ticketsService.getTicketsExist(ticketsUpdate.getId())) {
                            if (ticketsUpdate.getOwner() == null) {
                                Owner owner = ownerService.getOwnerByStringId("A0207");
                                ticketsUpdate.setOwner(owner);
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
            tentativaStatus=0;
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
            List<Tickets> ticketsAux = new ArrayList<Tickets>();

            LOG.info("Iniciou Validação se Existe Tickets:");
            URL url = new URL(urlMontada);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            if (conexao.getResponseCode() != codigoSucesso)
                throw new RuntimeException("HTTP error code : " + conexao.getResponseCode());
            BufferedReader resposta = new BufferedReader(new InputStreamReader((conexao.getInputStream())));

            String jsonEmString = Util.converteJsonEmString(resposta);

            Type listType = new TypeToken<ArrayList<Tickets>>() {
            }.getType();

            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            ticketsAux = gson.fromJson(jsonEmString, listType);
            for (int i = 0; i < ticketsAux.size(); i++) {
                if (ticketsAux.get(i).getId() == id) {
                    return 1;
                }
            }
            return ret;
        } catch (Exception e) {
            LOG.info(e);
            return 2;
        }
    }
}
