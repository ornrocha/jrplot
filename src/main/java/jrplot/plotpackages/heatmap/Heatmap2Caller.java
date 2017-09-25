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
package jrplot.plotpackages.heatmap;

import java.util.ArrayList;

import jrplot.plotpackages.common.figure.RPlotFigureMakerLibrary;
import jrplot.rbinders.components.interfaces.IDataMatrixDataLoader;
import jrplot.rbinders.components.matrix.DefaultMatrixContainer;
import pt.ornrocha.rtools.installutils.components.RPackageInfo;



public class Heatmap2Caller extends RPlotFigureMakerLibrary{

	
	public Heatmap2Caller(IDataMatrixDataLoader inputdata){
		super(inputdata);
	}
	


	@Override
	public void configure() throws Exception {
		
	}

	@Override
	public boolean needsConfiguration() {
		return false;
	}

	@Override
	protected ArrayList<RPackageInfo> requiredLibraries() {
		ArrayList<RPackageInfo> libs=new ArrayList<>();
        libs.add(RPackageInfo.define("gplots"));
		return libs;
	}

	@Override
	protected ArrayList<String> removeRInputObjects() {
        
		return null;
	}
	
	
	@Override
	protected String[] getFunctionsToExecutePlot() {
		return new String[]{"heatmap.2("+getDataREnvironmentSignature()+", col=redgreen(75), Rowv = F,cexRow = 0.7, cexCol=0.7, Colv =\"Rowv\", dendrogram=\"none\",scale =\"none\" , key=TRUE, symkey=FALSE, density.info=\"none\",trace=\"none\",keysize=0.5)"};
	}
	
	
	
	
	

/*	@Override   srtRow=45
	protected void executeFunction() throws Exception {
	
		String cmd="heatmap.2("+getDataREnvironmentSignature()+", col=topo.colors(75), Rowv = F, Colv =\"Rowv\", dendrogram=\"none\",scale =\"none\" , key=TRUE, symkey=FALSE, density.info=\"none\",trace=\"none\",srtRow=45)";
        runSingleCommandAndSaveFigure(cmd);
		
		
	}*/
	
	
	public static Heatmap2Caller load(double[][] data, ArrayList<String> rownames, ArrayList<String> columnnames){
		DefaultMatrixContainer matrix=DefaultMatrixContainer.create(data, rownames, columnnames);
		return new Heatmap2Caller(matrix);
	}
	
	public static Heatmap2Caller load(int[][] data, ArrayList<String> rownames, ArrayList<String> columnnames){
		DefaultMatrixContainer matrix=DefaultMatrixContainer.create(data, rownames, columnnames);
		return new Heatmap2Caller(matrix);
	}


	public static Heatmap2Caller load(IDataMatrixDataLoader data){
		return new Heatmap2Caller(data);
	}



/*	@Override
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
