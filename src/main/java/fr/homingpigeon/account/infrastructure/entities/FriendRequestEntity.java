/*package fr.homingpigeon.account.infrastructure.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "friend_request")
public class FriendRequestEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String friendship_id;

    @ManyToOne
    @JoinColumn(name = "username",nullable = false,insertable = false, updatable = false)
    private AccountEntity applicant;

    @ManyToOne
    @JoinColumn(name = "username",nullable = false,insertable = false, updatable = false)
    private AccountEntity recipient;

    public String getFriendship_id() {
        return friendship_id;
    }

    public void setFriendship_id(String friendship_id) {
        this.friendship_id = friendship_id;
    }

    public AccountEntity getApplicant() {
        return applicant;
    }

    public void setApplicant(AccountEntity applicant) {
        this.applicant = applicant;
    }

    public AccountEntity getRecipient() {
        return recipient;
    }

    public void setRecipient(AccountEntity recipient) {
        this.recipient = recipient;
    }
}
*/