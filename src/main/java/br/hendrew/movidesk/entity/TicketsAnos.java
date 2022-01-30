package br.hendrew.movidesk.entity;

public class TicketsAnos {

    private long ano;
    private long quantidade;

    public TicketsAnos() {
    }

    public long getAno() {
        return ano;
    }

    public void setAno(long ano) {
        this.ano = ano;
    }

    public long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(long quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "TicketsAnos [ano=" + ano + ", quantidade=" + quantidade + "]";
    }
    
}
