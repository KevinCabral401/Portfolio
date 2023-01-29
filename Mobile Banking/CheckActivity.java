package com.example.mobilebanking;

import static com.example.mobilebanking.SecondaryActivity.accountAdapter;
import static com.example.mobilebanking.SecondaryActivity.bankingSQLiteHelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class CheckActivity extends AppCompatActivity {

    ImageView imageView;
    EditText editText;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        imageView = findViewById(R.id.imageViewCheck);
        editText = findViewById(R.id.editTextCheckAmount);

        Bitmap imageBitmap = getIntent().getParcelableExtra("image");
        pos = getIntent().getIntExtra("pos", -1);
        imageView.setImageBitmap(imageBitmap);

    }

    public void depositCheck(View view){
        if(!String.valueOf(editText.getText()).isEmpty()) {
            Account myAccount = bankingSQLiteHelper.getAccount(pos);
            double original = myAccount.getAmount();
            double toAdd = Integer.parseInt(String.valueOf(editText.getText()));
            double amountToAdd = original + toAdd;
            bankingSQLiteHelper.updateAmount(pos, amountToAdd);
            Account.recentActivity.add("Deposited a check worth " + toAdd + " into " + myAccount.getNumber());
            Intent intent = new Intent(getApplicationContext(), SecondaryActivity.class);
            startActivity(intent);
            accountAdapter.notifyDataSetChanged();
        }
    }

}