package fr.homingpigeon.conversation.infrastructure.entities;

import fr.homingpigeon.account.infrastructure.entities.AccountEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "message")
public class MessageEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String message_id;

    @Column(name = "content")
    String content;

    @ManyToOne
    @JoinColumn(name = "username",nullable = false,insertable = false, updatable = false)
    AccountEntity recipient;

    @ManyToOne
    @JoinColumn(name = "username",nullable = false,insertable = false, updatable = false)
    AccountEntity sender;

    @Column(name = "date")
    LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "conversation_id",nullable = false)
    ConversationEntity conversation;

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

    public AccountEntity getRecipient() {
        return recipient;
    }

    public void setRecipient(AccountEntity recipient) {
        this.recipient = recipient;
    }

    public AccountEntity getSender() {
        return sender;
    }

    public void setSender(AccountEntity sender) {
        this.sender = sender;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public ConversationEntity getConversation() {
        return conversation;
    }

    public void setConversation(ConversationEntity conversation) {
        this.conversation = conversation;
    }
}
