package jdbc.controller;

import java.sql.Connection;
import java.util.List;
import jdbc.dao.UsuarioDAO;
import jdbc.factory.ConnectionFactory;
import jdbc.modelo.Usuarios;

public class UsuarioController {
    private UsuarioDAO usuariosDAO;

    public UsuarioController() {
        Connection connection = new ConnectionFactory().recuperarConexion();
        this.usuariosDAO = new UsuarioDAO(connection);
    }

    public void guardarUsuario(Usuarios usuario) {
        this.usuariosDAO.guardar(usuario);
    }

    public Usuarios buscarUsuarioPorId(String id) {
        return this.usuariosDAO.buscarPorId(id);
    }


    public List<Usuarios> listarUsuarios() {
        return this.usuariosDAO.listarUsuarios();
    }

    public void actualizarUsuario(Usuarios usuario) {
        this.usuariosDAO.actualizar(usuario);
    }

    public void eliminarUsuario(String id) {
        this.usuariosDAO.eliminar(id);
    }
}
