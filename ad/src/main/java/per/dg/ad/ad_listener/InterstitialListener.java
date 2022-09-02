package per.dg.ad.ad_listener;

public interface InterstitialListener {
    void onInterstitialAdReady();

    void onInterstitialAdLoadFailed(String error);

    void onInterstitialAdOpened();

    void onInterstitialAdClosed();

    void onInterstitialAdShowSucceeded();

    void onInterstitialAdShowFailed(String error);

    void onInterstitialAdClicked();
}
