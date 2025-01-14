
package mz.co.mahs.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import mz.co.mahs.dao.DaoCliente;
import mz.co.mahs.models.Cliente;
import mz.co.mahs.models.Utilizador;

public class FXMLClienteController implements Initializable, Crud {
	Alert alert = new Alert(AlertType.INFORMATION);
	Alert alertConfirm = new Alert(AlertType.CONFIRMATION);

	@FXML
	private AnchorPane rootPane;

	@FXML
	private TextField txtNome;

	@FXML
	private TextField txtApelido;

	@FXML
	private TextField txtEmail;

	@FXML
	private TableView<Cliente> tblCliente;

	@FXML
	private TableColumn<Cliente, Integer> colId;

	@FXML
	private TableColumn<Cliente, String> colNome;

	@FXML
	private TableColumn<Cliente, String> colApelido;

	@FXML
	private TableColumn<Cliente, String> colEmail;

	@FXML
	private TableColumn<Cliente, String> colTelefone;

	@FXML
	private TableColumn<Cliente, String> colSexo;

	@FXML
	private TableColumn<Cliente, String> colEndereco;

	@FXML
	private TableColumn<Cliente, Utilizador> colUtilizador;

	@FXML
	private TableColumn<Cliente, String> colDataRegisto;

	@FXML
	private TextField txtEndereco;

	@FXML
	private TextField txtTelefone;

	@FXML
	private ComboBox<String> cboSexo;
	ObservableList<String> sexo = FXCollections.observableArrayList("M", "F");

	@FXML
	private TextField txtID;

	@FXML
	private HBox hBoxButton;

	@FXML
	private Button btnAdd;

	@FXML
	private Button btnUpdate;

	@FXML
	private Button btnDelete;

	@FXML
	private void add(ActionEvent event) {
		acessoAdd();
		showInfo();
	}

	@FXML
	private void delete(ActionEvent event) {
		acessoDelete();
		showInfo();
		btnUpdate.setVisible(false);
		btnDelete.setVisible(false);
		btnAdd.setVisible(true);
	}

	@FXML
	private void handleMouseClickAction(MouseEvent event) {
		
		Cliente cliente = tblCliente.getSelectionModel().getSelectedItem();
		txtID.setText("" + cliente.getIdCliente());
		txtNome.setText("" + cliente.getNome());
		txtApelido.setText("" + cliente.getApelido());
		txtEmail.setText("" + cliente.getEmail());
		txtTelefone.setText("" + cliente.getTelefone());
		txtEndereco.setText("" + cliente.getEndereco());
		btnAdd.setVisible(false);
		btnUpdate.setVisible(true);
		btnDelete.setVisible(true);
	}

	@FXML
	private void update(ActionEvent event) {
		acessoUpdate();
		showInfo();
		btnAdd.setVisible(true);
		btnUpdate.setVisible(false);
		btnDelete.setVisible(false);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cboSexo.setItems(sexo);
		showInfo();
		btnUpdate.setVisible(false);
		btnDelete.setVisible(false);
		txtID.setVisible(false);

	}

	@Override
	public void acessoAdd() {
		// nome,genero,email,telefone,endereco
		Cliente cliente = new Cliente();
		Utilizador utilizador = new Utilizador();
		utilizador.setIdUtilizador(ControllerLogin.idUsuario);
		cliente.setNome(txtNome.getText().toUpperCase());
		cliente.setApelido(txtApelido.getText().toUpperCase());
		cliente.setEmail(txtEmail.getText().toLowerCase());
		cliente.setTelefone(txtTelefone.getText().toUpperCase());
		cliente.setEndereco(txtEndereco.getText().toUpperCase());
		cliente.setGenero(cboSexo.getValue().toLowerCase());
		cliente.setUtilizador(utilizador);
		DaoCliente.addCliente(cliente);
		limpar();
		btnAdd.setVisible(true);
		btnUpdate.setVisible(false);
		btnDelete.setVisible(false);
	}

	@Override
	public void acessoIsRecorded() {

	}

	@Override
	public void acessoUpdate() {
		Cliente cliente = new Cliente();
		Utilizador utilizador = new Utilizador();
		utilizador.setIdUtilizador(ControllerLogin.idUsuario);
		cliente.setNome(txtNome.getText().toUpperCase());
		cliente.setApelido(txtApelido.getText().toUpperCase());
		cliente.setEmail(txtEmail.getText().toLowerCase());
		cliente.setTelefone(txtTelefone.getText().toUpperCase());
		cliente.setEndereco(txtEndereco.getText().toUpperCase());
		cliente.setGenero(cboSexo.getValue().toUpperCase());
		cliente.setUtilizador(utilizador);
		cliente.setIdCliente(Integer.parseInt(txtID.getText()));
		DaoCliente.updateCliente(cliente);
		limpar();

	}

	@Override
	public void acessoDelete() {
		Cliente cliente = new Cliente();
		cliente.setIdCliente(Integer.parseInt(txtID.getText()));
		DaoCliente.deleteCliente(cliente);
		limpar();
	}

	@Override
	public void limpar() {
		// nome,genero,email,telefone,endereco
		txtNome.setText("");
		txtTelefone.setText("");
		txtEndereco.setText("");
		txtEmail.setText("");
		txtApelido.setText("");

	}

	@Override
	public void showInfo() {
		// nome,genero,email,telefone,endereco

		List<Cliente> list = DaoCliente.getAllCliente();
		final ObservableList<Cliente> obserList = FXCollections.observableArrayList(list);
		colId.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
		colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		colApelido.setCellValueFactory(new PropertyValueFactory<>("apelido"));
		colSexo.setCellValueFactory(new PropertyValueFactory<>("genero"));
		colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		colEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
		colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
		colUtilizador.setCellValueFactory(new PropertyValueFactory<>("utilizador"));
		colDataRegisto.setCellValueFactory(new PropertyValueFactory<>("dataRegisto"));
		tblCliente.setItems(obserList);

	}

}
