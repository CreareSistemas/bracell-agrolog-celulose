package br.com.crearesistemas.job;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import br.com.crearesistemas.service.ApontamentoService;


public class ParadasJob extends QuartzJobBean  {
	private static final Logger logger = Logger.getLogger(ParadasJob.class);
	
	@Autowired  private ApontamentoService apontamentoService;
	
	@Override
	protected void executeInternal(JobExecutionContext context)	throws JobExecutionException {
		//logger.info(" Inicio (INICIO ParadasJob): " + new Date() + "\n");		
		

		try {
			
			logger.info("*** === Inicio (INICIO Paradas): " + new Date() + "\n");		
			
			apontamentoService.processarApontamentosParada();
			
					
			logger.info("*** === tempo (FIM Paradas): \n");
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		try {
			
			logger.info("*** === Inicio (INICIO Paradas): " + new Date() + "\n");		
			
			apontamentoService.encerrarOrdens();
			
					
			logger.info("*** === tempo (FIM Paradas): \n");
		} catch(Exception e) {
			e.printStackTrace();
		}
		//logger.info("tempo (FIM ParadasJob): \n");		 
		 
	}
	

}