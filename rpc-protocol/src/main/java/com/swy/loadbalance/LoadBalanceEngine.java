package com.swy.loadbalance;

import com.swy.loadbalance.impl.HashLoadStrategy;
import com.swy.loadbalance.impl.PollLoadStrategy;
import com.swy.loadbalance.impl.RandomLoadStrategy;

import java.util.HashMap;
import java.util.Map;

public class LoadBalanceEngine {

    private static final Map<LoadBalanceEnum,LoadStrategy> loadBalanceMap = new HashMap<>();

    static {
        loadBalanceMap.put(LoadBalanceEnum.Random, new RandomLoadStrategy());
        loadBalanceMap.put(LoadBalanceEnum.Hash, new HashLoadStrategy());
        loadBalanceMap.put(LoadBalanceEnum.Polling, new PollLoadStrategy());
    }

    public static LoadStrategy queryLoadStrategy(String loadStrategy) {
        LoadBalanceEnum loadBalanceEnum = LoadBalanceEnum.queryByCode(loadStrategy);
        if (loadBalanceEnum == null) {
            //默认选择随机算法
            return new RandomLoadStrategy();
        }

        return loadBalanceMap.get(loadBalanceEnum);
    }
}
