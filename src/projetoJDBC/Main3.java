package projetoJDBC;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main3 {

	public static void main(String[] args) {
		try (Connection conexao = ConexaoDB.connectar()) {
			ProdutoDAO produtoDao = new ProdutoDAO(conexao);

			mostrarProdutos(produtoDao);

			// excluindo por id
//			produtoDao.excluir(3);

			// excluindo todos produtos
			produtoDao.excluirTodos();
			System.out.println("Lista após excluir todos");
			mostrarProdutos(produtoDao);

		} catch (SQLException e) {
			System.err.println("Erro geral: " + e.getMessage());
		}

	}

	public static void mostrarProdutos(ProdutoDAO produtoDao) {

		List<Produto> lista = produtoDao.listarTodos();
		if (!lista.isEmpty()) {
			lista.forEach(System.out::println);

		} else {
			System.out.println("Nenhum produto encontrado!");
		}

	}

}
