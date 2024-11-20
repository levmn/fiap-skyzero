package br.com.skyzero.resources;

import br.com.skyzero.model.bo.RegistroBO;
import br.com.skyzero.model.vo.Registro;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

@Path("/registro")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RegistroResource {
    private final RegistroBO registroBO;

    public RegistroResource() {
        this.registroBO = new RegistroBO();
    }

    @POST
    public Response criarRegistro(Registro registro) {
        try {
            Registro registroCadastrado = registroBO.cadastrarRegistro(registro);
            return Response.status(Response.Status.CREATED)
                    .entity(registroCadastrado)
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao criar o registro: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}/calcular")
    public Response atualizarEmissao(@PathParam("id") int id, Registro registro) {
        try {
            registroBO.calcularEmissao(id, registro.getTipoAviao(), registro.getDistancia());
            return Response.ok("Emissão calculada e atualizada.").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao atualizar o registro: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/usuario/{id}")
    public Response listarRegistroPorUsuario(@PathParam("id") int usuarioId) {
        try {
            List<Registro> registros = registroBO.listarRegistroPorUsuario(usuarioId);
            return Response.ok(registros).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar registros: " + e.getMessage())
                    .build();
        }
    }

    @GET
    public Response listarRegistros() {
        try {
            return Response.ok(registroBO.listarRegistros()).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar registros: " + e.getMessage())
                    .build();
        }
    }
}
