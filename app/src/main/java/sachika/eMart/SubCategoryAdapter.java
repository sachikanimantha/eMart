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
 * Created by Sachika on 12/6/2016.
 */

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.SubCatViewHolder> {

    private Context context;
    private ArrayList<SubCatergory> arrayList;

    public SubCategoryAdapter(Context context, ArrayList<SubCatergory> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public SubCatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_for_sub_categoris,parent,false);
        SubCatViewHolder subCatViewHolder = new SubCatViewHolder(view);
        return subCatViewHolder;
    }

    @Override
    public void onBindViewHolder(SubCatViewHolder holder, int position) {

        final SubCatergory subCatergory = arrayList.get(position);
        String url = HTTPPaths.baseUrl+ "img/"+subCatergory.sub_cat_img;
        //String url = "http://sampletemp.96.lt/android/img/"+subCatergory.sub_cat_img;
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.back)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.ivSubCatImage);
        holder.tvSubcatName.setText(subCatergory.sub_cat_name);
        holder.ivSubCatImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ProductActivity.class);
                intent.putExtra("subCategory",subCatergory);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        try {
            YoYo.with(Techniques.RotateInUpLeft)
                    .duration(700)
                    .playOn(holder.cvSubCat);
        }catch (Exception e){

        }
    }

    @Override
    public int getItemCount() {
        if(arrayList!=null){
            return arrayList.size();
        }
        return 0;
    }

    public static class SubCatViewHolder extends RecyclerView.ViewHolder{

        CardView cvSubCat;
        TextView tvSubcatName;
        ImageView ivSubCatImage;

        public SubCatViewHolder(View itemView) {
            super(itemView);
            cvSubCat = (CardView) itemView.findViewById(R.id.cvSubCat);
            tvSubcatName = (TextView)itemView.findViewById(R.id.tvSubCatName);
            ivSubCatImage = (ImageView)itemView.findViewById(R.id.ivSubCatImage);
        }
    }
}
