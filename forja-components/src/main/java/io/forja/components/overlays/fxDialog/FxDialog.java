package io.forja.components.overlays.fxDialog;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.buttonsAndActions.fxButton.ButtonVariant;
import io.forja.components.buttonsAndActions.fxButton.FxButton;
import io.forja.components.overlays.OverlayHost;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.components.typography.fxLabel.LabelVariant;
import io.forja.components.utilities.fxIcon.FxIcon;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * A modal dialog rendered on the {@link OverlayHost} overlay layer.
 *
 * <p>{@code FxDialog} is a {@link StackPane} containing a translucent
 * scrim (which absorbs clicks) and a centered panel {@link VBox} with a
 * title header, a body slot, and a footer row of action buttons. Show and
 * dismiss with {@link #show(Scene)} / {@link #close()}.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxDialog d = FxDialog.builder()
 *          .title("Rename project")
 *          .body(nameField)
 *          .footer(cancelButton, saveButton)
 *          .build();
 *      d.show(scene);
 *     }
 * </pre>
 *
 * @see OverlayHost
 * @see Builder
 */
public class FxDialog extends StackPane {

    private final Region scrim = new Region();
    private final VBox panel = new VBox();
    private final HBox header = new HBox();
    private final FxLabel titleLabel = new FxLabel("", LabelVariant.HEADING);
    private final FxIcon closeIcon = new FxIcon("fth-x");
    private final VBox bodyBox = new VBox();
    private final HBox footer = new HBox();

    private final StringProperty title = new SimpleStringProperty(this, "title", "");
    private final BooleanProperty dismissOnScrimClick = new SimpleBooleanProperty(this, "dismissOnScrimClick", true);
    private final BooleanProperty dismissOnEscape = new SimpleBooleanProperty(this, "dismissOnEscape", true);
    private final BooleanProperty showCloseIcon = new SimpleBooleanProperty(this, "showCloseIcon", true);

    /**
     * Creates an empty {@code FxDialog}.
     */
    public FxDialog() {
        super();
        getStyleClass().add("forja-dialog");
        setPickOnBounds(true);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(24));

        scrim.getStyleClass().add("forja-dialog-scrim");
        StackPane.setAlignment(scrim, Pos.CENTER);
        scrim.setOnMouseClicked(e -> {
            if (isDismissOnScrimClick()) close();
            e.consume();
        });

        panel.getStyleClass().add("forja-dialog-panel");
        panel.setSpacing(12);
        panel.setPadding(new Insets(20));
        panel.setMaxWidth(Region.USE_PREF_SIZE);
        panel.setMaxHeight(Region.USE_PREF_SIZE);
        panel.setOnMouseClicked(e -> e.consume());

        titleLabel.getStyleClass().add("forja-dialog-title");
        titleLabel.setVisible(false);
        titleLabel.setManaged(false);
        Region titleSpacer = new Region();
        HBox.setHgrow(titleSpacer, javafx.scene.layout.Priority.ALWAYS);
        closeIcon.getStyleClass().add("forja-dialog-close");
        closeIcon.setOnMouseClicked(e -> { close(); e.consume(); });
        header.getStyleClass().add("forja-dialog-header");
        header.setAlignment(Pos.CENTER_LEFT);
        header.setSpacing(8);
        header.getChildren().addAll(titleLabel, titleSpacer, closeIcon);

        bodyBox.getStyleClass().add("forja-dialog-body");
        bodyBox.setSpacing(8);

        footer.getStyleClass().add("forja-dialog-footer");
        footer.setAlignment(Pos.CENTER_RIGHT);
        footer.setSpacing(8);
        footer.setVisible(false);
        footer.setManaged(false);

        panel.getChildren().addAll(header, bodyBox, footer);
        getChildren().addAll(scrim, panel);

        title.addListener((obs, o, v) -> {
            String s = v == null ? "" : v;
            titleLabel.setText(s);
            boolean vis = !s.isEmpty();
            titleLabel.setVisible(vis);
            titleLabel.setManaged(vis);
        });
        showCloseIcon.addListener((obs, o, v) -> {
            closeIcon.setVisible(v);
            closeIcon.setManaged(v);
        });
        setOnKeyPressed(e -> {
            if (isDismissOnEscape() && e.getCode() == KeyCode.ESCAPE) {
                close();
                e.consume();
            }
        });
    }

    /** Shows this dialog on the given scene's overlay layer. */
    public void show(Scene scene) {
        OverlayHost.show(scene, this);
        requestFocus();
    }

    /** Removes this dialog from any overlay layer it is attached to. */
    public void close() { OverlayHost.dismiss(this); }

    /** Returns the scrim (backdrop) node. */
    public Region getScrim() { return scrim; }

    /** Returns the centered panel {@link VBox}. */
    public VBox getPanel() { return panel; }

    /** Returns the header row containing title + close icon. */
    public HBox getHeader() { return header; }

    /** Returns the title label. */
    public FxLabel getTitleLabel() { return titleLabel; }

    /** Returns the body {@link VBox} — put custom content children here. */
    public VBox getBodyBox() { return bodyBox; }

    /** Returns the footer row (for action buttons). */
    public HBox getFooter() { return footer; }

    /** Returns the close-icon node. */
    public FxIcon getCloseIcon() { return closeIcon; }

    /** Sets the body content children, replacing existing body children. */
    public void setBody(Node... nodes) {
        bodyBox.getChildren().setAll(nodes);
    }

    /** Sets the footer action children, replacing existing footer children. */
    public void setFooter(Node... nodes) {
        footer.getChildren().setAll(nodes);
        boolean vis = nodes != null && nodes.length > 0;
        footer.setVisible(vis);
        footer.setManaged(vis);
    }

    /** Returns the title property. */
    public StringProperty titleProperty() { return title; }

    /** Returns the current title. */
    public String getTitle() { return title.get(); }

    /** Sets the title text. Empty hides the title label. */
    public void setTitle(String v) { title.set(v == null ? "" : v); }

    /** Returns the dismiss-on-scrim-click property. */
    public BooleanProperty dismissOnScrimClickProperty() { return dismissOnScrimClick; }

    /** Returns whether clicking the scrim closes the dialog. */
    public boolean isDismissOnScrimClick() { return dismissOnScrimClick.get(); }

    /** Sets whether clicking the scrim closes the dialog. */
    public void setDismissOnScrimClick(boolean v) { dismissOnScrimClick.set(v); }

    /** Returns the dismiss-on-escape property. */
    public BooleanProperty dismissOnEscapeProperty() { return dismissOnEscape; }

    /** Returns whether ESC closes the dialog. */
    public boolean isDismissOnEscape() { return dismissOnEscape.get(); }

    /** Sets whether ESC closes the dialog. */
    public void setDismissOnEscape(boolean v) { dismissOnEscape.set(v); }

    /** Returns the show-close-icon property. */
    public BooleanProperty showCloseIconProperty() { return showCloseIcon; }

    /** Returns whether the header close (×) icon is visible. */
    public boolean isShowCloseIcon() { return showCloseIcon.get(); }

    /** Sets whether the header close (×) icon is visible. */
    public void setShowCloseIcon(boolean v) { showCloseIcon.set(v); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxDialog}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxDialog}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>title — empty (title label hidden)</li>
     *   <li>body / footer — empty</li>
     *   <li>dismissOnScrimClick — {@code true}</li>
     *   <li>dismissOnEscape — {@code true}</li>
     *   <li>showCloseIcon — {@code true}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxDialog, Builder> {

        private String title = "";
        private Node[] body = new Node[0];
        private Node[] footer = new Node[0];
        private boolean dismissOnScrimClick = true;
        private boolean dismissOnEscape = true;
        private boolean showCloseIcon = true;

        public Builder title(String title) { this.title = title == null ? "" : title; return this; }
        public Builder body(Node... body) { this.body = body == null ? new Node[0] : body; return this; }
        public Builder footer(Node... footer) { this.footer = footer == null ? new Node[0] : footer; return this; }
        public Builder dismissOnScrimClick(boolean dismissOnScrimClick) { this.dismissOnScrimClick = dismissOnScrimClick; return this; }
        public Builder dismissOnEscape(boolean dismissOnEscape) { this.dismissOnEscape = dismissOnEscape; return this; }
        public Builder showCloseIcon(boolean showCloseIcon) { this.showCloseIcon = showCloseIcon; return this; }

        /**
         * Sugar — builds a footer of one primary {@link FxButton} labeled
         * {@code okText} that closes the dialog on click.
         *
         * @param okText button label
         * @return this builder
         */
        public Builder okOnly(String okText) {
            FxButton ok = FxButton.builder().text(okText).variant(ButtonVariant.PRIMARY).build();
            this.footer = new Node[]{ ok };
            return this;
        }

        @Override
        public FxDialog build() {
            FxDialog d = new FxDialog();
            d.setTitle(title);
            d.setDismissOnScrimClick(dismissOnScrimClick);
            d.setDismissOnEscape(dismissOnEscape);
            d.setShowCloseIcon(showCloseIcon);
            if (body != null && body.length > 0) d.setBody(body);
            if (footer != null && footer.length > 0) {
                d.setFooter(footer);
                for (Node n : footer) {
                    if (n instanceof FxButton) {
                        FxButton fb = (FxButton) n;
                        if (fb.getOnAction() == null) fb.setOnAction(e -> d.close());
                    }
                }
            }
            applyBase(d);
            return d;
        }
    }
}
