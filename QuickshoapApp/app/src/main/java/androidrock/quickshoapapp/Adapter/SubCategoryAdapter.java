package androidrock.quickshoapapp.Adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidrock.quickshoapapp.MainActivity;
import androidrock.quickshoapapp.Moodel.MainCategoryData;
import androidrock.quickshoapapp.Moodel.MainCategoryRowHolder;
import androidrock.quickshoapapp.R;
import androidrock.quickshoapapp.helper.Common;

/**
 * Created by Vigo Telecome on 20-12-2016.
 */
public class SubCategoryAdapter extends RecyclerView.Adapter<MainCategoryRowHolder> {
    private List<MainCategoryData> myDataset;
    private List<MainCategoryData> filteredData;
    private Context mContext;
    private ItemFilter mFilter = new ItemFilter();

    public SubCategoryAdapter(Context context, List<MainCategoryData> myDataset) {
        this.myDataset = myDataset;
        this.mContext = context;
    }

    @Override
    public MainCategoryRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_category_single_row, parent, false);

        return new MainCategoryRowHolder(itemView);
    }

    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<MainCategoryData> list = myDataset;

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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(MainCategoryRowHolder holder, final int position) {
        final MainCategoryData mainCategoryData = myDataset.get(position);
        String imageurl = Common.ImageUrl + mainCategoryData.CategoryImagePath;
        Picasso.with(this.mContext).load(imageurl).into(holder.imgMainCategory);
        //holder.imgMainCategory.setBackground(mainCategoryData.getImageCategory());
        holder.txtMainCategory.setText(mainCategoryData.CategoryName);
        holder.imgMainCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "" + position, Toast.LENGTH_LONG);
                ((MainActivity) mContext).gotoProductRateDetails(mainCategoryData.ProductCategory_ID);
            }
        });
    }
    @Override
    public int getItemCount() {
        return (null != myDataset ? myDataset.size() : 0);
    }
}
