package per.dg.ad;

import android.app.Activity;

import per.dg.ad.ad_listener.InitializationSdkListener;

public interface IAd {
//    初始化 SDK
    void initSdk(Activity activity, boolean showBanner, boolean showInterstitial, boolean showRewardedVideo, boolean showOfferWall, InitializationSdkListener initializationSdkListener);

//    展示插屏广告
    void showInterstitial();

//    展示视频广告
    void showRewardedVideo();

//    展示积分墙
    void showOfferWall();
}
