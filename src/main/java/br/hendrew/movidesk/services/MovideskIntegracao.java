package br.hendrew.movidesk.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import br.hendrew.movidesk.entity.Owner;
import br.hendrew.movidesk.entity.Tickets;
import br.hendrew.movidesk.validation.Util;

@ApplicationScoped
public class MovideskIntegracao {
    static String webService = "https://api.movidesk.com/public/v1/tickets?" +
            "token=6fd0a503-b0d1-40fe-91f8-4cc4e3e027f7&$select=id,type,subject,createdDate,category," +
            "urgency,status,baseStatus,owner&$orderby=id&$expand=owner";
    static int codigoSucesso = 200;

    private final TicketsService ticketsService;
    private final OwnerService ownerService;

    @Inject
    public MovideskIntegracao(TicketsService ticketsService, OwnerService ownerService) {
        this.ticketsService = ticketsService;
        this.ownerService = ownerService;
    }

    public List<Tickets> buscarTodosTickets() throws Exception {
        String urlMontada = webService;
        List<Tickets> tickets = new ArrayList<Tickets>();
        Boolean end = true;
        long quant = 0;

        if (ticketsService.countTickets() == 0) {
            try {

                while (end) {
                    List<Tickets> ticketsAux = new ArrayList<Tickets>();

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
                        System.out.println(ticketsAux.get(i).getId());
                    }

                    end = ticketsAux.size() >= 1000;
                    quant = quant + 1000;
                }

                return tickets;
            } catch (Exception e) {
                System.out.println(e);
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
        long quant = ticketsService.countTickets() - 500;

        try {

            while (end) {
                List<Tickets> ticketsAux = new ArrayList<Tickets>();

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


                    if (ticketsService.getTicketsExist(ticketsAux.get(i).getId())) {
                        ticketsService.updateTickets(ticketsAux.get(i).getId(), ticketsAux.get(i)); 
                        System.out.println(ticketsAux.get(i).getId());
                    } else {
                        ticketsService.saveTickets(ticketsAux.get(i));
                        System.out.println(ticketsAux.get(i).getId());
                    }

                }

                end = ticketsAux.size() >= 1000;
                quant = quant + 1000;
            }

            return tickets;
        } catch (Exception e) {
            System.out.println(e);
            throw new Exception("ERRO: " + e);
        }
    }

    public List<Tickets> atualizarSituacaoTickets() throws Exception {
        String urlMontada = "https://api.movidesk.com/public/v1/tickets?" +
                "token=6fd0a503-b0d1-40fe-91f8-4cc4e3e027f7&$select=id,type,subject,createdDate,category," +
                "urgency,status,baseStatus,owner&id=";
        List<Tickets> tickets = new ArrayList<Tickets>();
        String baseStatus = "";

        try {
            for (int x = 0; x < 3; x++) {
                switch (x) {
                    case 0:
                        baseStatus = "InAttendance";
                        break;
                    case 1:
                        baseStatus = "New";
                        break;
                    case 2:
                        baseStatus = "Stopped";
                        break;
                }
                List<Tickets> ticketsAux = ticketsService.getTicketsbaseStatus(baseStatus);
                for (int i = 0; i < ticketsAux.size(); i++) {
                    System.out.println("Base Local" + ticketsAux.get(i).getId());
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
                        System.out.println("Base Movidesk" + ticketsUpdate.getId());
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
            return tickets;
        } catch (Exception e) {
            System.out.println(e);
            throw new Exception("ERRO: " + e);
        }
    }
}
