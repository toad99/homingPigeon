package fr.homingpigeon.conversation.infrastructure;

import fr.homingpigeon.common.AsymmetricCryptography;
import fr.homingpigeon.conversation.domain.model.Message;
import fr.homingpigeon.conversation.infrastructure.entities.MessageEntity;
import fr.homingpigeon.conversation.mappers.MessageMapper;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Repository;

import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

@Repository
public class MessageRepository {

    private MessageDAO messageDAO;
    private ConversationDAO conversationDAO;

    public MessageRepository(MessageDAO messageDAO,ConversationDAO conversationDAO) {
        this.messageDAO = messageDAO;
        this.conversationDAO = conversationDAO;
    }

    public void create(Message message) {
        MessageEntity entityToSave = MessageMapper.toEntity(message);
        entityToSave.setConversation(conversationDAO.getOne(message.getConversation_id()));
        entityToSave.setSender(conversationDAO.getOne(message.getConversation_id()).getMembers().stream().filter(x->x.getUsername().equals(message.getSender())).findFirst().get());
        entityToSave.setRecipient(conversationDAO.getOne(message.getConversation_id()).getMembers().stream().filter(x->x.getUsername().equals(message.getRecipient())).findFirst().get());

        try {
            PublicKey publicKey = AsymmetricCryptography.getPublic(conversationDAO.getOne(message.getConversation_id()).getMembers().stream()
                                                  .filter(x->x.getUsername().equals(message.getRecipient()))
                                                  .map(x->x.getPublic_key())
                                                  .findFirst().get());
            byte[] encrypted_msg = AsymmetricCryptography.do_RSAEncryption(message.getContent(), publicKey);
            entityToSave.setContent(Base64.encodeBase64String(encrypted_msg));
        } catch (Exception e) {
            System.out.println("error of encryption");
            e.printStackTrace();
        }
        messageDAO.save(entityToSave);
    }

    public boolean SenderInConversation(Message message) {
        return conversationDAO.getOne(message.getConversation_id()).getMembers().stream().filter(x->x.getUsername().equals(message.getSender())).findAny().isPresent();
    }

    public boolean RecipientInConversation(Message message) {
        return conversationDAO.getOne(message.getConversation_id()).getMembers().stream().filter(x->x.getUsername().equals(message.getRecipient())).findAny().isPresent();
    }
}
