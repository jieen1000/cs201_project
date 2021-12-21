import React from "react";
import SuiButton from "components/SuiButton";
import Stripe from "react-stripe-checkout";
import { Auth } from "aws-amplify";
import SuiBox from "components/SuiBox";
import { useState } from "react";

/**
 * {@code Checkout} creates the component for Stripe integration and checkout.
 *
 * @author Pang Jun Rong
 * @author Teo Keng Swee
 * @version 1.0
 * @since 2021-10-16
 */

export default function App(props) {
  const { employeeSkill, onClose, selectedValue, open, selection } = props;
  const startDate = new Date(
    selection.startDate.getFullYear(),
    selection.startDate.getMonth(),
    selection.startDate.getDate() + 1
  );
  const endDate = new Date(
    selection.endDate.getFullYear(),
    selection.endDate.getMonth(),
    selection.endDate.getDate() + 1
  );
  const numDays =
    (selection.endDate.getMonth() - selection.startDate.getMonth()) * 30 +
    selection.endDate.getDate() -
    selection.startDate.getDate() +
    1;
  const [loanCompany, setLoanCompany] = useState(null);

  const totalCost = (numDays * employeeSkill.cost * 100.0) / 7.0;

  async function handleToken(token) {
    Auth.currentSession().then((res) => {
      fetch(`/api/payment/charge`, {
        method: "POST",
        headers: {
          Authorization: "Bearer " + res.getIdToken().getJwtToken(),
          Accept: "application/json",
          "Content-Type": "application/json",
          token: token.id,
          amount: (totalCost / 100).toFixed(2),
        },
      })
        .then(() => {
          alert(
            "Payment Success \nYou will be contacted in 1-3 business days by the parent company regarding the hire-sharing."
          );
          const compId = res.getIdToken().payload["cognito:groups"][0];

          var dataFormatted = {
            startDate: startDate,
            endDate: endDate,
            totalCost: (totalCost / 100).toFixed(2),
            loanCompanyId: employeeSkill.uen,
            borrowingCompanyId: compId,
            employeeId: employeeSkill.workPermitNumber,
            status: "Pending",
          };
          fetch("/api/transactions", {
            method: "POST",
            headers: {
              Authorization: "Bearer " + res.getIdToken().getJwtToken(),
              Accept: "application/json",
              "Content-Type": "application/json",
            },
            body: JSON.stringify(dataFormatted),
          }).catch((error) => {
            alert(error);
          });
        })
        .catch((error) => {
          alert(error);
        });
    });
  }

  return (
    <Stripe
      name={employeeSkill.name} // the pop-in header title
      description={employeeSkill.company} // the pop-in header subtitle
      image={employeeSkill.imageURL}
      ComponentClass="div"
      label="Submit" // text inside the Stripe button
      panelLabel="Hire for" // prepended to the amount in the bottom pay button
      currency="SGD"
      amount={totalCost}
      locale="en"
      stripeKey="pk_test_51JjP7qFwG6YcxwhyrbB3ljilisjDA3b28136bJ1pQONRGxQY5IxfddiZk3Wx69w8w60BEkIaOi3hVBTsB01yrq04004bU9S3XQ"
      token={handleToken}
    >
      <SuiButton
        size="large"
        variant="gradient"
        buttonColor="success"
        circular
        type="submit"
      >
        <SuiBox px={3} color="white">
          Submit
        </SuiBox>
      </SuiButton>
    </Stripe>
  );
}
