package br.hendrew.movidesk.entity;

public class TicketsMesesDias {
    private String mesesdia;
    private long quantidade;
    private Category category;
    
    public TicketsMesesDias() {
    }

    public String getMesesDia() {
        return mesesdia;
    }

    public void setMesesDia(String mesesdia) {
        this.mesesdia = mesesdia;
    }

    public long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(long quantidade) {
        this.quantidade = quantidade;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "TicketsMeses [category=" + category + ", mesesdia=" + mesesdia + ", quantidade=" + quantidade + "]";
    }

    
}
