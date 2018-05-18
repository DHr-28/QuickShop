package androidrock.quickshoapapp.Adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

import androidrock.quickshoapapp.Moodel.OrderHistoryModel;
import androidrock.quickshoapapp.R;
import androidrock.quickshoapapp.helper.Common;
import androidrock.quickshoapapp.helper.MyCart_DB;

/**
 * Created by Vigo Telecome on 01-01-2017.
 */
public class OrderHistoryAdepter extends RecyclerView.Adapter<OrderHistoryAdepter.ProductRateRowHolder> {
    private List<OrderHistoryModel> myDataset;
    private Context mContext;
    MyCart_DB mycartdb;

    public OrderHistoryAdepter(Context context, List<OrderHistoryModel> myDataset) {
        this.myDataset = myDataset;
        this.mContext = context;
    }

    @Override
    public ProductRateRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_history_single_row, parent, false);

        return new ProductRateRowHolder(itemView);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(ProductRateRowHolder holder, final int position) {
        final OrderHistoryModel objOrderHistoryModel = myDataset.get(position);


        holder.OrderProductName.setText(String.valueOf(objOrderHistoryModel.SubCategoryName));
        holder.OrderStatus.setText(String.valueOf(objOrderHistoryModel.Status));
        holder.ShoapMasterName.setText(String.valueOf(objOrderHistoryModel.ShoapMasterName));
        holder.totalNetValue.setText(String.valueOf(objOrderHistoryModel.totalNetValue));

        String imageurl = Common.ImageUrl + objOrderHistoryModel.ImagePath;
        Picasso.with(this.mContext).load(imageurl).into(holder.orderproductimg);

    }
    @Override
    public int getItemCount() {
        return (null != myDataset ? myDataset.size() : 0);
    }



    public class ProductRateRowHolder extends RecyclerView.ViewHolder {

        public ImageView orderproductimg;
        public TextView OrderProductName;
        public TextView OrderStatus;
        public TextView ShoapMasterName;
        public TextView totalNetValue;

        public ProductRateRowHolder(View itemView) {
            super(itemView);
            OrderProductName = (TextView)itemView.findViewById(R.id.OrderProductName);
            OrderStatus = (TextView)itemView.findViewById(R.id.OrderStatus);
            //OrderDate = (TextView) itemView.findViewById(R.id.OrderDate);
            ShoapMasterName = (TextView)itemView.findViewById(R.id.ShoapMasterName);
            totalNetValue = (TextView)itemView.findViewById(R.id.totalNetValue);
            orderproductimg = (ImageView)itemView.findViewById(R.id.orderproductimg);
        }
    }
}

