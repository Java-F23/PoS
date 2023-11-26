package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.PaymentMethod;
// import GUI.SalesScreen;
import views.SalesScreen;

public class PaymentButtonsController implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "cash":
                // PaymentMethod tempPaymentMethod = GUI.PaymentMethod.fromString(cash);
                SalesScreen.setPaymentMethod(model.PaymentMethod.fromString("cash"));
                System.out.println("changing payment to: cash");
                break;
            case "visa":
                // tempPaymentMethod = GUI.PaymentMethod.fromString(visa);
                SalesScreen.setPaymentMethod(model.PaymentMethod.fromString("visa"));
                System.out.println("changing payment to: visa");
                break;
            case "mobile":
                // PaymentMethod tempPaymentMethod = GUI.PaymentMethod.fromString(mobile);
                SalesScreen.setPaymentMethod(model.PaymentMethod.fromString("mobile"));
                System.out.println("changing payment to: mobile");
                break;

            default:
                break;
        }
    }
}
