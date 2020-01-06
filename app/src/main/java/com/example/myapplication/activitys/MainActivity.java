package com.example.myapplication.activitys;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.BuildConfig;
import com.example.myapplication.R;
import com.example.myapplication.fragments.DailyBonusFragment;
import com.example.myapplication.fragments.EarnMoneyFragment;
import com.example.myapplication.fragments.CongratulationScreen;
import com.example.myapplication.fragments.FreeRoyalPassFragment;
import com.example.myapplication.fragments.HomeFragment;
import com.example.myapplication.fragments.InviteApp;
import com.example.myapplication.fragments.OnHomeFragmentInteractionListener;
import com.example.myapplication.fragments.RateUsBoxFragment;
import com.example.myapplication.fragments.SampleAddFragment;
import com.example.myapplication.fragments.SpinnerBonusFragment;
import com.example.myapplication.fragments.WalletFragment;
import com.example.myapplication.models.Utilities;
import com.facebook.ads.AdView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.ads.AdListener;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

import static com.example.myapplication.fragments.BaseFragment.DIALOG;

public class MainActivity extends BaseActivity implements OnHomeFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener {

    private static final int MY_PERMISSIONS_REQUEST_READ_CALL_LOG= 1;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 2;
    AdView adView;
    TextView mUCCount;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermition();
        setNavigationView();
        fireBaseAds();
        //facebookAds();
        showNativeAdd(false);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, HomeFragment.newInstance(), "Home");
        fragmentTransaction.commit();
    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    private void checkPermition() {
        /*int temp = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE);
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
        ||ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE},
                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);

        }
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED
                ||ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CALL_LOG},
                    MY_PERMISSIONS_REQUEST_READ_CALL_LOG);

        }

        if (ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
                ||ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
        }*/

        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                android.Manifest.permission.CALL_PHONE,
                android.Manifest.permission.READ_CALL_LOG,
                android.Manifest.permission.READ_PHONE_STATE,
                android.Manifest.permission.SEND_SMS
        };

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void setNavigationView() {
        dl = findViewById(R.id.dl);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);
        dl.addDrawerListener(t);
        t.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        nv = findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(this);

    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mUCCount  = findViewById(R.id.wallet_count);
        mUCCount.setText(String.format(getString(R.string.uc_count), Objects.requireNonNull(Utilities.getUser(this)).getScore()));
        return true;

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adView != null) {
            adView.destroy();
        }
    }

    @Override
    public void gotoHomeFragment() {
        replaceFragment(HomeFragment.newInstance(),null, false);
    }

    @Override
    public void goToDailyBonus() {
        setAddMode(true);
        replaceFragment(DailyBonusFragment.newInstance(),getString(R.string.daily_bonus), true);
    }

    @Override
    public void goToSpinnerBonus() {
        setAddMode(true);
        replaceFragment(SpinnerBonusFragment.newInstance(),getString(R.string.spinner_bonus), true);
    }

    @Override
    public void goToEarnMoney() {
        setAddMode(true);
        replaceFragment(EarnMoneyFragment.newInstance(),getString(R.string.earn_money), true);
    }

    @Override
    public void goToInvitationLink() {
        //showNativeAdd();
        setAddMode(true);
        replaceFragment(InviteApp.newInstance(),getString(R.string.invitation_link), true);
    }

    public void setText(String text) {
        Objects.requireNonNull(getSupportActionBar()).setTitle(text);
    }

    @Override
    public void goToAddsDemo() {
        replaceFragment(SampleAddFragment.newInstance(),getString(R.string.adds_demo), true);
    }


    @Override
    public void goToFreeRoyalPassScreen() {
        setAddMode(true);
        replaceFragment(FreeRoyalPassFragment.newInstance(),"Free Royal Pass", true);
    }

    @Override
    public void showCongratsFragment(int parseInt) {
        replaceFragment(CongratulationScreen.newInstance(parseInt),"Congratulations",false);
    }

    @Override
    public void showInterstitialAdd() {

        final InterstitialAd mInterstitialAd = new InterstitialAd(Objects.requireNonNull(this));
        mInterstitialAd.setAdUnitId(BuildConfig.INTERSTITIAL_ADD);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                showInterstitial(mInterstitialAd);
                //Toast.makeText(MainActivity.this, "onAdLoaded()", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                //Toast.makeText(MainActivity.this,
//                        "onAdFailedToLoad() with error code: " + errorCode,
                        //Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClosed() {
                goToSpinnerBonus();
            }
        });

    }
    private void showInterstitial(InterstitialAd interstitialAd) {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (interstitialAd != null && interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else {
            //Toast.makeText(MainActivity.this, "Ad did not load", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showAddVideo() {
        final ProgressDialog progressdialog = new ProgressDialog(this);
        progressdialog.setMessage("Please Wait....");
        progressdialog.show();
        //rewardedAd;
        final RewardedAdCallback adCallback = new RewardedAdCallback() {
            @Override
            public void onRewardedAdOpened() {
                //Toast.makeText(Objects.requireNonNull(MainActivity.this), "onRewardedAdOpened", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedAdClosed() {
                // Ad closed.
                //goToDailyBonus();
                //goToSpinnerBonus();
                //Toast.makeText(Objects.requireNonNull(MainActivity.this), "Ad closed.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUserEarnedReward(@NonNull RewardItem reward) {
                goToSpinnerBonus();
                // User earned reward.
                //Toast.makeText(Objects.requireNonNull(MainActivity.this), "User earned reward.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedAdFailedToShow(int errorCode) {
                // Ad failed to display
                //Toast.makeText(Objects.requireNonNull(MainActivity.this), "Ad failed to display.", Toast.LENGTH_SHORT).show();
            }
        };

        final RewardedAd rewardedAd = new RewardedAd(Objects.requireNonNull(MainActivity.this), "ca-app-pub-3940256099942544/5224354917");

        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                // Ad successfully loaded.
                progressdialog.dismiss();
                rewardedAd.show(Objects.requireNonNull(MainActivity.this), adCallback);
                //Toast.makeText(Objects.requireNonNull(MainActivity.this), "Ad successfully loaded.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedAdFailedToLoad(int errorCode) {
                // Ad failed to load.
                progressdialog.dismiss();
                //Toast.makeText(Objects.requireNonNull(MainActivity.this), "Ad failed to load.", Toast.LENGTH_SHORT).show();
            }
        };



        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
    }

    @Override
    public void showNativeAdd() {
        Intent intent = new Intent(MainActivity.this, NativeAddAdvance.class);
        startActivity(intent);
    }

    @Override
    public void setAddMode(boolean addMode) {
        showNativeAdd(addMode);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                //Toast.makeText(getApplicationContext(),"Back button clicked", Toast.LENGTH_SHORT).show();
                if(t.onOptionsItemSelected(item))
                    return true;
                break;
            /*case R.id.menu_about:
                isHamburgerAsUp();
                //Toast.makeText(MainActivity.this, "about",Toast.LENGTH_SHORT).show();
                break;*/
        }

        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch(id)
        {
            case R.id.menu_wallet:

                setAddMode(true);
                replaceFragment(WalletFragment.newInstance(), "My Wallet", true);
                break;
                //Toast.makeText(MainActivity.this, "Wallet",Toast.LENGTH_SHORT).show();break;
            /*case R.id.menu_about:
                //enableBackArrow();

                setAddMode(true);
                replaceFragment(AboutFragment.newInstance("", ""),"How to use", true);
                break;*/
            case R.id.menu_rate_us:
                FragmentTransaction ft = Objects.requireNonNull(this).getSupportFragmentManager().beginTransaction();
                ft.addToBackStack(null);
                final RateUsBoxFragment dialogFragment = RateUsBoxFragment.newInstance();
                dialogFragment.show(ft, DIALOG);

                break;
                /**/
                //Toast.makeText(MainActivity.this, "Rate us",Toast.LENGTH_SHORT).show();break;
            default:
                return true;
        }
        dl.closeDrawer(nv);
        return true;
    }
    @Override
    public void onBackPressed() {
        /*Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment instanceof  AboutFragment ) {

        }*/
        isHamburgerAsUp();
        super.onBackPressed();
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if(fragment instanceof  HomeFragment) {
            setAddMode(false);
        }
    }
    @Override
    public void enableBackArrow() {
        t.setDrawerIndicatorEnabled(false);
    }

    private void isHamburgerAsUp() {
        t.setDrawerIndicatorEnabled(true);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        /*if (requestCode == MY_PERMISSIONS_REQUEST_READ_PHONE_STATE || requestCode == MY_PERMISSIONS_REQUEST_READ_CALL_LOG
        |requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE ) {

            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                *//*Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);*//*

            } else {
                Toast.makeText(this,"Please accept the call Permission.",Toast.LENGTH_SHORT).show();
                checkPermition();
            }
        }*/
    }

}
