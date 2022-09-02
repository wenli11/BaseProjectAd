package per.dg.ad;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import per.dg.ad.ad_listener.InitializationSdkListener;
import per.dg.ad.ironsource.IronSourceAd;

public class AdManager implements IAd{

    private static AdManager INSTANCE;

    public static AdManager getInstance(){
        if(INSTANCE == null){
            synchronized (AdManager.class){
                if(INSTANCE == null){
                    INSTANCE = new AdManager();
                }
            }
        }
        return INSTANCE;
    }

    List<IAd> prepareAdList;
    List<IAd> preppedAdList;

    boolean hasInit;

    InitializationSdkListener parentInitializationSdkListener;

    InitializationSdkListener initializationSdkListener = new InitializationSdkListener() {
        @Override
        public void onInitializationSdkComplete(IAd ad) {
            for(IAd iad: prepareAdList){
                if(iad.equals(ad)){
                    prepareAdList.remove(iad);
                    preppedAdList.add(iad);
                    break;
                }
            }
            if(!hasInit){
                parentInitializationSdkListener.onInitializationSdkComplete(AdManager.this);
                hasInit = true;
            }
        }
    };

    private AdManager(){
        prepareAdList = new ArrayList<>();
        preppedAdList = new ArrayList<>();

        prepareAdList.add(new IronSourceAd());
    }

    @Override
    public void initSdk(Activity activity, boolean showBanner, boolean showInterstitial, boolean showRewardedVideo, boolean showOfferWall, InitializationSdkListener initializationSdkListener) {
        for (IAd ad: prepareAdList) {
            ad.initSdk(activity, showBanner, showInterstitial, showRewardedVideo, showOfferWall, this.initializationSdkListener);
        }
        parentInitializationSdkListener = initializationSdkListener;
    }

    @Override
    public void showInterstitial() {
        preppedAdList.get(0).showInterstitial();
    }

    @Override
    public void showRewardedVideo() {
        preppedAdList.get(0).showRewardedVideo();
    }

    @Override
    public void showOfferWall() {
        preppedAdList.get(0).showOfferWall();
    }
}
