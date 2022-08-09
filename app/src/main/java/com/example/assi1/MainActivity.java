package com.example.assi1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    private static final String PHONE = "PHONE";
    private EditText mPhoneInput;
    private String mPhoneNumber = "";
    private Button mButtons[] = new Button[14];
    private final int[] mButtonIds = {
            R.id.button1,R.id.button2,R.id.button3,
            R.id.button4,R.id.button5,R.id.button6,
            R.id.button7,R.id.button8, R.id.button9,
            R.id.buttonStar,R.id.button0,R.id.buttonHash,
            R.id.buttonBack,R.id.buttonCall
    };

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(PHONE,mPhoneNumber);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1){
            String[] permissions = {
                    "android.permission.CALL_PHONE"
            };
            requestPermissions(permissions, 200);
        }
        if(savedInstanceState != null) {
            mPhoneNumber = savedInstanceState.getString(PHONE,"");
        }
        mPhoneInput = (EditText) findViewById(R.id.editTextPhone);

        for(int i = 0; i < 14; i++) {
            mButtons[i] = (Button)findViewById(mButtonIds[i]);
            final int finalI = i;
            mButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(finalI < 12){
                        mPhoneNumber += mButtons[finalI].getText();
                        setPhoneInput(true);
                    }else if(finalI == 12){
                        setPhoneInput(false);
                    }else {// I == 13
                        callPhone();
                    }

                }
            });

        }


    }

    private void setPhoneInput(boolean addOrRm){
        if(addOrRm){
            mPhoneInput.setText(mPhoneNumber);
        }else{
            if(!Objects.equals(mPhoneNumber, "")){
                mPhoneNumber = mPhoneNumber.substring(0, mPhoneNumber.length() - 1);
            }
            mPhoneInput.setText(mPhoneNumber);
        }
    }
    private void callPhone(){
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri uri = Uri.parse("tel:" + mPhoneInput.getText());
        intent.setData(uri);
        startActivity(intent);
    }
}