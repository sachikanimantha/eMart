package sachika.eMart;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sachika on 12/27/2016.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewholder> {

    Context context;
    ArrayList<Order> arrayList;

    public OrderAdapter(Context context, ArrayList<Order> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public OrderViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_for_orders,parent,false);
        OrderViewholder orderViewholder = new OrderViewholder(view);
        return orderViewholder;
    }

    @Override
    public void onBindViewHolder(OrderViewholder holder, int position) {

        final Order order = arrayList.get(position);
        holder.tvDate.setText(order.o_date);
        holder.tvAmountPrice.setText(""+String.format("Rs.%.2f",order.amount));
        holder.tvViewProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,OrderProductsActivity.class);
                intent.putExtra("order",order);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(arrayList != null){
            return arrayList.size();
        }
        return 0;
    }

    public static class OrderViewholder extends RecyclerView.ViewHolder{

        CardView cvOrders;
        TextView tvAmount,tvDate,tvAmountPrice,tvViewProducts,tvDateName;

        public OrderViewholder(View itemView) {
            super(itemView);
            cvOrders = (CardView)itemView.findViewById(R.id.cvOrders);
            tvAmount = (TextView)itemView.findViewById(R.id.tvAmount);
            tvDate = (TextView)itemView.findViewById(R.id.tvDate);
            tvAmountPrice = (TextView)itemView.findViewById(R.id.tvAmountPrice);
            tvViewProducts = (TextView)itemView.findViewById(R.id.tvViewProducts);
            tvDateName = (TextView)itemView.findViewById(R.id.tvDateName);
        }
    }

}
