package servicio;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Reserva {

    private int id;
    private String nombreUsuario;
    private String apellidoUsuario;
    private String numeroEstacionamiento;
    private String fecha;
    private String horaInicio;
    private String horaFin;
    private String estado;

    public Reserva() {}

    @XmlElement
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @XmlElement
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    @XmlElement
    public String getApellidoUsuario() {
        return apellidoUsuario;
    }
    public void setApellidoUsuario(String apellidoUsuario) {
        this.apellidoUsuario = apellidoUsuario;
    }

    @XmlElement
    public String getNumeroEstacionamiento() {
        return numeroEstacionamiento;
    }
    public void setNumeroEstacionamiento(String numeroEstacionamiento) {
        this.numeroEstacionamiento = numeroEstacionamiento;
    }

    @XmlElement
    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @XmlElement
    public String getHoraInicio() {
        return horaInicio;
    }
    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    @XmlElement
    public String getHoraFin() {
        return horaFin;
    }
    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    @XmlElement
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
}
