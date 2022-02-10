package br.hendrew.movidesk.controller;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import br.hendrew.movidesk.entity.CategoryOwner;
import br.hendrew.movidesk.entity.Owner;
import br.hendrew.movidesk.exception.MenssageNotFoundException;
import br.hendrew.movidesk.exceptionhandler.ExceptionHandler;
import br.hendrew.movidesk.services.OwnerService;

@RequestScoped
@Path("api/owner")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OwnerController {

    private final OwnerService ownerService;

    @Inject
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GET
    @PermitAll
    @Operation(summary = "Listar Owner", description = "Lista todos Owner")
    @APIResponses(value = @APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Owner.class))))
    public List<Owner> getOwner() {
        return ownerService.getAllOwner();
    }

    @GET
    @PermitAll
    @Path("/count")
    @Operation(summary = "Listar Category Owner", description = "Lista todos Category Owner")
    @APIResponses(value = @APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class))))
    public long getCountOwner() {
        return ownerService.countOwner();
    }

    @GET
    @PermitAll
    @Path("/id/{id}")
    @Operation(summary = "Pegar  Owner", description = "Pesquisa por um ID o  Owner")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Owner.class))),
            @APIResponse(responseCode = "404", description = "Owner not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionHandler.ErrorResponseBody.class))) })
    public Owner getOwner(@PathParam("id") String id) throws MenssageNotFoundException {
        return ownerService.getOwnerByStringId(id);
    }

    @GET
    @PermitAll
    @Path("/page/{page}")
    @Operation(summary = "Pegar  Owner", description = "Pesquisa por um ID o  Owner")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Owner.class))),
            @APIResponse(responseCode = "404", description = "Owner not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionHandler.ErrorResponseBody.class))) })
    public List<Owner> getPageOwner(@PathParam("page") int page) throws MenssageNotFoundException {
        return ownerService.getOwnerPage(page, 10);
    }

    

    @POST
    @PermitAll
    @Path("/save")
    @Operation(summary = "Adicionar Owner", description = "Criar um novo Owner e persistir no banco")
    @APIResponses(value = @APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Owner.class))))
    public Owner createOwner(@Valid OwnerDTO ownerDTO) {
        return ownerService.saveOwner(ownerDTO.toOwner());

    }

    @PUT
    @PermitAll
    @Path("/edit/{id}")
    @Operation(summary = "Atualizar um Owner", description = "Atualizar um Owner existente via id")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Owner.class))),
            @APIResponse(responseCode = "404", description = "Alunos not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionHandler.ErrorResponseBody.class))) })
    public Owner updateOwner(@PathParam("id") String id, @Valid OwnerDTO ownerDTO)
            throws MenssageNotFoundException {
        return ownerService.updateOwner(id, ownerDTO.toOwner());
    }

    @DELETE
    @PermitAll
    @Path("/delete/{id}")
    @Operation(summary = "Apagar a Owner", description = "Apagar um Owner pelo ID")
    @APIResponses(value = { @APIResponse(responseCode = "204", description = "Success"),
            @APIResponse(responseCode = "404", description = "Owner not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionHandler.ErrorResponseBody.class))) })
    public boolean deleteOwner(@PathParam("id") String id) throws MenssageNotFoundException {
        ownerService.deleteOwner(id);
        return true;
    }

    @Schema(name = "OwnerDTO", description = "DTO para Criar um novo Owner")
    public static class OwnerDTO {

        @Schema(title = "personType", required = true)
        private long personType;

        @Schema(title = "profileType", required = true)
        private long profileType;

        @Schema(title = "businessName", required = true)
        private String businessName;

        @Schema(title = "email", required = true)
        private String email;

        @Schema(title = "phone", required = true)
        private String phone;

        @Schema(title = "pathPicture", required = true)
        private String pathPicture;

        @Schema(title = "categoryOwner", required = true)
        private CategoryOwner categoryOwner;

       

        public long getPersonType() {
            return personType;
        }



        public void setPersonType(long personType) {
            this.personType = personType;
        }



        public long getProfileType() {
            return profileType;
        }



        public void setProfileType(long profileType) {
            this.profileType = profileType;
        }



        public String getBusinessName() {
            return businessName;
        }



        public void setBusinessName(String businessName) {
            this.businessName = businessName;
        }



        public String getEmail() {
            return email;
        }



        public void setEmail(String email) {
            this.email = email;
        }



        public String getPhone() {
            return phone;
        }



        public void setPhone(String phone) {
            this.phone = phone;
        }



        public String getPathPicture() {
            return pathPicture;
        }



        public void setPathPicture(String pathPicture) {
            this.pathPicture = pathPicture;
        }



        public CategoryOwner getCategoryOwner() {
            return categoryOwner;
        }



        public void setCategoryOwner(CategoryOwner categoryOwner) {
            this.categoryOwner = categoryOwner;
        }



        public Owner toOwner() {

            Owner owner = new Owner();

            owner.setBusinessName(businessName);
            owner.setCategoryOwner(categoryOwner);
            owner.setEmail(email);
            owner.setPathPicture(pathPicture);
            owner.setPersonType(personType);
            owner.setPhone(phone);
            owner.setProfileType(profileType);


            return owner;
        }

    }
}
