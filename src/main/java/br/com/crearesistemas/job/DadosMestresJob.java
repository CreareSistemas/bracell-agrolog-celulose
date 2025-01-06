package br.com.crearesistemas.job;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import br.com.crearesistemas.service.DadosService;


public class DadosMestresJob extends QuartzJobBean  {
	private static final Logger logger = Logger.getLogger(DadosMestresJob.class);
	
	@Autowired  private DadosService dadosService;
	
	@Override
	protected void executeInternal(JobExecutionContext context)	throws JobExecutionException {
		//logger.info("Inicio (INICIO DadosJob): " + new Date() + "\n");		
		try {
		
			dadosService.consumirEventosParada();
			
			dadosService.consumirGruas();
			dadosService.consumirCaminhoes();
			
			dadosService.consumirMesas();
			dadosService.consumirLocais();		
			dadosService.consumirPilhas();
			
			dadosService.consumirProjetos();
			dadosService.consumirTalhoes();
			
			
			
			//logger.info("tempo (FIM DadosJob): \n");		 
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	

}