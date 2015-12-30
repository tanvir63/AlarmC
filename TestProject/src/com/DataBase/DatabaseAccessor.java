package com.DataBase;

import java.util.ArrayList;
import java.util.List;

import com.DomainModel.TestClass;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseAccessor {

	public static DataBaseHelper myDbHelper = null;
	public static SQLiteDatabase rdb = null;
	public static SQLiteDatabase wdb = null;

	public static synchronized final void initDB(Context context) throws Exception {
		if (myDbHelper == null) {
			myDbHelper = new DataBaseHelper(context);
			myDbHelper.openDataBase();
			rdb = myDbHelper.getReadableDatabase();
			wdb = myDbHelper.getWritableDatabase();
		}
	}

	public static synchronized final void closeDB() {
		if (myDbHelper != null) {
			myDbHelper.close();
			rdb.close();
			wdb.close();
			myDbHelper = null;
			rdb = null;
			wdb = null;
		}
	}

	public static List<TestClass> getTestList() {
		List<TestClass> ret = null;
		try {
			String[] selections = null;
			String qry = "select time1,note1 from test";
			Cursor cursor = rdb.rawQuery(qry, selections);
			ret = new ArrayList<TestClass>();
			while (cursor.moveToNext()) {
				TestClass testClass=new TestClass();
				testClass.time= cursor.getString(0);
				testClass.note= cursor.getString(1);
				ret.add(testClass);
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public static List<String> getCatalogList(String catalogue) {
		List<String> ret = null;
		try {
			String[] selections = null;
			// String qry =
			// "Select distinct (ordercatalogue.catalogue) from ordercatalogue where lot="
			// + lot + " order by ordre";
			String qry = "select distinct orderdomaine.domaine from orderdomaine where  orderdomaine.catalogue='"
					+ catalogue + "' order by ordre";
			Cursor cursor = rdb.rawQuery(qry, selections);
			ret = new ArrayList<String>();
			while (cursor.moveToNext()) {
				String item = cursor.getString(0);
				ret.add(item);
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static List<String[]> getDomainName(String catalogName) {
		List<String[]> ret = null;
		try {
			String[] selections = null;
			String qry = null;
			if (catalogName.contains("'")) {
				catalogName = catalogName.replace("'", "''");
			}
			// Log.i("DREG", "Catalog Name=" + catalogName);
			qry = "select distinct orderdomaine.domaine from cours,orderdomaine where cours.catalogue LIKE '"
					+ catalogName + "%' and orderdomaine.catalogue=cours.catalogue order by orderdomaine.ordre";
			Cursor cursor = rdb.rawQuery(qry, selections);
			ret = new ArrayList<String[]>();
			while (cursor.moveToNext()) {
				String[] row = new String[1];
				row[0] = cursor.getString(0);
				// row[1] = cursor.getString(1);
				ret.add(row);
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static List<String[]> getSSDomainName(String catalogue, String domainName) {
		List<String[]> ret = null;
		try {
			String[] selections = null;
			String qry = null;
			if (domainName.contains("'")) {
				domainName = domainName.replace("'", "''");
			}

			if (catalogue.contains("'")) {
				catalogue = catalogue.replace("'", "''");
			}

			// qry =
			// "select distinct ssdomaine from cours,orderdomaine where orderdomaine.domaine=cours.ssdomaine and cours.catalogue='Informatique' and cours.domaine like '"
			// + domainName + "' order by orderdomaine.ordre";

			qry = "select distinct orderdomaine.ssdomaine from cours,orderdomaine where "
					+ "orderdomaine.domaine=cours.domaine and cours.catalogue='" + catalogue
					+ "' and cours.domaine like '" + domainName + "' order by orderdomaine.ordre";

			Cursor cursor = rdb.rawQuery(qry, selections);
			ret = new ArrayList<String[]>();
			while (cursor.moveToNext()) {
				String[] row = new String[1];
				row[0] = cursor.getString(0);
				ret.add(row);
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static void deleteExample(String id) {
		try {
			wdb.delete("cours", "ID = " + id, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateChecked(String id) {
		try {
			ContentValues contentValues = new ContentValues();
			contentValues.put("selected", 1);
			wdb.update("cours", contentValues, "ID = " + id, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateUnChecked(String id) {
		try {
			ContentValues contentValues = new ContentValues();
			contentValues.put("selected", 0);
			wdb.update("cours", contentValues, "ID = " + id, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<String[]> getTytreList(String title, boolean hasSSDomain, String catalogName) {
		List<String[]> ret = null;
		try {
			String[] selections = null;
			String qry = null;

			if (title.contains("'")) {
				title = title.replace("'", "''");
			}

			if (catalogName.contains("'")) {
				catalogName = catalogName.replace("'", "''");
			}

			if (hasSSDomain) {
				qry = "select distinct titre from cours where ssdomaine LIKE '" + title + "%'  and catalogue like '"
						+ catalogName + "%'";
			} else {
				qry = "select distinct titre from cours where domaine LIKE '" + title + "%'  and catalogue like '"
						+ catalogName + "%'";
			}

			Cursor cursor = rdb.rawQuery(qry, selections);
			ret = new ArrayList<String[]>();
			while (cursor.moveToNext()) {
				String[] row = new String[2];
				row[0] = cursor.getString(0);
				row[1] = catalogName;
				ret.add(row);
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static List<String[]> getSECheckedTytreList(String title, boolean hasSSDomain, String catalogName) {
		List<String[]> ret = null;
		try {
			String[] selections = null;
			String qry = null;

			if (title.contains("'")) {
				title = title.replace("'", "''");
			}
			if (catalogName.contains("'")) {
				catalogName = catalogName.replace("'", "''");
			}

			if (hasSSDomain) {
				qry = "select distinct titre from cours where ssdomaine LIKE '" + title + "%'"
						+ "AND categorie LIKE 'SE%'  and catalogue like '" + catalogName + "%'";
			} else {
				qry = "select distinct titre from cours where domaine LIKE '" + title + "%'"
						+ "AND categorie LIKE 'SE%'  and catalogue like '" + catalogName + "%'";
			}

			Cursor cursor = rdb.rawQuery(qry, selections);
			ret = new ArrayList<String[]>();
			while (cursor.moveToNext()) {
				String[] row = new String[2];
				row[0] = cursor.getString(0);
				row[1] = catalogName;
				ret.add(row);
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static List<String[]> getCPCheckedTytreList(String title, boolean hasSSDomain, String catalogName) {
		List<String[]> ret = null;
		try {
			String[] selections = null;
			String qry = null;

			if (title.contains("'")) {
				title = title.replace("'", "''");
			}

			if (catalogName.contains("'")) {
				catalogName = catalogName.replace("'", "''");
			}

			if (hasSSDomain) {
				qry = "select distinct titre from cours where ssdomaine LIKE '" + title + "%'"
						+ "AND categorie LIKE 'CP%'  and catalogue like '" + catalogName + "%'";
			} else {
				qry = "select distinct titre from cours where domaine LIKE '" + title + "%'"
						+ "AND categorie LIKE 'CP%'  and catalogue like '" + catalogName + "%'";
			}

			Cursor cursor = rdb.rawQuery(qry, selections);
			ret = new ArrayList<String[]>();
			while (cursor.moveToNext()) {
				String[] row = new String[2];
				row[0] = cursor.getString(0);
				row[1] = catalogName;
				ret.add(row);
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static List<String[]> getNoneCheckedTytreList(String title, boolean hasSSDomain, String catalogName) {
		List<String[]> ret = null;
		try {
			String[] selections = null;
			String qry = null;

			if (title.contains("'")) {
				title = title.replace("'", "''");
			}

			if (catalogName.contains("'")) {
				catalogName = catalogName.replace("'", "''");
			}

			if (hasSSDomain) {
				qry = "select distinct titre from cours where ssdomaine Not LIKE '" + title + "%'"
						+ "AND (categorie Not LIKE 'CP%' AND categorie not LIKE 'SE%')  and catalogue like '"
						+ catalogName + "%'";
			} else {
				qry = "select distinct titre from cours where domaine NOT LIKE '" + title + "%'"
						+ "AND (categorie NOT LIKE 'CP%' AND categorie not LIKE 'SE%')  and catalogue like '"
						+ catalogName + "%'";
			}

			Cursor cursor = rdb.rawQuery(qry, selections);
			ret = new ArrayList<String[]>();
			while (cursor.moveToNext()) {
				String[] row = new String[2];
				row[0] = cursor.getString(0);
				row[1] = catalogName;
				ret.add(row);
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static List<String[]> getTitreDetailsList(String titreName) {
		List<String[]> ret = null;
		try {
			String[] selections = null;
			String qry = null;

			if (titreName.contains("'")) {
				titreName = titreName.replace("'", "''");
			}

			qry = "Select distinct code,titre,categorie,selected,domaine,duree,prerequis,Profil,synthese,id from cours WHERE titre LIKE '"
					+ titreName + "%'";

			Cursor cursor = rdb.rawQuery(qry, selections);
			ret = new ArrayList<String[]>();
			while (cursor.moveToNext()) {
				String[] row = new String[10];
				row[0] = cursor.getString(0);
				row[1] = cursor.getString(1);
				row[2] = cursor.getString(2);
				row[3] = cursor.getString(3);
				row[4] = cursor.getString(4);
				row[5] = cursor.getString(5);
				row[6] = cursor.getString(6);
				row[7] = cursor.getString(7);
				row[8] = cursor.getString(8);
				row[9] = cursor.getString(9);
				// row[1] = catalogName;
				ret.add(row);
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static List<String[]> getSearchList(String searchKey) {
		List<String[]> ret = null;
		try {
			String[] selections = null;
			String qry = null;

			if (searchKey.contains("'")) {
				searchKey = searchKey.replace("'", "''");
			}

			qry = "Select distinct titre,domaine from cours WHERE titreNoAccent LIKE '%" + searchKey + "%'"
					+ " OR code LIKE '%" + searchKey + "%'";

			Cursor cursor = rdb.rawQuery(qry, selections);
			ret = new ArrayList<String[]>();
			while (cursor.moveToNext()) {
				String[] row = new String[2];
				row[0] = cursor.getString(0);
				row[1] = cursor.getString(1);
				ret.add(row);
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static List<String[]> getChoixList() {
		List<String[]> ret = null;
		try {
			String[] selections = null;
			String qry = null;

			qry = "Select distinct id,titre,domaine,code from cours WHERE selected LIKE '1%'";

			Cursor cursor = rdb.rawQuery(qry, selections);
			ret = new ArrayList<String[]>();
			while (cursor.moveToNext()) {
				String[] row = new String[4];
				row[0] = cursor.getString(0);
				row[1] = cursor.getString(1);
				row[2] = cursor.getString(2);
				row[3] = cursor.getString(3);
				ret.add(row);
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static String getDomainColor(String catalog, String domaine) {
		String color = "";
		try {
			String[] selections = null;
			String qry = null;

			if (domaine.contains("'")) {
				domaine = domaine.replace("'", "''");
			}

			if (catalog.contains("'")) {
				catalog = catalog.replace("'", "''");
			}

			// qry = "select color from colors where colors.domaine='" + domaine
			// + "' and colors.catalogue='" + catalog
			// + "'";
			qry = "select distinct colors.color from certifications,cours,colors "
					+ "where cours.code=certifications.code and colors.catalogue=cours.catalogue  and colors.domaine=cours.domaine "
					+ "and certifications.catalogue=cours.catalogue and certifications.domaine like '" + domaine + "'";
			Cursor cursor = rdb.rawQuery(qry, selections);
			while (cursor.moveToNext()) {
				color = cursor.getString(0).toLowerCase();
				// cursor.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return color;
	}

	public static String getDomainColor(String domaine) {
		String color = "";
		try {
			String[] selections = null;
			String qry = null;

			if (domaine.contains("'")) {
				domaine = domaine.replace("'", "''");
			}

			qry = "select color from colors where colors.domaine='" + domaine + "' limit 0,1";
			Cursor cursor = rdb.rawQuery(qry, selections);
			while (cursor.moveToNext()) {
				color = cursor.getString(0).toLowerCase();
				// cursor.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		color = color.replace(".png", "");
		return color;
	}

	public static String getOptionalDomainColor(String domaine) {
		String color = "";
		try {
			String[] selections = null;
			String qry = null;
			if (domaine.contains("'")) {
				domaine = domaine.replace("'", "''");
			}

			qry = "select color from colors where domaine like '%" + domaine + "%' limit 0,1";
			Cursor cursor = rdb.rawQuery(qry, selections);
			while (cursor.moveToNext()) {
				color = cursor.getString(0).toLowerCase();

			}
			if (color.length() == 0) {
				if (domaine.equalsIgnoreCase("Gestion et Finances")) {
					color = "gestion.png";
				}
			}
			// if (domaine.equalsIgnoreCase("Ressources humaines")) {
			// color = "rh.png";
			// }
			// }
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return color;
	}

	public static String getQCMCatalogueColor(String catalogue) {
		String color = "";
		try {
			String[] selections = null;
			String qry = null;
			if (catalogue.contains("'")) {
				catalogue = catalogue.replace("'", "''");
			}

			qry = "select color from colors where catalogue like '%" + catalogue + "%' limit 0,1";
			Cursor cursor = rdb.rawQuery(qry, selections);
			while (cursor.moveToNext()) {
				color = cursor.getString(0).toLowerCase();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return color;
	}

	public static List<String[]> getQCMCatalogueList(String catalogueName) {
		List<String[]> ret = null;
		try {
			String[] selections = null;
			String qry = null;
			// qry =
			// "select distinct cours.catalogue from cours,qcm, ordercatalogue where ordercatalogue.catalogue=cours.catalogue and cours.catalogue not like 'Informatique' and ordercatalogue.lot=1  and cours.code=qcm.code order by ordercatalogue.ordre";
			qry = "select distinct cours.domaine from cours,qcm, orderdomaine where cours.catalogue =  '"
					+ catalogueName
					+ "' and orderdomaine.domaine=cours.domaine  and  cours.code=qcm.code order by orderdomaine.ordre";

			Cursor cursor = rdb.rawQuery(qry, selections);
			ret = new ArrayList<String[]>();
			while (cursor.moveToNext()) {
				String[] row = new String[1];
				row[0] = cursor.getString(0);
				ret.add(row);
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static boolean resetAllQCM() {

		int qcmUpdate = 0;
		int qcmItemUpdate = 0;
		boolean isUpdated = false;

		try {

			ContentValues qcmCv = new ContentValues();
			qcmCv.put("scorebefore", 0.0);
			qcmCv.put("scoreafter", 0.0);
			qcmUpdate = wdb.update("qcm", qcmCv, null, null);
			Log.i("DREG", "First table update =" + qcmUpdate);

			ContentValues qcmItemCv = new ContentValues();
			qcmItemCv.put("answeruser", 0);
			qcmItemUpdate = wdb.update("qcmssitem", qcmItemCv, null, null);
			Log.i("DREG", "Second table update =" + qcmItemUpdate);

			if ((qcmUpdate != 0) && (qcmItemUpdate != 0)) {
				isUpdated = true;
			} else {
				isUpdated = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return isUpdated;
	}

	public static List<String[]> getQCMDomainList(String catalogueName) {
		List<String[]> ret = null;
		try {
			String[] selections = null;
			String qry = null;

			if (catalogueName.contains("'")) {
				catalogueName = catalogueName.replace("'", "''");
			}

			// qry =
			// "select distinct domaine from cours,qcm where cours.code=qcm.code and catalogue='"
			// + catalogueName
			// + "' order by domaine";
			qry = "select distinct ssdomaine from cours,qcm where cours.code=qcm.code and domaine  like '"
					+ catalogueName + "' order by ssdomaine";
			Cursor cursor = rdb.rawQuery(qry, selections);
			ret = new ArrayList<String[]>();
			while (cursor.moveToNext()) {
				String[] row = new String[1];
				row[0] = cursor.getString(0);
				ret.add(row);
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static List<String[]> getQCMTitreList(String domaineName) {
		List<String[]> ret = null;
		try {
			String[] selections = null;
			String qry = null;

			if (domaineName.contains("'")) {
				domaineName = domaineName.replace("'", "''");
			}

			// qry =
			// "select titre,qcm.code,qcm.id,scorebefore,scoreafter  from cours,qcm where cours.code=qcm.code and domaine='"
			// + domaineName + "' order by titre";

			qry = "select titre,qcm.code,qcm.id,scorebefore,scoreafter from cours,qcm where cours.code=qcm.code and ssdomaine='"
					+ domaineName + "' order by titre";
			Cursor cursor = rdb.rawQuery(qry, selections);
			ret = new ArrayList<String[]>();
			while (cursor.moveToNext()) {
				String[] row = new String[5];
				row[0] = cursor.getString(0);
				row[1] = cursor.getString(1);
				row[2] = cursor.getString(2);
				row[3] = cursor.getString(3);
				row[4] = cursor.getString(4);
				ret.add(row);
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static List<String[]> getQCMItemList(int qcmId) {
		List<String[]> ret = null;
		try {
			String[] selections = null;
			String qry = null;

			qry = "select id,title,multiple, position,qcmid from qcmitem where qcmid=" + qcmId + " order by position";
			Cursor cursor = rdb.rawQuery(qry, selections);
			ret = new ArrayList<String[]>();
			while (cursor.moveToNext()) {
				String[] row = new String[5];
				row[0] = cursor.getString(0);
				row[1] = cursor.getString(1);
				row[2] = cursor.getString(2);
				row[3] = cursor.getString(3);
				row[4] = cursor.getString(4);
				Log.i("DREG", "ID =" + row[0]);
				Log.i("DREG", "Title =" + row[1]);
				ret.add(row);
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static List<String[]> getMCQOptions(int qcmId) {
		List<String[]> ret = null;
		try {
			String[] selections = null;
			String qry = null;

			qry = "select id,title,ok,position,answeruser  from qcmssitem where qcmitemid=" + qcmId
					+ " order by position";
			Cursor cursor = rdb.rawQuery(qry, selections);
			ret = new ArrayList<String[]>();
			while (cursor.moveToNext()) {
				String[] row = new String[5];
				row[0] = cursor.getString(0);
				row[1] = cursor.getString(1);
				row[2] = cursor.getString(2);
				row[3] = cursor.getString(3);
				row[4] = cursor.getString(4);
				Log.i("DREG", "id =" + row[0]);
				Log.i("DREG", "Title =" + row[1]);
				ret.add(row);
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static void updateQCMOptions(String id, String value) {
		try {
			ContentValues contentValues = new ContentValues();
			contentValues.put("answeruser", value);
			wdb.update("qcmssitem", contentValues, "id = " + id, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int getTotalPoints(int qcmId) {
		int totalPoints = 0;
		try {
			String[] selections = null;
			String qry = null;

			qry = "select sum(ok) from qcmitem,qcmssitem  where qcmitem.qcmid=" + qcmId + " and qcmitemid=qcmitem.id";
			Cursor cursor = rdb.rawQuery(qry, selections);
			while (cursor.moveToNext()) {
				totalPoints = Integer.parseInt(cursor.getString(0));
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return totalPoints;
	}

	public static int getGoodPoints(int qcmId) {
		int goodPoints = 0;
		try {
			String[] selections = null;
			String qry = null;

			qry = "select sum(ok) from qcmitem,qcmssitem  where qcmitem.qcmid=" + qcmId
					+ " and qcmitemid=qcmitem.id and ok=1 and answeruser=1";
			Cursor cursor = rdb.rawQuery(qry, selections);
			while (cursor.moveToNext()) {
				goodPoints = Integer.parseInt(cursor.getString(0));
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return goodPoints;
	}

	public static void updateQCMScore(int id, String scoreBefore, String scoreAfter) {
		try {
			ContentValues contentValues = new ContentValues();
			contentValues.put("scorebefore", scoreBefore);
			contentValues.put("scoreafter", scoreAfter);
			wdb.update("qcm", contentValues, "id = " + id, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<String[]> getScoreList() {
		List<String[]> ret = null;
		try {
			String[] selections = null;
			String qry = null;

			qry = "Select  qcm.id,cours.titre,scoreafter,scorebefore from cours,qcm where cours.code=qcm.code and scoreafter >0.0 order by scoreafter DESC";
			Cursor cursor = rdb.rawQuery(qry, selections);
			ret = new ArrayList<String[]>();
			while (cursor.moveToNext()) {
				String[] row = new String[4];
				row[0] = cursor.getString(0);
				row[1] = cursor.getString(1);
				row[2] = cursor.getString(2);
				row[3] = cursor.getString(3);
				Log.i("DREG", "Titre =" + row[1]);
				Log.i("DREG", "Score =" + row[2]);
				ret.add(row);
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static int getBeforeScore(int qcmId) {
		int ret = 0;
		try {
			String[] selections = null;
			String qry = null;

			qry = "Select  scoreafter from qcm where id=" + qcmId;
			Cursor cursor = rdb.rawQuery(qry, selections);
			while (cursor.moveToNext()) {
				String[] row = new String[4];
				ret = Integer.parseInt(cursor.getString(0));
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static List<String[]> getBestDomainName(String catalogName) {
		List<String[]> ret = null;
		try {
			String[] selections = null;
			String qry = null;
			if (catalogName.contains("'")) {
				catalogName = catalogName.replace("'", "''");
			}
			// if (catalogName.equalsIgnoreCase("Informatique")) {
			// qry =
			// "select  distinct cours.domaine from cours, ordercatalogue where  ordercatalogue.lot=2 and ordercatalogue.catalogue=cours.domaine  order by ordercatalogue.ordre";
			// } else {
			// qry =
			// "select  distinct cours.catalogue from cours, ordercatalogue where cours.catalogue <>'Informatique' and ordercatalogue.catalogue=cours.catalogue and best=1 order by ordercatalogue.ordre";
			// }
			qry = "select distinct orderdomaine.domaine from cours,orderdomaine where "
					+ "orderdomaine.catalogue=cours.catalogue and best=1 and cours.catalogue='" + catalogName
					+ "' order by orderdomaine.ordre";
			Cursor cursor = rdb.rawQuery(qry, selections);
			ret = new ArrayList<String[]>();
			while (cursor.moveToNext()) {
				String[] row = new String[1];
				row[0] = cursor.getString(0);
				ret.add(row);
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static List<String[]> getBestTitreWhileOnlyCatalogue(String catalogName) {
		List<String[]> ret = null;
		try {
			String[] selections = null;
			String qry = null;
			if (catalogName.contains("'")) {
				catalogName = catalogName.replace("'", "''");
			}
			// Log.i("DREG", "Catalog Name=" + catalogName);
			qry = "Select *from cours Where Best=1 And catalogue like '" + catalogName + "%'";
			Cursor cursor = rdb.rawQuery(qry, selections);
			ret = new ArrayList<String[]>();
			while (cursor.moveToNext()) {
				String[] row = new String[1];
				row[0] = cursor.getString(2);
				ret.add(row);
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static List<String[]> getBestTitreList(String catalogName, String domaineName) {
		List<String[]> ret = null;
		try {
			String[] selections = null;
			String qry = null;
			domaineName = domaineName.replace("'", "''");

			qry = "Select *from cours Where Best=1 And catalogue like '" + catalogName + "%' and domaine='"
					+ domaineName + "'";
			Cursor cursor = rdb.rawQuery(qry, selections);
			ret = new ArrayList<String[]>();
			while (cursor.moveToNext()) {
				String[] row = new String[1];
				row[0] = cursor.getString(2);
				ret.add(row);
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static List<String[]> getNouveautesDomainName(String catalogName) {
		List<String[]> ret = null;
		try {
			String[] selections = null;
			String qry = null;
			if (catalogName.contains("'")) {
				catalogName = catalogName.replace("'", "''");
			}
			// if (catalogName.equalsIgnoreCase("Informatique")) {
			// qry =
			// "select  distinct cours.domaine from cours, ordercatalogue where  ordercatalogue.lot=2 and ordercatalogue.catalogue=cours.domaine  order by ordercatalogue.ordre";
			// } else {
			// qry =
			// "select  distinct cours.catalogue from cours, ordercatalogue where cours.catalogue <>'Informatique' and ordercatalogue.catalogue=cours.catalogue and nouveau=1 order by ordercatalogue.ordre";
			// }
			qry = "select distinct orderdomaine.domaine from cours,orderdomaine where "
					+ "orderdomaine.catalogue=cours.catalogue and nouveau=1 and cours.catalogue='" + catalogName
					+ "' order by orderdomaine.ordre";
			Cursor cursor = rdb.rawQuery(qry, selections);
			ret = new ArrayList<String[]>();
			while (cursor.moveToNext()) {
				String[] row = new String[1];
				row[0] = cursor.getString(0);
				ret.add(row);
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static List<String[]> getTitreNouveautesList(String domaineName) {
		List<String[]> ret = null;
		try {
			String[] selections = null;
			String qry = null;
			domaineName = domaineName.replace("'", "''");
			qry = " Select  titre,code,duree,categorie from cours  b,orderdomaine a where "
					+ "b.domaine=a.domaine  and a.ssdomaine=b.ssdomaine and nouveau=1 and b.domaine like '"
					+ domaineName + "' order by  a.ordre asc,b.ordre asc";
			Cursor cursor = rdb.rawQuery(qry, selections);
			ret = new ArrayList<String[]>();
			while (cursor.moveToNext()) {
				String[] row = new String[4];
				row[0] = cursor.getString(0);
				row[1] = cursor.getString(1);
				row[2] = cursor.getString(2);
				row[3] = cursor.getString(3);
				ret.add(row);
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static List<String[]> getCertificationList(String catalogueName) {

		List<String[]> ret = null;
		try {
			String[] selections = null;
			String qry = null;

			// qry =
			// "select titre,code,catalogue,domaine from cours where code not like 'K%' and (titre LIKE '%CERTIFICATION%' OR titre LIKE '%Certificat%'"
			// +
			// " OR titre LIKE '%certif.%' OR titre LIKE '%certificate%' OR titre LIKE '%examen%') order by domaine";

			qry = "select distinct domaine from certifications where catalogue like '" + catalogueName
					+ "' order by ordre";

			Cursor cursor = rdb.rawQuery(qry, selections);
			ret = new ArrayList<String[]>();
			while (cursor.moveToNext()) {
				String[] row = new String[4];
				row[0] = cursor.getString(0);
				// row[1] = cursor.getString(1);
				// row[2] = cursor.getString(2);
				// row[3] = cursor.getString(3);
				ret.add(row);
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static List<String[]> getCertificationTitreList(String catalogue, String domaine) {

		List<String[]> ret = null;
		try {
			String[] selections = null;
			String qry = null;
			domaine = domaine.replace("'", "''");

			qry = "Select cours.titre from certifications , cours where certifications.domaine like '"
					+ domaine
					+ "' and certifications.catalogue='"
					+ catalogue
					+ "' and cours.catalogue=certifications.catalogue and cours.code=certifications.code order by cours.ordre";

			Cursor cursor = rdb.rawQuery(qry, selections);
			ret = new ArrayList<String[]>();
			while (cursor.moveToNext()) {
				String[] row = new String[1];
				row[0] = cursor.getString(0);
				ret.add(row);
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static List<String[]> getCycleCertificationList(String catalogueName) {

		List<String[]> ret = null;
		try {
			String[] selections = null;
			String qry = null;

			// qry =
			// "select titre,code from cours where code like 'K%' and titre not like 'Certification%'  order by domaine";
			qry = "select titre, code from cours where code like 'K%%' and titre not like 'Certification%%' and catalogue like '"
					+ catalogueName + "' and titre not like '%%FFP%%' and code not like '1U%%' order by titre";

			Cursor cursor = rdb.rawQuery(qry, selections);
			ret = new ArrayList<String[]>();
			while (cursor.moveToNext()) {
				String[] row = new String[2];
				String titre = cursor.getString(0);
				row[0] = titre;
				// Log.i("DREG", row[0]);
				row[1] = cursor.getString(1);
				ret.add(row);
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static String getColor(String code) {
		String color = "";
		try {
			String[] selections = null;
			String qry = null;

			// qry = "select color from colors,cours where cours.code='"
			// + code
			//
			// +
			// "' and ((colors.domaine=cours.catalogue and cours.catalogue<>'Informatique') or (colors.domaine=cours.domaine and cours.catalogue='Informatique')) limit 0,1";
			qry = "select color from colors,cours where cours.code='" + code
					+ "' and colors.domaine=cours.domaine limit 0,1";
			Cursor cursor = rdb.rawQuery(qry, selections);
			while (cursor.moveToNext()) {
				color = cursor.getString(0).toLowerCase();
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Log.i("DREG", "CODE: " + code + " - COLOR: " + color);
		return color;
	}

	public static String getOptionalColor(String code) {
		List<String[]> ret = null;
		String color = "";
		try {
			String[] selections = null;
			String qry = null;

			qry = "select color from colors,cours where cours.code='"
					+ code
					+ "' and ((colors.domaine=cours.catalogue and cours.catalogue<>'Informatique') or (colors.domaine=cours.domaine and cours.catalogue='Informatique')) limit 0,1";

			Cursor cursor = rdb.rawQuery(qry, selections);
			ret = new ArrayList<String[]>();
			while (cursor.moveToNext()) {
				color = cursor.getString(0).toLowerCase();
				String[] row = new String[1];
				row[0] = cursor.getString(0).toLowerCase();
				ret.add(row);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return color;
	}

}
