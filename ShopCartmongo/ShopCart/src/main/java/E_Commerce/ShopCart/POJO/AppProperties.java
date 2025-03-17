package E_Commerce.ShopCart.POJO;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.config")
public class AppProperties {

    private String name;
    private String version;
    private int port;
    private boolean maintenanceMode;

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }

    public int getPort() { return port; }
    public void setPort(int port) { this.port = port; }

    public boolean isMaintenanceMode() { return maintenanceMode; }
    public void setMaintenanceMode(boolean maintenanceMode) { this.maintenanceMode = maintenanceMode; }

    public void printConfig() {
        System.out.println("App Name: " + name);
        System.out.println("App Version: " + version);
        System.out.println("App Port: " + port);
        System.out.println("Maintenance Mode: " + maintenanceMode);
    }
}

