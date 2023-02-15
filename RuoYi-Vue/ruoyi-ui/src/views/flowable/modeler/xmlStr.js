export var xmlStr = `<?xml version="1.0" encoding="UTF-8"?>
<definitions xmlns="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:flowable="http://flowable.org/bpmn" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" xmlns:omgdc="http://www.omg.org/spec/DD/20100524/DC" xmlns:omgdi="http://www.omg.org/spec/DD/20100524/DI" xmlns:xsd="http://www.w3.org/2001/XMLSchema" targetNamespace="http://www.flowable.org/processdef">
  <process id="leaveTest" name="请假流程" isExecutable="true" flowable:formKey="1">
    <documentation>请假流程测试</documentation>
    <startEvent id="Event_0y0jull" name="开始">
      <outgoing>Flow_09cpbag</outgoing>
    </startEvent>
    <userTask id="Activity_1w6a7ng" name="经理审批">
      <extensionElements>
        <flowable:formProperty id="datetime" name="请假时间" readable="true" writable="false" />
        <flowable:formProperty id="days" name="请假天数" readable="true" writable="false" />
        <flowable:formProperty id="reason" name="请假理由" readable="false" writable="false" />
        <flowable:button value="1" label="签收" />
        <flowable:button value="2" label="通过" />
        <flowable:button value="5" label="转办" />
        <flowable:button value="6" label="通过" />
      </extensionElements>
      <incoming>Flow_0gdqm3b</incoming>
      <outgoing>Flow_12h5vbu</outgoing>
    </userTask>
    <userTask id="Activity_0gzc1m9" name="财务审批">
      <extensionElements>
        <flowable:formProperty id="datetime" name="请假时间" readable="false" writable="false" />
        <flowable:formProperty id="days" name="请假天数" readable="false" writable="false" />
        <flowable:formProperty id="reason" name="请假理由" readable="false" writable="false" />
      </extensionElements>
      <incoming>Flow_0imvqhr</incoming>
      <outgoing>Flow_0pfu2dh</outgoing>
    </userTask>
    <endEvent id="Event_0oku47u" name="结束">
      <incoming>Flow_0pfu2dh</incoming>
    </endEvent>
    <sequenceFlow id="Flow_0pfu2dh" sourceRef="Activity_0gzc1m9" targetRef="Event_0oku47u" />
    <parallelGateway id="Gateway_1xp5pye">
      <incoming>Flow_0qk7j09</incoming>
      <outgoing>Flow_0gdqm3b</outgoing>
      <outgoing>Flow_0x03veq</outgoing>
    </parallelGateway>
    <sequenceFlow id="Flow_0gdqm3b" sourceRef="Gateway_1xp5pye" targetRef="Activity_1w6a7ng" />
    <userTask id="Activity_05frkae" name="副经理审批">
      <extensionElements>
        <flowable:formProperty id="datetime" name="请假时间" readable="false" writable="false" />
        <flowable:formProperty id="days" name="请假天数" readable="false" writable="false" />
        <flowable:formProperty id="reason" name="请假理由" readable="false" writable="false" />
      </extensionElements>
      <incoming>Flow_0x03veq</incoming>
      <outgoing>Flow_14zarpw</outgoing>
    </userTask>
    <sequenceFlow id="Flow_0x03veq" sourceRef="Gateway_1xp5pye" targetRef="Activity_05frkae" />
    <parallelGateway id="Gateway_0bthikt">
      <incoming>Flow_14zarpw</incoming>
      <incoming>Flow_12h5vbu</incoming>
      <outgoing>Flow_0imvqhr</outgoing>
    </parallelGateway>
    <sequenceFlow id="Flow_14zarpw" sourceRef="Activity_05frkae" targetRef="Gateway_0bthikt" />
    <sequenceFlow id="Flow_0imvqhr" sourceRef="Gateway_0bthikt" targetRef="Activity_0gzc1m9" />
    <sequenceFlow id="Flow_12h5vbu" sourceRef="Activity_1w6a7ng" targetRef="Gateway_0bthikt" />
    <userTask id="Activity_17aaev3" name="组长审批">
      <extensionElements>
        <flowable:formProperty id="datetime" name="请假时间" readable="false" writable="false" />
        <flowable:formProperty id="days" name="请假天数" readable="false" writable="false" />
        <flowable:formProperty id="reason" name="请假理由" readable="false" writable="false" />
      </extensionElements>
      <incoming>Flow_09cpbag</incoming>
      <outgoing>Flow_0qk7j09</outgoing>
    </userTask>
    <sequenceFlow id="Flow_09cpbag" sourceRef="Event_0y0jull" targetRef="Activity_17aaev3" />
    <sequenceFlow id="Flow_0qk7j09" sourceRef="Activity_17aaev3" targetRef="Gateway_1xp5pye" />
  </process>
  <bpmndi:BPMNDiagram id="BPMNDiagram_parallel">
    <bpmndi:BPMNPlane id="BPMNPlane_parallel" bpmnElement="leaveTest">
      <bpmndi:BPMNEdge id="Flow_12h5vbu_di" bpmnElement="Flow_12h5vbu">
        <omgdi:waypoint x="190" y="10" />
        <omgdi:waypoint x="190" y="210" />
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge id="Flow_0imvqhr_di" bpmnElement="Flow_0imvqhr">
        <omgdi:waypoint x="210" y="230" />
        <omgdi:waypoint x="310" y="230" />
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge id="Flow_14zarpw_di" bpmnElement="Flow_14zarpw">
        <omgdi:waypoint x="115" y="230" />
        <omgdi:waypoint x="170" y="230" />
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge id="Flow_0x03veq_di" bpmnElement="Flow_0x03veq">
        <omgdi:waypoint x="65" y="120" />
        <omgdi:waypoint x="65" y="190" />
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge id="Flow_0gdqm3b_di" bpmnElement="Flow_0gdqm3b">
        <omgdi:waypoint x="65" y="80" />
        <omgdi:waypoint x="65" y="-30" />
        <omgdi:waypoint x="140" y="-30" />
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge id="Flow_0pfu2dh_di" bpmnElement="Flow_0pfu2dh">
        <omgdi:waypoint x="410" y="230" />
        <omgdi:waypoint x="500" y="230" />
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge id="Flow_09cpbag_di" bpmnElement="Flow_09cpbag">
        <omgdi:waypoint x="-160" y="100" />
        <omgdi:waypoint x="-110" y="100" />
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge id="Flow_0qk7j09_di" bpmnElement="Flow_0qk7j09">
        <omgdi:waypoint x="-10" y="100" />
        <omgdi:waypoint x="45" y="100" />
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNShape id="Activity_1w6a7ng_di" bpmnElement="Activity_1w6a7ng">
        <omgdc:Bounds x="140" y="-70" width="100" height="80" />
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="Activity_0gzc1m9_di" bpmnElement="Activity_0gzc1m9">
        <omgdc:Bounds x="310" y="190" width="100" height="80" />
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="Event_0oku47u_di" bpmnElement="Event_0oku47u">
        <omgdc:Bounds x="500" y="208" width="40" height="40" />
        <bpmndi:BPMNLabel>
          <omgdc:Bounds x="509" y="255" width="23" height="14" />
        </bpmndi:BPMNLabel>
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="Gateway_1xp5pye_di" bpmnElement="Gateway_1xp5pye">
        <omgdc:Bounds x="45" y="80" width="40" height="40" />
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="Activity_05frkae_di" bpmnElement="Activity_05frkae">
        <omgdc:Bounds x="15" y="190" width="100" height="80" />
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="Gateway_0bthikt_di" bpmnElement="Gateway_0bthikt">
        <omgdc:Bounds x="170" y="210" width="40" height="40" />
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="Activity_17aaev3_di" bpmnElement="Activity_17aaev3">
        <omgdc:Bounds x="-110" y="60" width="100" height="80" />
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="Event_0y0jull_di" bpmnElement="Event_0y0jull">
        <omgdc:Bounds x="-200" y="80" width="40" height="40" />
        <bpmndi:BPMNLabel>
          <omgdc:Bounds x="-191" y="120" width="23" height="14" />
        </bpmndi:BPMNLabel>
      </bpmndi:BPMNShape>
    </bpmndi:BPMNPlane>
  </bpmndi:BPMNDiagram>
</definitions>`
