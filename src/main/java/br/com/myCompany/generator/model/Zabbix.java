package br.com.myCompany.generator.model;

import java.util.List;

import com.google.gson.annotations.Expose;

public class Zabbix {

	@Expose(serialize=true)
	private List<Board> boards;
	@Expose(serialize=true)
	private List<ItensBoard> itensBoards;
	@Expose(serialize=true)
	private List<ItensGenerator> itensGenerator;
	
	public List<Board> getBoards() {
		return boards;
	}
	public void setBoards(List<Board> boards) {
		this.boards = boards;
	}
	public List<ItensBoard> getItensBoards() {
		return itensBoards;
	}
	public void setItensBoards(List<ItensBoard> itensBoards) {
		this.itensBoards = itensBoards;
	}
	public List<ItensGenerator> getItensGenerator() {
		return itensGenerator;
	}
	public void setItensGenerator(List<ItensGenerator> itensGenerator) {
		this.itensGenerator = itensGenerator;
	}	
}