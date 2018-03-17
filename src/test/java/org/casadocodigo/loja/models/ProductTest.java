package org.casadocodigo.loja.models;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

public class ProductTest {
	
	private Product p = new Product();

	@Test
	private void ProductSemIDTeste() {
		
		p.setDescription("description");
		p.setPages(30);
		p.setSummaryPath("none");
		p.setTitle("none");
		
		ArrayList<Price> preços = pricePricesTeste();
		p.setPrices(preços);
		
		Date n = new Date();
		p.setReleaseDate(n);
		
		assertEquals("[ ID = 'null', Título = 'none', Descrição = 'description', Páginas = '30', Lançamento = '"+n+"', Sumário = 'none'] Preços: [ EBOOK: 2 ] [ PRINTED: 3 ] [ COMBO: 4 ]", p.toString());
				
	}
	
	private ArrayList<Price> pricePricesTeste() {
		Price preço_1 = new Price();
		preço_1.setBookType(BookType.EBOOK);
		preço_1.setValue(new BigDecimal(2.00));
		
		Price preço_2 = new Price();
		preço_2.setBookType(BookType.PRINTED);
		preço_2.setValue(new BigDecimal(3.00));
		
		Price preço_3 = new Price();
		preço_3.setBookType(BookType.COMBO);
		preço_3.setValue(new BigDecimal(4.00));
		
		ArrayList<Price> preços = new ArrayList<Price>();
		
		preços.add(preço_1);
		preços.add(preço_2);
		preços.add(preço_3);
		
		return preços;
	}
	
	public Product retornaProdutoSemID() {
		ProductSemIDTeste();
		if(p == null) { System.out.println("Vazio");}
		return p;
	}

}
