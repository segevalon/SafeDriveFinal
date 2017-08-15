package com.segeval.safedrive.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.segeval.safedrive.R;
import com.segeval.safedrive.activities.DrawerLocker;
import com.segeval.safedrive.model.Model;
import com.segeval.safedrive.utils.Constants;
import com.segeval.safedrive.utils.DetailsAdapter;
import com.segeval.safedrive.utils.Log4jHelper;
import com.segeval.safedrive.utils.DetailsThread;

import org.apache.log4j.Logger;

import dmax.dialog.SpotsDialog;

public class FragmentDetailsList extends Fragment {


    Logger log = Log4jHelper.getLogger("DetailsListFragment");

    private ListView listView;

    private Activity activity;

    private DetailsThread detailsTask;

    private SpotsDialog dialog;

    private TextView timeView;


    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        this.activity = getActivity();
        setHasOptionsMenu(true);
        /**
         * Build the factory out side of the manager class
         */
//        ImageView smilyImage = (ImageView) view.findViewById(R.id.smiley_image);

//        Drawable red = getResources().getDrawable(R.drawable.red_smily);
//        Drawable green = getResources().getDrawable(R.drawable.green_smily);
//        if (smilyImage.getDrawable().getConstantState() != red.getConstantState())
//            smilyImage.setImageDrawable(red);

        String manager = getArguments().getString(Constants.MANAGER_TAG);
        String address = getArguments().getString(Constants.DEVICE_TAG);
        Model.getInstance().setDeviceAddress(address, manager);
        Model.getInstance().createNewManager(manager);
        ((DrawerLocker) getActivity()).setDrawerEnabled(false);
        listView = (ListView) view.findViewById(R.id.details);
        DetailsAdapter detailsAdapter = new DetailsAdapter(activity);
        listView.setAdapter(detailsAdapter);
        timeView = (TextView) view.findViewById(R.id.time);
        locationInit();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (!menu.hasVisibleItems()) {
            MenuInflater menuInflater = getActivity().getMenuInflater();
            menuInflater.inflate(R.menu.details_menu, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        stopRunning();
        return true;
    }

    interface CallbackWait {
        void onWaitStop();
    }

    private void waitForNSeconds(final int n, final CallbackWait callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = n; i >= 0; i--) {
                    final int finalI = i;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            changeDialogMessage(String.format("Waiting(%d)...", finalI));
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dismissDialog();
                        }
                    });
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onWaitStop();
                    }
                });
            }
        }).start();
    }


    private void stopRunning() {
        detailsTask.stopRunning(new DetailsThread.Callback() {
            @Override
            public void ThreadDidStop() {
                log.debug("stop running");
                int message = R.string.dsc_wifi_msg;
                //check if connected to the wifi
                WifiManager wifi = (WifiManager) activity.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//                WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                wifi.setWifiEnabled(false);

                waitForNSeconds(7, new CallbackWait() {
                    @Override
                    public void onWaitStop() {
                        showDialog("Trying to send...");
                        Model.getInstance().sendRemote(new Model.OnEvent() {
                            @Override
                            public void onSuccess() {
                                dismissDialog();
                                onSentSuccess();
                            }

                            @Override
                            public void onFailure() {
                                dismissDialog();
                                sendAgain();
                            }
                        });
                    }
                });
            }
        });
    }

    void sendAgain() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setCancelable(false).setMessage("Cannot connect to the cloud, Please try again.")
                .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        showDialog("Sending...");
                        Model.getInstance().sendRemote(new Model.OnEvent() {
                            @Override
                            public void onSuccess() {
                                dismissDialog();
                                onSentSuccess();
                            }

                            @Override
                            public void onFailure() {
                                dismissDialog();
                                sendAgain();
                            }
                        });
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new FragmentProfile(),
                                Constants.DETAILS_TAG).commit();
            }
        }).show();
    }

    /**
     * initate the location service - checks if the user specified a denial of the location services
     * and notify him about that . - > get the location listener ready
     */
    private void locationInit() {
        //sets the manager
        if (!Model.getInstance().isGpsEnabled()) {
            buildAlertMessageNoGps();
        } else initThread();
    }


    private void buildAlertMessageNoGps() {
        new AlertDialog.Builder(getActivity()).
                setMessage("Your GPS seems to be disabled, please enable it")
                .setCancelable(false)
                .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new FragmentProfile(),
                                        Constants.DETAILS_TAG).commit();
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new FragmentProfile(),
                                Constants.DETAILS_TAG).commit();
            }
        }).create().show();
    }

    public class CallBack {
        public void onStop() {
            stopRunning();
        }
    }

    /**
     * thread for recieving the information
     */
    private void initThread() {
        CallBack callBack = new CallBack();
        if (detailsTask == null)
            detailsTask = new DetailsThread(callBack, listView, getActivity(), timeView);
        log.debug("start detailsThread");
        detailsTask.start();
    }

    private void showDialog(String message) {
        dialog = new SpotsDialog(getActivity(), message);
        dialog.setCancelable(false);
        dialog.show();
    }

    private void changeDialogMessage(String message) {
        if (dialog == null)
            showDialog(message);
        dialog.setMessage(message);

    }


    private void dismissDialog() {
        if (dialog == null)
            return;
        if (dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    private void onSentSuccess() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Information has been Sent. \nThank You!")
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new FragmentProfile(),
                                        Constants.DETAILS_TAG).commit();
                    }
                }).setCancelable(false).show();
    }
}
