package br.com.crearesistemas.iam.mensagem.recebe;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import br.com.crearesistemas.iam.ResponseBase;
import br.com.crearesistemas.model.Mensagem;

/**
 * @author cneves, 19-Jun-2015
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseRecebeMensagem extends ResponseBase {

	private List<Mensagem> mensagens;

	public List<Mensagem> getMensagens() {
		return mensagens;
	}

	public void setMensagens(List<Mensagem> mensagens) {
		this.mensagens = mensagens;
	}

}
