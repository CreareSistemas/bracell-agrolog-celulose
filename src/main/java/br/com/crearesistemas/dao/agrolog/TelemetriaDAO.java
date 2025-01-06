package br.com.crearesistemas.dao.agrolog;

import java.util.List;

import br.com.crearesistemas.model.agrolog.Telemetria;


public interface TelemetriaDAO {

	public List<Telemetria> buscarTelemetrias(long mId);

	public boolean insereTelemetria(Telemetria telemetria);

	public boolean destinationHasRecord(Long id);

	public boolean atualizaTelemetria(Telemetria telemetria);
	

}
