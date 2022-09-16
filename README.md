# BaseProjectAd
## 思考
    . 目标：聚合广告sdk实现用户展示广告

    . 操作流程：用户（开发人员）初始化sdk，发起广告请求（请求广告、展示广告），接收到广告请求结果，对结果进行处理。

    . 切入点：广告分发策略（使用哪家广告）、广告的功能、广告结果回调

## 定义的类及接口
    . 定义的类：广告管理器 AdManager、默认广告分发策略 DefaultDistributionPolicyImpl

    . 定义的抽象类：广告功能 AbstractAggregateAd

    . 定义的接口：广告分发策略接口 IDistributionPolicy（IComponentStrategy 命名错误）、广告功能接口 IAd、广告初始化回调接口 InitializationSdkListener、banner广告回调 BannerListener、插屏广告回调 InterstitialListener、激励视频广告回调 RewardedVideoListener、积分墙广告回调 OfferWallListener、原生广告回调（暂未实现）

## 实现
    . 接口
        . IDistributionPolicy
            . 定义的方法
                . 获取要使用的广告
        . IAd
            . 定义的方法
        . InitializationSdkListener
            . 定义的方法
        . BannerListener
            . 定义的方法
        . InterstitialListener
            . 定义的方法
        . RewardedVideoListener
            . 定义的方法
        . OfferWallListener
            . 定义的方法
    . 抽象类
        . AbstractAggregateAd（实现IAd）
            . 实现的功能
                . 获取要使用的广告
    . 类
        . AdManager
            . 使用模式
                . 双重判空单例模式
                . 代理模式
            . 实现功能
                . 初始化聚合的广告sdk
                . 展示Banner
                . 请求插屏
                . 展示插屏
                . 请求视频
                . 展示视频
                . 请求积分墙
                . 展示积分墙
                . 请求原生广告（未实现）
                . 展示原生广告（未实现）

## 自定义分发策略以及广告
1、定义广告接口 IAd。定义初始化 sdk、展示 Banner、加载插屏、展示插屏、加载激励视频、展示激励视频、加载积分墙、展示积分墙接口
2、定义实现广告接口的抽象类 AbstractAd。在初始化 sdk 时，
面向接口编程，创建广告接口和分发策略接口；使用单例模式和代理模式构建广告管理类；策略模式构建广告的分发策略类；
