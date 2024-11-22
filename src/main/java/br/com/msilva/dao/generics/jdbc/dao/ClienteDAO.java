package br.com.msilva.dao.generics.jdbc.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.msilva.dao.generics.jdbc.ConnectionFactory;
import br.com.msilva.domain.Cliente;


public class ClienteDAO implements IClienteDAO {

	@Override
	public Integer cadastrar(Cliente cliente) throws Exception {
		//Conexão com o bando de dados
		Connection connection = null;
		//Comando que será realizado no banco de dados.
		PreparedStatement stm = null;
		try {
			connection = ConnectionFactory.getConnection();
			String sql = getSqlInsert();
			stm = connection.prepareStatement(sql);
			adicionarParametroInsert(stm, cliente);
			return stm.executeUpdate();
		}catch (Exception e) {
			throw e;
		}finally {
			closeConnection(connection,stm,null);
		}
	}
	
	

	@Override
	public Integer atualizar(Cliente cliente) throws Exception {
		Connection connection = null;
		PreparedStatement stm = null;
		
		try {
			connection = ConnectionFactory.getConnection();
			String sql = getUpdate();
			stm = connection.prepareStatement(sql);
			adicionarParametrosUpdate(stm, cliente);
			return stm.executeUpdate();
		} catch(Exception e) {
			throw e;
		} finally {
				closeConnection(connection, stm, null);
		}
	}
	

	@Override
	public Cliente buscar(String codigo) throws Exception {
		Connection connection = null;
		PreparedStatement stm = null;
		
		ResultSet rs = null;
		Cliente cliente = null;
		
		try {
			connection = ConnectionFactory.getConnection();
			String sql = getSqlSelect();
			stm = connection.prepareStatement(sql);
			adicionarParametroSelect(stm, codigo);
			rs = stm.executeQuery(); 
			
			if(rs.next()) {
				cliente = new Cliente();
				Long id = rs.getLong("ID");
				String nome = rs.getString("NOME");
				String cd = rs.getString("CODIGO");
				cliente.setId(id);
		    	cliente.setNome(nome);
		    	cliente.setCodigo(cd);
			}
		}catch(Exception e) {
			throw e;
		}finally {
			closeConnection(connection,stm,rs);
		}
		return cliente;
	}
	
	
	private String getSqlInsert() {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO TB_CLIENTE(ID, CODIGO, NOME) ");
		sb.append("VALUES(nextval('SQ_CLIENTE'),?,?)");
		return sb.toString();
	}


	private void adicionarParametroInsert(PreparedStatement stm, Cliente cliente)throws SQLException {
		stm.setString(1,cliente.getCodigo());
		stm.setString(2,cliente.getNome());
	}


	private String getUpdate() {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE TB_CLIENTE ");
		sb.append("SET NOME = ?, CODIGO = ? ");
		sb.append("WHERE ID = ?");
		return sb.toString();
	}
	
	private void adicionarParametrosUpdate(PreparedStatement stm, Cliente cliente)throws SQLException {
		stm.setString(1, cliente.getNome());
		stm.setString(2, cliente.getCodigo());
		stm.setLong(3, cliente.getId());	
	}
	
	private String getSqlSelect() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM TB_CLIENTE ");
		sb.append("WHERE CODIGO = ?");
		return sb.toString();
	}

	private void adicionarParametroSelect(PreparedStatement stm, String codigo)throws SQLException {
		stm.setString(1, codigo);
	}

	

	@Override
	public List<Cliente> buscarTodos() throws Exception {
		Connection connection = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
    	List<Cliente> list = new ArrayList<>();
    	Cliente cliente = null;
		
    	try {
    		connection = ConnectionFactory.getConnection();
			String sql = getSqlSelectAll();
			stm = connection.prepareStatement(sql);
			rs = stm.executeQuery();
			
			while(rs.next()) {
				cliente = new Cliente();
		    	Long id = rs.getLong("ID");
		    	String nome = rs.getString("NOME");
		    	String cd = rs.getString("CODIGO");
		    	cliente.setId(id);
		    	cliente.setNome(nome);
		    	cliente.setCodigo(cd);
		    	list.add(cliente);
			}
    	}catch(Exception e) {
    		throw e;
    	}finally {
    		closeConnection(connection,stm,rs);
    	}
    	return list;
	}


	@Override
	public Integer excluir(Cliente cliente) throws Exception {
		Connection connection = null;
		PreparedStatement stm = null;
		
		try {
			connection = ConnectionFactory.getConnection();
			String sql = getSetDelete();
			stm = connection.prepareStatement(sql);
			adicionarParametroDelete(stm, cliente);
			return stm.executeUpdate();
		}catch(Exception e){
			throw e;
		}finally {
			closeConnection(connection,stm,null);
		}
		
	}
	

	private String getSqlSelectAll() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM TB_CLIENTE");
		return sb.toString();
	}



	private void adicionarParametroDelete(PreparedStatement stm, Cliente cliente) throws SQLException {
		stm.setString(1, cliente.getCodigo());
	}

	private String getSetDelete() {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM TB_CLIENTE ");
		sb.append("WHERE CODIGO = ?");
		return sb.toString();
	}

	
	private void closeConnection(Connection connection, PreparedStatement stm, ResultSet rs) {
		try {
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
			if (stm != null && !stm.isClosed()) {
				stm.close();
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	
}
