package br.hendrew.movidesk.entity;


public class AgenteTickets {
    
    private String idAgente;
    private String businessName;
    private long quantTicketsInAttendance;
    private long quantTicketsNew;
    private long quantTicketsStopped;
    private long quantTicketsCanceled;
    private long quantTicketsResolved;
    private long quantTicketsClosed;

    public AgenteTickets() {
    }
    
    public long getQuantTicketsCanceled() {
        return quantTicketsCanceled;
    }



    public void setQuantTicketsCanceled(long quantTicketsCanceled) {
        this.quantTicketsCanceled = quantTicketsCanceled;
    }



    public long getQuantTicketsResolved() {
        return quantTicketsResolved;
    }



    public void setQuantTicketsResolved(long quantTicketsResolved) {
        this.quantTicketsResolved = quantTicketsResolved;
    }



    public long getQuantTicketsClosed() {
        return quantTicketsClosed;
    }



    public void setQuantTicketsClosed(long quantTicketsClosed) {
        this.quantTicketsClosed = quantTicketsClosed;
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
        return "AgenteTickets [businessName=" + businessName + ", idAgente=" + idAgente + ", quantTicketsCanceled="
                + quantTicketsCanceled + ", quantTicketsClosed=" + quantTicketsClosed + ", quantTicketsInAttendance="
                + quantTicketsInAttendance + ", quantTicketsNew=" + quantTicketsNew + ", quantTicketsResolved="
                + quantTicketsResolved + ", quantTicketsStopped=" + quantTicketsStopped + "]";
    }

   
    
    
}
