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
package jrplot.plotpackages.interactive.parallelcoords.parcoords;

import java.util.ArrayList;

import jrplot.plotpackages.interactive.shinywrapper.shinyfeature.IShinyAppFeature;
import jrplot.plotpackages.interactive.shinywrapper.shinyfeature.ShinyLoadMode;
import jrplot.plotpackages.interactive.shinywrapper.uicomponents.inputwidget.CheckboxInput;
import jrplot.plotpackages.interactive.shinywrapper.uicomponents.inputwidget.DownloadButton;
import jrplot.plotpackages.interactive.shinywrapper.uicomponents.inputwidget.SelectInput;
import jrplot.plotpackages.interactive.shinywrapper.uicomponents.inputwidget.SliderInput;
import jrplot.plotpackages.interactive.shinywrapper.uicomponents.layout.SidebarLayout;
import jrplot.plotpackages.interactive.shinywrapper.uicomponents.page.FluidPage;
import jrplot.plotpackages.interactive.shinywrapper.uicomponents.page.UIShinyPageLayout;
import jrplot.plotpackages.interactive.shinywrapper.uicomponents.panel.MainPanel;
import jrplot.plotpackages.interactive.shinywrapper.uicomponents.panel.SidebarPanel;
import jrplot.rbinders.components.dataframe.DefaultDataframeContainer;
import jrplot.rbinders.components.dataframe.datatype.DoubleDataColumn;
import jrplot.rbinders.components.dataframe.datatype.rownames.StringColumnRowNames;
import jrplot.rbinders.components.interfaces.IDataFrameDataLoader;
import jrplot.rbinders.functioncallers.AbstractRFunctionCallerMultiDatasets;
import pt.ornrocha.logutils.MTULogLevel;
import pt.ornrocha.logutils.messagecomponents.LogMessageCenter;
import pt.ornrocha.rtools.installutils.RInstallTools;
import pt.ornrocha.rtools.installutils.components.RPackageInfo;

public class ParcoordsWidgetMulti extends AbstractRFunctionCallerMultiDatasets<IDataFrameDataLoader> implements IShinyAppFeature{

	
	protected String plotsignature;
	protected boolean showrowname=false;
	protected ParcoordsBrushmode brushmode=ParcoordsBrushmode.STRUMS2D;
	protected boolean reorderable=false;
	protected boolean queue=true;
	protected ParcoordsColorScale colorscale=ParcoordsColorScale.D3CATEGORY10;
	protected boolean autorezise=true;
	protected Integer width=null; 
	protected Integer height=null;
	protected String colorby=null;
	protected ArrayList<String> functionstoeval=new ArrayList<>();
	
	public ParcoordsWidgetMulti(ArrayList<IDataFrameDataLoader> dataloaders) {
		super(dataloaders);
	}



	@Override
	public void configure() throws Exception {

	}

	@Override
	public boolean needsConfiguration() {
		return false;
	}



/*	protected String buildPlotCmds() {
		
		StringBuilder cmd=new StringBuilder();
		cmd.append(plotsignature+" <- "+getPlotConfiguration());
		return cmd.toString();
	}
	*/
	
	protected String getPlotConfiguration() {
		return null;
	}

	

	@Override
	protected boolean removeRObjectsAfterExecution() {
		return false;
	}
	
	

	@Override
	protected ArrayList<RPackageInfo> requiredLibraries() {
		ArrayList<RPackageInfo> libs=new ArrayList<>();
		libs.add(RPackageInfo.define("htmlwidgets"));
		libs.add(RPackageInfo.define("parcoords", "timelyportfolio/parcoords"));
		return libs;
	}
	
	@Override
	public void initRSessionEnvironment() throws Exception {
		initREnvironment();
		
	}
	
	@Override
	public ArrayList<RPackageInfo> getRequiredPackagesToShiny() {
		return requiredLibraries();
	}
	
	
	

	/*@Override
	public boolean useUIStringScript() {
		return false;
	}*/

	private String labelselectinput="Select Data"; 
	private String labelslider1="Width"; 
	private String labelslider2="Height";
	private String labelcheckbox1="Show Names";
	private String labeldownloadbutton="Download";
	private int sidepanelwidth=2;
	private int selectinputselectedindex=0;
	
	
	@Override
	public ShinyLoadMode getModeToLoadShiny() {
		return ShinyLoadMode.UIPAGELAYOUT_LISTSERVERFUNCTIONS;
	}
	
	public ParcoordsWidgetMulti setShinyLabelSlider1(String labelslider1) {
		this.labelslider1 = labelslider1;
		return this;
	}

	public ParcoordsWidgetMulti setShinyLabelSlider2(String labelslider2) {
		this.labelslider2 = labelslider2;
		return this;
	}

	public ParcoordsWidgetMulti setShinyLabelCheckbox1(String labelcheckbox1) {
		this.labelcheckbox1 = labelcheckbox1;
		return this;
	}

	public ParcoordsWidgetMulti setShinyLabelDownloadButton(String labeldownloadbutton) {
		this.labeldownloadbutton = labeldownloadbutton;
		return this;
	}
	
	public ParcoordsWidgetMulti setShinySidePanelWitdth(int width) {
		this.sidepanelwidth=width;
		return this;
	}
	
	/*@Override
	public ArrayList<String> getFunctionsToEvaluatePreviously() {
		return functionstoeval;
	}*/
	

	@Override
	public UIShinyPageLayout getPageLayout() {
		
		MainPanel mainpanel=MainPanel.createNew(ParcoordsPlotOutput.createNew("parcoords").setWidthPixels(1000).setHeightPixels(500));
		
		SidebarPanel sidepanel=new SidebarPanel();
		SelectInput selectdatacomp=SelectInput.createNew("datafr", labelselectinput,dataloaders, selectinputselectedindex);
		rsession.eval("datamap <- "+selectdatacomp.getMapChoicesList());
		rsession.unset(selectdatacomp.getChoices());
		
		sidepanel.addInputElement(selectdatacomp);
		
		
		sidepanel.addInputElement(SliderInput.createNew("width", labelslider1, 200, 8000, 800, 100));
		sidepanel.addInputElement(SliderInput.createNew("height", labelslider2, 200, 8000, 500, 100));
		
		if(dataloaders.get(selectinputselectedindex).getRowNames()!=null) {
			sidepanel.addInputElement(CheckboxInput.createNew("rownames", labelcheckbox1, true));
		}
		if(RInstallTools.pandocDirInstallation()!=null)
			sidepanel.addInputElement(DownloadButton.createNew("download", labeldownloadbutton));
		sidepanel.setWidth(sidepanelwidth);
		
					
		
		SidebarLayout sidebarlayout=new SidebarLayout(sidepanel, mainpanel);
		
		FluidPage page=new FluidPage().setTitle("Parallel Coordinates").addComponent(sidebarlayout);
		
		return page;
	}
	
	

	

	@Override
	public String getShinyStringUIScript() {
		return null;
	}

	
	
	@Override
	public ArrayList<String> getShinyServerFunctions() {

		//String data=dataloader.getDataRSignature();
		ArrayList<String> servercmds=new ArrayList<>();


		StringBuilder reactiveplot=new StringBuilder();

		reactiveplot.append("plot <- reactive({");
		//reactiveplot.append("parcoords(datamap[input$datafr], ");
		reactiveplot.append("parcoords(as.data.frame(datamap[[input$datafr]]), ");
		reactiveplot.append("height = input$height, "); // links ui slider height
		reactiveplot.append("width = input$width, "); // links ui slider widht
		
		if(dataloaders.get(selectinputselectedindex).getRowNames()!=null)
			reactiveplot.append("rownames = input$rownames,");
		else
			reactiveplot.append("rownames = TRUE,");
			
		reactiveplot.append("brushMode="+"\""+brushmode.getMode()+"\",");
		reactiveplot.append("reorderable ="+(reorderable?"T":"F")+",");
		//cmd.append("queue ="+(queue?"T":"F")+",");

		reactiveplot.append("color = "+getColorCmd());
		reactiveplot.append(")}");// close parcoords
		reactiveplot.append(")");// close reactive

		servercmds.add(reactiveplot.toString()); 



		servercmds.add("output$parcoords = renderParcoords(\n" + 
				"       plot()\n" + 
				"   )");

		if(RInstallTools.pandocDirInstallation()!=null)
			servercmds.add("output$download <- downloadHandler(\n" + 
					"     filename = function() {\n" + 
					"       paste0(\"parcoords_page\", \".html\")\n" + 
					"     },\n" + 
					"     \n" + 
					"     content = function(file) {\n" + 
					"       saveWidget(plot(), file)" + 
					"     }" + 
					"   )"); 


		return servercmds;

	}
	
	

	protected String getColorCmd() {
		
		StringBuilder str=new StringBuilder();
		if(colorby!=null) {
			str.append("list(colorBy =\""+colorby+"\","
					+ "colorScale ="+colorscale.getColorScaleCmd()+")");
		}
		else {
			str.append(colorscale.getColorScaleCmd());
		}
		
		return str.toString();
	}

	@Override
	public String getRuserLibsPath() {
		return ruserlibpath;
	}

	

	@Override
	public String getShinyStringServerScript() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPackageShinyLaunchFunction() {
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



		public static void main(String[] args) throws Exception {
		
		LogMessageCenter.getLogger().setLogLevel(MTULogLevel.TRACE);
		
		DefaultDataframeContainer dataframe=new DefaultDataframeContainer();
		
		StringColumnRowNames rownames=new StringColumnRowNames().addInputs("A1","B1","C1","D1");
		
		DoubleDataColumn col1=DoubleDataColumn.createFromInputs("C1", 1,0.3,0.5,0.4);
		DoubleDataColumn col2=DoubleDataColumn.createFromInputs("C2", 0.3,1,0.78,0.34);
		DoubleDataColumn col3=DoubleDataColumn.createFromInputs("C3", 0.5,0.78,1,0.54);
		DoubleDataColumn col4=DoubleDataColumn.createFromInputs("C4", 0.4,0.34,0.54,1);
		
		dataframe.appendDataColumnToDataframe(col1);
		dataframe.appendDataColumnToDataframe(col2);
		dataframe.appendDataColumnToDataframe(col3);
		dataframe.appendDataColumnToDataframe(col4);
		dataframe.setRowNames(rownames);
		
		//dataframe.writeDatatoFile("/home/orocha/MEOCloud/TRABALHO/R_Scripts/graficos/parallelcoordinates/toyparallelcoordata.csv");
		/*ParcoordsWidgetMulti parcoors=new ParcoordsWidgetMulti(dataframe);
		parcoors.setRuserlibpath("/home/orocha/R_Libs_test");
		ShinyControlCenter shinycc=new ShinyControlCenter(parcoors);
		Thread t1=new Thread(shinycc);
		t1.start();*/
		
	}



		


}
