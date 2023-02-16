//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.flowable.ui.modeler.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.Artifact;
import org.flowable.bpmn.model.Association;
import org.flowable.bpmn.model.BoundaryEvent;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.ConditionalEventDefinition;
import org.flowable.bpmn.model.DataObject;
import org.flowable.bpmn.model.ErrorEventDefinition;
import org.flowable.bpmn.model.EscalationEventDefinition;
import org.flowable.bpmn.model.Event;
import org.flowable.bpmn.model.EventDefinition;
import org.flowable.bpmn.model.EventSubProcess;
import org.flowable.bpmn.model.ExtensionElement;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.GraphicInfo;
import org.flowable.bpmn.model.Lane;
import org.flowable.bpmn.model.MessageEventDefinition;
import org.flowable.bpmn.model.Pool;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.SequenceFlow;
import org.flowable.bpmn.model.ServiceTask;
import org.flowable.bpmn.model.SignalEventDefinition;
import org.flowable.bpmn.model.StartEvent;
import org.flowable.bpmn.model.SubProcess;
import org.flowable.bpmn.model.TextAnnotation;
import org.flowable.bpmn.model.TimerEventDefinition;
import org.flowable.editor.language.json.converter.BpmnJsonConverter;
import org.flowable.editor.language.json.converter.util.CollectionUtils;
import org.flowable.ui.modeler.domain.AbstractModel;
import org.flowable.ui.modeler.service.mapper.EventInfoMapper;
import org.flowable.ui.modeler.service.mapper.InfoMapper;
import org.flowable.ui.modeler.service.mapper.ReceiveTaskInfoMapper;
import org.flowable.ui.modeler.service.mapper.SequenceFlowInfoMapper;
import org.flowable.ui.modeler.service.mapper.ServiceTaskInfoMapper;
import org.flowable.ui.modeler.service.mapper.UserTaskInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BpmnDisplayJsonConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(BpmnDisplayJsonConverter.class);
    protected BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();
    protected ObjectMapper objectMapper = new ObjectMapper();
    protected List<String> eventElementTypes = new ArrayList();
    protected Map<String, InfoMapper> propertyMappers = new HashMap();

    public BpmnDisplayJsonConverter() {
        this.eventElementTypes.add("StartEvent");
        this.eventElementTypes.add("EndEvent");
        this.eventElementTypes.add("BoundaryEvent");
        this.eventElementTypes.add("IntermediateCatchEvent");
        this.eventElementTypes.add("ThrowEvent");
        this.propertyMappers.put("BoundaryEvent", new EventInfoMapper());
        this.propertyMappers.put("EndEvent", new EventInfoMapper());
        this.propertyMappers.put("IntermediateCatchEvent", new EventInfoMapper());
        this.propertyMappers.put("ReceiveTask", new ReceiveTaskInfoMapper());
        this.propertyMappers.put("StartEvent", new EventInfoMapper());
        this.propertyMappers.put("SequenceFlow", new SequenceFlowInfoMapper());
        this.propertyMappers.put("ServiceTask", new ServiceTaskInfoMapper());
        this.propertyMappers.put("ThrowEvent", new EventInfoMapper());
        this.propertyMappers.put("UserTask", new UserTaskInfoMapper());
    }

    public void processProcessElements(AbstractModel processModel, ObjectNode displayNode, GraphicInfo diagramInfo) {
        BpmnModel pojoModel = null;
        if (!StringUtils.isEmpty(processModel.getModelEditorJson())) {
            try {
                JsonNode modelNode = this.objectMapper.readTree(processModel.getModelEditorJson());
                pojoModel = this.bpmnJsonConverter.convertToBpmnModel(modelNode);
            } catch (Exception var20) {
                LOGGER.error("Error transforming json to pojo {}", processModel.getId(), var20);
            }
        }

        if (pojoModel != null && !pojoModel.getLocationMap().isEmpty()) {
            ArrayNode elementArray = this.objectMapper.createArrayNode();
            ArrayNode flowArray = this.objectMapper.createArrayNode();
            if (CollectionUtils.isNotEmpty(pojoModel.getPools())) {
                ArrayNode poolArray = this.objectMapper.createArrayNode();
                boolean firstElement = true;

                for(Iterator var9 = pojoModel.getPools().iterator(); var9.hasNext(); firstElement = false) {
                    Pool pool = (Pool)var9.next();
                    ObjectNode poolNode = this.objectMapper.createObjectNode();
                    poolNode.put("id", pool.getId());
                    poolNode.put("name", pool.getName());
                    GraphicInfo poolInfo = pojoModel.getGraphicInfo(pool.getId());
                    this.fillGraphicInfo(poolNode, poolInfo, true);
                    Process process = pojoModel.getProcess(pool.getId());
                    if (process != null && CollectionUtils.isNotEmpty(process.getLanes())) {
                        ArrayNode laneArray = this.objectMapper.createArrayNode();
                        Iterator var15 = process.getLanes().iterator();

                        while(var15.hasNext()) {
                            Lane lane = (Lane)var15.next();
                            ObjectNode laneNode = this.objectMapper.createObjectNode();
                            laneNode.put("id", lane.getId());
                            laneNode.put("name", lane.getName());
                            this.fillGraphicInfo(laneNode, pojoModel.getGraphicInfo(lane.getId()), true);
                            laneArray.add(laneNode);
                        }

                        poolNode.set("lanes", laneArray);
                    }

                    poolArray.add(poolNode);
                    double rightX = poolInfo.getX() + poolInfo.getWidth();
                    double bottomY = poolInfo.getY() + poolInfo.getHeight();
                    double middleX = poolInfo.getX() + poolInfo.getWidth() / 2.0D;
                    if (firstElement || middleX < diagramInfo.getX()) {
                        diagramInfo.setX(middleX);
                    }

                    if (firstElement || poolInfo.getY() < diagramInfo.getY()) {
                        diagramInfo.setY(poolInfo.getY());
                    }

                    if (rightX > diagramInfo.getWidth()) {
                        diagramInfo.setWidth(rightX);
                    }

                    if (bottomY > diagramInfo.getHeight()) {
                        diagramInfo.setHeight(bottomY);
                    }
                }

                displayNode.set("pools", poolArray);
            } else {
                diagramInfo.setX(9999.0D);
                diagramInfo.setY(1000.0D);
            }

            Iterator var22 = pojoModel.getProcesses().iterator();

            while(var22.hasNext()) {
                Process process = (Process)var22.next();
                this.processElements(process.getFlowElements(), pojoModel, elementArray, flowArray, diagramInfo);
                this.processArtifacts(process.getArtifacts(), pojoModel, elementArray, flowArray, diagramInfo);
            }

            displayNode.set("elements", elementArray);
            displayNode.set("flows", flowArray);
            displayNode.put("diagramBeginX", diagramInfo.getX());
            displayNode.put("diagramBeginY", diagramInfo.getY());
            displayNode.put("diagramWidth", diagramInfo.getWidth());
            displayNode.put("diagramHeight", diagramInfo.getHeight());
        }
    }

    protected void processElements(Collection<FlowElement> elementList, BpmnModel model, ArrayNode elementArray, ArrayNode flowArray, GraphicInfo diagramInfo) {
        Iterator var6 = elementList.iterator();

        while(true) {
            FlowElement element;
            ObjectNode elementNode;
            List flowInfo;
            do {
                while(true) {
                    do {
                        if (!var6.hasNext()) {
                            return;
                        }

                        element = (FlowElement)var6.next();
                    } while(DataObject.class.isInstance(element));

                    if (element instanceof SequenceFlow) {
                        elementNode = this.objectMapper.createObjectNode();
                        SequenceFlow flow = (SequenceFlow)element;
                        elementNode.put("id", flow.getId());
                        elementNode.put("type", "sequenceFlow");
                        elementNode.put("sourceRef", flow.getSourceRef());
                        elementNode.put("targetRef", flow.getTargetRef());
                        elementNode.put("name", flow.getName());
                        flowInfo = model.getFlowLocationGraphicInfo(flow.getId());
                        break;
                    }

                    elementNode = this.objectMapper.createObjectNode();
                    elementNode.put("id", element.getId());
                    elementNode.put("name", element.getName());
                    GraphicInfo graphicInfo = model.getGraphicInfo(element.getId());
                    if (graphicInfo != null) {
                        this.fillGraphicInfo(elementNode, graphicInfo, true);
                        this.fillDiagramInfo(graphicInfo, diagramInfo);
                    }

                    String className = element.getClass().getSimpleName();
                    elementNode.put("type", className);
                    this.fillEventTypes(className, element, elementNode);
                    if (element instanceof ServiceTask) {
                        ServiceTask serviceTask = (ServiceTask)element;
                        if ("mail".equals(serviceTask.getType())) {
                            elementNode.put("taskType", "mail");
                        } else if ("camel".equals(serviceTask.getType())) {
                            elementNode.put("taskType", "camel");
                        } else if ("mule".equals(serviceTask.getType())) {
                            elementNode.put("taskType", "mule");
                        } else if ("http".equals(serviceTask.getType())) {
                            elementNode.put("taskType", "http");
                        } else if ("shell".equals(serviceTask.getType())) {
                            elementNode.put("taskType", "shell");
                        }
                    } else if (element instanceof BoundaryEvent) {
                        BoundaryEvent boundaryEvent = (BoundaryEvent)element;
                        elementNode.put("cancelActivity", boundaryEvent.isCancelActivity());
                    } else if (element instanceof StartEvent) {
                        StartEvent startEvent = (StartEvent)element;
                        if (startEvent.getSubProcess() instanceof EventSubProcess && !startEvent.isInterrupting()) {
                            elementNode.put("interrupting", false);
                        } else {
                            elementNode.put("interrupting", true);
                        }
                    }

                    if (this.propertyMappers.containsKey(className)) {
                        elementNode.set("properties", ((InfoMapper)this.propertyMappers.get(className)).map(element));
                    }

                    elementArray.add(elementNode);
                    if (element instanceof SubProcess) {
                        SubProcess subProcess = (SubProcess)element;
                        if (graphicInfo == null || graphicInfo.getExpanded() == null || graphicInfo.getExpanded()) {
                            this.processElements(subProcess.getFlowElements(), model, elementArray, flowArray, diagramInfo);
                            this.processArtifacts(subProcess.getArtifacts(), model, elementArray, flowArray, diagramInfo);
                        }
                    }
                }
            } while(!CollectionUtils.isNotEmpty(flowInfo));

            ArrayNode waypointArray = this.objectMapper.createArrayNode();
            Iterator var12 = flowInfo.iterator();

            while(var12.hasNext()) {
                GraphicInfo graphicInfo = (GraphicInfo)var12.next();
                ObjectNode pointNode = this.objectMapper.createObjectNode();
                this.fillGraphicInfo(pointNode, graphicInfo, false);
                waypointArray.add(pointNode);
                this.fillDiagramInfo(graphicInfo, diagramInfo);
            }

            elementNode.set("waypoints", waypointArray);
            String className = element.getClass().getSimpleName();
            if (this.propertyMappers.containsKey(className)) {
                elementNode.set("properties", ((InfoMapper)this.propertyMappers.get(className)).map(element));
            }

            flowArray.add(elementNode);
        }
    }

    protected void processArtifacts(Collection<Artifact> artifactList, BpmnModel model, ArrayNode elementArray, ArrayNode flowArray, GraphicInfo diagramInfo) {
        Iterator var6 = artifactList.iterator();

        while(var6.hasNext()) {
            Artifact artifact = (Artifact)var6.next();
            ObjectNode elementNode;
            if (artifact instanceof Association) {
                elementNode = this.objectMapper.createObjectNode();
                Association flow = (Association)artifact;
                elementNode.put("id", flow.getId());
                elementNode.put("type", "association");
                elementNode.put("sourceRef", flow.getSourceRef());
                elementNode.put("targetRef", flow.getTargetRef());
                this.fillWaypoints(flow.getId(), model, elementNode, diagramInfo);
                flowArray.add(elementNode);
            } else {
                elementNode = this.objectMapper.createObjectNode();
                elementNode.put("id", artifact.getId());
                if (artifact instanceof TextAnnotation) {
                    TextAnnotation annotation = (TextAnnotation)artifact;
                    elementNode.put("text", annotation.getText());
                }

                GraphicInfo graphicInfo = model.getGraphicInfo(artifact.getId());
                if (graphicInfo != null) {
                    this.fillGraphicInfo(elementNode, graphicInfo, true);
                    this.fillDiagramInfo(graphicInfo, diagramInfo);
                }

                String className = artifact.getClass().getSimpleName();
                elementNode.put("type", className);
                elementArray.add(elementNode);
            }
        }

    }

    protected void fillWaypoints(String id, BpmnModel model, ObjectNode elementNode, GraphicInfo diagramInfo) {
        List<GraphicInfo> flowInfo = model.getFlowLocationGraphicInfo(id);
        ArrayNode waypointArray = this.objectMapper.createArrayNode();
        Iterator var7 = flowInfo.iterator();

        while(var7.hasNext()) {
            GraphicInfo graphicInfo = (GraphicInfo)var7.next();
            ObjectNode pointNode = this.objectMapper.createObjectNode();
            this.fillGraphicInfo(pointNode, graphicInfo, false);
            waypointArray.add(pointNode);
            this.fillDiagramInfo(graphicInfo, diagramInfo);
        }

        elementNode.set("waypoints", waypointArray);
    }

    protected void fillEventTypes(String className, FlowElement element, ObjectNode elementNode) {
        if (this.eventElementTypes.contains(className)) {
            Event event = (Event)element;
            ObjectNode eventNode;
            if (CollectionUtils.isNotEmpty(event.getEventDefinitions())) {
                EventDefinition eventDef = (EventDefinition)event.getEventDefinitions().get(0);
                eventNode = this.objectMapper.createObjectNode();
                if (eventDef instanceof TimerEventDefinition) {
                    TimerEventDefinition timerDef = (TimerEventDefinition)eventDef;
                    eventNode.put("type", "timer");
                    if (StringUtils.isNotEmpty(timerDef.getTimeCycle())) {
                        eventNode.put("timeCycle", timerDef.getTimeCycle());
                    }

                    if (StringUtils.isNotEmpty(timerDef.getTimeDate())) {
                        eventNode.put("timeDate", timerDef.getTimeDate());
                    }

                    if (StringUtils.isNotEmpty(timerDef.getTimeDuration())) {
                        eventNode.put("timeDuration", timerDef.getTimeDuration());
                    }
                } else if (eventDef instanceof ConditionalEventDefinition) {
                    ConditionalEventDefinition conditionalDef = (ConditionalEventDefinition)eventDef;
                    eventNode.put("type", "conditional");
                    if (StringUtils.isNotEmpty(conditionalDef.getConditionExpression())) {
                        eventNode.put("condition", conditionalDef.getConditionExpression());
                    }
                } else if (eventDef instanceof ErrorEventDefinition) {
                    ErrorEventDefinition errorDef = (ErrorEventDefinition)eventDef;
                    eventNode.put("type", "error");
                    if (StringUtils.isNotEmpty(errorDef.getErrorCode())) {
                        eventNode.put("errorCode", errorDef.getErrorCode());
                    }
                } else if (eventDef instanceof EscalationEventDefinition) {
                    EscalationEventDefinition escalationDef = (EscalationEventDefinition)eventDef;
                    eventNode.put("type", "escalation");
                    if (StringUtils.isNotEmpty(escalationDef.getEscalationCode())) {
                        eventNode.put("escalationCode", escalationDef.getEscalationCode());
                    }
                } else if (eventDef instanceof SignalEventDefinition) {
                    SignalEventDefinition signalDef = (SignalEventDefinition)eventDef;
                    eventNode.put("type", "signal");
                    if (StringUtils.isNotEmpty(signalDef.getSignalRef())) {
                        eventNode.put("signalRef", signalDef.getSignalRef());
                    }
                } else if (eventDef instanceof MessageEventDefinition) {
                    MessageEventDefinition messageDef = (MessageEventDefinition)eventDef;
                    eventNode.put("type", "message");
                    if (StringUtils.isNotEmpty(messageDef.getMessageRef())) {
                        eventNode.put("messageRef", messageDef.getMessageRef());
                    }
                }

                elementNode.set("eventDefinition", eventNode);
            } else if (event.getExtensionElements().get("eventType") != null) {
                List<ExtensionElement> eventTypeElements = (List)event.getExtensionElements().get("eventType");
                if (eventTypeElements.size() > 0) {
                    eventNode = this.objectMapper.createObjectNode();
                    eventNode.put("type", "eventRegistry");
                    eventNode.put("eventKey", ((ExtensionElement)eventTypeElements.get(0)).getElementText());
                    elementNode.set("eventDefinition", eventNode);
                }
            }
        }

    }

    protected void fillGraphicInfo(ObjectNode elementNode, GraphicInfo graphicInfo, boolean includeWidthAndHeight) {
        this.commonFillGraphicInfo(elementNode, graphicInfo.getX(), graphicInfo.getY(), graphicInfo.getWidth(), graphicInfo.getHeight(), includeWidthAndHeight);
    }

    protected void commonFillGraphicInfo(ObjectNode elementNode, double x, double y, double width, double height, boolean includeWidthAndHeight) {
        elementNode.put("x", x);
        elementNode.put("y", y);
        if (includeWidthAndHeight) {
            elementNode.put("width", width);
            elementNode.put("height", height);
        }

    }

    protected void fillDiagramInfo(GraphicInfo graphicInfo, GraphicInfo diagramInfo) {
        double rightX = graphicInfo.getX() + graphicInfo.getWidth();
        double bottomY = graphicInfo.getY() + graphicInfo.getHeight();
        double middleX = graphicInfo.getX() + graphicInfo.getWidth() / 2.0D;
        if (middleX < diagramInfo.getX()) {
            diagramInfo.setX(middleX);
        }

        if (graphicInfo.getY() < diagramInfo.getY()) {
            diagramInfo.setY(graphicInfo.getY());
        }

        if (rightX > diagramInfo.getWidth()) {
            diagramInfo.setWidth(rightX);
        }

        if (bottomY > diagramInfo.getHeight()) {
            diagramInfo.setHeight(bottomY);
        }

    }
}
