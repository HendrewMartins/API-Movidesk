package br.hendrew.movidesk.entity;

public class Category {

    private long customizacao;
    private long treinamentoOnline;
    private long duvida;
    private long implantacao;
    private long licitacao;
    private long solicitacaoServico;
    private long homologacao;
    private long falha;
    private long semCategoria;
    private long solicitacaoTreinamento;
    private long sugestao;
    
    public Category() {
    }

    public long getCustomizacao() {
        return customizacao;
    }
    public void setCustomizacao(long customizacao) {
        this.customizacao = customizacao;
    }
    public long getTreinamentoOnline() {
        return treinamentoOnline;
    }
    public void setTreinamentoOnline(long treinamentoOnline) {
        this.treinamentoOnline = treinamentoOnline;
    }
    public long getDuvida() {
        return duvida;
    }
    public void setDuvida(long duvida) {
        this.duvida = duvida;
    }
    public long getImplantacao() {
        return implantacao;
    }
    public void setImplantacao(long implantacao) {
        this.implantacao = implantacao;
    }
    public long getLicitacao() {
        return licitacao;
    }
    public void setLicitacao(long licitacao) {
        this.licitacao = licitacao;
    }
    public long getSolicitacaoServico() {
        return solicitacaoServico;
    }
    public void setSolicitacaoServico(long solicitacaoServico) {
        this.solicitacaoServico = solicitacaoServico;
    }
    public long getHomologacao() {
        return homologacao;
    }
    public void setHomologacao(long homologacao) {
        this.homologacao = homologacao;
    }
    public long getFalha() {
        return falha;
    }
    public void setFalha(long falha) {
        this.falha = falha;
    }
    public long getSemCategoria() {
        return semCategoria;
    }
    public void setSemCategoria(long semCategoria) {
        this.semCategoria = semCategoria;
    }
    public long getSolicitacaoTreinamento() {
        return solicitacaoTreinamento;
    }
    public void setSolicitacaoTreinamento(long solicitacaoTreinamento) {
        this.solicitacaoTreinamento = solicitacaoTreinamento;
    }
    public long getSugestao() {
        return sugestao;
    }
    public void setSugestao(long sugestao) {
        this.sugestao = sugestao;
    }
       

    @Override
    public String toString() {
        return "Category [customizacao=" + customizacao + ", duvida=" + duvida + ", falha=" + falha + ", homologacao="
                + homologacao + ", implantacao=" + implantacao + ", licitacao=" + licitacao + ", semCategoria="
                + semCategoria + ", solicitacaoServico=" + solicitacaoServico + ", solicitacaoTreinamento="
                + solicitacaoTreinamento + ", sugestao=" + sugestao + ", treinamentoOnline=" + treinamentoOnline + "]";
    }

}
