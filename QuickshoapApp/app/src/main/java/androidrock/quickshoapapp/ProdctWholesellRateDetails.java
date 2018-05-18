package androidrock.quickshoapapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidrock.quickshoapapp.helper.Common;
import androidrock.quickshoapapp.helper.MyCart_DB;

/**
 * Created by Vigo Telecome on 23-12-2016.
 */
public class ProdctWholesellRateDetails extends AppCompatActivity {

    TableLayout table_wholesellrate;
    TextView productRate,storeKeeperName,storeKeeperEmail,storeKeeperRecidency,storeKeeperCity;
    EditText txtNumberofQuantity;
    int productid=0,storeid=0,numberofqunatity=0;
    Button addtocartbtn;
    MyCart_DB mycartdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        //objUserSessionManager = new UserSessionManager(getActivity());

        mycartdb = new MyCart_DB(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_wholesell_rate_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // getIntent() is a method from the started activity
        Intent myIntent = getIntent(); // gets the previously created intent
        productid = myIntent.getIntExtra("productid",0);
        storeid = myIntent.getIntExtra("storeid",0);

        table_wholesellrate = (TableLayout) findViewById(R.id.table_wholesellrate);
        getSupportActionBar().setTitle("");
        TableRow tbrow0 = new TableRow(this);
        //table column bind.

        TextView tv0 = new TextView(this);
        tv0.setText(" Range 1 ");
        tv0.setTextColor(Color.BLACK);
        tbrow0.addView(tv0);

        TextView tv1 = new TextView(this);
        tv1.setText(" Range 2 ");
        tv1.setTextColor(Color.BLACK);
        tbrow0.addView(tv1);

        TextView tv2 = new TextView(this);
        tv2.setText(" Discount % ");
        tv2.setTextColor(Color.BLACK);
        tbrow0.addView(tv2);

        table_wholesellrate.addView(tbrow0);

        productRate = (TextView)findViewById(R.id.productRate);
        Typeface customfont = Typeface.createFromAsset(getAssets(),"fonts/IDroid.otf");
        productRate.setTypeface(customfont);

        storeKeeperName = (TextView)findViewById(R.id.storeKeeperName);
        Typeface customfont1 = Typeface.createFromAsset(getAssets(),"fonts/Android Insomnia Regular.ttf");
        storeKeeperName.setTypeface(customfont1);

        storeKeeperEmail = (TextView)findViewById(R.id.storeKeeperEmail);
        Typeface customfont2 = Typeface.createFromAsset(getAssets(),"fonts/Android Insomnia Regular.ttf");
        storeKeeperEmail.setTypeface(customfont2);

        storeKeeperRecidency = (TextView)findViewById(R.id.storeKeeperRecidency);
        Typeface customfont3 = Typeface.createFromAsset(getAssets(),"fonts/Android Insomnia Regular.ttf");
        storeKeeperRecidency.setTypeface(customfont3);

        storeKeeperCity = (TextView)findViewById(R.id.storeKeeperCity);
        Typeface customfont4 = Typeface.createFromAsset(getAssets(),"fonts/Android Insomnia Regular.ttf");
        storeKeeperCity.setTypeface(customfont4);

        txtNumberofQuantity = (EditText)findViewById(R.id.txtNumberofQuantity);
        Typeface customfont5 = Typeface.createFromAsset(getAssets(),"fonts/IDroid.otf");
        txtNumberofQuantity.setTypeface(customfont5);

        addtocartbtn = (Button)findViewById(R.id.addtocartbtn);
        addtocartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        //store data in session
                numberofqunatity  = Integer.valueOf(txtNumberofQuantity.getText().toString());
                mycartdb.insert(numberofqunatity,productid,storeid);
                Toast.makeText(getApplicationContext(), "Item added into your cart.", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        fetchproductwholeselldetails();
    }

    @Override
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

    private void fetchproductwholeselldetails() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String REGISTER_URL = Common.serviceUrl +"/ProductDetailsByProductIdStoreId?productid="+
                productid + "&storeuserid="+storeid;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArrayproductratedetails = object.getJSONArray("productratedetails");

                            for(int i = 0 ; i<jsonArrayproductratedetails.length();i++){
                                JSONObject object1 = jsonArrayproductratedetails.getJSONObject(i);

                                productRate.setText(String.valueOf((double) object1.get("Rate")));
                                String FullName=(String) object1.get("Firstname") + (String) object1.get("Lastname");
                                storeKeeperName.setText(FullName);
                                storeKeeperEmail.setText((String) object1.get("Email"));
                                storeKeeperRecidency.setText((String) object1.get("Residency"));
                                storeKeeperCity.setText((String) object1.get("city"));
                            }


                            JSONArray jsonArrayproductwholesellrate = object.getJSONArray("productwholesellrate");

                            for(int i = 0 ; i<jsonArrayproductwholesellrate.length();i++){
                                JSONObject object1 = jsonArrayproductwholesellrate.getJSONObject(i);

                                TableRow tbrow = new TableRow(getApplicationContext());

                                TextView t1v = new TextView(getApplicationContext());
                                t1v.setText(String.valueOf((int) object1.get("QuantityRange1")));
                                t1v.setTextColor(Color.BLACK);
                                t1v.setGravity(Gravity.CENTER);
                                tbrow.addView(t1v);

                                TextView t2v = new TextView(getApplicationContext());
                                t2v.setText(String.valueOf((int) object1.get("QuantityRange2")));
                                t2v.setTextColor(Color.BLACK);
                                t2v.setGravity(Gravity.CENTER);
                                tbrow.addView(t2v);

                                TextView t3v = new TextView(getApplicationContext());
                                t3v.setText(String.valueOf((double) object1.get("DiscountPercentage")));
                                t3v.setTextColor(Color.BLACK);
                                t3v.setGravity(Gravity.CENTER);
                                tbrow.addView(t3v);

                                table_wholesellrate.addView(tbrow);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        Log.e("error", "" + error);
                    }
                }) {

        };
        requestQueue.add(stringRequest);
    }

}
