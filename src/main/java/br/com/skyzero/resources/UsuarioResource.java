package br.com.skyzero.resources;

import br.com.skyzero.model.bo.UsuarioBO;
import br.com.skyzero.model.vo.Usuario;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;

@Path("/usuario")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {
    private final UsuarioBO usuarioBO = new UsuarioBO();

    @POST
    public Response cadastrarUsuario(Usuario usuario) {
        try {
            String resultado = usuarioBO.cadastrarUsuario(usuario);
            return Response.status(Response.Status.CREATED)
                    .entity(resultado)
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao salvar o usuário no banco de dados: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response buscarUsuario(@PathParam("id") int id) {
        try {
            Usuario usuario = usuarioBO.buscarUsuario(id);
            if (usuario != null) {
                return Response.ok(usuario).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Usuário não encontrado.")
                        .build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar o usuário no banco de dados: " + e.getMessage())
                    .build();
        }
    }
}
