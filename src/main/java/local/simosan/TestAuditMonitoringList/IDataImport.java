package local.simosan.TestAuditMonitoringList;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * データソースからデータをインポートし、連想配列でデータを返却する。
 *
 */
public interface IDataImport {
	public ArrayList<HashMap<String, String>> dataRead();
}
