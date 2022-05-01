package com.urlshortner.utils;
 
import java.io.Serializable;
 
import javax.sql.DataSource;
 
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
 
 
public class UrlShortnerPropertiesUtils extends PropertyPlaceholderConfigurer implements Serializable {
 
 /** The Constant serialVersionUID. */
 private static final long serialVersionUID = 1L;
 
 private static UrlShortnerProperties customProps;
 
 private String dataSourceName;
 
 @Override
 public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
 DataSource dataSource = (DataSource) beanFactory.getBean(getDataSourceName());
 customProps = new UrlShortnerProperties(dataSource);
 setProperties(customProps);
 super.postProcessBeanFactory(beanFactory);
 }
 
 public String getDataSourceName() {
 return dataSourceName;
 }
 
 public void setDataSourceName(String dataSourceName) {
 this.dataSourceName = dataSourceName;
 }
 
 public static String getProperty(String key) {
 return (null == customProps.get(key)) ? "" : customProps.get(key).toString();
 }
}