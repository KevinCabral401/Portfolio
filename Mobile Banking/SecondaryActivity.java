package com.example.mobilebanking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class SecondaryActivity extends AppCompatActivity {

    static AccountAdapter accountAdapter;
    static BankingSQLiteHelper bankingSQLiteHelper;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);

        bankingSQLiteHelper = new BankingSQLiteHelper(getApplicationContext());
        SQLiteDatabase sqLiteDatabase = bankingSQLiteHelper.getReadableDatabase();

        accountAdapter = new AccountAdapter(bankingSQLiteHelper);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(accountAdapter);

    }

    public void showAddDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(SecondaryActivity.this);
        builder.setTitle("New Account");
        View row = getLayoutInflater().inflate(R.layout.alert_layout, null);
        EditText editTextName = row.findViewById(R.id.editTextNameAlert);
        EditText editTextNumber = row.findViewById(R.id.editTextNumberAlert);
        EditText editTextAmount = row.findViewById(R.id.editTextAmountAlert);
        EditText editTextType = row.findViewById(R.id.editTextTypeAlert);
        builder.setView(row);
        builder.setMessage("Continue?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                bankingSQLiteHelper.insert(String.valueOf(editTextNumber.getText()), String.valueOf(editTextName.getText()), Integer.parseInt(String.valueOf(editTextAmount.getText())), String.valueOf(editTextType.getText()));
                accountAdapter.notifyDataSetChanged();
                Account.recentActivity.add("Created new account " + editTextNumber.getText());
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

    public void deposit(View view){
        if(bankingSQLiteHelper.getCount() >= 1){
            Log.v("Here", "Allow Deposit");
            Intent intent = new Intent(getApplicationContext(), DepositActivity.class);
            startActivity(intent);
        }
    }

    public void transfer(View view){
        if(bankingSQLiteHelper.getCount() >= 2){
            Log.v("Here", "Allow Transfer");
            Intent intent = new Intent(getApplicationContext(), TransferActivity.class);
            startActivity(intent);
        } else {
            Log.v("HERE","No Transfer");
        }
    }

    public void pay(View view){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.add){
            showAddDialog();
        }
        if(id == R.id.delete){
            Intent intent = new Intent(getApplicationContext(), MessageActivity.class);
            startActivity(intent);
        }
        return true;
    }
}