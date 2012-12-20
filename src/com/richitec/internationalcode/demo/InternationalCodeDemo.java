package com.richitec.internationalcode.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.richitec.internationalcode.AreaAbbreviation;
import com.richitec.internationalcode.InternationalCodeBean;
import com.richitec.internationalcode.InternationalCodeManager;
import com.richitec.internationalcode.utils.InternationalCodeHelper;

public class InternationalCodeDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// get all international code first
		InternationalCodeHelper.getAllInternationalCodes();

		// test get all international code object array and all international
		// code set time
		Long _startTime = System.currentTimeMillis();
		List<Integer> _allInternationalCodes = InternationalCodeHelper
				.getAllInternationalCodes();
		System.out.println("Get all international codes duration = "
				+ (System.currentTimeMillis() - _startTime));
		_startTime = System.currentTimeMillis();
		List<String> _allInternationalCodesWithInternationalPrefix = InternationalCodeHelper
				.getAllInternationalCodesWithInternationalPrefix();
		System.out
				.println("Get all international codes with international prefix duration = "
						+ (System.currentTimeMillis() - _startTime));

		// test all international code object array and all international code
		// set size
		System.out.println("All international codes count = "
				+ _allInternationalCodes.size()
				+ " and with international prefix count = "
				+ _allInternationalCodesWithInternationalPrefix.size());

		// print all international code object array and all international code
		// set
		for (Integer integer : _allInternationalCodes) {
			System.out.println("International code = " + integer);
		}
		for (String string : _allInternationalCodesWithInternationalPrefix) {
			System.out
					.println("International code with international prefix = "
							+ string);
		}

		// get international prefix
		String _exceptionInternationalPrefix = "";
		System.out.println("Get international prefix = "
				+ InternationalCodeHelper
						.getInternationalPrefix(_exceptionInternationalPrefix)
				+ " except for = " + _exceptionInternationalPrefix);

		// get international code by abbreviation
		AreaAbbreviation _abbreviation = AreaAbbreviation.JP;
		List<AreaAbbreviation> _abbreviations = new ArrayList<AreaAbbreviation>();
		_abbreviations.add(AreaAbbreviation.CN);
		_abbreviations.add(AreaAbbreviation.AO);
		System.out.println("Get international code of = "
				+ _abbreviation
				+ " is "
				+ InternationalCodeHelper
						.getInternationalCodeByAbbreviation(_abbreviation));
		System.out.println("Get international codes of = "
				+ _abbreviations
				+ " are "
				+ InternationalCodeHelper
						.getInternationalCodeByAbbreviation(_abbreviations));

		// get international code with international prefix by abbreviation
		System.out
				.println("Get international code with internation prefix of = "
						+ _abbreviation
						+ " is "
						+ InternationalCodeHelper
								.getInternationalCodeWithInternationalPrefixByAbbreviation(_abbreviation));
		System.out
				.println("Get international codes with internation prefix of = "
						+ _abbreviations
						+ " are "
						+ InternationalCodeHelper
								.getInternationalCodeWithInternationalPrefixByAbbreviation(_abbreviations));

		// convert sqlite database to xml
		// new international code xml file and file write
		File _internationalCodeXmlFile = new File(
				"src/internationalcodedb/internationalcode.xml");
		FileOutputStream _internationalCodeXmlFileOutputStream;

		try {
			// check international code xml file
			if (!_internationalCodeXmlFile.exists()) {
				_internationalCodeXmlFile.createNewFile();
			} else {
				// clear international code xml file
				return;
			}

			// init international code xml file output stream
			_internationalCodeXmlFileOutputStream = new FileOutputStream(
					_internationalCodeXmlFile);

			// add start root element
			_internationalCodeXmlFileOutputStream
					.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
							.getBytes());
			_internationalCodeXmlFileOutputStream
					.write("<internationalCodes>\n".getBytes());

			// add each country element
			List<InternationalCodeBean> __allInternationalCodeArray = InternationalCodeManager
					.getInstance().getAllInternationalCodeArray();
			for (InternationalCodeBean internationalCodeBean : __allInternationalCodeArray) {
				// add start country element
				_internationalCodeXmlFileOutputStream
						.write("\t<internationalCode>\n".getBytes());

				// add country attributes
				_internationalCodeXmlFileOutputStream.write("\t\t<index>"
						.getBytes());
				_internationalCodeXmlFileOutputStream
						.write(internationalCodeBean.getId().toString()
								.getBytes());
				_internationalCodeXmlFileOutputStream.write("</index>\n"
						.getBytes());

				_internationalCodeXmlFileOutputStream.write("\t\t<code>"
						.getBytes());
				_internationalCodeXmlFileOutputStream
						.write(internationalCodeBean.getCode().toString()
								.getBytes());
				_internationalCodeXmlFileOutputStream.write("</code>\n"
						.getBytes());

				_internationalCodeXmlFileOutputStream.write("\t\t<area>"
						.getBytes());
				_internationalCodeXmlFileOutputStream
						.write(internationalCodeBean.getAreaName().getBytes());
				_internationalCodeXmlFileOutputStream.write("</area>\n"
						.getBytes());

				_internationalCodeXmlFileOutputStream
						.write("\t\t<abbreviation>".getBytes());
				_internationalCodeXmlFileOutputStream
						.write(internationalCodeBean.getAbbreviation()
								.getBytes());
				_internationalCodeXmlFileOutputStream.write("</abbreviation>\n"
						.getBytes());

				// add end country element
				_internationalCodeXmlFileOutputStream
						.write("\t</internationalCode>\n".getBytes());
			}

			// add end root element
			_internationalCodeXmlFileOutputStream.write("</internationalCodes>"
					.getBytes());

			// close international code xml file outputStream
			_internationalCodeXmlFileOutputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
