package servicio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import util.conexion;

public class DaoReserva {

    // Listar todas las reservas
    public static List<Reserva> listar() {
        List<Reserva> lista = new ArrayList<>();
        
        try {
            Connection conn = conexion.getConnection();
            String sql = "SELECT * FROM reservas";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Reserva r = new Reserva();
                r.setId(rs.getInt("id"));
                r.setUsuarioId(rs.getInt("usuario_id"));
                r.setCodEsta(rs.getInt("codEsta"));
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

    // Actualizar estado de reserva y modificar estado de estacionamiento en consecuencia
    public static boolean actualizarEstadoReserva(int idReserva, String nuevoEstado) {
        try {
            Connection conn = conexion.getConnection();
            
            // 1. Actualizar estado de la reserva
            String sqlReserva = "UPDATE reservas SET estado = ? WHERE id = ?";
            PreparedStatement psReserva = conn.prepareStatement(sqlReserva);
            psReserva.setString(1, nuevoEstado);
            psReserva.setInt(2, idReserva);
            
            int filasAfectadas = psReserva.executeUpdate();
            
            if (filasAfectadas > 0) {
                
                // 2. Obtener codEsta de la reserva para actualizar estacionamiento
                String sqlCodEsta = "SELECT codEsta FROM reservas WHERE id = ?";
                PreparedStatement psCodEsta = conn.prepareStatement(sqlCodEsta);
                psCodEsta.setInt(1, idReserva);
                ResultSet rs = psCodEsta.executeQuery();
                
                if (rs.next()) {
                    int codEsta = rs.getInt("codEsta");
                    
                    // 3. Determinar el nuevo estado para el estacionamiento
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

    // Método auxiliar para determinar el estado correspondiente del estacionamiento
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
                return null; // Estado desconocido, no se actualiza el estacionamiento
        }
    }
}
