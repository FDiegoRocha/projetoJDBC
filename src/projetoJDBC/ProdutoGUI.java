package projetoJDBC;

import java.sql.Connection;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProdutoGUI extends Application{
	private ProdutoDAO produtoDAO;
	private ObservableList<Produto> produtos;
	private TableView<Produto> tableView;
	private TextField nomeInput, quantidadeInput, precoInput;
	private ComboBox<String> statusComboBox;
	private Connection conexaoDB;
	

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage palco) {
		conexaoDB = ConexaoDB.connectar();
		produtoDAO = new ProdutoDAO(conexaoDB);
		produtos = FXCollections.observableArrayList(produtoDAO.listarTodos());
		
		palco.setTitle("Gerenciamento de estoque de Produtos");
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10,10,10,10));
		vbox.setSpacing(10);
		
		HBox nomeProdutoBox = new HBox();
		nomeProdutoBox.setSpacing(10);
		Label nomeLabel = new Label("Produto:");
		nomeInput = new TextField();
		nomeProdutoBox.getChildren().addAll(nomeLabel, nomeInput);
		
		HBox quantidadeBox = new HBox();
		quantidadeBox.setSpacing(10);
		Label quantidadeLabel = new Label("Quantidade:");
		quantidadeInput = new TextField();
		quantidadeBox.getChildren().addAll(quantidadeLabel, quantidadeInput);
		
		HBox precoBox = new HBox();
		precoBox.setSpacing(10);
		Label precoLabel = new Label("Preço:");
		precoInput = new TextField();
		precoBox.getChildren().addAll(precoLabel, precoInput);
		
		HBox statusBox = new HBox();
		statusBox.setSpacing(10);
		Label statusLabel = new Label("Status:");
		statusComboBox = new ComboBox<>();
		statusComboBox.getItems().addAll("Estoque normal", "Estoque Baixo");
		statusBox.getChildren().addAll(statusLabel, statusComboBox);
		
		Button addButton = new Button("Adicionar");
		addButton.setOnAction(e -> {
			Produto produto = new Produto();
			produto.setNome(nomeInput.getText());
			produto.setPreco(Double.parseDouble(precoInput.getText().replace(",", ".")));
			produto.setQuantidade(Integer.parseInt(quantidadeInput.getText()));
			produto.setStatus(statusComboBox.getValue());
			produtoDAO.inserir(produto);
			produtos.setAll(produtoDAO.listarTodos());
			limparCampos(nomeInput, precoInput, quantidadeInput);

		});
		Button updateButton = new Button("Atualizar");
		updateButton.setOnAction(e -> {
			// SELECIONA O PRODUTO NA TABELA COM CLIQUE EM CIMA
			Produto selectedProduto = tableView.getSelectionModel().getSelectedItem();
			if(selectedProduto != null) {
				// AQUI O PRODUTO JA ESTA SELECIONADO MAS SE FOR DIGITADO NOS INPUTS SERÁ REGISTRADO O ULTIMO TEXTO
				selectedProduto.setNome(nomeInput.getText());
				selectedProduto.setPreco(Double.parseDouble(precoInput.getText()));
				selectedProduto.setQuantidade(Integer.parseInt(quantidadeInput.getText()));
				selectedProduto.setStatus(statusComboBox.getValue());
				produtoDAO.atualizar(selectedProduto);
				produtos.addAll(produtoDAO.listarTodos());
				limparCampos();
			}
			
		});
		
		Button deleteButton = new Button("Excluir");
		deleteButton.setOnAction(e -> {
			Produto selectedProduto = tableView.getSelectionModel().getSelectedItem();
			if(selectedProduto != null) {
				 produtoDAO.excluir(selectedProduto.getId());
				 produtos.addAll(produtoDAO.listarTodos());
				 limparCampos();
			}
		});
		Button clearButton = new Button("Limpar");
		clearButton.setOnAction(e -> {
			limparCampos();
		});
		
	}

	public static void limparCampos() {
		input1.clear();
		input2.clear();
		input3.clear();
	}

}
