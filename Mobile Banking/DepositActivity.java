package com.example.mobilebanking;

import static com.example.mobilebanking.SecondaryActivity.accountAdapter;
import static com.example.mobilebanking.SecondaryActivity.bankingSQLiteHelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class DepositActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinnerAccounts;
    EditText editTextAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);

        spinnerAccounts = findViewById(R.id.spinnerDeposit);
        editTextAmount = findViewById(R.id.editTextAmountDeposit);

        spinnerAccounts.setOnItemSelectedListener(this);
        ArrayAdapter array = new ArrayAdapter(this, android.R.layout.simple_spinner_item, bankingSQLiteHelper.getAccounts());
        array.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        spinnerAccounts.setAdapter(array);

        if(getIntent().getBooleanExtra("Bool", false)){
            spinnerAccounts.setSelection(getIntent().getIntExtra("Pos", 0));
        }

    }

    public void depositSubmit(View view){
        if(!String.valueOf(editTextAmount.getText()).isEmpty()) {
            spinnerAccounts.getSelectedItemPosition();
            editTextAmount.getText();
            Account myAccount = bankingSQLiteHelper.getAccount(spinnerAccounts.getSelectedItemPosition());
            double original = myAccount.getAmount();
            double toAdd = Integer.parseInt(String.valueOf(editTextAmount.getText()));
            double amountToAdd = original + toAdd;
            bankingSQLiteHelper.updateAmount(spinnerAccounts.getSelectedItemPosition(), amountToAdd);
            accountAdapter.notifyDataSetChanged();
            Account.recentActivity.add("Deposited " + toAdd + " into " + myAccount.getNumber());
            Intent intent = new Intent(getApplicationContext(), SecondaryActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}