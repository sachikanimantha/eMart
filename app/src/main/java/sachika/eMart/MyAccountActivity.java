package sachika.eMart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
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
import com.squareup.picasso.Picasso;

import sachika.eMart.web.HTTPPaths;

public class MyAccountActivity extends AppCompatActivity {
    //Toolbar
    private Toolbar mToolBar;

    //Navigation Drawer
    private AccountHeader headerResult = null;
    private Drawer result = null;
    private static final int PROFILE_SETTING = 1;

    //Views
    TextView tvUserName,tvEmail,tvMyOrders,tvMyProfile,tvShippingAddress;
    ImageView ivProfile,ivIconOrder,ivIconMyProfile,ivIconShippingAddress;

    //SharedPreferences
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        initializeViews();


        //SharedPreferences
        pref = getSharedPreferences("login.conf", Context.MODE_PRIVATE);
        editor =pref.edit();
        final int id = pref.getInt("user_id",-1);
        final String name = pref.getString("name","");
        final String email = pref.getString("email","");
        String pro_pic = pref.getString("pro_pic","");
        String proPicUrl= HTTPPaths.baseUrl+ "img/"+pro_pic;
        // String proPicUrl="http://sampletemp.96.lt/android/img/"+pro_pic;
        Picasso.with(this)
                .load(proPicUrl)
                .placeholder(R.drawable.profile2)
                .error(android.R.drawable.stat_notify_error)
                .into(ivProfile);


        tvUserName.setText(name);
        tvEmail.setText(email);


        //onClickListeners
        tvMyOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MyOrdersActivity.class);
                startActivity(intent);
            }
        });
        ivIconOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MyOrdersActivity.class);
                startActivity(intent);
            }
        });

        tvMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MyProfileActivity.class);
                startActivity(intent);
            }
        });
        ivIconMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MyProfileActivity.class);
                startActivity(intent);
            }
        });

        tvShippingAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ShippingAddressActivity.class);
                startActivity(intent);
            }
        });
        ivIconShippingAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ShippingAddressActivity.class);
                startActivity(intent);
            }
        });


        //Toolbar
        mToolBar = (Toolbar) findViewById(R.id.tb_main);
        mToolBar.setTitle("My Account");

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
                        IntentIntegrator intentIntegrator = new IntentIntegrator(MyAccountActivity.this);
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
                            Toast.makeText(MyAccountActivity.this, ((Nameable) drawerItem).getName().getText(MyAccountActivity.this), Toast.LENGTH_SHORT).show();
                        }

                        if (((Nameable) drawerItem).getName().getText(MyAccountActivity.this)=="Shopping Cart"){
                            Intent intent = new Intent(getApplicationContext(),MyCartActivity.class);
                            startActivity(intent);
                        }

                        if (((Nameable) drawerItem).getName().getText(MyAccountActivity.this)=="Home"){
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }

                        if (((Nameable) drawerItem).getName().getText(MyAccountActivity.this)=="My Account"){
                            Intent intent = new Intent(getApplicationContext(),MyAccountActivity.class);
                            startActivity(intent);

                        }

                        if (((Nameable) drawerItem).getName().getText(MyAccountActivity.this)=="Messages"){
                            Intent intent = new Intent(getApplicationContext(),SignInActivity.class);
                            startActivity(intent);
                        }
                        if (((Nameable) drawerItem).getName().getText(MyAccountActivity.this)=="My Orders"){
                            Intent intent = new Intent(getApplicationContext(),MyOrdersActivity.class);
                            startActivity(intent);
                        }
                        if (((Nameable) drawerItem).getName().getText(MyAccountActivity.this)=="Settings"){
                            Intent intent = new Intent(getApplicationContext(),WelcomeActivity.class);
                            startActivity(intent);
                        }
                        if (((Nameable) drawerItem).getName().getText(MyAccountActivity.this)=="Logout"){
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

    }//end OnCreate

    public void initializeViews(){

        tvUserName =(TextView)findViewById(R.id.tvUserName);
        tvEmail =(TextView)findViewById(R.id.tvEmail);
        tvMyOrders =(TextView)findViewById(R.id.tvMyOrders);
        tvMyProfile =(TextView)findViewById(R.id.tvMyProfile);
        tvShippingAddress =(TextView)findViewById(R.id.tvShippingAddress);

        ivProfile =(ImageView)findViewById(R.id.ivProfile);
        ivIconOrder =(ImageView)findViewById(R.id.ivIconOrder);
        ivIconMyProfile =(ImageView)findViewById(R.id.ivIconMyProfile);
        ivIconShippingAddress =(ImageView)findViewById(R.id.ivIconShippingAddress);
    }

    //QrCode
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result !=null){
            if(result.getContents()==null){
                Toast.makeText(this, "You Canceled the scan", Toast.LENGTH_SHORT).show();
            }else {
                String qrResult=result.getContents();
                //Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, qrResult, Toast.LENGTH_SHORT).show();
                String array[] = qrResult.split(" ");
                int pro_id=Integer.parseInt(array[0]);
                int sub_cat_id=Integer.parseInt(array[1]);
                int brand_id=Integer.parseInt(array[2]);


            }

        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}
