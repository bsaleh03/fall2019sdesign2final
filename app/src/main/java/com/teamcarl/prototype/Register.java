package com.teamcarl.prototype;

import android.app.Activity;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.ResultSet;

public class Register extends Activity {

    ImageButton bRegister;

    EditText brand, password, confirmpassword;
    int user, brandInfo, brandId;
    String passwordInfo, spassword, sconfirmpassword, sBrandLink, hasuser, haspass;
    DataAccess db = new DataAccess();
    DatabaseHelper dbh = new DatabaseHelper(this);

    InternalDataAccess internalData = new InternalDataAccess();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        brand = (EditText) findViewById(R.id.brandEdit);
        password = (EditText) findViewById(R.id.passwordEdit);
        confirmpassword = (EditText) findViewById(R.id.confirmPass);
        bRegister = (ImageButton) findViewById(R.id.RegisterButton);


        password.setText(internalData.getSharedPreferencesKeyValue(getApplicationContext(), "password"));
        if (!password.getText().toString().isEmpty()) {
            // log user in (using saved file data)
            // get user data
            Intent intent = new Intent(Register.this, login.class);
            //intent.putExtra("User", user);
            try {
                startActivity(intent);
            } catch (Exception e) {
                Log.e("error in login act", e.getMessage());
            }
        }
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spassword = password.getText().toString();
                sconfirmpassword = confirmpassword.getText().toString();
                sBrandLink = brand.getText().toString();

                //checks if passwords match
                if (spassword.equals(sconfirmpassword) == false) {

                    Toast.makeText(getApplicationContext(), "Passwords don't match", Toast.LENGTH_SHORT).show();
                    System.out.println(spassword + sconfirmpassword);
                    //checks if password is at least 6 characters
                } else if (spassword.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password needs at least 6 characters", Toast.LENGTH_SHORT).show();
                }
                //checks if brand field is empty
                else if (sBrandLink.isEmpty()){
                Toast.makeText(getApplicationContext(), "Enter a brand code", Toast.LENGTH_SHORT).show();


                }
                else if (!doesBrandExist(sBrandLink))
                {
                    Toast.makeText(getApplicationContext(), "The Brand Number entered does not exist. Please enter valid Brand Number.", Toast.LENGTH_SHORT).show();
                }
                else {
                    String query = "insert into users (Password, BrandNumber)\n" +
                            "VALUES ('" + password.getText() + "', '" + brandId + "')" +
                            "SELECT @@IDENTITY, '" + password.getText() + "'";

                    String result = db.executeNonQuery(query);

                    if (!result.contains("error")) {
                        //result.getString(1) + " " + result.getString(2),
                        Toast.makeText(getApplicationContext(), "User Added", Toast.LENGTH_SHORT).show();

                        String queryUserID = "SELECT UserID, Password, BrandNumber \n" +
                                "FROM Users\n" +
                                "WHERE password = '" + password.getText() + "' AND BrandNumber = '" + brandId + "'";
                        dbh.login(sBrandLink, password.getText().toString());
                        ResultSet userResult = db.getDataTable(queryUserID);
                        try {
                            while (userResult.next()) {
                                user = userResult.getInt(1);
                                dbh.addUID(userResult.getInt(1));
                                passwordInfo = userResult.getString(2);
                            }
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                        }
                        Intent intent = new Intent(Register.this, RegisterInfo.class);
                        intent.putExtra("userID", Integer.toString(user));
                        intent.putExtra("brandNumber", Integer.toString(brandId));
                        intent.putExtra("password", passwordInfo);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Error Registering", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean doesBrandExist(String brand)
    {
        // query database to see if a result is retured from searching brand number/id
        String queryBrandId = "select brandId from brand where brandLink = '" + brand + "'";
        ResultSet userResult = db.getDataTable(queryBrandId);
        try {
            if (userResult.next()) {
                brandId = userResult.getInt(1);
                return true;
            }
            else
                return false;

        } catch (Exception e) {
            //Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
            System.out.println(e);
            return false;
        }
    }
}
