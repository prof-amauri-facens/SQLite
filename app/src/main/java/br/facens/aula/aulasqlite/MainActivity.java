package br.facens.aula.aulasqlite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtém referências para os botões do layout usando seus IDs
        Button btnCadastrarCategoria = findViewById(R.id.btnCadastrarCategoria);
        Button btnGerenciarCategorias = findViewById(R.id.btnGerenciarCategorias);
        Button btnCadastrarFilmes = findViewById(R.id.btnCadastrarFilmes);

        // Define um ouvinte de clique para o botão "Cadastrar Categoria"
        btnCadastrarCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cria uma nova intenção (Intent) para abrir a atividade "CadastrarCategoriaActivity"
                Intent intent = new Intent(MainActivity.this, CadastrarCategoriaActivity.class);
                // Inicia a atividade "CadastrarCategoriaActivity"
                startActivity(intent);
            }
        });

        // Define um ouvinte de clique para o botão "Gerenciar Categorias"
        btnGerenciarCategorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cria uma nova intenção (Intent) para abrir a atividade "GerenciarCategoriasActivity"
                Intent intent = new Intent(MainActivity.this, GerenciarCategoriasActivity.class);
                // Inicia a atividade "GerenciarCategoriasActivity"
                startActivity(intent);
            }
        });

        // Define um ouvinte de clique para o botão "Cadastrar Filmes"
        btnCadastrarFilmes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cria uma nova intenção (Intent) para abrir a atividade "CadastrarFilmesActivity"
                Intent intent = new Intent(MainActivity.this, CadastrarFilmesActivity.class);
                // Inicia a atividade "CadastrarFilmesActivity"
                startActivity(intent);
            }
        });
    }
}
