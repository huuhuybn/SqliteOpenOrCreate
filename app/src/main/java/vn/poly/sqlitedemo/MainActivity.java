package vn.poly.sqlitedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    StudentReaderSqlite studentReaderSqlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        studentReaderSqlite = new StudentReaderSqlite(this);

        Student student = new Student();
        student.id = "ABC3324";
        student.name = "Huy Nguyen";
        student.address = "Huy Nguyen";
        student.phone = "uyiuy";

        studentReaderSqlite.insertStudent(student);



    }

    @Override
    protected void onDestroy() {
        studentReaderSqlite.close();
        super.onDestroy();
    }
}
