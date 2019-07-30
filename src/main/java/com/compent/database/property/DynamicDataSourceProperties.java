package com.compent.database.property;

import com.compent.database.DynamicDataSource;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.util.Map;

@Import(DynamicDataSource.class)
@ConfigurationProperties(prefix = "compent")
@Getter
@Setter
@Component
public class DynamicDataSourceProperties {

    /**
     * key:   dataSourceName
     * value: DataSourceProperties
     */
    Map<String, DataSourceProperties> dataSources;
}
