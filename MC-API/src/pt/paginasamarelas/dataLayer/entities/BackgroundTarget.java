package pt.paginasamarelas.dataLayer.entities;

public class BackgroundTarget {
	
	private boolean mirrorGeographicTarget;
	private BackgrounEngineGeoTarget engineGeoTargets;
	
	
	public boolean isMirrorGeographicTarget() {
		return mirrorGeographicTarget;
	}
	public void setMirrorGeographicTarget(boolean mirrorGeographicTarget) {
		this.mirrorGeographicTarget = mirrorGeographicTarget;
	}
	public BackgrounEngineGeoTarget getEngineGeoTargets() {
		return engineGeoTargets;
	}
	public void setEngineGeoTargets(BackgrounEngineGeoTarget engineGeoTargets) {
		this.engineGeoTargets = engineGeoTargets;
	}
	

}
