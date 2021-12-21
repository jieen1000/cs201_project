package com.kaizen.client;

import com.stripe.Stripe;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

/**
 * {@code StripeClient} is used to charge on customer's card.
 *
 * @author Teo Keng Swee
 * @author Tan Jie En
 * @author Pang Jun Rong
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-19
 */
@Component
public class StripeClient {
    /**
     * Create a stripe client's controller and set api key of stripe to a specific
     * api key.
     */
    @Autowired
    StripeClient(@Value("${stripe.apiKey}") String apiKey) {
        Stripe.apiKey = apiKey;
    }

    /**
     * Create a customer with the specific token and email.
     * 
     * @param token the token containing customer's information.
     * @param email the email of customer.
     * @return the created customer.
     */
    public Customer createCustomer(String token, String email) throws Exception {
        Map<String, Object> customerParams = new HashMap<String, Object>();
        customerParams.put("email", email);
        customerParams.put("source", token);
        return Customer.create(customerParams);
    }

    /**
     * Get the customer with the specific id.
     * 
     * @param id the id of the customer.
     * @return the customer with that id.
     */
    private Customer getCustomer(String id) throws Exception {
        return Customer.retrieve(id);
    }

    /**
     * Charge the card with the specific token and amount.
     * 
     * @param token  the token containing card's information.
     * @param amount the amount to charge the card.
     * @return the charge on the card.
     */
    public Charge chargeNewCard(String token, double amount) throws Exception {
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", (int) (amount * 100));
        chargeParams.put("currency", "USD");
        chargeParams.put("source", token);
        Charge charge = Charge.create(chargeParams);
        return charge;
    }

    /**
     * Charge the customer's card with the specific id and amount.
     * 
     * @param customerId the id of the customer.
     * @param amount     the amount to charge the card.
     * @return the charge on the customer's card.
     */
    public Charge chargeCustomerCard(String customerId, int amount) throws Exception {
        String sourceCard = getCustomer(customerId).getDefaultSource();
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", amount);
        chargeParams.put("currency", "USD");
        chargeParams.put("customer", customerId);
        chargeParams.put("source", sourceCard);
        Charge charge = Charge.create(chargeParams);
        return charge;
    }
}