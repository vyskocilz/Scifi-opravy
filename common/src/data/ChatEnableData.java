package data;

import java.io.Serializable;

public class ChatEnableData implements Serializable {
    private boolean enabled;

    public ChatEnableData(boolean enabled) {
        this.enabled = enabled;
    }

    public ChatEnableData() {
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
