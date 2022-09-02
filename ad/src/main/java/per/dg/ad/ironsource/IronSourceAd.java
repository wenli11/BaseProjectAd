package per.dg.ad.ironsource;

import android.app.Activity;

import com.ironsource.adapters.supersonicads.SupersonicConfig;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.sdk.InitializationListener;

import per.dg.ad.IAd;
import per.dg.ad.ad_listener.InitializationSdkListener;

public class IronSourceAd implements IAd {

    private static final String ironSourceKey = "85460dcd";

    @Override
    public void initSdk(Activity activity, boolean showBanner, boolean showInterstitial, boolean showRewardedVideo, boolean showOfferWall, InitializationSdkListener initializationSdkListener) {
        if(showInterstitial)
            IronSource.setInterstitialListener(new DefaultInterstitialListener());
        if(showRewardedVideo)
            IronSource.setRewardedVideoListener(new DefaultRewardedVideoListener());
        if(showOfferWall) {
            IronSource.setRewardedVideoListener(new DefaultRewardedVideoListener());
            SupersonicConfig.getConfigObj().setClientSideCallbacks(true);
        }

        IronSource.init(activity, ironSourceKey, new InitializationListener() {
            @Override
            public void onInitializationComplete() {
                initializationSdkListener.onInitializationSdkComplete(IronSourceAd.this);
                loadAd();
            }
        });
    }

    private void loadAd(){
        IronSource.loadInterstitial();
    }

    @Override
    public void showInterstitial() {
        IronSource.showInterstitial();
    }

    @Override
    public void showRewardedVideo() {
        IronSource.showRewardedVideo();
    }

    @Override
    public void showOfferWall() {
        IronSource.showOfferwall();
    }
}
