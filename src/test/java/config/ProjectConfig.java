package config;

import org.aeonbits.owner.Config;

public interface ProjectConfig extends Config {
    @DefaultValue("Windows")
    String os();

    @DefaultValue("10")
    String os_version();

    @DefaultValue("80.0")
    String browser_version();

    @DefaultValue("Chrome")
    String browser();

    @DefaultValue("https://habr.com/ru/")
    String hostname();
}
