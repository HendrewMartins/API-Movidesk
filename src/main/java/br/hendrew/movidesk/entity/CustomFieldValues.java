package br.hendrew.movidesk.entity;

import java.util.List;

public class CustomFieldValues {

    private long customFieldId;
    private long customFieldRuleId;
    private long line;
    private String value;
    private List<Items> items;
    
    public CustomFieldValues() {
    }

    public long getCustomFieldId() {
        return customFieldId;
    }

    public void setCustomFieldId(long customFieldId) {
        this.customFieldId = customFieldId;
    }

    public long getCustomFieldRuleId() {
        return customFieldRuleId;
    }

    public void setCustomFieldRuleId(long customFieldRuleId) {
        this.customFieldRuleId = customFieldRuleId;
    }

    public long getLine() {
        return line;
    }

    public void setLine(long line) {
        this.line = line;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "CustomFieldItem [customFieldId=" + customFieldId + ", customFieldRuleId=" + customFieldRuleId
                + ", items=" + items + ", line=" + line + ", value=" + value + "]";
    }

    
}
