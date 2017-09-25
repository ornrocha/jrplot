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
package jrplot.plotpackages.interactive.shinywrapper.shinyfeature;

import java.util.ArrayList;

import org.math.R.Rsession;

import jrplot.plotpackages.interactive.shinywrapper.uicomponents.page.UIShinyPageLayout;
import pt.ornrocha.rtools.installutils.components.RPackageInfo;

public interface IShinyAppFeature {
	
	
	public ArrayList<RPackageInfo> getRequiredPackagesToShiny();
	
	/**
	 * do not include "server <- function(input, output)" in list of functions
	 */
	public ArrayList<String> getShinyServerFunctions();
	
	public UIShinyPageLayout getPageLayout();
	//public boolean useUIStringScript();
	
	/**
	 * must include "ui <- " at beginning  
	 */
	public String getShinyStringUIScript();
	
	/**
	 * must include "server <- function(input, output)" at beginning  
	 */
	public String getShinyStringServerScript();
	
	
	public String getPackageShinyLaunchFunction();
	
	public void initRSessionEnvironment() throws Exception;
	public Rsession getRsession() throws Exception;
	public String getRuserLibsPath();
	public ShinyLoadMode getModeToLoadShiny();
	//public ArrayList<String> getFunctionsToEvaluatePreviously();

}
