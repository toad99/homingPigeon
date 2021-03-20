package fr.homingpigeon.conversation.infrastructure;

import fr.homingpigeon.conversation.infrastructure.entities.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageDAO extends JpaRepository<MessageEntity,String> {

}
