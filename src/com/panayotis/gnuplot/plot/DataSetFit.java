package com.panayotis.gnuplot.plot;

import com.panayotis.gnuplot.dataset.ArrayDataSet;
import com.panayotis.gnuplot.dataset.DataSet;

public class DataSetFit extends DataSetPlot {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9067977707076530463L;

	String eqn = "";
	int varsLength = 0;

	private int xcolumn = 0;

	private int ycolumn = 0;

	private int errcolumn = 0;

	private String varstring;

	public String getVarString() {
		return varstring;
	}

	private String colstring = "";
	private String depVarString = "";
	private boolean withPlot = false;

	private String[] vars;
	private String[] initialVals = null;

	private String functionName = null;
	private boolean includePlot = false;

	private int depVarCount;

	public String getFunctionName() {
		return functionName;
	}

	public String getEquation() {
		return eqn;
	}

	public DataSetFit(double[][] dataset, String equation, String[] variables,
			int depVarCount, boolean includePlot) {
		new DataSetFit(new ArrayDataSet(dataset), equation, variables,
				depVarCount, includePlot);
	}

	/**
	 * @param variables
	 * @param depVarCount
	 */
	private void makeVarstring(String[] variables, int depVarCount) {
		for (int i = depVarCount; i < varsLength; i++) {
			varstring = varstring.concat(variables[i]);
			// only add the ',' if it isn't the last one
			if (i < varsLength - 1)
				varstring = varstring.concat(",");
		}
	}

	public DataSetFit(float[][] dataset, String equation, String[] variables,
			int depVarCount, boolean includePlot) {
		new DataSetFit(new ArrayDataSet(dataset), equation, variables,
				depVarCount, includePlot);
	}

	public DataSetFit(int[][] dataset, String equation, String[] variables,
			int depVarCount, boolean includePlot) {
		new DataSetFit(new ArrayDataSet(dataset), equation, variables,
				depVarCount, includePlot);
	}

	public DataSetFit(long[][] dataset, String equation, String[] variables,
			int depVarCount, boolean includePlot) {
		new DataSetFit(new ArrayDataSet(dataset), equation, variables,
				depVarCount, includePlot);
	}

	public DataSetFit(DataSet dataset, String equation, String[] variables,
			int depVarCount, boolean includePlot) {
		super(dataset);
		eqn = equation;
		varstring = "";
		varsLength = variables.length;
		buildDepVarString(variables, depVarCount);
		setVars(variables);
		makeVarstring(variables, depVarCount);
		this.includePlot = includePlot;
		this.depVarCount = depVarCount;
		setColumns(1, 2);	}

	/**
	 * @param variables
	 * @param depVarCount
	 */
	private void buildDepVarString(String[] variables, int depVarCount) {
		for (int i = 0; i < depVarCount; i++) {
			depVarString = depVarString.concat(variables[i]);
			if (i < depVarCount-1)
				depVarString = depVarString.concat(",");
		}
	}

	public String getDepVarString() {
		return depVarString;
	}

	public void retrieveFitDefinition(StringBuffer buf) {
		buf.append(" " + functionName + "(" + depVarString + ") '-'");
		buf.append(" using " + colstring);
		buf.append(" via " + varstring);
		buf.append(NL);
	}

	public void retrieveDefinition(StringBuffer buf) {
		if (colstring != "")
			this.set("using", colstring);
		buf.append(definition);
		appendProperties(buf);
		if (smooth != null)
			buf.append(smooth.toString());
		if (style != null)
			style.appendProperties(buf);
	}

	public void setColumns(int xcol, int ycol) {
		colstring = xcol + ":" + ycol;
	}

	public void setColumns(int xcol, int ycol, int errcol) {
		colstring = xcol + ":" + ycol + ":" + errcol;
	}

	/**
	 * @return the boolean withPlot
	 */
	public boolean isWithPlot() {
		return withPlot;
	}

	/**
	 * @param withPlot
	 *            set withPlot
	 */
	public void setWithPlot(boolean withPlot) {
		this.withPlot = withPlot;
	}

	public void setFuntionName(String name) {
		this.functionName = name;
	}

	public String[] getInitialVals() {
		return initialVals;
	}

	public void setInitialVals(String[] initialVals) {
		this.initialVals = initialVals;
	}

	public String[] getVars() {
		return vars;
	}

	public void setVars(String[] vars) {
		this.vars = vars;
	}

	public boolean isIncludePlot() {
		return includePlot;
	}

	public void setIncludePlot(boolean includePlot) {
		this.includePlot = includePlot;
	}

}
