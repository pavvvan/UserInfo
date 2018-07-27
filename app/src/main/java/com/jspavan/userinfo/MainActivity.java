package com.jspavan.userinfo;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper infoDB;
    EditText etName,etEmail,etPhone,etManu,etModel,etReg,etID;
    Button btnAddData,btnview,btndel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        infoDB = new DatabaseHelper(this);

        etName=(EditText) findViewById(R.id.etName);
        etEmail=(EditText) findViewById(R.id.etEmail);
        etPhone=(EditText) findViewById(R.id.etPhone);
        etManu=(EditText) findViewById(R.id.etManu);
        etModel=(EditText) findViewById(R.id.etModel);
        etReg=(EditText) findViewById(R.id.etReg);
        btnAddData=(Button)findViewById(R.id.btnAdd) ;
        btnview=(Button)findViewById(R.id.btnView) ;
        btndel=(Button)findViewById(R.id.btndel) ;

        etID = (EditText) findViewById(R.id.etID);

        AddData();
        ViewData();
        DeleteData();

    }

    public void AddData(){
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=etName.getText().toString();
                String email=etEmail.getText().toString();
                String phone=etPhone.getText().toString();
                String manu=etManu.getText().toString();
                String model=etModel.getText().toString();
                String reg=etReg.getText().toString();

                boolean insertData= infoDB.addData(name,email,phone,manu,model,reg);

                if (insertData == true){
                    Toast.makeText(MainActivity.this,"Successfully data added",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(MainActivity.this,"data already Exist",Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    public void ViewData(){
        btnview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor data = infoDB.showData();

                if (data.getCount() == 0) {
                    display("Error", "No Data Found.");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (data.moveToNext()) {
                    buffer.append("ID: " + data.getString(0) + "\n\n");
                    buffer.append("Name: " + data.getString(1) + "\n\n");
                    buffer.append("Email: " + data.getString(2) + "\n\n");
                    buffer.append("Phone: " + data.getString(3) + "\n\n");
                    buffer.append("Manufacturer: " + data.getString(4) + "\n\n");
                    buffer.append("Model: " + data.getString(5) + "\n\n");
                    buffer.append("REG NO: " + data.getString(6) + "\n===========================\n");

                    display("User & Vehicle Info:", buffer.toString());
                }
            }
        });
    }

    public void display(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intt=new Intent(MainActivity.this,MainActivity.class);
                startActivity(intt);
                finish();
            }
        });
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }

    public void DeleteData(){
        btndel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = etID.getText().toString().length();
                if(temp > 0){
                    Integer deleteRow = infoDB.deleteData(etID.getText().toString());
                    if(deleteRow > 0){
                        Toast.makeText(MainActivity.this, "Successfully Deleted ", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this, " Enter An ID to Delete ", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}
