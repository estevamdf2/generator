package br.com.myCompany.generator.model;

import com.google.gson.annotations.Expose;

public class Generator {
	
	@Expose(serialize=true)
	private int id;
	@Expose(serialize=true)
	private String name;	
	@Expose(serialize=true)
	private String nameHost;
	
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
	public String getNameHost() {
		return nameHost;
	}
	public void setNameHost(String nameHost) {
		this.nameHost = nameHost;
	}

	

}
