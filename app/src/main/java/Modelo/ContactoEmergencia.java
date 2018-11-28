package Modelo;

public class ContactoEmergencia {
    private int idContactoEmergencia;
    private String nombre;
    private String telefono;
    private String email;
    private int idUsuario;
    private int prioridad;


    public int getIdContactoEmergencia() {
        return idContactoEmergencia;
    }

    public void setIdContactoEmergencia(int idContactoEmergencia) {
        this.idContactoEmergencia = idContactoEmergencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
