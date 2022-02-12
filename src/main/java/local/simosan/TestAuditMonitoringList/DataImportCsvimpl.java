package local.simosan.TestAuditMonitoringList;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class DataImportCsvimpl implements IDataImport {

	private String csvpath = null;
	ArrayList<HashMap<String, String>> list = null;

	public DataImportCsvimpl(String csv) {
		// 変換対象のCSVファイルのパス
		this.csvpath = csv;
	}

	@Override
	public ArrayList<HashMap<String, String>> dataRead() {
		// データ格納用連想配列
		list = new ArrayList<HashMap<String, String>>();
		try {
			FileReader fr = new FileReader(this.csvpath);
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(fr);
			String line = null;
			String datestr = null;
			String hoststr = null;
			String idstr = null;
			String typestr = null;
			String sesstr = null;

			int i = 0;
			while ((line = br.readLine()) != null) {
				// 1件分のデータ(連想配列)
				HashMap<String, String> hdata = new HashMap<String, String>();
				// ヘッダ取得
				if (i == 0) {
					String[] header = line.split(",");
					datestr = trimDoubleQuot(header[0]);
					hoststr = trimDoubleQuot(header[1]);
					idstr = trimDoubleQuot(header[2]);
					typestr = trimDoubleQuot(header[3]);
					sesstr = trimDoubleQuot(header[4]);
				} else {
					// データ取得
					String[] dt = line.split(",");
					String datedtstr = trimDoubleQuot(dt[0]);
					String hostdtstr = trimDoubleQuot(dt[1]);
					String iddtstr = trimDoubleQuot(dt[2]);
					String typedtstr = trimDoubleQuot(dt[3]);
					String sesdtstr = trimDoubleQuot(dt[4]);
					// データ格納(フィールド名, データ)
					hdata.put(datestr, datedtstr);
					hdata.put(hoststr, hostdtstr);
					hdata.put(idstr, iddtstr);
					hdata.put(typestr, typedtstr);
					hdata.put(sesstr, sesdtstr);
					// 1件分のデータを格納
					list.add(hdata);
				}
				i++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// 文字列の前後のダブルクォーテーションを削除
	private static String trimDoubleQuot(String str) {
		char c = '"';
		if (str.charAt(0) == c && str.charAt(str.length() - 1) == c) {
			return str.substring(1, str.length() - 1);
		} else {
			return str;
		}
	}

}
