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
package jrplot.plotpackages.heatmap;

import java.util.ArrayList;

import jrplot.rbinders.components.interfaces.IDataLoader;
import jrplot.rbinders.functioncallers.AbstractRFunctionCallerSingleDataset;
import pt.ornrocha.rtools.installutils.components.RPackageInfo;



public class CallD3sHeatmat extends AbstractRFunctionCallerSingleDataset{


	public CallD3sHeatmat(IDataLoader dataloader) {
		super(dataloader);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected ArrayList<RPackageInfo> requiredLibraries() {
		// TODO Auto-generated method stub
		return null;
	}

	


	@Override
	public boolean needsConfiguration() {
		return false;
	}



	@Override
	protected void executeFunction() throws Exception {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected ArrayList<String> removeRInputObjects() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	protected boolean removeRObjectsAfterExecution() {
		// TODO Auto-generated method stub
		return false;
	}


/*	@Override
	protected boolean useSwingWorkerToExecution() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	protected SwingWorker<Boolean, Void> executeFunctionWithSwingWorker() {
		// TODO Auto-generated method stub
		return null;
	}*/

}
