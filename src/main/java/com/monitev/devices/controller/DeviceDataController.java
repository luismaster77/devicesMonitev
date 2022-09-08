package com.monitev.devices.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.monitev.devices.constantes.Constantes;
import com.monitev.devices.exception.ExceptionResponse;
import com.monitev.devices.services.brokermqtt.IBrokerMqtt;
import com.monitev.devices.services.devicesData.IDeviceData;

@RestController
@RequestMapping(value = { Constantes.URL_BASE })
public class DeviceDataController {
	
	@Autowired
	private IDeviceData iDeviceData;
	
	@Autowired
	private IBrokerMqtt iBrokerMqtt;

	@GetMapping(value = Constantes.URL_GENERAR_BACKUP)
	public ResponseEntity<Object> generarBackupDevice(@RequestParam String deviceId) throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
			response = iDeviceData.generarBackup(deviceId);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		} catch (RuntimeException exceptionResponse) {
			exceptionResponse = new ExceptionResponse(new Date(), !response.isEmpty()?response.get("resultado").toString():""+Constantes.MNS_ERROR_BACKUP,null);
			return new ResponseEntity<Object>(exceptionResponse.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = Constantes.URL_SUBSCRUBE_TOPIC)
	public ResponseEntity<Object> subscribeTopic() throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
			response = iBrokerMqtt.subscribeTopic();
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		} catch (RuntimeException exceptionResponse) {
			exceptionResponse = new ExceptionResponse(new Date(), !response.isEmpty()?response.get("resultado").toString():""+Constantes.MNS_ERROR_BACKUP,null);
			return new ResponseEntity<Object>(exceptionResponse.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
