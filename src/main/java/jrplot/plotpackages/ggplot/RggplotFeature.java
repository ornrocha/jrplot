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
package jrplot.plotpackages.ggplot;

import java.util.ArrayList;

import jrplot.plotpackages.common.figure.RPlotFigureMakerLibrary;
import jrplot.rbinders.components.interfaces.IDataLoader;
import pt.ornrocha.rtools.installutils.components.RPackageInfo;

public abstract class RggplotFeature extends RPlotFigureMakerLibrary{

	
	//protected String plotsignature;
	protected ArrayList<String> inputelements=new ArrayList<>();

	public RggplotFeature(IDataLoader inputdata) {
		super(inputdata);
	}
	
	
	protected abstract ArrayList<RPackageInfo> requiredExtraLibraries();
	protected abstract String getPlotUniqueSignatureID();
	
	
	@Override
	protected ArrayList<RPackageInfo> requiredLibraries() {
		ArrayList<RPackageInfo> libs=new ArrayList<>();
		libs.add(RPackageInfo.define("ggplot2"));
		ArrayList<RPackageInfo> extralibs=requiredExtraLibraries();
		if(extralibs!=null)
			libs.addAll(extralibs);
		
		return libs;
		
	}
	
	
	@Override
	protected ArrayList<String> removeRInputObjects() {
	
		if(inputelements.size()>0)
			return inputelements;
		return null;
	}
	

}
