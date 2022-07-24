package br.com.cursojsf;

import javax.faces.bean.ManagedBean;

@ManagedBean(name = "pessoaBean")
public class PessoaBean {
	private String name;
	private String sunName;

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
}
