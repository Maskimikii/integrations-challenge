package com.yaypay.challenge.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@JsonRootName("invoice")
public class InvoiceDTO {
    @JsonProperty(value = "invoice_date", required = true)
    private LocalDate invoiceDate;
    @JsonProperty(value = "invoice_due_date", required = true)
    private LocalDate dueDate;
    @JsonProperty(value = "status", required = true)
    private String status;
    @JsonProperty(value = "currency", required = true)
    private String currency;
    @JsonProperty(value = "exchange_rate", required = true)
    private Double exchangeRate;
    @JsonProperty(value = "discount", defaultValue = "0")
    private Double discount;
    @JsonProperty(value = "tax", required = true)
    private Double tax;
    @JsonProperty(value = "paid", required = true)
    private Double paid;
    @JsonProperty(value = "total", required = true)
    private Double total;
    @JsonProperty(value = "internal_id", required = true)
    private String internalId;
    @JsonProperty(value = "invoice_number", required = true)
    private String invoiceNumber;
    @JsonProperty(value = "deleted", defaultValue = "false")
    private boolean deleted;
    @JsonProperty(value = "customer_id", required = true)
    private String customerId;
    @JsonProperty(value = "custom_fields")
    private Map<String, String> customFields;
    @JsonProperty(value = "close_date")
    private LocalDate closeDate;
    @JsonProperty(value = "terms")
    private String terms;
    @JsonProperty(value = "invoice_lines")
    private List<InvoiceLineDTO> invoiceLines;

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getStatus() {
        return status;
    }

    public String getCurrency() {
        return currency;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public Double getDiscount() {
        return discount;
    }

    public Double getTax() {
        return tax;
    }

    public Double getPaid() {
        return paid;
    }

    public Double getTotal() {
        return total;
    }

    public String getInternalId() {
        return internalId;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Map<String, String> getCustomFields() {
        return customFields;
    }

    public LocalDate getCloseDate() {
        return closeDate;
    }

    public String getTerms() {
        return terms;
    }

    public List<InvoiceLineDTO> getInvoiceLines() {
        return invoiceLines;
    }

    @Override
    public String toString() {
        return "InvoiceDTO{" +
                "invoiceDate=" + invoiceDate +
                ", dueDate=" + dueDate +
                ", status='" + status + '\'' +
                ", currency='" + currency + '\'' +
                ", exchangeRate=" + exchangeRate +
                ", discount=" + discount +
                ", tax=" + tax +
                ", paid=" + paid +
                ", total=" + total +
                ", internalId='" + internalId + '\'' +
                ", invoiceNumber='" + invoiceNumber + '\'' +
                ", deleted=" + deleted +
                ", customerId='" + customerId + '\'' +
                ", customFields=" + customFields +
                ", closeDate=" + closeDate +
                ", terms='" + terms + '\'' +
                ", invoiceLines=" + invoiceLines +
                '}';
    }
}
