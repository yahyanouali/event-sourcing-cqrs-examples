package bankservice.port.incoming.adapter.resources.accounts.deposits;

import bankservice.domain.model.OptimisticLockingException;
import bankservice.service.account.AccountNotFoundException;
import bankservice.service.account.AccountService;
import bankservice.service.account.DepositAccountCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

@RestController()
public class DepositsResource {

  private final AccountService accountService;

  public DepositsResource(AccountService accountService) {
    this.accountService = checkNotNull(accountService);
  }

  @PostMapping("/accounts/{id}/deposits")
  public ResponseEntity post(@PathVariable("id") UUID accountId, @RequestBody DepositDto depositDto)
      throws AccountNotFoundException, OptimisticLockingException {

    DepositAccountCommand command = new DepositAccountCommand(accountId,
        depositDto.getAmount());
    accountService.process(command);
    return ResponseEntity.noContent().build();
  }
}
