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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

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
import br.hendrew.movidesk.exceptionhandler.ExceptionHandler;
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

    @GET
	@PermitAll
	@Path("/status/{status}")
    @Operation(summary = "Pegar Tickets Status", description = "Pesquisa por um Status")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Tickets.class))),
			@APIResponse(responseCode = "404", description = "Tickets not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionHandler.ErrorResponseBody.class))) })
	public List<Tickets> getstatus(@PathParam("status") String status) throws MenssageNotFoundException {
        return ticketsService.getTicketsbaseStatus(status);
    }

    @GET
	@PermitAll
	@Path("/urgency/{urgency}")
    @Operation(summary = "Pegar Tickets urgency", description = "Pesquisa por um urgency")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Tickets.class))),
			@APIResponse(responseCode = "404", description = "Tickets not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionHandler.ErrorResponseBody.class))) })
	public List<Tickets> geturgency(@PathParam("urgency") String urgency) throws MenssageNotFoundException {
        return ticketsService.getTicketsUrgency(urgency);
    }

    @GET
	@PermitAll
	@Path("/statussum/")
    @Operation(summary = "Pegar Quantidade Tickets por Status", description = "Pesquisa quantidade por um Status")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketsSituacao.class))),
			@APIResponse(responseCode = "404", description = "Tickets not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionHandler.ErrorResponseBody.class))) })
	public TicketsSituacao getSumTickets() throws MenssageNotFoundException {
        return ticketsService.getTicketsbaseStatusSUM();
    }

    @GET
	@PermitAll
	@Path("/statussevensum/")
    @Operation(summary = "Pegar Quantidade dos 7 dias de Tickets por Status", description = "Pesquisa quantidade dos 7 dias de por um Status")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketsSituacao.class))),
			@APIResponse(responseCode = "404", description = "Tickets not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionHandler.ErrorResponseBody.class))) })
	public TicketsSituacao getSevenSumTickets() throws MenssageNotFoundException {
        return ticketsService.getTicketsbaseStatusSUMDate();
    }

    @GET
	@PermitAll
	@Path("/statusdaysum/")
    @Operation(summary = "Pegar Quantidade do dia atual de Tickets por Status", description = "Pesquisa quantidade do dia atual por um Status")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketsSituacao.class))),
			@APIResponse(responseCode = "404", description = "Tickets not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionHandler.ErrorResponseBody.class))) })
	public TicketsSituacao getDaySumTickets() throws MenssageNotFoundException {
        return ticketsService.getTicketsbaseStatusSUMDay();
    }


    @GET
	@PermitAll
	@Path("/urgencysum/")
    @Operation(summary = "Pegar Quantidade Tickets por Urgencia", description = "Pesquisa quantidade por um Urgencia")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketsUrgency.class))),
			@APIResponse(responseCode = "404", description = "Tickets not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionHandler.ErrorResponseBody.class))) })
	public TicketsUrgency getSumUrgency() throws MenssageNotFoundException {
        return ticketsService.getTicketsUrgencySUM();
    }

    @GET
	@PermitAll
	@Path("/urgencysevensum/")
    @Operation(summary = "Pegar Quantidade dos 7 dias Tickets por Urgencia", description = "Pesquisa Quantidade dos 7 dias por um Urgencia")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketsUrgency.class))),
			@APIResponse(responseCode = "404", description = "Tickets not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionHandler.ErrorResponseBody.class))) })
	public TicketsUrgency getSumSevenUrgency() throws MenssageNotFoundException {
        return ticketsService.getTicketsUrgencySUMDate();
    }

    @GET
	@PermitAll
	@Path("/urgencydaysum/")
    @Operation(summary = "Pegar Quantidade do dia atual Tickets por Urgencia", description = "Pesquisa Quantidade do dia atual por um Urgencia")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketsUrgency.class))),
			@APIResponse(responseCode = "404", description = "Tickets not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionHandler.ErrorResponseBody.class))) })
	public TicketsUrgency getSumDayUrgency() throws MenssageNotFoundException {
        return ticketsService.getTicketsUrgencySUMDay();
    }

    @GET
	@PermitAll
	@Path("/type/{type}")
    @Operation(summary = "Pegar Tickets Tipo", description = "Pesquisa por um tipo")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Tickets.class))),
			@APIResponse(responseCode = "404", description = "Tickets not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionHandler.ErrorResponseBody.class))) })
	public List<Tickets> gettype(@PathParam("type") long type) throws MenssageNotFoundException {
        return ticketsService.getTicketsType(type);
    }

    @GET
	@PermitAll
	@Path("/typesum/")
    @Operation(summary = "Pegar Quantidade Tickets por tipo", description = "Pesquisa quantidade por um tipo")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketsType.class))),
			@APIResponse(responseCode = "404", description = "Tickets not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionHandler.ErrorResponseBody.class))) })
	public TicketsType getSumType() throws MenssageNotFoundException {
        return ticketsService.getTicketsTypeSUM();
    }

    @GET
	@PermitAll
	@Path("/ticketsowner/")
    @Operation(summary = "Pegar Tickets por Owner", description = "Pesquisa quantidade por Analista")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AgenteTickets.class))),
			@APIResponse(responseCode = "404", description = "Tickets not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionHandler.ErrorResponseBody.class))) })
	public List<AgenteTickets> getOwnerTickets() throws MenssageNotFoundException {
        return ticketsService.OwnerTickets();
    }

    @GET
	@PermitAll
	@Path("/ticketsownerseven/")
    @Operation(summary = "Pegar Tickets dos ultimos 7 dias por Owner", description = "Pesquisa quantidade dos ultimos 7 dias por Analista")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AgenteTickets.class))),
			@APIResponse(responseCode = "404", description = "Tickets not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionHandler.ErrorResponseBody.class))) })
	public List<AgenteTickets> getOwnerTicketsSeven() throws MenssageNotFoundException {
        return ticketsService.OwnerTicketsSeven();
    }

    @GET
	@PermitAll
	@Path("/ticketsownerday/")
    @Operation(summary = "Pegar Tickets do dia Atual por Owner", description = "Pesquisa quantidade do tickets do dia atual por Analista")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AgenteTickets.class))),
			@APIResponse(responseCode = "404", description = "Tickets not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionHandler.ErrorResponseBody.class))) })
	public List<AgenteTickets> getOwnerTicketsDay() throws MenssageNotFoundException {
        return ticketsService.OwnerTicketsDay();
    }

    @GET
	@PermitAll
	@Path("/ownercategory/")
    @Operation(summary = "Pegar Tickets por Owner e Categoria", description = "Pesquisa quantidade por Analista e Categoria")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AgenteCategory.class))),
			@APIResponse(responseCode = "404", description = "Tickets not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionHandler.ErrorResponseBody.class))) })
	public List<AgenteCategory> getOwnerTicketsAnalista() throws MenssageNotFoundException {
        return ticketsService.OwnerCategory();
    }

    @GET
	@PermitAll
	@Path("/sevencategory/")
    @Operation(summary = "Pegar Quantidade dos 7 dias Tickets  por Categoria", description = "Pesquisa Quantidade dos 7 dias por Categoria")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Category.class))),
			@APIResponse(responseCode = "404", description = "Tickets not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionHandler.ErrorResponseBody.class))) })
	public Category getSevenCategories() throws MenssageNotFoundException {
        return ticketsService.CategorySeven();
    }

    @GET
	@PermitAll
	@Path("/daycategory/")
    @Operation(summary = "Pegar Quantidade dos dia atual Tickets  por Categoria", description = "Pesquisa Quantidade do dia atual por Categoria")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Category.class))),
			@APIResponse(responseCode = "404", description = "Tickets not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionHandler.ErrorResponseBody.class))) })
	public Category getDayCategories() throws MenssageNotFoundException {
        return ticketsService.CategoryDay();
    }

    @GET
	@PermitAll
	@Path("/category/")
    @Operation(summary = "Pegar Tickets por Categoria", description = "Pesquisa quantidade por Categoria")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Category.class))),
			@APIResponse(responseCode = "404", description = "Tickets not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionHandler.ErrorResponseBody.class))) })
	public Category getCategories() throws MenssageNotFoundException {
        return ticketsService.Category();
    }

    @GET
	@PermitAll
	@Path("/ownerjustification/")
    @Operation(summary = "Pegar Tickets por Owner e Justification", description = "Pesquisa quantidade por Analista e Justification")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AgenteJustification.class))),
			@APIResponse(responseCode = "404", description = "Tickets not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionHandler.ErrorResponseBody.class))) })
	public List<AgenteJustification> getOwnerTicketsJustification() throws MenssageNotFoundException {
        return ticketsService.ownerJustifications();
    }

    @GET
	@PermitAll
	@Path("/justification/")
    @Operation(summary = "Pegar Tickets por Justifativa", description = "Pesquisa quantidade por Justificativa")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Justification.class))),
			@APIResponse(responseCode = "404", description = "Tickets not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionHandler.ErrorResponseBody.class))) })
	public Justification getJustification() throws MenssageNotFoundException {
        return ticketsService.justification();
    }

    @GET
	@PermitAll
	@Path("/anos/")
    @Operation(summary = "Pega Quantidade de Ticket por Ano", description = "Pega Quantidade de Ticket por Ano")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketsAnos.class))),
			@APIResponse(responseCode = "404", description = "Tickets not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionHandler.ErrorResponseBody.class))) })
	public List<TicketsAnos> getAnos() throws MenssageNotFoundException {
        return ticketsService.ticketsAnos();
    }

    @GET
	@PermitAll
	@Path("/anoscategory/")
    @Operation(summary = "Pega Quantidade de Ticket por Ano", description = "Pega Quantidade de Ticket por Ano")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketsAnos.class))),
			@APIResponse(responseCode = "404", description = "Tickets not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionHandler.ErrorResponseBody.class))) })
	public List<TicketsAnosCategory> getAnosCategory() throws MenssageNotFoundException {
        return ticketsService.ticketsAnosCategory();
    }

    @GET
	@PermitAll
	@Path("/mesescategory/")
    @Operation(summary = "Pega Quantidade de Ticket dos ultimos 12 meses", description = "Pega Quantidade de Ticket dos ultimos 12 meses")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketsMesesDias.class))),
			@APIResponse(responseCode = "404", description = "Tickets not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionHandler.ErrorResponseBody.class))) })
	public List<TicketsMesesDias> getMesesCategory() throws MenssageNotFoundException {
        return ticketsService.ticketsMesesCategory();
    }

    @GET
	@PermitAll
	@Path("/sevendaycategory/")
    @Operation(summary = "Pega Quantidade de Ticket dos ultimos 7 dias", description = "Pega Quantidade de Ticket dos ultimos 7 dias")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketsMesesDias.class))),
			@APIResponse(responseCode = "404", description = "Tickets not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionHandler.ErrorResponseBody.class))) })
	public List<TicketsMesesDias> getSevenDayCategory() throws MenssageNotFoundException {
        return ticketsService.ticketsSevenCategory();
    }

    @GET
	@PermitAll
	@Path("/daydaycategory/")
    @Operation(summary = "Pega Quantidade de Ticket do dia Atual", description = "Pega Quantidade de Ticket dos dia atual")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketsMesesDias.class))),
			@APIResponse(responseCode = "404", description = "Tickets not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionHandler.ErrorResponseBody.class))) })
	public List<TicketsMesesDias> getDayDayCategory() throws MenssageNotFoundException {
        return ticketsService.ticketsDayCategory();
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
