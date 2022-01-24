package com.bruno.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bruno.cursomc.domain.Produto;
import com.bruno.cursomc.dto.ProdutoDTO;
import com.bruno.cursomc.resources.utils.URL;
import com.bruno.cursomc.services.ProdutoService;

@RestController
@RequestMapping(value="/produto")
public class ProdutoResource {

	@Autowired
	private ProdutoService service;
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Produto> listar(@PathVariable Integer id) {
		
		Produto obj = service.find(id);
		return ResponseEntity.ok().body(obj);
		
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(name = "nome", defaultValue = "0") String nome,
			@RequestParam(name = "categorias", defaultValue = "0") String categorias,
			@RequestParam(name = "page", defaultValue = "0") Integer page, 
			@RequestParam(name = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(name = "orderBy", defaultValue = "nome") String orderBy, 
			@RequestParam(name = "direction", defaultValue = "ASC") String direction) {
		String nomeDecode = URL.decodeParam(nome);
		List<Integer> ids = URL.decodeIntList(categorias); 
		Page<Produto> list = service.search(nomeDecode, ids, page, linesPerPage, orderBy, direction);
		Page<ProdutoDTO> listDto = list.map(obj -> new ProdutoDTO(obj));
		return ResponseEntity.ok().body(listDto);
		
	}
}
