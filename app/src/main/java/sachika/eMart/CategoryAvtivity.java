package sachika.eMart;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Fade;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kosalgeek.android.json.JsonConverter;

import java.util.ArrayList;

import sachika.eMart.web.HTTPPaths;

public class CategoryAvtivity extends AppCompatActivity {
    //Toolbar
    private Toolbar mToolBar;

    ImageView ivImage;
    TextView tvName;

    RecyclerView rvAllCategories,rvMoreCategories;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Transition
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            Explode trans1= new Explode();
            trans1.setDuration(3000);
            Fade trans2 = new Fade();
            trans2.setDuration(3000);

            getWindow().setEnterTransition(trans1);
            getWindow().setReturnTransition(trans2);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_avtivity);
        ivImage = (ImageView)findViewById(R.id.ivAllCatImage);
        tvName = (TextView) findViewById(R.id.tvName);



    //get data from intent
        if(getIntent().getSerializableExtra("category")!=null){
            Category category = (Category) getIntent().getSerializableExtra("category");
        }//end intent if
            //Toolbar
            mToolBar = (Toolbar) findViewById(R.id.tb_main);


            mToolBar.setTitle("All Categories");






            //set the back arrow in the toolbar
            setSupportActionBar(mToolBar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(false);

            //mToolBar.setSubtitle("Explore Yourself...");


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





        // ==============All Product Category RecyclerView ================================================================

        rvAllCategories =(RecyclerView)findViewById(R.id.rvAllCategories);
        LinearLayoutManager proCatLayoutManager = new GridLayoutManager(getApplicationContext(),
                2,
                LinearLayoutManager.VERTICAL,
                false
        );
        /*LinearLayoutManager proCatLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL,
                true
        );*/




        rvAllCategories.setLayoutManager(proCatLayoutManager);
        rvAllCategories.setHasFixedSize(true);

        String proUrl = HTTPPaths.baseUrl+ "all_category_product.php";
        //  String proUrl ="http://sampletemp.96.lt/android/all_category_product.php";

        StringRequest proCatStringRequest = new StringRequest(Request.Method.GET, proUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<ProductCategory> proCategoryList = new JsonConverter<ProductCategory>()
                                .toArrayList(response, ProductCategory.class);
                        AllCategoriesAdapter allCategoriesAdapter = new AllCategoriesAdapter(getApplicationContext(),proCategoryList);
                        rvAllCategories.setAdapter(allCategoriesAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(proCatStringRequest);

        //More Category
        rvMoreCategories =(RecyclerView)findViewById(R.id.rvMoreCategories);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
       /*LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL,
                true
        );*/
        rvMoreCategories.setLayoutManager(layoutManager);

        String url = HTTPPaths.baseUrl+ "more_category.php";
        //String url = "http://sampletemp.96.lt/android/more_category.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<ProductCategory> proCatArray = new JsonConverter<ProductCategory>()
                                .toArrayList(response,ProductCategory.class);
                        MoreCategoryAdapter moreCategoryAdapter = new MoreCategoryAdapter(getApplicationContext(),proCatArray);
                        rvMoreCategories.setAdapter(moreCategoryAdapter);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);



    }
}
