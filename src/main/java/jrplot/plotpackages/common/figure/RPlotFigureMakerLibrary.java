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
package jrplot.plotpackages.common.figure;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.apache.commons.io.FilenameUtils;

import hu.kazocsaba.imageviewer.ImageViewer;
import jrplot.rbinders.components.interfaces.IDataLoader;
import jrplot.rbinders.functioncallers.AbstractRFunctionCallerSingleDataset;
import pt.ornrocha.fileutils.MTUFileUtils;
import pt.ornrocha.logutils.messagecomponents.LogMessageCenter;
import pt.ornrocha.stringutils.MTUStringUtils;
import pt.ornrocha.systemutils.OSystemUtils;
import pt.ornrocha.timeutils.MTUTimeUtils;

public abstract class RPlotFigureMakerLibrary extends AbstractRFunctionCallerSingleDataset<IDataLoader>{
	
	
	protected String figurefilepath;
	protected int width=0;
	protected int height=0;
	protected int resolution=0;
	protected int pointsize=0;
	protected boolean transpbg=false;
	protected RFigureUnits unit=RFigureUnits.none;
	protected RFigureFormat fileformat=RFigureFormat.PNG;
	protected boolean showimage=false;
	

	public RPlotFigureMakerLibrary(IDataLoader inputdata) {
		super(inputdata);
	}

	
	public void setOutputFileformat(RFigureFormat fileformat) {
		this.fileformat = fileformat;
	}

	public void saveFigureFilepath(String filepath){
		this.figurefilepath=filepath;
	}
	
	public void saveFigureFilepath(String dir, String figurename) {
		if(figurename==null) {
			figurename=MTUStringUtils.shortUUID();
		}
		File f=new File(dir);
		if(!f.exists())
			f.mkdirs();
		this.figurefilepath=FilenameUtils.concat(dir, figurename);
	}
	
	
	
	public String getFigureFilepath(){
		return figurefilepath;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public RPlotFigureMakerLibrary addWidth(int width){
		setWidth(width);
		return this;
	}
	
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public RPlotFigureMakerLibrary addHeight(int height) {
		this.height = height;
		return this;
	}
	
	
	public RPlotFigureMakerLibrary showImage(){
		this.showimage=true;
		return this;
	}
	
	public void setShowImage(boolean show){
		this.showimage=show;
	}
	
	
	 public void setTransparentBackground(boolean transpbg) {
		this.transpbg = transpbg;
	}
	 
	 public RPlotFigureMakerLibrary addTransparentBackground(){
			setTransparentBackground(true);
			return this;
		}
	 
	public void setResolution(int resolution) {
		this.resolution = resolution;
	}
	
	public RPlotFigureMakerLibrary addResolution(int res){
		this.resolution=res;
		return this;
	}

	public void setPointSize(int pointsize) {
		this.pointsize = pointsize;
	}
	
	public RPlotFigureMakerLibrary addPointSize(int pointsize) {
		this.pointsize = pointsize;
		return this;
	}

	public void setUnit(RFigureUnits unit) {
		this.unit = unit;
	}
	
	public RPlotFigureMakerLibrary addUnit(RFigureUnits unit){
		setUnit(unit);
		return this;
	}

	public void saveToPNG(File f, String...commands){
		toGraphic(f, RFigureFormat.PNG, commands);
	}
	
	public void saveToJPEG(File f, String...commands){
		toGraphic(f, RFigureFormat.JPEG, commands);
	}
	
	public void saveToBMP(File f, String...commands){
		toGraphic(f, RFigureFormat.BMP, commands);
	}
	
	public void saveToPostScript(File f, String...commands){
		toGraphic(f, RFigureFormat.POSTSCRIPT, commands);
	}
	
	public void saveToTIFF(File f, String...commands){
		toGraphic(f, RFigureFormat.TIFF, commands);
	}
	
	public void saveToPNG(String filepath, String...commands){
		saveToPNG(new File(filepath), commands);
	}
	
	public void saveToJPEG(String filepath, String...commands){
		saveToJPEG(new File(filepath), commands);
	}
	
	public void saveToBMP(String filepath, String...commands){
		saveToBMP(new File(filepath), commands);
	}
	
	public void saveToTIFF(String filepath, String...commands){
		saveToTIFF(new File(filepath), commands);
	}
	
	public void saveToPostScript(String filepath, String...commands){
		toGraphic(new File(filepath), RFigureFormat.POSTSCRIPT, commands);
	}
	
	protected abstract String[] getFunctionsToExecutePlot();
	
	
	
	@Override
	protected void executeFunction() throws Exception {
		
		String[] cmds=getFunctionsToExecutePlot();
		if(cmds!=null){
			validateSavePath();
			if(cmds.length==1)
				runSingleCommandAndSaveFigure(cmds[0]);
			else
				runCommandsAndSaveFigure(cmds);
			
			if(figurefilepath!=null)
				LogMessageCenter.getLogger().toClass(getClass()).addInfoMessage("The plot was successfully created, the filepath is: "+figurefilepath);
			else
				LogMessageCenter.getLogger().toClass(getClass()).addInfoMessage("The plot was successfully created");
			
			if(showimage)
				showPlot();
		}
		
	}
	
	protected void validateSavePath(){
		if(figurefilepath==null){
			String filename="plot"+getSignature()+"_generated_"+MTUTimeUtils.getCurrentDateAndTime("yyyyMMdd_HH_mm_ss");
			String dirname="JRPlots";
			String dirpath=FilenameUtils.concat(OSystemUtils.getUserHomeDirectory(), dirname);
			File f =new File(dirpath);
			if(!f.exists())
				f.mkdirs();
			
			figurefilepath=FilenameUtils.concat(dirpath,filename);
			//showimage=true;
		}
	}
	
	
	protected void runCommandsAndSaveFigure(String... commands){

		switch (fileformat) {
		case JPEG:
			figurefilepath=MTUFileUtils.buildFilePathWithExtension(figurefilepath, "jpeg");
			saveToJPEG(figurefilepath, commands);
			break;
		case BMP:
			figurefilepath=MTUFileUtils.buildFilePathWithExtension(figurefilepath, "bmp");
			saveToBMP(figurefilepath, commands);
			break;
        case TIFF:
        	figurefilepath=MTUFileUtils.buildFilePathWithExtension(figurefilepath, "tiff");
        	saveToTIFF(figurefilepath, commands);
        	break;
        case POSTSCRIPT:
        	figurefilepath=MTUFileUtils.buildFilePathWithExtension(figurefilepath, "eps");
        	saveToPostScript(figurefilepath, commands);
        	break;
		default:
			figurefilepath=MTUFileUtils.buildFilePathWithExtension(figurefilepath, "png");
			saveToPNG(figurefilepath, commands);
		}
	}
	
	protected void runSingleCommandAndSaveFigure(String cmd){
		String[] cmds=new String[]{cmd};
		runCommandsAndSaveFigure(cmds);
	}

	protected void toGraphic(File f,RFigureFormat format, String... commands) {
	       
	        try {
	        	figurefilepath=OSystemUtils.validatePath(figurefilepath);

				rsession.eval(format.toString() + "(\""+figurefilepath+"\""+ buildPlotSaveParameters()+ ")");
				for (String command : commands) {
					rsession.eval(command);
				}
				rsession.silentlyEval("dev.off()");
				rsession.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	        
	        //rsession.receiveFile(f);
	        //rsession.rm("plotfile_" + h);
	        //rsession.removeFile(f.getName());
	  }

	private String buildPlotSaveParameters(){
		StringBuilder str=new StringBuilder();
		if(width!=0 && height!=0){
			str.append(", width="+String.valueOf(width)+", height="+String.valueOf(height));
			if(!unit.equals(RFigureUnits.none))
				str.append(", units ='"+unit.toString()+"'");
		}
		if(transpbg)
			str.append(", bg = \"transparent\"");
		
		if(resolution!=0 && resolution>0)
			str.append(", res="+String.valueOf(resolution));
		if(pointsize!=0 && pointsize>0)
			str.append(", pointsize ="+String.valueOf(pointsize));
		
		if(str.length()==0)
			return "";
		else
			return str.toString();
	}
	
	
	public ImageViewer getImageViewer() throws IOException{
		BufferedImage img=ImageIO.read(new File(figurefilepath));
		ImageViewer viewer=new ImageViewer(img,true);
		return viewer;
	}
	
	
	protected void showPlot() throws IOException{
		ImageViewer viewer=getImageViewer();
		JFrame frame=new JFrame("Plot generated by ggplot");
		frame.getContentPane().add(viewer.getComponent());
		frame.setSize(800, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public void endSession() {
		rsession.end();
	}
	
	@Override
	protected boolean removeRObjectsAfterExecution() {
		return true;
	}
}
