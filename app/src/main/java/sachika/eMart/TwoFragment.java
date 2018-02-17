package sachika.eMart;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kosalgeek.android.json.JsonConverter;

import java.util.ArrayList;

import sachika.eMart.web.HTTPPaths;


/**
 * A simple {@link Fragment} subclass.
 */
public class TwoFragment extends Fragment {
    RecyclerView rvProductsJustForYou;
    public TwoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_two, container, false);
        rvProductsJustForYou =(RecyclerView)view.findViewById(R.id.rvProductsJustForYou);
        LinearLayoutManager layoutManager = new GridLayoutManager(getContext(),2,LinearLayoutManager.VERTICAL,false);
        rvProductsJustForYou.setLayoutManager(layoutManager);
        rvProductsJustForYou.setHasFixedSize(true);

        //set Data to RecyclerView

         String newUrl = HTTPPaths.baseUrl+ "new_all_product.php";
        //String newUrl ="http://sampletemp.96.lt/android/new_all_product.php";
        //=======
        StringRequest stringRequest = new StringRequest(Request.Method.GET, newUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<Product> proCategoryList = new JsonConverter<Product>()
                                .toArrayList(response, Product.class);
                        NewProductAdapter newProductAdapter = new NewProductAdapter(getContext(),proCategoryList);
                        rvProductsJustForYou.setAdapter(newProductAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
        //=======

        return  view;
    }

}
