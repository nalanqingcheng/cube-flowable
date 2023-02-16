//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.flowable.ui.modeler.service.mapper;

import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.ErrorEventDefinition;
import org.flowable.bpmn.model.Event;
import org.flowable.bpmn.model.EventDefinition;
import org.flowable.bpmn.model.MessageEventDefinition;
import org.flowable.bpmn.model.SignalEventDefinition;
import org.flowable.bpmn.model.TimerEventDefinition;
import org.flowable.editor.language.json.converter.util.CollectionUtils;

public class EventInfoMapper extends AbstractInfoMapper {
    public EventInfoMapper() {
    }

    protected void mapProperties(Object element) {
        Event event = (Event)element;
        if (CollectionUtils.isNotEmpty(event.getEventDefinitions())) {
            EventDefinition eventDef = (EventDefinition)event.getEventDefinitions().get(0);
            if (eventDef instanceof TimerEventDefinition) {
                TimerEventDefinition timerDef = (TimerEventDefinition)eventDef;
                if (StringUtils.isNotEmpty(timerDef.getTimeDate())) {
                    this.createPropertyNode("Timer date", timerDef.getTimeDate());
                }

                if (StringUtils.isNotEmpty(timerDef.getTimeDuration())) {
                    this.createPropertyNode("Timer duration", timerDef.getTimeDuration());
                }

                if (StringUtils.isNotEmpty(timerDef.getTimeDuration())) {
                    this.createPropertyNode("Timer cycle", timerDef.getTimeCycle());
                }
            } else if (eventDef instanceof SignalEventDefinition) {
                SignalEventDefinition signalDef = (SignalEventDefinition)eventDef;
                if (StringUtils.isNotEmpty(signalDef.getSignalRef())) {
                    this.createPropertyNode("Signal ref", signalDef.getSignalRef());
                }
            } else if (eventDef instanceof MessageEventDefinition) {
                MessageEventDefinition messageDef = (MessageEventDefinition)eventDef;
                if (StringUtils.isNotEmpty(messageDef.getMessageRef())) {
                    this.createPropertyNode("Message ref", messageDef.getMessageRef());
                }
            } else if (eventDef instanceof ErrorEventDefinition) {
                ErrorEventDefinition errorDef = (ErrorEventDefinition)eventDef;
                if (StringUtils.isNotEmpty(errorDef.getErrorCode())) {
                    this.createPropertyNode("Error code", errorDef.getErrorCode());
                }
            }
        }

        this.createListenerPropertyNodes("Execution listeners", event.getExecutionListeners());
    }
}
