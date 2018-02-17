package sachika.eMart;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import sachika.eMart.web.HTTPPaths;

/**
 * Created by Sachika on 12/14/2016.
 */

public class OrderConfirmationsAdapter extends RecyclerView.Adapter<OrderConfirmationsAdapter.OrderConfirmationViewHolder> {

    ArrayList<Cart> arrayList;
    Context context;
    OrderConfirmation orderConfirmation;

    public OrderConfirmationsAdapter(ArrayList<Cart> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        orderConfirmation = (OrderConfirmation)context;
    }

    @Override
    public OrderConfirmationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_fo_cart,parent,false);
        OrderConfirmationViewHolder orderConfirmationViewHolder = new OrderConfirmationViewHolder(view,orderConfirmation);
        return orderConfirmationViewHolder;
    }

    @Override
    public void onBindViewHolder(OrderConfirmationViewHolder holder, int position) {
        final Cart cart = arrayList.get(position);
        String url = HTTPPaths.baseUrl+ "android/img/"+cart.pimg;
        // String url = "http://sampletemp.96.lt/android/img/"+cart.pimg;

        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.back)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.ivCartProductImage);
        holder.tvCartName.setText(cart.pname);
        holder.tvCartPrice.setText("Rs."+String.format("%.2f",cart.price));
        holder.tvCartQty.setText(""+cart.cart_qty);
        holder.tvSubTotalPrice.setText("Rs."+String.format("%.2f",cart.total));
        holder.cbCartSelect.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        if(arrayList!=null){
            return arrayList.size();
        }
        return 0;
    }

    public static class OrderConfirmationViewHolder extends RecyclerView.ViewHolder{
        OrderConfirmation orderConfirmation;
        CardView cvCartItem,cvCartProductImage;
        CheckBox cbCartSelect;
        ImageView ivCartProductImage;
        TextView tvCartName,tvCartPrice,tvMinus,tvCartQty,tvPlus,tvSubTotal,tvSubTotalPrice;
        public OrderConfirmationViewHolder(View itemView,OrderConfirmation orderConfirmation) {
            super(itemView);
            this.orderConfirmation = orderConfirmation;
            cvCartItem = (CardView)itemView.findViewById(R.id.cvCartItem);
            cvCartProductImage = (CardView)itemView.findViewById(R.id.cvCartProductImage);
            cbCartSelect = (CheckBox)itemView.findViewById(R.id.cbCartSelect);
            ivCartProductImage = (ImageView)itemView.findViewById(R.id.ivCartProductImage);
            tvCartName = (TextView)itemView.findViewById(R.id.tvCartName);
            tvCartPrice = (TextView)itemView.findViewById(R.id.tvCartPrice);
            tvMinus = (TextView)itemView.findViewById(R.id.tvMinus);
            tvCartQty = (TextView)itemView.findViewById(R.id.tvCartQty);
            tvPlus = (TextView)itemView.findViewById(R.id.tvPlus);
            tvSubTotal = (TextView)itemView.findViewById(R.id.tvSubTotal);
            tvSubTotalPrice = (TextView)itemView.findViewById(R.id.tvSubTotalPrice);

            tvPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onQty();
                }
            });

            tvMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onQtyDis();
                }
            });


        }//end View Holder constructor
        public  void onQty(){
            orderConfirmation.increaseQty(getAdapterPosition());
        }
        public  void onQtyDis(){
            orderConfirmation.discreaseQty(getAdapterPosition());
        }

    }//end ViewHolder


}
