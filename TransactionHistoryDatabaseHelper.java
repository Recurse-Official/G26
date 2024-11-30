package mobilepaymentapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "transactions.db";
    private static final int DATABASE_VERSION = 11;

    private static final String TABLE_TRANSACTIONS = "transactions";
    private static final String KEY_ID = "id";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_PAYMENT_METHOD = "paymentMethod";
    private static final String KEY_DATE = "date";

    public TransactionHistoryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_TRANSACTION = "CREATE TABLE " + TABLE_TRANSACTIONS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_USER_ID + " INTEGER, "
                + KEY_AMOUNT + " TEXT, "
                + KEY_PAYMENT_METHOD + " TEXT, "
                + KEY_DATE + " TEXT)";
        db.execSQL(CREATE_TABLE_TRANSACTION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS);
        onCreate(db);
    }

    public void addTransaction(Transaction transaction, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, userId);
        values.put(KEY_AMOUNT, transaction.getAmount());
        values.put(KEY_PAYMENT_METHOD, transaction.getPaymentMethod());
        values.put(KEY_DATE, transaction.getDate());

        db.insert(TABLE_TRANSACTIONS, null, values);
        db.close();
    }

    public List<Transaction> getAllTransactionsForUser(int userId) {
        List<Transaction> transactions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_TRANSACTIONS + " WHERE " + KEY_USER_ID + " = " + userId;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int amountIndex = cursor.getColumnIndex(KEY_AMOUNT);
                int paymentMethodIndex = cursor.getColumnIndex(KEY_PAYMENT_METHOD);
                int dateIndex = cursor.getColumnIndex(KEY_DATE);

                String amount = amountIndex != -1 ? cursor.getString(amountIndex) : "N/A";
                String paymentMethod = paymentMethodIndex != -1 ? cursor.getString(paymentMethodIndex) : "N/A";
                String date = dateIndex != -1 ? cursor.getString(dateIndex) : "N/A";

                Transaction transaction = new Transaction(amount, paymentMethod, date);
                transactions.add(transaction);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return transactions;
    }

}
