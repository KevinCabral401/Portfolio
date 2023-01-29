package com.example.mobilebanking;

import static com.example.mobilebanking.SecondaryActivity.accountAdapter;
import static com.example.mobilebanking.SecondaryActivity.bankingSQLiteHelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView textViewNumber;
    TextView textViewName;
    TextView textViewAmount;
    int pos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        accountAdapter.notifyDataSetChanged();

        textViewName = findViewById(R.id.textViewNameDetail);
        textViewNumber = findViewById(R.id.textViewNumberDetail);
        textViewAmount = findViewById(R.id.textViewAmountDetail);

        pos = getIntent().getIntExtra("position", -1);
        Account myAccount = bankingSQLiteHelper.getAccount(pos);

        textViewNumber.setText(myAccount.getNumber());
        textViewName.setText(myAccount.getName());
        textViewAmount.setText(String.valueOf(myAccount.getAmount()));
    }

    public void depositDetail(View view){
        if(bankingSQLiteHelper.getCount() >= 1){
            Boolean bool = true;
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Intent intent = new Intent(getApplicationContext(), CheckActivity.class);
            intent.putExtra("image", imageBitmap);
            intent.putExtra("pos", pos);
            startActivity(intent);
        }
    }

}