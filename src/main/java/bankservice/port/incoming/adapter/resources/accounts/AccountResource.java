package bankservice.port.incoming.adapter.resources.accounts;

import bankservice.domain.model.account.Account;
import bankservice.service.account.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

@RestController
@RequestMapping("/accounts/{id}")
public class AccountResource {

  private final AccountService accountService;

  public AccountResource(AccountService accountService) {
    this.accountService = checkNotNull(accountService);
  }

  @GetMapping
  public ResponseEntity get(@PathVariable("id") UUID accountId) {
    Optional<Account> possibleAccount = accountService.loadAccount(accountId);
    if (!possibleAccount.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    AccountDto accountDto = toDto(possibleAccount.get());
    return ResponseEntity.ok(accountDto);
  }

  private AccountDto toDto(Account account) {
    AccountDto dto = new AccountDto();
    dto.setId(account.getId());
    dto.setBalance(account.getBalance());
    dto.setClientId(account.getClientId());
    return dto;
  }
}
