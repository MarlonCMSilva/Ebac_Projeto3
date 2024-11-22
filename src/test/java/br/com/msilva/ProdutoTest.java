package br.com.msilva;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import br.com.msilva.dao.generics.jdbc.dao.IProdutoDAO;
import br.com.msilva.dao.generics.jdbc.dao.ProdutoDAO;

import br.com.msilva.domain.Produto;

public class ProdutoTest {

	private IProdutoDAO produtoDAO;

	@Test
	public void cadastrarTest() throws Exception {
		produtoDAO = new ProdutoDAO();
		Produto produto = new Produto();

		produto.setProdutoCodigo("15");
		produto.setProdutoNome("Celular");

		Integer qtd = produtoDAO.cadastrar(produto);
		assertTrue(qtd == 1);

		Produto produtoBD = produtoDAO.buscar("15");
		assertNotNull(produtoBD);
		assertEquals(produto.getProdutoCodigo(), produtoBD.getProdutoCodigo());
		assertEquals(produto.getProdutoNome(), produtoBD.getProdutoNome());

		Integer qtdDel = produtoDAO.excluir(produtoBD);
		assertNotNull(qtdDel == 1);
	}

	@Test
	public void buscatTest() throws Exception {

		produtoDAO = new ProdutoDAO();
		Produto produto = new Produto();

		produto.setProdutoCodigo("15");
		produto.setProdutoNome("Celular");

		Integer qtd = produtoDAO.cadastrar(produto);
		assertTrue(qtd == 1);

		Produto produtoBD = produtoDAO.buscar("15");
		assertNotNull(produtoBD);
		assertEquals(produto.getProdutoCodigo(), produtoBD.getProdutoCodigo());
		assertEquals(produto.getProdutoNome(), produtoBD.getProdutoNome());

		Integer qtdDel = produtoDAO.excluir(produtoBD);
		assertNotNull(qtdDel == 1);
	}

	@Test
	public void excluirTest() throws Exception {
		produtoDAO = new ProdutoDAO();
		Produto produto = new Produto();

		produto.setProdutoCodigo("15");
		produto.setProdutoNome("Celular");

		Integer qtd = produtoDAO.cadastrar(produto);
		assertTrue(qtd == 1);

		Produto produtoBD = produtoDAO.buscar("15");
		assertNotNull(produtoBD);
		assertEquals(produto.getProdutoCodigo(), produtoBD.getProdutoCodigo());
		assertEquals(produto.getProdutoNome(), produtoBD.getProdutoNome());

		Integer qtdDel = produtoDAO.excluir(produtoBD);
		assertNotNull(qtdDel == 1);
	}

	@Test
	public void buscarTodosTest() throws Exception {
		produtoDAO = new ProdutoDAO();

		Produto produto = new Produto();
		produto.setProdutoCodigo("15");
		produto.setProdutoNome("Celular");
		Integer qtd = produtoDAO.cadastrar(produto);
		assertTrue(qtd == 1);

		Produto produtos = new Produto();
		produtos.setProdutoCodigo("12");
		produtos.setProdutoNome("Celular2");
		Integer qtd2 = produtoDAO.cadastrar(produtos);
		assertTrue(qtd2 == 1);

		List<Produto> list = produtoDAO.buscarTodos();
		assertNotNull(list);
		assertEquals(2, list.size());

		int qtdDel = 0;
		for (Produto prd : list) {
			produtoDAO.excluir(prd);
			qtdDel++;
		}
		assertEquals(list.size(), qtdDel);

		list = produtoDAO.buscarTodos();
		assertEquals(list.size(), 0);
	}

	@Test
	public void atualizarTest() throws Exception {
		produtoDAO = new ProdutoDAO();

		Produto produto = new Produto();
		produto.setProdutoCodigo("15");
		produto.setProdutoNome("Celular");
		Integer qtd = produtoDAO.cadastrar(produto);
		assertTrue(qtd == 1);

		Produto produtoBD = produtoDAO.buscar("15");
		assertNotNull(produtoBD);
		assertEquals(produto.getProdutoCodigo(), produtoBD.getProdutoCodigo());
		assertEquals(produto.getProdutoNome(), produtoBD.getProdutoNome());

		produtoBD.setProdutoCodigo("20");
		produtoBD.setProdutoNome("Atualizar Teste");
		Integer qtdUpdate = produtoDAO.atualizar(produtoBD);
		assertTrue(qtdUpdate == 1);

		Produto produtoBD1 = produtoDAO.buscar("15");
		assertNull(produtoBD1);

		Produto produtoBD2 = produtoDAO.buscar("20");
		assertNotNull(produtoBD2);
		assertEquals(produtoBD.getId(), produtoBD2.getId());
		assertEquals(produtoBD.getProdutoCodigo(), produtoBD2.getProdutoCodigo());
		assertEquals(produtoBD.getProdutoNome(), produtoBD2.getProdutoNome());

		List<Produto> list = produtoDAO.buscarTodos();
		for (Produto prd : list) {
			produtoDAO.excluir(prd);
		}
	}

}
