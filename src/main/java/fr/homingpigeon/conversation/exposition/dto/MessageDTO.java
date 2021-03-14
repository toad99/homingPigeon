package fr.homingpigeon.conversation.exposition.dto;

import java.time.LocalDateTime;

public class MessageDTO {
    private String id_conversation;
    private String content;
    private ConversationMemberDTO recipient;
    private ConversationMemberDTO sender;
    private LocalDateTime date;

    public MessageDTO(){}

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

    public ConversationMemberDTO getRecipient() {
        return recipient;
    }

    public void setRecipient(ConversationMemberDTO recipient) {
        this.recipient = recipient;
    }

    public ConversationMemberDTO getSender() {
        return sender;
    }

    public void setSender(ConversationMemberDTO sender) {
        this.sender = sender;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
