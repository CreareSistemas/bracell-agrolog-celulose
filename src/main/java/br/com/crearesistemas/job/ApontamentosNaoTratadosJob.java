package br.com.crearesistemas.job;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import br.com.crearesistemas.service.ApontamentoService;


public class ApontamentosNaoTratadosJob extends QuartzJobBean  {
	private static final Logger logger = Logger.getLogger(ApontamentosNaoTratadosJob.class);
	
	@Autowired  private ApontamentoService apontamentoService;
	
	@Override
	protected void executeInternal(JobExecutionContext context)	throws JobExecutionException {
		try {
			
			logger.info("*** === Inicio (INICIO Apontamentos Campo Não tratados): " + new Date() + "\n");		
			
			apontamentoService.processarApontamentosCampoNaoTratados();
			
					
			logger.info("*** === tempo (FIM Apontamentos Campo Não tratados): \n");
		} catch(Exception e) {
			e.printStackTrace();
		}

						 
		 
	}
	

}