package per.dg.ad.ad_listener;

public interface BannerListener {
    void onBannerAdLoaded();

    void onBannerAdLoadFailed(String error);

    void onBannerAdClicked();

    void onBannerAdScreenPresented();

    void onBannerAdScreenDismissed();

    void onBannerAdLeftApplication();
}
