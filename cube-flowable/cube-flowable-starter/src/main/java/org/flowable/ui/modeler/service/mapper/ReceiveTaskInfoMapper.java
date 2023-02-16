//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.flowable.ui.modeler.service.mapper;

import org.flowable.bpmn.model.ReceiveTask;

public class ReceiveTaskInfoMapper extends AbstractInfoMapper {
    public ReceiveTaskInfoMapper() {
    }

    protected void mapProperties(Object element) {
        ReceiveTask receiveTask = (ReceiveTask)element;
        this.createListenerPropertyNodes("Execution listeners", receiveTask.getExecutionListeners());
    }
}
