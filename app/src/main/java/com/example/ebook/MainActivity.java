package com.example.ebook;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    FrameLayout frameLayout;

    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        tabLayout =findViewById(R.id.tabLayout);
        frameLayout=findViewById(R.id.frameLayout);



        FragmentManager fManager =getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fManager.beginTransaction();
        fragmentTransaction.add(R.id.frameLayout,new FirstFragment());
        fragmentTransaction.commit();


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabPosition =tab.getPosition();

                if (tabPosition==0){
                    FragmentManager fManager =getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction= fManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout,new FirstFragment());
                    fragmentTransaction.commit();

                } else if (tabPosition==1) {
                    FragmentManager fManager =getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction= fManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout,new SecondFragment());
                    fragmentTransaction.commit();

                }else if (tabPosition==2) {
                    FragmentManager fManager =getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction= fManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout,new ThirdFragment());
                    fragmentTransaction.commit();
                }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });





        // --------------------------------- On Back Press  End---------------------------------------------


        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (backPressedTime + 2000 > System.currentTimeMillis()) {
                    finish(); // Activity বন্ধ
                } else {
                    Toast.makeText(MainActivity.this, "Press back again to exit", Toast.LENGTH_SHORT).show();
                }
                backPressedTime = System.currentTimeMillis();
            }
        };

        getOnBackPressedDispatcher().addCallback(this, callback);

        // --------------------------------- On Back Press  End---------------------------------------------




    }



    //------------------------- No Internet-------------------------------------

    @Override
    protected void onResume() {
        super.onResume();
        Network.checkInternet(this);
    }

    //------------------------- No Internet End Here-------------------------------------



}