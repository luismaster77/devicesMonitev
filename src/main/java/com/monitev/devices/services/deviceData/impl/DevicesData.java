package com.monitev.devices.services.deviceData.impl;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.thingsboard.rest.client.RestClient;
import org.thingsboard.server.common.data.Device;
import org.thingsboard.server.common.data.EntityType;
import org.thingsboard.server.common.data.id.CustomerId;
import org.thingsboard.server.common.data.id.EntityId;
import org.thingsboard.server.common.data.kv.TsKvEntry;
import org.thingsboard.server.common.data.page.PageData;
import org.thingsboard.server.common.data.page.PageLink;

import com.monitev.devices.constantes.Constantes;
import com.monitev.devices.services.devicesData.IDeviceData;

@Service
@PropertySource(Constantes.PATH)
public class DevicesData implements IDeviceData{

	@Autowired
	private Environment env;
	
	@SuppressWarnings({ "resource", "serial" })
	public Map<String, Object> generarBackup(String deviceId) throws Exception {
		Map<String, Object> response = new HashMap<>();	
		
		if(deviceId.isEmpty() || deviceId == null) {
			throw new Exception(Constantes.MNS_CAMPO_REQUERIDO);
		}
		
		String url = env.getProperty(Constantes.VAR_URL_BASE);
		String username = env.getProperty(Constantes.VAR_USERNAME);
		String password = env.getProperty(Constantes.VAR_PASSWORD);
		RestClient client = new RestClient(url);
		String payload = "";
		try {
			client.login(username, password);			
			String[] chunks = client.getToken().split("\\.");
			Base64.Decoder decoder = Base64.getUrlDecoder();
			payload = new String(decoder.decode(chunks[1]));
		} catch (Exception e) {
			response.put("respuesta", e);
			throw new Exception(Constantes.MNS_ERROR_INICIO_SESION);
		}
		JSONObject payloadJson = new JSONObject(payload);
		
		CustomerId customerId = new CustomerId(UUID.fromString(payloadJson.getString(Constantes.CUSTOMER_ID)));
		PageData<Device> tenantDevices;
		List<TsKvEntry> dataDevice;
		Device device = null;
		PageLink pageLink = new PageLink(10);
		do {	
		    EntityId entityId = new EntityId() {
				
				@Override
				public UUID getId() {
					return UUID.fromString(deviceId);
				}
				@Override
				public EntityType getEntityType() {
					return EntityType.DEVICE;
				}
			};
			
			 tenantDevices = client.getCustomerDevices(customerId,null, pageLink);
			 if(!tenantDevices.getData().isEmpty()) {
				for (Device dataDevices : tenantDevices.getData()) {
					if(UUID.fromString(dataDevices.getId().toString()).equals(UUID.fromString(deviceId))) {
						device = dataDevices;
						}
					}
				}
		     dataDevice = client.getLatestTimeseries(entityId,client.getTimeseriesKeys(entityId));	
		     pageLink = pageLink.nextPageLink();
		} while (tenantDevices.hasNext());
		client.logout();
		client.close();
		Map<String, Object> dev = new HashMap<>();
		dev.put("name", device.getName());
		dev.put("type", device.getType());
		response.put("telemetryDevice", dataDevice);
		response.put("device", dev);
		
		return response;
	}
	
//	@SuppressWarnings({ "resource", "rawtypes" })
//	private String generarExcel2(List<TsKvEntry> datos) throws Exception {
//	    XSSFWorkbook workbook = new XSSFWorkbook();
//		  	
//        // spreadsheet object
//        XSSFSheet spreadsheet = workbook.createSheet("Device Data");
//  
//        // creating a row object
//        XSSFRow row;
//      
//        Row header = spreadsheet.createRow(0);
//        header.createCell(0).setCellValue("NOMBRE VARIALBE");
//        header.createCell(1).setCellValue("VALOR VARIABLE");
//        header.createCell(2).setCellValue("TIEMPO");
// 
//      
//         //This data needs to be written (Object[])
//        int count = 1;
//        Map<String, Object[]> deviceData = new TreeMap<String, Object[]>();
//        int rowid = 1;	  
//        for (Iterator iterator = datos.iterator(); iterator.hasNext();) {
//			TsKvEntry tsKvEntry = (TsKvEntry) iterator.next();	
//			Date currentDate = new Date (tsKvEntry.getTs());
//			SimpleDateFormat dateFormat = new SimpleDateFormat(Constantes.FORMAT_DATE);
//			String date = dateFormat.format(currentDate);
//			deviceData.put(String.valueOf(count),new Object[] { tsKvEntry.getKey(), tsKvEntry.getValueAsString(),date });
//			count= count+1;
//			}
//		   
//        Set<String> keyid = deviceData.keySet();	  
//		for (String key : keyid) {	  
//			row = spreadsheet.createRow(rowid++);
//			Object[] objectArr = deviceData.get(key);
//			
//			int cellid = 0;
//			
//			for (Object obj : objectArr) {
//				Cell cell = row.createCell(cellid++);
//				cell.setCellValue(obj.toString());
//			}
//		}
//		
//		String nameFile = Constantes.NOM_ARCHIVO;
//		String user = System.getProperty(Constantes.USERNAME_PC);
//		java.io.File dir = new File("C:/Users/"+user+"/Documents/backup");
//		dir.mkdir();
//        FileOutputStream outs = new FileOutputStream(new File(dir+nameFile));
//        workbook.write(outs);  
//        outs.close();
//        return Constantes.MNS_SUCCESS+dir.getAbsolutePath();
//	}

}
