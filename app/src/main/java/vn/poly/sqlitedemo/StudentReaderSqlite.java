package vn.poly.sqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class StudentReaderSqlite {


    private SQLiteDatabase sqLiteDatabase;
    private String DATABASE_NAME = "students.db";

    private final String TABLE_NAME = "Student";
    private final String COLUMN_ID = "id";
    private final String COLUMN_NAME = "name";
    private final String COLUMN_PHONE = "phone";
    private final String COLUMN_ADDRESS = "address";


    public StudentReaderSqlite(Context context) {

        // phương thức openOrCreateDatabase có 3 tham số  . 1 là tên file cơ sở dữ liệu
        // 2 . Kiểu lưu trữ  ở đây MODE_PRIVATE tức là cơ sở dữ liệu đc lưu dạng private, chỉ để ứng dụng này sử dụng .
        // 3. Factory là 1 interface - sử dụng để tùy biến hoăc bổ sung mỗi khi các câu lệnh query đc gọi
        sqLiteDatabase = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + COLUMN_ID + " VARCHAR PRIMARY KEY, " +
                COLUMN_NAME + " VARCHAR, " + COLUMN_PHONE + " VARCHAR, " + COLUMN_ADDRESS + " VARCHAR)");

    }

    public long insertStudent(Student student) {

        // Tạo ra cặp tên cột vs dự liệu qua đối tượng ContentValues
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, student.id);
        values.put(COLUMN_NAME, student.name);
        values.put(COLUMN_PHONE, student.phone);
        values.put(COLUMN_ADDRESS, student.address);


        // thêm hàng mới vào trong bảng, dữ liệu trả về là id của hàng (nếu thêm thành công trả về 1 số > -1)
        long newRowId = sqLiteDatabase.insert(TABLE_NAME, null, values);


        return newRowId;
    }

    public long updateStudent(Student student) {


        // Tạo ra cặp tên cột vs dự liệu qua đối tượng ContentValues
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, student.id);
        values.put(COLUMN_NAME, student.name);
        values.put(COLUMN_PHONE, student.phone);
        values.put(COLUMN_ADDRESS, student.address);

        // update theo giá trị _ID
        String selection = COLUMN_ID + " = ?";

        // truyền vào giá trị của _ID
        String[] selectionArgs = {student.id};

        int count = sqLiteDatabase.update(
                TABLE_NAME,
                values,
                selection,
                selectionArgs);

        return count;
    }


    public long delStudent(String id) {

        // Xóa theo giá trị cột ID
        String selection = COLUMN_ID + " = ?";
        // Truyền giá trị cho id
        String[] selectionArgs = {id};
        // Thực hiện câu lệnh
        int deletedRows = sqLiteDatabase.delete(TABLE_NAME, selection, selectionArgs);

        return deletedRows;

    }


    public List<Student> getAllStudents() {

        List<Student> students = new ArrayList<>();

        String query_all = "SELECT * FROM " + TABLE_NAME;


        Cursor cursor = sqLiteDatabase.rawQuery(query_all, null);

        if (cursor.getCount() > 0) {
            while (cursor.isAfterLast()) {
                Student student = new Student();
                student.id = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
                student.name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                student.phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE));
                student.address = cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS));

                students.add(student);

            }
            cursor.close();
        }
        return students;
    }


    public void close() {
        sqLiteDatabase.close();
    }
}
