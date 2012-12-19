package com.richitec.internationalcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

public class InternationalCodeManager {

	private static final String LOG_TAG = "InternationalCodeManager";

	// international code database query timeout 30 seconds
	private static final Integer TIMEOUT = 30;

	// singleton instance
	private static volatile InternationalCodeManager _singletonInstance;

	// all international code object array
	private final List<InternationalCodeBean> _mAllInternationalCodeArray = new ArrayList<InternationalCodeBean>();

	// all international code set
	private final Set<Integer> _mAllInternationalCodeSet = new TreeSet<Integer>(
			new Comparator<Integer>() {

				@Override
				public int compare(Integer lhs, Integer rhs) {
					// define result value of comparator with left hand side and
					// right hand side
					int _compValue = lhs.compareTo(rhs);
					int _compABSValue = Math.abs(_compValue);

					return 0 == _compValue ? _compValue
							: _compValue == _compABSValue ? -_compABSValue
									: _compABSValue;
				}
			});

	// area abbreviation and international code map, key is area
	// abbreviation(String) and value is international code(Integer)
	private final Map<String, Integer> _mAbbreviationInternationalCodeMap = new HashMap<String, Integer>();

	// private constructor
	private InternationalCodeManager() {
		try {
			// load the sqlite-JDBC driver using the current class loader
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			// load sqlite-JDBC driver error
			e.printStackTrace();

			System.out.println(LOG_TAG
					+ ", load sqlite-JDBC driver error, exception message = "
					+ e.getMessage());
		}
	}

	// get InternationalCodeManager singleton instance
	public static InternationalCodeManager getInstance() {
		if (null == _singletonInstance) {
			synchronized (InternationalCodeManager.class) {
				if (null == _singletonInstance) {
					_singletonInstance = new InternationalCodeManager();
				}
			}
		}

		return _singletonInstance;
	}

	// get all international code object array
	public List<InternationalCodeBean> getAllInternationalCodeArray() {
		// check all international code object array and all international code
		// set
		if ((null == _mAllInternationalCodeArray || 0 == _mAllInternationalCodeArray
				.size())
				|| (null == _mAllInternationalCodeSet || 0 == _mAllInternationalCodeSet
						.size())) {
			// reload and traversal international code database again
			load7TraversalInternationalCodeDB();
		}

		return _mAllInternationalCodeArray;
	}

	// get all international code
	public List<Integer> getAllInternationalCode() {
		// all international code
		List<Integer> _allInternationalCode = new ArrayList<Integer>();

		// check all international code object array and all international code
		// set
		if ((null == _mAllInternationalCodeArray || 0 == _mAllInternationalCodeArray
				.size())
				|| (null == _mAllInternationalCodeSet || 0 == _mAllInternationalCodeSet
						.size())) {
			// reload and traversal international code database again
			load7TraversalInternationalCodeDB();
		}

		// generate all international code
		for (Object internationalCode : _mAllInternationalCodeSet.toArray()) {
			_allInternationalCode.add((Integer) internationalCode);
		}

		return _allInternationalCode;
	}

	// get international code by abbreviation
	public Integer getInternationalCodeByAbbreviation(
			AreaAbbreviation abbreviation) {
		return _mAbbreviationInternationalCodeMap.get(abbreviation.name());
	}

	// get international code list by some abbreviations
	public List<Integer> getInternationalCodeByAbbreviation(
			List<AreaAbbreviation> abbreviations) {
		// define return result
		List<Integer> _ret = new ArrayList<Integer>();

		// add all international codes
		if (abbreviations.isEmpty()) {
			_ret.addAll(getAllInternationalCode());
		} else {
			// add each allowed
			for (AreaAbbreviation areaAbbreviation : abbreviations) {
				// get international code by abbreviation
				_ret.add(getInternationalCodeByAbbreviation(areaAbbreviation));
			}
		}

		return _ret;
	}

	// load and traversal international code database, important do it first
	private void load7TraversalInternationalCodeDB() {
		// international code database path and its table of saving
		// international code and its keys
		final String DATABASE_PATH = "src/internationalcodedb/internationalcode.db";
		final String TABLE_NAME = "internationalcode";
		final String KEY_INDEX = "index";
		final String KEY_CODE = "code";
		final String KEY_AREANAME = "area";
		final String KEY_ABBREVIATION = "abbreviation";

		// international code xml file path and its json keys
		final String XMLFILE_PATH = "/internationalcodedb/internationalcode.xml";
		final String KEY_INTERNATIONALCODES = "internationalCodes";
		final String KEY_INTERNATIONALCODE = "internationalCode";

		// define international code id, code, area name and abbreviation
		Integer _id, _code;
		String _areaName, _abbreviation;

		// define sqlite database connection
		Connection _connection = null;

		try {
			// create a database connection for connecting international
			// database
			_connection = DriverManager.getConnection("jdbc:sqlite:"
					+ DATABASE_PATH);

			// define sql execute statement and set query timeout to 30
			// seconds
			Statement _statement = _connection.createStatement();
			_statement.setQueryTimeout(TIMEOUT);

			// query all data
			ResultSet _rs = _statement.executeQuery("select * from "
					+ TABLE_NAME);

			// read the result set
			while (_rs.next()) {
				// get international code id, code, area name and
				// abbreviation
				_id = _rs.getInt(KEY_INDEX);
				_code = _rs.getInt(KEY_CODE);
				_areaName = _rs.getString(KEY_AREANAME);
				_abbreviation = _rs.getString(KEY_ABBREVIATION);

				// System.out.println("International code id = " + _id
				// + ", code = " + _code + ", area name = " + _areaName
				// + " and abbreviation = " + _abbreviation);

				// add code to all international code set
				_mAllInternationalCodeSet.add(_code);

				// add abbreviation and code to abbreviation international code
				// map
				_mAbbreviationInternationalCodeMap.put(_abbreviation, _code);

				// generate international code bean and add to all international
				// code array
				InternationalCodeBean _internationalCode = new InternationalCodeBean();

				// set attributes
				_internationalCode.setId(_id);
				_internationalCode.setCode(_code);
				_internationalCode.setAreaName(_areaName);
				_internationalCode.setAbbreviation(_abbreviation);

				// add to list
				_mAllInternationalCodeArray.add(_internationalCode);
			}

			// close result set
			_rs.close();
		} catch (SQLException e) {
			// if the error message is "out of memory", it probably means no
			// database file is found
			e.printStackTrace();

			System.out
					.println(LOG_TAG
							+ ", access international code database error, exception message = "
							+ e.getMessage());

			System.out.println(LOG_TAG
					+ ", loading international code xml file");
			// load international code xml file
			try {
				// get international codes json object
				JSONObject _internationalCodesJsonObject = XML
						.toJSONObject(inputStream2String(this.getClass()
								.getResourceAsStream(XMLFILE_PATH)));

				// get all international codes json array
				JSONArray _internationalCodesJsonArray = (JSONArray) ((JSONObject) _internationalCodesJsonObject
						.get(KEY_INTERNATIONALCODES))
						.get(KEY_INTERNATIONALCODE);

				// traversal all international codes json array
				for (int i = 0; i < _internationalCodesJsonArray.length(); i++) {
					// get international code json object
					JSONObject _internationalCodeJsonObject = _internationalCodesJsonArray
							.getJSONObject(i);

					// get international code id, code, area name and
					// abbreviation
					_id = _internationalCodeJsonObject.getInt(KEY_INDEX);
					_code = _internationalCodeJsonObject.getInt(KEY_CODE);
					_areaName = _internationalCodeJsonObject
							.getString(KEY_AREANAME);
					_abbreviation = _internationalCodeJsonObject
							.getString(KEY_ABBREVIATION);

					// System.out.println("International code id = " + _id
					// + ", code = " + _code + ", area name = "
					// + _areaName + " and abbreviation = "
					// + _abbreviation);

					// add code to all international code set
					_mAllInternationalCodeSet.add(_code);

					// add abbreviation and code to abbreviation international
					// code map
					_mAbbreviationInternationalCodeMap
							.put(_abbreviation, _code);

					// generate international code bean and add to all
					// international
					// code array
					InternationalCodeBean _internationalCode = new InternationalCodeBean();

					// set attributes
					_internationalCode.setId(_id);
					_internationalCode.setCode(_code);
					_internationalCode.setAreaName(_areaName);
					_internationalCode.setAbbreviation(_abbreviation);

					// add to list
					_mAllInternationalCodeArray.add(_internationalCode);
				}
			} catch (JSONException ie) {
				// xml file to json object error
				ie.printStackTrace();

				System.out
						.println(LOG_TAG
								+ ", access international code xml file error, exception message = "
								+ ie.getMessage());
			}
		} finally {
			// close sqlite connection
			try {
				if (null != _connection) {
					_connection.close();
				}
			} catch (SQLException e) {
				// sqlite connection close failed
				e.printStackTrace();

				System.out
						.println(LOG_TAG
								+ ", international code sqlite connection close failed, exception message = "
								+ e.getMessage());
			}
		}
	}

	// convert inputStream to string
	private String inputStream2String(InputStream inputStream) {
		// define return result and buffer reader
		StringBuilder _ret = new StringBuilder();
		BufferedReader _bufferReader = new BufferedReader(
				new InputStreamReader(inputStream));

		try {
			// check buffer reader is ready
			while (_bufferReader.ready()) {
				// read line and append to return result
				_ret.append(_bufferReader.readLine());
			}

			// close buffer reader
			_bufferReader.close();
		} catch (IOException e) {
			// check buffer reader ready or read or close error
			e.printStackTrace();

			System.out
					.println(LOG_TAG
							+ ", check buffer reader ready or read or close error, exception = "
							+ e.getMessage());
		}

		return _ret.toString();
	}

}
