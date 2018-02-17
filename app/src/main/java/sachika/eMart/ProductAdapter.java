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
 * Created by Sachika on 12/8/2016.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductAdapterViewHolder> {

    private Context context;
    private ArrayList<Product> arrayList;

    public ProductAdapter(Context context, ArrayList<Product> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public ProductAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_for_products,parent,false);
        ProductAdapterViewHolder productAdapterViewHolder = new ProductAdapterViewHolder(view);
        return productAdapterViewHolder;
    }

    @Override
    public void onBindViewHolder(ProductAdapterViewHolder holder, int position) {
        final Product product = arrayList.get(position);
        String url = HTTPPaths.baseUrl+ "img/"+product.pimg;
        // String url = "http://sampletemp.96.lt/android/img/"+product.pimg;
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.back)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.ivProductImage);
        holder.tvProductName.setText(product.pname);
        holder.tvProductPrice.setText("Rs. "+String.format("%.2f",product.price));

        holder.ivProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ProductDetails.class);
                intent.putExtra("productArray",product);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        try {
            YoYo.with(Techniques.FlipInX)
                    .duration(700)
                    .playOn(holder.cvProduct);
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

    public static  class  ProductAdapterViewHolder extends RecyclerView.ViewHolder{

        ImageView ivProductImage;
        TextView tvProductName,tvProductPrice;
        CardView cvProduct;

        public ProductAdapterViewHolder(View itemView) {
            super(itemView);
            cvProduct = (CardView) itemView.findViewById(R.id.cvProduct);
            ivProductImage = (ImageView)itemView.findViewById(R.id.ivProductImage);
            tvProductName = (TextView)itemView.findViewById(R.id.tvProductName);
            tvProductPrice = (TextView)itemView.findViewById(R.id.tvProductPrice);

        }
    }

}
