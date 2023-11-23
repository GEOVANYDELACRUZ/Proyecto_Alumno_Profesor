package delacruz.examenfinal.alumnoapp.Entidades;

public class Notas {
    private String codigo;
    private String curso;
    private String subcodigo;
    private String criterio;
    private String valor;
    private String descripcion;
    private String cursNComment;
    private String cursNRespuesta;

    public Notas() {
    }

    public Notas(String codigo, String curso, String subcodigo, String criterio, String valor, String descripcion, String cursNComment, String cursNRespuesta) {
        this.codigo = codigo;
        this.curso = curso;
        this.subcodigo = subcodigo;
        this.criterio = criterio;
        this.valor = valor;
        this.descripcion = descripcion;
        this.cursNComment = cursNComment;
        this.cursNRespuesta = cursNRespuesta;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getSubcodigo() {
        return subcodigo;
    }

    public void setSubcodigo(String subcodigo) {
        this.subcodigo = subcodigo;
    }

    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return curso;
    }
}
