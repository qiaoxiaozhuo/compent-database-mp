package com.compent.database.config;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.compent.database.handler.CustomMetaObjectHandler;
import com.compent.database.handler.MyInjector;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author qiaoxiaozhuo
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"com.XX.dao"})
public class MyBatisPlusConfig implements TransactionManagementConfigurer {

    @Resource
    private DataSource dataSource;



    @Bean
    public GlobalConfig globalConfig() {
        GlobalConfig conf = new GlobalConfig();
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        dbConfig.setLogicDeleteValue("1");
        dbConfig.setLogicNotDeleteValue("0");
        dbConfig.setIdType(IdType.INPUT);
        conf.setDbConfig(dbConfig);
        return conf;
    }

    /**
     * 分页
     *
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }


    /**
     * 自动注入
     *
     * @return
     */
    @Bean
    @Primary
    public ISqlInjector myInjector() {
        return new MyInjector();
    }


    /**
     * 自动填充
     *
     * @return
     */
    @Bean
    @Primary
    public MetaObjectHandler metaObjectHandler() {
        return new CustomMetaObjectHandler();
    }


    /**
     * 乐观锁
     *
     * @return
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }



    @Bean
    public PerformanceInterceptor performanceInterceptor(){
        return new PerformanceInterceptor();
    }





    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}
