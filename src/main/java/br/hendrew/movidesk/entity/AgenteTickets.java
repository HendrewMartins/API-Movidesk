package br.hendrew.movidesk.entity;


public class AgenteTickets {
    
    public String idAgente;
    public String businessName;
    public long quantTicketsInAttendance;
    public long quantTicketsNew;
    public long quantTicketsStopped;
    
    public AgenteTickets() {
    }



    public String getIdAgente() {
        return idAgente;
    }



    public void setIdAgente(String idAgente) {
        this.idAgente = idAgente;
    }



    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public long getQuantTicketsInAttendance() {
        return quantTicketsInAttendance;
    }

    public void setQuantTicketsInAttendance(long quantTicketsInAttendance) {
        this.quantTicketsInAttendance = quantTicketsInAttendance;
    }

    

    public long getQuantTicketsNew() {
        return quantTicketsNew;
    }

    public void setQuantTicketsNew(long quantTicketsNew) {
        this.quantTicketsNew = quantTicketsNew;
    }

    public long getQuantTicketsStopped() {
        return quantTicketsStopped;
    }

    public void setQuantTicketsStopped(long quantTicketsStopped) {
        this.quantTicketsStopped = quantTicketsStopped;
    }

    @Override
    public String toString() {
        return "AgenteTickets [businessName=" + businessName + ", idAgente=" + idAgente + ", quantTicketsInAttendance="
                + quantTicketsInAttendance + ", quantTicketsNew=" + quantTicketsNew + ", quantTicketsStopped="
                + quantTicketsStopped + "]";
    }

   
    
    
}
