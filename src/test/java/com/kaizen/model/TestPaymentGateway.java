package com.kaizen.model;

/**
 * {@code TestPaymentGateway} is a mock class that stored configurations needed to do
 * testing related to {@link PaymentGatewayController}.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-11-06
 */
public class TestPaymentGateway {
    /**
     * Represents the URL extension of the paymentGateway's API endpoint.
     */
    public final static String URL_EXTENSION = "/api/payment/";

    /**
     * Represents the URL extension of the paymentGateway's API endpoint to charge.
     */
    public final static String URL_EXTENSION_CHARGE = URL_EXTENSION + "charge";

    /**
     * Represents the token key that used in paymentGateway's API call.
     */
    public final static String TOKEN_KEY = "token";

    /**
     * Represents the amount key that used in paymentGateway's API call.
     */
    public final static String AMOUNT_KEY = "amount";

    /**
     * Represents the amount that used in paymentGateway's API call.
     */
    public final static String TOKEN = "token";

    /**
     * Represents the amount that used in paymentGateway's API call.
     */
    public final static Double AMOUNT = 0.0;
}
