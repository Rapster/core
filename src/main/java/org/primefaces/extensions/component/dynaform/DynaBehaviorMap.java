package org.primefaces.extensions.component.dynaform;

import javax.faces.component.behavior.ClientBehavior;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DynaBehaviorMap extends HashMap<String, List<ClientBehavior>> {

    @Override
    public List<ClientBehavior> get(Object key) {
        List<ClientBehavior> behaviors = super.get(key);
        if (behaviors == null) {
            // Use small initial capacity - we typically only have 1 behavior per event type.
            behaviors = new ArrayList<>(3);
            put((String) key, behaviors);
        }

        return behaviors;
    }

    public boolean add(String key, ClientBehavior behavior) {
        List<ClientBehavior> behaviors = get(key);
        return behaviors.add(behavior);
    }
}
