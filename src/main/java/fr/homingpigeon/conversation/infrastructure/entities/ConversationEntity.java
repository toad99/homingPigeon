package fr.homingpigeon.conversation.infrastructure.entities;

import fr.homingpigeon.account.infrastructure.entities.AccountEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "conversation")

public class ConversationEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String conversation_id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "conversation")
    private List<MessageEntity> messages;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "conversations")
    private List<AccountEntity> members;

    public String getConversation_id() {
        return conversation_id;
    }

    public void setConversation_id(String conversation_id) {
        this.conversation_id = conversation_id;
    }

    public List<MessageEntity> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageEntity> messages) {
        this.messages = messages;
    }

    public List<AccountEntity> getMembers() {
        return members;
    }

    public void setMembers(List<AccountEntity> members) {
        this.members = members;
    }
}
