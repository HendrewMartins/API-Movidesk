package br.hendrew.movidesk.controller;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import br.hendrew.movidesk.entity.Owner;
import br.hendrew.movidesk.entity.Tickets;
import br.hendrew.movidesk.services.MovideskIntegracao;
import br.hendrew.movidesk.services.TicketsService;

import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@RequestScoped
@Path("api/tickets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TicketsController {

    private final TicketsService ticketsService;
    private final MovideskIntegracao mov;

    @Inject
    public TicketsController(TicketsService ticketsService,
            MovideskIntegracao mov) {
        this.ticketsService = ticketsService;
        this.mov = mov;
        
    }

    @GET
    @PermitAll
    @Path("/importTickets")
    @Operation(summary = "Importar TODOS TICKETS ARTIA", description = "Importar Todos os Tickets do Artia")
    @APIResponses(value = @APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Tickets.class))))
    public List<Tickets> getTickets() throws Exception {
        return mov.buscarTodosTickets();
    }

    @GET
    @PermitAll
    @Path("/updateTickets")
    @Operation(summary = "Importar Tickets e Atualizar", description = "Importar e Atualizar os Tickets do Artia")
    @APIResponses(value = @APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Tickets.class))))
    public List<Tickets> getAtualizarTickets() throws Exception {
        return mov.atualizarTickets();
    }

    @GET
    @PermitAll
    @Path("/statusTickets")
    @Operation(summary = "Atualiza Tickets Status", description = "Atualiza Status New, InAttendance, Stopped")
    @APIResponses(value = @APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Tickets.class))))
    public List<Tickets> getStatusTickets() throws Exception {
        return mov.atualizarSituacaoTickets();
    }

    @POST
    @PermitAll
    @Path("/save")
    @Operation(summary = "Adicionar Tickets Manualmente", description = "Criar um Tickets e persistir no banco")
    @APIResponses(value = @APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Tickets.class))))
    public Tickets createAluno(@Valid TicketsDto ticketsDto) {
        Tickets tickets = ticketsService.saveTickets(ticketsDto.toTickets());
        return tickets;
    }

    @Schema(name = "TicketsDto", description = "DTO para Criar um novo Tickets")
    public static class TicketsDto {

        @Schema(title = "id", required = true)
        private long id;;

        @Schema(title = "baseStatus", required = true)
        private String baseStatus;

        @Schema(title = "status", required = true)
        private String status;

        @Schema(title = "urgency", required = true)
        private String urgency;

        @Schema(title = "category", required = true)
        private String category;

        @Schema(title = "createdDate", required = true)
        private String createdDate;

        @Schema(title = "subject", required = true)
        private String subject;

        @Schema(title = "type")
        private long type;

        @Schema(title = "owner")
        private Owner owner;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getBaseStatus() {
            return baseStatus;
        }

        public void setBaseStatus(String baseStatus) {
            this.baseStatus = baseStatus;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUrgency() {
            return urgency;
        }

        public void setUrgency(String urgency) {
            this.urgency = urgency;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public long getType() {
            return type;
        }

        public void setType(long type) {
            this.type = type;
        }

        public Owner getOwner() {
            return owner;
        }

        public void setOwner(Owner owner) {
            this.owner = owner;
        }

        public Tickets toTickets() {

            Tickets tickets = new Tickets();

            tickets.setId(id);
            tickets.setBaseStatus(baseStatus);
            tickets.setCategory(category);
            tickets.setCreatedDate(createdDate);
            tickets.setOwner(owner);
            tickets.setStatus(status);
            tickets.setSubject(subject);
            tickets.setType(type);
            tickets.setUrgency(urgency);

            return tickets;
        }
    }
}
