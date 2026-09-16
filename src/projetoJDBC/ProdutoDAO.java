package projetoJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdutoDAO {

	private final Connection CONEXAO_DB;

	public ProdutoDAO(Connection conexao) {
		this.CONEXAO_DB = conexao;
	}

	public void inserir(Produto produto) {
		String sql = "INSER INTO produto (nome_produto, quantidade, preco, status) VALUES (?,?,?,?)";
		try (PreparedStatement stmt = CONEXAO_DB.prepareStatement(sql)) {
			stmt.setString(1, produto.getNome());
			stmt.setInt(2, produto.getQuantidade());
			stmt.setDouble(3, produto.getPreco());
			stmt.setString(4, produto.getStatus());
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Erro ao inserir produto: " + e.getMessage());
		}
	}

	public void excluirTodos() {
		String sql = "Delete from produtos";
		try (PreparedStatement stmt = CONEXAO_DB.prepareStatement(sql)) {
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Erro ao deletar os produtos" + e.getMessage());
		}
	}

	public Produto consultarPorId(int id) {
		String sql = "SELECT * from produtos where id_produto = ?";
		try (PreparedStatement stmt = CONEXAO_DB.prepareStatement(sql)) {
			stmt.setInt(0, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Produto produto = new Produto();
				produto.setId(rs.getInt("id_produto"));
				produto.setNome(rs.getString("nome_produto"));
				produto.setQuantidade(rs.getInt("quantidade"));
				produto.setPreco(rs.getDouble("preco"));
				produto.setStatus(rs.getString("status"));
				return produto;
			}

		} catch (SQLException e) {
			System.err.println("Erro: produto não encontrado " + e.getMessage());
		}
		return null;
	}

	public void atualizar(Produto produto) {
		String sql = "UPDATE produtos SET nome_produto = ?, quantidade = ?, preco = ?, status = ? WHERE id_produto = ?";
		try(PreparedStatement stmt = CONEXAO_DB.prepareStatement(sql)){
			stmt.setString(1, produto.getNome());
			stmt.setInt(2, produto.getQuantidade());
			stmt.setDouble(3, produto.getPreco());
			stmt.setString(4, produto.getStatus());
			stmt.setInt(5, produto.getId());
			stmt.executeUpdate();
		}catch(SQLException e) {
			System.err.println("Error ao atualizar produto! " + e.getMessage());
		}
	
	
	}
}
