package com.example.mobilebanking;

import static com.example.mobilebanking.SecondaryActivity.accountAdapter;
import static com.example.mobilebanking.SecondaryActivity.bankingSQLiteHelper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class TransferActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner toSpinner;
    Spinner fromSpinner;
    EditText amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        toSpinner = findViewById(R.id.spinnerFrom);
        fromSpinner = findViewById(R.id.spinnerTo);
        amount = findViewById(R.id.editTextAmount);

        toSpinner.setOnItemSelectedListener(this);
        fromSpinner.setOnItemSelectedListener(this);

        ArrayAdapter array = new ArrayAdapter(this, android.R.layout.simple_spinner_item, bankingSQLiteHelper.getAccounts());

        array.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        toSpinner.setAdapter(array);
        fromSpinner.setAdapter(array);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.v("Here", "Item Clicked");
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void transferSubmit(View view){ //deal with empty amount box
        Account myAccountFrom = bankingSQLiteHelper.getAccount(fromSpinner.getSelectedItemPosition());
        Account myAccountTo = bankingSQLiteHelper.getAccount(toSpinner.getSelectedItemPosition());
        Log.v("Compare", myAccountFrom.getNumber());
        Log.v("Compare", myAccountTo.getNumber());
        Log.v("Compare", String.valueOf(String.valueOf(myAccountFrom.getNumber()).equals(String.valueOf(myAccountTo))));
        if(!String.valueOf(myAccountFrom.getNumber()).equals(String.valueOf(myAccountTo.getNumber())) && !String.valueOf(amount.getText()).isEmpty()) {
            double fromAmount = myAccountFrom.getAmount();
            double amountTransfer = Double.parseDouble(String.valueOf(amount.getText()));
            double toAmount = myAccountTo.getAmount();
            double takeAwayFrom = fromAmount - amountTransfer;
            double addTo = toAmount + amountTransfer;
            if (amountTransfer <= fromAmount) {
                bankingSQLiteHelper.updateAmount(fromSpinner.getSelectedItemPosition(), takeAwayFrom);
                bankingSQLiteHelper.updateAmount(toSpinner.getSelectedItemPosition(), addTo);
                Account.recentActivity.add("Transfered " + amountTransfer + " from " + myAccountFrom.getNumber() + " to " + myAccountTo.getNumber());
                accountAdapter.notifyDataSetChanged();
                finish();
            } else {
                Toast toast = Toast.makeText(this, "Broke ", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}