package bankservice.domain.model.client;

import bankservice.domain.model.Specification;
import org.apache.commons.validator.routines.EmailValidator;

public class EmailSpecification implements Specification<String> {


    private EmailSpecification() {
        // to prevent outer creation
    }

    private static class Holder {
        private final static EmailSpecification INSTANCE = new EmailSpecification();
    }

    public static EmailSpecification getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public boolean isSatisfiedBy(String value) {
        return EmailValidator.getInstance().isValid(value);
    }
}
