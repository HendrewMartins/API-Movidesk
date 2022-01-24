package br.hendrew.movidesk.entity;

public class TicketsUrgency {

    private long baixa;
    private long media;
    private long alta;
    private long urgente;
    private long nulo;

    public TicketsUrgency() {
    }

    public long getBaixa() {
        return baixa;
    }

    public void setBaixa(long baixa) {
        this.baixa = baixa;
    }

    public long getMedia() {
        return media;
    }

    public void setMedia(long media) {
        this.media = media;
    }

    public long getAlta() {
        return alta;
    }

    public void setAlta(long alta) {
        this.alta = alta;
    }

    public long getUrgente() {
        return urgente;
    }

    public void setUrgente(long urgente) {
        this.urgente = urgente;
    }

    public long getNulo() {
        return nulo;
    }

    public void setNulo(long nulo) {
        this.nulo = nulo;
    }

    @Override
    public String toString() {
        return "TicketsUrgency [alta=" + alta + ", baixa=" + baixa + ", media=" + media + ", nulo=" + nulo
                + ", urgente=" + urgente + "]";
    } 
    
}
