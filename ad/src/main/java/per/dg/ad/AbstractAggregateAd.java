package per.dg.ad;

public abstract class AbstractAggregateAd implements IAd {

    private AdChannelInfo adChannelInfo;

    public AbstractAggregateAd(AbstractDistributionPolicy distributionPolicy){
        if (isAddToAggregateAd())
            add(distributionPolicy);
    }

    private void add(AbstractDistributionPolicy distributionPolicy){
        distributionPolicy.add(this);
    }

    abstract boolean isAddToAggregateAd();

    public void setAdChannelInfo(AdChannelInfo adChannelInfo){
        this.adChannelInfo = adChannelInfo;
    }

}
