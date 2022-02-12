package local.simosan.TestAuditMonitoringList;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 連想配列を永続化層に出力する
 *
 */
public interface IDataExport {
	public void dataExport(ArrayList<HashMap<String, String>> d);
}
