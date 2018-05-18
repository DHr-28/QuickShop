package androidrock.quickshoapapp.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Vigo Telecome on 29-12-2016.
 */
public class DBHelper extends SQLiteOpenHelper  {

    public static final String DATABASE_NAME = "QuickShoap.db";

    public static final String MYCART_TABLE_NAME = "MyCart";


    public static final String MYCART_COLUMN_ID = "id";
    public static final String MYCART_COLUMN_numberofquantityKey = "numberofquantityKey";
    public static final String MYCART_COLUMN_productidkey = "productidkey";
    public static final String CALLTYPE_COLUMN_wholeselleridkey = "wholeselleridkey";


    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "create table "+MYCART_TABLE_NAME+
                        " ("+MYCART_COLUMN_ID+" integer primary key, "+
                        MYCART_COLUMN_numberofquantityKey+" integer, "+
                        MYCART_COLUMN_productidkey+" integer, "+
                        CALLTYPE_COLUMN_wholeselleridkey+" integer)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + MYCART_TABLE_NAME);
        onCreate(db);
    }
}
