package fr.homingpigeon.account.infrastructure.entities;

/*import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "friendship")
public class FriendshipEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String friendship_id;

    @ManyToOne
    @JoinColumn(name = "username",nullable = false)
    private AccountEntity user;

    @ManyToOne
    @JoinColumn(name = "username",nullable = false)
    private AccountEntity friend;

    public String getFriendship_id() {
        return friendship_id;
    }

    public void setFriendship_id(String friendship_id) {
        this.friendship_id = friendship_id;
    }

    public AccountEntity getUser() {
        return user;
    }

    public void setUser(AccountEntity user) {
        this.user = user;
    }

    public AccountEntity getFriend() {
        return friend;
    }

    public void setFriend(AccountEntity friend) {
        this.friend = friend;
    }
}*/