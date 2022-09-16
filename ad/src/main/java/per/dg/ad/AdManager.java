package per.dg.ad;

import android.app.Activity;
import android.util.ArrayMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import per.dg.ad.ad_listener.BannerListener;
import per.dg.ad.ad_listener.InitializationSdkListener;
import per.dg.ad.ad_listener.InterstitialListener;
import per.dg.ad.ad_listener.OfferWallListener;
import per.dg.ad.ad_listener.RewardedVideoListener;
import per.dg.ad.ironsource.IronSourceAd;

public class AdManager{

//    private static AdManager INSTANCE;
//
//    public static AdManager getInstance(){
//        if(INSTANCE == null){
//            synchronized (AdManager.class){
//                if(INSTANCE == null){
//                    INSTANCE = new AdManager();
//                }
//            }
//        }
//        return INSTANCE;
//    }

    private IDistributionPolicy distributionPolicy;

    private InitializationSdkListener initializationSdkListener;

    private BannerListener bannerListener;

    private InterstitialListener interstitialListener;

    private RewardedVideoListener rewardedVideoListener;

    private OfferWallListener offerWallListener;

    private AdManager(IDistributionPolicy distributionPolicy){
//    , InitializationSdkListener initializationSdkListener, BannerListener bannerListener,
//                      InterstitialListener interstitialListener, RewardedVideoListener rewardedVideoListener, OfferWallListener offerWallListener){

        this.distributionPolicy = distributionPolicy;
//        this.initializationSdkListener = initializationSdkListener;
//        this.bannerListener = bannerListener;
//        this.interstitialListener = interstitialListener;
//        this.rewardedVideoListener = rewardedVideoListener;
//        this.offerWallListener = offerWallListener;

        AdChannelConfiguration.adChannel(this);
    }

    public void initSdk(Activity activity){
        distributionPolicy
    }

    public void showInterstitial() {
        if(interstitialListener == null){
            new Throwable("请设置 InterstitialListener！\n new AdManager.Builder().setInitializationSdkListener()");
        }
        if(distributionPolicy != null){
            distributionPolicy.getAd().showInterstitial();
        }
    }

    public void showRewardedVideo() {
        if(rewardedVideoListener == null){
            new Throwable("请设置 RewardedVideoListener！\n new AdManager.Builder().setRewardedVideoListener()");
        }
        if(distributionPolicy != null){
            distributionPolicy.getAd().showRewardedVideo();
        }
    }

    public void showOfferWall() {
        if(offerWallListener == null){
            new Throwable("请设置 OfferWallListener！\n new AdManager.Builder().setOfferWallListener()");
        }
        if(distributionPolicy != null){
            distributionPolicy.getAd().showOfferWall();
        }
    }

    public static class Builder{

        private IDistributionPolicy distributionPolicy;

        private InitializationSdkListener initializationSdkListener;

        private BannerListener bannerListener;

        private InterstitialListener interstitialListener;

        private RewardedVideoListener rewardedVideoListener;

        private OfferWallListener offerWallListener;

        public Builder setDistributionPolicy(IDistributionPolicy distributionPolicy) {
            this.distributionPolicy = distributionPolicy;
            return this;
        }

        public Builder setInitializationSdkListener(InitializationSdkListener initializationSdkListener) {
            this.initializationSdkListener = initializationSdkListener;
            return this;
        }

        public Builder setBannerListener(BannerListener bannerListener) {
            this.bannerListener = bannerListener;
            return this;
        }

        public Builder setInterstitialListener(InterstitialListener interstitialListener) {
            this.interstitialListener = interstitialListener;
            return this;
        }

        public Builder setRewardedVideoListener(RewardedVideoListener rewardedVideoListener) {
            this.rewardedVideoListener = rewardedVideoListener;
            return this;
        }

        public Builder setOfferWallListener(OfferWallListener offerWallListener) {
            this.offerWallListener = offerWallListener;
            return this;
        }

        public AdManager build(){
            if(distributionPolicy == null){
                distributionPolicy = new DefaultDistributionPolicyImpl(initializationSdkListener, bannerListener, interstitialListener, rewardedVideoListener, offerWallListener);
            }
            return new AdManager(distributionPolicy);
        }
    }
}
