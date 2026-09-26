package com.example.doan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {


    Toolbar toolbar;
    BottomAppBar bottom;
    DrawerLayout myDrawer;
    NavigationView myNavigation;
    FrameLayout frameLayout;

    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.menu);
        setSupportActionBar(toolbar);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        Fragment fragment=new HomeFragment();
        loadFragment(fragment);

        frameLayout = findViewById(R.id.frame_fragment);

        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id==R.id.icon_home){
                    toolbar.setTitle("Home");
                    Fragment fragment =new HomeFragment();
                    loadFragment(fragment);
                    return true;}
                else if (id==R.id.icon_service){
                    toolbar.setTitle("Service");
                    Fragment fragment=new ServiceFragment();
                    loadFragment(fragment);
                    return true;}
                else if (id==R.id.icon_cart){
                    toolbar.setTitle("Cart");
                    Fragment fragment=new CartFragment();
                    loadFragment(fragment);
                    return true;}
                else if (id==R.id.icon_history){
                    toolbar.setTitle("History");
                    Fragment fragment=new HistoryFragment();
                    loadFragment(fragment);
                    return true;}
                else
                    return false;
            }
        });

        setupDrawer();
    }

    private void setupDrawer() {
        myDrawer  = findViewById(R.id.drawerLayoutHome);
        myNavigation = findViewById(R.id.navigationViewHome);

        myNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int size = myNavigation.getMenu().size();
                for (int i = 0; i < size; i++) {
                    myNavigation.getMenu().getItem(i).setChecked(false);
                }

                if(item.getItemId() == R.id.DrawerRoom) {
                    // Do Something
                    // Tạo một instance của Fragment mới
                    Fragment fragment = new qlroomFragment();

                    // Load Fragment mới
                    loadFragment(fragment);

                    // Đóng DrawerLayout (nếu cần thiết)
                    myDrawer.closeDrawer(GravityCompat.START);

                    // Trả về true để chỉ ra rằng sự kiện đã được xử lý thành công
                    return true;
                }

                if(item.getItemId() == R.id.DrawerTypeRoom) {
                    Fragment fragment = new qlRoomTypeFragment();
                    loadFragment(fragment);
                    myDrawer.closeDrawer(GravityCompat.START);
                    return true;
                }
                if(item.getItemId() == R.id.DrawerBookingRoom) {
                    Fragment fragment = new qlbookingFragment();
                    loadFragment(fragment);
                    myDrawer.closeDrawer(GravityCompat.START);
                    return true;
                }
                if(item.getItemId() == R.id.Drawerservice) {
                    Fragment fragment = new qlserviceFragment();
                    loadFragment(fragment);
                    myDrawer.closeDrawer(GravityCompat.START);
                    return true;
                }
                if(item.getItemId() == R.id.DrawerCustomer) {
                    Fragment fragment = new qlcustomerFragment();
                    loadFragment(fragment);
                    myDrawer.closeDrawer(GravityCompat.START);
                    return true;
                }
                if(item.getItemId() == R.id.DrawerDoanhthu) {
                    Fragment fragment = new ThongKeDoanhThuFragment();
                    loadFragment(fragment);
                    myDrawer.closeDrawer(GravityCompat.START);
                    return true;
                }
                if(item.getItemId() == R.id.DrawerVoucher) {
                    Fragment fragment = new qlvoucherFragment();
                    loadFragment(fragment);
                    myDrawer.closeDrawer(GravityCompat.START);
                    return true;
                }

                item.setChecked(true);
                myDrawer.close();
                return true;
            }
        });


    }

    private void loadFragment(Fragment fragment) {
        // Implement fragment transaction logic here

        // Tạo một FragmentTransaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Thay đổi Fragment hiện tại trong FrameLayout
        transaction.replace(R.id.frame_fragment, fragment);

        // Commit Transaction
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_top, menu);

        // return true so that the menu pop up is opened
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (myDrawer.isOpen()) {
                myDrawer.close();
            } else {
                myDrawer.open();
            }
        }
        if (id==R.id.action_people){
            toolbar.setTitle("Tài khoản");
            Fragment fragment =new AccountFragment();
            loadFragment(fragment);
            return true;}

        else if (id==R.id.action_logout){
            toolbar.setTitle("Logout");
            auth.signOut();
            Intent i = new Intent(MainActivity.this, Login.class);
            startActivity(i);
            return true;}
        else
            return false;
    }

}