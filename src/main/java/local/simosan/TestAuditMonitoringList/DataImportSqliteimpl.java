package local.simosan.TestAuditMonitoringList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * PostgeSQLからデータをインポートし、連想配列でデータを返却する。
 *
 */

public class DataImportSqliteimpl implements IDataImport {

	public ArrayList<HashMap<String, String>> dataRead() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		ResultSetMetaData rsmd = null;
		String url = System.getenv("SQLITECONURL");
		ArrayList<HashMap<String, String>> list = null;

		// 環境変数空白チェック
		if (isAllNull(url)) {
			System.out.println("DB接続のための環境変数が空です");
			return list;
		}

		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection(url);
			// SELECT文の実行
			stmt = conn.createStatement();
			String sql = "select date,host,id,type,sessionid from t_sim_logonlogoff order by date";
			// データ取得
			rset = stmt.executeQuery(sql);
			// フィールド名取得
			rsmd = rset.getMetaData();
			// データ格納用連想配列
			list = new ArrayList<HashMap<String, String>>();
			// フィールド名の総数
			int rscnt = rsmd.getColumnCount();
			// SELECT結果
			while (rset.next()) {
				// 1件分のデータ(連想配列)
				HashMap<String, String> hdata = new HashMap<String, String>();
				for (int i = 1; i <= rscnt; i++) {
					// フィールド名
					String field = rsmd.getColumnName(i);
					// フィールド名に対するデータ
					String getdata = rset.getString(field);
					if (getdata == null) {
						getdata = "";
					}
					// データ格納(フィールド名, データ)
					hdata.put(field, getdata);
				}
				// 1件分のデータを格納
				list.add(hdata);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rset != null)
					rset.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return list;
			}
		}

		return list;
	}

	// パラメータが１つでも空だったらtrue。すべて値が入っていればfalse
	private boolean isAllNull(String... params) {
		for (String param : params) {
			// System.out.println(param);
			if (param.isEmpty()) {
				return true;
			}
		}
		return false;
	}

}
