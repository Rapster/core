package org.primefaces.extensions.behavior.dynaform;

import org.primefaces.behavior.ajax.AjaxBehavior;
import org.primefaces.behavior.ajax.AjaxBehaviorListenerImpl;
import org.primefaces.behavior.base.AbstractBehaviorHandler;
import org.primefaces.component.api.PrimeClientBehaviorHolder;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.BehaviorEvent;
import javax.faces.view.facelets.BehaviorConfig;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import java.util.Map;

public class DynaAjaxBehaviorHandler extends AbstractBehaviorHandler<DynaAjaxBehavior> {

    private static final Class[] EMPTY_PARAMS = new Class[0];
    private static final Class[] ARG_PARAMS = new Class[]{AjaxBehaviorEvent.class};
    private final TagAttribute process;
    private final TagAttribute update;
    private final TagAttribute onstart;
    private final TagAttribute onerror;
    private final TagAttribute onsuccess;
    private final TagAttribute oncomplete;
    private final TagAttribute disabled;
    private final TagAttribute immediate;
    private final TagAttribute listener;
    private final TagAttribute global;
    private final TagAttribute async;
    private final TagAttribute partialSubmit;
    private final TagAttribute resetValues;
    private final TagAttribute ignoreAutoUpdate;
    private final TagAttribute delay;
    private final TagAttribute timeout;
    private final TagAttribute partialSubmitFilter;
    private final TagAttribute form;
    private final TagAttribute skipChildren;
    private final TagAttribute forx;

    public DynaAjaxBehaviorHandler(BehaviorConfig config) {
        super(config);
        process = getAttribute(AjaxBehavior.PropertyKeys.process.name());
        update = getAttribute(AjaxBehavior.PropertyKeys.update.name());
        onstart = getAttribute(AjaxBehavior.PropertyKeys.onstart.name());
        onerror = getAttribute(AjaxBehavior.PropertyKeys.onerror.name());
        onsuccess = getAttribute(AjaxBehavior.PropertyKeys.onsuccess.name());
        oncomplete = getAttribute(AjaxBehavior.PropertyKeys.oncomplete.name());
        disabled = getAttribute(AjaxBehavior.PropertyKeys.disabled.name());
        immediate = getAttribute(AjaxBehavior.PropertyKeys.immediate.name());
        listener = getAttribute(AjaxBehavior.PropertyKeys.listener.name());
        global = getAttribute(AjaxBehavior.PropertyKeys.global.name());
        async = getAttribute(AjaxBehavior.PropertyKeys.async.name());
        partialSubmit = getAttribute(AjaxBehavior.PropertyKeys.partialSubmit.name());
        resetValues = getAttribute(AjaxBehavior.PropertyKeys.resetValues.name());
        ignoreAutoUpdate = getAttribute(AjaxBehavior.PropertyKeys.ignoreAutoUpdate.name());
        delay = getAttribute(AjaxBehavior.PropertyKeys.delay.name());
        timeout = getAttribute(AjaxBehavior.PropertyKeys.timeout.name());
        partialSubmitFilter = getAttribute(AjaxBehavior.PropertyKeys.partialSubmitFilter.name());
        form = getAttribute(AjaxBehavior.PropertyKeys.form.name());
        skipChildren = getAttribute(AjaxBehavior.PropertyKeys.skipChildren.name());
        forx = getAttribute(DynaAjaxBehavior.PropertyKeys.forx.toString());
    }

    @Override
    protected DynaAjaxBehavior createBehavior(FaceletContext ctx, String eventName, UIComponent parent) {
        Application application = ctx.getFacesContext().getApplication();
        DynaAjaxBehavior behavior = (DynaAjaxBehavior) application.createBehavior(DynaAjaxBehavior.BEHAVIOR_ID);
        setBehaviorAttribute(ctx, behavior, process, AjaxBehavior.PropertyKeys.process.getExpectedType());
        setBehaviorAttribute(ctx, behavior, update, AjaxBehavior.PropertyKeys.update.getExpectedType());
        setBehaviorAttribute(ctx, behavior, onstart, AjaxBehavior.PropertyKeys.onstart.getExpectedType());
        setBehaviorAttribute(ctx, behavior, onerror, AjaxBehavior.PropertyKeys.onerror.getExpectedType());
        setBehaviorAttribute(ctx, behavior, onsuccess, AjaxBehavior.PropertyKeys.onsuccess.getExpectedType());
        setBehaviorAttribute(ctx, behavior, oncomplete, AjaxBehavior.PropertyKeys.oncomplete.getExpectedType());
        setBehaviorAttribute(ctx, behavior, disabled, AjaxBehavior.PropertyKeys.disabled.getExpectedType());
        setBehaviorAttribute(ctx, behavior, immediate, AjaxBehavior.PropertyKeys.immediate.getExpectedType());
        setBehaviorAttribute(ctx, behavior, global, AjaxBehavior.PropertyKeys.global.getExpectedType());
        setBehaviorAttribute(ctx, behavior, async, AjaxBehavior.PropertyKeys.async.getExpectedType());
        setBehaviorAttribute(ctx, behavior, partialSubmit, AjaxBehavior.PropertyKeys.partialSubmit.getExpectedType());
        setBehaviorAttribute(ctx, behavior, listener, AjaxBehavior.PropertyKeys.listener.getExpectedType());
        setBehaviorAttribute(ctx, behavior, resetValues, AjaxBehavior.PropertyKeys.resetValues.getExpectedType());
        setBehaviorAttribute(ctx, behavior, ignoreAutoUpdate, AjaxBehavior.PropertyKeys.ignoreAutoUpdate.getExpectedType());
        setBehaviorAttribute(ctx, behavior, delay, AjaxBehavior.PropertyKeys.delay.getExpectedType());
        setBehaviorAttribute(ctx, behavior, timeout, AjaxBehavior.PropertyKeys.timeout.getExpectedType());
        setBehaviorAttribute(ctx, behavior, partialSubmitFilter, AjaxBehavior.PropertyKeys.partialSubmitFilter.getExpectedType());
        setBehaviorAttribute(ctx, behavior, form, AjaxBehavior.PropertyKeys.form.getExpectedType());
        setBehaviorAttribute(ctx, behavior, skipChildren, AjaxBehavior.PropertyKeys.skipChildren.getExpectedType());
        setBehaviorAttribute(ctx, behavior, forx, DynaAjaxBehavior.PropertyKeys.forx.getExpectedType());

        if (listener != null) {
            Class<? extends BehaviorEvent> eventMappingClass = null;
            if (parent instanceof PrimeClientBehaviorHolder) {
                Map<String, Class<? extends BehaviorEvent>> mapping = ((PrimeClientBehaviorHolder) parent).getBehaviorEventMapping();
                if (mapping != null) {
                    eventMappingClass = (Class) mapping.get(eventName);
                }
            }

            if (eventMappingClass == null) {
                behavior.addAjaxBehaviorListener(new AjaxBehaviorListenerImpl(
                        listener.getMethodExpression(ctx, Void.class, EMPTY_PARAMS),
                        listener.getMethodExpression(ctx, Void.class, ARG_PARAMS)));
            } else {
                behavior.addAjaxBehaviorListener(new AjaxBehaviorListenerImpl(
                        listener.getMethodExpression(ctx, Void.class, EMPTY_PARAMS),
                        listener.getMethodExpression(ctx, Void.class, ARG_PARAMS),
                        listener.getMethodExpression(ctx, Void.class, new Class[]{eventMappingClass})));
            }
        }

        return behavior;
    }


}
