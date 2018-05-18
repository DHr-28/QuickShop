package androidrock.quickshoapapp.Adapter;


import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidrock.quickshoapapp.MainActivity;
import androidrock.quickshoapapp.Moodel.MainCategoryData;
import androidrock.quickshoapapp.R;
import androidrock.quickshoapapp.helper.Common;

/**
 * Created by Vigo Telecome on 07-10-2016.
 */
public class MainCategoryAdapter extends RecyclerView.Adapter<MainCategoryAdapter.MainCategoryRowHolder> {
    private ArrayList<MainCategoryData> myDataset=null;
    private ArrayList<MainCategoryData> filteredData = null;
    private Context mContext;
    private ItemFilter mFilter = new ItemFilter();

    public MainCategoryAdapter(Context context, ArrayList<MainCategoryData> myDataset) {
        this.myDataset = myDataset;
        this.filteredData = myDataset ;
        this.mContext = context;
    }

    @Override
    public MainCategoryRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_category_single_row, parent, false);

        return new MainCategoryRowHolder(itemView);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(MainCategoryRowHolder holder, final int position) {
        final MainCategoryData mainCategoryData = filteredData.get(position);
        String imageurl = Common.ImageUrl + mainCategoryData.CategoryImagePath;
        Picasso.with(this.mContext).load(imageurl).into(holder.imgMainCategory);
        holder.txtMainCategory.setText(mainCategoryData.CategoryName);
        holder.imgMainCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) mContext).gotoSubProductList(mainCategoryData.ProductCategory_ID);
            }
        });

        holder.txtMainCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)mContext).gotoSubProductList(mainCategoryData.ProductCategory_ID);
            }
        });
    }
    @Override
    public int getItemCount() {
        return filteredData.size();
    }

    public class MainCategoryRowHolder extends RecyclerView.ViewHolder {

        public ImageView imgMainCategory;
        public TextView txtMainCategory;

        public MainCategoryRowHolder(View itemView) {
            super(itemView);
            imgMainCategory = (ImageView) itemView.findViewById(R.id.imgMainCategory);
            txtMainCategory = (TextView)itemView.findViewById(R.id.txtMainCategory);
        }
    }

    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final ArrayList<MainCategoryData> list = myDataset;

            int count = list.size();
            final ArrayList<MainCategoryData> nlist = new ArrayList<MainCategoryData>(count);

            MainCategoryData filterableString ;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i);
                if (filterableString.CategoryName.toLowerCase().contains(filterString)) {
                    nlist.add(filterableString);
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData = (ArrayList<MainCategoryData>) results.values;
            notifyDataSetChanged();
        }

    }


}
