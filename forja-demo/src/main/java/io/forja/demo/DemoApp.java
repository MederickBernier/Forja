package io.forja.demo;

import io.forja.Forja;
import io.forja.components.buttonsAndActions.fxButton.ButtonVariant;
import io.forja.components.dataDisplay.fxAvatar.AvatarSize;
import io.forja.tokens.SemanticVariant;
import io.forja.components.dataDisplay.fxAvatar.FxAvatar;
import io.forja.components.dataDisplay.fxAvatarGroup.FxAvatarGroup;
import io.forja.components.feedbackAndStatus.fxBadge.FxBadge;
import io.forja.components.typography.fxBlockquote.FxBlockquote;
import io.forja.components.inputs.fxPasswordField.FxPasswordField;
import io.forja.components.inputs.fxTextArea.FxTextArea;
import io.forja.components.inputs.InputVariant;
import io.forja.components.inputs.fxTextField.FxTextField;
import io.forja.components.layout.fxCard.CardVariant;
import io.forja.components.layout.fxCard.FxCard;
import io.forja.components.layout.fxContainer.ContainerWidth;
import io.forja.components.feedbackAndStatus.fxChip.FxChip;
import io.forja.components.layout.fxContainer.FxContainer;
import io.forja.components.feedbackAndStatus.fxStatusDot.FxStatusDot;
import io.forja.components.buttonsAndActions.fxButton.FxButton;
import io.forja.components.typography.fxCode.FxCode;
import io.forja.components.utilities.fxIcon.FxIcon;
import io.forja.components.typography.fxKbd.FxKbd;
import io.forja.components.buttonsAndActions.fxIconButton.FxIconButton;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.components.typography.fxLink.FxLink;
import io.forja.components.layout.fxSeparator.FxSeparator;
import io.forja.components.layout.fxSpacer.FxSpacer;
import io.forja.components.layout.fxRow.FxRow;
import io.forja.components.layout.fxSection.FxSection;
import io.forja.components.layout.fxStack.FxStack;
import io.forja.tokens.SpacingSize;
import io.forja.components.typography.fxText.FxText;
import io.forja.components.typography.fxText.TextVariant;
import io.forja.components.buttonsAndActions.fxIconButton.IconPosition;
import io.forja.components.typography.fxLabel.LabelVariant;
import io.forja.components.typography.fxLink.LinkVariant;
import io.forja.components.layout.fxSeparator.SeparatorVariant;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DemoApp extends Application {

    @Override
    public void start(Stage stage) {
        VBox root = new VBox(24);
        root.setPadding(new Insets(32));

        FxLabel heading = FxLabel.builder()
                .text("Forja Demo")
                .variant(LabelVariant.DISPLAY)
                .build();

        FxLabel buttonSectionLabel = FxLabel.builder()
                .text("Buttons")
                .variant(LabelVariant.SMALL)
                .muted(true)
                .build();

        HBox buttons = new HBox(8);
        buttons.getChildren().addAll(
                FxButton.builder().text("Primary").variant(ButtonVariant.PRIMARY).build(),
                FxButton.builder().text("Secondary").variant(ButtonVariant.SECONDARY).build(),
                FxButton.builder().text("Ghost").variant(ButtonVariant.GHOST).build(),
                FxButton.builder().text("Danger").variant(ButtonVariant.DANGER).build()
        );

        HBox disabledButtons = new HBox(8);
        disabledButtons.getChildren().addAll(
                FxButton.builder().text("Loading...").variant(ButtonVariant.PRIMARY).loading(true).build(),
                FxButton.builder().text("Disabled").variant(ButtonVariant.SECONDARY).loading(true).build()
        );

        FxLabel typographySectionLabel = FxLabel.builder()
                .text("Typography")
                .variant(LabelVariant.SMALL)
                .muted(true)
                .build();

        VBox typography = new VBox(8);
        typography.getChildren().addAll(
                FxLabel.builder().text("Display — 24px / bold").variant(LabelVariant.DISPLAY).build(),
                FxLabel.builder().text("Heading — 18px / bold").variant(LabelVariant.HEADING).build(),
                FxLabel.builder().text("Subheading — 14px / bold").variant(LabelVariant.SUBHEADING).build(),
                FxLabel.builder().text("Body — 13px / normal").variant(LabelVariant.BODY).build(),
                FxLabel.builder().text("Small — 11px / normal").variant(LabelVariant.SMALL).build(),
                FxLabel.builder().text("Mono — 12px / JetBrains Mono").variant(LabelVariant.MONO).build(),
                FxLabel.builder().text("Muted body — secondary information").variant(LabelVariant.BODY).muted(true).build()
        );

        FxLabel primitivesSectionLabel = FxLabel.builder()
                .text("Primitives")
                .variant(LabelVariant.SMALL)
                .muted(true)
                .build();

        VBox separators = new VBox(12);
        separators.getChildren().addAll(
                FxLabel.builder().text("Hairline (0.5px)").variant(LabelVariant.SMALL).muted(true).build(),
                FxSeparator.builder().variant(SeparatorVariant.HAIRLINE).build(),
                FxLabel.builder().text("Default (1px)").variant(LabelVariant.SMALL).muted(true).build(),
                FxSeparator.builder().variant(SeparatorVariant.DEFAULT).build(),
                FxLabel.builder().text("Strong (2px)").variant(LabelVariant.SMALL).muted(true).build(),
                FxSeparator.builder().variant(SeparatorVariant.STRONG).build()
        );

        HBox verticalSeparatorRow = new HBox(12);
        verticalSeparatorRow.getChildren().addAll(
                FxLabel.builder().text("Left").variant(LabelVariant.BODY).build(),
                FxSeparator.builder().orientation(Orientation.VERTICAL).variant(SeparatorVariant.DEFAULT).build(),
                FxLabel.builder().text("Middle").variant(LabelVariant.BODY).build(),
                FxSeparator.builder().orientation(Orientation.VERTICAL).variant(SeparatorVariant.STRONG).build(),
                FxLabel.builder().text("Right").variant(LabelVariant.BODY).build()
        );

        VBox primitives = new VBox(16);
        primitives.getChildren().addAll(separators, verticalSeparatorRow);

        FxLabel iconsSectionLabel = FxLabel.builder()
                .text("Icons")
                .variant(LabelVariant.SMALL)
                .muted(true)
                .build();

        HBox iconVariants = new HBox(16);
        iconVariants.setAlignment(Pos.CENTER_LEFT);
        iconVariants.getChildren().addAll(
                iconWithLabel("fth-check-circle", SemanticVariant.DEFAULT, "default"),
                iconWithLabel("fth-circle",       SemanticVariant.MUTED,   "muted"),
                iconWithLabel("fth-zap",          SemanticVariant.ACCENT,  "accent"),
                iconWithLabel("fth-check",        SemanticVariant.SUCCESS, "success"),
                iconWithLabel("fth-alert-triangle", SemanticVariant.WARNING, "warning"),
                iconWithLabel("fth-x-octagon",    SemanticVariant.DANGER,  "danger"),
                iconWithLabel("fth-info",         SemanticVariant.INFO,    "info")
        );

        HBox iconSizes = new HBox(16);
        iconSizes.setAlignment(Pos.CENTER_LEFT);
        iconSizes.getChildren().addAll(
                FxIcon.builder().literal("fth-settings").size(12).build(),
                FxIcon.builder().literal("fth-settings").size(16).build(),
                FxIcon.builder().literal("fth-settings").size(24).build(),
                FxIcon.builder().literal("fth-settings").size(32).build(),
                FxIcon.builder().literal("fth-settings").size(48).build()
        );

        VBox icons = new VBox(16);
        icons.getChildren().addAll(iconVariants, iconSizes);

        FxLabel iconButtonsSectionLabel = FxLabel.builder()
                .text("Icon Buttons")
                .variant(LabelVariant.SMALL)
                .muted(true)
                .build();

        HBox iconButtonsLeft = new HBox(8);
        iconButtonsLeft.getChildren().addAll(
                FxIconButton.builder().text("Save").icon("fth-save").variant(ButtonVariant.PRIMARY).build(),
                FxIconButton.builder().text("Edit").icon("fth-edit").variant(ButtonVariant.SECONDARY).build(),
                FxIconButton.builder().text("Share").icon("fth-share-2").variant(ButtonVariant.GHOST).build(),
                FxIconButton.builder().text("Delete").icon("fth-trash-2").variant(ButtonVariant.DANGER).build()
        );

        HBox iconButtonsRight = new HBox(8);
        iconButtonsRight.getChildren().addAll(
                FxIconButton.builder().text("Next").icon("fth-arrow-right").iconPosition(IconPosition.RIGHT).variant(ButtonVariant.PRIMARY).build(),
                FxIconButton.builder().text("Download").icon("fth-download").iconPosition(IconPosition.RIGHT).variant(ButtonVariant.SECONDARY).build()
        );

        HBox iconButtonsOnly = new HBox(8);
        iconButtonsOnly.getChildren().addAll(
                FxIconButton.builder().icon("fth-settings").iconPosition(IconPosition.ONLY).variant(ButtonVariant.PRIMARY).build(),
                FxIconButton.builder().icon("fth-bell").iconPosition(IconPosition.ONLY).variant(ButtonVariant.SECONDARY).build(),
                FxIconButton.builder().icon("fth-search").iconPosition(IconPosition.ONLY).variant(ButtonVariant.GHOST).build(),
                FxIconButton.builder().icon("fth-x").iconPosition(IconPosition.ONLY).variant(ButtonVariant.DANGER).build()
        );

        VBox iconButtons = new VBox(12);
        iconButtons.getChildren().addAll(iconButtonsLeft, iconButtonsRight, iconButtonsOnly);

        FxLabel linksSectionLabel = FxLabel.builder()
                .text("Links")
                .variant(LabelVariant.SMALL)
                .muted(true)
                .build();

        HBox links = new HBox(24);
        links.getChildren().addAll(
                FxLink.builder().text("Default link").variant(LinkVariant.DEFAULT).build(),
                FxLink.builder().text("Muted link").variant(LinkVariant.MUTED).build(),
                FxLink.builder().text("External docs").variant(LinkVariant.EXTERNAL).build()
        );

        FxLabel paragraphsSectionLabel = FxLabel.builder()
                .text("Paragraphs")
                .variant(LabelVariant.SMALL)
                .muted(true)
                .build();

        VBox paragraphs = new VBox(12);
        paragraphs.getChildren().addAll(
                FxText.builder()
                        .text("Forja sits on top of JavaFX — not around it. It leverages the existing scene graph, CSS engine, skin architecture, and property binding system.")
                        .variant(TextVariant.LEAD)
                        .maxWidth(640)
                        .build(),
                FxText.builder()
                        .text("Every component is a real JavaFX control. FXML, SceneBuilder, accessibility tools, existing code — all compatible, no escape hatches needed. The library adds a design system, a fluent API, and components that look like they were made in this decade.")
                        .variant(TextVariant.BODY)
                        .maxWidth(640)
                        .build(),
                FxText.builder()
                        .text("Early development — not yet published to Maven Central.")
                        .variant(TextVariant.BODY)
                        .muted(true)
                        .maxWidth(640)
                        .build()
        );

        FxLabel blockquotesSectionLabel = FxLabel.builder()
                .text("Blockquotes")
                .variant(LabelVariant.SMALL)
                .muted(true)
                .build();

        VBox blockquotes = new VBox(16);
        blockquotes.getChildren().addAll(
                FxBlockquote.builder()
                        .quote("Shape what already works.")
                        .cite("— Forja motto")
                        .maxWidth(640)
                        .build(),
                FxBlockquote.builder()
                        .quote("JavaFX is capable, battle-tested, and running in production across hospitals, factories, and enterprise systems that will never see Electron.")
                        .maxWidth(640)
                        .build()
        );

        FxLabel kbdSectionLabel = FxLabel.builder()
                .text("Keyboard Shortcuts")
                .variant(LabelVariant.SMALL)
                .muted(true)
                .build();

        HBox shortcutSave = new HBox(4);
        shortcutSave.setAlignment(Pos.CENTER_LEFT);
        shortcutSave.getChildren().addAll(
                FxLabel.builder().text("Save:").variant(LabelVariant.BODY).build(),
                FxKbd.builder().text("⌘").build(),
                FxKbd.builder().text("S").build()
        );

        HBox shortcutPalette = new HBox(4);
        shortcutPalette.setAlignment(Pos.CENTER_LEFT);
        shortcutPalette.getChildren().addAll(
                FxLabel.builder().text("Command palette:").variant(LabelVariant.BODY).build(),
                FxKbd.builder().text("Ctrl").build(),
                FxKbd.builder().text("Shift").build(),
                FxKbd.builder().text("P").build()
        );

        HBox shortcutEscape = new HBox(4);
        shortcutEscape.setAlignment(Pos.CENTER_LEFT);
        shortcutEscape.getChildren().addAll(
                FxLabel.builder().text("Close dialog:").variant(LabelVariant.BODY).build(),
                FxKbd.builder().text("Esc").build()
        );

        VBox kbds = new VBox(8);
        kbds.getChildren().addAll(shortcutSave, shortcutPalette, shortcutEscape);

        FxLabel codeSectionLabel = FxLabel.builder()
                .text("Inline Code")
                .variant(LabelVariant.SMALL)
                .muted(true)
                .build();

        HBox codeRow1 = new HBox(6);
        codeRow1.setAlignment(Pos.CENTER_LEFT);
        codeRow1.getChildren().addAll(
                FxLabel.builder().text("Install Forja with").variant(LabelVariant.BODY).build(),
                FxCode.builder().text("Forja.install(scene)").build(),
                FxLabel.builder().text("before showing the stage.").variant(LabelVariant.BODY).build()
        );

        HBox codeRow2 = new HBox(6);
        codeRow2.setAlignment(Pos.CENTER_LEFT);
        codeRow2.getChildren().addAll(
                FxLabel.builder().text("Set").variant(LabelVariant.BODY).build(),
                FxCode.builder().text("variant").build(),
                FxLabel.builder().text("to").variant(LabelVariant.BODY).build(),
                FxCode.builder().text("ButtonVariant.PRIMARY").build(),
                FxLabel.builder().text("for the main action.").variant(LabelVariant.BODY).build()
        );

        VBox codes = new VBox(6);
        codes.getChildren().addAll(codeRow1, codeRow2);

        FxLabel badgesSectionLabel = FxLabel.builder()
                .text("Badges")
                .variant(LabelVariant.SMALL)
                .muted(true)
                .build();

        HBox badges = new HBox(8);
        badges.setAlignment(Pos.CENTER_LEFT);
        badges.getChildren().addAll(
                FxBadge.builder().text("DEFAULT").variant(SemanticVariant.DEFAULT).build(),
                FxBadge.builder().text("MUTED").variant(SemanticVariant.MUTED).build(),
                FxBadge.builder().text("ACCENT").variant(SemanticVariant.ACCENT).build(),
                FxBadge.builder().text("SUCCESS").variant(SemanticVariant.SUCCESS).build(),
                FxBadge.builder().text("WARNING").variant(SemanticVariant.WARNING).build(),
                FxBadge.builder().text("DANGER").variant(SemanticVariant.DANGER).build(),
                FxBadge.builder().text("INFO").variant(SemanticVariant.INFO).build()
        );

        FxLabel chipsSectionLabel = FxLabel.builder()
                .text("Chips")
                .variant(LabelVariant.SMALL)
                .muted(true)
                .build();

        HBox chipVariants = new HBox(8);
        chipVariants.setAlignment(Pos.CENTER_LEFT);
        chipVariants.getChildren().addAll(
                FxChip.builder().text("default").variant(SemanticVariant.DEFAULT).build(),
                FxChip.builder().text("muted").variant(SemanticVariant.MUTED).build(),
                FxChip.builder().text("accent").variant(SemanticVariant.ACCENT).build(),
                FxChip.builder().text("success").variant(SemanticVariant.SUCCESS).build(),
                FxChip.builder().text("warning").variant(SemanticVariant.WARNING).build(),
                FxChip.builder().text("danger").variant(SemanticVariant.DANGER).build(),
                FxChip.builder().text("info").variant(SemanticVariant.INFO).build()
        );

        HBox chipRemovable = new HBox(8);
        chipRemovable.setAlignment(Pos.CENTER_LEFT);
        chipRemovable.getChildren().addAll(
                FxChip.builder().text("javafx").variant(SemanticVariant.ACCENT).removable(true).build(),
                FxChip.builder().text("ui-toolkit").variant(SemanticVariant.ACCENT).removable(true).build(),
                FxChip.builder().text("design-system").variant(SemanticVariant.SUCCESS).removable(true).build(),
                FxChip.builder().text("draft").variant(SemanticVariant.WARNING).removable(true).build()
        );

        VBox chips = new VBox(8);
        chips.getChildren().addAll(chipVariants, chipRemovable);

        FxLabel statusDotsSectionLabel = FxLabel.builder()
                .text("Status Dots")
                .variant(LabelVariant.SMALL)
                .muted(true)
                .build();

        HBox statusDots = new HBox(16);
        statusDots.setAlignment(Pos.CENTER_LEFT);
        statusDots.getChildren().addAll(
                dotWithLabel(SemanticVariant.DEFAULT, "idle"),
                dotWithLabel(SemanticVariant.MUTED, "draft"),
                dotWithLabel(SemanticVariant.ACCENT, "active"),
                dotWithLabel(SemanticVariant.SUCCESS, "online"),
                dotWithLabel(SemanticVariant.WARNING, "degraded"),
                dotWithLabel(SemanticVariant.DANGER, "offline"),
                dotWithLabel(SemanticVariant.INFO, "syncing")
        );

        FxLabel spacersSectionLabel = FxLabel.builder()
                .text("Spacers")
                .variant(LabelVariant.SMALL)
                .muted(true)
                .build();

        HBox spacerFlex = new HBox(0);
        spacerFlex.setAlignment(Pos.CENTER_LEFT);
        spacerFlex.setPrefWidth(640);
        spacerFlex.getChildren().addAll(
                FxButton.builder().text("Back").variant(ButtonVariant.GHOST).build(),
                FxSpacer.builder().build(),
                FxButton.builder().text("Cancel").variant(ButtonVariant.SECONDARY).build(),
                FxSpacer.builder().size(8, Orientation.HORIZONTAL).build(),
                FxButton.builder().text("Save").variant(ButtonVariant.PRIMARY).build()
        );

        HBox spacerFixed = new HBox(0);
        spacerFixed.setAlignment(Pos.CENTER_LEFT);
        spacerFixed.getChildren().addAll(
                FxBadge.builder().text("A").variant(SemanticVariant.ACCENT).build(),
                FxSpacer.builder().size(48, Orientation.HORIZONTAL).build(),
                FxBadge.builder().text("B").variant(SemanticVariant.SUCCESS).build(),
                FxSpacer.builder().size(24, Orientation.HORIZONTAL).build(),
                FxBadge.builder().text("C").variant(SemanticVariant.WARNING).build()
        );

        VBox spacers = new VBox(8);
        spacers.getChildren().addAll(spacerFlex, spacerFixed);

        FxLabel avatarsSectionLabel = FxLabel.builder()
                .text("Avatars")
                .variant(LabelVariant.SMALL)
                .muted(true)
                .build();

        HBox avatars = new HBox(12);
        avatars.setAlignment(Pos.CENTER_LEFT);
        avatars.getChildren().addAll(
                FxAvatar.builder().initials("MB").size(AvatarSize.COMPACT).build(),
                FxAvatar.builder().initials("MB").size(AvatarSize.DEFAULT).build(),
                FxAvatar.builder().initials("MB").size(AvatarSize.COMFORTABLE).build(),
                FxAvatar.builder().initials("AK").size(AvatarSize.DEFAULT).build(),
                FxAvatar.builder().initials("RS").size(AvatarSize.DEFAULT).build(),
                FxAvatar.builder().initials("JD").size(AvatarSize.DEFAULT).build()
        );

        FxLabel avatarGroupsSectionLabel = FxLabel.builder()
                .text("Avatar Groups")
                .variant(LabelVariant.SMALL)
                .muted(true)
                .build();

        FxAvatarGroup smallGroup = FxAvatarGroup.builder()
                .avatar("MB")
                .avatar("AK")
                .avatar("RS")
                .size(AvatarSize.DEFAULT)
                .build();

        FxAvatarGroup overflowGroup = FxAvatarGroup.builder()
                .avatar("MB")
                .avatar("AK")
                .avatar("RS")
                .avatar("JD")
                .avatar("LP")
                .avatar("ER")
                .avatar("TS")
                .max(3)
                .size(AvatarSize.DEFAULT)
                .build();

        FxAvatarGroup comfortableGroup = FxAvatarGroup.builder()
                .avatar("MB")
                .avatar("AK")
                .avatar("RS")
                .avatar("JD")
                .max(2)
                .size(AvatarSize.COMFORTABLE)
                .build();

        VBox avatarGroups = new VBox(12);
        avatarGroups.getChildren().addAll(smallGroup, overflowGroup, comfortableGroup);

        FxLabel stacksSectionLabel = FxLabel.builder()
                .text("Stacks")
                .variant(LabelVariant.SMALL)
                .muted(true)
                .build();

        FxStack stackSm = FxStack.builder()
                .gap(SpacingSize.SM)
                .children(
                        FxBadge.builder().text("ITEM A").variant(SemanticVariant.ACCENT).build(),
                        FxBadge.builder().text("ITEM B").variant(SemanticVariant.ACCENT).build(),
                        FxBadge.builder().text("ITEM C").variant(SemanticVariant.ACCENT).build()
                ).build();

        FxStack stackLg = FxStack.builder()
                .gap(SpacingSize.LG)
                .children(
                        FxBadge.builder().text("ITEM A").variant(SemanticVariant.SUCCESS).build(),
                        FxBadge.builder().text("ITEM B").variant(SemanticVariant.SUCCESS).build(),
                        FxBadge.builder().text("ITEM C").variant(SemanticVariant.SUCCESS).build()
                ).build();

        FxStack stackXl = FxStack.builder()
                .gap(SpacingSize.XL)
                .children(
                        FxBadge.builder().text("ITEM A").variant(SemanticVariant.WARNING).build(),
                        FxBadge.builder().text("ITEM B").variant(SemanticVariant.WARNING).build(),
                        FxBadge.builder().text("ITEM C").variant(SemanticVariant.WARNING).build()
                ).build();

        HBox stacks = new HBox(32, stackSm, stackLg, stackXl);

        FxLabel rowsSectionLabel = FxLabel.builder()
                .text("Rows")
                .variant(LabelVariant.SMALL)
                .muted(true)
                .build();

        FxRow rowSm = FxRow.builder()
                .gap(SpacingSize.SM)
                .children(
                        FxBadge.builder().text("A").variant(SemanticVariant.ACCENT).build(),
                        FxBadge.builder().text("B").variant(SemanticVariant.ACCENT).build(),
                        FxBadge.builder().text("C").variant(SemanticVariant.ACCENT).build()
                ).build();

        FxRow rowLg = FxRow.builder()
                .gap(SpacingSize.LG)
                .children(
                        FxBadge.builder().text("A").variant(SemanticVariant.SUCCESS).build(),
                        FxBadge.builder().text("B").variant(SemanticVariant.SUCCESS).build(),
                        FxBadge.builder().text("C").variant(SemanticVariant.SUCCESS).build()
                ).build();

        FxRow rowXl = FxRow.builder()
                .gap(SpacingSize.XL)
                .children(
                        FxBadge.builder().text("A").variant(SemanticVariant.WARNING).build(),
                        FxBadge.builder().text("B").variant(SemanticVariant.WARNING).build(),
                        FxBadge.builder().text("C").variant(SemanticVariant.WARNING).build()
                ).build();

        VBox rows = new VBox(8, rowSm, rowLg, rowXl);

        FxLabel containerSectionLabel = FxLabel.builder()
                .text("Container (SM, padding XL)")
                .variant(LabelVariant.SMALL)
                .muted(true)
                .build();

        FxContainer container = FxContainer.builder()
                .width(ContainerWidth.SM)
                .padding(SpacingSize.XL)
                .child(
                        new VBox(12,
                                FxLabel.builder().text("Centered Content").variant(LabelVariant.HEADING).build(),
                                FxText.builder()
                                        .text("FxContainer caps width to its breakpoint and applies token-aligned padding. Place it inside a centering parent (VBox + Pos.CENTER, BorderPane center slot) to anchor the content horizontally.")
                                        .variant(TextVariant.BODY)
                                        .build()
                        )
                )
                .build();

        container.setStyle("-fx-background-color: -forja-bg-card; -fx-border-color: -forja-border-default; -fx-border-width: 0.5px; -fx-background-radius: 8px; -fx-border-radius: 8px;");
        VBox containerWrapper = new VBox(container);
        containerWrapper.setAlignment(Pos.CENTER);

        FxSection sectionsExample = FxSection.builder()
                .title("Sections")
                .separator(true)
                .gap(SpacingSize.MD)
                .content(
                        FxText.builder()
                                .text("FxSection composes a title, optional leading separator, and content children into a single VBox. Use it to give visual structure to grouped content.")
                                .variant(TextVariant.BODY)
                                .maxWidth(640)
                                .build(),
                        FxRow.builder()
                                .gap(SpacingSize.SM)
                                .children(
                                        FxBadge.builder().text("ALPHA").variant(SemanticVariant.ACCENT).build(),
                                        FxBadge.builder().text("BETA").variant(SemanticVariant.SUCCESS).build(),
                                        FxBadge.builder().text("RC").variant(SemanticVariant.WARNING).build()
                                )
                                .build()
                )
                .build();

        FxLabel cardsSectionLabel = FxLabel.builder()
                .text("Cards")
                .variant(LabelVariant.SMALL)
                .muted(true)
                .build();

        FxCard cardDefault = FxCard.builder()
                .variant(CardVariant.DEFAULT)
                .header(FxLabel.builder().text("Default").variant(LabelVariant.SUBHEADING).build())
                .body(FxText.builder()
                        .text("Subtle border, card background. The standard surface.")
                        .variant(TextVariant.BODY)
                        .build())
                .footer(FxRow.builder()
                        .gap(SpacingSize.SM)
                        .children(
                                FxButton.builder().text("Cancel").variant(ButtonVariant.GHOST).build(),
                                FxButton.builder().text("Save").variant(ButtonVariant.PRIMARY).build()
                        )
                        .build())
                .build();

        FxCard cardOutlined = FxCard.builder()
                .variant(CardVariant.OUTLINED)
                .header(FxLabel.builder().text("Outlined").variant(LabelVariant.SUBHEADING).build())
                .body(FxText.builder()
                        .text("Prominent border, transparent background. Use to emphasize boundaries.")
                        .variant(TextVariant.BODY)
                        .build())
                .build();

        FxCard cardElevated = FxCard.builder()
                .variant(CardVariant.ELEVATED)
                .header(FxLabel.builder().text("Elevated").variant(LabelVariant.SUBHEADING).build())
                .body(FxText.builder()
                        .text("Stronger top edge signals elevation without resorting to drop shadows.")
                        .variant(TextVariant.BODY)
                        .build())
                .build();

        HBox cards = new HBox(16, cardDefault, cardOutlined, cardElevated);
        cards.setAlignment(Pos.TOP_LEFT);
        cardDefault.setPrefWidth(220);
        cardOutlined.setPrefWidth(220);
        cardElevated.setPrefWidth(220);

        FxLabel textFieldsSectionLabel = FxLabel.builder()
                .text("Text Fields")
                .variant(LabelVariant.SMALL)
                .muted(true)
                .build();

        FxTextField tfPlain = FxTextField.builder()
                .promptText("Plain field")
                .build();

        FxTextField tfWithIcon = FxTextField.builder()
                .promptText("name@example.com")
                .leadingIcon("fth-mail")
                .helperText("We'll never share your email.")
                .build();

        FxTextField tfSearch = FxTextField.builder()
                .promptText("Search...")
                .leadingIcon("fth-search")
                .trailingIcon("fth-x")
                .build();

        FxTextField tfError = FxTextField.builder()
                .text("nope")
                .leadingIcon("fth-user")
                .errorText("Username already taken.")
                .build();

        FxTextField tfSuccess = FxTextField.builder()
                .text("mederick")
                .leadingIcon("fth-user")
                .trailingIcon("fth-check")
                .variant(InputVariant.SUCCESS)
                .helperText("Looks good.")
                .build();

        tfPlain.setMaxWidth(320);
        tfWithIcon.setMaxWidth(320);
        tfSearch.setMaxWidth(320);
        tfError.setMaxWidth(320);
        tfSuccess.setMaxWidth(320);

        VBox textFields = new VBox(12, tfPlain, tfWithIcon, tfSearch, tfError, tfSuccess);

        FxLabel textAreasSectionLabel = FxLabel.builder()
                .text("Text Areas")
                .variant(LabelVariant.SMALL)
                .muted(true)
                .build();

        FxTextArea taPlain = FxTextArea.builder()
                .promptText("Tell us about yourself...")
                .rows(3)
                .helperText("Max 500 characters.")
                .build();

        FxTextArea taAuto = FxTextArea.builder()
                .text("Line one\nLine two\nLine three\nLine four")
                .rows(2)
                .maxRows(8)
                .autoResize(true)
                .helperText("Auto-resizes between 2 and 8 rows.")
                .build();

        FxTextArea taError = FxTextArea.builder()
                .text("oops")
                .rows(2)
                .errorText("Description is required.")
                .build();

        taPlain.setMaxWidth(320);
        taAuto.setMaxWidth(320);
        taError.setMaxWidth(320);

        VBox textAreas = new VBox(12, taPlain, taAuto, taError);

        FxLabel passwordFieldsSectionLabel = FxLabel.builder()
                .text("Password Fields")
                .variant(LabelVariant.SMALL)
                .muted(true)
                .build();

        FxPasswordField pwPlain = FxPasswordField.builder()
                .promptText("Password")
                .leadingIcon("fth-lock")
                .build();

        FxPasswordField pwReveal = FxPasswordField.builder()
                .text("hunter2")
                .leadingIcon("fth-lock")
                .revealable(true)
                .helperText("Click the eye to reveal.")
                .build();

        FxPasswordField pwError = FxPasswordField.builder()
                .text("123")
                .leadingIcon("fth-lock")
                .revealable(true)
                .errorText("Password must be at least 8 characters.")
                .build();

        pwPlain.setMaxWidth(320);
        pwReveal.setMaxWidth(320);
        pwError.setMaxWidth(320);

        VBox passwordFields = new VBox(12, pwPlain, pwReveal, pwError);

        root.getChildren().addAll(
                heading,
                buttonSectionLabel, buttons, disabledButtons,
                typographySectionLabel, typography,
                primitivesSectionLabel, primitives,
                iconsSectionLabel, icons,
                iconButtonsSectionLabel, iconButtons,
                linksSectionLabel, links,
                paragraphsSectionLabel, paragraphs,
                blockquotesSectionLabel, blockquotes,
                kbdSectionLabel, kbds,
                codeSectionLabel, codes,
                badgesSectionLabel, badges,
                chipsSectionLabel, chips,
                statusDotsSectionLabel, statusDots,
                spacersSectionLabel, spacers,
                avatarsSectionLabel, avatars,
                avatarGroupsSectionLabel, avatarGroups,
                stacksSectionLabel, stacks,
                rowsSectionLabel, rows,
                containerSectionLabel, containerWrapper,
                sectionsExample,
                cardsSectionLabel, cards,
                textFieldsSectionLabel, textFields,
                textAreasSectionLabel, textAreas,
                passwordFieldsSectionLabel, passwordFields
        );

        Scene scene = new Scene(root, 900, 4020);
        Forja.install(scene);

        stage.setTitle("Forja Demo");
        stage.setScene(scene);
        stage.show();
    }

    private static HBox dotWithLabel(SemanticVariant variant, String label) {
        HBox box = new HBox(6);
        box.setAlignment(Pos.CENTER_LEFT);
        box.getChildren().addAll(
                FxStatusDot.builder().variant(variant).build(),
                FxLabel.builder().text(label).variant(LabelVariant.BODY).build()
        );
        return box;
    }

    private static VBox iconWithLabel(String literal, SemanticVariant variant, String label) {
        VBox box = new VBox(4);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(
                FxIcon.builder().literal(literal).size(24).variant(variant).build(),
                FxLabel.builder().text(label).variant(LabelVariant.SMALL).muted(true).build()
        );
        return box;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
