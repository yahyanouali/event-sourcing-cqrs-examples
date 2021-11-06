package bankservice.projection.clientaccounts;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

@RestController
public class ClientAccountsResource {

  private final AccountsRepository accountsRepository;

  public ClientAccountsResource(AccountsRepository accountsRepository) {
    this.accountsRepository = checkNotNull(accountsRepository);
  }

  @GetMapping("clients/{id}/accounts")
  public ResponseEntity get(@PathVariable("id") UUID clientId) {
    List<AccountProjection> accounts = accountsRepository.getAccounts(clientId);
    return ResponseEntity.ok(accounts);
  }
}
