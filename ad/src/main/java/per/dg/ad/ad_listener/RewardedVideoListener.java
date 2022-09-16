package per.dg.ad.ad_listener;

import com.ironsource.mediationsdk.model.Placement;

public interface RewardedVideoListener {

    void onRewardedVideoAdOpened();

    void onRewardedVideoAdClosed();

    void onRewardedVideoAvailabilityChanged(boolean var1);

    void onRewardedVideoAdStarted();

    void onRewardedVideoAdEnded();

    void onRewardedVideoAdRewarded(String var1);

    void onRewardedVideoAdShowFailed(String error);

    void onRewardedVideoAdClicked(String var1);

}
