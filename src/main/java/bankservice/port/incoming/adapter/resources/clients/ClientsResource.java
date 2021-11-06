package bankservice.port.incoming.adapter.resources.clients;

import static com.google.common.base.Preconditions.checkNotNull;

import bankservice.domain.model.client.Client;
import bankservice.domain.model.client.Email;
import bankservice.service.client.ClientService;
import bankservice.service.client.EnrollClientCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import javax.validation.Valid;

@RestController
public class ClientsResource {

  private final ClientService clientService;

  public ClientsResource(ClientService clientService) {
    this.clientService = checkNotNull(clientService);
  }

  @PostMapping("/clients")
  public ResponseEntity post(@RequestBody ClientDto newClientDto) {
    EnrollClientCommand enrollClientCommand = new EnrollClientCommand(
        newClientDto.getName(), new Email(newClientDto.getEmail()));
    Client client = clientService.process(enrollClientCommand);
    URI clientUri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(client.getId())
            .toUri();
    return ResponseEntity.created(clientUri).build();
  }
}
