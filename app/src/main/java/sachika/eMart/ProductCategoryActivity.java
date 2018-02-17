package sachika.eMart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class ProductCategoryActivity extends AppCompatActivity {
Toolbar mToolBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category);

        //get data from intent
        if(getIntent().getSerializableExtra("productCategory")!=null){
            final ProductCategory productCategory = (ProductCategory) getIntent().getSerializableExtra("productCategory");





            //Toolbar
            mToolBar = (Toolbar) findViewById(R.id.tb_main);
            mToolBar.setTitle(productCategory.pro_cat_name);
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
        }



    }
}
