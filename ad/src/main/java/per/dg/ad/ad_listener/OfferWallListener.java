package per.dg.ad.ad_listener;

import com.ironsource.mediationsdk.logger.IronSourceError;

public interface OfferWallListener {
    void onOfferWallAvailable(boolean available);

    void onOfferWallOpened();

    void onOfferWallShowFailed(String error);

    boolean onOfferWallAdCredited(int credits, int totalCredits, boolean totalCreditsFlag);

    void onGetOfferWallCreditsFailed(String error);

    void onOfferWallClosed();
}
