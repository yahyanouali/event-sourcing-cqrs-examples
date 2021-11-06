package bankservice.port.incoming.adapter.resources.accounts.withdrawals;

import bankservice.domain.model.OptimisticLockingException;
import bankservice.domain.model.account.NonSufficientFundsException;
import bankservice.service.account.AccountNotFoundException;
import bankservice.service.account.AccountService;
import bankservice.service.account.WithdrawAccountCommand;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

@RestController
public class WithdrawalsResource {

  private final AccountService accountService;

  public WithdrawalsResource(AccountService accountService) {
    this.accountService = checkNotNull(accountService);
  }

  @PostMapping("/accounts/{id}/withdrawals")
  public ResponseEntity post(@PathVariable("id") UUID accountId, @RequestBody WithdrawalDto withdrawalDto)
      throws AccountNotFoundException, OptimisticLockingException {

    WithdrawAccountCommand command = new WithdrawAccountCommand(accountId,
        withdrawalDto.getAmount());
    try {
      accountService.process(command);
    } catch (NonSufficientFundsException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    return ResponseEntity.noContent().build();
  }
}
