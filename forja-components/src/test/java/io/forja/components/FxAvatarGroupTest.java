package io.forja.components;

import static io.forja.testsupport.ForjaTestSupport.*;
import io.forja.components.dataDisplay.fxAvatar.FxAvatar;
import io.forja.components.dataDisplay.fxAvatarGroup.FxAvatarGroup;
import io.forja.components.dataDisplay.fxAvatar.AvatarSize;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class FxAvatarGroupTest {

    @Start
    void start(Stage stage) {
        // Empty: ApplicationExtension boots the JavaFX toolkit; no UI needed.
    }

    @Test
    void builderDefaults() {
        FxAvatarGroup group = onFx(() -> FxAvatarGroup.builder().build());

        assertEquals(3, group.getMax());
        assertEquals(AvatarSize.DEFAULT, group.getSize());
        assertEquals(0, group.getAvatars().size());
        assertEquals(0, group.getChildren().size());
        assertNull(group.getOverflowBadge());
        assertTrue(group.getStyleClass().contains("forja-avatar-group"));
    }

    @Test
    void builderAddsAvatarsFromInitials() {
        FxAvatarGroup group = onFx(() -> FxAvatarGroup.builder()
                .avatar("AA")
                .avatar("BB")
                .avatar("CC")
                .build());

        assertEquals(3, group.getAvatars().size());
        assertEquals(3, group.getChildren().size());
        assertNull(group.getOverflowBadge());
    }

    @Test
    void overflowShowsBadgeWithCount() {
        FxAvatarGroup group = onFx(() -> FxAvatarGroup.builder()
                .avatar("AA").avatar("BB").avatar("CC")
                .avatar("DD").avatar("EE")
                .max(3)
                .build());

        assertEquals(5, group.getAvatars().size());
        assertEquals(4, group.getChildren().size(), "3 visible avatars + 1 overflow badge");

        FxAvatar badge = group.getOverflowBadge();
        assertNotNull(badge);
        assertEquals("+2", badge.getInitials());
        assertTrue(badge.getStyleClass().contains("forja-avatar-overflow"));
    }

    @Test
    void noOverflowWhenSizeEqualsMax() {
        FxAvatarGroup group = onFx(() -> FxAvatarGroup.builder()
                .avatar("AA").avatar("BB").avatar("CC")
                .max(3)
                .build());

        assertEquals(3, group.getChildren().size());
        assertNull(group.getOverflowBadge());
    }

    @Test
    void mutatingListTriggersRebuild() {
        FxAvatarGroup group = onFx(() -> FxAvatarGroup.builder().max(3).build());

        onFx(() -> {
            group.getAvatars().add(new FxAvatar("AA"));
            group.getAvatars().add(new FxAvatar("BB"));
            return null;
        });
        assertEquals(2, group.getChildren().size());
        assertNull(group.getOverflowBadge());

        onFx(() -> {
            group.getAvatars().add(new FxAvatar("CC"));
            group.getAvatars().add(new FxAvatar("DD"));
            return null;
        });
        assertEquals(4, group.getChildren().size(), "3 visible + 1 overflow badge");
        assertEquals("+1", group.getOverflowBadge().getInitials());

        onFx(() -> {
            group.getAvatars().clear();
            return null;
        });
        assertEquals(0, group.getChildren().size());
        assertNull(group.getOverflowBadge());
    }

    @Test
    void changingMaxRebuilds() {
        FxAvatarGroup group = onFx(() -> FxAvatarGroup.builder()
                .avatar("AA").avatar("BB").avatar("CC").avatar("DD").avatar("EE")
                .max(3)
                .build());

        assertEquals(4, group.getChildren().size());
        assertEquals("+2", group.getOverflowBadge().getInitials());

        onFx(() -> { group.setMax(5); return null; });
        assertEquals(5, group.getChildren().size());
        assertNull(group.getOverflowBadge());

        onFx(() -> { group.setMax(2); return null; });
        assertEquals(3, group.getChildren().size());
        assertEquals("+3", group.getOverflowBadge().getInitials());
    }

    @Test
    void sizePropagatesToMembers() {
        FxAvatarGroup group = onFx(() -> FxAvatarGroup.builder()
                .avatar("AA").avatar("BB")
                .size(AvatarSize.COMPACT)
                .build());

        for (javafx.scene.Node child : group.getChildren()) {
            assertEquals(AvatarSize.COMPACT, ((FxAvatar) child).getSize());
        }

        onFx(() -> { group.setSize(AvatarSize.COMFORTABLE); return null; });

        for (javafx.scene.Node child : group.getChildren()) {
            assertEquals(AvatarSize.COMFORTABLE, ((FxAvatar) child).getSize());
        }
    }
}
