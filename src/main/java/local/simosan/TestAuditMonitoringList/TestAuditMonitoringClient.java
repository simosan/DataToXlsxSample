package local.simosan.TestAuditMonitoringList;

/**
 * Main
 *
 */
public class TestAuditMonitoringClient {
	public static void main(String[] args) {
		// データ取得オブジェクト生成
		IDataImport di = new DataImportPgimpl(); //データをPostgreSQLから取得し連想配列に格納
		// IDataImport di = new DataImportSqliteimpl(); // データをPostgreSQLから取得し連想配列に格納
		// IDataImport di = new DataImportCsvimpl("/path_to/logonlogoff.csv");
		// //データをCSVから取得し連想配列に格納

		// 永続化オブジェクト生成
		// IDataExport de = new DataExportCui(); // データをCUIでレコード形式で表示
		IDataExport de = new DataExportXlsx(); //データをExcelに出力

		// サービスオブジェクト生成（引数にオブジェクトをDI）
		AuditService as = new AuditService(di, de);
		boolean rtn = as.auditMonitring();
		if (rtn == false) {
			System.out.println("AuditService.auditMonitringでエラー発生!");
			System.exit(1);
		}

	}
}
