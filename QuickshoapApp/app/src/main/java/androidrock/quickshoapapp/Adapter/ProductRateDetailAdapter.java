package androidrock.quickshoapapp.Adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import androidrock.quickshoapapp.Moodel.ProductRateDetails;
import androidrock.quickshoapapp.ProdctWholesellRateDetails;
import androidrock.quickshoapapp.R;

/**
 * Created by Vigo Telecome on 22-12-2016.
 */
public class ProductRateDetailAdapter extends RecyclerView.Adapter<ProductRateDetailAdapter.ProductRateRowHolder> {
    private List<ProductRateDetails> myDataset;
    private Context mContext;

    public ProductRateDetailAdapter(Context context, List<ProductRateDetails> myDataset) {
        this.myDataset = myDataset;
        this.mContext = context;
    }

    @Override
    public ProductRateRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_rate_signle_row, parent, false);

        return new ProductRateRowHolder(itemView);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(ProductRateRowHolder holder, final int position) {
        final ProductRateDetails objProductRateDetails = myDataset.get(position);



        holder.productcontactemail.setText(objProductRateDetails.Email);
        holder.productRate.setText(String.valueOf(objProductRateDetails.Rate));
        holder.productcontactemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "" + position, Toast.LENGTH_LONG);

                Intent myIntent = new Intent(mContext, ProdctWholesellRateDetails.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                myIntent.putExtra("productid", objProductRateDetails.SubProductCategoryId);
                myIntent.putExtra("storeid", objProductRateDetails.Userid);
                mContext.startActivity(myIntent);
                /*((MainActivity)mContext).gotoProductWholesellRateDetailsbyproductidstoreid
                        (objProductRateDetails.SubProductCategoryId,objProductRateDetails.Userid);*/
            }
        });
    }
    @Override
    public int getItemCount() {
        return (null != myDataset ? myDataset.size() : 0);
    }



    public class ProductRateRowHolder extends RecyclerView.ViewHolder {

        public TextView productRate;
        public TextView productcontactemail;

        public ProductRateRowHolder(View itemView) {
            super(itemView);
                productcontactemail = (TextView) itemView.findViewById(R.id.productcontactemail);
                productRate = (TextView) itemView.findViewById(R.id.productRate);
        }
    }
}
