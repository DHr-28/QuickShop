package androidrock.quickshoapapp.Adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidrock.quickshoapapp.MainActivity;
import androidrock.quickshoapapp.Moodel.MyCartModel;
import androidrock.quickshoapapp.R;
import androidrock.quickshoapapp.helper.Common;
import androidrock.quickshoapapp.helper.MyCart_DB;

/**
 * Created by Vigo Telecome on 28-12-2016.
 */
public class MyCartAdapter  extends RecyclerView.Adapter<MyCartAdapter.ProductRateRowHolder> {
    private List<MyCartModel> myDataset;
    private Context mContext;
    MyCart_DB mycartdb;

    public MyCartAdapter(Context context, List<MyCartModel> myDataset, MyCart_DB mycartdb) {
        this.myDataset = myDataset;
        this.mContext = context;
        this.mycartdb = mycartdb;
    }

    @Override
    public ProductRateRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_cart_single_row, parent, false);

        return new ProductRateRowHolder(itemView);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(ProductRateRowHolder holder, final int position) {
        final MyCartModel objProductRateDetails = myDataset.get(position);


        holder.txtBasicRate.setText(String.valueOf(objProductRateDetails.basicrate));
        holder.txtNumberofQuantity.setText(String.valueOf(objProductRateDetails.numberofqunatity));
        holder.txttotalValue.setText(String.valueOf(objProductRateDetails.totalValue));
        holder.txtWholesellvalue.setText(String.valueOf(objProductRateDetails.totalNetValue));
        holder.txtStoreName.setText(String.valueOf(objProductRateDetails.ShoapMasterName));
        holder.txtBasicRate.setText(String.valueOf(objProductRateDetails.basicrate));

        String imageurl = Common.ImageUrl + objProductRateDetails.ImagePath;
        Picasso.with(this.mContext).load(imageurl).into(holder.cartproductimg);

        holder.removefromcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  holder.
                mycartdb.delete(objProductRateDetails.ProductId,objProductRateDetails.wholesellersotreid);
                ((MainActivity)mContext).gotomycart();
            }
        });
    }
    @Override
    public int getItemCount() {
        return (null != myDataset ? myDataset.size() : 0);
    }



    public class ProductRateRowHolder extends RecyclerView.ViewHolder {

        public ImageView cartproductimg;
        public TextView txtBasicRate;
        public TextView txtNumberofQuantity;
        public TextView txttotalValue;
        public TextView txtWholesellvalue;
        public TextView txtStoreName;
        public TextView removefromcart;

        public ProductRateRowHolder(View itemView) {
            super(itemView);
            txtBasicRate = (TextView)itemView.findViewById(R.id.txtBasicRate);
            txtNumberofQuantity = (TextView)itemView.findViewById(R.id.txtNumberofQuantity);
            txttotalValue = (TextView)itemView.findViewById(R.id.txttotalValue);
            txtWholesellvalue = (TextView)itemView.findViewById(R.id.txtWholesellvalue);
            txtStoreName = (TextView)itemView.findViewById(R.id.txtStoreName);
            cartproductimg = (ImageView)itemView.findViewById(R.id.cartproductimg);
            removefromcart = (TextView)itemView.findViewById(R.id.removefromcart);
        }
    }
}
