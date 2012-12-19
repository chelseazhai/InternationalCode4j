package com.richitec.internationalcode;

import java.io.Serializable;

public class InternationalCodeBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 452055789260873164L;

	// identifier
	private Integer id;
	// code
	private Integer code;
	// area name
	private String areaName;
	// abbreviation
	private String abbreviation;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	@Override
	public String toString() {
		// init international code description
		StringBuilder _internationalCodeDescription = new StringBuilder();

		// append id, code, area name and abbreviation
		_internationalCodeDescription.append("International code id: ")
				.append(id).append(", ");
		_internationalCodeDescription.append("code: ").append(code)
				.append(", ");
		_internationalCodeDescription.append("area name: ").append(areaName)
				.append(", ");
		_internationalCodeDescription.append("abbreviation: ")
				.append(abbreviation).append("\n");

		return _internationalCodeDescription.toString();
	}

}
