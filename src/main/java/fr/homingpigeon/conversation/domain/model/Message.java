package fr.homingpigeon.conversation.domain.model;

import java.time.LocalDateTime;

public class Message {
    private String message_id;
    private String content;
    private String recipient;
    private String sender;
    private LocalDateTime date;
    private String conversation_id;

    public Message(String message_id, String content, String recipient, String sender, LocalDateTime date,
                   String conversation_id) {
        this.message_id = message_id;
        this.content = content;
        this.recipient = recipient;
        this.sender = sender;
        this.date = date;
        this.conversation_id = conversation_id;
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getConversation_id() {
        return conversation_id;
    }

    public void setConversation_id(String conversation_id) {
        this.conversation_id = conversation_id;
    }
}
