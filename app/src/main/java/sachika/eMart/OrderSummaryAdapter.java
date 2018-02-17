package sachika.eMart;

import android.content.Context;
import android.support.v7.widget.CardView;
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
 * Created by Sachika on 12/27/2016.
 */

public class OrderSummaryAdapter extends RecyclerView.Adapter<OrderSummaryAdapter.OrderSummaryViewHolder> {

    Context context;
    ArrayList<OrderSummary> arrayList;

    public OrderSummaryAdapter(Context context, ArrayList<OrderSummary> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public OrderSummaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =LayoutInflater.from(parent.getContext());
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_for_order_summary,parent,false);
        OrderSummaryViewHolder orderSummaryViewHolder = new OrderSummaryViewHolder(view);
        return orderSummaryViewHolder;

    }

    @Override
    public void onBindViewHolder(OrderSummaryViewHolder holder, int position) {
        OrderSummary orderSummary = arrayList.get(position);
            String url = HTTPPaths.baseUrl+ "img/"+orderSummary.pimg;
            //String url = "http://sampletemp.96.lt/android/img/"+orderSummary.pimg;
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.back)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.ivOrderProductImage);
        holder.tvOrderName.setText(orderSummary.pname);
        holder.tvOrderQty.setText(orderSummary.cart_qty+"");
        holder.tvOrderPrice.setText(String.format("Rs.%.2f",orderSummary.price));
        holder.tvSubTotalPrice.setText(String.format("Rs.%.2f",orderSummary.total));

    }

    @Override
    public int getItemCount() {
        if(arrayList!=null){
            return arrayList.size();
        }
        return 0;
    }

    public static class OrderSummaryViewHolder extends RecyclerView.ViewHolder{

        CardView cvOrderItem,cvOrderProductImage;
        ImageView ivOrderProductImage;
        TextView tvOrderName,tvOrderPrice,tvOrderQty,tvSubTotal,tvSubTotalPrice;

        public OrderSummaryViewHolder(View itemView) {
            super(itemView);
            cvOrderItem = (CardView)itemView.findViewById(R.id.cvOrderItem);
            ivOrderProductImage = (ImageView)itemView.findViewById(R.id.ivOrderProductImage);
            cvOrderProductImage = (CardView)itemView.findViewById(R.id.cvOrderProductImage);
            tvOrderName = (TextView)itemView.findViewById(R.id.tvOrderName);
            tvOrderPrice = (TextView)itemView.findViewById(R.id.tvOrderPrice);
            tvOrderQty = (TextView)itemView.findViewById(R.id.tvOrderQty);
            tvSubTotal = (TextView)itemView.findViewById(R.id.tvSubTotal);
            tvSubTotalPrice = (TextView)itemView.findViewById(R.id.tvSubTotalPrice);
        }
    }

}
