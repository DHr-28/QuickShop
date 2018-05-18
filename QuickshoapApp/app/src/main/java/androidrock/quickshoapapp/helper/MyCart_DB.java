package androidrock.quickshoapapp.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Vigo Telecome on 29-12-2016.
 */
public class MyCart_DB extends DBHelper {

    public MyCart_DB(Context context) {
        super(context);
    }

    public boolean insert(int numberofquantity, int productid, int wholesellerid) {
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues contentValues = new ContentValues();
        contentValues.put(MYCART_COLUMN_numberofquantityKey, numberofquantity);
        contentValues.put(MYCART_COLUMN_productidkey, productid);
        contentValues.put(CALLTYPE_COLUMN_wholeselleridkey, wholesellerid);


        db.insert(MYCART_TABLE_NAME, null, contentValues);
        return true;
    }

    public Integer delete(Integer productid,int storeid) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(MYCART_TABLE_NAME,
                MYCART_COLUMN_productidkey + " = ? AND "+ CALLTYPE_COLUMN_wholeselleridkey + " = ?" ,
                new String[]{Integer.toString(productid) , Integer.toString(storeid)});
    }

    public Cursor SelectAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT  " + MyCart_DB.MYCART_COLUMN_ID + " as _id," +
                MyCart_DB.MYCART_COLUMN_numberofquantityKey + "," +
                MyCart_DB.CALLTYPE_COLUMN_wholeselleridkey + "," +
                MyCart_DB.MYCART_COLUMN_productidkey + " FROM " +
                MyCart_DB.MYCART_TABLE_NAME, null);

    }
}
