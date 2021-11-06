package bankservice.port.incoming.adapter.resources.accounts;

import bankservice.domain.model.account.Account;
import bankservice.service.account.AccountService;
import bankservice.service.account.OpenAccountCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.google.common.base.Preconditions.checkNotNull;

@RestController
public class AccountsResource {

  private final AccountService accountService;

  public AccountsResource(AccountService accountService) {
    this.accountService = checkNotNull(accountService);
  }

  @PostMapping("/accounts")
  public ResponseEntity post(@RequestBody AccountDto accountDto) {
    OpenAccountCommand command = new OpenAccountCommand(accountDto.getClientId());
    Account account = accountService.process(command);
    URI accountUri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(account.getId())
            .toUri();
    return ResponseEntity.created(accountUri).build();
  }
}
