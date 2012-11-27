package com.panayotis.gnuplot.plot;

import com.panayotis.gnuplot.dataset.DataSet;

public class DataSetFit extends DataSetPlot {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9067977707076530463L;

	String eqn = "";
	final int varsLength;

	private int xcolumn;

	private int ycolumn;

	private int errcolumn;

	public DataSetFit(double[][] dataset, String equation, String[] variables) {
		super(dataset);
		eqn = equation;
		String varstring = "";
		varsLength = variables.length;
		for (int i=0;i<varsLength;i++){
			varstring.concat(variables[i]);
			// only add the ',' if it isn't the last one
			if (i<varsLength-1) varstring.concat(",");
		}
		this.set("via", varstring);
	}

	public DataSetFit(float[][] dataset, String equation, String[] variables) {
		super(dataset);
		eqn = equation;
		String varstring = "";
		varsLength = variables.length;
		for (int i=0;i<varsLength;i++){
			varstring.concat(variables[i]);
			// only add the ',' if it isn't the last one
			if (i<varsLength-1) varstring.concat(",");
		}
		this.set("via", varstring);
	}

	public DataSetFit(int[][] dataset, String equation, String[] variables) {
		super(dataset);
		eqn = equation;
		String varstring = "";
		varsLength = variables.length;
		for (int i=0;i<varsLength;i++){
			varstring.concat(variables[i]);
			// only add the ',' if it isn't the last one
			if (i<varsLength-1) varstring.concat(",");
		}
		this.set("via", varstring);
	}

	public DataSetFit(long[][] dataset, String equation, String[] variables) {
		super(dataset);
		eqn = equation;
		String varstring = "";
		varsLength = variables.length;
		for (int i=0;i<varsLength;i++){
			varstring.concat(variables[i]);
			// only add the ',' if it isn't the last one
			if (i<varsLength-1) varstring.concat(",");
		}
		this.set("via", varstring);
	}

	public DataSetFit(DataSet dataset, String equation, String[] variables) {
		super(dataset);
		eqn = equation;
		String varstring = "";
		varsLength = variables.length;
		for (int i=0;i<varsLength;i++){
			varstring.concat(variables[i]);
			// only add the ',' if it isn't the last one
			if (i<varsLength-1) varstring.concat(",");
		}
		this.set("via", varstring);
	}
	
	public void retrieveDefinition(StringBuffer buf) {
        buf.append(definition);
        appendProperties(buf);
        if (smooth!=null) buf.append(smooth.toString());
        if (style!=null) style.appendProperties(buf);
    }
	
	public void setColumns(int xcol, int ycol) {
		xcolumn = xcol;
		ycolumn = ycol;
	}

	public void setColumns(int xcol, int ycol, int errcol) {
		xcolumn = xcol;
		ycolumn = ycol;
		errcolumn = errcol;
	}

}
