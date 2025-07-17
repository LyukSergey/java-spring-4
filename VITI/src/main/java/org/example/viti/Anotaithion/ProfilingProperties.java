package org.example.viti.Anotaithion;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "profiling")
public class ProfilingProperties {
    private boolean enabled = false; // За замовчуванням вимкнено

    // Ми не використовуємо Lombok, тому потрібно вручну створити getter і setter

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
