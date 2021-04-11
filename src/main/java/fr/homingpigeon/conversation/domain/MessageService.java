package fr.homingpigeon.conversation.domain;

import fr.homingpigeon.common.ValidationError;
import fr.homingpigeon.common.exception.ForbiddenException;
import fr.homingpigeon.common.exception.ValidationErrorException;
import fr.homingpigeon.conversation.domain.model.Message;
import fr.homingpigeon.conversation.infrastructure.ConversationRepository;
import fr.homingpigeon.conversation.infrastructure.MessageRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class MessageService {

    private MessageRepository messageRepository;
    private ConversationRepository conversationRepository;

    public MessageService(MessageRepository messageRepository, ConversationRepository conversationRepository) {
        this.messageRepository = messageRepository;
        this.conversationRepository = conversationRepository;
    }

    public void sendMessage(Message message) {
        List<ValidationError> validationErrors = validateSendMessage(message);
        if(validationErrors.size() != 0)
            throw new ValidationErrorException(validationErrors);

        Set<String> set = conversationRepository.getOne(message.getConversation_id()).getMembers();
        set.remove(message.getSender());
        for(String member : set){
            message.setRecipient(member);
            messageRepository.create(message);
        }

    }

    private List<ValidationError> validateSendMessage(Message message) {
        List<ValidationError> validationErrors = new ArrayList<>();

        if(message.getConversation_id() == null){
            validationErrors.add(new ValidationError("Conversation_id not specified"));
            return validationErrors;
        }
        if(!messageRepository.SenderInConversation(message))
            throw new ForbiddenException("Vous n'êtes pas dans la conversation");
        if(message.getContent() == null)
            validationErrors.add(new ValidationError("Content not specified"));

        return validationErrors;
    }
}
