package com.compent.database.property;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataSourceProperties {

    private String url;
    private String username;
    private String password;

    private Integer initialSize = 5;
    private Integer minIdle = 5;
    private Integer maxActive = 20;
    private Long maxWait = 60000L;
    private Integer queryTimeout;
    private Long timeBetweenEvictionRunsMillis = 60000L;
    private Long minEvictableIdleTimeMillis = 300000L;
    private String validationQuery = "SELECT 1";
    private Boolean testWhileIdle = true;
    private Boolean testOnBorrow = false;
    private Boolean testOnReturn = false;
    private Boolean poolPreparedStatements = true;
    private Integer maxPoolPreparedStatementPerConnectionSize = 20;
    private Boolean useGlobalDataSourceStat = true;




}
