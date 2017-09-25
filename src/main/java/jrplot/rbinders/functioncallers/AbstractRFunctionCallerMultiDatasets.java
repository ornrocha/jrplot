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
import java.util.List;

import jrplot.rbinders.components.interfaces.IDataLoader;
import pt.ornrocha.logutils.messagecomponents.LogMessageCenter;
import pt.ornrocha.rtools.connectors.RConnectorProperties;


public abstract class AbstractRFunctionCallerMultiDatasets<T extends IDataLoader> extends AbstractRFunctionsCaller{
	

	protected List<T> dataloaders;
	

	public AbstractRFunctionCallerMultiDatasets(List<T> dataloaders){
		this.dataloaders=dataloaders;
	}
	
	public AbstractRFunctionCallerMultiDatasets(T dataloader){
		this.dataloaders=new ArrayList<>();
		dataloaders.add(dataloader);
	}
	
	public AbstractRFunctionCallerMultiDatasets(List<T> dataloaders, RConnectorProperties props){
		this.dataloaders=dataloaders;
		setPropertiesToConnectRserve(props);
	}
	
	public AbstractRFunctionCallerMultiDatasets(ArrayList<T> dataloaders, String ruserlibpath){
		this.dataloaders=dataloaders;
		setRuserlibpath(ruserlibpath);
	}
	
	
	public AbstractRFunctionCallerMultiDatasets(T dataloader, String ruserlibpath){
		this.dataloaders=new ArrayList<>();
		dataloaders.add(dataloader);
		setRuserlibpath(ruserlibpath);
	}
	
	
	protected abstract boolean needsConfiguration();
	protected abstract void configure() throws Exception;
    
	protected abstract ArrayList<String> removeRInputObjects();
	protected abstract void executeFunction() throws Exception;
	protected abstract boolean removeRObjectsAfterExecution();
	
	
	
	protected String getDataREnvironmentSignature(int index){
		return dataloaders.get(index).getDataRSignature();
	}
	
	
	protected ArrayList<String> getDataREnvironmentSignatures(){
		ArrayList<String> res=new ArrayList<>();
		for (IDataLoader iDataLoader : dataloaders) {
			res.add(iDataLoader.getDataRSignature());
		}
		return res;
	}
	
	@Override
	protected void initREnvironment() throws Exception {
		
		boolean state=initRSession();
		if(state) {
			if(dataloaders!=null) {
				for (IDataLoader iDataLoader : dataloaders) {
					iDataLoader.loadData(rsession);
				}
			}
			if(needsConfiguration())
					configure();	
		}		
	}
	
	
	
	
	
	@Override
	public void run(){
		
		boolean valid=true;
		try {
			initREnvironment();
		} catch (Exception e) {
			valid=false;
			LogMessageCenter.getLogger().addCriticalErrorMessage("Error at initializing R environment : ", e);
		}
		
		if(valid) {
			try {
				executeFunction();
				if(removeRObjectsAfterExecution())
					removeObjectsREnvironment();
			} catch (Exception e) {
				LogMessageCenter.getLogger().addCriticalErrorMessage("Error executing R "+getClass().getSimpleName()+" Function: ", e);
			}
			
		}

	}
	
	
	
	private void removeObjectsREnvironment(){
		for (IDataLoader dataloader : dataloaders) {
			dataloader.unsetInputs(rsession);
		}
		if(removeRInputObjects()!=null && removeRInputObjects().size()>0){
			boolean r=rsession.unset(removeRInputObjects());
			if(r)
				LogMessageCenter.getLogger().toClass(getClass()).addDebugMessage("Objects were removed from R workspace:\n", removeRInputObjects(), "\n");
		}
	}
	
	
	/*protected void loadMatrixToREnvironment(){
		loadMatrixToREnvironment(datamatrix, rowids, columnids);
	}*/
	
	
	
/*	protected ArrayList<String> getBaseInputObjects(){
		ArrayList<String> objectstoremove=new ArrayList<>();
		if(datamatrixname!=null){
			objectstoremove.add(datamatrixname);
			if(rowmatrixnames!=null)
				objectstoremove.add(rowmatrixnames);
			if(colmatrixnames!=null)
				objectstoremove.add(colmatrixnames);
		}
		return objectstoremove;
	}*/
	
	
	/*@SuppressWarnings("static-access")
	private Rsession openRsession() throws Exception{
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
					return RConnector.getSessionTryMode();
				}
			}
			LogMessageCenter.getLogger().toClass(getClass()).addCriticalErrorMessage("No installation of R was detected, please install R first. ");
			return null;
		}
	}*/
	
	
	
	
	
/*	
	public static boolean loadRequiredLibraries(Rsession rsession, ArrayList<RPackageInfo> requiredlibs) throws Exception{
		return loadRequiredLibraries(rsession,requiredlibs, null);
	}
	
	
	
	
	public static boolean loadRequiredLibraries(Rsession rsession, ArrayList<RPackageInfo> requiredlibs, String RLibPath) throws Exception{
		
		
		boolean loadok=true;
		
		if(requiredlibs!=null){
			for (RPackageInfo packageInfo : requiredlibs) {
				
				if(RLibPath!=null && !RLibPath.isEmpty())
					loadok=RConnector.loadRPackage(rsession, packageInfo,RLibPath);
				else
					loadok=RConnector.loadRPackage(rsession, packageInfo);
				
				if(!loadok)
					return false;
			}
		}
		return loadok;
	}*/	
			/*for (Map.Entry<String, Boolean> lib: requiredlibs.entrySet()) {
				if(lib.getValue()){
					if(RLibPath!=null && !RLibPath.isEmpty())
						loadok=RConnector.loadBioconductorLibrary(rsession,lib.getKey(), RLibPath);
					else
						loadok= RConnector.loadBioconductorLibrary(rsession,lib.getKey());
				}
				else{
					if(RLibPath!=null && !RLibPath.isEmpty())
						loadok= RConnector.loadRLibrary(rsession,lib.getKey(), RLibPath);
					else
						loadok= RConnector.loadRPackage(rsession,lib.getKey());
				}
				
				if(!loadok)
					return loadok;
				
			}*/
	

}
