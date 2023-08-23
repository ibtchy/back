package com.sofrecom.cobli.controller.service;

import com.sofrecom.cobli.models.BRAME;
import com.sofrecom.cobli.models.ControleTravaux;
import com.sofrecom.cobli.models.DESAT;
import com.sofrecom.cobli.models.ESIMB;
import com.sofrecom.cobli.models.Graphic;
import com.sofrecom.cobli.models.Nropm;

public interface ServicesInterface {
	
	public abstract boolean isExisteEsimb(ESIMB esimb);
	public abstract boolean isExisteGrafic(Graphic esimb);
	public abstract boolean isExisteDesat(DESAT desat);
	public abstract boolean isExisteNropm(Nropm  nropm);
	public abstract boolean isExisteBrame(BRAME brame);
	
	public abstract String importData();
	public abstract boolean isExisteDesatR(DESAT desat);
	//public abstract boolean isExisteCTT(ControleTravaux ct);
}
