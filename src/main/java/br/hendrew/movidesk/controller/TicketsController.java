package br.hendrew.movidesk.controller;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import br.hendrew.movidesk.entity.Tickets;
import br.hendrew.movidesk.services.MovideskIntegracao;

import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@RequestScoped
@Path("api/tickets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TicketsController {
    
    @GET
	@PermitAll
	@Operation(summary = "Listar Tickets Pendentes", description = "Lista Tickets Pendentes")
	@APIResponses(value = @APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Tickets.class))))
	public List<Tickets> getTickets() throws Exception {
        MovideskIntegracao mov = new MovideskIntegracao();
        return mov.buscarTickets();
    }
}
