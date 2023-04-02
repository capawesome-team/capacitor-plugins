package io.capawesome.capacitorjs.plugins.datetimepicker;

public class DatetimePickerConfig {

    private Theme theme = Theme.AUTO;

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(String value, Theme fallback) {
        Theme theme = DatetimePickerHelper.convertStringToTheme(value);
        if (theme == null) {
            this.theme = fallback;
        } else {
            this.theme = theme;
        }
    }
}
