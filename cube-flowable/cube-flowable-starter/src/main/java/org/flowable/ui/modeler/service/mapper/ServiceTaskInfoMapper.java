//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.flowable.ui.modeler.service.mapper;

import org.flowable.bpmn.model.ServiceTask;

public class ServiceTaskInfoMapper extends AbstractInfoMapper {
    public ServiceTaskInfoMapper() {
    }

    protected void mapProperties(Object element) {
        ServiceTask serviceTask = (ServiceTask)element;
        if ("class".equals(serviceTask.getImplementationType())) {
            this.createPropertyNode("Class", serviceTask.getImplementation());
        } else if ("expression".equals(serviceTask.getImplementationType())) {
            this.createPropertyNode("Expression", serviceTask.getImplementation());
        } else if ("delegateExpression".equals(serviceTask.getImplementationType())) {
            this.createPropertyNode("Delegate expression", serviceTask.getImplementation());
        }

        if (serviceTask.isAsynchronous()) {
            this.createPropertyNode("Asynchronous", true);
            this.createPropertyNode("Exclusive", !serviceTask.isNotExclusive());
        }

        if ("mail".equalsIgnoreCase(serviceTask.getType())) {
            this.createPropertyNode("Type", "Mail task");
        }

        if ("http".equalsIgnoreCase(serviceTask.getType())) {
            this.createPropertyNode("Type", "Http task");
        }

        this.createPropertyNode("Result variable name", serviceTask.getResultVariableName());
        this.createFieldPropertyNodes("Field extensions", serviceTask.getFieldExtensions());
        this.createListenerPropertyNodes("Execution listeners", serviceTask.getExecutionListeners());
        this.createPropertyNode("Use local scope for result variable", serviceTask.isUseLocalScopeForResultVariable());
        this.createPropertyNode("Failed job retry time cycle", serviceTask.getFailedJobRetryTimeCycleValue());
    }
}
