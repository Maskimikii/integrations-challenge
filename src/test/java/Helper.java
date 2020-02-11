import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.yaypay.challenge.dtos.InvoiceDTO;
import com.yaypay.challenge.dtos.InvoiceLineDTO;

import java.time.LocalDate;
import java.util.Collections;

public class Helper {
    public static void main(String[] args) throws JsonProcessingException {

        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setCloseDate(LocalDate.now());
        invoiceDTO.setCurrency("USD");
        invoiceDTO.setCustomerId("cust_id");
        invoiceDTO.setCustomFields(Collections.singletonMap("key", "value"));
        invoiceDTO.setDeleted(true);
        invoiceDTO.setDiscount(Double.valueOf(1.0));
        invoiceDTO.setExchangeRate(1.1);
        invoiceDTO.setDueDate(LocalDate.now());
        invoiceDTO.setInternalId("invoice_id");
        invoiceDTO.setInvoiceDate(LocalDate.now());
        invoiceDTO.setInvoiceNumber("12324234");
        invoiceDTO.setPaid(0.0);
        invoiceDTO.setStatus("UNPAID");
        invoiceDTO.setTax(2.43);
        invoiceDTO.setTotal(9999.900);

        InvoiceLineDTO invoiceLineDTO = new InvoiceLineDTO();
        invoiceLineDTO.setAmount(3.3);
        invoiceLineDTO.setCustomFields(Collections.singletonMap("key", "value"));
        invoiceLineDTO.setDescription("descrp");
        invoiceLineDTO.setInvoiceId("invoice_id");
        invoiceLineDTO.setName("name");
        invoiceLineDTO.setQuantity(1.0);
        invoiceLineDTO.setRate(3.5);
        invoiceDTO.setInvoiceLines(Collections.singletonList(invoiceLineDTO));

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        System.out.println(mapper.writeValueAsString(invoiceDTO));
    }
}
