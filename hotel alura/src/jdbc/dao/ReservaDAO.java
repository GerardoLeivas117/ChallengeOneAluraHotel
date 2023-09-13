package jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import jdbc.modelo.Reserva;

public class ReservaDAO {

    private Connection connection;

    public ReservaDAO(Connection connection) {
        this.connection = connection;
    }

    public void guardar(Reserva reserva) {
        try {
            String sql = "INSERT INTO reservas (fecha_entrada, fecha_salida, valor, forma_pago) VALUES (?, ?, ?, ?)";

            try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstm.setDate(1, reserva.getFechaE());
                pstm.setDate(2, reserva.getFechaS());
                pstm.setString(3, reserva.getValor());
                pstm.setString(4, reserva.getFormaPago());

                pstm.executeUpdate();

                try (ResultSet rst = pstm.getGeneratedKeys()) {
                    while (rst.next()) {
                        reserva.setId(rst.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Reserva> buscar() {
        List<Reserva> reservas = new ArrayList<>();
        try {
            String sql = "SELECT id, fecha_entrada, fecha_salida, valor, forma_pago FROM reservas";

            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                try (ResultSet rst = pstm.executeQuery()) {
                    while (rst.next()) {
                        Reserva reserva = new Reserva(
                            rst.getInt("id"),
                            rst.getDate("fecha_entrada"),
                            rst.getDate("fecha_salida"),
                            rst.getString("valor"),
                            rst.getString("forma_pago")
                        );

                        reservas.add(reserva);
                    }
                }
            }
            return reservas;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Reserva> buscarPorId(Integer id) {
        List<Reserva> reservas = new ArrayList<>();
        try {
            String sql = "SELECT id, fecha_entrada, fecha_salida, valor, forma_pago FROM reservas WHERE id = ?";

            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.setInt(1, id);
                try (ResultSet rst = pstm.executeQuery()) {
                    while (rst.next()) {
                        Reserva reserva = new Reserva(
                            rst.getInt("id"),
                            rst.getDate("fecha_entrada"),
                            rst.getDate("fecha_salida"),
                            rst.getString("valor"),
                            rst.getString("forma_pago")
                        );

                        reservas.add(reserva);
                    }
                }
            }
            return reservas;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void eliminar(Integer id) {
        try (PreparedStatement stm = connection.prepareStatement("DELETE FROM reservas WHERE id = ?")) {
            stm.setInt(1, id);
            stm.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void actualizar(Reserva reserva) {
        try (PreparedStatement stm = connection.prepareStatement("UPDATE reservas SET fecha_entrada = ?, fecha_salida = ?, valor = ?, forma_pago = ? WHERE id = ?")) {
            stm.setDate(1, reserva.getFechaE());
            stm.setDate(2, reserva.getFechaS());
            stm.setString(3, reserva.getValor());
            stm.setString(4, reserva.getFormaPago());
            stm.setInt(5, reserva.getId());
            stm.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
