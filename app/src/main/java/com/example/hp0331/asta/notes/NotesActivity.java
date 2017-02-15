package com.example.hp0331.asta.notes;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.hp0331.asta.R;
import com.example.hp0331.asta.notes.db.NotesDB;

public class NotesActivity extends ListActivity {
    private SimpleCursorAdapter adapter = null;
    private NotesDB db;
    private SQLiteDatabase dbRead;

    public static final int REQUEST_CODE_ADD_NOTE = 1;
    public static final int REQUEST_CODE_EDIT_NOTE = 2;

    /**
     * ʵ��OnClickListener�ӿڣ������־��ť�ļ���
     */
    private View.OnClickListener btnAddNote_clickHandler = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // �з��ؽ���Ŀ����༭��־��Activity��
            // requestCode If >= 0, this code will be returned
            // in onActivityResult() when the activity exits.
            startActivityForResult(new Intent(NotesActivity.this,
                    AtyEditNote.class), REQUEST_CODE_ADD_NOTE);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        // �������ݿ�
        db = new NotesDB(this);
        dbRead = db.getReadableDatabase();

        // ��ѯ���ݿⲢ��������ʾ��ListView�ϡ�
        // ����ʹ��CursorLoader�����������Ϊ��UI�̣߳�������������Ӧ����
        adapter = new SimpleCursorAdapter(this, R.layout.notes_list_cell, null,
                new String[] { NotesDB.COLUMN_NAME_NOTE_NAME,
                        NotesDB.COLUMN_NAME_NOTE_DATE }, new int[] {
                R.id.tvName, R.id.tvDate });
        setListAdapter(adapter);

        refreshNotesListView();

        findViewById(R.id.btnAddNote).setOnClickListener(
                btnAddNote_clickHandler);
    }


    /**
     * ��д�������ʼ��б��еıʼ���Ŀ�����ʱ�����ã��򿪱༭�ʼ�ҳ�棬ͬ�´��뵱ǰ�ʼǵ���Ϣ
     */
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        // ��ȡ��ǰ�ʼ���Ŀ��Cursor����
        Cursor c = adapter.getCursor();
        c.moveToPosition(position);

        // ��ʽIntent�����༭�ʼ�ҳ��
        Intent i = new Intent(NotesActivity.this, AtyEditNote.class);

        // ����ʼ�id��name��content
        i.putExtra(AtyEditNote.EXTRA_NOTE_ID,
                c.getInt(c.getColumnIndex(NotesDB.COLUMN_NAME_ID)));
        i.putExtra(AtyEditNote.EXTRA_NOTE_NAME,
                c.getString(c.getColumnIndex(NotesDB.COLUMN_NAME_NOTE_NAME)));
        i.putExtra(AtyEditNote.EXTRA_NOTE_CONTENT,
                c.getString(c.getColumnIndex(NotesDB.COLUMN_NAME_NOTE_CONTENT)));

        // �з��صĿ���Activity
        startActivityForResult(i, REQUEST_CODE_EDIT_NOTE);

        super.onListItemClick(l, v, position, id);
    }

    /**
     * Called when an activity you launched exits, giving you the requestCode
     * you started it with ����������Activity���ڲ����ؽ��ʱ���õķ���
     *
     * ���ӱ༭�ʼ�ҳ�淵��ʱ���ã�ˢ�±ʼ��б�
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUEST_CODE_ADD_NOTE:
            case REQUEST_CODE_EDIT_NOTE:
                if (resultCode == Activity.RESULT_OK) {
                    refreshNotesListView();
                }
                break;

            default:
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * ˢ�±ʼ��б����ݴ����ݿ��в�ѯ
     */
    public void refreshNotesListView() {
        /**
         * Change the underlying cursor to a new cursor. If there is an existing
         * cursor it will be closed.
         *
         * Parameters: cursor The new cursor to be used
         */
        adapter.changeCursor(dbRead.query(NotesDB.TABLE_NAME_NOTES, null, null,
                null, null, null, null));

    }

}
