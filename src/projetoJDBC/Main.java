package projetoJDBC;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		try (Connection conexao = ConexaoDB.connectar()) {
			ProdutoDAO produtoDao = new ProdutoDAO(conexao);

			mostrarProdutos(produtoDao);

			Produto produto1 = new Produto("Notebook", 10, 1999.99, "Em estoque");
			Produto produto2 = new Produto("Smatphone", 20, 1499.99, "Estoque baixo");
			Produto produto3 = new Produto("Table", 15, 799.99, "Estoque baixo");

			produtoDao.inserir(produto1);
			produtoDao.inserir(produto2);
			produtoDao.inserir(produto3);

			mostrarProdutos(produtoDao);

			Produto produtoConsultado = produtoDao.consultarPorId(1);
			if (produtoConsultado != null) {
				System.out.println("Produto encontrado: " + produtoConsultado.getNome());
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
