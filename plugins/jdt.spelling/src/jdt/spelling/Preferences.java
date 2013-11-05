package jdt.spelling;

import java.util.HashMap;
import java.util.Map;

import jdt.spelling.checker.JavaNameType;
import jdt.spelling.checker.JavaType;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.osgi.service.prefs.BackingStoreException;

public class Preferences extends AbstractPreferenceInitializer {

	public static final String JDT_SPELLING_MARKER_COLOR = "jdt.spelling.marker.color";
	public static final String JDT_SPELLING_MARKER_HIGHLIGHT = "jdt.spelling.marker.highlight";
	public static final String JDT_SPELLING_MARKER_OVERVIEW = "jdt.spelling.marker.overview";
	public static final String JDT_SPELLING_MARKER_TEXT = "jdt.spelling.marker.text";
	public static final String JDT_SPELLING_MARKER_RULER = "jdt.spelling.marker.ruler";
	public static final String JDT_SPELLING_IGNORE_SINGLE_LETTER = "jdt.spelling.ignore.single.letter";

	private static final Map<String, Object> DEFAULTS = new HashMap<String, Object>();

	static {
		DEFAULTS.put(JavaType.TYPE.name(), JavaNameType.UPPER_CAMEL_CASE.name());
		DEFAULTS.put(JavaType.ENUM_TYPE.name(), JavaNameType.UPPER_CAMEL_CASE.name());
		DEFAULTS.put(JavaType.ANNOTATION.name(), JavaNameType.UPPER_CAMEL_CASE.name());
		DEFAULTS.put(JavaType.CONSTANT.name(), JavaNameType.UPPER.name());
		DEFAULTS.put(JavaType.METHOD.name(), JavaNameType.LOWER_CAMEL_CASE.name());
		DEFAULTS.put(JavaType.PACKAGE_DECLARATION.name(), JavaNameType.DOT.name());
		DEFAULTS.put(JavaType.FIELD.name(), JavaNameType.LOWER_CAMEL_CASE.name());
		DEFAULTS.put(JavaType.ENUM_INSTANCE.name(), JavaNameType.UPPER.name());
		DEFAULTS.put(JavaType.LOCAL_VARIABLE.name(), JavaNameType.LOWER_CAMEL_CASE.name());
		DEFAULTS.put(JavaType.FIELD.name(), JavaNameType.LOWER_CAMEL_CASE.name());
		DEFAULTS.put(JDT_SPELLING_IGNORE_SINGLE_LETTER, true);
	}

	@Override
	public void initializeDefaultPreferences() {
		Preferences.restoreDefaults();
	}

	public static void restoreDefaults() {
		IEclipsePreferences prefs = DefaultScope.INSTANCE.getNode(Plugin.getPluginId());

		restoreDefaultString(prefs, JavaType.TYPE.name());
		restoreDefaultString(prefs, JavaType.ENUM_TYPE.name());
		restoreDefaultString(prefs, JavaType.ANNOTATION.name());
		restoreDefaultString(prefs, JavaType.CONSTANT.name());
		restoreDefaultString(prefs, JavaType.METHOD.name());
		restoreDefaultString(prefs, JavaType.PACKAGE_DECLARATION.name());
		restoreDefaultString(prefs, JavaType.FIELD.name());
		restoreDefaultString(prefs, JavaType.ENUM_INSTANCE.name());
		restoreDefaultString(prefs, JavaType.LOCAL_VARIABLE.name());
		restoreDefaultString(prefs, JavaType.FIELD.name());
		restoreDefaultBoolean(prefs, JDT_SPELLING_IGNORE_SINGLE_LETTER);

		try {
			prefs.flush();
		} catch (BackingStoreException e) {
			Plugin.log(e);
		}
	}

	private static void restoreDefaultString(IEclipsePreferences prefs, String name) {
		prefs.put(name, (String) DEFAULTS.get(name));
	}

	private static void restoreDefaultBoolean(IEclipsePreferences prefs, String name) {
		prefs.putBoolean(name, (Boolean) DEFAULTS.get(name));
	}

	private static void restoreDefaultInt(IEclipsePreferences prefs, String name) {
		prefs.putBoolean(name, (Boolean) DEFAULTS.get(name));
	}

	public static boolean getBoolean(String prefId) {
		IEclipsePreferences prefs = DefaultScope.INSTANCE.getNode(Plugin.getPluginId());
		return prefs.getBoolean(prefId, (Boolean) DEFAULTS.get(prefId));
	}

	public static int getInt(String prefId) {
		IEclipsePreferences prefs = DefaultScope.INSTANCE.getNode(Plugin.getPluginId());
		return prefs.getInt(prefId, (Integer) DEFAULTS.get(prefId));
	}

	public static String getString(String prefId) {
		IEclipsePreferences prefs = DefaultScope.INSTANCE.getNode(Plugin.getPluginId());
		return prefs.get(prefId, (String) DEFAULTS.get(prefId));
	}

	public static void setBoolean(String prefId, boolean value) throws BackingStoreException {
		IEclipsePreferences prefs = DefaultScope.INSTANCE.getNode(Plugin.getPluginId());
		prefs.putBoolean(prefId, value);
		prefs.flush();
	}

	public static void setInt(String prefId, int value) throws BackingStoreException {
		IEclipsePreferences prefs = DefaultScope.INSTANCE.getNode(Plugin.getPluginId());
		prefs.putInt(prefId, value);
		prefs.flush();
	}

	public static void setString(String prefId, String value) throws BackingStoreException {
		IEclipsePreferences prefs = DefaultScope.INSTANCE.getNode(Plugin.getPluginId());
		prefs.put(prefId, value);
		prefs.flush();
	}

	public static void setJavaNameType(JavaType javaType, JavaNameType javaNameType) throws BackingStoreException {
		setString(javaType.name(), javaNameType.name());
	}

	public static JavaNameType getJavaNameType(JavaType javaType) {
		String name = getString(javaType.name());
		return JavaNameType.valueOf(name);
	}
}