package androidrock.quickshoapapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import androidrock.quickshoapapp.helper.Common;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    EditText Firstname, Lastname, Email, Password, residency, City;
    Button Submit;
    TextInputLayout firstname,lastname,email,password,Residency,city;
    String strfirstname,strLastname,strEmail,strPassword,strResidency,strcity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextInputLayout firstname = (TextInputLayout) findViewById(R.id.firstname);
        TextInputLayout lastname = (TextInputLayout) findViewById(R.id.lastname);
        TextInputLayout email = (TextInputLayout) findViewById(R.id.email);
        TextInputLayout password = (TextInputLayout) findViewById(R.id.password);
        TextInputLayout Residency = (TextInputLayout) findViewById(R.id.Residency);
        TextInputLayout city = (TextInputLayout) findViewById(R.id.city);


        Firstname = (EditText) findViewById(R.id.fname);
        Lastname = (EditText) findViewById(R.id.lname);
        Email = (EditText) findViewById(R.id.loginemail);
        Password = (EditText) findViewById(R.id.loginpwd);
        residency = (EditText) findViewById(R.id.residency);
        City = (EditText) findViewById(R.id.City);
        Submit = (Button) findViewById(R.id.btnSubmit);
        getSupportActionBar().setTitle("SignUp");

        Submit.setOnClickListener(this);
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.backmenu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.backbutton) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getData() {
        strfirstname = Firstname.getText().toString();
        strLastname = Lastname.getText().toString();
        strEmail = Email.getText().toString();
        strPassword = Password.getText().toString();
        strResidency = residency.getText().toString();
        strcity = City.getText().toString();
    }


    private void registerUser() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String REGISTER_URL = Common.serviceUrl +"/SignUp";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response != "0")
                        {
                                Toast.makeText(SignUp.this, "Registration successful", Toast.LENGTH_LONG).show();
                                finish();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error", "" + error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("Firstname",strfirstname);
                params.put("Lastname",strLastname);
                params.put("Email",strEmail);
                params.put("Password",strPassword);
                params.put("Residency",strResidency);
                params.put("city",strcity);
                return params;
            }

        };
        requestQueue.add(stringRequest);
    }

    public Boolean validation(){
        String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        if(strfirstname.isEmpty()) {
            Firstname.setError("Required Firstname");
            Firstname.requestFocus();
            return false;
        }
        if(strLastname.isEmpty()){
            Lastname.setError("Required Lastname");
            Lastname.requestFocus();
            return false;
        }

        if(!strEmail.matches(emailPattern)){
            Email.setError("Please Enter Valid Email!");
            Email.requestFocus();
            return false;
        }if(strPassword.isEmpty()){
            Password.setError("Required Password");
            Password.requestFocus();
            return false;
        }if(strResidency.isEmpty()){
            residency.setError("Required Residency");
            residency.requestFocus();
            return false;
        }if(strcity.isEmpty()){
            City.setError("Required City");
            City.requestFocus();
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:
                getData();
                if(validation()){
                    registerUser();
                }
                break;


        }
    }
}
