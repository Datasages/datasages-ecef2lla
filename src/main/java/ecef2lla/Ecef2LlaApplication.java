package ecef2lla;

import org.orekit.time.AbsoluteDate;
import org.orekit.time.TimeScalesFactory;
import org.orekit.models.earth.Geoid;
import org.orekit.models.earth.ReferenceEllipsoid;
import org.orekit.utils.Constants;
import org.orekit.utils.IERSConventions;
import org.orekit.frames.FactoryManagedFrame;
import org.orekit.frames.FramesFactory;
import org.orekit.forces.gravity.potential.GravityFieldFactory;
import org.orekit.forces.gravity.potential.NormalizedSphericalHarmonicsProvider;
import org.orekit.bodies.GeodeticPoint;
import org.orekit.data.DataProvidersManager;
import org.orekit.data.DirectoryCrawler;

import java.io.File;
import org.hipparchus.geometry.euclidean.threed.Vector3D;

import java.lang.Math;

// Sample Single Class implementation that demo



public class Ecef2LlaApplication {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		GeodeticPoint geoPoint = new GeodeticPoint(Math.toRadians(43.4723), Math.toRadians(-80.5449), 329);
		Vector3D convertedGeoPoint = geodeticToCartesian(geoPoint);
		System.out.println("Geodetic point to cartesian point: ");
		System.out.println(geoPoint);
		System.out.println(convertedGeoPoint);
		System.out.println("");
		

		Vector3D cartesianPoint = new Vector3D(761616,-4573255,4365946);
		GeodeticPoint convertedCartesianPoint = cartesianToGeodetic(cartesianPoint);
		System.out.println("Cartesian point to geodetic point: ");
		System.out.println(cartesianPoint);
		System.out.println(convertedCartesianPoint);
		System.out.println("");		
		
		

	}
	
	public static GeodeticPoint cartesianToGeodetic (Vector3D cartesianPoint) {
		File orekitData = new File("orekit-data");
		DataProvidersManager manager = DataProvidersManager.getInstance();
		manager.addProvider(new DirectoryCrawler(orekitData));		
		FactoryManagedFrame itrf = FramesFactory.getITRF(IERSConventions.IERS_2010, true);
		ReferenceEllipsoid referenceEllipsoid = ReferenceEllipsoid.getWgs84(itrf);
		NormalizedSphericalHarmonicsProvider gravity = GravityFieldFactory.getConstantNormalizedProvider(2,2);
		Geoid geoid = new Geoid(gravity, referenceEllipsoid);
		
		GeodeticPoint geodeticPoint = geoid.transform(cartesianPoint, itrf, new AbsoluteDate());
		return geodeticPoint;
		
	}
	
	public static Vector3D geodeticToCartesian (GeodeticPoint geodeticPoint) {
		File orekitData = new File("orekit-data");
		DataProvidersManager manager = DataProvidersManager.getInstance();
		manager.addProvider(new DirectoryCrawler(orekitData));		
		FactoryManagedFrame itrf = FramesFactory.getITRF(IERSConventions.IERS_2010, true);
		ReferenceEllipsoid referenceEllipsoid = ReferenceEllipsoid.getWgs84(itrf);
		NormalizedSphericalHarmonicsProvider gravity = GravityFieldFactory.getConstantNormalizedProvider(2,2);
		Geoid geoid = new Geoid(gravity, referenceEllipsoid);
		
		Vector3D vector3DPoint = geoid.transform(geodeticPoint);
		return vector3DPoint;
	}

}
