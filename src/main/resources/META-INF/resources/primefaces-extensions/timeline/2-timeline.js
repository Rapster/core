/**
 * PrimeFaces Extensions Timeline Widget.
 *
 * @author Nilesh Namdeo Mali
 */
PrimeFacesExt.widget.Timeline = PrimeFaces.widget.BaseWidget.extend({

    /**
     * Initializes the widget.
     *
     * @param {object} cfg The widget configuration.
     */
    init:function (cfg) {
        this._super(cfg);
        this.jq = $(PrimeFaces.escapeClientId(this.id));
        this.jq.width(cfg.width);
        this.jq.height(cfg.height);
        var _self = this;
        var options = {
            'width':"100%",
            'height':this.jq.height() + "px",
            'selectable':false,

            'showCurrentTime':true, // show a red bar displaying the current time
            'showCustomTime':false, // show a blue, draggable bar displaying a custom time

            'showNavigation':cfg.showNavigation,

            'groupsOnRight':false,
            'axisOnTop':(cfg.axisPosition == "top") ? true : false,
            'style':cfg.eventStyle
        };

        this.jq.timeline(cfg.dataSource, options);

        PrimeFacesExt.removeWidgetScript(cfg.id);
    },
    checkResize:function () {
        this.jq.timeline('checkResize');
    }
});
