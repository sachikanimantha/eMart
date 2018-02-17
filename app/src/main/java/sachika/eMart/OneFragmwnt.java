package sachika.eMart;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

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
public class OneFragmwnt extends Fragment {
    //RecyclerViews
    RecyclerView rvCat,rvProductCategory,rvNewArrivals;

    //More TextViews
    TextView tvMoreCategory,tvMoreNewArrivals;

    //SlideShoe
    WebView wvSlideShow;
    public OneFragmwnt() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one_fragmwnt, container, false);
        rvCat =(RecyclerView)view.findViewById(R.id.rvCat);

        //slide show
        wvSlideShow = (WebView)view.findViewById(R.id.wvSlideShow);
        wvSlideShow.getSettings().setLoadsImagesAutomatically(true);
        wvSlideShow.getSettings().setJavaScriptEnabled(true);
        wvSlideShow.setScrollBarStyle(view.SCROLLBARS_INSIDE_OVERLAY);
        wvSlideShow.loadUrl(HTTPPaths.siteUrl+"slide1.html");


        //LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL,
                false
        );

        /*LinearLayoutManager layoutManager = new GridLayoutManager(this
                3,
                LinearLayoutManager.HORIZONTAL,
                true
        );*/

/*
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(
                3,
                LinearLayoutManager.HORIZONTAL
        );
*/

//      CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL);
//        CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.VERTICAL);

        rvCat.setLayoutManager(layoutManager);
        rvCat.setHasFixedSize(true);
        String url =HTTPPaths.baseUrl+ "category.php";
        //String url = "http://sampletemp.96.lt/android/category.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<Category> categoryList = new JsonConverter<Category>()
                                .toArrayList(response, Category.class);
                        CategoryAdapter adapter = new CategoryAdapter(getContext(),categoryList);
                        rvCat.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error!=null){
                            //Log.d("MainActivity",error.getMessage());
                            Toast.makeText(getContext(), "Something went wrong, Check your connection", Toast.LENGTH_LONG).show();
                        }

                    }
                }
        );
        MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);


        //============== Categories You'll Love RecyclerView ================================================================

        rvProductCategory =(RecyclerView)view.findViewById(R.id.rvProductCategory);
        LinearLayoutManager proCatLayoutManager = new GridLayoutManager(getContext(),
                3,
                LinearLayoutManager.VERTICAL,
                false
        );
        /*LinearLayoutManager proCatLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL,
                true
        );*/

        rvProductCategory.setLayoutManager(proCatLayoutManager);
        rvProductCategory.setHasFixedSize(true);

        String proUrl =HTTPPaths.baseUrl+ "category_product.php";
        //String proUrl ="http://sampletemp.96.lt/android/category_product.php";

        StringRequest proCatStringRequest = new StringRequest(Request.Method.GET, proUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<ProductCategory> proCategoryList = new JsonConverter<ProductCategory>()
                                .toArrayList(response, ProductCategory.class);
                       ProductCategoryAdapter productCategoryAdapter = new ProductCategoryAdapter(getContext(),proCategoryList);
                       rvProductCategory.setAdapter(productCategoryAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        MySingleton.getInstance(getContext()).addToRequestQueue(proCatStringRequest);
        tvMoreCategory =(TextView)view.findViewById(R.id.tvMoreCategory);
        tvMoreCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),CategoryAvtivity.class);
                getActivity().startActivity(intent);
            }
        });

        tvMoreNewArrivals=(TextView)view.findViewById(R.id.tvMoreNewArrivals);
        tvMoreNewArrivals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),NewProducts.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });


        //==============New Arrivals RecyclerView ================================================================

        rvNewArrivals =(RecyclerView)view.findViewById(R.id.rvNewArrivals);
        LinearLayoutManager newArrivals = new LinearLayoutManager(getContext(),

                LinearLayoutManager.HORIZONTAL,
                false
        );
        /*LinearLayoutManager proCatLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL,
                true
        );*/




        rvNewArrivals.setLayoutManager(newArrivals);
        rvNewArrivals.setHasFixedSize(true);

          String newUrl =HTTPPaths.baseUrl+ "new_product.php";
        // String newUrl ="http://sampletemp.96.lt/android/new_product.php";

        StringRequest newCatStringRequest = new StringRequest(Request.Method.GET, newUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<Product> proCategoryList = new JsonConverter<Product>()
                                .toArrayList(response, Product.class);
                        NewProductAdapter newProductAdapter = new NewProductAdapter(getContext(),proCategoryList);
                        rvNewArrivals.setAdapter(newProductAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        MySingleton.getInstance(getContext()).addToRequestQueue(newCatStringRequest);
        return view;
    }


}
