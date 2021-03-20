package fr.homingpigeon.conversation.domain.model;

import fr.homingpigeon.account.domain.model.Account;

import java.util.List;
import java.util.Set;

public class Conversation {
    private String id_conversation;
    private Set<String> members;
    private List<Message> messages;

    public Conversation(String id_conversation, Set<String> members,
                        List<Message> messages) {
        this.id_conversation = id_conversation;
        this.members = members;
        this.messages = messages;
    }

    public String getId_conversation() {
        return id_conversation;
    }

    public Set<String> getMembers() {
        return members;
    }

    public List<Message> getMessages() {
        return messages;
    }
}
