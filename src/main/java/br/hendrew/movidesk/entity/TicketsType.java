package br.hendrew.movidesk.entity;

public class TicketsType {

    private long interno;
    private long externo;

    public TicketsType() {
    }

    public long getInterno() {
        return interno;
    }

    public void setInterno(long interno) {
        this.interno = interno;
    }

    public long getExterno() {
        return externo;
    }

    public void setExterno(long externo) {
        this.externo = externo;
    }

    @Override
    public String toString() {
        return "TicketsType [externo=" + externo + ", interno=" + interno + "]";
    } 
    
}
