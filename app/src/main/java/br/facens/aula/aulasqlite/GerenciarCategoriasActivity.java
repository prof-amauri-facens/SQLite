package br.facens.aula.aulasqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import br.facens.aula.aulasqlite.db.DBHelper;

public class GerenciarCategoriasActivity extends AppCompatActivity {
    private ListView listViewCategorias;
    private ArrayAdapter<String> categoriaAdapter;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciar_categorias);

        listViewCategorias = findViewById(R.id.listViewCategorias);
        categoriaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listViewCategorias.setAdapter(categoriaAdapter);

        // Abre o banco de dados em modo de leitura/escrita
        DBHelper dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        listarCategorias();

        // Define um ouvinte de clique para o ListView
        listViewCategorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtém o ID da categoria selecionada
                long categoriaId = id;

                // Exclui a categoria do banco de dados e atualiza a lista
                deletarCategoria(categoriaId);
            }
        });
    }

    private void listarCategorias() {
        categoriaAdapter.clear();

        /*
        O Cursor é uma classe do Android que permite a você interagir com os resultados de uma consulta
        ao banco de dados SQLite. Ele funciona como um ponteiro que percorre os registros retornados
        pela consulta SQL.
        Cursor cursor = db.rawQuery("SELECT * FROM categorias", null);:

        db.rawQuery é um método da classe SQLiteDatabase que permite executar uma consulta SQL direta no banco de dados.
        A consulta SQL "SELECT * FROM categorias" é usada para selecionar todos os registros da tabela de categorias.
        O resultado da consulta é armazenado em um objeto Cursor. Esse Cursor irá conter os registros que correspondem à consulta.

         */
        Cursor cursor = db.rawQuery("SELECT * FROM categorias", null);
        /*
        O while (cursor.moveToNext()) é usado para percorrer os resultados do Cursor. O método moveToNext()
         move o Cursor para o próximo registro, e retorna true se houver um próximo registro disponível,
         ou false se não houver mais registros para ler.Dentro do loop while, você pode acessar os
         valores de colunas de cada registro usando métodos como getLong() e getString() do `Cursor.
         O código dentro do loop é executado uma vez para cada registro retornado pela consulta.
         */
        while (cursor.moveToNext()) {
            @SuppressLint("Range") long id = cursor.getLong(cursor.getColumnIndex(DBHelper.COLUMN_ID));
            @SuppressLint("Range") String nome = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_NOME));
            categoriaAdapter.add(id + ": " + nome);
        }

        cursor.close();
    }

    private void deletarCategoria(long categoriaId) {
        // Exclui a categoria do banco de dados
        /*
        db.delete: Este é um método da classe SQLiteDatabase que é usada para excluir registros de
        uma tabela no banco de dados.
        DBHelper.TABLE_CATEGORIAS: DBHelper.TABLE_CATEGORIAS é uma constante que representa o nome da
        tabela no banco de dados onde as categorias são armazenadas. Isso garante que estamos excluindo
        registros da tabela correta.
        DBHelper.COLUMN_ID + " = ?": Esta parte define a cláusula WHERE da consulta de exclusão. Ela
        especifica qual registro deve ser excluído. Neste caso, estamos excluindo o registro onde a
        coluna COLUMN_ID é igual ao valor fornecido.
        new String[]{String.valueOf(categoriaId)}: Esta é uma matriz de argumentos que substituirá o
        ponto de interrogação na cláusula WHERE. Neste caso, String.valueOf(categoriaId) converte o
        valor categoriaId em uma string, que será usada na cláusula WHERE. Isso garante que apenas
        o registro com o ID correspondente ao categoriaId seja excluído.

        Em resumo, a linha diz ao banco de dados para excluir o registro da tabela de categorias onde
        a coluna COLUMN_ID é igual ao categoriaId fornecido como argumento. Isso efetivamente remove
        a categoria do banco de dados com base em seu ID. Após a exclusão, a lista de categorias é
        atualizada para refletir a categoria removida.
         */
        db.delete(DBHelper.TABLE_CATEGORIAS, DBHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(categoriaId)});

        // Atualiza a lista de categorias
        listarCategorias();

        Toast.makeText(this, "Categoria excluída com sucesso.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
