package androidrock.quickshoapapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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
import androidrock.quickshoapapp.helper.MyCart_DB;
import androidrock.quickshoapapp.helper.UserSessionManager;

public class OrderConfirm extends AppCompatActivity implements View.OnClickListener {

    EditText orderaddress1, orderaddress2, ordercontact, Pincode;
    Button Confirm;
    String strorderaddress1, strorderaddress2, strordercontact, strPincode;

    Cursor mycartCursor;
    MyCart_DB mycartdb;
    UserSessionManager objUserSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mycartdb = new MyCart_DB(this);
        objUserSessionManager = new UserSessionManager(this);
        orderaddress1 = (EditText) findViewById(R.id.orderaddress1);
        orderaddress2 = (EditText) findViewById(R.id.orderaddress2);
        ordercontact = (EditText) findViewById(R.id.ordercontact);
        Pincode = (EditText) findViewById(R.id.Pincode);
        Confirm = (Button) findViewById(R.id.Confirm);
        getSupportActionBar().setTitle("Order Confirm");

        Confirm.setOnClickListener(this);

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
        strorderaddress1 = orderaddress1.getText().toString();
        strorderaddress2 = orderaddress2.getText().toString();
        strordercontact = ordercontact.getText().toString();
        strPincode = Pincode.getText().toString();
    }

    public Boolean validation() {

        if (strorderaddress1.isEmpty()) {
            orderaddress1.setError("Empty");
            orderaddress1.requestFocus();
            return false;
        }
        if (strorderaddress2.isEmpty()) {
            orderaddress2.setError("Empty");
            orderaddress2.requestFocus();
            return false;

        }
        if (strordercontact.isEmpty()) {
            ordercontact.setError("Empty");
            ordercontact.requestFocus();
            return false;
        }
        if (strPincode.isEmpty()) {
            Pincode.setError("Empty");
            Pincode.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    private void OrderCartInsert(final int productid, final int storeUSerid, final int numberofquantity, final int lastindex1 ) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String REGISTER_URL = Common.serviceUrl + "/OrderCartInsert";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != "0") {
                            Toast.makeText(OrderConfirm.this, "Order successfully", Toast.LENGTH_LONG).show();
                        }
                        if(lastindex1 == 1)
                        {
                            Intent myIntent = new Intent(OrderConfirm.this, MainActivity.class);
                            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            // Add new Flag to start new Activity
                            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(myIntent);
                            finish();
                        }
                        //cart empty
                        mycartdb.delete(productid,storeUSerid);
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
                int loginretailuserid = objUserSessionManager.getloginretailerid();
                params.put("RetailerUserId", String.valueOf(loginretailuserid));

                params.put("OrderAddress1", strorderaddress1);
                params.put("OrderAddress2", strorderaddress2);
                params.put("OrderContactNumber", strordercontact);
                params.put("OrderPincode", strPincode);

                params.put("ProductId", String.valueOf(productid));
                params.put("storeUSerid", String.valueOf(storeUSerid));
                params.put("numberofquantity", String.valueOf(numberofquantity));

                return params;
            }

        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        getData();
        if (validation()) {

            //fetch all table records.
            mycartCursor = mycartdb.SelectAll();

            if (mycartCursor.moveToFirst()) {
                while (mycartCursor.isAfterLast() == false) {
                    int productid = mycartCursor.getInt(mycartCursor.getColumnIndex(MyCart_DB.MYCART_COLUMN_productidkey));
                    int storeUSerid = mycartCursor.getInt(mycartCursor.getColumnIndex(MyCart_DB.CALLTYPE_COLUMN_wholeselleridkey));
                    int numberofquantity = mycartCursor.getInt(mycartCursor.getColumnIndex(MyCart_DB.MYCART_COLUMN_numberofquantityKey));

                    int lastindex=0;
                    if(mycartCursor.isLast())
                    {
                        lastindex=1;
                    }

                    OrderCartInsert(productid, storeUSerid, numberofquantity,lastindex);
                    mycartCursor.moveToNext();
                }
            }
            mycartCursor.close();

            // OrderCartInsert();

        }
    }


}

