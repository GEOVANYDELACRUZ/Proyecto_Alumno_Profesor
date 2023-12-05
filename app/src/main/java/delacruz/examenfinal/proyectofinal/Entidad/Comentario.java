package delacruz.examenfinal.proyectofinal.Entidad;

public class Comentario {
    private String curNombre;
    private String IDAlumC;
    private String Alumno;
    private String cursNota;
    private String cursCriterio;
    private String cursNComment;
    private String cursNRespuesta;
    private String IDCursNota;

    public Comentario() {
    }

    public String getIDCursNota() {
        return IDCursNota;
    }

    public void setIDCursNota(String IDCursNota) {
        this.IDCursNota = IDCursNota;
    }

    public String getCurNombre() {
        return curNombre;
    }

    public void setCurNombre(String curNombre) {
        this.curNombre = curNombre;
    }

    public String getIDAlumC() {
        return IDAlumC;
    }

    public void setIDAlumC(String IDAlumC) {
        this.IDAlumC = IDAlumC;
    }

    public String getAlumno() {
        return Alumno;
    }

    public void setAlumno(String alumno) {
        Alumno = alumno;
    }

    public String getCursNota() {
        return cursNota;
    }

    public void setCursNota(String cursNota) {
        this.cursNota = cursNota;
    }

    public String getCursCriterio() {
        return cursCriterio;
    }

    public void setCursCriterio(String cursCriterio) {
        this.cursCriterio = cursCriterio;
    }

    public String getCursNComment() {
        return cursNComment;
    }

    public void setCursNComment(String cursNComment) {
        this.cursNComment = cursNComment;
    }

    public String getCursNRespuesta() {
        return cursNRespuesta;
    }

    public void setCursNRespuesta(String cursNRespuesta) {
        this.cursNRespuesta = cursNRespuesta;
    }
}
