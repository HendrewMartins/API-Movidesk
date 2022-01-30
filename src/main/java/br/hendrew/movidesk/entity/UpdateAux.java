package br.hendrew.movidesk.entity;

public class UpdateAux {
    
    private long id;
    private long tipoAtualizacao; //(0=total, 1=parcial, 2=status)
    private String horaInicioAtualizacao;
    private String dataInicioAtualizacao;
    private String horaFimAtualizacao;
    private String dataFimAtualizacao;
    private String errorAtualizacao;

    public UpdateAux() {
    }

    public long getId() {
        return id;
    }



    public void setId(long id) {
        this.id = id;
    }



    public long getTipoAtualizacao() {
        return tipoAtualizacao;
    }



    public void setTipoAtualizacao(long tipoAtualizacao) {
        this.tipoAtualizacao = tipoAtualizacao;
    }



    public String getHoraInicioAtualizacao() {
        return horaInicioAtualizacao;
    }



    public void setHoraInicioAtualizacao(String horaInicioAtualizacao) {
        this.horaInicioAtualizacao = horaInicioAtualizacao;
    }



    public String getDataInicioAtualizacao() {
        return dataInicioAtualizacao;
    }



    public void setDataInicioAtualizacao(String dataInicioAtualizacao) {
        this.dataInicioAtualizacao = dataInicioAtualizacao;
    }



    public String getHoraFimAtualizacao() {
        return horaFimAtualizacao;
    }



    public void setHoraFimAtualizacao(String horaFimAtualizacao) {
        this.horaFimAtualizacao = horaFimAtualizacao;
    }



    public String getDataFimAtualizacao() {
        return dataFimAtualizacao;
    }



    public void setDataFimAtualizacao(String dataFimAtualizacao) {
        this.dataFimAtualizacao = dataFimAtualizacao;
    }



    public String getErrorAtualizacao() {
        return errorAtualizacao;
    }



    public void setErrorAtualizacao(String errorAtualizacao) {
        this.errorAtualizacao = errorAtualizacao;
    }



    @Override
    public String toString() {
        return "UpdateAux [dataFimAtualizacao=" + dataFimAtualizacao + ", dataInicioAtualizacao="
                + dataInicioAtualizacao + ", errorAtualizacao=" + errorAtualizacao + ", horaFimAtualizacao="
                + horaFimAtualizacao + ", horaInicioAtualizacao=" + horaInicioAtualizacao + ", id=" + id
                + ", tipoAtualizacao=" + tipoAtualizacao + "]";
    }
    
}
