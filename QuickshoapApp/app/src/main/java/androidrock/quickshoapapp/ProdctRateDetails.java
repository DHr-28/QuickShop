package androidrock.quickshoapapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidrock.quickshoapapp.Adapter.ProductRateDetailAdapter;
import androidrock.quickshoapapp.Moodel.ProductRateDetails;
import androidrock.quickshoapapp.helper.Common;
import androidrock.quickshoapapp.helper.DividerItemDecoration;

/**
 * Created by Vigo Telecome on 22-12-2016.
 */
public class ProdctRateDetails  extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ProductRateDetailAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<ProductRateDetails> myDataset = new ArrayList<>();

    ImageView productImg;
    TextView txtdescription;
    int productid=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.productrate_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // getIntent() is a method from the started activity
        Intent myIntent = getIntent(); // gets the previously created intent
        productid = myIntent.getIntExtra("productid",0);


        mRecyclerView = (RecyclerView)  findViewById(R.id.recyclerView);
        productImg = (ImageView)  findViewById(R.id.productImg);
        txtdescription=(TextView)  findViewById(R.id.txtdescription);
        Typeface customfont = Typeface.createFromAsset(getAssets(),"fonts/Android Insomnia Regular.ttf");
        txtdescription.setTypeface(customfont);


        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        getSupportActionBar().setTitle("");
        fetchproductratedetails();

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


    private void fetchproductratedetails() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String REGISTER_URL = Common.serviceUrl +"/ProductRateByProductId?id="+productid;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONArray("lstdata");
                            for(int i = 0 ; i<jsonArray.length();i++){
                                JSONObject object1 = jsonArray.getJSONObject(i);

                                ProductRateDetails objProductRateDetails = new ProductRateDetails(
                                        (Integer)object1.get("SubProductCategoryId"),
                                        (String) object1.get("SubCategoryName"),
                                        (String) object1.get("Description"),
                                        (double) object1.get("Rate"),
                                        (Integer) object1.get("Userid"),
                                        (String) object1.get("Email"),
                                        (String) object1.get("ImagePath")
                                );

                                //set image path and description here
                                String imageurl = Common.ImageUrl +objProductRateDetails.imgpath;
                                Picasso.with(getApplicationContext()).load(imageurl).into(productImg);
                                txtdescription.setText(objProductRateDetails.Description);
                                myDataset.add(objProductRateDetails);
                            }

                            mAdapter = new ProductRateDetailAdapter(getApplicationContext(),myDataset);
                            mRecyclerView.setAdapter(mAdapter);

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

