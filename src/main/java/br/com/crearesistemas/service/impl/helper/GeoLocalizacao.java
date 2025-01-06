package br.com.crearesistemas.service.impl.helper;

import org.geotools.referencing.GeodeticCalculator;

import com.vividsolutions.jts.geom.Coordinate;

public class GeoLocalizacao {
	
	private static final Coordinate coordenadaFabrica 	= new Coordinate(-48.80963802337647, -22.539433655400497);
	
	private static final Coordinate coordenadaPortoPelotas 	= new Coordinate(-52.334512, -31.782381);

	public static final Coordinate coordenadaForaDaFabrica = new Coordinate(-51.351695, -30.129087);
	
	public static final Coordinate coordenadaForaDoPorto = new Coordinate(-52.338464, -31.781094);
	
	
	public static Double distanciaEmMetrosFabrica(float longitude, float latitude) {
		// calcula a distancia linear do ativo ate a fabrica					
		Coordinate coordenadaAtivo 	= new Coordinate(longitude, latitude);
		Double distanciaLinearEmMetrosFabrica = distanciaEmMetros(coordenadaAtivo.x, coordenadaAtivo.y, coordenadaFabrica.x, coordenadaFabrica.y);
		return distanciaLinearEmMetrosFabrica;
	}
	
	public static double distanciaEmMetros(double long1, double lat1, double long2, double lat2) {
		GeodeticCalculator gc = new GeodeticCalculator();
		gc.setStartingGeographicPoint(long1, lat1);
		gc.setDestinationGeographicPoint(long2, lat2);
		return gc.getOrthodromicDistance();
	}

	public static double distanciaEmMetrosPortoPelotas(float longitude,	float latitude) {
		// calcula a distancia linear do ativo ate o porto					
		Coordinate coordenadaAtivo 	= new Coordinate(longitude, latitude);
		Double distanciaLinearEmMetrosFabrica = distanciaEmMetros(coordenadaAtivo.x, coordenadaAtivo.y, coordenadaPortoPelotas.x, coordenadaPortoPelotas.y);
		return distanciaLinearEmMetrosFabrica;
	}

	public static boolean validateCoords(Float latitude, Float longitude) {
		if  (latitude !=  null && longitude != null) {
			return (latitude >= -90 && latitude <= 90) && (longitude >= -180 && longitude <= 180); 
		} else {
			return false;	
		}
		
	}


}
