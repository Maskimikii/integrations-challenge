package com.yaypay.challenge.services;

import com.yaypay.challenge.dtos.InvoiceDTO;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

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
        //based on the batch size split the payload on chunks and persist it in Db
        // do all magic here
//        namedParameterJdbcTemplate.batchUpdate()
    }
}
