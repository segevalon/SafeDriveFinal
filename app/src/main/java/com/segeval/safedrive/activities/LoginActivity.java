package com.segeval.safedrive.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.segeval.safedrive.R;
import com.segeval.safedrive.fragments.FragmentLogin;
import com.segeval.safedrive.utils.Log4jRuntime;


public class LoginActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log4jRuntime log4jRuntime = new Log4jRuntime();
        Thread.setDefaultUncaughtExceptionHandler(log4jRuntime);
        setContentView(R.layout.activity_login);

        Fragment DeviceList = new FragmentLogin();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container_login, DeviceList);
        transaction.commit();

    }


}
