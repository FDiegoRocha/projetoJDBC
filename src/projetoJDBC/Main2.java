package projetoJDBC;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main2 {

	public static void main(String[] args) {
		try (Connection conexao = ConexaoDB.connectar()) {
			ProdutoDAO produtoDao = new ProdutoDAO(conexao);

			mostrarProdutos(produtoDao);

			Produto produtoConsultado = produtoDao.consultarPorId(1);
			if (produtoConsultado != null) {
				produtoConsultado.setNome("Laptop");
				System.out.println("Novo nome do Produto: " + produtoConsultado.getNome());
				produtoDao.atualizar(produtoConsultado);
				
				System.out.println("A base de dados ficou assim: ");
				mostrarProdutos(produtoDao);
			} else {
				System.out.println("Produto não encontrado.");
			}

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
