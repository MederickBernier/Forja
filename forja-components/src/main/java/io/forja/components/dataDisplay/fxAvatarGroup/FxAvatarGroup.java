package io.forja.components.dataDisplay.fxAvatarGroup;

import io.forja.components.dataDisplay.fxAvatar.AvatarSize;
import io.forja.components.dataDisplay.fxAvatar.FxAvatar;

import io.forja.builder.FxNodeBuilder;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

/**
 * A horizontal stack of overlapping {@link FxAvatar}s with an optional
 * overflow count.
 *
 * <p>{@code FxAvatarGroup} extends {@link HBox} and renders the first
 * {@link #getMax} avatars from {@link #getAvatars}, applying a negative
 * left margin so they overlap. When there are more avatars than {@code max},
 * a final "{@code +N}" overflow badge is appended.
 *
 * <p>The preferred way to construct an {@code FxAvatarGroup} is via the builder:</p>
 * <pre>
 *     {@code
 *      FxAvatarGroup team = FxAvatarGroup.builder()
 *          .avatar("MB")
 *          .avatar("AK")
 *          .avatar("RS")
 *          .avatar("JD")
 *          .avatar("LP")
 *          .max(3)
 *          .size(AvatarSize.DEFAULT)
 *          .build();
 *     }
 * </pre>
 *
 * @see FxAvatar
 * @see AvatarSize
 * @see Builder
 */
public class FxAvatarGroup extends HBox {

    private static final int DEFAULT_MAX = 3;
    private static final double OVERLAP_FRACTION = 0.32;

    private final ObservableList<FxAvatar> avatars = FXCollections.observableArrayList();
    private final IntegerProperty max = new SimpleIntegerProperty(this, "max", DEFAULT_MAX);
    private final ObjectProperty<AvatarSize> size = new SimpleObjectProperty<>(this, "size", AvatarSize.DEFAULT);

    private FxAvatar overflowBadge;

    /**
     * Creates an empty avatar group with the default max (3) and default size.
     */
    public FxAvatarGroup() {
        getStyleClass().add("forja-avatar-group");
        setAlignment(Pos.CENTER_LEFT);
        setSpacing(0);

        avatars.addListener((ListChangeListener<FxAvatar>) c -> rebuild());
        max.addListener((obs, old, val) -> rebuild());
        size.addListener((obs, old, val) -> rebuild());

        rebuild();
    }

    /**
     * Creates an avatar group with the given size.
     *
     * @param size avatar size applied to every member
     */
    public FxAvatarGroup(AvatarSize size) {
        this();
        setSize(size);
    }

    private void rebuild() {
        getChildren().clear();
        int maxVal = Math.max(getMax(), 0);
        int total = avatars.size();
        int visible = Math.min(maxVal, total);
        double overlap = -getSize().diameter() * OVERLAP_FRACTION;

        for (int i = 0; i < visible; i++) {
            FxAvatar avatar = avatars.get(i);
            avatar.setSize(getSize());
            if (!avatar.getStyleClass().contains("forja-avatar-group-member")) {
                avatar.getStyleClass().add("forja-avatar-group-member");
            }
            if (i > 0) {
                HBox.setMargin(avatar, new Insets(0, 0, 0, overlap));
            } else {
                HBox.setMargin(avatar, Insets.EMPTY);
            }
            getChildren().add(avatar);
        }

        int overflow = total - visible;
        if (overflow > 0) {
            FxAvatar badge = buildOverflowBadge(overflow);
            HBox.setMargin(badge, new Insets(0, 0, 0, overlap));
            getChildren().add(badge);
            overflowBadge = badge;
        } else {
            overflowBadge = null;
        }
    }

    private FxAvatar buildOverflowBadge(int overflow) {
        FxAvatar badge = new FxAvatar("+" + overflow, getSize());
        badge.getStyleClass().addAll("forja-avatar-group-member", "forja-avatar-overflow");
        return badge;
    }

    /**
     * Returns the underlying mutable avatar list. Mutating this list triggers
     * a rebuild.
     */
    public ObservableList<FxAvatar> getAvatars() { return avatars; }

    /** Returns the max property. */
    public IntegerProperty maxProperty() { return max; }

    /** Returns the current visible-avatar cap. */
    public int getMax() { return max.get(); }

    /** Sets the visible-avatar cap. */
    public void setMax(int v) { max.set(v); }

    /** Returns the size property. */
    public ObjectProperty<AvatarSize> sizeProperty() { return size; }

    /** Returns the current size variant. */
    public AvatarSize getSize() { return size.get(); }

    /** Sets the size variant — propagates to every member avatar. */
    public void setSize(AvatarSize v) { size.set(v); }

    /**
     * Returns the current overflow badge node, or {@code null} when no
     * overflow is shown. Useful for tests and advanced styling.
     */
    public FxAvatar getOverflowBadge() { return overflowBadge; }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxAvatarGroup}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxAvatarGroup}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>max — 3</li>
     *   <li>size — {@link AvatarSize#DEFAULT}</li>
     *   <li>avatars — empty</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxAvatarGroup, Builder> {

        private final java.util.List<FxAvatar> avatars = new java.util.ArrayList<>();
        private int max = DEFAULT_MAX;
        private AvatarSize size = AvatarSize.DEFAULT;

        /** Adds an FxAvatar instance to the group. */
        public Builder avatar(FxAvatar avatar) {
            avatars.add(avatar);
            return this;
        }

        /** Adds an initials-only avatar to the group. */
        public Builder avatar(String initials) {
            avatars.add(new FxAvatar(initials));
            return this;
        }

        /** Adds an image-or-initials avatar to the group. */
        public Builder avatar(String source, String initials) {
            FxAvatar a = new FxAvatar(initials);
            a.setSource(source);
            avatars.add(a);
            return this;
        }

        public Builder max(int max) {
            this.max = max;
            return this;
        }

        public Builder size(AvatarSize size) {
            this.size = size;
            return this;
        }

        public FxAvatarGroup build() {
            FxAvatarGroup group = new FxAvatarGroup(size);
            group.setMax(max);
            group.getAvatars().addAll(avatars);
            applyBase(group);
            return group;
        }
    }
}
