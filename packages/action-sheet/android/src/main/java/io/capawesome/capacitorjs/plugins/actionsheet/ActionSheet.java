package io.capawesome.capacitorjs.plugins.actionsheet;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import io.capawesome.capacitorjs.plugins.actionsheet.classes.options.ActionSheetButton;
import io.capawesome.capacitorjs.plugins.actionsheet.classes.options.ShowActionsOptions;
import io.capawesome.capacitorjs.plugins.actionsheet.classes.results.ShowActionsResult;
import io.capawesome.capacitorjs.plugins.actionsheet.interfaces.NonEmptyResultCallback;
import java.util.List;

public class ActionSheet {

    private static final int COLOR_DESTRUCTIVE = Color.parseColor("#B3261E");
    private static final int COLOR_MESSAGE = Color.parseColor("#757575");

    @NonNull
    private final ActionSheetPlugin plugin;

    public ActionSheet(@NonNull ActionSheetPlugin plugin) {
        this.plugin = plugin;
    }

    public void showActions(@NonNull ShowActionsOptions options, @NonNull NonEmptyResultCallback<ShowActionsResult> callback) {
        Activity activity = plugin.getActivity();
        activity.runOnUiThread(() -> {
            BottomSheetDialog dialog = new BottomSheetDialog(
                activity,
                com.google.android.material.R.style.Theme_MaterialComponents_DayNight_BottomSheetDialog
            );
            LinearLayout container = new LinearLayout(activity);
            container.setOrientation(LinearLayout.VERTICAL);
            int verticalPadding = dpToPx(activity, 8);
            container.setPadding(0, verticalPadding, 0, verticalPadding);

            if (options.getTitle() != null) {
                container.addView(createHeaderView(activity, options.getTitle(), true));
            }
            if (options.getMessage() != null) {
                container.addView(createHeaderView(activity, options.getMessage(), false));
            }

            List<ActionSheetButton> buttons = options.getButtons();
            int cancelIndex = -1;
            for (int i = 0; i < buttons.size(); i++) {
                ActionSheetButton button = buttons.get(i);
                if (button.isCancel()) {
                    if (cancelIndex == -1) {
                        cancelIndex = i;
                    }
                    continue;
                }
                final int index = i;
                TextView buttonView = createButtonView(activity, button.getTitle(), button.isDestructive());
                buttonView.setOnClickListener(view -> {
                    callback.success(new ShowActionsResult(index, false));
                    dialog.dismiss();
                });
                container.addView(buttonView);
            }

            final int resolvedCancelIndex = cancelIndex;
            if (cancelIndex != -1) {
                container.addView(createDividerView(activity));
                TextView cancelButtonView = createButtonView(activity, buttons.get(cancelIndex).getTitle(), false);
                cancelButtonView.setOnClickListener(view -> {
                    callback.success(new ShowActionsResult(resolvedCancelIndex, true));
                    dialog.dismiss();
                });
                container.addView(cancelButtonView);
            }

            dialog.setCancelable(options.isCancelable());
            dialog.setOnCancelListener(dialogInterface -> callback.success(new ShowActionsResult(resolvedCancelIndex, true)));

            NestedScrollView scrollView = new NestedScrollView(activity);
            scrollView.addView(container);
            dialog.setContentView(scrollView);
            dialog.show();
        });
    }

    @NonNull
    private TextView createButtonView(@NonNull Context context, @NonNull String title, boolean destructive) {
        TextView textView = new TextView(context);
        textView.setText(title);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        int horizontalPadding = dpToPx(context, 24);
        int verticalPadding = dpToPx(context, 16);
        textView.setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding);
        if (destructive) {
            textView.setTextColor(COLOR_DESTRUCTIVE);
        }
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
        textView.setBackgroundResource(outValue.resourceId);
        textView.setClickable(true);
        textView.setFocusable(true);
        return textView;
    }

    @NonNull
    private View createDividerView(@NonNull Context context) {
        View divider = new View(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dpToPx(context, 1));
        int margin = dpToPx(context, 8);
        params.setMargins(0, margin, 0, margin);
        divider.setLayoutParams(params);
        divider.setBackgroundColor(Color.parseColor("#1F000000"));
        return divider;
    }

    @NonNull
    private TextView createHeaderView(@NonNull Context context, @NonNull String text, boolean isTitle) {
        TextView textView = new TextView(context);
        textView.setText(text);
        int horizontalPadding = dpToPx(context, 24);
        int verticalPadding = dpToPx(context, 8);
        textView.setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding);
        if (isTitle) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            textView.setTypeface(Typeface.DEFAULT_BOLD);
        } else {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            textView.setTextColor(COLOR_MESSAGE);
        }
        return textView;
    }

    private int dpToPx(@NonNull Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
