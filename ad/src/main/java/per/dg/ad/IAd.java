package per.dg.ad;

import android.app.Activity;

import per.dg.ad.ad_listener.BannerListener;
import per.dg.ad.ad_listener.InitializationSdkListener;
import per.dg.ad.ad_listener.InterstitialListener;
import per.dg.ad.ad_listener.OfferWallListener;
import per.dg.ad.ad_listener.RewardedVideoListener;

public interface IAd {
//    初始化 SDK
    void initSdk(Activity activity, boolean showBanner, boolean showInterstitial, boolean showRewardedVideo, boolean showOfferWall, InitializationSdkListener initializationSdkListener);

    void initSdk(Activity activity, InitializationSdkListener initializationSdkListener, BannerListener bannerListener,
                 InterstitialListener interstitialListener, RewardedVideoListener rewardedVideoListener, OfferWallListener offerWallListener);

//    展示插屏广告
    void showInterstitial();

//    展示视频广告
    void showRewardedVideo();

//    展示积分墙
    void showOfferWall();
}
