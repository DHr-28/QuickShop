package androidrock.quickshoapapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
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
import java.util.List;

import androidrock.quickshoapapp.Adapter.MainCategoryAdapter;
import androidrock.quickshoapapp.Moodel.MainCategoryData;
import androidrock.quickshoapapp.helper.Common;
import ss.com.bannerslider.banners.DrawableBanner;
import ss.com.bannerslider.events.OnBannerClickListener;
import ss.com.bannerslider.views.BannerSlider;

/**
 * Created by Vigo Telecome on 06-11-2016.
 */
public class MainCategory extends Fragment {

    private RecyclerView mRecyclerView;
    public MainCategoryAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<MainCategoryData> myDataset = new ArrayList<>();




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_category, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        fetchMainCategoryList();
        return view;
    }

    private void fetchMainCategoryList() {

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String REGISTER_URL = Common.serviceUrl +"/ProductCategorySelectAll";
        // final  Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.hotel);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONArray("lstdata");
                            for(int i = 0 ; i<jsonArray.length();i++){
                                JSONObject object1 = jsonArray.getJSONObject(i);

                                MainCategoryData getterSetter3 = new MainCategoryData(
                                        (String) object1.get("CategoryName"),
                                        (String) object1.get("CategoryImagePath"),
                                        (Integer) object1.get("ProductCategory_ID"));
                                myDataset.add(getterSetter3);
                            }

                            mAdapter = new MainCategoryAdapter(getActivity(),myDataset);
                            mRecyclerView.setAdapter(mAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                        Log.d("error", "" + error);
                    }
                }) {

        };
        requestQueue.add(stringRequest);

    }

}
