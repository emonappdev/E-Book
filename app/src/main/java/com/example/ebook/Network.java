package com.example.ebook;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

public class Network {

    private static AlertDialog alertDialog;

    public static void checkInternet(final Activity activity) {
        if (activity == null || activity.isFinishing()) return;

        if (!isConnected(activity)) {
            showNoInternetDialog(activity);
        } else {
            dismissDialog();
        }
    }

    private static boolean isConnected(Activity activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    private static void showNoInternetDialog(final Activity activity) {
        if (alertDialog != null && alertDialog.isShowing()) return;

        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.no_internet, null);

        alertDialog = new AlertDialog.Builder(activity)
                .setView(dialogView)
                .setCancelable(false)
                .create();

        Button Retry = dialogView.findViewById(R.id.Retry);
        Button Exit = dialogView.findViewById(R.id.Exit);

        Retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dismissDialog();
                checkInternet(activity);
            }
        });

        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
                activity.finishAffinity();
                System.exit(0);
            }
        });

        alertDialog.show();
    }

    private static void dismissDialog() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
            alertDialog = null;
        }
    }

}
