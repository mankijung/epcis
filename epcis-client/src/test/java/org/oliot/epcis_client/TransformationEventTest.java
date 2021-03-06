package org.oliot.epcis_client;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (C) 2014-16 Jaewook Byun
 *
 * This project is part of Oliot (oliot.org), pursuing the implementation of
 * Electronic Product Code Information Service(EPCIS) v1.1 specification in
 * EPCglobal.
 * [http://www.gs1.org/gsmp/kc/epcglobal/epcis/epcis_1_1-standard-20140520.pdf]
 * 
 *
 * @author Jaewook Jack Byun, Ph.D student
 * 
 *         Korea Advanced Institute of Science and Technology (KAIST)
 * 
 *         Real-time Embedded System Laboratory(RESL)
 * 
 *         bjw0829@kaist.ac.kr, bjw0829@gmail.com
 */
public class TransformationEventTest{
	
	@Test
	public void baseTransformationEventCapture() {
		try {
			// Make basic Object Event
			TransformationEvent transformationEvent = new TransformationEvent();
			EPCISClient client = new EPCISClient(new URL("http://localhost:8080/epcis-capture/Service/BsonDocumentCapture"));
			client.addTransformationEvent(transformationEvent);
			client.sendDocument();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void basicTransformationEventCapture() {
		try {
			// Make basic Object Event
			TransformationEvent transformationEvent = new TransformationEvent(System.currentTimeMillis(), "+09:00");
			
			transformationEvent.setTransformationID("transformationID");
			
			List<String> inputEPCList = new ArrayList<String>();
			inputEPCList.add("urn:epc:id:sgtin:0614141.107346.2018");
			transformationEvent.setInputEPCList(inputEPCList);
			
			List<String> outputEPCList = new ArrayList<String>();
			outputEPCList.add("urn:epc:id:sgtin:0614141.107346.2019");
			transformationEvent.setOutputEPCList(outputEPCList);
			
			transformationEvent.setBizStep("urn:epcglobal:cbv:bizstep:receiving");
			
			transformationEvent.setDisposition("urn:epcglobal:cbv:disp:in_progress");
			
			transformationEvent.setReadPoint("urn:epc:id:sgln:0012345.11111.400");
			
			transformationEvent.setBizLocation("urn:epc:id:sgln:0012345.11111.0");
			
			Map<String,List<String>> bizTransactionList = new HashMap<String,List<String>>();
			List<String> bizTransaction1 = new ArrayList<String>();
			bizTransaction1.add("http://transaction.acme.com/po/12345678");
			bizTransactionList.put("urn:epcglobal:cbv:btt:po", bizTransaction1 );
			List<String> bizTransaction2 = new ArrayList<String>();
			bizTransaction2.add("urn:epcglobal:cbv:bt:0614141073467:1152");
			bizTransactionList.put("urn:epcglobal:cbv:btt:desadv", bizTransaction2 );
			transformationEvent.setBizTransactionList(bizTransactionList);
			
			Map<String,String> namespaces = new HashMap<String,String>();
			namespaces.put("example", "http://ns.example.com/epcis");
			transformationEvent.setNamespaces(namespaces);
			
			Map<String, Map<String,Object>> extensionMap = new HashMap<String, Map<String,Object>>();
			Map<String, Object> extension = new HashMap<String, Object>();
			extension.put("temperature", new Integer(36));
			extension.put("emg", new Double(22));
			extension.put("ecg", new Long(11));
			extensionMap.put("example", extension);
			//transformationEvent.setExtensions(extensionMap);
			
			EPCISClient client = new EPCISClient(new URL("http://localhost:8080/epcis/Service/BsonDocumentCapture"));
			client.addTransformationEvent(transformationEvent);
			client.sendDocument();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}
