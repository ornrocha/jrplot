/************************************************************************** 
 * Orlando Rocha (ornrocha@gmail.com)
 *
 * This is free software: you can redistribute it and/or modify 
 * it under the terms of the GNU Public License as published by 
 * the Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version. 
 * 
 * This code is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
 * GNU Public License for more details. 
 * 
 * You should have received a copy of the GNU Public License 
 * along with this code. If not, see http://www.gnu.org/licenses/ 
 *  
 */
package jrplot.rbinders.functioncallers;

import java.util.ArrayList;

import org.math.R.Rsession;

import pt.ornrocha.logutils.messagecomponents.LogMessageCenter;
import pt.ornrocha.rtools.connectors.RConnector;
import pt.ornrocha.rtools.connectors.RConnectorProperties;
import pt.ornrocha.rtools.installutils.RInstallTools;
import pt.ornrocha.rtools.installutils.components.RPackageInfo;
import pt.ornrocha.stringutils.MTUStringUtils;

public abstract class AbstractRFunctionsCaller implements Runnable{

	
	protected Rsession rsession;
	protected RConnectorProperties connectionprops;
	protected String ruserlibpath=null;
	protected String uniqueid;
	
	
	protected abstract ArrayList<RPackageInfo> requiredLibraries();
	protected abstract void initREnvironment() throws Exception;
	
	
	
	public void setPropertiesToConnectRserve(RConnectorProperties props){
		this.connectionprops=props;
	}
	
	public void setRuserlibpath(String ruserlibpath) {
		this.ruserlibpath=ruserlibpath;
	}
	
	
	protected String getSignature(){
		if(uniqueid==null)
			uniqueid=MTUStringUtils.shortUUID();
		return uniqueid;
	}
	
	
	protected boolean initRSession() throws Exception {
		
		rsession=openRsession();
		if(rsession!=null){
			return RConnector.loadRequiredLibraries(rsession,requiredLibraries(),ruserlibpath);
		}
		else 
			throw new Exception("Invalid Rsession, the current session is not opened");
	}
	
	
	public Rsession getRsession() throws Exception{
		if(rsession==null)
			rsession=openRsession();
		return rsession;
	}
	
	
	@SuppressWarnings("static-access")
	protected Rsession openRsession() throws Exception{
		String Rpath = null;
		
		if(RConnector.isSessionOn()){
			return RConnector.getSession();
		}
		else{
		
			Rpath = RInstallTools.getSystemR_HOME();
	
			if(Rpath!=null && !Rpath.isEmpty()){
				if(connectionprops!=null) {
					if(connectionprops.getRUserFolderPath()!=null)
						ruserlibpath=connectionprops.getRUserFolderPath();
					return RConnector.startInstance(connectionprops).getSession();
				}
				else{
					if(ruserlibpath!=null){
						return RConnector.startLocalInstance(ruserlibpath).getSession();
					}
					
					return RConnector.getSessionTryMode();
				}
			}
			LogMessageCenter.getLogger().toClass(getClass()).addCriticalErrorMessage("No installation of R was detected, please install R first. ");
			return null;
		}
	}
	
	public String getRuserLibsPath() {
		return ruserlibpath;
	}

}
