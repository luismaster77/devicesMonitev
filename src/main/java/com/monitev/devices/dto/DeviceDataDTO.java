package com.monitev.devices.dto;

import java.io.Serializable;

public class DeviceDataDTO implements Serializable{
	
	private static final long serialVersionUID = 709455984737299326L;
	
	private String nombreVariable;
	private String valorVariable;
	private long tiempo;
	
	public DeviceDataDTO(String nombreVariable, String valorVariable, long tiempo) {
		this.nombreVariable = nombreVariable;
		this.valorVariable = valorVariable;
		this.tiempo = tiempo;
	}
	public DeviceDataDTO() {
		super();
	}
	public String getNombreVariable() {
		return nombreVariable;
	}
	public void setNombreVariable(String nombreVariable) {
		this.nombreVariable = nombreVariable;
	}
	public String getValorVariable() {
		return valorVariable;
	}
	public void setValorVariable(String valorVariable) {
		this.valorVariable = valorVariable;
	}
	public long getTiempo() {
		return tiempo;
	}
	public void setTiempo(long tiempo) {
		this.tiempo = tiempo;
	}	
}
