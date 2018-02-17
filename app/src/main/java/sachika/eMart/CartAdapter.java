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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sachika.eMart.web.HTTPPaths;

/**
 * Created by Sachika on 12/10/2016.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartAdapterViewHolder>{
    double total ;
    Context context;
    ArrayList<Cart> arrayList;
    TextView tvGranTotalAmount;
    MyCartActivity myCartActivity;
    public CartAdapter(Context context, ArrayList<Cart> arrayList,TextView tvGranTotalAmount) {
        this.context = context;
        myCartActivity = (MyCartActivity)context;
        this.arrayList = arrayList;
        this.tvGranTotalAmount=tvGranTotalAmount;

    }

    @Override
    public CartAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_fo_cart,parent,false);
       CartAdapterViewHolder cartAdapterViewHolder =new CartAdapterViewHolder(view,myCartActivity);
        return cartAdapterViewHolder;
    }

    @Override
    public void onBindViewHolder(CartAdapterViewHolder holder, int position) {
        final Cart cart = arrayList.get(position);
        String url =  HTTPPaths.baseUrl+ "img/"+cart.pimg;

        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.back)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.ivCartProductImage);
        holder.tvCartName.setText(cart.pname);
        holder.tvCartPrice.setText("Rs."+String.format("%.2f",cart.price));
        holder.tvCartQty.setText(""+cart.cart_qty);
        holder.tvSubTotalPrice.setText("Rs."+String.format("%.2f",cart.total));

        if(!myCartActivity.isActionMode){
            holder.cbCartSelect.setVisibility(View.GONE);
        }
        else {
            holder.cbCartSelect.setVisibility(View.VISIBLE);
        }


      /* holder.cbCartSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    total = total +cart.total;
                    //Toast.makeText(context, cart.total+"", Toast.LENGTH_SHORT).show();
                    tvGranTotalAmount.setText("Rs."+String.format("%.2f",total));

                }
                else {
                    total = total -cart.total;
                    tvGranTotalAmount.setText("Rs."+String.format("%.2f",total));
                    //Toast.makeText(context, " ", Toast.LENGTH_SHORT).show();

                }
            }
        });*/





    }

    @Override
    public int getItemCount() {
        if (arrayList!=null){
            return arrayList.size();
        }
        return 0;
    }

    public static class CartAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        MyCartActivity myCartActivity;
        CardView cvCartItem,cvCartProductImage;
        CheckBox cbCartSelect;
        ImageView ivCartProductImage;
        TextView tvCartName,tvCartPrice,tvMinus,tvCartQty,tvPlus,tvSubTotal,tvSubTotalPrice;

        public CartAdapterViewHolder(View itemView,MyCartActivity myCartActivity) {
            super(itemView);
            this.myCartActivity = myCartActivity;
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
            cvCartItem.setOnLongClickListener(myCartActivity);

            cbCartSelect.setOnClickListener(this);//implements viewholder class from View.onClickListener & MyCartActivity prepare Method
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


        }

        @Override
        public void onClick(View view) {
            myCartActivity.prepare(view,getAdapterPosition());
        }

        public  void onQty(){
            myCartActivity.increaseQty(getAdapterPosition());
        }
        public  void onQtyDis(){
            myCartActivity.discreaseQty(getAdapterPosition());
        }

    }

    public void deleteItems(ArrayList<Cart> list){
        for (final Cart cart: list) {


            String url=  HTTPPaths.baseUrl+ "cart_item_delete.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success){
                                    Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(context, "Delete Fail", Toast.LENGTH_SHORT).show();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }



                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }
            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("cart_id",cart.cart_id+"");
                    params.put("pro_id",cart.pid+"");
                    params.put("qty",cart.qty+cart.cart_qty+"");
                    return params;
                }
            };

            MySingleton.getInstance(context).addToRequestQueue(stringRequest);
            arrayList.remove(cart);
        }
        notifyDataSetChanged();
    }


}
