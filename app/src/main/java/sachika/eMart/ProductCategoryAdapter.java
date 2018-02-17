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
 * Created by Sachika on 11/19/2016.
 */

public class ProductCategoryAdapter extends RecyclerView.Adapter<ProductCategoryAdapter.MyViewHolder> {
    Context context;
    ArrayList<ProductCategory> arrayList;

    public ProductCategoryAdapter(Context context, ArrayList<ProductCategory> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_for_product_category,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final ProductCategory productCategory = arrayList.get(position);
         String url = HTTPPaths.baseUrl+ "img/"+productCategory.pro_cat_img;
        System.out.println("Image Url: "+url);
        // String url = "http://sampletemp.96.lt/android/img/"+productCategory.pro_cat_img;
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.back)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.ivProductCategory);
        holder.tvProductCategory.setText(productCategory.pro_cat_name);
        holder.ivProductCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(context, productCategory.pro_cat_name +" "+ productCategory.pro_cat_img, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,SubCategoryActivity.class);
                intent.putExtra("product_category",productCategory);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        try {
            YoYo.with(Techniques.FlipInX)
                    .duration(700)
                    .playOn(holder.cvProductCategory);
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

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CardView cvProductCategory;
        TextView tvProductCategory;
        ImageView ivProductCategory;
        public MyViewHolder(View itemView) {
            super(itemView);
            cvProductCategory = (CardView) itemView.findViewById(R.id.cvCategoryProduct);
            tvProductCategory= (TextView) itemView.findViewById(R.id.tvCategoryProduct);
            ivProductCategory= (ImageView) itemView.findViewById(R.id.ivCategoryProduct);
        }

    }
}
