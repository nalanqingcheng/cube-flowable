//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.flowable.ui.modeler.service.mapper;

import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.ScriptTask;

public class ScriptTaskInfoMapper extends AbstractInfoMapper {
    public ScriptTaskInfoMapper() {
    }

    protected void mapProperties(Object element) {
        ScriptTask scriptTask = (ScriptTask)element;
        if (StringUtils.isNotEmpty(scriptTask.getScriptFormat())) {
            this.createPropertyNode("Script format", scriptTask.getScriptFormat());
        }

        if (StringUtils.isNotEmpty(scriptTask.getScript())) {
            this.createPropertyNode("Script", scriptTask.getScript());
        }

        this.createListenerPropertyNodes("Execution listeners", scriptTask.getExecutionListeners());
    }
}
