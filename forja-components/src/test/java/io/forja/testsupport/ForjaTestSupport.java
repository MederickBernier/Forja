package io.forja.testsupport;

import javafx.css.PseudoClass;
import javafx.scene.Node;
import org.testfx.util.WaitForAsyncUtils;

import java.util.concurrent.Callable;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Shared TestFX helpers for Forja component tests.
 *
 * <p>Provides:
 * <ul>
 *   <li>{@link #onFx(Callable)} — runs a body on the JavaFX thread and
 *       returns its value once pending events drain.</li>
 *   <li>{@link #assertHasPseudoClass(Node, String)} — asserts a node has the
 *       given pseudo-class active.</li>
 *   <li>{@link #assertLacksPseudoClass(Node, String)} — inverse assertion.</li>
 * </ul>
 *
 * <p>Use via static import:</p>
 * <pre>{@code
 * import static io.forja.testsupport.ForjaTestSupport.*;
 * }</pre>
 */
public final class ForjaTestSupport {

    private ForjaTestSupport() {}

    /**
     * Runs {@code body} on the JavaFX thread, waits for pending FX events to
     * drain, and returns the body's result. Wraps any thrown exception in a
     * {@link RuntimeException} so tests can use this without checked-exception
     * boilerplate.
     */
    public static <T> T onFx(Callable<T> body) {
        try {
            T result = WaitForAsyncUtils.asyncFx(body).get();
            WaitForAsyncUtils.waitForFxEvents();
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /** Asserts the node currently has the named pseudo-class active. */
    public static void assertHasPseudoClass(Node node, String name) {
        assertTrue(
                node.getPseudoClassStates().contains(PseudoClass.getPseudoClass(name)),
                "Expected pseudo-class :" + name + " on " + node
        );
    }

    /** Asserts the node does NOT currently have the named pseudo-class active. */
    public static void assertLacksPseudoClass(Node node, String name) {
        assertFalse(
                node.getPseudoClassStates().contains(PseudoClass.getPseudoClass(name)),
                "Did not expect pseudo-class :" + name + " on " + node
        );
    }
}
