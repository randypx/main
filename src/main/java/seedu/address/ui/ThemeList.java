package seedu.address.ui;

import java.util.HashMap;

/**
 * Provide list of themes and respective URL to their CSS stylesheet
 */
public class ThemeList {
    private HashMap<String, String> themeList;

    public ThemeList() {
        themeList = new HashMap<>();
        themeList.put("dark", "view/DarkTheme.css");
    }

    public String getThemeStyleSheet(String theme) {
        if (!themeList.containsKey(theme)) {
            return themeList.get("dark");
        }
        return themeList.get(theme);
    }
}
