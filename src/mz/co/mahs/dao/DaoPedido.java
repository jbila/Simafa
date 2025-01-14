package mz.co.mahs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import mz.co.mahs.conection.Conexao;
import mz.co.mahs.models.Cliente;
import mz.co.mahs.models.FormasDePagamento;
import mz.co.mahs.models.Pedido;
import mz.co.mahs.models.Utilizador;

public class DaoPedido {

	static Alert alertErro = new Alert(AlertType.ERROR);
	static Alert alertInfo = new Alert(AlertType.INFORMATION);
	private static final String INSERT = "INSERT INTO tbl_pedido(idCliente,idUtilizador,idFormasPagamento,status,valorDoPedido,dataRegisto) VALUES(?,?,?,?,?,?)";
	private static final String LIST = "select * from tbl_pedido order by idPedido desc";
	private static final String DELETE = "{CALL ps_Pedido(?)}";
	private static final String UPDATE = "UPDATE tbl_pedido set idCliente=?,idUtilizador=?,idFformasDepagamento=?,estado=?,valorDoPedido=? WHERE idPedido=?";

	private static Connection conn = null;
	private static PreparedStatement stmt;
	// private static CallableStatement cs=null;
	private static ResultSet rs = null;

	public static int add(Pedido pedido) {
		 int lastId=0;
		try {
			LocalDate localDate = LocalDate.now();
			String dataRegisto = DateTimeFormatter.ofPattern("yyy-MM-dd").format(localDate);

			conn = Conexao.connect();
			stmt = conn.prepareStatement(INSERT);
			final PreparedStatement stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
			//cliente,utilizador,formasPagamento,estado,valorPedido
			stmt.setInt(1, pedido.getCliente().getIdCliente());
			stmt.setInt(2, pedido.getUtilizador().getIdUtilizador());
			stmt.setInt(3, pedido.getFormasDepagamento().getId());
			stmt.setBoolean(4, pedido.getEstado());
			stmt.setDouble(5, pedido.getValorPedido());
			stmt.setString(6, dataRegisto);
			stmt.executeUpdate();
			
			final ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
			   lastId = rs.getInt(1);
			  
			    
			}
			alertInfo.setHeaderText("Information");
			alertInfo.setContentText("pedido Feito ");
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
		   return lastId;
	}
//-------------------------------------------------------------------

	public static void delete(Pedido pedido) {

		try {
			conn = Conexao.connect();
			stmt = conn.prepareStatement(DELETE);
			stmt.setInt(1, pedido.getIdPedido());
			stmt.execute();
			alertInfo.setHeaderText("Information");
			alertInfo.setContentText("pedido Removida");
			alertInfo.showAndWait();

		} catch (SQLException e) {

			alertErro.setHeaderText("Erro");
			alertErro.setContentText("Erro ao eliminar a  pedido: " + e.getMessage());
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
	public static void update(Pedido pedido) {
		try {

			conn = Conexao.connect();
			stmt = conn.prepareStatement(UPDATE);

			stmt.setInt(1, pedido.getCliente().getIdCliente());
			stmt.setInt(2, pedido.getUtilizador().getIdUtilizador());
			stmt.setInt(3, pedido.getFormasDepagamento().getId());
			stmt.setBoolean(4, pedido.getEstado());
			stmt.setDouble(5, pedido.getValorPedido());
			stmt.setInt(6, pedido.getIdPedido());
			stmt.executeUpdate();

			alertInfo.setHeaderText("Information");
			alertInfo.setContentText("Updated ");
			alertInfo.showAndWait();
		}

		catch (SQLException ex) {
			alertErro.setHeaderText("Error");
			alertErro.setContentText("Erro ao actualizar a pedido: " + ex.getMessage());
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

	public static List<Pedido> getAll() {
		List<Pedido> pedidos = new ArrayList<>();
		try {
			conn = Conexao.connect();
			stmt = conn.prepareCall(LIST);
			rs = stmt.executeQuery();
			while (rs.next()) {

				Pedido pedido = new Pedido();// objecto principal

				Cliente cliente = new Cliente();// OBJECTO SECUNDARIO
				cliente.setNome(rs.getString("cliente"));

				Utilizador utilizador = new Utilizador();// OBJECTO SECUNDARIO
				utilizador.setUsername(rs.getString("utilizador"));

				FormasDePagamento formasDepagamento = new FormasDePagamento();// OBJECTO SECUNDARIO
				formasDepagamento.setNome(rs.getString("pagamento"));

				pedido.setIdPedido(rs.getInt("idPedido"));
				pedido.setDataRegisto(rs.getString("dataRegisto"));
				pedido.setEstado(rs.getBoolean("estado"));
				pedido.setFormasDepagamento(formasDepagamento);
				pedido.setCliente(cliente);
				pedido.setUtilizador(utilizador);
				pedidos.add(pedido);
			}

		} catch (SQLException ex) {
			alertErro.setHeaderText("Erro");
			alertErro.setContentText("Erro ao listar  Pedidos  " + ex.getMessage());
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

		return pedidos;
	}
//------------------------------------------------------------------------------------

	public static List<Pedido> search(int numero) {
		List<Pedido> pedidos = new ArrayList<>();
		try {
			conn = Conexao.connect();
			stmt = conn.prepareCall(LIST);
			rs = stmt.executeQuery();
			while (rs.next()) {

				Pedido pedido = new Pedido();// objecto principal

				Cliente cliente = new Cliente();// OBJECTO SECUNDARIO
				cliente.setNome(rs.getString("cliente"));

				Utilizador utilizador = new Utilizador();// OBJECTO SECUNDARIO
				utilizador.setUsername(rs.getString("utilizador"));

				FormasDePagamento formasDepagamento = new FormasDePagamento();// OBJECTO SECUNDARIO
				formasDepagamento.setNome(rs.getString("pagamento"));

				pedido.setIdPedido(rs.getInt("idPedido"));
				pedido.setDataRegisto(rs.getString("dataRegisto"));
				pedido.setEstado(rs.getBoolean("estado"));
				pedido.setFormasDepagamento(formasDepagamento);
				pedido.setCliente(cliente);
				pedido.setUtilizador(utilizador);
				pedidos.add(pedido);

			}

		} catch (SQLException ex) {
			alertErro.setHeaderText("Erro");
			alertErro.setContentText("Erro ao listar  Pedido  " + ex.getMessage());
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

		return pedidos;
	}
//----------------------------------------------------------------------------------------
}
