package io.capawesome.capacitorjs.plugins.appshortcut.classes.results;

import androidx.annotation.NonNull;
import androidx.core.content.pm.ShortcutInfoCompat;

import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;

import java.util.List;

import io.capawesome.capacitorjs.plugins.appshortcut.interfaces.Result;

public class GetResult implements Result {
  private final List<ShortcutInfoCompat> shortcuts;

  public GetResult(@NonNull List<ShortcutInfoCompat> shortcuts) {
    this.shortcuts = shortcuts;
  }

  @NonNull
  public JSObject toJSObject() {
    JSArray resultArray = new JSArray();
    for (ShortcutInfoCompat shortcut: shortcuts) {
      JSObject shortcutObject = new JSObject();
      shortcutObject.put("id", shortcut.getId());
      CharSequence title = shortcut.getShortLabel();
      shortcutObject.put("title", title.toString());
      CharSequence description = shortcut.getLongLabel();
      shortcutObject.put("description", description == null ? "" : description.toString());
      resultArray.put(shortcutObject);
    }
    JSObject result = new JSObject();
    result.put("shortcuts", resultArray);
    return result;
  }
}
