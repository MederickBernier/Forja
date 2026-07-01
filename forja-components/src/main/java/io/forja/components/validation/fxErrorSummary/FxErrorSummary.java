package io.forja.components.validation.fxErrorSummary;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.components.typography.fxLabel.LabelVariant;
import io.forja.components.utilities.fxIcon.FxIcon;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * An aggregate list of validation errors, framed as a danger-colored panel.
 * Auto-hides when the errors list is empty.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxErrorSummary summary = FxErrorSummary.builder()
 *          .title("Please fix the following:")
 *          .errors("Email is required", "Password too short")
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxErrorSummary extends VBox {

    private final FxIcon icon = new FxIcon("fth-alert-octagon");
    private final FxLabel titleLabel = new FxLabel("", LabelVariant.BODY);
    private final HBox header = new HBox();
    private final VBox listBox = new VBox();

    private final ObservableList<String> errors = FXCollections.observableArrayList();
    private final StringProperty title = new SimpleStringProperty(this, "title", "There were errors");

    /**
     * Creates an empty (hidden) {@code FxErrorSummary}.
     */
    public FxErrorSummary() {
        super();
        getStyleClass().add("forja-error-summary");
        pseudoClassStateChanged(PseudoClass.getPseudoClass("danger"), true);
        setSpacing(6);
        header.setSpacing(8);
        header.setAlignment(Pos.CENTER_LEFT);
        icon.getStyleClass().add("forja-error-summary-icon");
        titleLabel.getStyleClass().add("forja-error-summary-title");
        HBox.setHgrow(titleLabel, Priority.ALWAYS);
        header.getChildren().addAll(icon, titleLabel);
        listBox.setSpacing(2);
        listBox.getStyleClass().add("forja-error-summary-list");
        getChildren().addAll(header, listBox);

        title.addListener((obs, o, v) -> titleLabel.setText(v == null ? "" : v));
        errors.addListener((javafx.collections.ListChangeListener<String>) c -> refresh());
        refresh();
    }

    /** Re-renders the errors list and toggles visibility. Called on every
     *  {@link #getErrors()} change; can be invoked explicitly to force a
     *  visibility refresh after external mutations (e.g. via applyBase). */
    public void refresh() {
        listBox.getChildren().clear();
        for (String e : errors) {
            FxLabel row = FxLabel.builder().text("• " + (e == null ? "" : e)).variant(LabelVariant.SMALL).build();
            row.getStyleClass().add("forja-error-summary-item");
            listBox.getChildren().add(row);
        }
        boolean vis = !errors.isEmpty();
        setVisible(vis);
        setManaged(vis);
    }

    /** Returns the errors list. */
    public ObservableList<String> getErrors() { return errors; }

    /** Returns the title property. */
    public StringProperty titleProperty() { return title; }
    /** Returns the current title. */
    public String getTitle() { return title.get(); }
    /** Sets the title. */
    public void setTitle(String v) { title.set(v == null ? "" : v); }

    /** Returns the header row. */
    public HBox getHeader() { return header; }
    /** Returns the list container. */
    public VBox getListBox() { return listBox; }
    /** Returns the icon. */
    public FxIcon getIcon() { return icon; }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxErrorSummary}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxErrorSummary}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>title — {@code "There were errors"}</li>
     *   <li>errors — empty (hidden)</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxErrorSummary, Builder> {

        private String title = "There were errors";
        private List<String> errors = Collections.emptyList();

        public Builder title(String title) { this.title = title == null ? "" : title; return this; }
        public Builder errors(List<String> errors) { this.errors = errors == null ? Collections.<String>emptyList() : errors; return this; }
        public Builder errors(String... errors) { return errors(errors == null ? Collections.<String>emptyList() : Arrays.asList(errors)); }

        @Override
        public FxErrorSummary build() {
            FxErrorSummary s = new FxErrorSummary();
            s.setTitle(title);
            applyBase(s);
            s.getErrors().setAll(errors);
            // Force refresh — applyBase may set visible=true unconditionally.
            s.refresh();
            return s;
        }
    }
}
