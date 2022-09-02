package per.dg.ad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import per.dg.ad.ad_listener.InitializationSdkListener;

public class MainActivity extends AppCompatActivity {

    AdManager adManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        initData();
    }

    private void initData(){
        adManager = AdManager.getInstance();
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_init_sdk:
                adManager.initSdk(this, true, true, true, true, new InitializationSdkListener() {
                    @Override
                    public void onInitializationSdkComplete(IAd ad) {

                    }
                });
                break;
            case R.id.bt_show_in:
                adManager.showInterstitial();
                break;
            case R.id.bt_show_re:
                adManager.showRewardedVideo();
                break;
            case R.id.bt_show_offer:
                adManager.showOfferWall();
                break;
        }
    }
}