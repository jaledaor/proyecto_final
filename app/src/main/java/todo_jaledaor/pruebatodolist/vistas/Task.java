package todo_jaledaor.pruebatodolist.vistas;

public class Task {
    private String pregunta;
    private String respuesta;
    private String categoria;
    private String fecha;
    private boolean respondida;

    public Task() {
    }

    public Task(String pregunta, String categoria, String respuesta, String fecha, boolean respondida) {
        this.pregunta = pregunta;
        this.categoria = categoria;
        this.respuesta = respuesta;
        this.fecha = fecha;
        this.respondida = respondida;
    }


    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean isRespondida() {
        return respondida;
    }

    public void setRespondida(boolean respondida) {
        this.respondida = respondida;
    }
}