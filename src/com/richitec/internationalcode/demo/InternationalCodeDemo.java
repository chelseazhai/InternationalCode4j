package com.richitec.internationalcode.demo;

import java.util.List;

import com.richitec.internationalcode.utils.InternationalCodeHelper;

public class InternationalCodeDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// get all international code first
		InternationalCodeHelper.getAllInternationalCodes();

		// get all international code array and with international prefix array
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
		System.out.println("All international codes count = "
				+ _allInternationalCodes.size()
				+ " and with international prefix count = "
				+ _allInternationalCodesWithInternationalPrefix.size());
		for (Integer integer : _allInternationalCodes) {
			System.out.println("International code = " + integer);
		}
		for (String string : _allInternationalCodesWithInternationalPrefix) {
			System.out
					.println("International code with international prefix = "
							+ string);
		}
	}

}
