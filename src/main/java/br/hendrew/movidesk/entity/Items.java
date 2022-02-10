package br.hendrew.movidesk.entity;

public class Items {

    private long personId;
    private long clientId;
    private String team;
    private String customFieldItem;
    private String storageFileGuid;
    private String fileName;
    
    public Items() {
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getCustomFieldItem() {
        return customFieldItem;
    }

    public void setCustomFieldItem(String customFieldItem) {
        this.customFieldItem = customFieldItem;
    }

    public String getStorageFileGuid() {
        return storageFileGuid;
    }

    public void setStorageFileGuid(String storageFileGuid) {
        this.storageFileGuid = storageFileGuid;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "Items [clientId=" + clientId + ", customFieldItem=" + customFieldItem + ", fileName=" + fileName
                + ", personId=" + personId + ", storageFileGuid=" + storageFileGuid + ", team=" + team + "]";
    }
    
}
