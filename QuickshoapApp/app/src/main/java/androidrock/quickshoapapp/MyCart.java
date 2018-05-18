package androidrock.quickshoapapp;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.List;

import androidrock.quickshoapapp.Adapter.MyCartAdapter;
import androidrock.quickshoapapp.Moodel.MyCartModel;
import androidrock.quickshoapapp.helper.Common;
import androidrock.quickshoapapp.helper.DividerItemDecoration;
import androidrock.quickshoapapp.helper.MyCart_DB;
import androidrock.quickshoapapp.helper.UserSessionManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyCart extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MyCartAdapter mAdapter;

    private List<MyCartModel> myDataset = new ArrayList<>();
    Cursor mycartCursor;
    MyCart_DB mycartdb;
    Button CheckOutbtn;

    UserSessionManager objUserSessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mycartdb = new MyCart_DB(getActivity());
        objUserSessionManager = new UserSessionManager(getActivity());

        View view = inflater.inflate(R.layout.fragment_my_cart, container, false);

        mRecyclerView = (RecyclerView) view. findViewById(R.id.recyclerViewCart);
        CheckOutbtn = (Button)view.findViewById(R.id.CheckOutbtn);
        CheckOutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(objUserSessionManager.checkLogin())
                {
                    //goto order history page.
                    Intent myIntent = new Intent(getActivity(), OrderConfirm.class);
                    startActivity(myIntent);

                }
                else {

                    Intent myIntent = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivity(myIntent);
                }
            }
        });

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        //fetch all table records.
        mycartCursor = mycartdb.SelectAll();

        if (mycartCursor.moveToFirst()) {
            while (mycartCursor.isAfterLast() == false) {
                int productid = mycartCursor.getInt(mycartCursor.getColumnIndex(MyCart_DB.MYCART_COLUMN_productidkey));
                int storeUSerid = mycartCursor.getInt(mycartCursor.getColumnIndex(MyCart_DB.CALLTYPE_COLUMN_wholeselleridkey));
                int numberofquantity = mycartCursor.getInt(mycartCursor.getColumnIndex(MyCart_DB.MYCART_COLUMN_numberofquantityKey));
                int cartid = mycartCursor.getInt(mycartCursor.getColumnIndex("_id"));
                int lastindex=0;
                if(mycartCursor.isLast())
                {
                    lastindex=1;
                }
                fetchcartdetailitem(productid, storeUSerid, numberofquantity,lastindex);
                mycartCursor.moveToNext();
            }
        }
        mycartCursor.close();
        return view;

    }
    private void fetchcartdetailitem(int productid, int storeUSerid, int numberofquantity,final int lastindex1 ) {

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String REGISTER_URL = Common.serviceUrl +"/GetShopCartDetails?ProductId="+productid+"&storeUSerid="+storeUSerid+"&numberofquantity="+numberofquantity+"";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONArray("lstdata");
                            for(int i = 0 ; i<jsonArray.length();i++){
                                JSONObject object1 = jsonArray.getJSONObject(i);
                                MyCartModel objMyCartModel = new MyCartModel();
                                objMyCartModel.basicrate= (double)object1.get("basicrate");
                                objMyCartModel.wholeselldiscount= (double)object1.get("wholeselldiscount");
                                objMyCartModel.totalValue= (double)object1.get("totalValue");
                                objMyCartModel.ImagePath= (String)object1.get("ImagePath");
                                objMyCartModel.Description= (String)object1.get("Description");
                                objMyCartModel.ShoapMasterName= (String)object1.get("ShoapMasterName");
                                objMyCartModel.totalNetValue= (double)object1.get("totalNetValue");
                                objMyCartModel.numberofqunatity =(int)object1.get("numberofqunatity");
                                objMyCartModel.ProductId =(int)object1.get("ProductId");
                                objMyCartModel.wholesellersotreid =(int)object1.get("wholesellersotreid");

                                myDataset.add(objMyCartModel);
                            }

                            if(lastindex1 == 1)
                            {
                                mAdapter = new MyCartAdapter(getActivity(),myDataset,mycartdb);
                                mRecyclerView.setAdapter(mAdapter);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                        Log.e("error", "" + error);
                    }
                }) {

        };
        requestQueue.add(stringRequest);
    }

}
