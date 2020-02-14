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

    public static final String INSERT_INVOICE_DTO =
            "INSERT " +
                    "INTO upload_invoice " +
                        "(invoice_date, " +
                        "due_date, " +
                        "status, " +
                        "currency, " +
                        "exchange_rate, " +
                        "discount, " +
                        "tax, " +
                        "paid, " +
                        "total, " +
    //                    "sparse, " +
                        "internal_id, " +
    //                    "source_system, " +
                        "invoice_number, " +
                        "is_deleted, " +
    //                    "created_at, " +
    //                    "updated_at, " +
                        "customer_internal_id, " +
    //                    "notes, " +
                        "custom_fields, " +
    //                    "close_date, " +
    //                    "pdf_url, " +
                        "terms) " +

                    "values (" +
                        ":invoiceDate," +
                        ":dueDate," +
                        ":status," +
                        ":currency," +
                        ":exchangeRate," +
                        ":discount," +
                        ":tax," +
                        ":paid," +
                        ":total," +
                        ":internalId," +
                        ":invoiceNumber," +
                        ":deleted," +
                        ":customerId," +
                        ":customFields," +
    //                    ":close_date," +
                        ":terms);";

    @Override
    public void processData(List<InvoiceDTO> payload) {

//        List<Map<String, Object>> batchValues = new ArrayList<>(payload.size());
        List<Map<String, Object>> batchValues = new ArrayList<>(payload.size());

        List<InvoiceLineDTO> invoiceLineDTOList = new ArrayList<>();

        for (InvoiceDTO invoiceDTO : payload) {
            invoiceLineDTOList.addAll(invoiceDTO.getInvoiceLines());

            batchValues.add(
                    new MapSqlParameterSource("invoiceDate", invoiceDTO.getInvoiceDate())
                            .addValue("dueDate", invoiceDTO.getDueDate())
                            .addValue("status", invoiceDTO.getStatus())
                            .addValue("currency", invoiceDTO.getCurrency())
                            .addValue("exchangeRate", invoiceDTO.getExchangeRate())
                            .addValue("discount", invoiceDTO.getDiscount())
                            .addValue("tax", invoiceDTO.getTax())
                            .addValue("paid", invoiceDTO.getPaid())
                            .addValue("total", invoiceDTO.getTotal())
                            .addValue("internalId", invoiceDTO.getInternalId())
                            .addValue("invoiceNumber", invoiceDTO.getInvoiceNumber())
                            .addValue("deleted", invoiceDTO.isDeleted())
                            .addValue("customerId", invoiceDTO.getCustomerId())
                            .addValue("customFields", invoiceDTO.getCustomFields())
                            .addValue("terms", invoiceDTO.getTerms())
                            .getValues());
        }

//        System.out.println(payload.size());
//        System.out.println(payload.get(payload.size()-1).toString());

//        Map namedParameters = new HashMap();
//        namedParameters.put("invoice_date", Integer.valueOf(forum.getForumId()));


//        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(payload.toArray());
//        namedParameterJdbcTemplate.batchUpdate(INSERT_INVOICE_DTO, batch);

        //based on the batch size split the payload on chunks and persist it in Db
        // do all magic here
        namedParameterJdbcTemplate.batchUpdate(INSERT_INVOICE_DTO, batchValues.toArray(new Map[payload.size()]));
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
