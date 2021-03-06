package org.casadocodigo.loja.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotBlank
	private String title;
	@NotBlank
	@Lob
	private String description;
	@Min(30)
	private int pages;
	@ElementCollection
	private List<Price> prices = new ArrayList<Price>();
	@NotNull
	@DateTimeFormat
	private Date releaseDate;
	private String summaryPath;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getPages() {
		return pages;
	}
	public void setPages(int pages) {
		this.pages = pages;
	}
	public List<Price> getPrices() {
		return prices;
	}
	public void setPrices(List<Price> prices) {
		this.prices = prices;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getSummaryPath() {
		return summaryPath;
	}
	public void setSummaryPath(String summaryPath) {
		this.summaryPath = summaryPath;
	}
	
	public String toString() {
		StringBuilder n = new StringBuilder();
		for (Price i : this.getPrices()) {
			n.append(i.toString());
		}
		return "[ ID = '" +this.getId()+ "', Título = '" +this.getTitle()+
				"', Descrição = '" +this.getDescription()+
				"', Páginas = '" +this.getPages()+ "', Lançamento = '" +this.getReleaseDate()+
				"', Sumário = '" +this.getSummaryPath()+ "'] Preços:" +n.toString();
	}

	public BigDecimal priceFor(BookType bookType) {
		return prices
				.stream()
				.filter(price -> price.getBookType().equals(bookType))
				.findFirst().get().getValue();
	}
}
