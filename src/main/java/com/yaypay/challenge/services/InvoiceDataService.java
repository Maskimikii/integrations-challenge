package com.yaypay.challenge.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yaypay.challenge.dtos.InvoiceDTO;
import com.yaypay.challenge.dtos.InvoiceLineDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InvoiceDataService implements DataService<InvoiceDTO> {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final int batchSize;

    public InvoiceDataService(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                              int batchSize) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.batchSize = batchSize;
    }

    @Value("${sql.insert.invoicedto}")
    String insertInvoiceDtoSql;

    @Value("${sql.insert.invoicelinesdto}")
    String insertInvoiceDtoLinesSql;

    @Override
    public void processData(List<InvoiceDTO> payload) {
        System.out.println("*** Received list of " + payload.size() + " InvoiceDTO records. Uploading to db...");
        saveUpload_invoiceToDb(payload);
        System.out.println("invoiceDTO data uploaded to DB - Begin uploading invoice lines data to db...");
        saveUploadInvoiceItemToDb(payload);
        System.out.println("finished recording " + payload.size() + " records");
    }

    private void saveUploadInvoiceItemToDb(List<InvoiceDTO> payload) {
        List<InvoiceLineDTO> allInvoiceLineDTOList = new ArrayList<>();
        payload.forEach(inv -> {
            if (inv.getInvoiceLines() != null) {
                inv.getInvoiceLines().forEach( line -> allInvoiceLineDTOList.add(line));
            }
        });
        List<Map<String, Object>> batchValues = new ArrayList<>(payload.size());
        allInvoiceLineDTOList.forEach(invoiceLineDTO -> {
            try {
                batchValues.add(
                    new MapSqlParameterSource("description", invoiceLineDTO.getDescription())
                        .addValue("quantity", invoiceLineDTO.getQuantity())
                        .addValue("rate", invoiceLineDTO.getRate())
                        .addValue("amount", invoiceLineDTO.getAmount())
                        .addValue("internal_id", invoiceLineDTO.getInvoiceId())
                        .addValue("source_system", "")
                        .addValue("name", "")
                        .addValue("custom_fields", new ObjectMapper().writeValueAsString(invoiceLineDTO.getCustomFields()))
                        .addValue("updated_at", "")
                        .addValue("item_type", "")
                        .getValues());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        namedParameterJdbcTemplate.batchUpdate(insertInvoiceDtoLinesSql, batchValues.toArray(new Map[allInvoiceLineDTOList.size()]));
    }

    void saveUpload_invoiceToDb(List<InvoiceDTO> payload) {
        List<Map<String, Object>> batchValues = new ArrayList<>(payload.size());
        payload.forEach(invoiceDTO -> {
            try {
                batchValues.add(
                    new MapSqlParameterSource("invoice_date", invoiceDTO.getInvoiceDate())
                        .addValue("due_date", invoiceDTO.getDueDate())
                        .addValue("status", invoiceDTO.getStatus())
                        .addValue("currency", invoiceDTO.getCurrency())
                        .addValue("exchange_rate", invoiceDTO.getExchangeRate())
                        .addValue("discount", invoiceDTO.getDiscount())
                        .addValue("tax", invoiceDTO.getTax())
                        .addValue("paid", invoiceDTO.getPaid())
                        .addValue("total", invoiceDTO.getTotal())
                        .addValue("sparse", false)
                        .addValue("internal_id", invoiceDTO.getInternalId())
                        .addValue("source_system", "")
                        .addValue("invoice_number", invoiceDTO.getInvoiceNumber())
                        .addValue("is_deleted", invoiceDTO.isDeleted())
                        .addValue("created_at", LocalDate.now())
                        .addValue("updated_at", LocalDate.now())
                        .addValue("customer_internal_id", invoiceDTO.getCustomerId())
                        .addValue("notes", "")
                        .addValue("custom_fields", new ObjectMapper().writeValueAsString(invoiceDTO.getCustomFields()))
                        .addValue("pdf_url", "")
                        .addValue("terms", invoiceDTO.getTerms())
                        .getValues());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        namedParameterJdbcTemplate.batchUpdate(insertInvoiceDtoSql, batchValues.toArray(new Map[payload.size()]));
    }
}
