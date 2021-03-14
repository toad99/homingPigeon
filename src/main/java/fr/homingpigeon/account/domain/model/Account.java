package fr.homingpigeon.account.domain.model;

import fr.homingpigeon.conversation.domain.model.Conversation;

import java.util.List;


public class Account {
    private String username;
    private String password;
    private String public_key;
    private List<Conversation> conversations;
    private List<String> friendships;
    private List<String> friend_requests;

    public Account(String username, String password, String public_key,
                   List<Conversation> conversations, List<String> friendships,
                   List<String> friend_requests) {
        this.username = username;
        this.password = password;
        this.public_key = public_key;
        this.conversations = conversations;
        this.friendships = friendships;
        this.friend_requests = friend_requests;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPublic_key() {
        return public_key;
    }

    public void setPublic_key(String public_key) {
        this.public_key = public_key;
    }

    public List<Conversation> getConversations() {
        return conversations;
    }

    public void setConversations(List<Conversation> conversations) {
        this.conversations = conversations;
    }

    public List<String> getFriendships() {
        return friendships;
    }

    public void setFriendships(List<String> friendships) {
        this.friendships = friendships;
    }

    public List<String> getFriend_requests() {
        return friend_requests;
    }

    public void setFriend_requests(List<String> friend_requests) {
        this.friend_requests = friend_requests;
    }
}
