package org.primefaces.extensions.behavior.dynaform;

import org.primefaces.behavior.ajax.AjaxBehavior;

public class DynaAjaxBehavior extends AjaxBehavior {

    public static final String BEHAVIOR_ID = "org.primefaces.extensions.dynaform.DynaAjaxBehavior";

    public enum PropertyKeys {
        forx(String.class, "for");

        private final Class<?> expectedType;
        private String name;

        PropertyKeys(Class<?> expectedType, String name) {
            this.expectedType = expectedType;
            this.name = name;
        }

        public Class<?> getExpectedType() {
            return expectedType;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public String getFor() {
        return (String) eval(PropertyKeys.forx.toString(), (Object) null);
    }

    public void setFor(String forx) {
        put(PropertyKeys.forx.toString(), forx);
    }
}
