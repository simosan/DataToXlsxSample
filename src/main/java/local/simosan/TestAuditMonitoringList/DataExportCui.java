package local.simosan.TestAuditMonitoringList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * 読み込んだデータをCUIにレコード形式で出力。以下の様に出力。
 * =================================
 * date,2022-01-21 21:41:24
 * type,LOGON
 * host,sim8
 * sessionid,344264
 * id,sim
 * =================================
 *     ~
 */
public class DataExportCui implements IDataExport {

	@Override
	public void dataExport(ArrayList<HashMap<String, String>> d) {
		for(HashMap<String,String> ss : d) {
			System.out.println("=================================");
			for(Entry<String,String> ent : ss.entrySet()) {
				System.out.println(ent.getKey() + "," + ent.getValue());
			}
		}
		
	}

}
