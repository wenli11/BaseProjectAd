package per.dg.ad;

import per.dg.ad.ad_listener.BannerListener;
import per.dg.ad.ad_listener.InitializationSdkListener;
import per.dg.ad.ad_listener.InterstitialListener;
import per.dg.ad.ad_listener.OfferWallListener;
import per.dg.ad.ad_listener.RewardedVideoListener;

public class DefaultDistributionPolicyImpl extends AbstractDistributionPolicy{

    private InitializationSdkListener initializationSdkListener;

    private BannerListener bannerListener;

    private InterstitialListener interstitialListener;

    private RewardedVideoListener rewardedVideoListener;

    private OfferWallListener offerWallListener;

    public DefaultDistributionPolicyImpl(InitializationSdkListener initializationSdkListener, BannerListener bannerListener,
                                         InterstitialListener interstitialListener, RewardedVideoListener rewardedVideoListener, OfferWallListener offerWallListener){
        this.initializationSdkListener = initializationSdkListener;
        this.bannerListener = bannerListener;
        this.interstitialListener = interstitialListener;
        this.rewardedVideoListener = rewardedVideoListener;
        this.offerWallListener = offerWallListener;
    }

}
