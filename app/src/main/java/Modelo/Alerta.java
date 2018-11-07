package Modelo;

public class Alerta {
    private int idAlerta;
    private String lat;
    private String lon;
    private String status;
    private int idTipoAlerta;
    private int idUsuario;


    public int getIdAlerta() {
        return idAlerta;
    }

    public void setIdAlerta(int idAlerta) {
        this.idAlerta = idAlerta;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIdTipoAlerta() {
        return idTipoAlerta;
    }

    public void setIdTipoAlerta(int idTipoAlerta) {
        this.idTipoAlerta = idTipoAlerta;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
