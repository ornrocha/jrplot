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
package jrplot.rbinders.components.interfaces;

import java.util.ArrayList;

import jrplot.rbinders.components.dataframe.datatype.DataColumn;
import jrplot.rbinders.components.dataframe.datatype.VariableType;
import jrplot.rbinders.components.dataframe.datatype.rownames.ColumnRowNames;

public interface IDataFrameDataLoader extends IDataLoader{

	public void setRowNames(ColumnRowNames rownames);
	public void appendDataColumnToDataframe(DataColumn column);
	public ArrayList<VariableType> getTypeVariablesInDataFrame();
	public int getNumberColumns();
	public boolean containsVariable(String id);
	public Integer getColumnIndex(String id);
	public ArrayList<DataColumn> getDataColumns();
	public ColumnRowNames getRowNames();
	public void setDataframeName(String namedataframe);
	public String getDataframeName();
}
