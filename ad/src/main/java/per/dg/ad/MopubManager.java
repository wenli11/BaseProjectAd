package per.dg.ad;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

//import com.cards.solitaire.fungame.utils.livedata.LiveDataBus;
//import com.cards.solitaire.fungame.utils.livedata.LiveDataBusKey;
//import com.cards.solitaire.fungame.utils.network.model.AccountInfo;
import com.ironsource.adapters.supersonicads.SupersonicConfig;
import com.ironsource.mediationsdk.ISBannerSize;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.IronSourceBannerLayout;
import com.ironsource.mediationsdk.integration.IntegrationHelper;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.model.Placement;
import com.ironsource.mediationsdk.sdk.BannerListener;
import com.ironsource.mediationsdk.sdk.InterstitialListener;
import com.ironsource.mediationsdk.sdk.OfferwallListener;
import com.ironsource.mediationsdk.sdk.RewardedVideoListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MopubManager {

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            initInterstitial();
        }
    };

    public static MopubManager INSTANCE;

    private int ad = 0;

    private boolean sdkHasInit = false;

    private static String ironSourceKey = "15404e4c9";
//    private static String ironSourceKey = "85460dcd";

    private static String pkg = "";

    private String userId = "";

    public static MopubManager getInstance(Context context){
        if(INSTANCE == null){
            synchronized (MopubManager.class){
                if(INSTANCE == null){
                    INSTANCE = new MopubManager(context);
                }
            }
        }
        return INSTANCE;
    }

    private Context context;

    private FrameLayout bannerContainer;

    IronSourceBannerLayout banner;

    private long showInterstitialTime = 0;

    private AdClosedListener adClosedListener;

    private void getKey(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL mUrl = null;
                String strResult = null;
                BufferedReader reader = null;
                HttpURLConnection mHttpUrlConnection = null;
                try {
                    mUrl = new URL("https://mfb.us-east-1.linodeobjects.com/" + pkg + ".txt");
//                    mUrl = new URL("https://mfb.us-east-1.linodeobjects.com/com.blockpuzzle.freegames.jewelscrush.txt");
                    mHttpUrlConnection = (HttpURLConnection) mUrl.openConnection();
                    mHttpUrlConnection.setConnectTimeout(10000);// 设置连接超时
                    mHttpUrlConnection.setRequestMethod("GET");// get方式 发起请求
                    mHttpUrlConnection.connect();

//                    if (mHttpUrlConnection.getResponseCode() != 200) {
//                        throw new RuntimeException("Fail to request url");
//                    }

//                    byte[] result;
                    StringBuffer buffer=new StringBuffer();
//                    InputStream mInputStream = mHttpUrlConnection.getInputStream();// 得到网络返回的流
                    reader=new BufferedReader(new InputStreamReader(mHttpUrlConnection.getInputStream(),"utf-8"));
//                    result = new byte[mInputStream.available()];
//                    System.out.println("input 的长度:" + mInputStream.available());
//                    mInputStream.read(result);
                    String lines=null;
                    while ((lines=reader.readLine())!=null){
                        lines=new String(lines.getBytes(),"utf-8");
                        buffer=buffer.append(lines+"\n");
                    }
                    reader.close();


                    strResult = buffer.toString();
                    if(strResult != null && strResult.contains("#")){
                        String[] adInfos = strResult.split("#");
                        if (adInfos[0].equals("i"))
                        {
                            ad = 0;
                            ironSourceKey = adInfos[1];
                            ironSourceKey = ironSourceKey.replace("\n", "");
                        }
                        else if (adInfos[0].equals("m"))
                        {
                            ad = 1;
                            if (adInfos[1].contains("-"))
                            {


                            }
                        }
                    }
                    Log.e(TAG, "run: "+strResult );
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(mHttpUrlConnection != null) {
                        mHttpUrlConnection.disconnect();
                    }
                    if(strResult == null || !strResult.contains("#")){

                    }
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initAd();
                        }
                    });
                }
            }
        }).start();
    }

    private MopubManager(Context context){
        pkg = context.getPackageName();
        getKey();
        this.context = context;
    }

    private void initAd(){
        if(ad == 0){
            initIronSource();
        }else if(ad == 1){

        }
    }

    public void setUserId(String userId) {
        if(this.userId != userId){
            sdkHasInit = false;
            this.userId = userId;
            initAd();
        }
    }

    private void initIronSource() {
        if(sdkHasInit){
            return;
        }

        SupersonicConfig.getConfigObj().setClientSideCallbacks(true);

        IronSource.setInterstitialListener(new InterstitialListener() {
            @Override
            public void onInterstitialAdReady() {
                Log.e(TAG, "onInterstitialAdReady: " );
                Log.e(TAG, "onInterstitialAdReady: "+IronSource.isInterstitialReady() );
            }

            @Override
            public void onInterstitialAdLoadFailed(IronSourceError ironSourceError) {
                Log.e(TAG, "onInterstitialAdLoadFailed: error message:"+ironSourceError.getErrorMessage()+"\n"+"error code："+ironSourceError.getErrorCode() );
                handler.sendEmptyMessageDelayed(0, 1000*2);
            }

            @Override
            public void onInterstitialAdOpened() {
                Log.e(TAG, "onInterstitialAdOpened: ");
            }

            @Override
            public void onInterstitialAdClosed() {
                Log.e(TAG, "onInterstitialAdClosed: ");
                initInterstitial();
                if(adClosedListener != null){
                    adClosedListener.onAdClosedListener();
                }
            }

            @Override
            public void onInterstitialAdShowSucceeded() {
                Log.e(TAG, "onInterstitialAdShowSucceeded: ");

            }

            @Override
            public void onInterstitialAdShowFailed(IronSourceError ironSourceError) {
                Log.e(TAG, "onInterstitialAdShowFailed: ");
                initInterstitial();
            }

            @Override
            public void onInterstitialAdClicked() {
                Log.e(TAG, "onInterstitialAdClicked: ");
            }
        });

        IronSource.setOfferwallListener(new OfferwallListener() {
            @Override
            public void onOfferwallAvailable(boolean isAvailable) {
                Log.d(TAG, "onOfferwallAvailable: "+isAvailable);
            }
            @Override
            public void onOfferwallOpened() {
                Log.d(TAG, "onOfferwallOpened: ");
            }

            @Override
            public void onOfferwallShowFailed(IronSourceError error) {
                Log.d(TAG, "onOfferwallShowFailed: ");
            }
            @Override
            public boolean onOfferwallAdCredited(int credits, int totalCredits, boolean totalCreditsFlag) {
                Log.d(TAG, "onOfferwallAdCredited: ");
//                LiveDataBus.getInstance().with(LiveDataBusKey.ACCOUNT_INFO, AccountInfo.class).postValue(null);
                return true;
            }
            @Override
            public void onGetOfferwallCreditsFailed(IronSourceError error) {
                Log.d(TAG, "onGetOfferwallCreditsFailed: "+error);
            }
            @Override
            public void onOfferwallClosed() {
                Log.d(TAG, "onOfferwallClosed: ");
            }
        });

        IronSource.setRewardedVideoListener(new RewardedVideoListener() {
            @Override
            public void onRewardedVideoAdOpened() {
                Log.d(TAG, "onRewardedVideoAdOpened: ");
            }

            @Override
            public void onRewardedVideoAdClosed() {
                Log.d(TAG, "onRewardedVideoAdClosed: ");
            }

            @Override
            public void onRewardedVideoAvailabilityChanged(boolean b) {
                Log.d(TAG, "onRewardedVideoAvailabilityChanged: "+b);
            }

            @Override
            public void onRewardedVideoAdStarted() {
                Log.d(TAG, "onRewardedVideoAdStarted: ");
            }

            @Override
            public void onRewardedVideoAdEnded() {
                Log.d(TAG, "onRewardedVideoAdEnded: ");
            }

            @Override
            public void onRewardedVideoAdRewarded(Placement placement) {
                Log.d(TAG, "onRewardedVideoAdRewarded: "+placement);
            }

            @Override
            public void onRewardedVideoAdShowFailed(IronSourceError ironSourceError) {
                Log.d(TAG, "onRewardedVideoAdShowFailed: "+ironSourceError);
            }

            @Override
            public void onRewardedVideoAdClicked(Placement placement) {
                Log.d(TAG, "onRewardedVideoAdClicked: "+placement);
            }
        });

        if(userId == null || "".equals(userId) || ironSourceKey == null || "".equals(ironSourceKey)){
            return;
        }

        IronSource.setUserId(userId);
        Activity activity = (Activity) context;
        IronSource.init(activity, ironSourceKey);

        Log.d(TAG, "initIronSource: userId="+userId+"/ironSourceKey="+ironSourceKey);

        sdkHasInit = true;

        IntegrationHelper.validateIntegration(activity);

        initBanner();
        initInterstitial();
    }

    public MopubManager setIronSourceBanner(FrameLayout bannerContainer) {
        if(this.bannerContainer != null){
            this.bannerContainer.removeAllViews();
            IronSource.destroyBanner(banner);
        }
        this.bannerContainer = bannerContainer;
        if(sdkHasInit){
            initBanner();
        }
        return this;
    }


    private void initBanner(){
        if(ad == 0){
            if(bannerContainer == null){
                return;
            }
            banner = IronSource.createBanner((Activity) context, ISBannerSize.BANNER);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT);
            bannerContainer.addView(banner, 0, layoutParams);

            banner.setBannerListener(new BannerListener() {
                @Override
                public void onBannerAdLoaded() {
                    // Called after a banner ad has been successfully loaded
                    Log.e(TAG, "onBannerAdLoaded: ");
                }

                @Override
                public void onBannerAdLoadFailed(IronSourceError error) {
                    Log.e(TAG, "onBannerAdLoadFailed: "+error);
                    // Called after a banner has attempted to load an ad but failed.
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bannerContainer.removeAllViews();
                        }
                    });
                    initBanner();
                }

                @Override
                public void onBannerAdClicked() {
                    // Called after a banner has been clicked.
                    Log.e(TAG, "onBannerAdClicked: " );
                }

                @Override
                public void onBannerAdScreenPresented() {
                    // Called when a banner is about to present a full screen content.
                    Log.e(TAG, "onBannerAdScreenPresented: ");
                }

                @Override
                public void onBannerAdScreenDismissed() {
                    // Called after a full screen content has been dismissed
                    Log.e(TAG, "onBannerAdScreenDismissed: ");
                }

                @Override
                public void onBannerAdLeftApplication() {
                    // Called when a user would be taken out of the application context.
                    Log.e(TAG, "onBannerAdLeftApplication: ");
                }
            });

            IronSource.loadBanner(banner);
        }else if(ad == 1){

        }

    }

    private void initInterstitial(){
        if(ad == 0){
            if(!IronSource.isInterstitialReady()){
                IronSource.loadInterstitial();
                Log.e(TAG, "initInterstitial: " );
            }
        }else if(ad == 1){

        }
    }

    public boolean showInterstitial(){

        long l = System.currentTimeMillis();
        if(showInterstitialTime == 0 || l - showInterstitialTime > 60*1000){
            if(ad == 0){
                Log.e(TAG, "showInterstitial: "+IronSource.isInterstitialReady() );
                if(IronSource.isInterstitialReady()){
                    IronSource.showInterstitial();
                    showInterstitialTime = l;
                    return true;
                }else {
                    initInterstitial();
                }
            }else if(ad == 1){

            }
        }
        if(adClosedListener != null){
            adClosedListener.onAdClosedListener();
        }
        return false;
    }

    public boolean showInterstitial(AdClosedListener adClosedListener){
        this.adClosedListener = adClosedListener;
        long l = System.currentTimeMillis();
        if(showInterstitialTime == 0 || l - showInterstitialTime > 60*1000){
            if(ad == 0){
                if(IronSource.isInterstitialReady()){
                    IronSource.showInterstitial();
                    showInterstitialTime = l;
                    return true;
                }else {
                    initInterstitial();
                }
            }else if(ad == 1){

            }
        }
        if(adClosedListener != null){
            adClosedListener.onAdClosedListener();
        }
        return false;
    }

    public void showOfferwall(){
        if(IronSource.isOfferwallAvailable()) {
            IronSource.showOfferwall();
            Log.d(TAG, "showOfferwall: has show");
        }else {
            Log.d(TAG, "showOfferwall: not loaded");
        }
    }

    public void showRewardVideo(){
        if(IronSource.isRewardedVideoAvailable()){
            IronSource.showRewardedVideo(null);
        }else {
            Log.d(TAG, "showRewardVideo: not loaded");
        }
    }

    public interface AdClosedListener{
        void onAdClosedListener();
    }
}
