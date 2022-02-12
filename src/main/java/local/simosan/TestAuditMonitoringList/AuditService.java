package local.simosan.TestAuditMonitoringList;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * データ入力、出力にかかるサービス層
 *
 */
public class AuditService {

	private IDataImport di;
	private IDataExport de;
	ArrayList<HashMap<String, String>> hshd;

	// 依存性注入
	public AuditService(IDataImport imp, IDataExport exp) {
		this.di = imp;
		this.de = exp;
	}

	// 監査モニタリングのために監査データを取得し永続化する。
	public boolean auditMonitring() {
		// データソースから監査データを取得する。
		hshd = di.dataRead();
		if (hshd == null) {
			System.out.println("IDataImport.dataReadでエラー発生!");
			return false;
		}

		// データを出力
		de.dataExport(hshd);

		return true;
	}

}
