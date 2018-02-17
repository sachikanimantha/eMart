package sachika.eMart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kosalgeek.android.json.JsonConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sachika.eMart.web.HTTPPaths;

public class ProductActivity extends AppCompatActivity {
    //Toolbar
    private Toolbar mToolBar;

    //Recycler View
    RecyclerView rvProduct;

    SubCatergory subCatergory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        //get data from intent
        if(getIntent().getSerializableExtra("subCategory")!=null){
            subCatergory  = (SubCatergory) getIntent().getSerializableExtra("subCategory");

            //Toolbar
            mToolBar = (Toolbar) findViewById(R.id.tb_main);
            mToolBar.setTitle(subCatergory.sub_cat_name);
            //mToolBar.setSubtitle("Explore Yourself...");

            //set the back arrow in the toolbar
            setSupportActionBar(mToolBar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(false);

            mToolBar.inflateMenu(R.menu.menu_main);
            mToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    int id = item.getItemId();

                /*switch (id){
                    case R.id.cart:
                        startActivity(intent);
                        break;
                    case  R.id.category:
                        CarFragment fragment = (CarFragment) getSupportFragmentManager().findFragmentByTag("MainFrag");
                        if(fragment==null){
                            fragment = new CarFragment();
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.rl_fragment_container,fragment,"MainFrag");
                            ft.commit();
                        }
                        break;

                }*/
                    return true;
                }
            });
        }//if

        //Product Recycler View
        rvProduct = (RecyclerView)findViewById(R.id.rvProduct);
        LinearLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2,LinearLayoutManager.VERTICAL,false);
       /* StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(
                2,
                LinearLayoutManager.VERTICAL
        );*/
        rvProduct.setLayoutManager(layoutManager);
        rvProduct.setHasFixedSize(true);

         String url = HTTPPaths.baseUrl+ "product.php";
        // String url = "http://sampletemp.96.lt/android/product.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<Product> arrayList = new JsonConverter<Product>().toArrayList(response,Product.class);
                        ProductAdapter productAdapter = new ProductAdapter(getApplicationContext(),arrayList);
                        rvProduct.setAdapter(productAdapter);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ProductActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("sub_cat_id",subCatergory.sub_cat_id+"");
                return params;
            }
        };

    MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);


    }//main OnCreate
}
