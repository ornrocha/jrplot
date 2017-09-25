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
package jrplot.functioncallers.hclust;

import java.util.ArrayList;

import org.math.R.Rsession;

import jrplot.functioncallers.RFunctionCaller;
import pt.ornrocha.rtools.installutils.components.RPackageInfo;

public class RDistanceMatrixComputationFunction implements RFunctionCaller{

	public enum DistanceMethod{
		euclidean,
		maximum,
		manhattan,
		canberra,
		binary,
		minkowski;
	}
	
	private DistanceMethod method=DistanceMethod.euclidean;
	private boolean diag=false;
	private boolean upper=false;
	private String datareferenceid;
	private Rsession rsession;
	
	public RDistanceMatrixComputationFunction() {}
	
	public RDistanceMatrixComputationFunction(Rsession rsession, String datareferenceinrsession) {
		this.rsession=rsession;
		this.datareferenceid=datareferenceinrsession;
	}
	
	public RDistanceMatrixComputationFunction(Rsession rsession, String datareferenceinrsession, DistanceMethod method) {
		this(rsession,datareferenceinrsession);
		setMethod(method);
	}
	
	public RDistanceMatrixComputationFunction(DistanceMethod method) {
		setMethod(method);
	}
	
	
	public RDistanceMatrixComputationFunction setMethod(DistanceMethod method) {
		this.method=method;
		return this;
	}
	
	
	
	@Override
	public String executeFunction() {
		String nameoutput="dist_"+datareferenceid;
        rsession.eval(nameoutput+" <- dist( scale("+datareferenceid+"), method =\""+method.toString()+"\", "
        		+ "diag ="+(diag?"TRUE":"FALSE")+","
        				+ "  upper ="+(upper?"TRUE":"FALSE")+")");
		return nameoutput;
	}

	@Override
	public void setRsession(Rsession rsession) {
		this.rsession=rsession;
	}

	@Override
	public void setDataToAnalyse(String datareferenceinrsession) {
        this.datareferenceid=datareferenceinrsession;
		
	}

	@Override
	public ArrayList<RPackageInfo> requiredLibraries() {
		return null;
	}

}
