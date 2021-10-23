package br.com.myCompany.generator.model;

import java.util.List;

import com.google.gson.annotations.Expose;

public class Board {

	@Expose(serialize=true)
	private int id;
	@Expose(serialize=true)
	private String name;
	@Expose(serialize=true)
	private String ip;
	@Expose(serialize=true)
	private int portBoard;
	@Expose(serialize=true)
	private int portZabbix;
	@Expose(serialize=true)
	private String ipZabbixServer;
	@Expose(serialize=true)
	private List<Generator> generators;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPortBoard() {
		return portBoard;
	}
	public void setPortBoard(int portBoard) {
		this.portBoard = portBoard;
	}
	public int getPortZabbix() {
		return portZabbix;
	}
	public void setPortZabbix(int portZabbix) {
		this.portZabbix = portZabbix;
	}
	public String getIpZabbixServer() {
		return ipZabbixServer;
	}
	public void setIpZabbixServer(String ipZabbixServer) {
		this.ipZabbixServer = ipZabbixServer;
	}
	public List<Generator> getGenerators() {
		return generators;
	}
	public void setGenerators(List<Generator> generators) {
		this.generators = generators;
	}
	
	
}
