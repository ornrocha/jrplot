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
package jrplot.plotpackages.common.interactive;

import java.util.ArrayList;

import jrplot.rbinders.components.interfaces.IDataLoader;
import jrplot.rbinders.functioncallers.AbstractRFunctionCallerSingleDataset;
import pt.ornrocha.rtools.installutils.components.RPackageInfo;

public abstract class RhtmlWidget<T extends IDataLoader> extends AbstractRFunctionCallerSingleDataset<IDataLoader>{

	
	protected ArrayList<String> inputelements=new ArrayList<>();
	
	
	public RhtmlWidget(T dataloader) {
		super(dataloader);
	}
	
	protected abstract String[] getFunctionsToExecutePlot();
	protected abstract ArrayList<RPackageInfo> loadRequiredLibraries();
	
	protected void removeAtTheEndFromREnvironment(String objectid) {
		this.inputelements.add(objectid);
	}


	@Override
	protected ArrayList<RPackageInfo> requiredLibraries() {
		ArrayList<RPackageInfo> libs=new ArrayList<>();
		libs.add(RPackageInfo.define("htmlwidgets"));
		if(loadRequiredLibraries()!=null)
			libs.addAll(loadRequiredLibraries());
		return libs;
	}
	
	
	
	@Override
	protected ArrayList<String> removeRInputObjects() {
		if(inputelements.size()>0)
			return inputelements;
		return null;
	}
	
	@Override
	protected void executeFunction() throws Exception {
		String[] functionstoexecute=getFunctionsToExecutePlot();
		for (int i = 0; i < functionstoexecute.length; i++) {
			rsession.eval(functionstoexecute[i]);
		}
		rsession.close();
		
	}


}
