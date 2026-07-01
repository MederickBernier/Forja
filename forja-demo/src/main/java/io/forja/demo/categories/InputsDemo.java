package io.forja.demo.categories;

import io.forja.components.inputs.InputVariant;
import io.forja.components.inputs.fxAutocomplete.FxAutocomplete;
import io.forja.components.inputs.fxCodeEditor.FxCodeEditor;
import io.forja.components.inputs.fxJsonEditor.FxJsonEditor;
import io.forja.components.inputs.fxMarkdownEditor.FxMarkdownEditor;
import io.forja.components.inputs.fxMaskedInput.FxMaskedInput;
import io.forja.components.inputs.fxNumberField.FxNumberField;
import io.forja.components.inputs.fxOTPInput.FxOTPInput;
import io.forja.components.inputs.fxPasswordField.FxPasswordField;
import io.forja.components.inputs.fxRichTextEditor.FxRichTextEditor;
import io.forja.components.inputs.fxSearchField.FxSearchField;
import io.forja.components.inputs.fxTagInput.FxTagInput;
import io.forja.components.inputs.fxTextArea.FxTextArea;
import io.forja.components.inputs.fxTextField.FxTextField;
import java.util.Arrays;
import javafx.scene.Node;
import javafx.scene.Scene;

public class InputsDemo implements CategoryDemo {

    @Override public String key() { return "inputs"; }
    @Override public String title() { return "Inputs"; }
    @Override public String icon() { return "fth-edit-3"; }

    @Override
    public Node build(Scene scene) {
        return Demo.category(title(),
                Demo.block("FxTextField", "Single-line text input with icons, helper and error states.",
                        FxTextField.builder().promptText("Your name").build(),
                        FxTextField.builder().text("hello@forja.io").leadingIcon("fth-mail").build(),
                        FxTextField.builder().text("bad value").variant(InputVariant.ERROR).errorText("That doesn't look right").build()),

                Demo.block("FxTextArea", "Multi-line text input with optional auto-resize.",
                        FxTextArea.builder().promptText("Write a comment...").rows(3).build(),
                        FxTextArea.builder().text("Auto-growing text area.").autoResize(true).build()),

                Demo.block("FxPasswordField", "Masked password entry with an optional reveal toggle.",
                        FxPasswordField.builder().promptText("Password").revealable(true).build(),
                        FxPasswordField.builder().text("weak").variant(InputVariant.ERROR).errorText("Too short").build()),

                Demo.block("FxSearchField", "Search box with clear button and search callback.",
                        FxSearchField.builder().promptText("Search...").clearable(true).build()),

                Demo.block("FxNumberField", "Numeric input with steppers, min/max, prefix and suffix.",
                        FxNumberField.builder().value(42).min(0).max(100).step(1).build(),
                        FxNumberField.builder().value(9.99).decimals(2).prefix("$").build(),
                        FxNumberField.builder().value(200).max(100).variant(InputVariant.ERROR).errorText("Above the limit").build()),

                Demo.block("FxMaskedInput", "Fixed-format input driven by a mask pattern.",
                        FxMaskedInput.builder().mask("(###) ###-####").promptText("Phone").build(),
                        FxMaskedInput.builder().mask("##/##/####").text("01/01/2026").build()),

                Demo.block("FxOTPInput", "Segmented one-time-code entry.",
                        FxOTPInput.builder().length(6).digitsOnly(true).build(),
                        FxOTPInput.builder().length(4).initial("1234").build()),

                Demo.block("FxTagInput", "Chip-style multi-value entry.",
                        FxTagInput.builder().tags("java", "javafx").promptText("Add a tag").build(),
                        FxTagInput.builder().variant(InputVariant.ERROR).errorText("Pick at least one tag").build()),

                Demo.block("FxAutocomplete", "Text field with a filtered suggestion dropdown.",
                        FxAutocomplete.<String>builder()
                                .items(Arrays.asList("Apple", "Banana", "Cherry"))
                                .promptText("Pick a fruit").build()),

                Demo.block("FxCodeEditor", "Syntax-friendly code editor with line numbers.",
                        FxCodeEditor.builder().text("int x = 21;\nreturn x * 2;").showLineNumbers(true).build()),

                Demo.block("FxJsonEditor", "Code editor pre-tuned for JSON.",
                        FxJsonEditor.builder().text("{\n  \"forja\": true,\n  \"version\": 1\n}").build()),

                Demo.block("FxMarkdownEditor", "Split-pane markdown source with live preview.",
                        FxMarkdownEditor.builder().text("# Hello\n\nSome **markdown** text.").build()),

                Demo.block("FxRichTextEditor", "WYSIWYG rich-text editor.",
                        FxRichTextEditor.builder().text("Editable rich text.").build()));
    }
}
