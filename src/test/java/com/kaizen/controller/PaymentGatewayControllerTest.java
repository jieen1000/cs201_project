package com.kaizen.controller;

import static org.mockito.Mockito.*;

import com.kaizen.client.StripeClient;
import com.kaizen.model.TestPaymentGateway;
import com.kaizen.service.employee.EmployeeService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * {@code PaymentGatewayControllerTest} is a test class to do unit testing on
 * {@link PaymentGatewayController}.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-11-06
 */
@ContextConfiguration(classes = { PaymentGatewayController.class })
@ExtendWith(SpringExtension.class)
class PaymentGatewayControllerTest {
    /**
     * The paymentGateway's controller used for testing.
     */
    @Autowired
    private PaymentGatewayController paymentGatewayController;

    /**
     * The mocked stripe's client used for testing.
     */
    @MockBean
    private StripeClient stripeClient;

    /**
     * {@code chargeCard_MissingToken_ExpectBadRequest} is a test on
     * {@link PaymentGatewayController#chargeCard(String, Double)} to verify
     * if the method will return Http Status Bad Request(400) when the token is missing in the call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void chargeCard_MissingToken_ExpectBadRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(TestPaymentGateway.URL_EXTENSION_CHARGE).header(TestPaymentGateway.AMOUNT_KEY, 
                        TestPaymentGateway.AMOUNT);

        MockMvcBuilders.standaloneSetup(paymentGatewayController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code chargeCard_MissingAmount_ExpectBadRequest} is a test on
     * {@link PaymentGatewayController#chargeCard(String, Double)} to verify if the
     * method will return Http Status Bad Request(400) when the amount is missing in the call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void chargeCard_MissingAmount_ExpectBadRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(TestPaymentGateway.URL_EXTENSION_CHARGE)
                .header(TestPaymentGateway.TOKEN_KEY, TestPaymentGateway.TOKEN);

        MockMvcBuilders.standaloneSetup(paymentGatewayController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code chargeCard_MissingAmount_ExpectBadRequest} is a test on
     * {@link PaymentGatewayController#chargeCard(String, Double)} to verify if the
     * method will call {@link EmployeeService#getEmployee(String)} and
     * {@link PaymentGatewayService#addPaymentGateway(PaymentGateway)} and return
     * the created specific paymentGateway with Http Status Created(201) and content
     * type of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void chargeCard_Charged_ExpectCharged() throws Exception {
        when(stripeClient.chargeNewCard(any(String.class), any(Double.class))).thenReturn(null);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(TestPaymentGateway.URL_EXTENSION_CHARGE)
                .header(TestPaymentGateway.TOKEN_KEY, TestPaymentGateway.TOKEN)
                .header(TestPaymentGateway.AMOUNT_KEY, 
                        TestPaymentGateway.AMOUNT);

        MockMvcBuilders.standaloneSetup(paymentGatewayController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(stripeClient).chargeNewCard(any(String.class), any(Double.class));
    }
}
