package br.com.crearesistemas.job;


import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import br.com.crearesistemas.service.ApontamentoService;


public class ApontamentosJob extends QuartzJobBean  {
	private static final Logger logger = Logger.getLogger(ApontamentosJob.class);
	
	@Autowired  private ApontamentoService apontamentoService;
	
	@Override
	protected void executeInternal(JobExecutionContext context)	throws JobExecutionException {
		logger.info("*** Inicio (INICIO ApontamentosJob): " + new Date() + "\n");
		try {
			apontamentoService.processarApontamentosGruasCampo();
		} catch(Exception e) {
			e.printStackTrace();
		}
				 
		 
		try {
			apontamentoService.processarApontamentosFabrica();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			
			//apontamentoService.processarApontamentosParada();
					
		} catch(Exception e) {
			e.printStackTrace();
		}
		logger.info("*** FIM (FIM ApontamentosJob): " + new Date() + "\n");
	}
	

}