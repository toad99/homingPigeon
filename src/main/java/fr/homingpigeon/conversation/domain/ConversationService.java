package fr.homingpigeon.conversation.domain;

import fr.homingpigeon.account.infrastructure.AccountRepository;
import fr.homingpigeon.common.ValidationError;
import fr.homingpigeon.common.exception.ForbiddenException;
import fr.homingpigeon.common.exception.ValidationErrorException;
import fr.homingpigeon.conversation.domain.model.Conversation;
import fr.homingpigeon.conversation.infrastructure.ConversationRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final AccountRepository accountRepository;

    public ConversationService(
            ConversationRepository conversationRepository,
            AccountRepository accountRepository) {
        this.conversationRepository = conversationRepository;
        this.accountRepository = accountRepository;
    }

    public Conversation createConversation(Conversation conversation,String username) {
        conversation.getMembers().add(username);
        List<ValidationError> validationErrors = validateCreate(conversation,username);
        if(validationErrors.size() != 0)
            throw new ValidationErrorException(validationErrors);
        return conversationRepository.create(conversation);
    }

    private List<ValidationError> validateCreate(Conversation conversation,String username) {
        List<ValidationError> validationErrors = new ArrayList<>();
        Set<String> set = new HashSet<String>(conversation.getMembers());

        if(conversation.getMembers() == null) {
            validationErrors.add(new ValidationError("Members not specified"));
            return validationErrors;
        }

        if(set.size() < 2)
            validationErrors.add(new ValidationError("You can't start a conversation alone"));

        if(conversation.getMembers().stream().filter(x -> !accountRepository.exists(x)).findAny().isPresent())
            validationErrors.add(new ValidationError("At least one of the members do not exist" +
                    conversation.getMembers().stream().filter(x -> !accountRepository.exists(x)).collect(Collectors.toList())));

        if(conversation.getMembers().stream()
                                   .filter(x -> !x.equals(username) && !accountRepository.getOne(x).getFriendships().contains(username))
                                   .findAny()
                                   .isPresent())
            validationErrors.add(new ValidationError("At least one of the members is not your friend" +
                    conversation.getMembers()
                                .stream()
                                .filter(x -> !x.equals(username) && !accountRepository.getOne(x).getFriendships().contains(username))
                                .collect(Collectors.toList())));

        if(conversationRepository.exists(conversation.getMembers()))
            validationErrors.add(new ValidationError("A conversation with the same members already exists"));
        return validationErrors;
    }

    public Conversation getConversation(String username, String conversation_id) {
        Conversation conversation = conversationRepository.getOne(conversation_id);
        if(!conversation.getMembers().contains(username))
            throw new ForbiddenException("Vous n'êtes pas dans la conversation");
        return conversation;
    }
}
