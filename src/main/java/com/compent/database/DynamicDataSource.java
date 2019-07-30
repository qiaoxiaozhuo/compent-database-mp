package com.compent.database;

import com.alibaba.druid.pool.DruidDataSource;
import com.compent.database.property.DataSourceProperties;
import com.compent.database.property.DynamicDataSourceProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource implements InitializingBean {

    @Resource
    private DynamicDataSourceProperties dynamicDataSourceProperties;

    @Override
    protected Object determineCurrentLookupKey() {
        return DbContextChoose.getDataSourceType();
    }

    @Override
    public void afterPropertiesSet() {
        try {
            initDynamicDataSources();
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            System.exit(1);
        }

        super.afterPropertiesSet();
    }

    private void initDynamicDataSources() throws SQLException {
        Map<String, DataSourceProperties> dataSourceProperties = dynamicDataSourceProperties.getDataSources();
        if (dataSourceProperties == null) {
            log.info("未配置数据源！");
            return;
        }
        Map<Object, Object> dataSources = new HashMap<>((int) Math.ceil(dataSourceProperties.size() / 0.75));

        DataSource defaultDataSource = null;
        for (Map.Entry<String, DataSourceProperties> entry : dataSourceProperties.entrySet()) {
            String key = entry.getKey();
            DataSourceProperties value = entry.getValue();
            DataSource dataSource = createDataSource(key, value);

            dataSources.put(key, dataSource);

            if (defaultDataSource == null) {
                defaultDataSource = dataSource;
                this.setDefaultTargetDataSource(defaultDataSource);
            }
        }

        this.setTargetDataSources(dataSources);
    }

    /**
     * 配置属性
     * https://github.com/alibaba/druid/wiki/DruidDataSource%E9%85%8D%E7%BD%AE%E5%B1%9E%E6%80%A7%E5%88%97%E8%A1%A8
     * @param dataSourceName
     * @param dataSourceProperties
     * @return
     * @throws SQLException
     */
    private DataSource createDataSource(String dataSourceName, DataSourceProperties dataSourceProperties)
            throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(dataSourceProperties.getUrl());
        dataSource.setUsername(dataSourceProperties.getUsername());
        dataSource.setPassword(dataSourceProperties.getPassword());
        dataSource.setName(dataSourceName);
        setNotNull(dataSource::setInitialSize, dataSourceProperties::getInitialSize);
        setNotNull(dataSource::setMinIdle, dataSourceProperties::getMinIdle);
        setNotNull(dataSource::setMaxActive, dataSourceProperties::getMaxActive);
        setNotNull(dataSource::setMaxWait, dataSourceProperties::getMaxWait);
        setNotNull(dataSource::setQueryTimeout, dataSourceProperties::getQueryTimeout);
        setNotNull(dataSource::setTimeBetweenEvictionRunsMillis, dataSourceProperties::getTimeBetweenEvictionRunsMillis);
        setNotNull(dataSource::setMinEvictableIdleTimeMillis, dataSourceProperties::getMinEvictableIdleTimeMillis);
        setNotNull(dataSource::setValidationQuery, dataSourceProperties::getValidationQuery);
        setNotNull(dataSource::setTestWhileIdle, dataSourceProperties::getTestWhileIdle);
        setNotNull(dataSource::setTestOnBorrow, dataSourceProperties::getTestOnBorrow);
        setNotNull(dataSource::setTestOnReturn, dataSourceProperties::getTestOnReturn);
        setNotNull(dataSource::setPoolPreparedStatements, dataSourceProperties::getPoolPreparedStatements);
        setNotNull(dataSource::setMaxPoolPreparedStatementPerConnectionSize, dataSourceProperties::getMaxPoolPreparedStatementPerConnectionSize);
        setNotNull(dataSource::setUseGlobalDataSourceStat, dataSourceProperties::getUseGlobalDataSourceStat);
        dataSource.setFilters("stat");
        return dataSource;
    }

    private <T> void setNotNull(Consumer<T> consumer, Supplier<T> supplier) {
        T t = supplier.get();
        if (t != null){
            consumer.accept(t);
        }
    }
}
