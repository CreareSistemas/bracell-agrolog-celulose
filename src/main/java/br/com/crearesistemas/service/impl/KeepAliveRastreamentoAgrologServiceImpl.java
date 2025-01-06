package br.com.crearesistemas.service.impl;


import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import br.com.crearesistemas.dao.agrolog.KeepAliveRastreamentoDAO;
import br.com.crearesistemas.dao.agrolog.KeepAliveRastreamentoJdbcDAO;
import br.com.crearesistemas.iam.rastreamento.envia.EnviaKeepAliveRastreamento;
import br.com.crearesistemas.model.agrolog.Dispositivo;
import br.com.crearesistemas.model.agrolog.Implemento;
import br.com.crearesistemas.model.agrolog.KeepAliveRastreamento;
import br.com.crearesistemas.service.DispositivoAgrologService;
import br.com.crearesistemas.service.ImplementoAgrologService;
import br.com.crearesistemas.service.KeepAliveRastreamentoAgrologService;
import br.com.crearesistemas.service.ServiceImpl;

@Service
public class KeepAliveRastreamentoAgrologServiceImpl extends ServiceImpl<KeepAliveRastreamentoDAO> implements KeepAliveRastreamentoAgrologService {

	private static final Logger logger = Logger.getLogger(KeepAliveRastreamentoAgrologServiceImpl.class);

	
	private static final Integer KEEPALIVE = 2;

	@Resource
	private DispositivoAgrologService dispositivoService;

	@Resource
	private ImplementoAgrologService implementoService;
	
	@Override
	@Resource(name = "rastreamentoAgrologJdbcDAO")
	public void setDao(KeepAliveRastreamentoDAO dao) {
		super.setDao(dao);
	}

	@Override
	public boolean enviarKeepAliveRastreamento(EnviaKeepAliveRastreamento entrada) {
		
		Dispositivo dipositivo = dispositivoService.selecionaPorIdentificador(entrada.getIdDispositivo());
		
		if (dipositivo != null) {
			Implemento implemento = implementoService.selecionaPorIdEquipamento(dipositivo.getEquipamento_id());
			
			if (implemento != null) {
				KeepAliveRastreamento rastreamento = new KeepAliveRastreamento();
				rastreamento.setDispositivo_id(dipositivo==null? null :dipositivo.getId());
				rastreamento.setData(entrada.getData());
				rastreamento.setImplemento_id(implemento==null? null :implemento.getId());
				rastreamento.setLatitude(entrada.getLatitude());
				rastreamento.setLongitude(entrada.getLongitude());
				rastreamento.setNumVersao(0);
				rastreamento.setDataHistorico(new Date());
				rastreamento.setTipoRastreamento(KEEPALIVE);
				rastreamento.setHodometro(entrada.getHodometro());
				rastreamento.setHorimetro(entrada.getHorimetro());
				rastreamento.setVelocidade(entrada.getVelocidade());
				rastreamento.setRpm(entrada.getRpm());
				rastreamento.setIgnicao(entrada.getIgnicao());
				rastreamento.setEstadoOperacional1(entrada.getEstadoOperacional1()); // 15 cheio, 14 vazio
				rastreamento.setAnguloDirecao(entrada.getAnguloDirecao());
				
				if (getDao().destinationHasRecord(implemento.getId())) {
					return getDao().atualizaRastreamento(rastreamento);
				} else {
					logger.info("----- Insere: " + entrada.getIdDispositivo());
					return getDao().insereKeepAliveRastreamento(rastreamento);		
				}
			}
		} else {
			logger.info("Dispositivo não encontrado: " + entrada.getIdDispositivo());
		}
		
		return false;
	}

	@Override
	public boolean inserirRastreamento(KeepAliveRastreamento rastreamento) {
		return super.getDao().insereKeepAliveRastreamento(rastreamento);
	}

}
