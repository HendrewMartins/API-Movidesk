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
import br.hendrew.movidesk.exception.MenssageNotFoundException;
import br.hendrew.movidesk.exceptionhandler.ExceptionHandler;
import br.hendrew.movidesk.services.CategoryOwnerService;

@RequestScoped
@Path("api/controllercategory")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryOwnerController {

    private final CategoryOwnerService categoryOwnerService;

    @Inject
    public CategoryOwnerController(CategoryOwnerService categoryOwnerService) {
        this.categoryOwnerService = categoryOwnerService;
    }

    @GET
    @PermitAll
    @Operation(summary = "Listar Category Owner", description = "Lista todos Category Owner")
    @APIResponses(value = @APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryOwner.class))))
    public List<CategoryOwner> getCategoryOwner() {
        return categoryOwnerService.getAllCategoryOwner();  
    }

    @GET
    @PermitAll
    @Path("/count")
    @Operation(summary = "Listar Category Owner", description = "Lista todos Category Owner")
    @APIResponses(value = @APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class))))
    public long getCountCategoryOwner() {
        return categoryOwnerService.countCategoryOwner();  
    }

    @GET
	@PermitAll
	@Path("/id/{id}")
	@Operation(summary = "Pegar Category Owner", description = "Pesquisa por um ID o Category Owner")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryOwner.class))),
			@APIResponse(responseCode = "404", description = "Alunos not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionHandler.ErrorResponseBody.class))) })
	public CategoryOwner getCategoryOwner(@PathParam("id") int id) throws MenssageNotFoundException {
		return categoryOwnerService.getCategoryOwnerById(id);
	}

    @GET
	@PermitAll
	@Path("/page/{page}")
	@Operation(summary = "Pegar Category Owner", description = "Pesquisa por um ID o Category Owner")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryOwner.class))),
			@APIResponse(responseCode = "404", description = "Category Owner not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionHandler.ErrorResponseBody.class))) })
	public List<CategoryOwner> getPageCategoryOwner(@PathParam("page") int page) throws MenssageNotFoundException {
		return categoryOwnerService.getCategoryOwnerPage(page,10);
	}


    @POST
	@PermitAll
	@Path("/save")
	@Operation(summary = "Adicionar Category Owner", description = "Criar um novo Category Owner e persistir no banco")
	@APIResponses(value = @APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryOwner.class))))
	public CategoryOwner createCategoryOwner(@Valid CategoryOwnerDTO categoryDTO) {
		return categoryOwnerService.saveCategoryOwner(categoryDTO.toCategoryOwner());

	}

    @PUT
	@PermitAll
	@Path("/edit/{id}")
	@Operation(summary = "Atualizar um Category Owner", description = "Atualizar um Category Owner existente via id")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryOwner.class))),
			@APIResponse(responseCode = "404", description = "Category Owner not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionHandler.ErrorResponseBody.class))) })
	public CategoryOwner updateCategoryOwner(@PathParam("id") int id, @Valid CategoryOwnerDTO categoryDTO)
			throws MenssageNotFoundException {
		return categoryOwnerService.updateCategoryOwner(id, categoryDTO.toCategoryOwner());
	}

	@DELETE
	@PermitAll
	@Path("/delete/{id}")
	@Operation(summary = "Apagar a Category Owner", description = "Apagar um Category Owner pelo ID")
	@APIResponses(value = { @APIResponse(responseCode = "204", description = "Success"),
			@APIResponse(responseCode = "404", description = "Category Owner not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionHandler.ErrorResponseBody.class))) })
	public boolean deleteCategoryOwner(@PathParam("id") int id) throws MenssageNotFoundException {
		categoryOwnerService.deleteCategoryOwner(id);
		return true;
	}

    @GET
	@PermitAll
	@Path("/desc/{desc}")
	@Operation(summary = "Pegar Categoria", description = "Pesquisa por Categoria do Owner")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryOwner.class))),
			@APIResponse(responseCode = "404", description = "Category Owner not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionHandler.ErrorResponseBody.class))) })
	public List<CategoryOwner> getAlunoData(@PathParam("desc") String desc) throws MenssageNotFoundException {
		return categoryOwnerService.getCategoryOwnerByDesCategory(desc);
		
	}

    @Schema(name = "CategoryOwnerDTO", description = "DTO para Criar um novo CategoryOwner")
	public static class CategoryOwnerDTO {

		@Schema(title = "desCategoria", required = true)
		private String desCategoria;


		public String getDesCategoria() {
            return desCategoria;
        }


        public void setDesCategoria(String desCategoria) {
            this.desCategoria = desCategoria;
        }

        public CategoryOwner toCategoryOwner() {
			

			CategoryOwner categoryOwner = new CategoryOwner();

			categoryOwner.setDesCategoria(desCategoria);

			return categoryOwner;
		}

    }
}
