package bankservice.projection.accounttransactions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

@RestController("/accounts/{id}/transactions")
public class AccountTransactionsResource {

  private TransactionsRepository transactionsRepository;

  public AccountTransactionsResource(TransactionsRepository transactionsRepository) {
    this.transactionsRepository = checkNotNull(transactionsRepository);
  }

  @GetMapping
  public ResponseEntity get(@PathVariable("id") UUID accountId) {
    List<TransactionProjection> transactionProjections = transactionsRepository
        .listByAccount(accountId);
    return ResponseEntity.ok(transactionProjections);
  }
}
