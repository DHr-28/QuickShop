package androidrock.quickshoapapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.Date;
import java.util.List;

import androidrock.quickshoapapp.Adapter.OrderHistoryAdepter;
import androidrock.quickshoapapp.Moodel.OrderHistoryModel;
import androidrock.quickshoapapp.helper.Common;
import androidrock.quickshoapapp.helper.DividerItemDecoration;
import androidrock.quickshoapapp.helper.UserSessionManager;

/**
 * Created by Vigo Telecome on 01-01-2017.
 */
public class OrderHistory extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private OrderHistoryAdepter mAdapter;

    private List<OrderHistoryModel> myDataset = new ArrayList<>();

    UserSessionManager objUserSessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        objUserSessionManager = new UserSessionManager(getActivity());

        View view = inflater.inflate(R.layout.order_history, container, false);

        mRecyclerView = (RecyclerView) view. findViewById(R.id.recyclerViewCart);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        int loginuserid = objUserSessionManager.getloginretailerid();
        if(loginuserid > 0) {
            fetchorderhistory(loginuserid);
        }
        else {
            Toast.makeText(getActivity(), "No order found.", Toast.LENGTH_LONG).show();
        }
        return view;

    }


    private void fetchorderhistory(int userid) {

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String REGISTER_URL = Common.serviceUrl +"/GetOrderDetails?retailuserId="+userid;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONArray("lstdata");
                            for(int i = 0 ; i<jsonArray.length();i++){
                                JSONObject object1 = jsonArray.getJSONObject(i);

                                OrderHistoryModel objOrderHistoryModel = new OrderHistoryModel();
                                objOrderHistoryModel.SubCategoryName= (String)object1.get("SubCategoryName");
                                objOrderHistoryModel.Status= (String)object1.get("Status");
                                objOrderHistoryModel.ShoapMasterName= (String)object1.get("ShoapMasterName");
                                objOrderHistoryModel.totalNetValue= (double)object1.get("totalNetValue");
                                objOrderHistoryModel.ImagePath= (String)object1.get("ImagePath");





                                myDataset.add(objOrderHistoryModel);
                            }
                            mAdapter = new OrderHistoryAdepter(getActivity(),myDataset);
                            mRecyclerView.setAdapter(mAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                        Log.d("error", "" + error);
                    }
                }) {

        };
        requestQueue.add(stringRequest);
    }



}
