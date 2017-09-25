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
package jrplot.rbinders.components.dataframe;

import java.io.IOException;
import java.util.ArrayList;

import org.math.R.Rsession;

import jrplot.rbinders.components.dataframe.datatype.DataColumn;
import jrplot.rbinders.components.dataframe.datatype.VariableType;
import jrplot.rbinders.components.dataframe.datatype.rownames.ColumnRowNames;
import jrplot.rbinders.components.interfaces.IDataFrameDataLoader;
import pt.ornrocha.ioutils.writers.MTUWriterUtils;
import pt.ornrocha.stringutils.MTUStringUtils;

public class DefaultDataframeContainer implements IDataFrameDataLoader{


	 @SuppressWarnings("rawtypes")
	 protected ArrayList<DataColumn> data;
	 protected ColumnRowNames rownames;
	 protected String uniqueid;
	 protected ArrayList<String> inputvarids;
	 protected String dataframevar=null;
	 protected int maxrowsize=0;
	 protected String dataframename;

	 
	 public DefaultDataframeContainer(){
		 data=new ArrayList<>();
		 inputvarids=new ArrayList<>();
		 createuniqueid();
	 }
	 
	 public DefaultDataframeContainer(ColumnRowNames rownames){
		 data=new ArrayList<>();
		 inputvarids=new ArrayList<>();
		 this.rownames=rownames;
		 createuniqueid();
	 }
	 
	 
	 
	 public DefaultDataframeContainer(String namedataframe){
		 data=new ArrayList<>();
		 inputvarids=new ArrayList<>();
		 this.dataframename=namedataframe;
		 createuniqueid();
	 }
	 
	 
	 
	 
	public void setDataframeName(String namedataframe) {
		this.dataframename=namedataframe;
	}
	
	@Override
	public String getDataframeName() {
		return dataframename;
	}
	 
	 

	@Override
	@SuppressWarnings("rawtypes")
	public void appendDataColumnToDataframe(DataColumn column){
		if(data==null)
			data=new ArrayList<>();
		data.add(column);
		if(column.getData().size()>maxrowsize)
			maxrowsize=column.getData().size();
	 }
	
	
	public DefaultDataframeContainer addDataColumnToDataframe(DataColumn column){
		appendDataColumnToDataframe(column);
		return this;
	}

	 private void createuniqueid(){
		 this.uniqueid=MTUStringUtils.shortUUID();
	 }
	 
	 public String getSignature(){
		 return uniqueid;
	 }
	 
	 public void setRowNames(ColumnRowNames rownames) {
		 this.rownames=rownames;
	 }
	 
	 public DefaultDataframeContainer addRowNames(ColumnRowNames rownames) {
		 setRowNames(rownames);
		 return this;
	 }
	

	@SuppressWarnings("rawtypes")
	@Override
	public void loadData(Rsession session) {
		
		ArrayList<String> elemsids=new ArrayList<>();
		
		for (DataColumn dataColumn : data) {
			String elemid=addDatatypeToRenvironment(dataColumn, session);
			inputvarids.add(elemid);
			elemsids.add(dataColumn.getColumnname());
		}
		
		if(dataframename!=null)
			dataframevar=dataframename;
		else
			dataframevar="dataframe"+uniqueid;
		session.silentlyEval(dataframevar+" <- data.frame("+buildDataframeInputs(elemsids)+")", true);
		if(rownames!=null) {
			String rownamesid=addRowNamesToRenvironment(rownames, session);
			session.silentlyEval("row.names("+dataframevar+") <- "+rownamesid);
			inputvarids.add(rownamesid);
		}
		
		unsetInputs(session);
		
		inputvarids=new ArrayList<>();
		inputvarids.add(dataframevar);
	}
	
	private String buildDataframeInputs(ArrayList<String> elemsids){
		
		StringBuilder str=new StringBuilder();
		for (int i = 0; i < elemsids.size(); i++) {
			str.append(elemsids.get(i)+" = "+inputvarids.get(i)+", ");
		}
		
		str.append("stringsAsFactors = FALSE");
		
		return str.toString();
	}
	
	
	@SuppressWarnings("rawtypes")
	private String addDatatypeToRenvironment(DataColumn dataColumn, Rsession session){
		
		String varid=dataColumn.getColumnname()+uniqueid;
		session.set(varid, dataColumn.getDataArray());
		return varid;
	}
	
	@SuppressWarnings("rawtypes")
	private String addRowNamesToRenvironment(ColumnRowNames rownames, Rsession session){
		
		String varid="rownames_"+uniqueid;
		session.set(varid, rownames.getRowNamesArray());
		return varid;
	}

	@Override
	public void unsetInputs(Rsession session) {
		if(inputvarids.size()>0)
			session.unset(inputvarids.toArray(new String[inputvarids.size()]));
		
	}


	@Override
	public String getDataRSignature() {
		return dataframevar;
	}


	@Override
	public ArrayList<VariableType> getTypeVariablesInDataFrame() {
         
		ArrayList<VariableType> res=new ArrayList<>();
		if(data.size()>0)
			for (int i = 0; i < data.size(); i++) {
				VariableType type=data.get(i).getType();
				if(!res.contains(type))
					res.add(type);
			}
		
		return res;
	}


	@Override
	public int getNumberColumns() {
		return data.size();
	}


	@Override
	public boolean containsVariable(String id) {
        
		for (int i = 0; i <data.size(); i++) {
			if(data.get(i).getColumnname().equals(id))
				return true;
		}
		return false;
	}


	@Override
	public Integer getColumnIndex(String id) {
        
		for (int i = 0; i < data.size(); i++) {
			if(data.get(i).getColumnname().equals(id))
				return i;
		}
		return null;
	}


	@Override
	public ArrayList<DataColumn> getDataColumns() {
		return data;
	}


	@Override
	public ColumnRowNames getRowNames() {
		return rownames;
	}


	@Override
	public void writeDatatoFile(String filepath) throws IOException {
		
		StringBuilder headerdata=new StringBuilder();
		
		if(rownames!=null)
			headerdata.append("\t");
		
		for (int i = 0; i < data.size(); i++) {
			headerdata.append(data.get(i).getColumnname());
			if(i<(data.size()-1))
				headerdata.append("\t");
		}
		headerdata.append("\n");
		
		StringBuilder out=new StringBuilder();
		out.append(headerdata);
		
		for (int i = 0; i < maxrowsize; i++) {
			
			if(rownames!=null)
				out.append(rownames.getNames().get(i)+"\t");
			
			for (int j = 0; j < data.size(); j++) {
				out.append(String.valueOf(data.get(j).getValueAt(i)));
				if(j<(data.size()-1))
					out.append("\t");
			}
			out.append("\n");
		}
		
		MTUWriterUtils.writeStringWithFileChannel(out.toString(), filepath, 0);
	}

	
	
	
	
	

}
