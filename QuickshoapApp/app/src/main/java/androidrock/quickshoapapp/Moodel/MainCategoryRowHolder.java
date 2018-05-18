package androidrock.quickshoapapp.Moodel;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidrock.quickshoapapp.R;

public class MainCategoryRowHolder extends RecyclerView.ViewHolder {

    public ImageView imgMainCategory;
    public TextView txtMainCategory;

    public MainCategoryRowHolder(View itemView) {
        super(itemView);
        imgMainCategory = (ImageView) itemView.findViewById(R.id.imgMainCategory);
        txtMainCategory = (TextView)itemView.findViewById(R.id.txtMainCategory);
    }
}
