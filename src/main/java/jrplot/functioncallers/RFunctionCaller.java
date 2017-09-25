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
package jrplot.functioncallers;

import java.util.ArrayList;

import org.math.R.Rsession;

import pt.ornrocha.rtools.installutils.components.RPackageInfo;

public interface RFunctionCaller {

	/*
	 * return the variable name concerning to result of the function
	 */
	public String executeFunction();
	
	/*
	 * set to which session the function its associated
	 */
	public void setRsession(Rsession rsession);
	
	/*
	 * add the identifier of data that will be used in function, these data must be loaded previously in R session
	 */
	public void setDataToAnalyse(String datareferenceinrsession);
	
	public ArrayList<RPackageInfo> requiredLibraries();
	
}
