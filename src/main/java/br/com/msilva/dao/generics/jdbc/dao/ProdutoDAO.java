package br.com.msilva.dao.generics.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.msilva.dao.generics.jdbc.ConnectionFactory;
import br.com.msilva.domain.Produto;

public class ProdutoDAO implements IProdutoDAO {

	@Override
	public Integer cadastrar(Produto produto) throws Exception {
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			connection = ConnectionFactory.getConnection();
			String sql = getSqlInsert();
			stm = connection.prepareStatement(sql);
			adicionarParametroInsert(stm, produto);
			return stm.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			closeConnection(connection, stm, null);
		}
	}

	@Override
	public Integer atualizar(Produto produto) throws Exception {
		Connection connection = null;
		PreparedStatement stm = null;

		try {
			connection = ConnectionFactory.getConnection();
			String sql = getUpdate();
			stm = connection.prepareStatement(sql);
			adicionarParametrosUpdate(stm, produto);
			return stm.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			closeConnection(connection, stm, null);
		}
	}

	@Override
	public Produto buscar(String codigo) throws Exception {
		Connection connection = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		Produto produto = null;

		try {
			connection = ConnectionFactory.getConnection();
			String sql = getSqlSelect();
			stm = connection.prepareStatement(sql);
			adicionarParametroSelect(stm, codigo);
			rs = stm.executeQuery();

			if (rs.next()) {
				produto = new Produto();
				Long id = rs.getLong("ID");
				String nome = rs.getString("NOME");
				String cd = rs.getString("CODIGO");
				produto.setId(id);
				produto.setProdutoNome(nome);
				produto.setProdutoCodigo(cd);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			closeConnection(connection, stm, rs);
		}
		return produto;
	}

	@Override
	public List<Produto> buscarTodos() throws Exception {
		Connection connection = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		List<Produto> list = new ArrayList<>();
		Produto produto = null;

		try {
			connection = ConnectionFactory.getConnection();
			String sql = getSqlSelectAll();
			stm = connection.prepareStatement(sql);
			rs = stm.executeQuery();

			while (rs.next()) {
				produto = new Produto();
				Long id = rs.getLong("ID");
				String nome = rs.getString("NOME");
				String cd = rs.getString("CODIGO");
				produto.setId(id);
				produto.setProdutoNome(nome);
				produto.setProdutoCodigo(cd);
				list.add(produto);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			closeConnection(connection, stm, rs);
		}
		return list;
	}

	private String getSqlSelectAll() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM TB_PRODUTO");
		return sb.toString();
	}

	@Override
	public Integer excluir(Produto produto) throws Exception {
		Connection connection = null;
		PreparedStatement stm = null;

		try {
			connection = ConnectionFactory.getConnection();
			String sql = getSetDelete();
			stm = connection.prepareStatement(sql);
			adicionarParametroDelete(stm, produto);
			return stm.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			closeConnection(connection, stm, null);
		}
	}

	private String getSqlInsert() {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO TB_PRODUTO(ID, CODIGO, NOME) ");
		sb.append("VALUES(nextval('SQ_PRODUTO'),?,?)");
		return sb.toString();
	}

	private void adicionarParametroInsert(PreparedStatement stm, Produto produto) throws SQLException {
		stm.setString(1, produto.getProdutoCodigo());
		stm.setString(2, produto.getProdutoNome());
	}

	private String getUpdate() {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE TB_PRODUTO ");
		sb.append("SET NOME = ?, CODIGO = ? ");
		sb.append("WHERE ID = ?");
		return sb.toString();
	}

	private void adicionarParametrosUpdate(PreparedStatement stm, Produto produto) throws SQLException {
		stm.setString(1, produto.getProdutoNome());
		stm.setString(2, produto.getProdutoCodigo());
		stm.setLong(3, produto.getId());
	}

	private String getSqlSelect() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM TB_PRODUTO ");
		sb.append("WHERE CODIGO = ?");
		return sb.toString();
	}

	private void adicionarParametroSelect(PreparedStatement stm, String codigo) throws SQLException {
		stm.setString(1, codigo);
	}

	private String getSetDelete() {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM TB_PRODUTO ");
		sb.append("WHERE CODIGO = ?");
		return sb.toString();
	}

	private void adicionarParametroDelete(PreparedStatement stm, Produto produto) throws SQLException {
		stm.setString(1, produto.getProdutoCodigo());

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
			e1.printStackTrace();
		}

	}

}
