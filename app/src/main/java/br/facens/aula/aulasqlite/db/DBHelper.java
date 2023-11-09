package br.facens.aula.aulasqlite.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    // Nome do banco de dados
    private static final String DATABASE_NAME = "categorias.db";

    // Versão do banco de dados
    private static final int DATABASE_VERSION = 1;

    // Nome da tabela de categorias
    public static final String TABLE_CATEGORIAS = "categorias";

    // Nome da coluna de ID
    public static final String COLUMN_ID = "_id";

    // Nome da coluna de nome da categoria
    public static final String COLUMN_NOME = "nome";

    // SQL para criar a tabela de categorias
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_CATEGORIAS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NOME + " TEXT);";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /*
    Este método é chamado quando o banco de dados SQLite é criado pela primeira vez. Isso ocorre quando
    o banco de dados ainda não existe no sistema de arquivos, e sua aplicação solicita a criação do banco de dados.
    No método onCreate, você deve definir a estrutura do banco de dados, criar tabelas e definir quaisquer
    configurações iniciais necessárias. Isso é tipicamente feito por meio da execução de comandos SQL, como a criação de tabelas.
    O método onCreate recebe um objeto SQLiteDatabase como parâmetro, que representa o banco de dados
    recém-criado. Você usa esse objeto para executar comandos SQL para criar as tabelas e realizar outras inicializações.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Cria a tabela de categorias quando o banco de dados é criado
        db.execSQL(TABLE_CREATE);
    }

    /*
    Este método é chamado quando a versão do banco de dados é atualizada, ou seja, quando a estrutura
    do banco de dados existente precisa ser modificada devido a uma alteração no esquema ou nas tabelas.
    O método onUpgrade é chamado quando a versão atual do banco de dados (definida no construtor do
    SQLiteOpenHelper) é maior do que a versão anterior que foi criada no dispositivo.
    No método onUpgrade, você deve implementar a lógica para atualizar o banco de dados existente para
     se adequar à nova versão. Isso pode incluir a adição, remoção ou modificação de tabelas e dados existentes.
    Uma abordagem comum é executar um comando SQL para descartar a tabela antiga (usando DROP TABLE IF EXISTS)
    e, em seguida, chamar o método onCreate para recriar a nova estrutura do banco de dados.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Atualiza o banco de dados, se necessário
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIAS);
        onCreate(db);
    }
}
