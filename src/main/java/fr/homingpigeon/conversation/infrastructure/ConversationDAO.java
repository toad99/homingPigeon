package fr.homingpigeon.conversation.infrastructure;

import fr.homingpigeon.conversation.infrastructure.entities.ConversationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationDAO extends JpaRepository<ConversationEntity,String> {

}
