package com.example.operaadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    EditText edt_name, edt_email, edt_pwd, edt_cpwd;
    Button register_btn;
    TextView have_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edt_name = findViewById(R.id.edt_name);
        edt_email = findViewById(R.id.edt_email);
        edt_pwd = findViewById(R.id.edt_pass);
        edt_cpwd = findViewById(R.id.edt_cpass);
        register_btn = findViewById(R.id.reg_btn);
        have_account = findViewById(R.id.have_acc);
    }
}