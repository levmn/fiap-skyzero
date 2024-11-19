package br.com.skyzero.model.bo;

import br.com.skyzero.model.dao.RegistroDAO;
import br.com.skyzero.model.vo.Registro;
import br.com.skyzero.service.CarbonApiService;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class RegistroBO {
    private final RegistroDAO registroDAO;
    private final CarbonApiService carbonApiService;

    public RegistroBO() {
        this.registroDAO = new RegistroDAO();
        this.carbonApiService = new CarbonApiService();
    }

    public int cadastrarRegistro(Registro registro) throws SQLException {
        validarRegistro(registro);
        registro.setDataRegistro(new Date());
        return registroDAO.inserir(registro);
    }

    public void calcularEmissao(int idRegistro, String tipoAviao, double distancia) throws SQLException {
        if (distancia <= 0) {
            throw new IllegalArgumentException("A distância deve ser maior que zero.");
        }

        double emissaoCalculada = carbonApiService.calcularEmissao(tipoAviao, distancia);

        registroDAO.atualizar(idRegistro, emissaoCalculada);
    }

    public List<Registro> listarRegistros() throws SQLException {
        return registroDAO.listar();
    }

    private void validarRegistro(Registro registro) {
        if (registro.getUsuario() == null || registro.getUsuario().getId() <= 0) {
            throw new IllegalArgumentException("Usuário inválido.");
        }
        if (!"PassengerAirplane".equals(registro.getTipoAviao()) && !"CargoAirplane".equals(registro.getTipoAviao())) {
            throw new IllegalArgumentException("Tipo de avião inválido.");
        }
    }
}
