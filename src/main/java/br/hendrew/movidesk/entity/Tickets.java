package br.hendrew.movidesk.entity;



import java.sql.Time;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tickets")
public class Tickets {

    @Id
	//@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    
    @Column(name = "baseStatus", nullable = true)
    private String baseStatus;

    @Column(name = "status", nullable = true)
    private String status;

    @Column(name = "urgency", nullable = true)
    private String urgency;

    @Column(name = "category", nullable = true)
    private String category;

    @Column(name = "createdDate", nullable = true)
    private String createdDate;

    @Column(name = "subject", nullable = true)
    private String subject;

    @Column(name = "type", nullable = true, columnDefinition = "int default 0")
    private long type;
    
    @ManyToOne
    @JoinColumn(name = "owner", nullable = true)
    private Owner owner;

    @ManyToOne
    @JoinColumn(name = "clients", nullable = true)
    private Clients clients;

    @ManyToOne
    @JoinColumn(name = "customClients", nullable = true)
    private CustomClients customClients;

    @Column(name = "slaResponseTime", nullable = true, columnDefinition = "int default 0")
    private long slaResponseTime;

    @Column(name = "slaSolutionTime", nullable = true, columnDefinition = "int default 0")
    private long slaSolutionTime;

    @Column(name = "slaAgreementRule", nullable = true)
    private String slaAgreementRule;

    @Column(name = "slaAgreement", nullable = true)
    private String slaAgreement;

    @Column(name = "chatWaitingTime", nullable = true, columnDefinition = "int default 0")
    private long chatWaitingTime;

    @Column(name = "chatTalkTime", nullable = true, columnDefinition = "int default 0")
    private long chatTalkTime;

    @Column(name = "resolvedInFirstCall", nullable = true)
    private boolean resolvedInFirstCall;
    
    @Column(name = "stoppedTimeWorkingTime", nullable = true, columnDefinition = "int default 0")
    private long stoppedTimeWorkingTime;

    @Column(name = "stoppedTime", nullable = true, columnDefinition = "int default 0")
    private long stoppedTime;
    
    @Column(name = "lifeTimeWorkingTime", nullable = true, columnDefinition = "int default 0")
    private long lifeTimeWorkingTime;
    
    @Column(name = "lastUpdate", nullable = true)
    private String lastUpdate;
    
    @Column(name = "actionCount", nullable = true,  columnDefinition = "int default 0")
    private long actionCount;
    
    @Column(name = "lastActionDate", nullable = true)
    private String lastActionDate;
    
    @Column(name = "closedIn", nullable = true)
    private String closedIn;
    
    @Column(name = "reopenedIn", nullable = true)
    private String reopenedIn;
    
    @Column(name = "resolvedIn", nullable = true)
    private String resolvedIn;
    
    @Column(name = "ownerTeam", nullable = true)
    private String ownerTeam;
    
    @Column(name = "originEmailAccount", nullable = true)
    private String originEmailAccount;
    
    @Column(name = "origin", nullable = true, columnDefinition = "int default 0")
    private long origin;
    
    @Column(name = "justification", nullable = true)
    private String justification;

    @Column(name = "protocol", nullable = true)
    private String protocol;
        
    @Column(name = "dataTicket", nullable = true)
    private Date dataTicket;

    @Column(name = "horaTicket", nullable = true)
    private Time horaTicket;


    
    public Tickets() {
    }

    public String getBaseStatus() {
        return baseStatus;
    }
    public void setBaseStatus(String baseStatus) {
        this.baseStatus = baseStatus;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getUrgency() {
        return urgency;
    }
    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public long getType() {
        return type;
    }
    public void setType(long type) {
        this.type = type;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public Owner getOwner() {
        return owner;
    }
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    
    public Date getDataTicket() {
        return dataTicket;
    }

    public void setDataTicket(Date dataTicket) {
        this.dataTicket = dataTicket;
    }


    public long getSlaResponseTime() {
        return slaResponseTime;
    }

    public void setSlaResponseTime(long slaResponseTime) {
        this.slaResponseTime = slaResponseTime;
    }

    public long getSlaSolutionTime() {
        return slaSolutionTime;
    }

    public void setSlaSolutionTime(long slaSolutionTime) {
        this.slaSolutionTime = slaSolutionTime;
    }

    public String getSlaAgreementRule() {
        return slaAgreementRule;
    }

    public void setSlaAgreementRule(String slaAgreementRule) {
        this.slaAgreementRule = slaAgreementRule;
    }

    public String getSlaAgreement() {
        return slaAgreement;
    }

    public void setSlaAgreement(String slaAgreement) {
        this.slaAgreement = slaAgreement;
    }

    public long getChatWaitingTime() {
        return chatWaitingTime;
    }

    public void setChatWaitingTime(long chatWaitingTime) {
        this.chatWaitingTime = chatWaitingTime;
    }

    public long getChatTalkTime() {
        return chatTalkTime;
    }

    public void setChatTalkTime(long chatTalkTime) {
        this.chatTalkTime = chatTalkTime;
    }

    public boolean getResolvedInFirstCall() {
        return resolvedInFirstCall;
    }

    public void setResolvedInFirstCall(boolean resolvedInFirstCall) {
        this.resolvedInFirstCall = resolvedInFirstCall;
    }

    public long getStoppedTimeWorkingTime() {
        return stoppedTimeWorkingTime;
    }

    public void setStoppedTimeWorkingTime(long stoppedTimeWorkingTime) {
        this.stoppedTimeWorkingTime = stoppedTimeWorkingTime;
    }

    public long getStoppedTime() {
        return stoppedTime;
    }

    public void setStoppedTime(long stoppedTime) {
        this.stoppedTime = stoppedTime;
    }

    public long getLifeTimeWorkingTime() {
        return lifeTimeWorkingTime;
    }

    public void setLifeTimeWorkingTime(long lifeTimeWorkingTime) {
        this.lifeTimeWorkingTime = lifeTimeWorkingTime;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public long getActionCount() {
        return actionCount;
    }

    public void setActionCount(long actionCount) {
        this.actionCount = actionCount;
    }

    public String getLastActionDate() {
        return lastActionDate;
    }

    public void setLastActionDate(String lastActionDate) {
        this.lastActionDate = lastActionDate;
    }

    public String getClosedIn() {
        return closedIn;
    }

    public void setClosedIn(String closedIn) {
        this.closedIn = closedIn;
    }

    public String getReopenedIn() {
        return reopenedIn;
    }

    public void setReopenedIn(String reopenedIn) {
        this.reopenedIn = reopenedIn;
    }

    public String getResolvedIn() {
        return resolvedIn;
    }

    public void setResolvedIn(String resolvedIn) {
        this.resolvedIn = resolvedIn;
    }

    public String getOwnerTeam() {
        return ownerTeam;
    }

    public void setOwnerTeam(String ownerTeam) {
        this.ownerTeam = ownerTeam;
    }

    public String getOriginEmailAccount() {
        return originEmailAccount;
    }

    public void setOriginEmailAccount(String originEmailAccount) {
        this.originEmailAccount = originEmailAccount;
    }

    public long getOrigin() {
        return origin;
    }

    public void setOrigin(long origin) {
        this.origin = origin;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Time getHoraTicket() {
        return horaTicket;
    }

    public void setHoraTicket(Time horaTicket) {
        this.horaTicket = horaTicket;
    }

    

    public Clients getClients() {
        return clients;
    }

    public void setClients(Clients clients) {
        this.clients = clients;
    }

    public CustomClients getCustomClients() {
        return customClients;
    }

    public void setCustomClients(CustomClients customClients) {
        this.customClients = customClients;
    }

    @Override
    public String toString() {
        return "Tickets [actionCount=" + actionCount + ", baseStatus=" + baseStatus + ", category=" + category
                + ", chatTalkTime=" + chatTalkTime + ", chatWaitingTime=" + chatWaitingTime + ", clients=" + clients
                + ", closedIn=" + closedIn + ", createdDate=" + createdDate + ", customClients=" + customClients
                + ", dataTicket=" + dataTicket + ", horaTicket=" + horaTicket + ", id=" + id + ", justification="
                + justification + ", lastActionDate=" + lastActionDate + ", lastUpdate=" + lastUpdate
                + ", lifeTimeWorkingTime=" + lifeTimeWorkingTime + ", origin=" + origin + ", originEmailAccount="
                + originEmailAccount + ", owner=" + owner + ", ownerTeam=" + ownerTeam + ", protocol=" + protocol
                + ", reopenedIn=" + reopenedIn + ", resolvedIn=" + resolvedIn + ", resolvedInFirstCall="
                + resolvedInFirstCall + ", slaAgreement=" + slaAgreement + ", slaAgreementRule=" + slaAgreementRule
                + ", slaResponseTime=" + slaResponseTime + ", slaSolutionTime=" + slaSolutionTime + ", status=" + status
                + ", stoppedTime=" + stoppedTime + ", stoppedTimeWorkingTime=" + stoppedTimeWorkingTime + ", subject="
                + subject + ", type=" + type + ", urgency=" + urgency + "]";
    }  

    
}
