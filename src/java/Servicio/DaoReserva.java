package servicio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import util.conexion;

public class DaoReserva {

    public static List<Reserva> listar() {
        List<Reserva> lista = new ArrayList<>();

        try {
            Connection conn = conexion.getConnection();
            String sql = "SELECT r.id, u.nombre, u.apellido, e.numero, r.fecha, r.hora_inicio, r.hora_fin, r.estado " +
                         "FROM reservas r " +
                         "JOIN usuarios u ON r.usuario_id = u.id " +
                         "JOIN estacionamiento e ON r.codEsta = e.codEsta";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Reserva r = new Reserva();
                r.setId(rs.getInt("id"));
                r.setNombreUsuario(rs.getString("nombre"));
                r.setApellidoUsuario(rs.getString("apellido"));
                r.setNumeroEstacionamiento(rs.getString("numero"));
                r.setFecha(rs.getString("fecha"));
                r.setHoraInicio(rs.getString("hora_inicio"));
                r.setHoraFin(rs.getString("hora_fin"));
                r.setEstado(rs.getString("estado"));
                lista.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public static boolean actualizarEstadoReserva(int idReserva, String nuevoEstado) {
        try {
            Connection conn = conexion.getConnection();

            String sqlReserva = "UPDATE reservas SET estado = ? WHERE id = ?";
            PreparedStatement psReserva = conn.prepareStatement(sqlReserva);
            psReserva.setString(1, nuevoEstado);
            psReserva.setInt(2, idReserva);

            int filasAfectadas = psReserva.executeUpdate();

            if (filasAfectadas > 0) {
                String sqlCodEsta = "SELECT codEsta FROM reservas WHERE id = ?";
                PreparedStatement psCodEsta = conn.prepareStatement(sqlCodEsta);
                psCodEsta.setInt(1, idReserva);
                ResultSet rs = psCodEsta.executeQuery();

                if (rs.next()) {
                    int codEsta = rs.getInt("codEsta");

                    String nuevoEstadoEstacionamiento = determinarEstadoEstacionamiento(nuevoEstado);
                    if (nuevoEstadoEstacionamiento != null) {
                        String sqlEstacionamiento = "UPDATE estacionamiento SET estado = ? WHERE codEsta = ?";
                        PreparedStatement psEsta = conn.prepareStatement(sqlEstacionamiento);
                        psEsta.setString(1, nuevoEstadoEstacionamiento);
                        psEsta.setInt(2, codEsta);
                        psEsta.executeUpdate();
                    }
                }

                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private static String determinarEstadoEstacionamiento(String estadoReserva) {
        switch (estadoReserva.toLowerCase()) {
            case "reservada":
                return "reservado";
            case "asistio":
                return "ocupado";
            case "cancelada":
            case "no_asistio":
            case "culmino_tiempo":
                return "disponible";
            default:
                return null;
        }
    }
}
