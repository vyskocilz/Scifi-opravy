package chat;

import data.ChatEnableData;
import server.ServerUtils;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * User: Zdenec
 */
public class ChatServerData {

    private boolean chatEnabled = false;
    private static final String CHAT_ENABLE_TITLE = "Zapnout chat";
    private static final String CHAT_DISABLE_TITLE = "Vypnout chat";
    private String chatButtonTitle = CHAT_ENABLE_TITLE;

    public String getChatButtonTitle() {
        return chatButtonTitle;
    }

    public void setChatButtonTitle(String chatButtonTitle) {
        String old = this.chatButtonTitle;
        this.chatButtonTitle = chatButtonTitle;
        changeSupport.firePropertyChange("chatButtonTitle", old, chatButtonTitle);
    }

    public boolean isChatEnabled() {
        return chatEnabled;
    }

    public void setChatEnabled(boolean chatEnabled) {
        boolean old = this.chatEnabled;
        this.chatEnabled = chatEnabled;
        changeSupport.firePropertyChange("chatEnabled", old, chatEnabled);
        if (chatEnabled) {
            setChatButtonTitle(CHAT_DISABLE_TITLE);
        } else {
            setChatButtonTitle(CHAT_ENABLE_TITLE);
        }
        ServerUtils.getClientGroup().broadcast(new ChatEnableData(chatEnabled));
    }

    private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
}
