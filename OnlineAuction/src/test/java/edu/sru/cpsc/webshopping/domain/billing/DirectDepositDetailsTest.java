package edu.sru.cpsc.webshopping.domain.billing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {DirectDepositDetailsTest.class})
public class DirectDepositDetailsTest {

    private DirectDepositDetails directDepositDetails;

    @BeforeEach
    public void setUp() {
        directDepositDetails = new DirectDepositDetails();
    }

    @Test
    public void testSetAndGetId() {
        long id = 1L;
        directDepositDetails.setId(id);
        assertEquals(id, directDepositDetails.getId());
    }

    @Test
    public void testSetAndGetAccountholderName() {
        String accountholderName = "John Doe";
        directDepositDetails.setAccountholderName(accountholderName);
        assertEquals(accountholderName, directDepositDetails.getAccountholderName());
    }

    @Test
    public void testSetAndGetRoutingNumber() {
        String routingNumber = "123456789";
        directDepositDetails.setRoutingNumber(routingNumber);
        assertEquals(routingNumber, directDepositDetails.getRoutingNumber());
    }

    @Test
    public void testSetAndGetAccountNumber() {
        String accountNumber = "987654321";
        directDepositDetails.setAccountNumber(accountNumber);
        assertEquals(accountNumber, directDepositDetails.getAccountNumber());
    }

    @Test
    public void testSetAndGetBankName() {
        String bankName = "Bank of America";
        directDepositDetails.setBankName(bankName);
        assertEquals(bankName, directDepositDetails.getBankName());
    }

    @Test
    public void testSetAndGetBillingAddress() {
        BankAddress bankAddress = new BankAddress();
        directDepositDetails.setBankAddress(bankAddress);
        assertNotNull(directDepositDetails.getBankAddress());
    }

    @Test
    public void testTransferFields() {
        DirectDepositDetails other = new DirectDepositDetails();
        other.setAccountholderName("Jane Doe");
        other.setRoutingNumber("111000025");
        other.setAccountNumber("123456789");
        other.setBankName("Wells Fargo");
        BankAddress bankAddress = new BankAddress();
        other.setBankAddress(bankAddress);

        directDepositDetails.transferFields(other);

        assertEquals(other.getAccountholderName(), directDepositDetails.getAccountholderName());
        assertEquals(other.getRoutingNumber(), directDepositDetails.getRoutingNumber());
        assertEquals(other.getAccountNumber(), directDepositDetails.getAccountNumber());
        assertEquals(other.getBankName(), directDepositDetails.getBankName());
        assertEquals(other.getBankAddress(), directDepositDetails.getBankAddress());
    }

    @Test
    public void testBuildFromForm() {
        DirectDepositDetails_Form other = new DirectDepositDetails_Form();
        other.setAccountholderName("Jane Doe");
        other.setRoutingNumber("111000025");
        other.setAccountNumber("123456789");
        other.setBankName("Wells Fargo");
        ShippingAddress billingAddress = new ShippingAddress();
        directDepositDetails.buildFromForm(other);

        assertEquals(other.getAccountholderName(), directDepositDetails.getAccountholderName());
        assertEquals(other.getRoutingNumber(), directDepositDetails.getRoutingNumber());
        assertEquals(other.getAccountNumber(), directDepositDetails.getAccountNumber());
        assertEquals(other.getBankName(), directDepositDetails.getBankName());
        assertEquals(billingAddress, directDepositDetails.getBankAddress());
    }
}