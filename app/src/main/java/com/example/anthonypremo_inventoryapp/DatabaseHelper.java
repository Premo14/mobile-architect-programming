package com.example.anthonypremo_inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String ITEM_TABLE = "ITEM_TABLE";
    public static final String COLUMN_ITEM_NAME = "ITEM_NAME";
    public static final String COLUMN_ITEM_QUANTITY = "ITEM_QUANTITY";
    public static final String ACCOUNT_TABLE = "ACCOUNT_TABLE";
    public static final String COLUMN_ACCOUNT_USERNAME = "ACCOUNT_USERNAME";
    public static final String COLUMN_ACCOUNT_PASSWORD = "ACCOUNT_PASSWORD";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "inventoryApp.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String itemTable = "CREATE TABLE " + ITEM_TABLE + " (" + COLUMN_ITEM_NAME + " TEXT, " + COLUMN_ITEM_QUANTITY + " INT)";
        db.execSQL(itemTable);
        String accountTable = "CREATE TABLE " + ACCOUNT_TABLE + " (" + COLUMN_ACCOUNT_USERNAME + " TEXT, " + COLUMN_ACCOUNT_PASSWORD + " TEXT)";
        db.execSQL(accountTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public boolean login(AccountModel accountModel, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ACCOUNT_USERNAME, accountModel.getUsername());
        cv.put(COLUMN_ACCOUNT_PASSWORD, accountModel.getPassword());

        String queryString = "SELECT " + COLUMN_ACCOUNT_USERNAME + " FROM " + ACCOUNT_TABLE + " WHERE " + COLUMN_ACCOUNT_USERNAME + "=?";
        Cursor cursor = db.rawQuery(queryString, new String[]{accountModel.getUsername()});

        if (cursor.moveToFirst() && cursor.getString(1).equals(accountModel.getPassword())) {
            Toast.makeText(context, "Welcome " + accountModel.getUsername()+ "!", Toast.LENGTH_SHORT).show();
            cursor.close();
            return true;
        }
        Toast.makeText(context, "Invalid Credentials.\n Try Creating an Account.", Toast.LENGTH_SHORT).show();
        cursor.close();
        return false;
    }

    public boolean addAccount(AccountModel accountModel, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ACCOUNT_USERNAME, accountModel.getUsername());
        cv.put(COLUMN_ACCOUNT_PASSWORD, accountModel.getPassword());

        String queryString = "SELECT " + COLUMN_ACCOUNT_USERNAME + " FROM " + ACCOUNT_TABLE + " WHERE " + COLUMN_ACCOUNT_USERNAME + "=?";
        Cursor cursor = db.rawQuery(queryString, new String[]{accountModel.getUsername()});

        if (cursor.moveToFirst()) {
            Toast.makeText(context, "Account already exists.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            long insert = db.insert(ACCOUNT_TABLE, null, cv);
            if (insert == -1) {
                cv.clear();
                cursor.close();
                db.close();
                return false;
            } else {
                Toast.makeText(context, "Created: " + accountModel, Toast.LENGTH_SHORT).show();
            }
        }
        cv.clear();
        cursor.close();
        db.close();
        return true;
    }

    public boolean addItem(ItemModel itemModel, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ITEM_NAME, itemModel.getName());
        cv.put(COLUMN_ITEM_QUANTITY, itemModel.getQuantity());

        String queryString = "SELECT " + COLUMN_ITEM_NAME + " FROM " + ITEM_TABLE + " WHERE " + COLUMN_ITEM_NAME + "=?";
        Cursor cursor = db.rawQuery(queryString, new String[]{itemModel.getName()});

        if (cursor.moveToFirst()) {
            Toast.makeText(context, "Item already exists.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            long insert = db.insert(ITEM_TABLE, null, cv);
            if (insert == -1) {
                cv.clear();
                cursor.close();
                db.close();
                return false;
            } else {
                Toast.makeText(context, "Added: " + itemModel, Toast.LENGTH_SHORT).show();
            }
        }
        cv.clear();
        cursor.close();
        db.close();
        return true;
    }

    public boolean removeItem(ItemModel itemModel, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ITEM_NAME, itemModel.getName());
        cv.put(COLUMN_ITEM_QUANTITY, itemModel.getQuantity());

        String whereClause = COLUMN_ITEM_NAME + "=?";
        String queryString = "SELECT " + COLUMN_ITEM_NAME + " FROM " + ITEM_TABLE + " WHERE " + COLUMN_ITEM_NAME + "=?";
        Cursor cursor = db.rawQuery(queryString, new String[]{itemModel.getName()});
        if (!cursor.moveToFirst()) {
            Toast.makeText(context, "Item does not exist.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            String[] args = new String[]{itemModel.getName()};
            db.delete(ITEM_TABLE, whereClause, args);
            Toast.makeText(context, "Removed: " + itemModel, Toast.LENGTH_SHORT).show();
        }
        cv.clear();
        cursor.close();
        db.close();
        return true;
    }

    public boolean updateItem(ItemModel itemModel, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ITEM_NAME, itemModel.getName());
        cv.put(COLUMN_ITEM_QUANTITY, itemModel.getQuantity());

        String whereClause = COLUMN_ITEM_NAME + "=?";
        String queryString = "SELECT " + COLUMN_ITEM_NAME + " FROM " + ITEM_TABLE + " WHERE " + COLUMN_ITEM_NAME + "=?";
        Cursor cursor = db.rawQuery(queryString, new String[]{itemModel.getName()});
        if (!cursor.moveToFirst()) {
            Toast.makeText(context, "Item does not exist.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            String[] args = new String[]{itemModel.getName()};
            db.update(ITEM_TABLE, cv, whereClause, args);
            Toast.makeText(context, "Updated: " + itemModel, Toast.LENGTH_SHORT).show();
        }
        cv.clear();
        cursor.close();
        db.close();
        return true;
    }

    public List<ItemModel> getAllItems() {
        List<ItemModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + ITEM_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                String itemName = cursor.getString(0);
                int itemQuantity = cursor.getInt(1);

                ItemModel newItem = new ItemModel(itemName, itemQuantity);
                returnList.add(newItem);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return returnList;
    }
}
