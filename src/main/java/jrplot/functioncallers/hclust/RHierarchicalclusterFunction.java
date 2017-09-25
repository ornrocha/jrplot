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

import jrplot.functioncallers.hclust.RDistanceMatrixComputationFunction.DistanceMethod;
import pt.ornrocha.rtools.installutils.components.RPackageInfo;

public class RHierarchicalclusterFunction implements IRHClustFunction{

	public enum HClustMethod{
		
		WARDD{
			@Override
			public String getMethod() {
				return "ward.D";
			}
		},
		WARDD2{
			@Override
			public String getMethod() {
				return "ward.D2";
			}
		},
		SINGLE{
			@Override
			public String getMethod() {
				return "single";
			}
		},
		COMPLETE{
			@Override
			public String getMethod() {
				return "complete";
			}
		},
		AVERAGE{
			@Override
			public String getMethod() {
				return "average";
			}
		},
		MCQUITTY{
			@Override
			public String getMethod() {
				return "mcquitty";
			}
		},
		MEDIAN{
			@Override
			public String getMethod() {
				return "median";
			}
		},
		CENTROID{
			@Override
			public String getMethod() {
				return "centroid";
			}
		};

		
		
		@Override
		public String toString() {
			return getMethod();
		}
		
		public String getMethod() {
			return getMethod();
		}
		
		
	}
	
	
	
	private Rsession rsession;
	private String dataid;
	private HClustMethod hclustmethod=HClustMethod.COMPLETE;
	private RDistanceMatrixComputationFunction distancefunction=null;
	private boolean calculatedistances=false;
	
	public RHierarchicalclusterFunction(boolean calculatedistance) {
		this.calculatedistances=calculatedistance;
	}
	
	public RHierarchicalclusterFunction(Rsession rsession, String datareferenceinrsession, boolean calculatedistance) {
		setRsession(rsession);
		setDataToAnalyse(datareferenceinrsession);
		this.calculatedistances=calculatedistance;
	}
	
	public RHierarchicalclusterFunction(Rsession rsession, String datareferenceinrsession, RDistanceMatrixComputationFunction distancefunction) {
		setRsession(rsession);
		setDataToAnalyse(datareferenceinrsession);
		setDistanceFunction(distancefunction);
		calculatedistances=true;
	}
	
	
	public RHierarchicalclusterFunction(Rsession rsession, String datareferenceinrsession, DistanceMethod distancemethod, HClustMethod hclustmethod) {
		setRsession(rsession);
		setDataToAnalyse(datareferenceinrsession);
		this.distancefunction=new RDistanceMatrixComputationFunction(rsession,datareferenceinrsession, distancemethod);
		this.hclustmethod=hclustmethod;
		calculatedistances=true;
	}
	
	public  RHierarchicalclusterFunction setDistanceFunction(RDistanceMatrixComputationFunction distfunction) {
		distfunction.setRsession(rsession);
		distfunction.setDataToAnalyse(dataid);
		this.distancefunction=distfunction;
		this.calculatedistances=true;
		return this;
	}
	
	
	public RHierarchicalclusterFunction setHClustMethod(HClustMethod hclustmethod) {
		this.hclustmethod=hclustmethod;
		return this;
	}
	
	
	

	@Override
	public void setRsession(Rsession rsession) {
	    this.rsession=rsession;
	}
	
	
	@Override
	public void setDataToAnalyse(String datareferenceinrsession) {
		this.dataid=datareferenceinrsession;
		
	}
	
	
	@Override
	public String executeFunction() {
		
		String resultfunctionref="hclust_"+dataid;
		
		if(!calculatedistances) {
			String dissim="dissim_"+dataid;
			rsession.eval(dissim+" <- (1- cor("+dataid+"))/2");
	
			rsession.eval(resultfunctionref+" <- hclust(as.dist("+dissim+"))");
		}
		else {
			if(calculatedistances && distancefunction==null)
				distancefunction=new RDistanceMatrixComputationFunction(rsession,dataid);
			
			String distmeas=distancefunction.executeFunction();
			
			rsession.eval(resultfunctionref+" <- hclust("+distmeas+", method =\""+hclustmethod.getMethod()+"\")");
		}
		
		return resultfunctionref;
	}



	@Override
	public ArrayList<RPackageInfo> requiredLibraries() {
		return null;
	}



	

	

}
