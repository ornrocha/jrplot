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

import java.io.IOException;
import java.util.ArrayList;

import jrplot.rbinders.components.dataframe.datatype.DataColumn;
import jrplot.rbinders.components.dataframe.datatype.VariableType;
import jrplot.rbinders.components.interfaces.IDataFrameDataLoader;
import pt.ornrocha.rtools.installutils.components.RPackageInfo;

public class BiclusterParallelCoordinates extends AbstractGGPlotParallelCoordinates{

	

	public BiclusterParallelCoordinates(IDataFrameDataLoader inputdata) {
		super(inputdata);
	}
	
	public BiclusterParallelCoordinates(IDataFrameDataLoader inputdata, String parcoordvarid) {
		super(inputdata,parcoordvarid);
	}

	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean needsConfiguration() {
		return false;
	}


	@Override
	protected String getPlotUniqueSignatureID() {
		return "bic_parcoord"+getSignature();
	}

	@Override
	protected String[] getFunctionsToExecutePlot() {

		String[] plcmd=new String[2];
		plcmd[0]=buildPlotCmds();
		plcmd[1]="print("+getPlotUniqueSignatureID()+")";
		return plcmd;
	}
	

	protected String buildPlotCmds() {
		
		StringBuilder cmd=new StringBuilder();
		
		String data=dataloader.getDataRSignature();
		
		inputelements.add(getPlotUniqueSignatureID());
		
		try {
			cmd.append(getPlotUniqueSignatureID()+"<- ggparcoord(data="+data+", "+getColumnsCount()+", ");

			if((parcoordindentifier!=null))
				//cmd.append("mapping=aes(color=as.factor("+parcoordindentifier+")) ");
				cmd.append("mapping=aes(color="+parcoordindentifier+") ");
			cmd.append(", scale = \""+scale.toString()+"\")");
			
			//if((parcoordindentifier!=null))
				//cmd.append("+scale_color_discrete(\""+parcoordindentifier+"\",labels="+data+"$"+parcoordindentifier+")");
			
			cmd.append("+ theme(legend.position = \"bottom\", legend.direction = \"horizontal\")");
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		return cmd.toString();	
	}
	
	private String getColumnsCount() throws IOException{
		
		IDataFrameDataLoader data=(IDataFrameDataLoader) dataloader;
		ArrayList<VariableType> typevars=data.getTypeVariablesInDataFrame();
		if(typevars.size()==1 && typevars.get(0).equals(VariableType.NUMERIC))
			return "columns = 1:"+data.getNumberColumns();
		else if(parcoordindentifier!=null){
			int varindex=data.getColumnIndex(parcoordindentifier);
			if(varindex==0)
				return "columns = 2:"+data.getNumberColumns();
			else if(varindex==(data.getNumberColumns()-1))
				return "columns = 1:"+(data.getNumberColumns()-1);
			else
				throw new IOException("Variable regarding to parallel coordinates must be in the beginning or end of dataframe");
		}
		else{
			
			ArrayList<Integer> numericindexes=new ArrayList<>();
			StringBuilder str=new StringBuilder();
			str.append("c(");
			
			ArrayList<DataColumn> cols=data.getDataColumns();
			
			for (int i = 0; i < cols.size(); i++) {
				if(cols.get(i).getType().equals(VariableType.NUMERIC))
					numericindexes.add(i);
			}
			
			
			for (int i = 0; i < numericindexes.size(); i++) {
				str.append(numericindexes.get(i));
				if(i<numericindexes.size()-1)
					str.append(", ");
			}
			str.append(")");
			
			return "columns = "+str.toString();
			
		}
			
		
		
	}

	@Override
	protected ArrayList<RPackageInfo> requiredExtraLibraries() {
		return null;
	}

	/*@Override
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
