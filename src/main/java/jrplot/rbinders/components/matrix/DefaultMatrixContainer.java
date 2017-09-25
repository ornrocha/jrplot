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
package jrplot.rbinders.components.matrix;

import java.util.ArrayList;

import org.math.R.Rsession;

import jrplot.rbinders.components.interfaces.IDataMatrixDataLoader;
import jrplot.rbinders.components.matrix.datatype.DoubleDataMatrix;
import jrplot.rbinders.components.matrix.datatype.IntDataMatrix;
import pt.ornrocha.stringutils.MTUStringUtils;

public class DefaultMatrixContainer implements IDataMatrixDataLoader{

	private String uniqueid;
	private ArrayList<String> inputvarids;
	private String matrixvar=null;
	
	 @SuppressWarnings("rawtypes")
	private DataMatrix datamatrix=null;
	
	
	
	@SuppressWarnings("rawtypes")
	public DefaultMatrixContainer(DataMatrix data){
		this.datamatrix=data;
		inputvarids=new ArrayList<>();
		createuniqueid();
	}
	
	
	private void createuniqueid(){
		 this.uniqueid=MTUStringUtils.shortUUID();
	 }
	

	@Override
	public void loadData(Rsession session) {
		
		matrixvar="matrix"+uniqueid;
		inputvarids.add(matrixvar);
		
		session.set(matrixvar, datamatrix.getMatrix());
		
		if(datamatrix.getRownames()!=null){
			String rownames="rows_"+matrixvar;
			inputvarids.add(rownames);
			session.set(rownames, datamatrix.getRownames());
			session.silentlyEval("rownames("+matrixvar+")="+rownames);
		}
		if(datamatrix.getColumnnames()!=null){
			String columnames="columns_"+matrixvar;
			inputvarids.add(columnames);
			session.set(columnames, datamatrix.getColumnnames());
			session.silentlyEval("colnames("+matrixvar+")="+columnames);
		}
	}

	@Override
	public void unsetInputs(Rsession session) {
		if(inputvarids.size()>0)
			session.unset(inputvarids.toArray(new String[inputvarids.size()]));
		
		
	}


	@Override
	public String getDataRSignature() {
		return matrixvar;
		
	}
	
	public static DefaultMatrixContainer create(double[][] data, ArrayList<String> rownames, ArrayList<String> columnnames){
		DoubleDataMatrix matrix=new DoubleDataMatrix(data, rownames, columnnames);
		return new DefaultMatrixContainer(matrix);
	}
	
	public static DefaultMatrixContainer create(int[][] data, ArrayList<String> rownames, ArrayList<String> columnnames){
		IntDataMatrix matrix=new IntDataMatrix(data, rownames, columnnames);
		return new DefaultMatrixContainer(matrix);
	}


	@Override
	public void writeDatatoFile(String filepath) {
		// TODO Auto-generated method stub
		
	}

}
