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

import jrplot.rbinders.components.interfaces.IDataLoader;
import pt.ornrocha.logutils.messagecomponents.LogMessageCenter;
import pt.ornrocha.rtools.connectors.RConnectorProperties;


public abstract class AbstractRFunctionCallerSingleDataset<T extends IDataLoader> extends AbstractRFunctionsCaller{
	

	protected T dataloader;

	

	public AbstractRFunctionCallerSingleDataset(T dataloader){
		this.dataloader=dataloader;
	}
	
	public AbstractRFunctionCallerSingleDataset(T dataloader, RConnectorProperties props){
		this.dataloader=dataloader;
		setPropertiesToConnectRserve(props);
	}
	
	public AbstractRFunctionCallerSingleDataset(T dataloader, String ruserlibpath){
		this.dataloader=dataloader;
		setRuserlibpath(ruserlibpath);
	}
	
	
	protected abstract boolean needsConfiguration();
	protected abstract void configure() throws Exception;
    
	protected abstract ArrayList<String> removeRInputObjects();
	protected abstract void executeFunction() throws Exception;
	protected abstract boolean removeRObjectsAfterExecution();
	
	
	
	protected String getDataREnvironmentSignature(){
		return dataloader.getDataRSignature();
	}
	
	@Override
	protected void initREnvironment() throws Exception {
		
		boolean state=initRSession();
		if(state) {
			if(dataloader!=null)
					dataloader.loadData(rsession);
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
	
	
	
	
	
	
	/*@Override
	public void run(){

		try {
			rsession=openRsession();
			if(rsession!=null){
				boolean loadlibs=RConnector.loadRequiredLibraries(rsession,requiredLibraries(),ruserlibpath);
				if(loadlibs){
					if(dataloader!=null)
						dataloader.loadData(rsession);
					if(needsConfiguration())
						configure();

					if(useSwingWorkerToExecution()) {
								SwingWorker<Boolean, Void> worker=executeFunctionWithSwingWorker();
								worker.execute();

								while (!worker.isDone()) {
									Thread.sleep(100);
								}
							}
							else
					executeFunction();
					if(removeRObjectsAfterExecution())
						removeObjectsREnvironment();
					//rsession.end();
				}
			}
			else
				LogMessageCenter.getLogger().addCriticalErrorMessage("Rsession could not be opened");
		} catch (Exception e) {
			LogMessageCenter.getLogger().addCriticalErrorMessage("Error executing R Function: ", e);
		}


	}*/
	
	
	
	private void removeObjectsREnvironment(){
		dataloader.unsetInputs(rsession);
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
