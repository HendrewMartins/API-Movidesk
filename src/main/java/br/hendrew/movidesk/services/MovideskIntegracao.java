package br.hendrew.movidesk.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import br.hendrew.movidesk.entity.Tickets;
import br.hendrew.movidesk.validation.Util;

public class MovideskIntegracao {
    static String webService = "https://api.movidesk.com/public/v1/tickets?" +
            "token=6fd0a503-b0d1-40fe-91f8-4cc4e3e027f7&$select=id,type,subject,createdDate,category," +
            "urgency,status,baseStatus,owner&$orderby=id&$expand=owner&$top=1000&$skip=";
    static int codigoSucesso = 200;

    public List<Tickets> buscarTickets() throws Exception {
        String urlMontada = webService;
        List<Tickets> tickets = new ArrayList<Tickets>();
        Boolean end = true;
        long quant = 100000;

        try {

            while (end) {
                List<Tickets> ticketsAux = new ArrayList<Tickets>();

                URL url = new URL(urlMontada + quant);
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
                    if(tickets.size()==0){
                        tickets.add(0, ticketsAux.get(i));
                    }
                    else{
                        Integer position = tickets.size();
                        tickets.add(position, ticketsAux.get(i));
                    }
                    
                }

                end = ticketsAux.size() >= 1000;
                quant = quant + 1000;
                System.out.println(quant);
            }

            return tickets;
        } catch (Exception e) {
            throw new Exception("ERRO: " + e);
        }
    }
}
