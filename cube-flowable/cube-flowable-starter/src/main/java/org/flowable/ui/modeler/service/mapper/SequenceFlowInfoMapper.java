//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.flowable.ui.modeler.service.mapper;

import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.SequenceFlow;

public class SequenceFlowInfoMapper extends AbstractInfoMapper {
    public SequenceFlowInfoMapper() {
    }

    protected void mapProperties(Object element) {
        SequenceFlow sequenceFlow = (SequenceFlow)element;
        if (StringUtils.isNotEmpty(sequenceFlow.getConditionExpression())) {
            this.createPropertyNode("Condition expression", sequenceFlow.getConditionExpression());
        }

        this.createListenerPropertyNodes("Execution listeners", sequenceFlow.getExecutionListeners());
    }
}
