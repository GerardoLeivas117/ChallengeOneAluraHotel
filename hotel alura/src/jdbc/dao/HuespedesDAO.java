package jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jdbc.modelo.Huespedes;

public class HuespedesDAO {
	private Connection connection;

	public HuespedesDAO(Connection connection) {
		this.connection = connection;
	}

	public void guardar(Huespedes huesped) {
		try {
			String sql = "INSERT INTO huespedes (nombre, apellido, fecha_nacimiento, nacionalidad, telefono, id_reserva) VALUES (?, ?, ?, ?, ?, ?)";

			try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				pstm.setString(1, huesped.getNombre());
				pstm.setString(2, huesped.getApellido());
				pstm.setDate(3, huesped.getFechaNacimiento());
				pstm.setString(4, huesped.getNacionalidad());
				pstm.setString(5, huesped.getTelefono());
				pstm.setInt(6, huesped.getIdReserva());

				pstm.execute();

				try (ResultSet rst = pstm.getGeneratedKeys()) {
					while (rst.next()) {
						huesped.setId(rst.getInt(1));
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Huespedes> listarHuespedes() {
		List<Huespedes> huespedes = new ArrayList<>();
		try {
			String sql = "SELECT id, nombre, apellido, fecha_nacimiento, nacionalidad, telefono, id_reserva FROM huespedes";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				try (ResultSet rst = pstm.executeQuery()) {
					while (rst.next()) {
						Huespedes huesped = new Huespedes(rst.getInt("id"), rst.getString("nombre"),
								rst.getString("apellido"), rst.getDate("fecha_nacimiento"),
								rst.getString("nacionalidad"), rst.getString("telefono"), rst.getInt("id_reserva"));

						huespedes.add(huesped);
					}
				}
			}
			return huespedes;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Huespedes> buscarHuespedesPorApellido(String apellido) {
		List<Huespedes> huespedes = new ArrayList<>();
		try {
			String sql = "SELECT id, nombre, apellido, fecha_nacimiento, nacionalidad, telefono, id_reserva FROM huespedes WHERE apellido = ?";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setString(1, apellido);
				try (ResultSet rst = pstm.executeQuery()) {
					while (rst.next()) {
						Huespedes huesped = new Huespedes(rst.getInt("id"), rst.getString("nombre"),
								rst.getString("apellido"), rst.getDate("fecha_nacimiento"),
								rst.getString("nacionalidad"), rst.getString("telefono"), rst.getInt("id_reserva"));

						huespedes.add(huesped);
					}
				}
			}
			return huespedes;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Huespedes> buscarPorIdReserva(Integer idReserva) {
		List<Huespedes> huespedes = new ArrayList<>();
		try {
			String sql = "SELECT id, nombre, apellido, fecha_nacimiento, nacionalidad, telefono, id_reserva FROM huespedes WHERE id_reserva = ?";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setInt(1, idReserva);
				try (ResultSet rst = pstm.executeQuery()) {
					while (rst.next()) {
						Huespedes huesped = new Huespedes(rst.getInt("id"), rst.getString("nombre"),
								rst.getString("apellido"), rst.getDate("fecha_nacimiento"),
								rst.getString("nacionalidad"), rst.getString("telefono"), rst.getInt("id_reserva"));

						huespedes.add(huesped);
					}
				}
			}
			return huespedes;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void actualizar(Huespedes huesped) {
		try (PreparedStatement stm = connection.prepareStatement(
				"UPDATE huespedes SET nombre = ?, apellido = ?, fecha_nacimiento = ?, nacionalidad = ?, telefono = ?, id_reserva = ? WHERE id = ?")) {
			stm.setString(1, huesped.getNombre());
			stm.setString(2, huesped.getApellido());
			stm.setDate(3, huesped.getFechaNacimiento());
			stm.setString(4, huesped.getNacionalidad());
			stm.setString(5, huesped.getTelefono());
			stm.setInt(6, huesped.getIdReserva());
			stm.setInt(7, huesped.getId());
			stm.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void eliminar(Integer id) {
		try (PreparedStatement stm = connection.prepareStatement("DELETE FROM huespedes WHERE id = ?")) {
			stm.setInt(1, id);
			stm.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
