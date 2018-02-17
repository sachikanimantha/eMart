package sachika.eMart;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;

/**
 * Created by Sachika on 12/5/2016.
 */

public class MoreCategoryAdapter extends RecyclerView.Adapter<MoreCategoryAdapter.MoreCategoryAdapterViewHolder> {
    Context context;
    ArrayList<ProductCategory> arrayList;

    public MoreCategoryAdapter(Context context, ArrayList<ProductCategory> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public MoreCategoryAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_for_more_categories,parent,false);
        MoreCategoryAdapterViewHolder moreCatergoryAdapterViewHolder = new MoreCategoryAdapterViewHolder(view);
        return moreCatergoryAdapterViewHolder;
    }

    @Override
    public void onBindViewHolder(MoreCategoryAdapterViewHolder holder, int position) {
        final ProductCategory productCategory = arrayList.get(position);
        holder.tvName.setText(productCategory.pro_cat_name);

        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,SubCategoryActivity.class);
                intent.putExtra("product_category",productCategory);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.ivIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,SubCategoryActivity.class);
                intent.putExtra("product_category",productCategory);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        try {
            YoYo.with(Techniques.RotateInUpLeft)
                    .duration(700)
                    .playOn(holder.tvName);
        }catch (Exception e){

        }
    }




    @Override
    public int getItemCount() {
       if(arrayList!=null){
           return  arrayList.size();
       }
        return 0;
    }

    public static class MoreCategoryAdapterViewHolder extends RecyclerView.ViewHolder{

        TextView tvName;
        ImageView ivIcon;

        public MoreCategoryAdapterViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvMoreCategoryName);
            ivIcon = (ImageView) itemView.findViewById(R.id.ivIcon);
        }
    }

}
