package br.com.msilva;

import br.com.msilva.dao.generics.jdbc.dao.ClienteDAO;
import br.com.msilva.dao.generics.jdbc.dao.IClienteDAO;
import br.com.msilva.domain.Cliente;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class ClienteTest {
	
	private IClienteDAO clienteDAO;
	
	@Test
	public void cadastrarTest() throws Exception{
		 clienteDAO = new ClienteDAO();
		
		Cliente cliente = new Cliente();
		cliente.setCodigo("15");
		cliente.setNome("Marlon Silva");
		
		Integer qtd = clienteDAO.cadastrar(cliente);
		assertTrue(qtd == 1);
		
		Cliente clienteBD = clienteDAO.buscar("15");
		assertNotNull(clienteBD);
		assertEquals(cliente.getCodigo(), clienteBD.getCodigo());
		assertEquals(cliente.getNome(), clienteBD.getNome());
		
		Integer qtdDel = clienteDAO.excluir(clienteBD);
		assertNotNull(qtdDel == 1 );	
	}
	
	@Test
	public void buscatTest() throws Exception{
		clienteDAO = new ClienteDAO();
		Cliente cliente = new Cliente();
		cliente.setCodigo("15");
		cliente.setNome("Marlon Silva");
		
		Integer qtd = clienteDAO.cadastrar(cliente);
		assertTrue(qtd == 1);
		
		Cliente clienteBD = clienteDAO.buscar("15");
		assertNotNull(clienteBD);
		assertEquals(cliente.getCodigo(), clienteBD.getCodigo());
		assertEquals(cliente.getNome(), clienteBD.getNome());
		
		Integer qtdDel = clienteDAO.excluir(clienteBD);
		assertNotNull(qtdDel == 1 );	
	}
	
	@Test
	public void excluirTest() throws Exception{
		clienteDAO = new ClienteDAO();
		Cliente cliente = new Cliente();
		cliente.setCodigo("15");
		cliente.setNome("Marlon Silva");
		
		Integer qtd = clienteDAO.cadastrar(cliente);
		assertTrue(qtd == 1);
		
		Cliente clienteBD = clienteDAO.buscar("15");
		assertNotNull(clienteBD);
		assertEquals(cliente.getCodigo(), clienteBD.getCodigo());
		assertEquals(cliente.getNome(), clienteBD.getNome());
		
		Integer qtdDel = clienteDAO.excluir(clienteBD);
		assertNotNull(qtdDel == 1 );	
	}
	
	@Test
	public void buscarTodosTest() throws Exception{
		 clienteDAO = new ClienteDAO();
		 
		 Cliente cliente = new Cliente();
		 cliente.setCodigo("15");
		 cliente.setNome("Marlon Silva");
		 Integer qtd = clienteDAO.cadastrar(cliente);
		 assertTrue(qtd == 1);
		 
		 Cliente clientes = new Cliente();
		 clientes.setCodigo("12");
		 clientes.setNome("Teste");
		 Integer qtd2 = clienteDAO.cadastrar(clientes);
		 assertTrue(qtd2 == 1);
		 
		 
		 List<Cliente> list = clienteDAO.buscarTodos();
		 assertNotNull(list);
		 assertEquals(2, list.size());
		 
		 
		 int qtdDel = 0;
		 for(Cliente cli: list) {
			 clienteDAO.excluir(cli);
			 qtdDel ++;
		 }
		 assertEquals(list.size(), qtdDel);
		 
		 list = clienteDAO.buscarTodos();
		 assertEquals(list.size(), 0);
	}
	
	@Test
	public void atualizarTest() throws Exception{
		 clienteDAO = new ClienteDAO();
		 
		 Cliente cliente = new Cliente();
		 cliente.setCodigo("15");
		 cliente.setNome("Marlon Silva");
		 Integer qtd = clienteDAO.cadastrar(cliente);
		 assertTrue(qtd == 1);
		 
		 
		 Cliente clienteBD = clienteDAO.buscar("15");
		 assertNotNull(clienteBD);
		 assertEquals(cliente.getCodigo(), clienteBD.getCodigo());
		 assertEquals(cliente.getNome(), clienteBD.getNome());	
		
		 clienteBD.setCodigo("20");
		 clienteBD.setNome("Atualizar Teste");
		 Integer  qtdUpdate = clienteDAO.atualizar(clienteBD);
		 assertTrue(qtdUpdate == 1);
		 
		 Cliente clienteBD1 = clienteDAO.buscar("15");
		 assertNull(clienteBD1);
		 
		 Cliente clienteBD2 = clienteDAO.buscar("20");
		 assertNotNull(clienteBD2);
		 assertEquals(clienteBD.getId(), clienteBD2.getId());
		 assertEquals(clienteBD.getCodigo(), clienteBD2.getCodigo());
		 assertEquals(clienteBD.getNome(), clienteBD2.getNome());
		 
		 
		 List<Cliente> list = clienteDAO.buscarTodos();
		 for(Cliente cli: list) {
			 clienteDAO.excluir(cli);
		 }
	}
	
}
