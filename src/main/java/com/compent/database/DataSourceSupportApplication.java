package com.compent.database;

import com.compent.database.config.DruidConfig;
import com.compent.database.config.MyBatisPlusConfig;
import com.compent.database.property.DynamicDataSourceProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;


/**
 * @author qiaoxiaozhuo
 */
@SpringBootApplication
@EnableConfigurationProperties({DynamicDataSourceProperties.class})
@Import({DynamicDataSourceProperties.class, MyBatisPlusConfig.class, DruidConfig.class})
public class DataSourceSupportApplication {
}
