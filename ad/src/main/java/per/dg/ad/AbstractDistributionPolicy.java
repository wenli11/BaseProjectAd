package per.dg.ad;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractDistributionPolicy implements IDistributionPolicy {

    private Map<Class, AbstractAggregateAd> prepareAdMap = new HashMap<>();

    public void add(AbstractAggregateAd aggregateAd) {
        if(prepareAdMap.get(aggregateAd.getClass()) != null){
            prepareAdMap.put(aggregateAd.getClass(), aggregateAd);
        }else {
            new Throwable("已添加该渠道广告！");
        }
    }
}
