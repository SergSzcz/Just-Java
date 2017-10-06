package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;


import static android.R.id.message;
import static com.example.android.justjava.R.id.quantity;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the increment button is clicked.
     */

    public void increment (View view) {
        if (quantity == 100){
            Context context = getApplicationContext();
            CharSequence text = "You can't order more than 100 coffes!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }else {
            quantity = quantity + 1;
        }
        displayQuantity(quantity);
    }
    public void decrement (View view) {
        if (quantity == 1) {
            Context context = getApplicationContext();
            CharSequence text = "You can't order less than 1 coffe!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        } else {
            quantity = quantity - 1;
        }
        displayQuantity(quantity);
    }
    public void submitOrder(View view) {

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.notify_me_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        EditText text = (EditText) findViewById(R.id.name_edit_text);
        String name_text = text.getText().toString();

        int price = calculatePrice();
        String summary = createOrderSummary(price, hasWhippedCream, hasChocolate, name_text);
        displayMessage(summary);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_TEXT, summary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    private boolean whippedCream() {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.notify_me_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        return hasWhippedCream;
    }

    private boolean chocolate() {
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();;
        return hasChocolate;
    }


    /** Calculate the price of the order.
     *
     */
    private int calculatePrice(){
        boolean chocolate = chocolate();
        boolean whippedCream = whippedCream();
        int price = 0;
        if (chocolate){
            price = 2 * quantity;
        }
        if (whippedCream){
            price = price + (1 * quantity);
        }
        price = price + (5 * quantity);
        return price;
    }

    /** Creates order summary
     *
     * @param cena price of the order
     */
    private String createOrderSummary(int cena, boolean check, boolean chocolate, String name) {
        String vChocolate;
        String vCream;
        if (chocolate && check){
            vChocolate = "Yes!";
            vCream = "Yes!";
        }else if(!chocolate && check) {
            vChocolate = "No";
            vCream = "Yes!";
        }else if(chocolate && !check){
            vChocolate = "Yes!";
            vCream = "No";
        }else {
            vChocolate = "No";
            vCream = "No";

        }
        String summary = "Name: " + name + "\nQuantity: " + quantity + "\nHas whipped cream ? " + vCream + "\nHas chocolate ? " + vChocolate + "\nTotal: $" + cena + "\nThank you!";
        return summary;
    }
}


