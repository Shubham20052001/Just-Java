/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * This method is called when the - button is clicked.
     */
    public void decrement(View view) {
        if(quantity>1){
        quantity = quantity - 1;
        displayQuantity(quantity);}
        else { Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();}
    }
    /**
     * This method is called when the + button is clicked.
     */
    public void increment(View view) {
       if(quantity<100){
           quantity = quantity + 1;
           displayQuantity(quantity);}
       else{ Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();}
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText editText = findViewById(R.id.name);
        String name = editText.getText().toString();

        CheckBox chocolate = findViewById(R.id.chocolate);
        boolean haschocolate = chocolate.isChecked();

        CheckBox whippedCream = findViewById(R.id.whipped_cream);
        boolean haswhippedCream = whippedCream.isChecked();

        int totalPrice = calculatePrice(haswhippedCream,haschocolate);
        String priceMessage = orderSummary(totalPrice,haswhippedCream,haschocolate,name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for" + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        displayMessage(priceMessage);

    }

    private String orderSummary(int n,boolean haswhippedCream,boolean haschocolate,String name){
        String Summary = "Name: "+ name + "\nAdd whipped Cream? "+ haswhippedCream +"\nAdd Chocolate? "+haschocolate+ "\nQuantity = " + quantity + "\nTotal = $" + n + "\nThank you!";
        return  Summary;
    }

    /**
     * Calculates the price of the order.
     */
    private int calculatePrice(boolean haswhippedCream,boolean haschocolate) {
        int price = 5;
        if(haswhippedCream) price+=1;
        if(haschocolate) price += 2;
        return price * quantity;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}