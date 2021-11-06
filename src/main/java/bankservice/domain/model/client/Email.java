package bankservice.domain.model.client;

import static com.google.common.base.Preconditions.checkArgument;

import bankservice.domain.model.ValueObject;

public class Email extends ValueObject {

  private final String value;

  public Email(String value) {
    checkArgument(EmailSpecification.getInstance().isSatisfiedBy(value));
    this.value = value;
  }

  public String getValue() {
    return value;
  }

}
