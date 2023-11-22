package com.greentree.engine.moon.script.javascript;

import com.greentree.engine.moon.script.Console;
import com.greentree.engine.moon.script.Script;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;

import java.util.Objects;

public final class JavaScriptScript implements Script {

    private static final String DEFAULT_NAME = "script1";
    private String name;
    private String script, old_script;
    private Context context;
    private ScriptableObject scope;

    public JavaScriptScript(String script) {
        this(DEFAULT_NAME, script);
    }

    public JavaScriptScript(String name, String script) {
        this.name = name;
        this.script = script;
        clear();

    }

    @Override
    public void clear() {
        if (!isClosing()) {
            if (context != null)
                context.close();
            context = Context.enter();
            scope = context.initSafeStandardObjects();
            set("console", new Console(System.out));
        }
    }

    @Override
    public void close() {
        context.close();
        context = null;
        script = null;
        old_script = null;
        scope = null;
    }

    @Override
    public void delete(String name) {
        if (!isClosing()) {
            ScriptableObject.deleteProperty(scope, name);
        }
    }

    @Override
    public <T> T get(Class<T> cls, String name) {
        if (!isClosing())
            return ScriptableObject.getTypedProperty(scope, name, cls);
        return null;
    }

    @Override
    public Object get(String name) {
        if (!isClosing())
            return ScriptableObject.getProperty(scope, name);
        return null;
    }

    @Override
    public void run() {
        if (!isClosing())
            try {
                context.evaluateString(scope, script, name, 0, null);
            } catch (Exception e) {
                script = old_script != null ? old_script : "";
                throw e;
            } finally {
                old_script = null;
            }
    }

    @Override
    public void set(String name, Object value) {
        if (!isClosing()) {
            if (ScriptableObject.hasProperty(scope, name)) {
                var js_value = Context.jsToJava(ScriptableObject.getProperty(scope, name),
                        value.getClass());
                if (js_value == value)
                    return;
            }
            final var js = Context.javaToJS(value, scope, context);
            ScriptableObject.putProperty(scope, name, js);
        }
    }

    @Override
    public void setConst(String name, Object value) {
        if (!isClosing()) {
            if (ScriptableObject.hasProperty(scope, name)) {
                var js_value = Context.jsToJava(ScriptableObject.getProperty(scope, name),
                        value.getClass());
                if (js_value == value)
                    return;
                throw new IllegalArgumentException("property " + name + " already added");
            }
            final var js = Context.javaToJS(value, scope, context);
            ScriptableObject.putConstProperty(scope, name, js);
        }
    }

    private boolean isClosing() {
        return script == null;
    }

    public void setScript(String name, String script) {
        this.name = Objects.requireNonNull(name);
        setScript(script);
    }

    public void setScript(String script) {
        if (old_script == null)
            old_script = this.script;
        this.script = script;
    }

}
