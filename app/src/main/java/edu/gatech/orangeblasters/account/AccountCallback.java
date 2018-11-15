package edu.gatech.orangeblasters.account;

import java.util.Optional;

/**
 * Represents a callback for accounts
 *
 * @param <T> the type of account
 */
@FunctionalInterface
public interface AccountCallback<T extends Account> {
    /**
     * Called when an account is returned
     *
     * @param result an optional account result
     */
    void onComplete(Optional<T> result); //Optional makes it explicit when
    // we return if we have a negative result
}
