package fr.homingpigeon.conversation.domain.model;

import fr.homingpigeon.account.domain.model.Account;

import java.time.LocalDateTime;

public class Message {
    private String id_conversation;
    private String content;
    private Account recipient;
    private Account sender;
    private LocalDateTime date;
    private Conversation conversation;

    public Message(String id_conversation, String content, Account recipient,
                   Account sender, LocalDateTime date, Conversation conversation) {
        this.id_conversation = id_conversation;
        this.content = content;
        this.recipient = recipient;
        this.sender = sender;
        this.date = date;
        this.conversation = conversation;
    }

    public String getId_conversation() {
        return id_conversation;
    }

    public void setId_conversation(String id_conversation) {
        this.id_conversation = id_conversation;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Account getRecipient() {
        return recipient;
    }

    public void setRecipient(Account recipient) {
        this.recipient = recipient;
    }

    public Account getSender() {
        return sender;
    }

    public void setSender(Account sender) {
        this.sender = sender;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }
}
