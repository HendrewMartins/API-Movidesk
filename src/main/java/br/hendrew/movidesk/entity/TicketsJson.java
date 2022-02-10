package br.hendrew.movidesk.entity;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class TicketsJson {

    private long id;
    private String baseStatus;
    private String status;
    private String urgency;
    private String category;
    private String createdDate;
    private String subject;
    private long type;
    private Owner owner;
    private long slaResponseTime;
    private long slaSolutionTime;
    private String slaAgreementRule;
    private String slaAgreement;
    private long chatWaitingTime;
    private long chatTalkTime;
    private boolean resolvedInFirstCall;
    private long stoppedTimeWorkingTime;
    private long stoppedTime;
    private long lifeTimeWorkingTime;
    private String lastUpdate;
    private long actionCount;
    private String lastActionDate;
    private String closedIn;
    private String reopenedIn;
    private String resolvedIn;
    private String ownerTeam;
    private String originEmailAccount;
    private long origin;
    private String justification;
    private String protocol;
    private Date dataTicket;
    private Time horaTicket;
    private List<Clients> clients;
    private  List<CustomFieldValues> customFieldValues;
    // private ActionsAux actions;

    public TicketsJson() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
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

    public Date getDataTicket() {
        return dataTicket;
    }

    public void setDataTicket(Date dataTicket) {
        this.dataTicket = dataTicket;
    }

    public Time getHoraTicket() {
        return horaTicket;
    }

    public void setHoraTicket(Time horaTicket) {
        this.horaTicket = horaTicket;
    }
    // public ActionsAux getActions() {
    // return actions;
    // }
    // public void setActions(ActionsAux actions) {
    // this.actions = actions;
    // }

    public List<Clients> getClients() {
        return clients;
    }

    public void setClients(List<Clients> clients) {
        this.clients = clients;
    }

    
    public List<CustomFieldValues> getCustomFieldItem() {
        return customFieldValues;
    }

    public void setCustomFieldItem(List<CustomFieldValues> customFieldValues) {
        this.customFieldValues = customFieldValues;
    }

    @Override
    public String toString() {
        return "TicketsJson [actionCount=" + actionCount + ", baseStatus=" + baseStatus + ", category=" + category
                + ", chatTalkTime=" + chatTalkTime + ", chatWaitingTime=" + chatWaitingTime + ", clients=" + clients
                + ", closedIn=" + closedIn + ", createdDate=" + createdDate + ", customFieldValue=" + customFieldValues
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
