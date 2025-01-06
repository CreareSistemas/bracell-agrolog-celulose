package br.com.crearesistemas.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.io.WKTWriter;

import oracle.jdbc.OracleConnection;
import oracle.spatial.geometry.JGeometry;
import oracle.spatial.util.WKT;
import oracle.sql.STRUCT;

@SuppressWarnings("deprecation")
public class GeometryOracleUtil {

	
	  public static STRUCT geometryToOracleStruct(Geometry geometry, Connection conn, int srid) throws Exception {
	        String wkt = geometryToWkt(geometry);
	        STRUCT oracleGeometry = (STRUCT)wkt2struct(wkt, conn);
	        return trocaSRID(oracleGeometry,conn, srid);
	    }
	  
	  public static oracle.sql.STRUCT trocaSRID(oracle.sql.STRUCT struct, Connection con, int srid) throws SQLException {
	        JGeometry geom = null;
	        try {
	            geom = JGeometry.load(struct);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        geom.setSRID(srid);
	        OracleConnection ocon = con.unwrap(OracleConnection.class);
	        oracle.sql.STRUCT dbObject = JGeometry.store(geom, ocon);

	        return dbObject;
	    }



	    public static Object wkt2struct(String wkt, Connection con) {
	        try {
	            JGeometry geom = new WKT().toJGeometry(wkt.getBytes());
	            OracleConnection ocon = con.unwrap(OracleConnection.class);
	            return JGeometry.store(geom, ocon);
	        } catch (Exception exc) {
	            RuntimeException rexc = new RuntimeException(exc);
	            rexc.setStackTrace(exc.getStackTrace());
	            throw rexc;
	        }
	    }

	  public static final int VALID_SRID = 4326;
	    public static String geometryToWkt(Geometry sourceGeom) {
	        String wktStr = null;
	        if (sourceGeom != null) {
	            WKTWriter wktWriter = new WKTWriter();
	            wktStr = wktWriter.write(sourceGeom);
	            try {
	                // necessario identificar a origem
	                CoordinateReferenceSystem sourceCrs = DefaultGeographicCRS.WGS84;
	                String wkt = convertWktToReferenceSystem(VALID_SRID, sourceCrs, wktStr);
	                if (wkt != null) {
	                    wktStr = wkt;
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	        return wktStr;
	    }
	    
	    
	    public static String convertWktToReferenceSystem(
	            int referenceSystemId, CoordinateReferenceSystem sourceCrs,
	            String wktText) throws Exception
	    {
	        if (sourceCrs == null) {
	            throw new NullPointerException("Sistema de referência de coordenadas não pode ser nulo");
	        }

	        if (referenceSystemId <= 0) {
	            throw new IllegalArgumentException("Sistema de referência de destino deve ser número maior que zero");
	        }

	        if (wktText == null || wktText.length() == 0) {
	            throw new IllegalArgumentException("Texto WKT não pode ser nulo ou vazio");
	        }

	        try {
	            WKTReader reader = new WKTReader(new GeometryFactory(new PrecisionModel()));
	            Geometry sourceGeometry = reader.read(wktText);

	            CoordinateReferenceSystem targetCrs = DefaultGeographicCRS.WGS84;

	            MathTransform transform = CRS.findMathTransform(sourceCrs, targetCrs, true);
	            Geometry targetGeometry = JTS.transform(sourceGeometry, transform);

	            return targetGeometry.toText();
	        } catch (Exception e) {
	            throw new Exception("Erro na conversão de SRID: " + e.getMessage(), e.getCause());
	        }
	    }





}
