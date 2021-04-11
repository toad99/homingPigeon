package fr.homingpigeon.conversation.exposition;

import fr.homingpigeon.conversation.exposition.dto.MessageDTO;
import fr.homingpigeon.security.jwt.JwtConfig;
import fr.homingpigeon.common.UsefullFunctions;
import fr.homingpigeon.conversation.domain.ConversationService;
import fr.homingpigeon.conversation.domain.model.Conversation;
import fr.homingpigeon.conversation.exposition.dto.ConversationDTO;
import fr.homingpigeon.conversation.mappers.ConversationMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/conversation")
public class ConversationController {
    private ConversationService conversationService;
    private SecretKey jwtSecret;

    @Autowired
    public ConversationController(ConversationService conversationService,SecretKey jwtSecret) {
        this.conversationService = conversationService;
        this.jwtSecret = jwtSecret;
    }

    @PostMapping("/create")
    public ResponseEntity createConversation(@RequestBody ConversationDTO conversationDTO,
                                             @RequestHeader("Authorization") String header) throws URISyntaxException {
        conversationDTO.setId_conversation(null);
        conversationDTO.setMessages(Collections.emptyList());
        Conversation conversation = ConversationMapper.toConversation(conversationDTO);
        Conversation createdConversation = this.conversationService.createConversation(conversation,
                UsefullFunctions.getUsernameFromHeader(header,jwtSecret));
        return ResponseEntity.created(new URI("/conversation/" + createdConversation.getId_conversation())).build();
    }

    @GetMapping("/{conversation_id}")
    public ConversationDTO getConversation(@RequestHeader("Authorization") String header,
            @PathVariable("conversation_id") String conversation_id) {
        String username = UsefullFunctions.getUsernameFromHeader(header,jwtSecret);
        return ConversationMapper.toDTO(conversationService.getConversation(username,conversation_id));
    }

    @GetMapping("/{conversation_id}/members")
    public Set<String> getConversationMembers(@RequestHeader("Authorization") String header,
                                              @PathVariable("conversation_id") String conversation_id) {
        String username = UsefullFunctions.getUsernameFromHeader(header,jwtSecret);
        return ConversationMapper.toDTO(conversationService.getConversation(username,conversation_id)).getMembers();
    }

    @GetMapping("/{conversation_id}/messages")
    public List<MessageDTO> getConversationMessages(@RequestHeader("Authorization") String header,
                                                   @PathVariable("conversation_id") String conversation_id) {
        String username = UsefullFunctions.getUsernameFromHeader(header,jwtSecret);
        return ConversationMapper.toDTO(conversationService.getConversation(username,conversation_id))
                                 .getMessages().stream()
                                               .filter(x->x.getRecipient().equals(username) || x.getSender().equals(username))
                                               .collect(Collectors.toList());
    }

}
