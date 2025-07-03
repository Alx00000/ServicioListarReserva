package servicio;

import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;

@WebService(serviceName = "ServicioReservas")
public class ServicioReservas {

    @WebMethod(operationName = "listarReservas")
    public List<Reserva> listarReservas() {
        return DaoReserva.listar();
    }

    @WebMethod(operationName = "cambiarEstadoReserva")
    public boolean cambiarEstadoReserva(int idReserva, String nuevoEstado) {
        return DaoReserva.actualizarEstadoReserva(idReserva, nuevoEstado);
    }
}
