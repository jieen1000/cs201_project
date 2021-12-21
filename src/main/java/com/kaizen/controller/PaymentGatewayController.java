package com.kaizen.controller;

import com.kaizen.client.StripeClient;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * {@code PaymentGatewayController} is a rest controller for payment gateway.
 *
 * @author Teo Keng Swee
 * @version 1.0
 * @since 2021-10-15
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/api/payment")
public class PaymentGatewayController {
    /**
     * The stripe client used to charge card.
     */
    private StripeClient stripeClient;

    /**
     * Create a payment gateway's controller with the specific stripe client.
     * 
     * @param stripeClient the stripe client used by the application.
     */
    @Autowired
    PaymentGatewayController(StripeClient stripeClient) {
        this.stripeClient = stripeClient;
    }

    /**
     * Charge the card with the specific token and amount through the stripe client.
     * 
     * @param token  the token of the card's information.
     * @param amount the amount to charge the card.
     * @return the charge on the card.
     */
    @PostMapping("/charge")
    public Charge chargeCard(@RequestHeader(value = "token") String token,
            @RequestHeader(value = "amount") Double amount) throws Exception {
        System.out.println("hello");
        return this.stripeClient.chargeNewCard(token, amount);
    }
}