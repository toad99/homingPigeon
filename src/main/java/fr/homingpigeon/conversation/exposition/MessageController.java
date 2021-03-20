package fr.homingpigeon.conversation.exposition;

import fr.homingpigeon.common.UsefullFunctions;
import fr.homingpigeon.conversation.domain.MessageService;
import fr.homingpigeon.conversation.exposition.dto.MessageDTO;
import fr.homingpigeon.conversation.mappers.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.crypto.SecretKey;
import java.net.URISyntaxException;


@RestController
@RequestMapping("/message")
public class MessageController {
    private MessageService messageService;
    private SecretKey jwtSecret;

    @Autowired
    public MessageController(MessageService messageService,SecretKey jwtSecret) {
        this.messageService = messageService;
        this.jwtSecret = jwtSecret;
    }

    @PostMapping("/send")
    public ResponseEntity sendMessage(@RequestBody MessageDTO messageDTO,
                                             @RequestHeader("Authorization") String header) throws URISyntaxException {
        String sender = UsefullFunctions.getUsernameFromHeader(header,jwtSecret);
        messageDTO.setMessage_id(null);
        messageDTO.setSender(sender);
        messageService.sendMessage(MessageMapper.toMessage(messageDTO));
        return ResponseEntity.ok("Message sent");
    }
}
