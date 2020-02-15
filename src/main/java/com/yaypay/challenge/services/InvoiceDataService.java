package com.yaypay.challenge.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yaypay.challenge.dtos.InvoiceDTO;
import com.yaypay.challenge.dtos.InvoiceLineDTO;
import com.yaypay.challenge.entities.DebugDude;
import netscape.javascript.JSObject;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

public class InvoiceDataService implements DataService<InvoiceDTO> {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final int batchSize;

    public InvoiceDataService(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                              int batchSize) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.batchSize = batchSize;
    }

    @Override
    public void processData(List<InvoiceDTO> payload) {
        System.out.println("*** size" + payload.size());
        saveUpload_invoiceToDb(payload);
//        saveUploadInvoiceItemToDb(payload);
        System.out.println("finished recording " + payload.size() + " records");
    }

    private void saveUploadInvoiceItemToDb(List<InvoiceDTO> payload) {
        List<InvoiceLineDTO> invoiceLineDTOList = new ArrayList<>();
        payload.forEach(invoiceLineDTO -> invoiceLineDTOList.addAll(invoiceLineDTO.getInvoiceLines()));
        System.out.println("Lines count = " + invoiceLineDTOList.size());

        String sql = "INSERT INTO upload_invoice_item (description, quantity, rate, amount, internal_id, source_system ," +
                "name, custom_fields, updated_at, item_type)" +
                "VALUES (:description, :quantity, :rate, :amount, :internal_id, :source_system, :name, " +
                ":custom_fields, :updated_at, :item_type);";

        List<Map<String, Object>> batchValues = new ArrayList<>(payload.size());

        invoiceLineDTOList.forEach(invoiceLineDTO ->
        {
            try {
                System.out.println(invoiceLineDTO.toString());
                batchValues.add(
                        new MapSqlParameterSource("description", invoiceLineDTO.getDescription())
                                .addValue("quantity", invoiceLineDTO.getQuantity())
                                .addValue("rate", invoiceLineDTO.getRate())
                                .addValue("amount", invoiceLineDTO.getAmount())
                                .addValue("internal_id", invoiceLineDTO.getInvoiceId())
                                .addValue("source_system", "")
                                .addValue("name", "")
                                .addValue("custom_fields", new ObjectMapper().writeValueAsString(invoiceLineDTO.getCustomFields()))
                                .addValue("updated_at", LocalDate.now())
                                .addValue("item_type", "")
                                .getValues());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        namedParameterJdbcTemplate.batchUpdate(sql, batchValues.toArray(new Map[invoiceLineDTOList.size()]));


    }

    @Transactional
    void saveUpload_invoiceToDb(List<InvoiceDTO> payload) {
        List<Map<String, Object>> batchValues = new ArrayList<>(payload.size());

        String sql = "INSERT INTO upload_invoice (invoice_date, due_date, status, currency, exchange_rate, discount, " +
                "tax, paid, total, sparse, internal_id, source_system, invoice_number, is_deleted, created_at, " +
                "updated_at, customer_internal_id, notes, custom_fields, pdf_url, terms)" +
                "VALUES (" +
                ":invoice_date, " +
                ":due_date, " +
                ":status, " +
                ":currency, " +
                ":exchange_rate, " +
                ":discount, " +
                ":tax, " +
                ":paid, " +
                ":total, " +
                ":sparse, " +
                ":internal_id, " +
                ":source_system, " +
                ":invoice_number, " +
                ":is_deleted, " +
                ":created_at, " +
                ":updated_at, " +
                ":customer_internal_id, " +
                ":notes, " +
                ":custom_fields, " +
                ":pdf_url, " +
                ":terms);";

        payload.forEach(invoiceDTO ->
        {
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
        namedParameterJdbcTemplate.batchUpdate(sql, batchValues.toArray(new Map[payload.size()]));
    }

    public void debug() {
        Map<String, String> stuff = new HashMap<>();
        stuff.put("weed", "1 kilo");
        stuff.put("snacks", "2 peaces");

        List<DebugDude> dudeList = Arrays.asList(
                new DebugDude(23, "Dude", LocalDate.now(),1967.6300, true, stuff),
                new DebugDude(24, "Alex", LocalDate.now(),1967.6400, false, stuff )
        );
        List<Map<String, Object>> batchValues = new ArrayList<>(dudeList.size());

        String sql = "INSERT INTO debug_dude (age, name, date, money, gay, stuff) VALUES (:age, :name, :date, :money, :gay, :stuff);";
        for (DebugDude dude : dudeList) {
            try {
                batchValues.add(
                        new MapSqlParameterSource("age", dude.getAge())
                                .addValue("name", dude.getName())
                                .addValue("date", dude.getDate())
                                .addValue("money", dude.getMoney())
                                .addValue("gay", dude.isGay())
                                .addValue("stuff", new ObjectMapper().writeValueAsString(dude.getStuff()))
                                .getValues());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        namedParameterJdbcTemplate.batchUpdate(sql, batchValues.toArray(new Map[dudeList.size()]));

    }
}
