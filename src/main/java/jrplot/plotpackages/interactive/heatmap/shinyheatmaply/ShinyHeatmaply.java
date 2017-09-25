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
package jrplot.plotpackages.interactive.heatmap.shinyheatmaply;

import java.util.ArrayList;

import jrplot.plotpackages.interactive.shinywrapper.shinyfeature.IShinyAppFeature;
import jrplot.plotpackages.interactive.shinywrapper.shinyfeature.ShinyControlCenter;
import jrplot.plotpackages.interactive.shinywrapper.shinyfeature.ShinyLoadMode;
import jrplot.plotpackages.interactive.shinywrapper.uicomponents.page.UIShinyPageLayout;
import jrplot.rbinders.components.dataframe.DefaultDataframeContainer;
import jrplot.rbinders.components.dataframe.datatype.DoubleDataColumn;
import jrplot.rbinders.components.dataframe.datatype.rownames.StringColumnRowNames;
import jrplot.rbinders.components.interfaces.IDataFrameDataLoader;
import jrplot.rbinders.functioncallers.AbstractRFunctionCallerMultiDatasets;
import pt.ornrocha.logutils.MTULogLevel;
import pt.ornrocha.logutils.messagecomponents.LogMessageCenter;
import pt.ornrocha.rtools.connectors.RConnectorProperties;
import pt.ornrocha.rtools.installutils.components.RPackageInfo;

public class ShinyHeatmaply extends AbstractRFunctionCallerMultiDatasets<IDataFrameDataLoader> implements IShinyAppFeature{

	
	
	
	
	public ShinyHeatmaply(ArrayList<IDataFrameDataLoader> dataloaders) {
		super(dataloaders);
	}

	public ShinyHeatmaply(ArrayList<IDataFrameDataLoader> dataloaders, RConnectorProperties props) {
		super(dataloaders, props);
	}

	public ShinyHeatmaply(ArrayList<IDataFrameDataLoader> dataloaders, String ruserlibpath) {
		super(dataloaders, ruserlibpath);
	}

	public ShinyHeatmaply(IDataFrameDataLoader dataloader, String ruserlibpath) {
		super(dataloader, ruserlibpath);
	}


	public ShinyHeatmaply(IDataFrameDataLoader dataloader) {
		super(dataloader);
	}

	
	@Override
	protected ArrayList<RPackageInfo> requiredLibraries() {
		ArrayList<RPackageInfo> list=new ArrayList<>();
		list.add(RPackageInfo.define("plotly"));
		list.add(RPackageInfo.define("viridis"));
		list.add(RPackageInfo.define("jsonlite"));
		list.add(RPackageInfo.define("RColorBrewer"));
		list.add(RPackageInfo.define("readxl",RPackageInfo.define("cellranger")));
		list.add(RPackageInfo.define("DT"));
		list.add(RPackageInfo.define("xtable"));
		list.add(RPackageInfo.define("htmltools"));
		list.add(RPackageInfo.define("htmlwidgets"));
		list.add(RPackageInfo.define("dplyr"));
		list.add(RPackageInfo.define("heatmaply"));
		list.add(RPackageInfo.define("shinyHeatmaply"));
		
		return list;
	}
	

	@Override
	public ArrayList<RPackageInfo> getRequiredPackagesToShiny() {
		return requiredLibraries();
	}
	
	
	@Override
	public ShinyLoadMode getModeToLoadShiny() {
		return ShinyLoadMode.BUILTIN_PACKAGE_FUNCTION;
	}

	@Override
	public String getPackageShinyLaunchFunction() {

		StringBuilder str=new StringBuilder();

		if(dataloaders.size()==1)
			str.append("launch_heatmaply("+dataloaders.get(0).getDataRSignature()+")");

		else {
			
			str.append("launch_heatmaply(list(");
			for (int i = 0; i < dataloaders.size(); i++) {
			   IDataFrameDataLoader dataloader=dataloaders.get(i);
               str.append("'"+dataloader.getDataRSignature()+"'="+dataloader.getDataRSignature());
               if(i<(dataloaders.size()-1))
            	   str.append(", ");
			}
			str.append("))");
		}
		return str.toString();
	}

	
	
	@Override
	public void initRSessionEnvironment() throws Exception {
		initREnvironment();
	}

	

	@Override
	protected boolean needsConfiguration() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void configure() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public ArrayList<String> getShinyServerFunctions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UIShinyPageLayout getPageLayout() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getShinyStringUIScript() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getShinyStringServerScript() {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	protected ArrayList<String> removeRInputObjects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void executeFunction() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean removeRObjectsAfterExecution() {
		// TODO Auto-generated method stub
		return false;
	}

	
	


	public static void main(String[] args) throws Exception {
		
		LogMessageCenter.getLogger().setLogLevel(MTULogLevel.TRACE);
		
		DefaultDataframeContainer dataframe=new DefaultDataframeContainer("data1");
		
		StringColumnRowNames rownames=new StringColumnRowNames().addInputs("A1","B1","C1","D1");
		
		DoubleDataColumn col1=DoubleDataColumn.createFromInputs("C1", 1,0.3,0.5,0.4);
		DoubleDataColumn col2=DoubleDataColumn.createFromInputs("C2", 0.3,1,0.78,0.34);
		DoubleDataColumn col3=DoubleDataColumn.createFromInputs("C3", 0.5,0.78,1,0.54);
		DoubleDataColumn col4=DoubleDataColumn.createFromInputs("C4", 0.4,0.34,0.54,1);
		
		dataframe.addDataColumnToDataframe(col1)
		.addDataColumnToDataframe(col2)
		.addDataColumnToDataframe(col3)
		.addDataColumnToDataframe(col4)
		.addRowNames(rownames);
		
		
		DefaultDataframeContainer dataframe2=new DefaultDataframeContainer("data2");
		dataframe2.addDataColumnToDataframe(col2)
		.addDataColumnToDataframe(col3)
		.addDataColumnToDataframe(col1)
		.addDataColumnToDataframe(col4)
		.addRowNames(rownames);
		
		ArrayList<IDataFrameDataLoader> listdata=new ArrayList<>();
		listdata.add(dataframe);
		listdata.add(dataframe2);
		
		
		ShinyHeatmaply heatmap=new ShinyHeatmaply(listdata);
		//heatmap.setRuserlibpath("/home/orocha/discodados/R_LIB_testes");
		
		ShinyControlCenter cc=new ShinyControlCenter(heatmap);
		cc.run();

		
		
	}

	/*@Override
	public ArrayList<String> getFunctionsToEvaluatePreviously() {
		// TODO Auto-generated method stub
		return null;
	}*/
		
	
	
}
