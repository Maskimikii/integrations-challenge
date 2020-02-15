package com.yaypay.challenge.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class InvoiceLineDTO {
    @JsonProperty(value = "description")
    private String description;
    @JsonProperty(value = "quantity", required = true)
    private Double quantity;
    @JsonProperty(value = "rate", required = true)
    private Double rate;
    @JsonProperty(value = "amount", required = true)
    private Double amount;
    @JsonProperty(value = "invoiceId", required = true)
    private String invoiceId;
    @JsonProperty(value = "name", required = true)
    private String name;
    @JsonProperty(value = "custom_fields")
    private Map<String, String> customFields;

    public String getDescription() {
        return description;
    }

    public Double getQuantity() {
        return quantity;
    }

    public Double getRate() {
        return rate;
    }

    public Double getAmount() {
        return amount;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getCustomFields() {
        if (customFields == null) {
            return new HashMap<>();
        }
        return customFields;
    }
}
