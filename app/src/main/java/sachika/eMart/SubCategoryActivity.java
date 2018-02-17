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

public class SubCategoryActivity extends AppCompatActivity {
    Toolbar mToolBar;
    RecyclerView rvSubCategory;
    ProductCategory productCategory;
    String id = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);

        //get data from intent
        if(getIntent().getSerializableExtra("product_category")!=null){
             productCategory = (ProductCategory) getIntent().getSerializableExtra("product_category");
            id= Integer.toString(productCategory.id);
            //Toolbar
            mToolBar = (Toolbar) findViewById(R.id.tb_main);
            mToolBar.setTitle(productCategory.pro_cat_name);
            //mToolBar.setSubtitle("Explore Yourself...");

            //set the back arrow in the toolbar
            setSupportActionBar(mToolBar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);

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

        }//end if

        rvSubCategory = (RecyclerView)findViewById(R.id.rvSubCategory);
        LinearLayoutManager layoutManager = new GridLayoutManager (getApplicationContext(),3,LinearLayoutManager.VERTICAL,false);
        /*StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(
                3,
                LinearLayoutManager.VERTICAL
        );*/
        rvSubCategory.setLayoutManager(layoutManager);
        rvSubCategory.setHasFixedSize(true);
        String url = HTTPPaths.baseUrl+ "sub_category.php";
        //String url = "http://sampletemp.96.lt/android/sub_category.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            ArrayList<SubCatergory> arrayList = new JsonConverter<SubCatergory>().toArrayList(response,SubCatergory.class);
                            SubCategoryAdapter adapter = new SubCategoryAdapter(getApplicationContext(),arrayList);
                            rvSubCategory.setAdapter(adapter);
                        }catch (Exception e){
                            e.printStackTrace();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SubCategoryActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String,String> params = new HashMap<String, String>();
                params.put("cat_id",productCategory.id+"");
                return params;
            }
        };

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);







    }
}
