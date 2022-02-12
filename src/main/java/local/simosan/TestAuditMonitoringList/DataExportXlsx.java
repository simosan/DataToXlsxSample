package local.simosan.TestAuditMonitoringList;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 * 読み込んだデータをExcelに出力
 *
 */
public class DataExportXlsx implements IDataExport {

	// エクセルファイル名
	private String xlsxfilename = "/tmp/AuditMonitoring.xlsx";
	private SXSSFWorkbook book = null;
	// private XSSFWorkbook book = null;

	@Override
	public void dataExport(ArrayList<HashMap<String, String>> d) {

		try {
			// XSSFだとバカでかいワークブックを作るとOutofMemoryが発生するらしい。
			// メモリ消費量の少ないSXSSFを使っているがXSSFより出来ないことが多いので注意が必要。
			// ワークブックに大量のシートを作ったり、大量のデータを入力しなければXSSFで作っても良いと思う。
			book = new SXSSFWorkbook();
			// book = new XSSFWorkbook();
			// シート作成
			Sheet sheet = book.createSheet("test");
			sheet = book.getSheet("test");

			// 列幅指定
			// 1列目（date)
			sheet.setColumnWidth(0, 25 * 256);
			// ２列目(host)
			sheet.setColumnWidth(1, 15 * 256);
			// 3列目(id)
			sheet.setColumnWidth(2, 15 * 256);
			// 4列目(type)
			sheet.setColumnWidth(3, 15 * 256);
			// 5列目(sessionid)
			sheet.setColumnWidth(4, 35 * 256);

			// タイトル行指定。
			Row titlerow = sheet.createRow(1);
			// タイトルのセル指定
			Cell titlecell = titlerow.createCell(2);
			// フォント作成（タイトル）
			Font titlefont = book.createFont();
			// フォント名の指定
			titlefont.setFontName("Meiryo UI");
			// フォントサイズの指定
			titlefont.setFontHeightInPoints((short) 20);
			// タイトルにアンダーライン
			titlefont.setUnderline(Font.U_SINGLE);
			// スタイルの作成
			CellStyle titlestyle = book.createCellStyle();
			// フォントセット
			titlestyle.setFont(titlefont);
			// 文字揃え
			titlestyle.setAlignment(HorizontalAlignment.CENTER);
			// スタイルをセルに設定
			titlecell.setCellStyle(titlestyle);
			// セルに「表のタイトル」を設定
			titlecell.setCellValue("TESTシステム");

			// ヘッダ行指定
			Row headerrow = sheet.createRow(2);
			// フォント作成（ヘッダ）
			Font headerfont = book.createFont();
			// フォント名の指定
			headerfont.setFontName("Meiryo UI");
			// フォントサイズの指定
			headerfont.setFontHeightInPoints((short) 12);
			// スタイルの作成
			CellStyle headerstyle = book.createCellStyle();
			// フォントセット
			headerstyle.setFont(headerfont);
			// 背景色
			headerstyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.index);
			headerstyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			// 文字揃え
			headerstyle.setAlignment(HorizontalAlignment.LEFT);
			headerstyle.setBorderTop(BorderStyle.THIN);
			headerstyle.setBorderBottom(BorderStyle.THICK);
			headerstyle.setBorderRight(BorderStyle.THIN);
			headerstyle.setBorderLeft(BorderStyle.THIN);
			// 連想配列の要素とDB/CSVのフィールドの列の順番は一致しないことがあるので、
			// まずフィールドを先に読み込んで順番を抑える。
			// 各フィールドに番号を付与し配列に格納。
			// データ読み込み時はその付与した位置(固定されたフィールド位置）に従って出力するセル列を特定する。
			int[] idxnum = new int[5];
			int i = 0;
			// セルに「表のヘッダ」を設定
			for (HashMap<String, String> ss : d) {
				for (Entry<String, String> ent : ss.entrySet()) {
					switch (ent.getKey()) {
					case "date":
						Cell headercelldate = headerrow.createCell(0);
						headercelldate.setCellStyle(headerstyle);
						headercelldate.setCellValue(ent.getKey());
						idxnum[i] = 0;
						break;
					case "host":
						Cell headercellhost = headerrow.createCell(1);
						headercellhost.setCellStyle(headerstyle);
						headercellhost.setCellValue(ent.getKey());
						idxnum[i] = 1;
						break;
					case "id":
						Cell headercellid = headerrow.createCell(2);
						headercellid.setCellStyle(headerstyle);
						headercellid.setCellValue(ent.getKey());
						idxnum[i] = 2;
						break;
					case "type":
						Cell headercelltype = headerrow.createCell(3);
						headercelltype.setCellStyle(headerstyle);
						headercelltype.setCellValue(ent.getKey());
						idxnum[i] = 3;
						break;
					case "sessionid":
						Cell headercellses = headerrow.createCell(4);
						headercellses.setCellStyle(headerstyle);
						headercellses.setCellValue(ent.getKey());
						idxnum[i] = 4;
						break;
					}
					i++;
				}
				break;
			}

			// データ行初期化（4行目）
			int rownum = 3;
			int cnt = 0;
			// データ行指定
			Row datarow = sheet.createRow(rownum);
			Cell datacell = datarow.createCell(cnt);
			// フォント作成（ヘッダ）
			Font datafont = book.createFont();
			// フォント名の指定
			datafont.setFontName("Meiryo UI");
			// フォントサイズの指定
			datafont.setFontHeightInPoints((short) 12);
			// スタイルの作成
			CellStyle datastyle = book.createCellStyle();
			// フォントセット
			datastyle.setFont(datafont);
			// 文字揃え
			datastyle.setAlignment(HorizontalAlignment.LEFT);
			datastyle.setBorderTop(BorderStyle.THIN);
			datastyle.setBorderBottom(BorderStyle.THIN);
			datastyle.setBorderRight(BorderStyle.THIN);
			datastyle.setBorderLeft(BorderStyle.THIN);

			// ここ行単位のデータ
			for (HashMap<String, String> ss : d) {
				cnt = 0;
				datarow = sheet.createRow(rownum);
				// ここ列単位のデータ
				for (Entry<String, String> ent : ss.entrySet()) {
					datacell = datarow.createCell(idxnum[cnt]);
					datacell.setCellStyle(datastyle);
					datacell.setCellValue(ent.getValue());
					cnt++;
				}
				rownum++;
			}

			// エクセルファイル作成
			FileOutputStream outPutFile = new FileOutputStream(xlsxfilename);
			book.write(outPutFile);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// SXSSFWorkbookの場合はいる。ディスクに一時ファイルを生成しているのでこれで削除。
			book.dispose();
		}
	}
}
