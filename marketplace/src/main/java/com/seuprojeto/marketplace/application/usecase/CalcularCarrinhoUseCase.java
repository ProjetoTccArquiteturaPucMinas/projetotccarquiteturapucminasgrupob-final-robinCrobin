package com.seuprojeto.marketplace.application.usecase;

import java.util.ArrayList;
import java.util.List;

import com.seuprojeto.marketplace.application.dto.SelecaoCarrinho;
import com.seuprojeto.marketplace.domain.model.Carrinho;
import com.seuprojeto.marketplace.domain.model.ItemCarrinho;
import com.seuprojeto.marketplace.domain.model.Produto;
import com.seuprojeto.marketplace.domain.model.ResumoCarrinho;
import com.seuprojeto.marketplace.domain.repository.ProdutoRepositorio;

public class CalcularCarrinhoUseCase {

    private final ProdutoRepositorio produtoRepositorio;

    public CalcularCarrinhoUseCase(ProdutoRepositorio produtoRepositorio) {
        this.produtoRepositorio = produtoRepositorio;
    }

    public ResumoCarrinho executar(List<SelecaoCarrinho> selecaoCarrinhos) {
        List<ItemCarrinho> itens = new ArrayList<>();
        for (SelecaoCarrinho selecao : selecaoCarrinhos) {
            Produto produto = produtoRepositorio.findById(selecao.getIdProduto())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + selecao.getIdProduto()));
            itens.add(new ItemCarrinho(produto, selecao.getQuantidade()));
        }

        return new Carrinho(itens).calcularResumo();
    }
}