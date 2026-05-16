package io.forja.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * Utility class responsible for registering dynamic CSS strings as
 * temporary stylesheet files for use with JavaFX scene graph nodes.
 *
 * <p>JavaFX requires stylesheets to be registered as URIs pointing to
 * accessible resources. This class handles the conversion of raw CSS
 * strings into temporary files and returns their URI for registration
 * via {@link javafx.scene.Node#getStyleSheets()}.</p>
 *
 * <p>Temporary files are automatically deleted when the JVM shuts down</p>
 *
 * This class is not intended to be instantiated
 */
public final class ForjaStylesheets{
    private ForjaStylesheets(){}

    /**
     * Writes the given CSS string to a temporary file and returns its
     * URI as string suitable for use with
     * {@link javafx.scene.Node#getStyleSheets()}.
     *
     * <p>The temporary file is created with the prefix {@code forja-style-}
     * and the suffix {@code .css}.  It is automatically deleted on JVM exit.</p>
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * String uri = ForjaStylesheets.register("#my-id {-fx-background-color: red;}
     * control.getStylesheets().add(uri);
     * }</pre>
     *
     * @param css the CSS content to register, must not be {@code null}
     * @return a URI string pointing to the registered temporary stylesheet
     * @throws RuntimeException if the temporary file cannot be created or written
     */

    public static String register(String css){
        try{
            File temp = File.createTempFile("forja-style-",".css");
            temp.deleteOnExit();

            PrintWriter writer = new PrintWriter(temp, StandardCharsets.UTF_8.name());
            try{
                writer.print(css);
            }finally{
                writer.close();
            }

            return temp.toURI().toString();
        }catch(IOException e){
            throw new RuntimeException(
                    "Forja: failed to register dynamic stylesheet", e
            );
        }
    }
}
