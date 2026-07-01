package io.forja.demo.categories;

import io.forja.components.selection.fxCheckBox.FxCheckBox;
import io.forja.components.selection.fxCheckGroup.FxCheckGroup;
import io.forja.components.selection.fxColorPicker.FxColorPicker;
import io.forja.components.selection.fxComboBox.FxComboBox;
import io.forja.components.selection.fxMultiSelect.FxMultiSelect;
import io.forja.components.selection.fxRadioGroup.FxRadioGroup;
import io.forja.components.selection.fxRangeSlider.FxRangeSlider;
import io.forja.components.selection.fxRating.FxRating;
import io.forja.components.selection.fxSlider.FxSlider;
import io.forja.components.selection.fxSwitch.FxSwitch;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

import java.util.Arrays;

public class SelectionDemo implements CategoryDemo {

    @Override public String key() { return "selection"; }
    @Override public String title() { return "Selection"; }
    @Override public String icon() { return "fth-check-square"; }

    @Override
    public Node build(Scene scene) {
        return Demo.category(title(),
                Demo.block("FxCheckBox", "Single checkbox with optional indeterminate state.",
                        FxCheckBox.builder().text("Unchecked").build(),
                        FxCheckBox.builder().text("Checked").selected(true).build(),
                        FxCheckBox.builder().text("Indeterminate").allowIndeterminate(true).indeterminate(true).build()),

                Demo.block("FxCheckGroup", "Group of checkboxes with multi-select.",
                        FxCheckGroup.builder().label("Toppings")
                                .items("Cheese", "Mushroom", "Onion").selected("Cheese").build()),

                Demo.block("FxColorPicker", "Color swatch picker.",
                        FxColorPicker.builder().value(Color.CORNFLOWERBLUE).build(),
                        FxColorPicker.builder().value(Color.TOMATO).build()),

                Demo.block("FxComboBox", "Single-select dropdown.",
                        FxComboBox.<String>builder().items(Arrays.asList("One", "Two", "Three"))
                                .value("Two").promptText("Pick one").build()),

                Demo.block("FxMultiSelect", "Dropdown that selects multiple values.",
                        FxMultiSelect.builder().items("Alpha", "Beta", "Gamma")
                                .selected("Alpha").promptText("Select tags").build()),

                Demo.block("FxRadioGroup", "Mutually exclusive option group.",
                        FxRadioGroup.builder().label("Size")
                                .items("Small", "Medium", "Large").value("Medium")
                                .orientation(Orientation.HORIZONTAL).build()),

                Demo.block("FxRangeSlider", "Two-thumb slider for a value range.",
                        FxRangeSlider.builder().min(0).max(100).lowValue(20).highValue(80)
                                .step(5).showValues(true).suffix("%").build()),

                Demo.block("FxRating", "Star rating input.",
                        FxRating.builder().max(5).value(3).build(),
                        FxRating.builder().max(5).value(4).readonly(true).build()),

                Demo.block("FxSlider", "Single-value slider.",
                        FxSlider.builder().min(0).max(100).value(40).step(1).showValue(true).suffix("%").build()),

                Demo.block("FxSwitch", "On/off toggle switch.",
                        FxSwitch.builder().text("Off").build(),
                        FxSwitch.builder().text("On").selected(true).build()));
    }
}
