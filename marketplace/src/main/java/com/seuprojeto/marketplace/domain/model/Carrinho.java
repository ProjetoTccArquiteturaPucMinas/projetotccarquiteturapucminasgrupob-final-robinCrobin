package com.seuprojeto.marketplace.domain.model;

import java.math.BigDecimal;
import java.util.List;

public class Carrinho {

    private final List<ItemCarrinho> itens;

    public Carrinho(List<ItemCarrinho> itens) {
        this.itens = itens;
    }

    public List<ItemCarrinho> getItens() {
        return itens;
    }

    public ResumoCarrinho calcularResumo() {
        BigDecimal valorTotal = BigDecimal.ZERO;
        BigDecimal desconto = BigDecimal.ZERO;

        for (ItemCarrinho item : itens) {
            Produto produto = item.getProduto();

            valorTotal = valorTotal.add(produto.getPreco().multiply(new BigDecimal(item.getQuantidade())));

            if (produto.getCategoriaProduto() == CategoriaProduto.CAPINHA) {
                desconto = desconto.add(new BigDecimal("0.03"));
            }
            else if (produto.getCategoriaProduto() == CategoriaProduto.CARREGADOR) {
                desconto = desconto.add(new BigDecimal("0.05"));
            }
            else if (produto.getCategoriaProduto() == CategoriaProduto.FONE) {
                desconto = desconto.add(new BigDecimal("0.03"));
            }
            else if (produto.getCategoriaProduto() == CategoriaProduto.PELICULA) {
                desconto = desconto.add(new BigDecimal("0.02"));
            }
            else if (produto.getCategoriaProduto() == CategoriaProduto.SUPORTE) {
                desconto = desconto.add(new BigDecimal("0.02"));
            }
        }

        int totalUnidades = 0;
        for (ItemCarrinho item : itens) {
            totalUnidades += item.getQuantidade();
        }

        if (totalUnidades == 2) {
            desconto = desconto.add(new BigDecimal("0.05"));
        } else if (totalUnidades == 3) {
            desconto = desconto.add(new BigDecimal("0.07"));
        } else if (totalUnidades >= 4) {
            desconto = desconto.add(new BigDecimal("0.1"));
        }

        if (desconto.compareTo(new BigDecimal("0.25")) > 0) {
            desconto = new BigDecimal("0.25");
        }

        BigDecimal valorDesconto = valorTotal.multiply(desconto);

        return new ResumoCarrinho(valorTotal, valorDesconto);
    }
}
