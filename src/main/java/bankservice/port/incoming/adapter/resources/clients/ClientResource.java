package bankservice.port.incoming.adapter.resources.clients;

import static com.google.common.base.Preconditions.checkNotNull;

import bankservice.domain.model.client.Client;
import bankservice.domain.model.client.Email;
import bankservice.service.client.ClientService;
import bankservice.service.client.UpdateClientCommand;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
public class ClientResource {

  private ClientService clientService;

  public ClientResource(ClientService clientService) {
    this.clientService = checkNotNull(clientService);
  }

  @GetMapping("/clients/{id}")
  public ResponseEntity get(@PathVariable("id") UUID clientId) {
    Optional<Client> possibleClient = clientService.loadClient(clientId);
    if (!possibleClient.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    ClientDto clientDto = toDto(possibleClient.get());
    return ResponseEntity.ok(clientDto);
  }

  @PutMapping
  public ResponseEntity put(@PathVariable("id") UUID clientId, @Valid @NotNull ClientDto clientDto) {
    UpdateClientCommand command = new UpdateClientCommand(
        clientId, clientDto.getName(), new Email(clientDto.getEmail()));
    clientService.process(command);
    return ResponseEntity.noContent().build();
  }

  private ClientDto toDto(Client client) {
    ClientDto dto = new ClientDto();
    dto.setId(client.getId());
    dto.setName(client.getName());
    dto.setEmail(client.getEmail().getValue());
    return dto;
  }
}
