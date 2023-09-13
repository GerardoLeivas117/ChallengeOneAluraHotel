package jdbc.controller;

import java.sql.Connection;
import java.sql.Date;
import java.util.Collections;
import java.util.List;

import jdbc.dao.ReservaDAO;
import jdbc.factory.ConnectionFactory;
import jdbc.modelo.Reserva;

public class ReservasController {
    private ReservaDAO reservaDAO;

    public ReservasController() {
        Connection connection = new ConnectionFactory().recuperarConexion();
        this.reservaDAO = new ReservaDAO(connection);
    }

    public void guardar(Reserva reserva) {
        this.reservaDAO.guardar(reserva);
    }

    public List<Reserva> buscar() {
        return this.reservaDAO.buscar();
    }

    public List<Reserva> buscarId(String id) {
        try {        	
            int idNumero = Integer.parseInt(id);
            return this.reservaDAO.buscarPorId(idNumero);
        } catch (NumberFormatException e) {
            return Collections.emptyList(); // Devolver una lista vacía en caso de error.
        }
    }

    public void actualizar(Date fechaE, Date fechaS, String valor, String formaPago, Integer id) {
        this.reservaDAO.actualizar(new Reserva(id, fechaE, fechaS, valor, formaPago));
    }

    public void eliminar(Integer id) {
        this.reservaDAO.eliminar(id);
    }
}
