package mobilepaymentapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class PaymentOptionsDatabaseHelper extends SQLiteOpenHelper {

    protected static final String DATABASE_NAME = "CardDatabase";
    protected static final  String TABLE_CARDS = "cards";
    protected static final String KEY_ID = "id";
    protected static final String KEY_NAME = "name";
    protected static final String KEY_CARD_NUMBER = "card_number";
    protected static final String KEY_EXPIRY_DATE = "expiry_date";
    protected static final String KEY_CCV_CODE = "ccv_code";

    protected static final String TABLE_BANK_ACCOUNTS = "bank_accounts";
    protected static final  String KEY_BANK_ID = "id";
    protected static final String KEY_ACCOUNT_HOLDER_NAME = "account_holder_name";
    protected static final String KEY_ACCOUNT_NUMBER = "account_number";
    protected static final String KEY_ROUTING_NUMBER = "routing_number";

    protected static final String KEY_USER_ID = "user_id";

    protected static final int DATABASE_VERSION = 10;


    public PaymentOptionsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CARDS_TABLE = "CREATE TABLE " + TABLE_CARDS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_USER_ID + " INTEGER,"
                + KEY_NAME + " TEXT,"
                + KEY_CARD_NUMBER + " TEXT,"
                + KEY_EXPIRY_DATE + " TEXT,"
                + KEY_CCV_CODE + " TEXT"
                + ")";
        db.execSQL(CREATE_CARDS_TABLE);

        String CREATE_BANK_ACCOUNTS_TABLE = "CREATE TABLE " + TABLE_BANK_ACCOUNTS + "("
                + KEY_BANK_ID + " INTEGER PRIMARY KEY,"
                + KEY_USER_ID + " INTEGER,"
                + KEY_ACCOUNT_HOLDER_NAME + " TEXT,"
                + KEY_ACCOUNT_NUMBER + " TEXT,"
                + KEY_ROUTING_NUMBER + " TEXT"
                + ")";
        db.execSQL(CREATE_BANK_ACCOUNTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BANK_ACCOUNTS);
        onCreate(db);
    }

    void addCard(CreditCard card, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, userId);
        values.put(KEY_NAME, card.getName());
        values.put(KEY_CARD_NUMBER, card.getCardNumber());
        values.put(KEY_EXPIRY_DATE, card.getExpiryDate());
        values.put(KEY_CCV_CODE, card.getCcvCode());

        db.insert(TABLE_CARDS, null, values);
        db.close();
    }

    public void deleteCard(String cardNumber, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CARDS, KEY_CARD_NUMBER + " = ? AND " + KEY_USER_ID + " = ?", new String[] { cardNumber, String.valueOf(userId) });
        db.close();
    }

    ArrayList<CreditCard> getAllCardsForUser(int userId) {
        ArrayList<CreditCard> cardList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_CARDS + " WHERE " + KEY_USER_ID + " = " + userId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                CreditCard card = new CreditCard
                        (cursor.getInt(1), // userID
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5));
                cardList.add(card);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return cardList;
    }


    public void deleteBankAccount(String accountNumber, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BANK_ACCOUNTS, KEY_ACCOUNT_NUMBER + " = ? AND " + KEY_USER_ID + " = ?", new String[] { accountNumber, String.valueOf(userId) });
        db.close();
    }

    public void addBankAccount(BankAccount account, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, userId);
        values.put(KEY_ACCOUNT_HOLDER_NAME, account.getAccountHolderName());
        values.put(KEY_ACCOUNT_NUMBER, account.getAccountNumber());
        values.put(KEY_ROUTING_NUMBER, account.getRoutingNumber());

        db.insert(TABLE_BANK_ACCOUNTS, null, values);
        db.close();
    }

    public ArrayList<BankAccount> getAllBankAccountsForUser(int userId) {
        ArrayList<BankAccount> bankList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_BANK_ACCOUNTS + " WHERE " + KEY_USER_ID + " = " + userId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                BankAccount account = new BankAccount(
                        cursor.getInt(1), // userID
                        cursor.getString(2), // Account holder's name
                        cursor.getString(3),  // Account number
                        cursor.getString(4) // Routing number

                );
                bankList.add(account);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return bankList;
    }

    public String getTableNameCards() {
        return TABLE_CARDS;
    }

    public String getKeyId() {
        return KEY_ID;
    }

    public String getKeyName() {
        return KEY_NAME;
    }

    public String getCardNumber() {
        return KEY_CARD_NUMBER;
    }

    public String getExpiryDate() {
        return KEY_EXPIRY_DATE;
    }

    public String getCcvCode() {
        return KEY_CCV_CODE;
    }

    public String getTableNameBankAccounts() {
        return TABLE_BANK_ACCOUNTS;
    }

    public String getKeyBankId() {
        return KEY_BANK_ID;
    }

    public String getKeyAccountHolderName() {
        return KEY_ACCOUNT_HOLDER_NAME;
    }

    public String getKeyAccountNumber() {
        return KEY_ACCOUNT_NUMBER;
    }

    public String getKeyRoutingNumber() {
        return KEY_ROUTING_NUMBER;
    }


}
