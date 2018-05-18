package androidrock.quickshoapapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

import androidrock.quickshoapapp.helper.DBHelper;
import androidrock.quickshoapapp.helper.UserSessionManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {

    Fragment subfragment;
    UserSessionManager objUserSessionManager;
    Menu menuheader;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        objUserSessionManager = new UserSessionManager(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        gotoHomepage();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //user already login
        if(menu != null && objUserSessionManager != null) {

            if (objUserSessionManager.checkLogin()) {
                //link text-logout
                if(menu.findItem(R.id.loginlogout) != null) {
                    menu.findItem(R.id.loginlogout).setTitle("logout");
                }
            } else {
                //link text -login
                if(menu.findItem(R.id.loginlogout) != null) {
                    menu.findItem(R.id.loginlogout).setTitle("login");
                }
            }
        }

        return super.onPrepareOptionsMenu(menu);
    }



    private void resetSearchBar() {
        if (menuheader != null) {
            menuheader.findItem(R.id.menu_item_search).collapseActionView();
        }
        if (searchView != null) {
            searchView.setQuery("", false);
            searchView.clearFocus();

        }
    }

    private void gotoHomepage() {
        if (menuheader != null) {
            menuheader.findItem(R.id.cartcheckout).collapseActionView();
            menuheader.findItem(R.id.cartcheckout).setVisible(false);
            resetSearchBar();
            menuheader.findItem(R.id.menu_item_search).setVisible(true);
        }

        subfragment = new MainCategory();// homePage();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentFrame, subfragment);
        fragmentTransaction.commit();
        getSupportActionBar().setTitle("Categories");
    }

    public void gotoSubProductList(int id) {

        if(menuheader != null){
            menuheader.findItem(R.id.cartcheckout).collapseActionView();
            menuheader.findItem(R.id.cartcheckout).setVisible(false);
            resetSearchBar();
            menuheader.findItem(R.id.menu_item_search).setVisible(true);
        }
        Bundle bundle = new Bundle();
        bundle.putInt("productid", id);

        subfragment = new SubCategory();// homePage();
        subfragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentFrame, subfragment);
        fragmentTransaction.commit();
        getSupportActionBar().setTitle("Sub Categories");
    }

    public void gotoProductRateDetails(int id)
    {
        menuheader.findItem(R.id.cartcheckout).setVisible(false);
        resetSearchBar();
        menuheader.findItem(R.id.menu_item_search).setVisible(false);
        Intent myIntent = new Intent(this, ProdctRateDetails.class);
        myIntent.putExtra("productid", id);
        startActivity(myIntent);


    }

    public  void gotomycart()
    {
        menuheader.findItem(R.id.cartcheckout).setVisible(false);
        resetSearchBar();
        menuheader.findItem(R.id.menu_item_search).setVisible(true);
        subfragment = new MyCart();// homePage();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentFrame, subfragment);
        fragmentTransaction.commit();
        getSupportActionBar().setTitle("My Cart");
    }

    public  void gotoOrderHistory()
    {
        menuheader.findItem(R.id.cartcheckout).setVisible(false);
        resetSearchBar();
        menuheader.findItem(R.id.menu_item_search).setVisible(true);
        subfragment = new OrderHistory();// homePage();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentFrame, subfragment);
        fragmentTransaction.commit();
        getSupportActionBar().setTitle("Order History");
    }

    @Override
    public void onBackPressed() {
/*        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit");
        builder.setMessage("Are You Sure?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.menu_item_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        menuheader = menu;
        menuheader.findItem(R.id.menu_item_search).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.loginlogout) {
            objUserSessionManager.clearAllSession();
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            return true;






        }
        else  if (id == R.id.cartcheckout) {
            if(objUserSessionManager.checkLogin())
            {
                //goto order history page.
                Intent myIntent = new Intent(this, OrderConfirm.class);
                startActivity(myIntent);
            }
            else {
                Intent myIntent = new Intent(this, LoginActivity.class);
                startActivity(myIntent);
            }

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (menuheader != null) {
            menuheader.findItem(R.id.cartcheckout).collapseActionView();
        }

        if (id == R.id.nav_camera) {
            gotoHomepage();
        }
        else if (id == R.id.mycart) {
            gotomycart();
        }

        else if (id == R.id.orderHistory) {
            gotoOrderHistory();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (subfragment instanceof MainCategory) {
            ((MainCategory) subfragment).mAdapter.getFilter().filter(query);
            ((SubCategory) subfragment).mAdapter.getFilter().filter(query);
            return true;
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (subfragment instanceof MainCategory) {
            if(((MainCategory) subfragment).mAdapter != null) {
                ((MainCategory) subfragment).mAdapter.getFilter().filter(newText);
                return true;
                }
            }
        return false;
    }
}
