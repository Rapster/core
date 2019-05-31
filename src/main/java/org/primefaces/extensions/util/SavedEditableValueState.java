/**
 * Copyright 2011-2019 PrimeFaces Extensions
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.primefaces.extensions.util;

import org.primefaces.component.api.SavedState;

import javax.faces.component.behavior.ClientBehavior;
import java.util.*;

/**
 * Keeps state of a component implementing {@link javax.faces.component.EditableValueHolder}. This class is used in
 * {@link org.primefaces.extensions.component.base.AbstractDynamicData}.
 *
 * @author Oleg Varaksin / last modified by $Author$
 * @version $Revision$
 * @since 0.5
 */
public class SavedEditableValueState extends SavedState {

    private static final long serialVersionUID = 20120425L;

    private Object labelValue;

    private Object disabled;

    private Map<String, List<ClientBehavior>> behaviors;

    public void reset() {
        setSubmittedValue(null);
        setValid(true);
        setValue(null);
        setLocalValueSet(false);
    }

    public Object getLabelValue() {
        return labelValue;
    }

    public void setLabelValue(Object labelValue) {
        this.labelValue = labelValue;
    }

    public Object getDisabled() {
        return disabled;
    }

    public void setDisabled(Object disabled) {
        this.disabled = disabled;
    }

    public Map<String, List<ClientBehavior>> getBehaviors() {
        if (behaviors == null) {
            return Collections.emptyMap();
        }
        return behaviors;
    }

    public void setBehaviors(Map<String, List<ClientBehavior>> behaviors) {
        this.behaviors = new HashMap<>();

        for (Map.Entry<String, List<ClientBehavior>> entry : behaviors.entrySet()) {
            this.behaviors.put(entry.getKey(), Collections.unmodifiableList(new ArrayList<>(entry.getValue())));
        }

        this.behaviors = Collections.unmodifiableMap(this.behaviors);
    }
}
