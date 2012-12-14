package com.richitec.internationalcode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InternationalCodeManager {

	private static final String LOG_TAG = "InternationalCodeManager";

	// international code database path
	private static final String DATABASE_PATH = "src/internationalcodedb/internationalcode.db";

	// international code database query timeout 30 seconds
	private static final Integer TIMEOUT = 30;

	// singleton instance
	private static volatile InternationalCodeManager _singletonInstance;

	// all international code object array
	private final List<InternationalCodeBean> _mAllInternationalCodeArray = new ArrayList<InternationalCodeBean>();

	// all international code set
	private final Set<Integer> _mAllInternationalCodeSet = new HashSet<Integer>();

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
	public Object[] getAllInternationalCode() {
		// check all international code object array and all international code
		// set
		if ((null == _mAllInternationalCodeArray || 0 == _mAllInternationalCodeArray
				.size())
				|| (null == _mAllInternationalCodeSet || 0 == _mAllInternationalCodeSet
						.size())) {
			// reload and traversal international code database again
			load7TraversalInternationalCodeDB();
		}

		return _mAllInternationalCodeSet.toArray();
	}

	// load and traversal international code database, important do it first
	private void load7TraversalInternationalCodeDB() {
		// international code database name of table for saving international
		// code and its keys
		final String TABLE_NAME = "internationalcode";
		final String KEY_INDEX = "index";
		final String KEY_CODE = "code";
		final String KEY_COUNTRY = "country";
		final String KEY_ABBREVIATION = "abbreviation";

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
				// get international code id, code, country name and
				// abbreviation
				Integer _id = _rs.getInt(KEY_INDEX);
				Integer _code = _rs.getInt(KEY_CODE);
				String _countryName = _rs.getString(KEY_COUNTRY);
				String _abbreviation = _rs.getString(KEY_ABBREVIATION);

				// System.out
				// .println("International code id = " + _id + ", code = "
				// + _code + ", country name = " + _countryName
				// + " and abbreviation = " + _abbreviation);

				// add code to all international code set
				_mAllInternationalCodeSet.add(_code);

				// generate international code bean and add to all international
				// code array
				InternationalCodeBean _internationalCode = new InternationalCodeBean();

				// set attributes
				_internationalCode.setId(_id);
				_internationalCode.setCode(_code);
				_internationalCode.setCountryName(_countryName);
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

}