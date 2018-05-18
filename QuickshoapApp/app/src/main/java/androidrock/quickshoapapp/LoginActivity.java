package androidrock.quickshoapapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidrock.quickshoapapp.helper.Common;
import androidrock.quickshoapapp.helper.MyCart_DB;
import androidrock.quickshoapapp.helper.UserSessionManager;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText loginemail, loginpwd;
    CheckBox showpwd;
    Button btnLogin;
    Toolbar toolbar;
    TextInputLayout email,Password;
    TextView txtsignup,forgot;

    String strEmail, strPassword;
    UserSessionManager objUserSessionManager;
    MyCart_DB mycartdb;
    Cursor mycartCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mycartdb = new MyCart_DB(this);
        objUserSessionManager = new UserSessionManager(this);

        TextInputLayout email = (TextInputLayout) findViewById(R.id.email);
        TextInputLayout Password = (TextInputLayout) findViewById(R.id.Password);
        getSupportActionBar().setTitle("Login");

        txtsignup = (TextView) findViewById(R.id.txtsignup);
        forgot = (TextView) findViewById(R.id.forgot);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        loginemail = (EditText) findViewById(R.id.loginemail);
        loginpwd = (EditText) findViewById(R.id.loginpwd);
        showpwd = (CheckBox) findViewById(R.id.showpwd);
        btnLogin.setOnClickListener(this);
        txtsignup.setOnClickListener(this);
        forgot.setOnClickListener(this);
        showpwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    // show password
                    loginpwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    loginpwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
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
        strEmail = loginemail.getText().toString();
        strPassword = loginpwd.getText().toString();
    }


    public Boolean validation() {
        String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        if (!strEmail.matches(emailPattern)) {
            loginemail.setError("Please Enter Valid Email!");
            loginemail.requestFocus();
            return false;
        }
        if (strPassword.isEmpty()) {
            loginpwd.setError("Required Password");
            loginpwd.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.txtsignup) {
            Intent myIntent = new Intent(this,SignUp.class);
            startActivity(myIntent);
        }
        else if(v.getId() == R.id.forgot){
            Intent myIntent = new Intent(this,ForgotPassword.class);
            startActivity(myIntent);
        }
        else if (v.getId() == R.id.btnLogin) {

            getData();
            if (validation()) {

                mycartCursor = mycartdb.SelectAll();
                if (mycartCursor.moveToFirst()) {

                    loginUser(1);
                }
                else
                {
                    loginUser(2);

                }
                mycartCursor.close();

            }
        }
    }

    private void loginUser(final int redirectflag) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String REGISTER_URL = Common.serviceUrl + "/Login";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONArray("lstdata");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object1 = jsonArray.getJSONObject(i);
                                if ((Integer) object1.get("Userid") > 0) {

                                    objUserSessionManager.createUserLoginSession((Integer) object1.get("Userid"),
                                            (String) object1.get("Email"),
                                            (String) object1.get("Password")
                                    );

                                    if(redirectflag == 1) {
                                        //cart have items so redirect to confirm page
                                        Intent myIntent = new Intent(LoginActivity.this, OrderConfirm.class);
                                        myIntent.putExtra("Firstname",strEmail);
                                        startActivity(myIntent);
                                        finish();
                                    }
                                    else
                                    {
                                        Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
                                        myIntent.putExtra("Firstname",strEmail);
                                        startActivity(myIntent);
                                        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        // Add new Flag to start new Activity
                                        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        finish();

                                    }


                                } else {
                                    Toast.makeText(LoginActivity.this, "login failed", Toast.LENGTH_LONG).show();
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
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
                params.put("UserName", strEmail);
                params.put("Password", strPassword);
                return params;
            }

        };
        requestQueue.add(stringRequest);
    }
}
