package projetoJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {
	private static final String URL_JDBC_PADRAO = "jdbc:sqlite:meu_banco_de_dados.db";

	public static Connection connectar() {
		try {

			return DriverManager.getConnection(URL_JDBC_PADRAO);
		} catch (SQLException e) {
			System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
			return null;
		}
	}
	
	public static Connection connectarGenerico(String url, String usuario, String senha) {
		try {
			return DriverManager.getConnection(url, usuario, senha);
		}catch( SQLException e) {
			System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
			return null;
		}
	}

}
