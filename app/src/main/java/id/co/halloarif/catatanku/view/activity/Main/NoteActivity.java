package id.co.halloarif.catatanku.view.activity.Main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import id.co.halloarif.catatanku.R;

public class NoteActivity extends AppCompatActivity {
    private EditText etNoteInputJudulfvbi;
    private EditText etNoteInputCatatanfvbi;
    private LinearLayout llNoteInputBottomfvbi;
    private ImageView ivImageNoteInputfvbi;
    private ImageButton ibNoteInputTTSfvbi;
    private Button bNoteInputSubmitfvbi;
    private ImageButton ibNoteInputSTTfvbi;
    private ImageButton ibNoteInputImagefvbi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_input);

        initComponent();
        initParam();
        initSession();
        initContent();
        initListener();
    }

    private void initComponent() {
        etNoteInputJudulfvbi = (EditText) findViewById(R.id.etNoteInputJudul);
        etNoteInputCatatanfvbi = (EditText) findViewById(R.id.etNoteInputCatatan);
        llNoteInputBottomfvbi = (LinearLayout) findViewById(R.id.llNoteInputBottom);
        ivImageNoteInputfvbi = (ImageView) findViewById(R.id.ivImageNoteInput);
        ibNoteInputTTSfvbi = (ImageButton) findViewById(R.id.ibNoteInputTTS);
        bNoteInputSubmitfvbi = (Button) findViewById(R.id.bNoteInputSubmit);
        ibNoteInputSTTfvbi = (ImageButton) findViewById(R.id.ibNoteInputSTT);
        ibNoteInputImagefvbi = (ImageButton) findViewById(R.id.ibNoteInputImage);
    }

    private void initParam() {

    }

    private void initSession() {

    }

    private void initContent() {

    }

    private void initListener() {
        ibNoteInputTTSfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        bNoteInputSubmitfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        ibNoteInputSTTfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        ibNoteInputImagefvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        ivImageNoteInputfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_input, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menuNoteInputChangeColor) {

        } else if (id == R.id.menuNoteInputShare) {

        } else if (id == R.id.menuNoteInputAddFriend) {

        }

        return super.onOptionsItemSelected(item);
    }
}
