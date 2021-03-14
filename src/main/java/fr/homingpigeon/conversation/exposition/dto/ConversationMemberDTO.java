package fr.homingpigeon.conversation.exposition.dto;

import fr.homingpigeon.account.domain.model.Account;
import fr.homingpigeon.conversation.domain.model.Conversation;

public class ConversationMemberDTO {
    String conversation_member_id;
    Account account;
    Conversation conversation;

    public ConversationMemberDTO() {}

    public String getConversation_member_id() {
        return conversation_member_id;
    }

    public void setConversation_member_id(String conversation_member_id) {
        this.conversation_member_id = conversation_member_id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }
}
