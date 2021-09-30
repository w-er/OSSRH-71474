package com.wencoder.generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;

public class Test {

    public static void main(String[] args) {
        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();

        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();

        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig();

        // 包名策略配置
        PackageConfig packageConfig = new PackageConfig();

        AutoGenerator generator = new AutoGenerator();
        generator.setGlobalConfig(globalConfig);
        generator.setDataSource(dataSourceConfig);
        generator.setStrategy(strategyConfig);
        generator.setPackageInfo(packageConfig);
        generator.execute();

    }
}
