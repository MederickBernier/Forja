package io.forja.components;

import static io.forja.testsupport.ForjaTestSupport.*;
import io.forja.components.dataDisplay.fxAvatar.FxAvatar;
import io.forja.components.dataDisplay.fxAvatar.AvatarSize;

import javafx.css.PseudoClass;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class FxAvatarTest {

    @Start
    void start(Stage stage) {
        // Empty: ApplicationExtension boots the JavaFX toolkit; no UI needed.
    }

    @Test
    void builderDefaults() {
        FxAvatar avatar = onFx(() -> FxAvatar.builder().build());

        assertNull(avatar.getSource());
        assertEquals("", avatar.getInitials());
        assertEquals(AvatarSize.DEFAULT, avatar.getSize());
        assertTrue(avatar.getStyleClass().contains("forja-avatar"));
        assertNotNull(avatar.getClip(), "Avatar should have a circular clip applied");
        assertFalse(avatar.getImageView().isVisible(), "ImageView should be hidden when no source set");
    }

    @Test
    void builderSetsAllProperties() {
        FxAvatar avatar = onFx(() -> FxAvatar.builder()
                .initials("MB")
                .size(AvatarSize.COMFORTABLE)
                .id("user-avatar")
                .styleClass("custom")
                .userData("payload")
                .build());

        assertEquals("MB", avatar.getInitials());
        assertEquals("MB", avatar.getInitialsLabel().getText());
        assertEquals(AvatarSize.COMFORTABLE, avatar.getSize());
        assertEquals(44, avatar.getPrefWidth());
        assertEquals(44, avatar.getPrefHeight());
        assertEquals("user-avatar", avatar.getId());
        assertTrue(avatar.getStyleClass().contains("custom"));
        assertEquals("payload", avatar.getUserData());
    }

    @Test
    void constructorVariants() {
        FxAvatar empty = onFx(() -> new FxAvatar());
        FxAvatar withInitials = onFx(() -> new FxAvatar("AK"));
        FxAvatar withSize = onFx(() -> new FxAvatar("RS", AvatarSize.COMPACT));

        assertEquals("", empty.getInitials());
        assertEquals(AvatarSize.DEFAULT, empty.getSize());
        assertEquals("AK", withInitials.getInitials());
        assertEquals(AvatarSize.DEFAULT, withInitials.getSize());
        assertEquals("RS", withSize.getInitials());
        assertEquals(AvatarSize.COMPACT, withSize.getSize());
        assertEquals(28, withSize.getPrefWidth());
    }

    @Test
    void sizePseudoClassUpdates() {
        FxAvatar avatar = onFx(() -> FxAvatar.builder().size(AvatarSize.COMPACT).build());

        assertHasPseudoClass(avatar, "compact");
        assertLacksPseudoClass(avatar, "default");
        assertLacksPseudoClass(avatar, "comfortable");

        onFx(() -> { avatar.setSize(AvatarSize.COMFORTABLE); return null; });

        assertHasPseudoClass(avatar, "comfortable");
        assertLacksPseudoClass(avatar, "compact");
        assertEquals(44, avatar.getPrefWidth());
    }

    @Test
    void allSizesMapToCorrectPseudoClassAndDiameter() {
        for (AvatarSize size : AvatarSize.values()) {
            FxAvatar avatar = onFx(() -> FxAvatar.builder().size(size).build());
            assertHasPseudoClass(avatar, size.name().toLowerCase());
            assertEquals(size.diameter(), avatar.getPrefWidth());
            assertEquals(size.diameter(), avatar.getPrefHeight());
        }
    }

    @Test
    void initialsPropertyPropagatesToLabel() {
        FxAvatar avatar = onFx(() -> FxAvatar.builder().initials("A").build());

        assertEquals("A", avatar.getInitialsLabel().getText());

        onFx(() -> { avatar.setInitials("BC"); return null; });

        assertEquals("BC", avatar.getInitialsLabel().getText());

        onFx(() -> { avatar.setInitials(null); return null; });

        assertEquals("", avatar.getInitialsLabel().getText());
    }

    @Test
    void nullOrEmptySourceHidesImageView() {
        FxAvatar avatar = onFx(() -> FxAvatar.builder().initials("MB").build());

        assertFalse(avatar.getImageView().isVisible());

        onFx(() -> { avatar.setSource(""); return null; });
        assertFalse(avatar.getImageView().isVisible());

        onFx(() -> { avatar.setSource(null); return null; });
        assertFalse(avatar.getImageView().isVisible());
    }
}
