package fr.homingpigeon.conversation.domain.model;

import fr.homingpigeon.account.domain.model.Account;

import java.util.List;

public class Conversation {
    private String id_conversation;
    private List<Account> members;
    private List<Message> messages;

    public Conversation(String id_conversation,
                        List<Account> members,
                        List<Message> messages) {
        this.id_conversation = id_conversation;
        this.members = members;
        this.messages = messages;
    }

    public String getId_conversation() {
        return id_conversation;
    }

    public void setId_conversation(String id_conversation) {
        this.id_conversation = id_conversation;
    }

    public List<Account> getMembers() {
        return members;
    }

    public void setMembers(List<Account> members) {
        this.members = members;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
