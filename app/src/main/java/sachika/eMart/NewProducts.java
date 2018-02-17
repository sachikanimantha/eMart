package sachika.eMart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.zxing.integration.android.IntentIntegrator;
import com.kosalgeek.android.json.JsonConverter;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

import java.util.ArrayList;

import sachika.eMart.web.HTTPPaths;

public class NewProducts extends AppCompatActivity {
    //Toolbar
    private Toolbar mToolBar;

    //Navigation Drawer
    private AccountHeader headerResult = null;
    private Drawer result = null;
    private static final int PROFILE_SETTING = 1;

    RecyclerView rvNewProducts;


    //SharedPreferences
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_products);

        //SharedPreferences
        pref = getSharedPreferences("login.conf", Context.MODE_PRIVATE);
        editor =pref.edit();
        final int id = pref.getInt("user_id",-1);

        //Toolbar
        mToolBar = (Toolbar) findViewById(R.id.tb_main);
        mToolBar.setTitle("New Arrivals");

        //set the back arrow in the toolbar
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);

        mToolBar.inflateMenu(R.menu.menu_main);
        mToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                switch (id){
                    case R.id.cart:
                        Intent intent = new Intent(getApplicationContext(),MyCartActivity.class);
                        startActivity(intent);
                        break;

                    case  R.id.category:
                        IntentIntegrator intentIntegrator = new IntentIntegrator(NewProducts.this);
                        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                        intentIntegrator.setPrompt("Scan QR");
                        intentIntegrator.setCameraId(0);
                        intentIntegrator.setBeepEnabled(true);
                        intentIntegrator.setBarcodeImageEnabled(false);
                        intentIntegrator.initiateScan();
                        break;

                }
                return true;
            }
        });

        //=========Navigation Drawer===========================================
        //Drawer Header
        // Create a few sample profile

        final IProfile profile = new ProfileDrawerItem().withName("Sachika Nimantha").withEmail("sachi2011ac@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile));
        final IProfile profile2 = new ProfileDrawerItem().withName("Dilhara Jayathissa").withEmail("dilhara@gmail.com").withIcon(Uri.parse("https://avatars3.githubusercontent.com/u/887462?v=3&s=460"));
        final IProfile profile3 = new ProfileDrawerItem().withName("Ironman Muster").withEmail("ironman@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile2));
        final IProfile profile4 = new ProfileDrawerItem().withName("Superman House").withEmail("superman.house@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile3));
        final IProfile profile5 = new ProfileDrawerItem().withName("Mr. Xxx").withEmail("mister.xxx@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile4)).withIdentifier(4);
        final IProfile profile6 = new ProfileDrawerItem().withName("Batman").withEmail("batman@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile5));

        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .withTranslucentStatusBar(false)
                .addProfiles(
                        profile,
                        profile2,
                        profile3,
                        profile4,
                        profile5,
                        profile6,

                        new ProfileSettingDrawerItem().withName("Add Account").withDescription("Add new eMart Account").withIcon(GoogleMaterial.Icon.gmd_plus_one).withIdentifier(PROFILE_SETTING),
                        new ProfileSettingDrawerItem().withName("Manage Account").withIcon(GoogleMaterial.Icon.gmd_settings)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        //sample usage of the onProfileChanged listener
                        //if the clicked item has the identifier 1 add a new profile ;)
                        if (profile instanceof IDrawerItem && ((IDrawerItem) profile).getIdentifier() == PROFILE_SETTING) {
                            IProfile newProfile = new ProfileDrawerItem().withNameShown(true).withName("Batman").withEmail("batman@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile5));
                            if (headerResult.getProfiles() != null) {
                                //we know that there are 2 setting elements. set the new profile above them ;)
                                headerResult.addProfile(newProfile, headerResult.getProfiles().size() - 2);
                            } else {
                                headerResult.addProfiles(newProfile);
                            }
                        }

                        //false if you have not consumed the event and it should close the drawer
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();

        //Drawer
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolBar)
                .withDisplayBelowStatusBar(false)
                .withActionBarDrawerToggleAnimated(true)
                .withDrawerGravity(Gravity.LEFT)
                .withSavedInstance(savedInstanceState)
                .withSelectedItem(0)
                .withTranslucentStatusBar(false)
                .withAccountHeader(headerResult) //set the AccountHeader  created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Home").withIcon(GoogleMaterial.Icon.gmd_home).withIdentifier(1).withSelectedColor(3),
                        new PrimaryDrawerItem().withName("My Account").withIcon(GoogleMaterial.Icon.gmd_account_box).withIdentifier(2).withSelectedColor(2),
                        new PrimaryDrawerItem().withName("Shopping Cart").withIcon(FontAwesome.Icon.faw_shopping_cart).withIdentifier(3).withBadge("5").withBadgeStyle(new BadgeStyle(Color.RED, Color.RED)),
                        new PrimaryDrawerItem().withName("My Orders").withIcon(GoogleMaterial.Icon.gmd_list).withIdentifier(4),
                        new PrimaryDrawerItem().withName("Messages").withIcon(GoogleMaterial.Icon.gmd_message).withIdentifier(5).withBadge("2").withBadgeStyle(new BadgeStyle(Color.RED, Color.RED)),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withDescription("Explore Yourself...").withName("eMart App").withIdentifier(6),
                        new SecondaryDrawerItem().withName("Settings").withIcon(GoogleMaterial.Icon.gmd_settings),
                        new SecondaryDrawerItem().withName("About Us").withIcon(GoogleMaterial.Icon.gmd_apps).withTag("Bullhorn"),
                        new SecondaryDrawerItem().withName("Logout").withIcon(GoogleMaterial.Icon.gmd_exit_to_app)
                ) // add the items we want to use with our Drawer
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem instanceof Nameable) {
                            Toast.makeText(NewProducts.this, ((Nameable) drawerItem).getName().getText(NewProducts.this), Toast.LENGTH_SHORT).show();
                        }

                        if (((Nameable) drawerItem).getName().getText(NewProducts.this)=="Shopping Cart"){
                            Intent intent = new Intent(getApplicationContext(),MyCartActivity.class);
                            startActivity(intent);
                        }

                        if (((Nameable) drawerItem).getName().getText(NewProducts.this)=="Home"){
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }

                        if (((Nameable) drawerItem).getName().getText(NewProducts.this)=="My Account"){
                            Intent intent = new Intent(getApplicationContext(),MyAccountActivity.class);
                            startActivity(intent);

                        }

                        if (((Nameable) drawerItem).getName().getText(NewProducts.this)=="Messages"){
                            Intent intent = new Intent(getApplicationContext(),SignInActivity.class);
                            startActivity(intent);
                        }
                        if (((Nameable) drawerItem).getName().getText(NewProducts.this)=="My Orders"){
                            Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
                            startActivity(intent);
                        }
                        if (((Nameable) drawerItem).getName().getText(NewProducts.this)=="Settings"){
                            Intent intent = new Intent(getApplicationContext(),WelcomeActivity.class);
                            startActivity(intent);
                        }
                        if (((Nameable) drawerItem).getName().getText(NewProducts.this)=="Logout"){
                            //pref.edit().clear().commit();
                            editor = pref.edit();
                            editor.clear();
                            editor.commit();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                        return false;
                    }
                })
                .build();

        //set Data to RecyclerView

        String newUrl = HTTPPaths.baseUrl+ "new_all_product.php";
        //   String newUrl ="http://sampletemp.96.lt/android/new_all_product.php";

                rvNewProducts = (RecyclerView)findViewById(R.id.rvNewProducts);
                LinearLayoutManager layoutManager = new GridLayoutManager(NewProducts.this,2,LinearLayoutManager.VERTICAL,false);
                rvNewProducts.setLayoutManager(layoutManager);
                rvNewProducts.setHasFixedSize(true);

                //=======
                StringRequest stringRequest = new StringRequest(Request.Method.GET, newUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                ArrayList<Product> proCategoryList = new JsonConverter<Product>()
                                        .toArrayList(response, Product.class);
                                NewProductAdapter newProductAdapter = new NewProductAdapter(NewProducts.this,proCategoryList);
                                rvNewProducts.setAdapter(newProductAdapter);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });

                MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
                //=======



    }//end onCreate
}
