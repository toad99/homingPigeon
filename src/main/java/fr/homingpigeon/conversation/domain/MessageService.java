package fr.homingpigeon.conversation.domain;

import fr.homingpigeon.account.domain.model.Account;
import fr.homingpigeon.common.ValidationError;
import fr.homingpigeon.common.exception.ValidationErrorException;
import fr.homingpigeon.conversation.domain.model.Message;
import fr.homingpigeon.conversation.infrastructure.ConversationRepository;
import fr.homingpigeon.conversation.infrastructure.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    private MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void sendMessage(Message message) {
        List<ValidationError> validationErrors = validateSendMessage(message);
        if(validationErrors.size() != 0)
            throw new ValidationErrorException(validationErrors);
        messageRepository.create(message);
    }

    private List<ValidationError> validateSendMessage(Message message) {
        List<ValidationError> validationErrors = new ArrayList<>();

        if(message.getRecipient() == null){
            validationErrors.add(new ValidationError("Recipient not specified"));
            return validationErrors;
        }

        if(message.getConversation_id() == null){
            validationErrors.add(new ValidationError("Conversation_id not specified"));
            return validationErrors;
        }

        if(!messageRepository.SenderInConversation(message))
            validationErrors.add(new ValidationError("You are not in the conversation"));
        if(!messageRepository.RecipientInConversation(message))
            validationErrors.add(new ValidationError("The recipient is not in the conversation"));
        if(message.getContent() == null)
            validationErrors.add(new ValidationError("Content not specified"));

        return validationErrors;
    }
}
