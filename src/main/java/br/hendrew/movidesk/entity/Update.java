package br.hendrew.movidesk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "atualizacao")
public class Update {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "tipoAtualizacao", nullable = true)
    private long tipoAtualizacao; //(0=total, 1=parcial, 2=status)

    @Column(name = "horaInicioAtualizacao", nullable = true)
    private Time horaInicioAtualizacao;

    @Column(name = "dataInicioAtualizacao", nullable = true)
    private Date dataInicioAtualizacao;

    @Column(name = "horaFimAtualizacao", nullable = true)
    private Time horaFimAtualizacao;

    @Column(name = "dataFimAtualizacao", nullable = true)
    private Date dataFimAtualizacao;

    @Column(name = "errorAtualizacao", nullable = true)
    private String errorAtualizacao;

    public Update() {
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

    public Time getHoraInicioAtualizacao() {
        return horaInicioAtualizacao;
    }

    public void setHoraInicioAtualizacao(Time horaInicioAtualizacao) {
        this.horaInicioAtualizacao = horaInicioAtualizacao;
    }

    public Date getDataInicioAtualizacao() {
        return dataInicioAtualizacao;
    }

    public void setDataInicioAtualizacao(Date dataInicioAtualizacao) {
        this.dataInicioAtualizacao = dataInicioAtualizacao;
    }

    public Time getHoraFimAtualizacao() {
        return horaFimAtualizacao;
    }

    public void setHoraFimAtualizacao(Time horaFimAtualizacao) {
        this.horaFimAtualizacao = horaFimAtualizacao;
    }

    public Date getDataFimAtualizacao() {
        return dataFimAtualizacao;
    }

    public void setDataFimAtualizacao(Date dataFimAtualizacao) {
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
        return "Update [dataFimAtualizacao=" + dataFimAtualizacao + ", dataInicioAtualizacao=" + dataInicioAtualizacao
                + ", errorAtualizacao=" + errorAtualizacao + ", horaFimAtualizacao=" + horaFimAtualizacao
                + ", horaInicioAtualizacao=" + horaInicioAtualizacao + ", id=" + id + ", tipoAtualizacao="
                + tipoAtualizacao + "]";
    }

    
}
