package sachika.eMart;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import sachika.eMart.web.HTTPPaths;

/**
 * Created by Sachika on 12/5/2016.
 */

public class AllCategoriesAdapter extends RecyclerView.Adapter<AllCategoriesAdapter.AllCategoryHolder> {
    Context context;
    ArrayList<ProductCategory> arrayList;

    public AllCategoriesAdapter(Context context, ArrayList<ProductCategory> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public AllCategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_for_all_categories,parent,false);
       AllCategoryHolder allCategoryHolder = new AllCategoryHolder(view);
        return allCategoryHolder;
    }

    @Override
    public void onBindViewHolder(AllCategoryHolder holder, int position) {
        final ProductCategory productCategory = arrayList.get(position);
        String url = HTTPPaths.baseUrl+ "img/"+productCategory.pro_cat_img;
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.back)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.ivImage);
        holder.tvName.setText(productCategory.pro_cat_name);

        holder.ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,SubCategoryActivity.class);
                intent.putExtra("product_category",productCategory);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        try {
            YoYo.with(Techniques.Tada)
                    .duration(700)
                    .playOn(holder.cvAllCategory);
        }catch (Exception e){

        }

    }

    @Override
    public int getItemCount() {
        if(arrayList !=null){
            return arrayList.size();
        }
        return 0;
    }

    public static class AllCategoryHolder extends RecyclerView.ViewHolder{
        ImageView ivImage;
        TextView tvName;
        CardView cvAllCategory;
        public AllCategoryHolder(View itemView) {
            super(itemView);
            ivImage =(ImageView) itemView.findViewById(R.id.ivAllCatImage);
            tvName = (TextView) itemView.findViewById(R.id.tvAllCatName);
            cvAllCategory = (CardView) itemView.findViewById(R.id.cvAllCategory);
        }
    }
}
