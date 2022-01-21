package br.hendrew.movidesk.exceptionhandler;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.hendrew.movidesk.exception.MenssageNotFoundException;

@Provider
public class ExceptionHandler implements ExceptionMapper<Exception> {

	@Override
	public Response toResponse(Exception exception) {
		if (exception instanceof MenssageNotFoundException) {
			return Response.status(Response.Status.NOT_FOUND).entity(new ErrorResponseBody(exception.getMessage()))
					.build();
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
				.entity(new ErrorResponseBody("Aconteceu algo inesperado. Tente novamente")).build();
	}

	public static final class ErrorResponseBody {

		private final String message;

		public ErrorResponseBody(String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}
	}

}
