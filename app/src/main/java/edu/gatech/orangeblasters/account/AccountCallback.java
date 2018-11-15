package edu.gatech.orangeblasters.account;

import java.util.Optional;

@FunctionalInterface
public interface AccountCallback<T extends Account> {
    void onComplete(Optional<T> result); //Optional makes it explicit when
    // we return if we have a negative result
}
