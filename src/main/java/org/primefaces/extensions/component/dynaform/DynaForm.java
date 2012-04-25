/*
 * Copyright 2011-2012 PrimeFaces Extensions.
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
 *
 * $Id$
 */

package org.primefaces.extensions.component.dynaform;

import java.util.ArrayList;
import java.util.List;

import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;

import org.primefaces.component.api.Widget;
import org.primefaces.extensions.component.base.AbstractDynamicData;
import org.primefaces.extensions.model.common.DataWrapper;
import org.primefaces.extensions.model.dynaform.DynaFormElement;

/**
 * <code>DynaForm</code> component.
 *
 * @author  Oleg Varaksin / last modified by $Author$
 * @version $Revision$
 */
@ResourceDependencies({
                          @ResourceDependency(library = "primefaces", name = "jquery/jquery.js"),
                          @ResourceDependency(library = "primefaces", name = "primefaces.js"),
                          @ResourceDependency(library = "primefaces-extensions", name = "primefaces-extensions.css"),
                          @ResourceDependency(library = "primefaces-extensions", name = "primefaces-extensions.js")
                      })
public class DynaForm extends AbstractDynamicData implements Widget {

	public static final String COMPONENT_FAMILY = "org.primefaces.extensions.component";
	private static final String DEFAULT_RENDERER = "org.primefaces.extensions.component.DynaFormRenderer";

	/**
	 * Properties that are tracked by state saving.
	 *
	 * @author  Oleg Varaksin / last modified by $Author$
	 * @version $Revision$
	 */
	protected enum PropertyKeys {

		widgetVar,
		labelPosition, // "left" | "top" | "right"
		autoSubmit,
		style,
		styleClass;

		private String toString;

		PropertyKeys(final String toString) {
			this.toString = toString;
		}

		PropertyKeys() {
		}

		@Override
		public String toString() {
			return ((this.toString != null) ? this.toString : super.toString());
		}
	}

	public DynaForm() {
		setRendererType(DEFAULT_RENDERER);
	}

	@Override
	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	public String getWidgetVar() {
		return (String) getStateHelper().eval(PropertyKeys.widgetVar, null);
	}

	public void setWidgetVar(final String widgetVar) {
		setAttribute(PropertyKeys.widgetVar, widgetVar);
	}

	public void setLabelPosition(final String labelPosition) {
		setAttribute(PropertyKeys.labelPosition, labelPosition);
	}

	public String getLabelPosition() {
		return (String) getStateHelper().eval(PropertyKeys.labelPosition, "left");
	}

	public boolean isAutoSubmit() {
		return (Boolean) getStateHelper().eval(PropertyKeys.autoSubmit, false);
	}

	public void setAutoSubmit(final boolean autoSubmit) {
		setAttribute(PropertyKeys.autoSubmit, autoSubmit);
	}

	public void setStyle(final String style) {
		setAttribute(PropertyKeys.style, style);
	}

	public String getStyle() {
		return (String) getStateHelper().eval(PropertyKeys.style, null);
	}

	public void setStyleClass(final String styleClass) {
		setAttribute(PropertyKeys.styleClass, styleClass);
	}

	public String getStyleClass() {
		return (String) getStateHelper().eval(PropertyKeys.styleClass, null);
	}

	public String resolveWidgetVar() {
		final FacesContext context = FacesContext.getCurrentInstance();
		final String userWidgetVar = (String) getAttributes().get(PropertyKeys.widgetVar.toString());

		if (userWidgetVar != null) {
			return userWidgetVar;
		}

		return "widget_" + getClientId(context).replaceAll("-|" + UINamingContainer.getSeparatorChar(context), "_");
	}

	public void setAttribute(final PropertyKeys property, final Object value) {
		getStateHelper().put(property, value);

		@SuppressWarnings("unchecked")
		List<String> setAttributes =
		    (List<String>) this.getAttributes().get("javax.faces.component.UIComponentBase.attributesThatAreSet");
		if (setAttributes == null) {
			final String cname = this.getClass().getName();
			if (cname != null && cname.startsWith(OPTIMIZED_PACKAGE)) {
				setAttributes = new ArrayList<String>(6);
				this.getAttributes().put("javax.faces.component.UIComponentBase.attributesThatAreSet", setAttributes);
			}
		}

		if (setAttributes != null && value == null) {
			final String attributeName = property.toString();
			final ValueExpression ve = getValueExpression(attributeName);
			if (ve == null) {
				setAttributes.remove(attributeName);
			} else if (!setAttributes.contains(attributeName)) {
				setAttributes.add(attributeName);
			}
		}
	}

	@Override
	protected DataWrapper findData(final String key) {
		Object value = getValue();
		if (value == null) {
			return null;
		}

		DataWrapper dataWrapper = null;
		Class clazz = value.getClass();
		if (List.class.isAssignableFrom(clazz)) {
			List dynaFormElements = (List) value;
			for (Object obj : dynaFormElements) {
				if (obj instanceof DynaFormElement) {
					DynaFormElement dynaFormElement = (DynaFormElement) obj;
					if (key.equals(dynaFormElement.getKey())) {
						return dynaFormElement;
					}
				} else {
					throw new FacesException("Elements in DynaForm must be of type DynaFormElement");
				}
			}
		} else if (clazz.isArray()) {
			Class elementType = clazz.getComponentType();
			if (!DynaFormElement.class.isAssignableFrom(elementType)) {
				throw new FacesException("Elements in DynaForm must be of type DynaFormElement");
			}

			DynaFormElement[] dynaFormElements = (DynaFormElement[]) value;
			for (DynaFormElement dynaFormElement : dynaFormElements) {
				if (key.equals(dynaFormElement.getKey())) {
					return dynaFormElement;
				}
			}
		} else {
			throw new FacesException("Value of DynaForm must be either List oder Array");
		}

		return dataWrapper;
	}
}
