package projetoJDBC;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CriadorTabela {
	public static void main(String[] args) {
		try (Connection conexao = ConexaoDB.connectar()) {
			Statement stmt = conexao.createStatement();
			
			String comandoSql = "CREATE TABLE produtos (" + 
					"id_produto INTEGER PRIMARY KEY," +
					"nome_produto TEXT NOT NULL, " +
					"quantidade INTEGER," +
					"preco REAL," + 
					"status TEXT" +
					");";
			
			System.out.println(comandoSql);

			stmt.execute(comandoSql);
			System.out.println("Tabela 'Produtos' criada com sucesso!");
			
			
		} catch (SQLException e) {
			System.err.println("Erro ao criar a tabela " + e.getMessage());
			e.printStackTrace();
		}
	}

}
