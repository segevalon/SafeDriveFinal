package com.segeval.safedrive.activities;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.segeval.safedrive.R;
import com.segeval.safedrive.fragments.FragmentConf;
import com.segeval.safedrive.fragments.FragmentProfile;
import com.segeval.safedrive.fragments.FragmentRides;
import com.segeval.safedrive.model.Model;
import com.segeval.safedrive.utils.Log4jHelper;

import java.io.File;

public class MainActivity extends AppCompatActivity implements DrawerLocker {

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String title;
    PendingIntent pendingIntent;
    private IntentFilter[] intentFiltersArray;
    private String[][] techListsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.navList);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        addDrawerItems();
        setupDrawer();
        title = getSupportActionBar().getTitle().toString();

        Fragment fragmentProfile = new FragmentProfile();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, fragmentProfile);
        transaction.commit();


    }

    private void addDrawerItems() {
        final String[] btnArray = {"Home", "Send Logs", "Records History", "Configuration", "Logout"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, btnArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (btnArray[position].equals("Home")) {
                    Fragment fragment = getFragmentManager().findFragmentById(R.id.fragment_container);
                    if (!(fragment instanceof FragmentProfile))
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentProfile()).commit();
                } else if (btnArray[position].equals("Send Logs")) {
                    Intent email = new Intent(Intent.ACTION_SEND);
                    File logFile = new File(Environment.getExternalStorageDirectory(), Log4jHelper.logFileName);
                    Uri path = Uri.fromFile(logFile);
                    email.setType("text/html");
                    email.putExtra(Intent.EXTRA_EMAIL, new String[]{"segevalon50@gmail.com"});
                    email.putExtra(Intent.EXTRA_SUBJECT, "log from " + Model.getInstance().getId());
                    email.putExtra(Intent.EXTRA_STREAM, path);
                    startActivity(Intent.createChooser(email, "Send email"));
                } else if (btnArray[position].equals("Records History")) {
                    Fragment fragment = getFragmentManager().findFragmentById(R.id.fragment_container);
                    if (!(fragment instanceof FragmentRides))
                        getFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new FragmentRides()).commit();
                } else if (btnArray[position].equals("Configuration")) {
                    FragmentConf fragmentConfiguration = new FragmentConf();
                    fragmentConfiguration.show(getFragmentManager(), "ms");
                }  else if (btnArray[position].equals("Logout")) {
                    new AlertDialog.Builder(MainActivity.this).setMessage("Are you really want to logout?")
                            .setTitle("Logout").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        }
                    }).setNegativeButton("No", null).show();
                }

                mDrawerLayout.closeDrawer(mDrawerList);
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                title = getSupportActionBar().getTitle().toString();
                getSupportActionBar().setTitle("What's Up?");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(title);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!mDrawerLayout.isDrawerVisible(mDrawerList))
            new AlertDialog.Builder(this).setTitle("Exit?")
                    .setMessage("Are you really want to exit?")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.this.finish();
                        }
                    }).create().show();
        else mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setDrawerEnabled(boolean enabled) {
        int lockMode = enabled ? DrawerLayout.LOCK_MODE_UNLOCKED :
                DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        mDrawerLayout.setDrawerLockMode(lockMode);
        mDrawerToggle.setDrawerIndicatorEnabled(enabled);
    }


}

