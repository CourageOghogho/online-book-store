package com.interswitchng.onlinebookstore.config;

import com.zaxxer.hikari.HikariDataSource;
import java.io.IOException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatasourceConfig {

  @Value("${spring.datasource.driver-class-name}")
  private String datasourceDriverClassName;
  @Value("${spring.datasource.url}")
  private String datasourceUrl;
  @Value("${spring.datasource.username}")
  private String datasourceUsername;
  @Value("${spring.datasource.password}")
  private String datasourcePassword;
  @Value("${spring.datasource.hikari.maximum-pool-size}")
  private int maxPoolSize;


  @Bean(name = "onlineBookStoreDs")
  public DataSource datasource() throws IOException {

    final HikariDataSource ds = new HikariDataSource();
    ds.setDriverClassName(datasourceDriverClassName);
    ds.setJdbcUrl(datasourceUrl);
    ds.setUsername(datasourceUsername);
    ds.setPassword(this.datasourcePassword);
    ds.setMaximumPoolSize(maxPoolSize);

    return ds;
  }

}
