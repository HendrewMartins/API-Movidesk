package br.hendrew.movidesk.controller;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import br.hendrew.movidesk.entity.Update;
import br.hendrew.movidesk.entity.UpdateAux;
import br.hendrew.movidesk.exception.MenssageNotFoundException;
import br.hendrew.movidesk.exceptionhandler.ExceptionHandler;
import br.hendrew.movidesk.services.AtualizacaoService;

import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@RequestScoped
@Path("api/atualizacao")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AtualizacaoController {
    private final AtualizacaoService atualizacaoService;

    @Inject
    public AtualizacaoController(AtualizacaoService atualizacaoService) {
        this.atualizacaoService = atualizacaoService;
    }

    @GET
    @PermitAll
    @Operation(summary = "Pegar Ultima Atualização", description = "Pegar Ultima Atualização")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Update.class))),
            @APIResponse(responseCode = "404", description = "Tickets not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionHandler.ErrorResponseBody.class))) })
    public UpdateAux getstatus() throws MenssageNotFoundException {
        UpdateAux aux = new UpdateAux();
        Update up = atualizacaoService.getEofAtualizacao();
        aux.setId(up.getId());
        aux.setTipoAtualizacao(up.getTipoAtualizacao());
        String dataAux = ""+up.getDataFimAtualizacao();
        aux.setDataFimAtualizacao(""+dataAux.substring(8,10)+"/"
                                    +dataAux.substring(5, 7)+"/"
                                    +dataAux.substring(0, 4));
        aux.setHoraFimAtualizacao(""+up.getHoraFimAtualizacao());
        return aux; 
    }

}
