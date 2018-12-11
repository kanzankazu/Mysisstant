package id.co.halloarif.catatanku.view;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import id.co.halloarif.catatanku.R;
import id.co.halloarif.catatanku.presenter.MainListener;
import id.co.halloarif.catatanku.support.util.SessionUtil;
import id.co.halloarif.catatanku.view.adapter.MainActivityNoteFriendRVAdapter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int REQ_PERM_CODE = 12;
    private ImageView ivMainProfilefvbi;
    private TextView tvMainMottofvbi;
    private LinearLayout cvMainAlarmfvbi;
    private LinearLayout cvMainAcarafvbi;
    private LinearLayout cvMainNotefvbi;
    private RecyclerView rvMainNoteFriendfvbi;

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private MainActivityNoteFriendRVAdapter adapter;
    private MainListener mainListener;

    private boolean doubleBackToExitPressedOnce;
    private Menu navigationViewMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolBar();
        initParam();
        initSession();
        initPermission();

    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationViewMenu = navigationView.getMenu();
        /*COntoh*/
        //MenuItem item = navigationViewMenu.findItem(R.id.menuNoteInputAddFriend);
        //item.setTitle("NewTitleForCamera");
    }

    private void initParam() {

    }

    private void initSession() {

    }

    private void initPermission() {
        // cek apakah sudah memiliki permission untuk access fine location
        if (
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                ) {
            // cek apakah perlu menampilkan info kenapa membutuhkan access fine location
            if (
                    ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) ||
                            ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                            ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_CONTACTS) ||
                            ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_PHONE_STATE)||
                            ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.RECORD_AUDIO)
                    ) {
                String[] perm = {
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.RECORD_AUDIO
                };
                ActivityCompat.requestPermissions(MainActivity.this, perm, REQ_PERM_CODE);
            } else {
                // request permission untuk access fine location
                String[] perm = {
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.RECORD_AUDIO
                };
                ActivityCompat.requestPermissions(MainActivity.this, perm, REQ_PERM_CODE);
            }
        } else {
            // permission access fine location didapat
            // SystemUtil.showToast(MainActivity.this, "Yay, has permission", Toast.LENGTH_SHORT,Gravity.TOP);
            initComponent();
            initContent();
            initListener();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_PERM_CODE: //private final int = 1
                Log.d("Lihat", "onRequestPermissionsResult MainActivity grantResults.length : " + grantResults.length);
                Log.d("Lihat", "onRequestPermissionsResult MainActivity permissions.length : " + permissions.length);
                boolean isPerpermissionForAllGranted = false;
                ArrayList<String> listStat = new ArrayList<>();
                if (grantResults.length > 0 && permissions.length == grantResults.length) {
                    for (int i = 0; i < permissions.length; i++) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            isPerpermissionForAllGranted = true;
                            listStat.add("1");
                        } else {
                            isPerpermissionForAllGranted = false;
                            listStat.add("0");
                        }
                    }
                } else {
                    isPerpermissionForAllGranted = true;
                }

                int frequency0 = Collections.frequency(listStat, "0");
                int frequency1 = Collections.frequency(listStat, "1");

                if (frequency1 != grantResults.length) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                    alertDialogBuilder.setMessage("you denied some permission, you must give all permission to next proccess?");
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            initPermission();
                        }
                    });
                    alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            finish();
                            Toast.makeText(getApplicationContext(), "Permission is not given", Toast.LENGTH_SHORT).show();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else {
                    initComponent();
                    initContent();
                    initListener();
                }

                /*if (!isPerpermissionForAllGranted) {
                    finish();
                    SystemUtil.showToast(getApplicationContext(), "Izin tidak di berikan", Snackbar.LENGTH_LONG,Gravity.TOP);
                } else {
                    initComponent();
                    initContent();
                    initListener();
                }*/
                break;
        }
    }

    private void initComponent() {
        ivMainProfilefvbi = (ImageView) findViewById(R.id.ivMainProfile);
        tvMainMottofvbi = (TextView) findViewById(R.id.tvMainMotto);
        cvMainAlarmfvbi = (LinearLayout) findViewById(R.id.cvMainAlarm);
        cvMainAcarafvbi = (LinearLayout) findViewById(R.id.cvMainAcara);
        cvMainNotefvbi = (LinearLayout) findViewById(R.id.cvMainNote);
        rvMainNoteFriendfvbi = (RecyclerView) findViewById(R.id.rvMainNoteFriend);
    }

    private void initContent() {
        adapter = new MainActivityNoteFriendRVAdapter();
        rvMainNoteFriendfvbi.setAdapter(adapter);
        rvMainNoteFriendfvbi.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initListener() {

        cvMainAlarmfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainListener.onAlarmClick();
            }
        });
        cvMainAcarafvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainListener.onAcaraClick();
            }
        });
        cvMainNotefvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainListener.onNoteClick();
            }
        });

        mainListener = new MainListener() {
            @Override
            public void onAlarmClick() {
                moveToAlarm();
            }

            @Override
            public void onAcaraClick() {
                moveToAcara();
            }

            @Override
            public void onNoteClick() {
                moveToNote();
            }
        };
    }

    private void moveToAlarm() {
        Intent intent = new Intent(MainActivity.this, AlarmSummaryActivity.class);
        startActivity(intent);
    }

    private void moveToAcara() {
        Intent intent = new Intent(MainActivity.this, AcaraSummaryActivity.class);
        startActivity(intent);
    }

    private void moveToNote() {
        Intent intent = new Intent(MainActivity.this, NoteSummaryActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            onBackPressedOut();
        }
    }

    public void onBackPressedOut() {
        if (doubleBackToExitPressedOnce) {
            /*CODE HERE!!!*/
            //super.onBackPressed();
            //return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Tekan sekali lagi untuk keluar", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_signout) {
            Intent intent = new Intent(MainActivity.this, LogRegActivity.class);
            startActivity(intent);
            finish();

            SessionUtil.removeAllSharedPreferences();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /* @Override
     public boolean onCreateOptionsMenu(Menu navigationViewMenu) {
         getMenuInflater().inflate(R.navigationViewMenu.main, navigationViewMenu);
         return true;
     }

     @Override
     public boolean onOptionsItemSelected(MenuItem item) {
         int id = item.getItemId();

         //noinspection SimplifiableIfStatement
         if (id == R.id.action_settings) {
             return true;
         }

         return super.onOptionsItemSelected(item);
     }*/



}
