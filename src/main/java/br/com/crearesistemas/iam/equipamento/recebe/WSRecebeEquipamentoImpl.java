package br.com.crearesistemas.iam.equipamento.recebe;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import br.com.crearesistemas.iam.WSBase;
import br.com.crearesistemas.model.EntidadeProprietaria;
import br.com.crearesistemas.model.Equipamento;
import br.com.crearesistemas.model.credencial.Credencial;
import br.com.crearesistemas.service.EntidadeProprietariaService;
import br.com.crearesistemas.service.EquipamentoService;

public class WSRecebeEquipamentoImpl extends WSBase implements WSRecebeEquipamento {
	private static final Logger logger = Logger.getLogger(WSRecebeEquipamentoImpl.class);

	@Resource
	private EquipamentoService equipamentoService;

	@Resource
	private EntidadeProprietariaService entidadeProprietariaService;

	@Override
	public ResponseRecebeEquipamento recebeEquipamento(RecebeEquipamento recebeEquipamento, Credencial credencial) {
		credencial.autenticacao();
		{
			logger.info("Pedido de envio de equipamentos recebido: " + recebeEquipamento + ".");
		}
		ResponseRecebeEquipamento saida = new ResponseRecebeEquipamento();
		List<Equipamento> listaEquipamentos = buscarEquipamentos(recebeEquipamento.getmId(), recebeEquipamento.getCustomerId());
		EntidadeProprietaria entidadeProprietaria = buscarEntidadeProprietaria(recebeEquipamento.getCustomerId());
		saida.setEquipamento(listaEquipamentos);
		saida.setEntidadeProprietaria(entidadeProprietaria);
		saida.setErro(WSBase.STATUS_NENHUM_ERRO_ENCONTRADO);
		{
			logger.info("Total de equipamentos a serem enviados: " + listaEquipamentos.size() + ".\n");
		}
		return saida;

	}

	private EntidadeProprietaria buscarEntidadeProprietaria(Long customerId) {
		return entidadeProprietariaService.buscarEntidadeProprietaria(customerId);
	}

	private List<Equipamento> buscarEquipamentos(long mId, Long customerId) {
		return equipamentoService.buscarEquipamentos(mId, customerId);
	}
}
