package br.com.crearesistemas.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import br.com.crearesistemas.model.agrolog.OrdemTransporte;

@Transactional
public abstract class ServiceImpl<D> {

	private D dao;

	public D getDao() {
		return dao;
	}

	@Resource
	public void setDao(D dao) {
		this.dao = dao;
	}

	

}
