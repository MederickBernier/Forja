package io.forja.demo.categories;

import io.forja.components.inputs.fxTextField.FxTextField;
import io.forja.components.validation.fxErrorSummary.FxErrorSummary;
import io.forja.components.validation.fxForm.FxForm;
import io.forja.components.validation.fxFormField.FxFormField;
import io.forja.components.validation.fxValidator.FxValidator;
import javafx.scene.Node;
import javafx.scene.Scene;

public class ValidationDemo implements CategoryDemo {

    @Override public String key() { return "validation"; }
    @Override public String title() { return "Validation"; }
    @Override public String icon() { return "fth-check-circle"; }

    @Override
    public Node build(Scene scene) {
        // FxValidator<String> is non-visual validation logic; shown by wiring it into an FxFormField.
        FxValidator<String> nameValidator = FxValidator.<String>builder()
                .rule(FxValidator.required("Name is required"))
                .rule(FxValidator.minLength(3, "At least 3 characters"))
                .build();

        FxTextField nameInput = FxTextField.builder().promptText("Your name").build();
        FxFormField<String> nameField = FxFormField.<String>builder()
                .label("Name")
                .control(nameInput)
                .required(true)
                .validator(nameValidator)
                .valueSupplier(nameInput::getText)
                .build();

        FxValidator<String> emailValidator = FxValidator.<String>builder()
                .rule(FxValidator.required("Email is required"))
                .rule(FxValidator.email("Not a valid email"))
                .build();
        FxTextField emailInput = FxTextField.builder().promptText("you@example.com").build();
        FxFormField<String> emailField = FxFormField.<String>builder()
                .label("Email")
                .control(emailInput)
                .required(true)
                .validator(emailValidator)
                .valueSupplier(emailInput::getText)
                .build();

        return Demo.category(title(),
                // ponytail: minimal — FxValidator is validation logic, shown via FxFormField
                Demo.block("FxValidator", "Non-visual rule chain (required, minLength, email, ...) wired into a field. Type and blur to see live validation.",
                        nameField),

                Demo.block("FxFormField", "Labelled input with an attached validator and error slot.",
                        emailField),

                Demo.block("FxForm", "Groups fields and validates them together, with an optional error summary.",
                        FxForm.builder()
                                .field(FxFormField.<String>builder()
                                        .label("Username")
                                        .control(FxTextField.builder().promptText("username").build())
                                        .required(true)
                                        .validator(FxValidator.<String>builder()
                                                .rule(FxValidator.required("Username is required"))
                                                .build())
                                        .build())
                                .showSummary(true)
                                .summaryTitle("Please fix the following")
                                .build()),

                Demo.block("FxErrorSummary", "Standalone list of validation errors.",
                        FxErrorSummary.builder()
                                .title("2 errors found")
                                .errors("Name is required", "Email is not valid")
                                .build()));
    }
}
