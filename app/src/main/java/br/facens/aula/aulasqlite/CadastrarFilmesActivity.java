package br.facens.aula.aulasqlite;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.facens.aula.aulasqlite.db.DBHelper;

public class CadastrarFilmesActivity extends AppCompatActivity {
    private Spinner spinnerCategorias;
    private EditText editTextNomeFilme;
    private Button buttonEnviar;

    private DatabaseReference firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_filmes);

        spinnerCategorias = findViewById(R.id.spinnerCategorias);
        editTextNomeFilme = findViewById(R.id.editTextNomeFilme);
        buttonEnviar = findViewById(R.id.buttonEnviar);

        // Inicialize o Firebase
        firebaseDatabase = FirebaseDatabase.getInstance().getReference("filmes");

        // Configurar o Spinner com categorias do banco de dados SQLite
        List<String> categorias = obterCategoriasDoSQLite();
        ArrayAdapter<String> categoriaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categorias);
        categoriaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategorias.setAdapter(categoriaAdapter);

        // Configurar o evento de clique do botão
        buttonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarFilmeParaFirebase();
            }
        });
    }

    private List<String> obterCategoriasDoSQLite() {
        List<String> categorias = new ArrayList<>();

        // 1. Abra ou obtenha uma instância do banco de dados SQLite usando a classe DBHelper (você deve ter uma classe DBHelper).

        // 2. Execute uma consulta SQL para obter as categorias da tabela de categorias.
        // Por exemplo:
        DBHelper dbHelper = new DBHelper(this);

        String query = "SELECT nome FROM categorias";
        SQLiteDatabase db = dbHelper.getReadableDatabase(); // Substitua 'dbHelper' pelo nome da sua instância de DBHelper.
        Cursor cursor = db.rawQuery(query, null);

        // 3. Percorra o resultado do cursor e adicione as categorias à lista.
        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String categoria = cursor.getString(cursor.getColumnIndex("nome"));
                categorias.add(categoria);
            } while (cursor.moveToNext());
        }

        // 4. Feche o cursor e o banco de dados.
        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return categorias;
    }

    private void enviarFilmeParaFirebase() {
        String categoriaSelecionada = spinnerCategorias.getSelectedItem().toString();
        String nomeFilme = editTextNomeFilme.getText().toString();

        // Verifique se o campo de nome do filme não está vazio
        if (!nomeFilme.isEmpty()) {
            // Crie um objeto Map para representar os dados do filme
            Map<String, Object> filmeData = new HashMap<>();
            filmeData.put("nome", nomeFilme);
            filmeData.put("categoria", categoriaSelecionada);

            // Crie uma chave única para o novo filme
            String novaChave = firebaseDatabase.child("filmes").push().getKey();

            // Use DatabaseReference para enviar os dados para o Firebase
            firebaseDatabase.child("filmes").child(novaChave).setValue(filmeData, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    if (error == null) {
                        // Sucesso: Os dados foram enviados com sucesso para o Firebase.
                        // Você pode fornecer feedback ao usuário aqui, por exemplo, exibindo um Toast.
                        Toast.makeText(CadastrarFilmesActivity.this, "Filme enviado com sucesso para o Firebase.", Toast.LENGTH_SHORT).show();
                    } else {
                        // Erro: Houve um problema ao enviar os dados.
                        // Você pode fornecer feedback de erro ao usuário aqui, por exemplo, exibindo um Toast com uma mensagem de erro.
                        Toast.makeText(CadastrarFilmesActivity.this, "Erro ao enviar filme para o Firebase.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            // Limpe o campo de nome do filme após a inserção
            editTextNomeFilme.setText("");
        } else {
            // Exiba uma mensagem de erro ao usuário (por exemplo, usando um Toast) informando que o campo de nome do filme está vazio.
            Toast.makeText(this, "Campo de nome do filme está vazio.", Toast.LENGTH_SHORT).show();
        }
    }
}
