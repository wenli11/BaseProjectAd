package per.dg.ad;

import java.util.ArrayList;
import java.util.List;

public interface IDistributionPolicy {

    List<IAd> prepareAdList = new ArrayList<>();

    List<IAd> preppedAdList = new ArrayList<>();

    void onSdkInitSuccess(IAd ad);

    void a();

    IAd getAd();

}
