package androidrock.quickshoapapp;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
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

import androidrock.quickshoapapp.Adapter.SubCategoryAdapter;
import androidrock.quickshoapapp.Moodel.MainCategoryData;
import androidrock.quickshoapapp.helper.Common;

/**
 * Created by Vigo Telecome on 20-12-2016.
 */
public class SubCategory  extends Fragment {

    private RecyclerView mRecyclerView;
    public SubCategoryAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<MainCategoryData> myDataset = new ArrayList<>();

int productid=0;

    public SubCategory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey("productid"))
        {
            productid = getArguments().getInt("productid");
        }

        View view = inflater.inflate(R.layout.fragment_category, container, false);

        mRecyclerView = (RecyclerView) view. findViewById(R.id.recyclerView);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
       /* mAdapter = new MainCategoryAdapter(getActivity(),myDataset);
        mRecyclerView.setAdapter(mAdapter);
        prepareData();*/
        fetchFoodTypeList();
        return view;
    }


    private void fetchFoodTypeList() {

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String REGISTER_URL = Common.serviceUrl +"/SubProductCategorySelectAll?id="+productid;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONArray("lstdata");
                            for(int i = 0 ; i<jsonArray.length();i++){
                                JSONObject object1 = jsonArray.getJSONObject(i);
                                //arraylstfromdb.add(objmodel);CategoryName=Beverages ProductCategory_ID=1006
                                MainCategoryData getterSetter3 = new MainCategoryData(
                                        (String) object1.get("SubCategoryName"),
                                        (String) object1.get("ImagePath"),
                                        (Integer) object1.get("SubProductCategoryId"));
                                myDataset.add(getterSetter3);
                            }

                            mAdapter = new SubCategoryAdapter(getActivity(),myDataset);
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
                        Log.e("error", "" + error);
                    }
                }) {

        };
        requestQueue.add(stringRequest);
    }

}
