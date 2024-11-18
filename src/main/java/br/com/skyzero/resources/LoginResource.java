package br.com.skyzero.resources;

import br.com.skyzero.model.bo.LoginBO;
import br.com.skyzero.model.vo.Login;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;

@Path("/login")
public class LoginResource {
    private LoginBO loginBO;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Login credentials) {
        try {
            Login usuarioAutenticado = loginBO.autenticar(credentials.getCnpj(), credentials.getSenha());
            return Response.ok(usuarioAutenticado).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("CNPJ ou senha inválidos.").build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao autenticar usuário: " + e.getMessage()).build();
        }
    }
}
