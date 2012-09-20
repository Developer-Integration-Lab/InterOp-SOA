package net.aegis.mv.mockdata;

import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

class CSVDataLoader {
	
	<T> List<T> load(Class<T> type, String file) {		
		CSVReader csvReader = null;		
		try {
			//1. Get the URI of the specified file		
			String resourcePath = getClass().getClassLoader().getResource(file).toURI().getPath();		
			
			//2. Create the CVS Reader for the specified file.
			csvReader = new CSVReader(new FileReader(resourcePath));
			
			//3. Create the column-to-object mapping
			Map<String, String> map = createMapping(type);
			
			//4. Create the mapping strategy 
			HeaderColumnNameTranslateMappingStrategy<T> strat = new HeaderColumnNameTranslateMappingStrategy<T>();
			strat.setColumnMapping(map);
			strat.setType(type);
			
			//5. Finally, parse the data into objects
			CsvToBean<T> csvToBean = new CsvToBean<T>();		
			return csvToBean.parse(strat, csvReader);			
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
		finally {		
			if(csvReader != null) { try { csvReader.close(); }catch(Exception ex) {} }
		}
	}

	private <T> Map<String, String> createMapping(Class<T> type) {
		Map<String, String> map = new HashMap<String, String>();
		if(type == TransactionMetaData.class) {
	        map.put("case_id", "caseId");
	        map.put("statuscode", "statusCode");
	        map.put("msgtype", "messageType");
	        map.put("time", "time");
	        map.put("rule", "rule");
	        map.put("method", "method");
	        map.put("path", "path");
	        map.put("client", "client");
	        map.put("server", "server");
	        map.put("sender", "sender");
	        map.put("contenttype", "contentType");
	        map.put("contentlength", "contentLength");
	        map.put("duration", "duration");
	        map.put("msgfilepath", "messageFilePath");
	        map.put("msgheader", "messageHeader");
	        map.put("msg", "message");
	        map.put("senderHCID", "senderHCID");
	        map.put("receiver", "receiver");
	        map.put("is_initiator_scenario", "initiatorScenario");
	        map.put("is_success_scenario", "successScenario");
		}else if(type == QDExpectedMessage.class) {
			map.put("case_id", "caseId");
			map.put("message_id", "messageId");
			map.put("patientId", "patientId");
			map.put("hcid", "hcid");
			map.put("aaid", "aaId");
			map.put("docEntryStatus", "docEntryStatus");
			map.put("returnType", "returnType");
			map.put("serviceStartTimeFrom", "serviceStartTimeFrom");
			map.put("serviceStartTimeTo", "serviceStartTimeTo");
			map.put("serviceStopTimeFrom", "serviceStopTimeFrom");
			map.put("serviceStopTimeTo", "serviceStopTimeTo");
			map.put("msg_type", "messageType");
			map.put("doc_list", "docList");
		}else if(type == RDExpectedMessage.class) {
			map.put("case_id", "caseId");
			map.put("message_id", "messageId");
			map.put("hcid", "hcid");
			map.put("msg_type", "messageType");
			map.put("registry_status", "registryStatus");
			map.put("doc_list", "docList");
		} else if(type == ExpectedDocument.class) {
			map.put("id", "id");
			map.put("document_unique_id", "documentUniqueId");
			map.put("hash", "hash");
			map.put("size", "size");
			map.put("class_code", "classCode");
			map.put("ext_obj_status", "extrinsicObjectStatus");
			map.put("repo_id", "repositoryUniqueId");			
		}
		return map;
	}

}
