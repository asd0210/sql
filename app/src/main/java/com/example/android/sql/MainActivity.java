package com.example.android.sql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "debug";

    // @formatter:off
    private String[] NAMES = new String[]{
            "Anastassia", "Juan", "Enrique",
            "Frannie", "Paloma", "Francisco",
            "Lorenzio", "Maryvonne", "Siv",
            "Georgie", "Casimir", "Catharine",
            "Joker"};
    // @formatter:on

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.buttonStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTest();
            }
        });
    }

    private void startTest() {
        ContactDbOpenHelper helper = null;
        SQLiteDatabase db = null;
        try {
            // ContactDbOpenHelperを生成
            helper = new ContactDbOpenHelper(this);
            // 書き込み可能なSQLiteDatabaseインスタンスを取得
            db = helper.getWritableDatabase();
            // �f�[�^�̍쐬
            createData(db);
            // �f�[�^�̍X�V
            updateData(db);
            // �f�[�^�̍폜
            deleteData(db);
            // �f�[�^�̌���
            searchData(db);
        } finally {
            if (db != null) {
                db.close();
            }
            if (helper != null) {
                helper.close();
            }
        }
    }

    private void createData(SQLiteDatabase db) {
        for (int i = 0; i < NAMES.length; i++) {
            // 生成するデータを格納するContentValuesを生成
            ContentValues values = new ContentValues();
            values.put(Contact.NAME, NAMES[i]);
            values.put(Contact.AGE, 20);
            // 戻り値は生成されたデータの_IDが返される
            long id = db.insert(Contact.TBNAME, null, values);
            Log.d(TAG, "insert data:" + id);
        }
    }

    private void updateData(SQLiteDatabase db) {
        // 更新データを格納するContentValuesを生成
        ContentValues values = new ContentValues();
        // Contact.NAMEにaが含まれるデータの年齢を25に変更
        values.put(Contact.AGE, 25);
        // 戻り値は更新した数が返される
        int n = db.update(Contact.TBNAME, values, Contact.NAME + " like ?",
                new String[]{"%a%"});
        Log.d(TAG, "insert data:" + n);
    }

    private void deleteData(SQLiteDatabase db) {
        // Contact.NAMEがJokerのデータを削除
        // 戻り値は更新した数が返される
        int n = db.delete(Contact.TBNAME, Contact.NAME + " = ?",
                new String[]{"Joker"});
        Log.d(TAG, "delete data:" + n);
    }

    private void searchData(SQLiteDatabase db) {
        Cursor cursor = null;
        try {
            // Commentsテーブルのすべてのデータを取得
            cursor = db.query(Contact.TBNAME, null, Contact.AGE + " > ?",
                    new String[] { Integer.toString(20) }, null, null,
                    Contact.NAME);
            // Cursorにデータが1件以上ある場合処理を行う
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    // 名前を取得
                    String name = cursor.getString(cursor
                            .getColumnIndex(Contact.NAME));
                    // 年齢を取得
                    int age = cursor.getInt(cursor.getColumnIndex(Contact.AGE));
                    Log.d(TAG, name + ":" + age);

                    // 次のデータへCursorを移動
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
