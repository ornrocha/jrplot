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
package jrplot.plotpackages.ggplot.parallelcoord;

import java.util.ArrayList;

import jrplot.plotpackages.ggplot.RggplotFeature;
import jrplot.plotpackages.ggplot.parallelcoord.components.GgparcoordScale;
import jrplot.rbinders.components.interfaces.IDataFrameDataLoader;
import jrplot.rbinders.components.interfaces.IDataLoader;
import pt.ornrocha.rtools.installutils.components.RPackageInfo;

public abstract class AbstractGGPlotParallelCoordinates extends RggplotFeature{

	
	protected GgparcoordScale scale=GgparcoordScale.globalminmax;
	protected String parcoordindentifier=null;
	
	
	public AbstractGGPlotParallelCoordinates(IDataFrameDataLoader inputdata) {
		super(inputdata);
	}
	
	public AbstractGGPlotParallelCoordinates(IDataLoader inputdata, String parcoordindentifier) {
		super(inputdata);
		this.parcoordindentifier=parcoordindentifier;
	}
	
	public void setScale(GgparcoordScale scale){
		this.scale=scale;
	}


	@Override
	protected ArrayList<RPackageInfo> requiredLibraries() {
		ArrayList<RPackageInfo> libs=new ArrayList<>();
		libs.add(RPackageInfo.define("GGally", RPackageInfo.define("ggplot2")));
		ArrayList<RPackageInfo> extralibs=requiredExtraLibraries();
		if(extralibs!=null)
			libs.addAll(extralibs);

		return libs;
		
	}
	
	
	
}
