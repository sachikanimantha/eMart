package sachika.eMart;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import sachika.eMart.web.HTTPPaths;

/**
 * Created by Sachika on 11/17/2016.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

   private Context context;
    ArrayList<Category> categoryList;

    public CategoryAdapter(Context context, ArrayList<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_for_category,parent,false);
        CategoryViewHolder categoryViewHolder = new CategoryViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {

        final Category category = categoryList.get(position);
         String url = HTTPPaths.baseUrl+ "img/"+category.cat_img;
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.all)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.ivImage);
        holder.ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (category.cat_name.equals("All Categories")){
                    Intent intent = new Intent(context,CategoryAvtivity.class);
                    intent.putExtra("category",category);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                   /* //Transition
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                       *//* Explode trans1= new Explode();
                        trans1.setDuration(3000);
                        Fade trans2 = new Fade();
                        trans2.setDuration(3000);

                        getWindow().setEnterTransition(trans1);
                        getWindow().setReturnTransition(trans2);*//*
                        *//*ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, null);
                        context.startActivity(intent,activityOptions.toBundle());*//*
                    }
                    else {
                        context.startActivity(intent);
                    }*/

                }


            }
        });

        holder.tvName.setText(category.cat_name);
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (category.cat_name.equals("All Categories")){
                    Intent intent = new Intent(context,CategoryAvtivity.class);
                    intent.putExtra("category",category);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if(categoryList != null){
            return categoryList.size();
        }
        return 0;
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder{
        ImageView ivImage;
        TextView tvName;
        public CategoryViewHolder(View itemView) {
            super(itemView);
            ivImage = (ImageView)itemView.findViewById(R.id.ivAllCatImage);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
        }
    }
}
