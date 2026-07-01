package io.forja.components.validation.fxValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * A sequential rule engine for validating a single value against a chain of
 * {@link Rule}s. Runs rules in order, returns on the first non-{@code null}
 * error message. No scene graph dependencies — pure logic, safe to unit test.
 *
 * <p>Convenient built-in rule factories are exposed as static methods:
 * {@link #required()}, {@link #minLength(int, String)},
 * {@link #matches(String, String)}, {@link #email(String)}, etc.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxValidator<String> emailValidator = FxValidator.<String>builder()
 *          .rule(FxValidator.required())
 *          .rule(FxValidator.email("Must be a valid email"))
 *          .build();
 *      String err = emailValidator.validate("bad"); // "Must be a valid email"
 *     }
 * </pre>
 *
 * @param <T> value type
 * @see Rule
 * @see Builder
 */
public class FxValidator<T> {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    private final List<Rule<T>> rules;

    private FxValidator(List<Rule<T>> rules) {
        this.rules = new ArrayList<>(rules);
    }

    /**
     * Validates the given value against every rule in order.
     *
     * @param value candidate
     * @return first error message, or {@code null} if all rules pass
     */
    public String validate(T value) {
        for (Rule<T> r : rules) {
            String err = r.validate(value);
            if (err != null) return err;
        }
        return null;
    }

    /** Returns whether the value is valid (no rule fires). */
    public boolean isValid(T value) { return validate(value) == null; }

    /** Returns an unmodifiable view of the configured rules. */
    public List<Rule<T>> getRules() { return Collections.unmodifiableList(rules); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxValidator}.
     *
     * @param <T> value type
     * @return a new {@link Builder} instance
     */
    public static <T> Builder<T> builder() { return new Builder<T>(); }

    /** Convenience — creates a validator from a variadic rule list. */
    @SafeVarargs
    public static <T> FxValidator<T> of(Rule<T>... rules) {
        FxValidator.Builder<T> b = FxValidator.builder();
        if (rules != null) for (Rule<T> r : rules) b.rule(r);
        return b.build();
    }

    // ─── Built-in rule factories ─────────────────────────────────────

    /** Rule: rejects {@code null}/blank strings ("Required"). */
    public static Rule<String> required() { return required("Required"); }

    /** Rule: rejects {@code null}/blank strings with the given message. */
    public static Rule<String> required(String message) {
        return v -> (v == null || v.trim().isEmpty()) ? message : null;
    }

    /** Rule: minimum string length (only enforced when non-null). */
    public static Rule<String> minLength(int min, String message) {
        return v -> (v == null || v.length() >= min) ? null : message;
    }

    /** Rule: maximum string length. */
    public static Rule<String> maxLength(int max, String message) {
        return v -> (v == null || v.length() <= max) ? null : message;
    }

    /** Rule: string matches a regex. */
    public static Rule<String> matches(String regex, String message) {
        final Pattern p = Pattern.compile(regex);
        return v -> (v != null && p.matcher(v).matches()) ? null : message;
    }

    /** Rule: string looks like an email. */
    public static Rule<String> email(String message) {
        return v -> (v != null && EMAIL_PATTERN.matcher(v).matches()) ? null : message;
    }

    /** Rule: number is within {@code [min, max]}. */
    public static Rule<Double> range(double min, double max, String message) {
        return v -> (v != null && v >= min && v <= max) ? null : message;
    }

    /**
     * Fluent builder for constructing an {@link FxValidator}.
     *
     * @param <T> value type
     */
    public static class Builder<T> {

        private final List<Rule<T>> rules = new ArrayList<>();

        public Builder<T> rule(Rule<T> rule) {
            if (rule != null) rules.add(rule);
            return this;
        }

        @SafeVarargs
        public final Builder<T> rules(Rule<T>... more) {
            if (more != null) rules.addAll(Arrays.asList(more));
            return this;
        }

        public FxValidator<T> build() {
            return new FxValidator<T>(rules);
        }
    }
}
