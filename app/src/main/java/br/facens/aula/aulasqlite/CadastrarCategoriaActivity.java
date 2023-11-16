package br.facens.aula.aulasqlite;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import br.facens.aula.aulasqlite.db.DBHelper;

public class CadastrarCategoriaActivity extends AppCompatActivity {
    private EditText etCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_categoria);

        etCategoria = findViewById(R.id.etCategoria);
        Button btnAdicionarCategoria = findViewById(R.id.btnAdicionarCategoria);

        btnAdicionarCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtém o nome da categoria inserido pelo usuário
                String nomeCategoria = etCategoria.getText().toString();

                // Verifica se o nome da categoria não está vazio
                if (!nomeCategoria.isEmpty()) {
                    // Cria um DBHelper para gerenciar o banco de dados SQLite
                    DBHelper dbHelper = new DBHelper(CadastrarCategoriaActivity.this);

                    // Obtém um objeto SQLiteDatabase para escrever no banco de dados
                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    // Cria um ContentValues para inserir os dados no banco de dados
                    ContentValues values = new ContentValues();
                    values.put("nome", nomeCategoria);

                    // Insere os dados no banco de dados
                    /*
                    O "nullColumnHack" é usado quando você deseja inserir um registro na tabela e algumas
                    colunas podem aceitar valores nulos, mas você não deseja fornecer valores para essas
                    colunas em particular. Definindo o "nullColumnHack" como null, você permite que o Android
                     insira um valor padrão nulo nessas colunas, caso seja necessário.
                     */
                    long newRowId = db.insert(DBHelper.TABLE_CATEGORIAS, null, values);

                    // Fecha o banco de dados
                    db.close();

                    // Retorna à atividade anterior (ou você pode fazer algo diferente aqui)
                    finish();
                }
            }
        });
    }
}
