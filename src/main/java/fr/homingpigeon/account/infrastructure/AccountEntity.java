package fr.homingpigeon.account.infrastructure;

import fr.homingpigeon.conversation.infrastructure.entities.ConversationEntity;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "account")

public class AccountEntity {
    @Id
    @Column(name = "username",insertable = false,updatable = false)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "public_key")
    private String public_key;

    @ManyToMany
    @JoinTable(
            name = "conversation_member",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "conversation_id"))
    private List<ConversationEntity> conversations;

    @ManyToMany
    @JoinTable(
            name = "friendship_transitive_view",
            joinColumns = @JoinColumn(name = "friend1"),
            inverseJoinColumns = @JoinColumn(name = "friend2"))
    private List<AccountEntity> friendships;

    @ManyToMany
    @JoinTable(
            name = "friend_request",
            joinColumns = @JoinColumn(name = "recipient"),
            inverseJoinColumns = @JoinColumn(name = "applicant"))
    private List<AccountEntity> friend_requests;

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

    public List<ConversationEntity> getConversations() {
        return conversations;
    }

    public void setConversations(
            List<ConversationEntity> conversations) {
        this.conversations = conversations;
    }

    public List<AccountEntity> getFriendships() {
        return friendships;
    }

    public void setFriendships(List<AccountEntity> friendships) {
        this.friendships = friendships;
    }

    public List<AccountEntity> getFriend_requests() {
        return friend_requests;
    }

    public void setFriend_requests(List<AccountEntity> friend_requests) {
        this.friend_requests = friend_requests;
    }
}