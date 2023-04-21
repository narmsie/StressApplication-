package com.example.destresser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText edName, edMatricID, edEmail, edPassword;
    Button btn;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edName = findViewById(R.id.editTextRegName);
        edMatricID = findViewById(R.id.editTextRegmatricID);
        edEmail = findViewById(R.id.editTextRegEmailAddress);
        edPassword = findViewById(R.id.editTextRegPassword);
        btn = findViewById(R.id.register_button);
        tv = findViewById(R.id.textViewExistingUser);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edName.getText().toString();
                String matricID = edMatricID.getText().toString();
                String email = edEmail.getText().toString();
                String password = edPassword.getText().toString();
                Database db = new Database(getApplicationContext(),"destresser",null,1);


                if(name.length()==0 || matricID.length()==0 || email.length()==0 || password.length()==0){
                    Toast.makeText(getApplicationContext(), "Please fill in the details", Toast.LENGTH_SHORT).show();
                    return;
                } else if(db.register(name,matricID,email,password)==1){
                    // User with the same matric number already exists
                    Toast.makeText(getApplicationContext(), "Matric ID already exist. Proceed to Login", Toast.LENGTH_SHORT).show();
                    return;
                }else if(!name.matches("[a-zA-Z]+")){
                    Toast.makeText(getApplicationContext()," Name Should Only Contain Alphabetical Character", Toast.LENGTH_SHORT).show();
                    return;
                }else if(matricID.length()<=7){
                    Toast.makeText(getApplicationContext(), "Matric ID Must Have Minimum 8 Character ", Toast.LENGTH_SHORT).show();
                    return;
                }else if(!matricID.matches("^[a-zA-Z][a-zA-Z][0-9][0-9][0-9][0-9][0-9][0-9]$")){
                    Toast.makeText(getApplicationContext(),"Enter correct matric ID", Toast.LENGTH_SHORT).show();
                    return;
                }else if(!email.matches("^[a-zA-Z0-9._%+-]+@(student\\.)?uthm\\.edu\\.my$")){
                    Toast.makeText(getApplicationContext(), "Enter your UTHM student or staff email", Toast.LENGTH_SHORT).show();
                    return;
                }else if(password.length()<=7) {
                    Toast.makeText(getApplicationContext(), "Password Must Have Minimum 8 Character ", Toast.LENGTH_SHORT).show();
                    return;
                }else if(!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")){
                    Toast.makeText(getApplicationContext(),"Password Must Have at least one uppercase letter, one lowercase letter, one number and one special character:", Toast.LENGTH_LONG).show();
                    return;
                }else{
                    Toast.makeText(getApplicationContext(), "Register Successful", Toast.LENGTH_SHORT).show();
                }
                db.register(name, matricID, email, password);
            }
        });
    }
}