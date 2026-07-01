package io.forja.components.validation.fxValidator;

/**
 * A validation rule — takes a candidate value, returns either
 * {@code null} (valid) or an error message.
 *
 * <p>Rules are pure functions; they should not mutate state or hold
 * references to UI. Compose complex rules with
 * {@link FxValidator} which runs several in sequence and stops on the first
 * failure.
 *
 * <p>Example:</p>
 * <pre>
 *     {@code
 *      Rule<String> notEmpty = s -> s == null || s.isEmpty() ? "Required" : null;
 *     }
 * </pre>
 *
 * @param <T> the value type being validated
 */
@FunctionalInterface
public interface Rule<T> {

    /**
     * Validates the given value.
     *
     * @param value candidate
     * @return error message, or {@code null} if valid
     */
    String validate(T value);
}
