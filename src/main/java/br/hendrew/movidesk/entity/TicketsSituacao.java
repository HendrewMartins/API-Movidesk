package br.hendrew.movidesk.entity;

public class TicketsSituacao {
    
    private long inAttendance;
    private long newReg;
    private long stopped;
    private long resolved;
    private long canceled;
    private long closed;

    public TicketsSituacao() {
    }

    public long getInAttendance() {
        return inAttendance;
    }

    public void setInAttendance(long inAttendance) {
        this.inAttendance = inAttendance;
    }

    public long getNewReg() {
        return newReg;
    }

    public void setNewReg(long newReg) {
        this.newReg = newReg;
    }

    public long getStopped() {
        return stopped;
    }

    public void setStopped(long stopped) {
        this.stopped = stopped;
    }

    public long getResolved() {
        return resolved;
    }

    public void setResolved(long resolved) {
        this.resolved = resolved;
    }

    public long getCanceled() {
        return canceled;
    }

    public void setCanceled(long canceled) {
        this.canceled = canceled;
    }

    public long getClosed() {
        return closed;
    }

    public void setClosed(long closed) {
        this.closed = closed;
    }

    @Override
    public String toString() {
        return "TicketsSituacao [canceled=" + canceled + ", closed=" + closed + ", inAttendance=" + inAttendance
                + ", newreg=" + newReg + ", resolved=" + resolved + ", stopped=" + stopped + "]";
    }
    
}
