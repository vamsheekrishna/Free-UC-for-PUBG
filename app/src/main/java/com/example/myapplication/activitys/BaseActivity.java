package com.example.myapplication.activitys;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.fragments.BaseFragment;
import com.example.myapplication.R;
import com.example.myapplication.fragments.DialogBoxFragment;
import com.example.myapplication.models.Utilities;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.NativeCustomTemplateAd;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Locale;
import java.util.Objects;

import static com.example.myapplication.fragments.BaseFragment.DIALOG;

public class BaseActivity extends AppCompatActivity {

    private static final String AD_MANAGER_AD_UNIT_ID = "/6499/example/native";
    private static final String SIMPLE_TEMPLATE_ID = "10104090";

    private UnifiedNativeAd nativeAd;
    private boolean isShowNativeAdd = true;
    private boolean isMute = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!Utilities.internetConnectionAvailable(30000)) {
            showDialog(null, R.drawable.plogo, getString(R.string.network_error_msg));
        } else {
            MobileAds.initialize(this, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                }
            });
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }


    protected void replaceFragment(BaseFragment baseFragment, String fragment_id, boolean isAddToBackStack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.container, baseFragment, fragment_id);
        if(isAddToBackStack) {
            fragmentTransaction.addToBackStack(fragment_id);
        }
        fragmentTransaction.commit();
    }

    public void setTitle(String name){
        Objects.requireNonNull(getSupportActionBar()).setTitle(name);
    }

    public void fireBaseAds() {
        com.google.android.gms.ads.AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(isShowNativeAdd) {
            showNativeAdd();
        }
    }

    public void showNativeAdd(boolean isShow) {
        isShowNativeAdd = isShow;
        if (isShowNativeAdd) {
            findViewById(R.id.google_native_add).setVisibility(View.VISIBLE);
            showNativeAdd();
        } else {
            findViewById(R.id.google_native_add).setVisibility(View.GONE);
            handler.removeCallbacks(periodicUpdate);
        }
    }
    private  void showNativeAdd() {

            refreshAd(false, true);
            handler.postDelayed(periodicUpdate, 10 * 1000);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (nativeAd != null) {
            nativeAd.destroy();
        }
        if(isShowNativeAdd) {
            isShowNativeAdd = false;
        }
        handler.removeCallbacks(periodicUpdate);
    }

    Handler handler = new Handler();
    private Runnable periodicUpdate = new Runnable () {
        @Override
        public void run() {
            // scheduled another events to be in 10 seconds later
            handler.postDelayed(periodicUpdate, 15*1000 );
            refreshAd(false, true);
        }
    };
    public void refreshAd(boolean requestUnifiedNativeAds,
                          boolean requestCustomTemplateAds) {
        if (!requestUnifiedNativeAds && !requestCustomTemplateAds) {
            Toast.makeText(this, "At least one ad format must be checked to request an ad.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        AdLoader.Builder builder = new AdLoader.Builder(this, AD_MANAGER_AD_UNIT_ID);

        if (requestUnifiedNativeAds) {
            builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                @Override
                public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                    // You must call destroy on old ads when you are done with them,
                    // otherwise you will have a memory leak.
                    if (nativeAd != null) {
                        nativeAd.destroy();
                    }
                    nativeAd = unifiedNativeAd;
                    FrameLayout frameLayout = findViewById(R.id.google_native_add);
                    UnifiedNativeAdView adView = (UnifiedNativeAdView) getLayoutInflater().inflate(R.layout.google_custom_add, null);
                    populateUnifiedNativeAdView(unifiedNativeAd, adView);
                    frameLayout.removeAllViews();
                    frameLayout.addView(adView);
                }

            });
        }

        if (requestCustomTemplateAds) {
            builder.forCustomTemplateAd(SIMPLE_TEMPLATE_ID,
                    new NativeCustomTemplateAd.OnCustomTemplateAdLoadedListener() {
                        @Override
                        public void onCustomTemplateAdLoaded(NativeCustomTemplateAd ad) {
                            FrameLayout frameLayout = findViewById(R.id.google_native_add);
                            View adView = getLayoutInflater().inflate(R.layout.google_custom_add, null);
                            populateSimpleTemplateAdView(ad, adView);
                            frameLayout.removeAllViews();
                            frameLayout.addView(adView);
                        }
                    },
                    new NativeCustomTemplateAd.OnCustomClickListener() {
                        @Override
                        public void onCustomClick(NativeCustomTemplateAd ad, String s) {
                            Toast.makeText(BaseActivity.this,
                                    "A custom click has occurred in the simple template",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        VideoOptions videoOptions = new VideoOptions.Builder().setStartMuted(isMute).build();
        //VideoOptions videoOptions = new VideoOptions.Builder().setStartMuted(false).build();

        NativeAdOptions adOptions = new NativeAdOptions.Builder()
                .setVideoOptions(videoOptions)
                .build();

        builder.withNativeAdOptions(adOptions);

        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
                //Toast.makeText(BaseActivity.this, "Failed to load native ad: " + errorCode, Toast.LENGTH_SHORT).show();
            }
        }).build();

        adLoader.loadAd(new PublisherAdRequest.Builder().build());
    }

    /*private void populateUnifiedNativeAdView(UnifiedNativeAd nativeAd, UnifiedNativeAdView adView) {
        // Set the media view.
        adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));

        // Set other ad assets.
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        // The headline and mediaContent are guaranteed to be in every UnifiedNativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        adView.getMediaView().setMediaContent(nativeAd.getMediaContent());

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        adView.setNativeAd(nativeAd);

        // Get the video controller for the ad. One will always be provided, even if the ad doesn't
        // have a video asset.
        VideoController vc = nativeAd.getVideoController();

        // Updates the UI to say whether or not this ad has a video asset.
        if (vc.hasVideoContent()) {
            *//*videoStatus.setText(String.format(Locale.getDefault(),
                    "Video status: Ad contains a %.2f:1 video asset.",
                    vc.getAspectRatio()));*//*

            // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
            // VideoController will call methods on this object when events occur in the video
            // lifecycle.
            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                @Override
                public void onVideoEnd() {
                    // Publishers should allow native ads to complete video playback before
                    // refreshing or replacing them with another ad in the same UI location.
                    *//*refresh.setEnabled(true);
                    videoStatus.setText("Video status: Video playback has ended.");*//*
                    super.onVideoEnd();
                }
            });
        } else {
            *//*videoStatus.setText("Video status: Ad does not contain a video asset.");
            refresh.setEnabled(true);*//*
        }
    }

*/
    private void populateSimpleTemplateAdView(final NativeCustomTemplateAd nativeCustomTemplateAd,
                                              View adView) {
        /*//TextView headline = adView.findViewById(R.id.simplecustom_headline);
        TextView caption = adView.findViewById(R.id.simplecustom_caption);

        //headline.setText(nativeCustomTemplateAd.getText("Headline"));
        caption.setText(nativeCustomTemplateAd.getText("Caption"));*/

        FrameLayout mediaPlaceholder = adView.findViewById(R.id.simplecustom_media_placeholder);

        // Get the video controller for the ad. One will always be provided, even if the ad doesn't
        // have a video asset.
        VideoController vc = nativeCustomTemplateAd.getVideoController();

        // Apps can check the VideoController's hasVideoContent property to determine if the
        // NativeCustomTemplateAd has a video asset.
        if (vc.hasVideoContent()) {
            mediaPlaceholder.addView(nativeCustomTemplateAd.getVideoMediaView());
            /*videoStatus.setText(String.format(Locale.getDefault(),
                    "Video status: Ad contains a %.2f:1 video asset.",
                    vc.getAspectRatio()));*/

            // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
            // VideoController will call methods on this object when events occur in the video
            // lifecycle.
            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                public void onVideoEnd() {
                    // Publishers should allow native ads to complete video playback before
                    // refreshing or replacing them with another ad in the same UI location.
                    /*refresh.setEnabled(true);
                    videoStatus.setText("Video status: Video playback has ended.");*/
                    super.onVideoEnd();
                }
            });
        } else {
            ImageView mainImage = new ImageView(this);
            mainImage.setAdjustViewBounds(true);
            mainImage.setImageDrawable(nativeCustomTemplateAd.getImage("MainImage").getDrawable());

            mainImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nativeCustomTemplateAd.performClick("MainImage");
                }
            });
            mediaPlaceholder.addView(mainImage);
            /*refresh.setEnabled(true);
            videoStatus.setText("Video status: Ad does not contain a video asset.");*/
        }
    }
    private void populateUnifiedNativeAdView(UnifiedNativeAd nativeAd, UnifiedNativeAdView adView) {
        // Set the media view.
        adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));

        // Set other ad assets.
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        // The headline and mediaContent are guaranteed to be in every UnifiedNativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        adView.getMediaView().setMediaContent(nativeAd.getMediaContent());

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        adView.setNativeAd(nativeAd);

        // Get the video controller for the ad. One will always be provided, even if the ad doesn't
        // have a video asset.
        VideoController vc = nativeAd.getVideoController();

        // Updates the UI to say whether or not this ad has a video asset.
        if (vc.hasVideoContent()) {

            // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
            // VideoController will call methods on this object when events occur in the video
            // lifecycle.
            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                @Override
                public void onVideoEnd() {
                    // Publishers should allow native ads to complete video playback before
                    // refreshing or replacing them with another ad in the same UI location.
                    super.onVideoEnd();
                }
            });
        } else {
        }
    }

    void showDialog(String score, int bg_image, String title) {
        FragmentTransaction ft = Objects.requireNonNull(this).getSupportFragmentManager().beginTransaction();
        ft.addToBackStack(null);
        final DialogFragment dialogFragment = DialogBoxFragment.newInstance(title ,score, bg_image);
        dialogFragment.setCancelable(false);
        dialogFragment.show(ft, DIALOG);
    }
}
