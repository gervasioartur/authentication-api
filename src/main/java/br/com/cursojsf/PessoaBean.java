package br.com.cursojsf;

import javax.faces.bean.ManagedBean;

@ManagedBean(name = "pessoaBean")
public class PessoaBean {
	private String name;
	private String sunName;
	private String completeName;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSunName() {
		return sunName;
	}

	public void setSunName(String sunName) {
		this.sunName = sunName;
	}

	public String getCompleteName() {
		this.setCompleteName();
		return completeName;
	}

	public void setCompleteName() {
		this.completeName = this.name + " " + this.sunName;
	}

}
