package utilities;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class Configuration {

        private static PropertiesConfiguration configuration;
        static
        {
            try {
                configuration = new PropertiesConfiguration("src/main/java/configurations/config.properties");
            } catch (ConfigurationException e)
            {
                e.printStackTrace();
            }
        }
       
        public String getProperty(String key) {
            return configuration.getString(key);

        }

    }
