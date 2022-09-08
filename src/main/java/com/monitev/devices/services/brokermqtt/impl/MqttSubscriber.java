package com.monitev.devices.services.brokermqtt.impl;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.collections4.map.HashedMap;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Service;

import com.monitev.devices.services.brokermqtt.IBrokerMqtt;

@Service
public class MqttSubscriber implements MqttCallback,IBrokerMqtt{
	 /** The broker url. */
	private static final Logger LOGGER = Logger.getLogger(MqttSubscriber.class.getName());
    private static final String brokerUrl ="tcp://iotmonitev.com:1883";
    /** The client id. */
    private static final String clientId = "test1";
    /** The topic. */
    private static final String topic = "v1/devices/me/telemetry/+";
    
    public MqttSubscriber() {
		super();
	}
	@SuppressWarnings("resource")
	public void subscribe() {
        //    logger file name and pattern to log
        MemoryPersistence persistence = new MemoryPersistence();
        try
        {
        	 MqttCallback callback = new MqttCallback() {

                 @Override
                 public void messageArrived(String topic, MqttMessage message) {
                     LOGGER.severe("MESSAGE FROM " + topic + ": '" + new String(message.getPayload(), StandardCharsets.UTF_8) + "' qos: " + message.getQos());
                 }

                 @Override
                 public void deliveryComplete(IMqttDeliveryToken token) {
                 }

                 @Override
                 public void connectionLost(Throwable cause) {                 
                     System.exit(0);
                 }
             };
            MqttClient sampleClient = new MqttClient(brokerUrl, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("checking");
            System.out.println("Mqtt Connecting to broker: " + brokerUrl);
			connOpts.setUserName("EOHyXtM1YB6BkRsYmxTU");
            sampleClient.connect(connOpts);
            System.out.println("Mqtt Connected");
            sampleClient.setCallback(callback);
            sampleClient.subscribe(topic);
            System.out.println("Subscribed");
            System.out.println("Listening");
        } catch (MqttException me) {
            System.out.println("reason " 	+ me.getReasonCode());
            System.out.println("msg " 		+ me.getMessage());
            System.out.println("loc " 		+ me.getLocalizedMessage());
            System.out.println("cause " 	+ me.getCause());
            System.out.println("excep " 	+ me);
            me.printStackTrace();
        }
    }
    //Called when the client lost the connection to the broker
    public void connectionLost(Throwable arg0) {
    }
    //Called when a outgoing publish is complete
    public void deliveryComplete(IMqttDeliveryToken arg0) {
    }
    public void messageArrived(String topic, MqttMessage message) {
        System.out.println("Topic:" + topic);
        System.out.println("Message: " +message.toString());
    }
    
	public Map<String, Object> subscribeTopic() {
		Map<String, Object> response = new HashedMap<String, Object>();
		subscribe();
		return response;
	}
}
