package com.aguatno.myfirebase.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.trusted.LauncherActivityMetadata;
import android.support.customtabs.trusted.TrustedWebActivityBuilder;
import android.support.customtabs.trusted.TwaLauncher;
import android.support.customtabs.trusted.splashscreens.PwaWrapperSplashScreenStrategy;
import android.util.Log;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "TWALauncherActivity";
    private LauncherActivityMetadata mMetadata;
    private TwaLauncher mTwaLauncher;
    private PwaWrapperSplashScreenStrategy mSplashScreenStrategy;
    private boolean mBrowserWasLaunched;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);


        super.onCreate(savedInstanceState);

        this.mMetadata = LauncherActivityMetadata.parse(this);

        TrustedWebActivityBuilder twaBuilder = (new TrustedWebActivityBuilder(this, this.getLaunchingUrl())).setStatusBarColor(this.getColorCompat(this.mMetadata.statusBarColorId));
        this.mTwaLauncher = new TwaLauncher(this);
        this.mTwaLauncher.launch(twaBuilder, this.mSplashScreenStrategy, () -> {
            this.mBrowserWasLaunched = true;
        });  //.launch causes the incorrect behaviour, launch(Uri uri), understandably, behaves identically

    }

    protected Uri getLaunchingUrl() {
        Uri uri = getIntent().getData();
        if (uri != null) {
            Log.d(TAG, "Using URL from Intent (" + uri + ").");
            return uri;
        }

        if (mMetadata.defaultUrl != null) {
            Log.d(TAG, "Using URL from Manifest (" + mMetadata.defaultUrl + ").");
            return Uri.parse(mMetadata.defaultUrl);
        }

        return Uri.parse("https://www.example.com/");
    }

    private int getColorCompat(int splashScreenBackgroundColorId) {
        return ContextCompat.getColor(this, splashScreenBackgroundColorId);
    }
}
