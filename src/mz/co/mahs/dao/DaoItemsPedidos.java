package mz.co.mahs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import mz.co.mahs.conection.Conexao;
import mz.co.mahs.models.Cliente;
import mz.co.mahs.models.ItemsPedidos;
import mz.co.mahs.models.Utilizador;
import mz.co.mahs.models.Pedido;

public class DaoItemsPedidos {

	static Alert alertErro = new Alert(AlertType.ERROR);
	static Alert alertInfo = new Alert(AlertType.INFORMATION);
	private static final String INSERT = "INSERT INTO tbl_ItemsPedidos(idProducto,idPedido,quantidade,precoUnitario) VALUES(?,?,?,?)";
	private static final String LIST = "select * from tbl_ItemsPedidos order by id desc";
	private static final String DELETE = "{CALL ps_Delete_User(?)}";
	private static final String UPDATE = "UPDATE tbl_ItemsPedidos  ";

	private static Connection conn = null;
	private static PreparedStatement stmt;
	private static CallableStatement cs = null;
	private static ResultSet rs = null;

	public static void add(List<ItemsPedidos> itemsPedidos) {
			List<ItemsPedidos> items=itemsPedidos;
		try {

			conn = Conexao.connect();
			stmt = conn.prepareStatement(INSERT);
		for (ItemsPedidos itemsPedidos2 : items) {
			stmt.setInt(1, itemsPedidos2.getProducto().getIdProducto());
			stmt.setInt(2, itemsPedidos2.getPedido().getIdPedido());
			stmt.setInt(3, itemsPedidos2.getQuantidade());
			stmt.setDouble(4, itemsPedidos2.getPrecoUnitario());
			stmt.executeUpdate();
				}
			alertInfo.setHeaderText("Information");
			alertInfo.setContentText("item adicionada ");
			alertInfo.showAndWait();
			//
		} catch (SQLException ex) {
			alertInfo.setHeaderText("Information");
			alertInfo.setContentText(" " + ex);
			alertInfo.showAndWait();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
	}
//-------------------------------------------------------------------

	public static void delete(ItemsPedidos itemsPedidos) {

		try {
			conn = Conexao.connect();
			stmt = conn.prepareStatement(DELETE);
			stmt.setInt(1, itemsPedidos.getIdItemsPedido());
			stmt.execute();
			alertInfo.setHeaderText("Information");
			alertInfo.setContentText("ItemVenda Removida");
			alertInfo.showAndWait();

		} catch (SQLException e) {

			alertErro.setHeaderText("Erro");
			alertErro.setContentText("Erro ao eliminar a  Item: " + e.getMessage());
			alertErro.showAndWait();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
	}

	// ------------------------------------------------------------------------------
	public static void update(ItemsPedidos itemsPedidos) {
		try {

			conn = Conexao.connect();
			stmt = conn.prepareStatement(UPDATE);
			conn = Conexao.connect();
			stmt = conn.prepareStatement(INSERT);
			stmt.setInt(1, itemsPedidos.getProducto().getIdProducto());
			stmt.setInt(2, itemsPedidos.getPedido().getIdPedido());
			stmt.setInt(3, itemsPedidos.getQuantidade());
			stmt.setDouble(4, itemsPedidos.getPrecoUnitario());
			stmt.setInt(6, itemsPedidos.getIdItemsPedido());
			stmt.executeUpdate();
			alertInfo.setHeaderText("Information");
			alertInfo.setContentText("Updated ");
			alertInfo.showAndWait();
		}

		catch (SQLException ex) {
			alertErro.setHeaderText("Error");
			alertErro.setContentText("Erro ao actualizar a items: " + ex.getMessage());
			alertErro.showAndWait();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
	}
//------------------------------------------------------------------------------

	public static List<ItemsPedidos> getAll() {
		List<ItemsPedidos> itemsPedidos = new ArrayList<>();
		try {
			conn = Conexao.connect();
			stmt = conn.prepareCall(LIST);
			rs = stmt.executeQuery();
			while (rs.next()) {
				Cliente cliente=new Cliente();
				cliente.setNome(rs.getString("nome"));
				Utilizador utilizador=new Utilizador();
				utilizador.setNome(rs.getString("utilizador"));
				Pedido pedido=new Pedido();
				pedido.setDataRegisto(rs.getString("dataRegisto"));
				
				ItemsPedidos itemsPedido=new ItemsPedidos();
				itemsPedido.setQuantidade(rs.getInt("quantidade"));
				itemsPedidos.add(itemsPedido);

			}

		} catch (SQLException ex) {
			alertErro.setHeaderText("Erro");
			alertErro.setContentText("Erro ao listar  items  " + ex.getMessage());
			alertErro.showAndWait();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
			try {
				stmt.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}

		}

		return itemsPedidos;
	}
	}
