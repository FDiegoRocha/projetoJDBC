package projetoJDBC;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProdutoGUI extends Application {
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
		vbox.setPadding(new Insets(10, 10, 10, 10));
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
			try {
				Produto produto = new Produto();
				produto.setNome(nomeInput.getText());
				produto.setPreco(Double.parseDouble(precoInput.getText().replace(",", ".")));
				produto.setQuantidade(Integer.parseInt(quantidadeInput.getText()));
				produto.setStatus(statusComboBox.getValue());
				produtoDAO.inserir(produto);
				produtos.setAll(produtoDAO.listarTodos());
				limparCampos();
			}catch(NumberFormatException n) {
				System.err.println("Erro ao digitar, insira um valor valido! " + n.getMessage());
			}
			

		});
		Button updateButton = new Button("Atualizar");
		updateButton.setOnAction(e -> {
			// SELECIONA O PRODUTO NA TABELA COM CLIQUE EM CIMA
			Produto selectedProduto = tableView.getSelectionModel().getSelectedItem();
			if (selectedProduto != null) {
				// AQUI O PRODUTO JA ESTA SELECIONADO MAS SE FOR DIGITADO NOS INPUTS SERÁ
				// REGISTRADO O ULTIMO TEXTO
				selectedProduto.setNome(nomeInput.getText());
				selectedProduto.setPreco(Double.parseDouble(precoInput.getText().replace(",", ".")));
				selectedProduto.setQuantidade(Integer.parseInt(quantidadeInput.getText()));
				selectedProduto.setStatus(statusComboBox.getValue());
				produtoDAO.atualizar(selectedProduto);
				produtos.setAll(produtoDAO.listarTodos());
				limparCampos();
			}

		});

		Button deleteButton = new Button("Excluir");
		deleteButton.setOnAction(e -> {
			Produto selectedProduto = tableView.getSelectionModel().getSelectedItem();
			if (selectedProduto != null) {
				produtoDAO.excluir(selectedProduto.getId());
				produtos.setAll(produtoDAO.listarTodos());
				limparCampos();
			}
		});
		Button clearButton = new Button("Limpar");
		clearButton.setOnAction(e -> {
			limparCampos();
		});

		tableView = new TableView<>();
		tableView.setItems(produtos); // Define a lista de produtos na tabela.
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS); // Ajusta o tamanho das
																							// colunas.
		List<TableColumn<Produto, ?>> columns = List.of(criarColuna("ID", "id"), criarColuna("Produto", "nome"),
				criarColuna("Quantidade", "quantidade"), criarColuna("Preço", "preco"),
				criarColuna("Status", "status"));
		tableView.getColumns().addAll(columns);
		tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				nomeInput.setText(newSelection.getNome());
				quantidadeInput.setText(String.valueOf(newSelection.getQuantidade()));
				precoInput.setText(String.valueOf(newSelection.getPreco()));
				statusComboBox.setValue(String.valueOf(newSelection.getStatus()));
			}
		});
		HBox buttonBox = new HBox();
		buttonBox.setSpacing(10);
		buttonBox.getChildren().addAll(addButton, updateButton, deleteButton, clearButton); // Adiciona os botões

		vbox.getChildren().addAll(nomeProdutoBox, quantidadeBox, precoBox, statusBox, buttonBox, tableView);

		Scene scene = new Scene(vbox, 800, 600);
		// scene.getStylesheets().add("styles-produtos.css"); //Adiciona folhas de
		// estilos
		palco.setScene(scene);
		palco.show();

	} // fim metodo start()

	// O METODO STOP É CHAMADO AUTOMATICAMENTE QUANDO A PLAICAÇÃO JAVAFX É
	// ENCERRADA.
	@Override
	public void stop() {
		try {
			conexaoDB.close();
		} catch (SQLException e) {
			System.err.println("Erro ao fechar conexao " + e.getMessage());
		}
	}

	private void limparCampos() {
		nomeInput.clear();
		quantidadeInput.clear();
		precoInput.clear();
		statusComboBox.setValue(null);
	}

	private TableColumn<Produto, ?> criarColuna(String title, String property) {
		TableColumn<Produto, ?> col = new TableColumn<>(title);
		col.setCellValueFactory(new PropertyValueFactory<>(property));
		return col;
	}

}
