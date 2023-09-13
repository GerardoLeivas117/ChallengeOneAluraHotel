package jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import jdbc.modelo.Usuarios;

public class UsuarioDAO {
    private Connection connection;

    public UsuarioDAO(Connection connection) {
        this.connection = connection;
    }

    public void guardar(Usuarios usuario) {
        try {
            String sql = "INSERT INTO credential_user (id, password) VALUES (?, ?)";

            try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstm.setString(1, usuario.getId());
                pstm.setString(2, usuario.getPassword());

                pstm.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar el usuario en la base de datos.", e);
        }
    }

    public Usuarios buscarPorId(String id) {
        try {
            String sql = "SELECT id, password FROM credential_user WHERE id = ?";
            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.setString(1, id);
                try (ResultSet rst = pstm.executeQuery()) {
                    if (rst.next()) {
                        return new Usuarios(rst.getString("id"), rst.getString("password"));
                    } else {
                        return null; // El usuario no se encontró
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar el usuario por ID en la base de datos.", e);
        }
    }


    public List<Usuarios> listarUsuarios() {
        List<Usuarios> usuarios = new ArrayList<>();
        try {
            String sql = "SELECT id, password FROM credential_user";
            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                try (ResultSet rst = pstm.executeQuery()) {
                    while (rst.next()) {
                        Usuarios usuario = new Usuarios(rst.getString("id"), rst.getString("password"));
                        usuarios.add(usuario);
                    }
                }
            }
            return usuarios;
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar usuarios en la base de datos.", e);
        }
    }

    public void actualizar(Usuarios usuario) {
        try {
            String sql = "UPDATE credential_user SET password = ? WHERE id = ?";
            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.setString(1, usuario.getPassword());
                pstm.setString(2, usuario.getId());
                pstm.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el usuario en la base de datos.", e);
        }
    }

    public void eliminar(String id) {
        try {
            String sql = "DELETE FROM credential_user WHERE id = ?";
            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.setString(1, id);
                pstm.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el usuario de la base de datos.", e);
        }
    }
}

