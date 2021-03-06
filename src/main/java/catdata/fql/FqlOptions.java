package catdata.fql;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.function.Function;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import catdata.Pair;
import catdata.Unit;
import catdata.ide.Language;
import catdata.ide.Options;

public class FqlOptions extends Options {

	private static final long serialVersionUID = 1L;

	@Override
	public String getName() {
		return Language.FQL.toString();
	}
	
	@Override
	public int size() {
		return 0;
	} 
	
	public enum SQLKIND {
		H2, NATIVE, JDBC
	}

	public String instFlow_graph = "ISOMLayout";
	public String schFlow_graph = "ISOMLayout";
	public String schema_graph = "ISOMLayout";
	public String mapping_graph = "FRLayout";
	public String inst_graph = "ISOMLayout";
	public String trans_graph = "ISOMLayout";
	private String query_graph = "ISOMLayout";

	public SQLKIND sqlKind = SQLKIND.H2;

	public boolean ALLOW_NULLS = true;

	public String prelude = "CREATE DATABASE FQL; USE FQL; SET @guid := 0;";

	public String afterlude = "DROP DATABASE FQL; ";

	public String jdbcUrl = "jdbc:mysql://localhost:3306/?user=root";
	// public boolean useJDBC = false;
	public String jdbcClass = "com.mysql.jdbc.Driver";

	public int varlen = 255;

	public boolean VALIDATE = true;

	public int MAX_PATH_LENGTH = 8;

	public int MAX_DENOTE_ITERATIONS = 65536;

	public boolean ALL_GR_PATHS = false;

	public boolean VALIDATE_WITH_EDS = false;

	public boolean continue_on_error = false;

	public boolean ALLOW_INFINITES = false;

	public boolean schema_rdf = true;
	public boolean schema_graphical = true;
	public boolean schema_tabular = true;
	public boolean schema_textual = true;
	public boolean schema_denotation = true;
	public boolean schema_ed = true;
	public boolean schema_dot = true;
	public boolean schema_check = true;
	
	public boolean mapping_graphical = true;
	public boolean mapping_tabular = true;
	public boolean mapping_textual = true;
	public boolean mapping_ed = true;

	public boolean query_graphical = true;
	public boolean query_textual = true;

	public boolean inst_rdf = true;
	public boolean inst_graphical = true;
	public boolean inst_tabular = true;
	public boolean inst_textual = true;
	public boolean inst_joined = true;
	public boolean inst_gr = true;
	public boolean inst_obs = true;
	//public boolean limit_examples = true;
	public boolean inst_adom = true;
	public boolean inst_dot = true;
	
	public boolean allow_surjective = true;

	public boolean transform_graphical = true;
	public boolean transform_tabular = true;
	public boolean transform_textual = true;

	static String label1text = "If un-checked, the schemas in the viewer for queries will not contain any arrows.";
	static String label2text = "<html>The none and some options only shows declarations from the input program.<br>The all option shows all declarations including those generated by query composition.<br>The some option suppresses identity mappings.</html>";
	static String label3text = "<html>Instances in FQL must have globally unique keys.<br>To ensure this, FQL (and the generated SQL) will often compute new isomorphic instances with freshly chosen keys.<br>For debugging purposes it is sometimes useful to suppress this behavior.";
	private static final String label4text = "<html>By not computing source and target categories, and by not performing the check that a mapping takes path equivalences to path equivalences,<br>it is possible to compute Delta and SIGMA for infinite schemas.  See the employees example.</html>";
	private static final String label5text = "This is an internal consistency check that checks if intermediate categories generated by FQL do in fact obey the category axioms.";
	private static final String label6text = "Bounds the maximum length that the paths in a schema can be.";
	private static final String label7text = "Bounds the maximum number of iterations to compute the category denoted by a schema.";
	private static final String label8text = "Sets the size of Strings in the SQL output (used for ID columns and string columns).";
	static String labelMtext = "Allows multiple viewers for the same editor.";


	public static final String layout_prefix = "edu.uci.ics.jung.algorithms.layout.";

	private static final String[] layouts = { "CircleLayout", "FRLayout", "ISOMLayout",
			"KKLayout", "SpringLayout" };

	private final String layout_string = "<html>" +
			"" +
			"<li>CircleLayout: places vertices on a circle</li>" +
			"<li>FRLayout: Fruchterman-Reingold algorithm (force-directed)</li>" +
			"<li>SOMLayout: self-organizing map layout</li>" +
			"<li>KKLayout: Kamada-Kawai algorithm (tries to maintain distances)</li>" +
			"<li>SpringLayout: simple force-directed layout</li>" +
			"" +
			"</html>";
	
	@Override
	public Pair<JComponent, Function<Unit, Unit>> display() {
		JPanel general1 = new JPanel(new GridLayout(22, 1));
		JPanel general2 = new JPanel(new GridLayout(22, 1));

		JSplitPane generalsplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

		generalsplit.add(general1);
		generalsplit.add(general2);

		JCheckBox surjB = new JCheckBox("", allow_surjective);
		JLabel surjL = new JLabel("Allow surjective pi (dangerous):");
		general1.add(surjL);
		general2.add(surjB);
		surjL.setToolTipText("Allows the attribute mapping of pi migrations to be surjections.");
		
		JCheckBox coeB = new JCheckBox("", continue_on_error);
		JLabel coeL = new JLabel("Continue on errors (dangerous):");
		general1.add(coeL);
		general2.add(coeB);
		coeL.setToolTipText("FQL will attempt to continue on errors, and report these after opening the viewer.");

		JCheckBox ed = new JCheckBox("", VALIDATE_WITH_EDS);
		ed.setToolTipText("Validates Sigma/Delta migrations (on objects) using embedded dependencies");
		JLabel edL = new JLabel("Validate sigmas and deltas using EDs:");
		general1.add(edL);
		general2.add(ed);

		JCheckBox nullbox = new JCheckBox("", ALLOW_NULLS);
		nullbox.setToolTipText("Allow full sigma to create null attribute values");
		JLabel nullL = new JLabel("Allow SIGMA to create nulls (dangerous):");
		general1.add(nullL);
		general2.add(nullbox);

		JCheckBox gr = new JCheckBox("", ALL_GR_PATHS);
		gr.setToolTipText("Show all paths in category of elements");
		JLabel grL = new JLabel("Show all paths in elements view:");
		general1.add(grL);
		general2.add(gr);

	
		JCheckBox jcb0 = new JCheckBox("", ALLOW_INFINITES);
		JLabel label4 = new JLabel(
				"Allow some infinite schemas (dangerous):");
		label4.setToolTipText(label4text);
		general1.add(label4);
		general2.add(jcb0);

		JCheckBox jcb = new JCheckBox("", VALIDATE);
		JLabel label5 = new JLabel("Validate all categories:");
		label5.setToolTipText(label5text);
		general1.add(label5);
		general2.add(jcb);

		ButtonGroup group = new ButtonGroup();
		JRadioButton nativeButton = new JRadioButton("Naive");
		JRadioButton h2Button = new JRadioButton("H2");
		JRadioButton jdbcButton = new JRadioButton("JDBC");
		group.add(nativeButton);
		group.add(h2Button);
		group.add(jdbcButton);
		JPanel jdbcBox = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
		jdbcBox.add(nativeButton);
		jdbcBox.add(h2Button);
		jdbcBox.add(jdbcButton);
		switch (sqlKind) {
		case H2:
			group.setSelected(h2Button.getModel(), true);
			break;
		case JDBC:
			group.setSelected(jdbcButton.getModel(), true);
			break;
		case NATIVE:
			group.setSelected(nativeButton.getModel(), true);
			break;
		default:
			throw new RuntimeException();
		}

		JLabel jdbcLabel = new JLabel("SQL Engine:");
		jdbcLabel
				.setToolTipText("Choose between a naive SQL engine, the H2 engine, or an external JDBC engine.");
		general1.add(jdbcLabel);
		general2.add(jdbcBox);

		JTextField jdbcField = new JTextField(jdbcUrl);
		JLabel jdbcLabel2 = new JLabel("JDBC URL:");
		jdbcLabel2.setToolTipText("The JDBC connection to use.");
		general1.add(jdbcLabel2);
		general2.add(Options.wrap(jdbcField));

		JTextField jdbcField2 = new JTextField(jdbcClass);
		JLabel jdbcLabel22 = new JLabel("JDBC Driver Class:");
		jdbcLabel22.setToolTipText("The JDBC class to use.");
		general1.add(jdbcLabel22);
		general2.add(Options.wrap(jdbcField2));

		JTextField plen = new JTextField(Integer.toString(MAX_PATH_LENGTH));
		JLabel label6 = new JLabel("Maximum path length:");
		label6.setToolTipText(label6text);
		general1.add(label6);
		general2.add(Options.wrap(plen));

		JTextField iter = new JTextField(
				Integer.toString(MAX_DENOTE_ITERATIONS));
		JLabel label7 = new JLabel(
				"Maximum iterations for left-kan:");
		label7.setToolTipText(label7text);
		general1.add(label7);
		general2.add(Options.wrap(iter));
	
		JTextField vlen = new JTextField(Integer.toString(varlen));
		JLabel label8 = new JLabel("VARCHAR size:");
		label8.setToolTipText(label8text);
		general1.add(label8);
		general2.add(Options.wrap(vlen));

		JTextField area = new JTextField(12);
		area.setText(prelude);
		JLabel areaLabel = new JLabel("Generated SQL prelude:");
		areaLabel.setToolTipText("Set the prelude for the generated SQL.");
		general1.add(areaLabel);
		general2.add(Options.wrap(area));
		// area.setMaximumSize(new Dimension(200, 300));

		JTextField area2 = new JTextField(12);
		area2.setText(afterlude);
		JLabel areaLabel2 = new JLabel("Generated SQL postlude:");
		areaLabel2.setToolTipText("Set the postlude for the generated SQL.");
		general1.add(areaLabel2);
		general2.add(Options.wrap(area2));
		// area2.setMaximumSize(new Dimension(200, 300));

	
		JPanel schemaArea = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JCheckBox schema_graphical_box = new JCheckBox("Graph",
				schema_graphical);
		JCheckBox schema_textual_box = new JCheckBox("Text", schema_textual);
		JCheckBox schema_tabular_box = new JCheckBox("Table", schema_tabular);
		JCheckBox schema_ed_box = new JCheckBox("ED", schema_ed);
		JCheckBox schema_denotation_box = new JCheckBox("Denotation",
				schema_denotation);
		JCheckBox schema_rdf_box = new JCheckBox("OWL", schema_rdf);
		JCheckBox schema_dot_box = new JCheckBox("Dot", schema_dot);
		JCheckBox schema_chk_box = new JCheckBox("Check", schema_check);

		// JPanel schemaTemp = new JPanel();
		// schemaTemp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		JComboBox<String> schemaBox = new JComboBox<>(layouts);
		schemaBox.setSelectedItem(schema_graph);
		schemaBox.setToolTipText(layout_string);
		schemaArea.add(schemaBox);
		schemaArea.add(schema_graphical_box);

		// schemaArea.add(schemaTemp);

		schemaArea.add(schema_textual_box);
		schemaArea.add(schema_tabular_box);
		schemaArea.add(schema_ed_box);
		schemaArea.add(schema_denotation_box);
		schemaArea.add(schema_rdf_box);
		schemaArea.add(schema_chk_box);
		// schemaArea.add(schemaBox);
		JLabel schema_label = new JLabel("Schema viewer panels:");
		schema_label.setToolTipText("Sets which viewers to use for schemas.");
		general1.add(schema_label);
		general2.add(schemaArea);

	
		JPanel mappingArea = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JCheckBox mapping_graphical_box = new JCheckBox("Graph",
				mapping_graphical);
		JCheckBox mapping_textual_box = new JCheckBox("Text", mapping_textual);
		JCheckBox mapping_tabular_box = new JCheckBox("Table", mapping_tabular);
		JCheckBox mapping_ed_box = new JCheckBox("ED", mapping_ed);
		// JPanel mappingTemp = new JPanel();
		// schemaTemp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		JComboBox<String> mappingBox = new JComboBox<>(layouts);
		mappingBox.setToolTipText(layout_string);
		mappingBox.setSelectedItem(mapping_graph);

		mappingArea.add(mappingBox);
		mappingArea.add(mapping_graphical_box);

		// mappingArea.add(mappingTemp);
		mappingArea.add(mapping_textual_box);
		mappingArea.add(mapping_tabular_box);
		mappingArea.add(mapping_ed_box);
		JLabel mapping_label = new JLabel("Mapping viewer panels:");
		mapping_label.setToolTipText("Sets which viewers to use for mappings.");
		general1.add(mapping_label);
		general2.add(mappingArea);


		JPanel instArea = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JCheckBox inst_graphical_box = new JCheckBox("Graph", inst_graphical);
		JCheckBox inst_textual_box = new JCheckBox("Text", inst_textual);
		JCheckBox inst_tabular_box = new JCheckBox("Table", inst_tabular);
		JCheckBox inst_joined_box = new JCheckBox("Join", inst_joined);
		JCheckBox inst_gr_box = new JCheckBox("Elems", inst_gr);
		JCheckBox inst_obs_box = new JCheckBox("Obs", inst_obs);
		JCheckBox inst_rdf_box = new JCheckBox("RDF", inst_rdf);
		JCheckBox inst_adom_box = new JCheckBox("Adom", inst_adom);
		JCheckBox inst_dot_box = new JCheckBox("Dot", inst_dot);
		
		JComboBox<String> instBox = new JComboBox<>(layouts);
		instBox.setToolTipText(layout_string);
		instBox.setSelectedItem(inst_graph);
		instArea.add(instBox);
		instArea.add(inst_graphical_box);

		instArea.add(inst_textual_box);
		instArea.add(inst_tabular_box);
		instArea.add(inst_joined_box);
		instArea.add(inst_gr_box);
		instArea.add(inst_obs_box);
		instArea.add(inst_rdf_box);
		instArea.add(inst_adom_box);
		instArea.add(inst_dot_box);
		JLabel inst_label = new JLabel("Instance viewer panels:");
		inst_label.setToolTipText("Sets which viewers to use for instances.");
		general1.add(inst_label);
		general2.add(instArea);

		instBox.setToolTipText(layout_string);
	
		JPanel transformArea = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JCheckBox transform_graphical_box = new JCheckBox("Graph",
				transform_graphical);
		JCheckBox transform_textual_box = new JCheckBox("Text",
				transform_textual);
		JCheckBox transform_tabular_box = new JCheckBox("Table",
				transform_tabular);
		JComboBox<String> transBox = new JComboBox<>(layouts);
		transBox.setToolTipText(layout_string);
		transBox.setSelectedItem(trans_graph);

		transformArea.add(transBox);
		transformArea.add(transform_graphical_box);

		transformArea.add(transform_textual_box);
		transformArea.add(transform_tabular_box);
		JLabel transform_label = new JLabel("Transform viewer panels:");
		mapping_label
				.setToolTipText("Sets which viewers to use for transforms.");
		general1.add(transform_label);
		general2.add(transformArea);

		JPanel fullQueryArea = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JCheckBox query_graphical_box = new JCheckBox("Graph", query_graphical);
		JCheckBox query_textual_box = new JCheckBox("Text", query_textual);
		JComboBox<String> queryBox = new JComboBox<>(layouts);
		queryBox.setToolTipText(layout_string);
		queryBox.setSelectedItem(query_graph);

		fullQueryArea.add(queryBox);
		fullQueryArea.add(query_graphical_box);

		fullQueryArea.add(query_textual_box);
		JLabel query_label = new JLabel("Full Query viewer panels:");
		query_label
				.setToolTipText("Sets which viewers to use for full queries.");
		general1.add(query_label);
		general2.add(fullQueryArea);
		
		JComboBox<String> instFlowBox = new JComboBox<>(layouts);
		instFlowBox.setToolTipText(layout_string);
		instFlowBox.setSelectedItem(instFlow_graph);

		JPanel instFlowArea = new JPanel(new FlowLayout(FlowLayout.LEFT));
		instFlowArea.add(instFlowBox);
		JLabel instFlow_label = new JLabel("Instance Flow graph layouts:");
		instFlow_label.setToolTipText("Sets which graph layout to use for instance flow.");
		general1.add(instFlow_label);
		general2.add(instFlowArea);
		
		JComboBox<String> schFlowBox = new JComboBox<>(layouts);
		schFlowBox.setToolTipText(layout_string);
		schFlowBox.setSelectedItem(schFlow_graph);

		JPanel schFlowArea = new JPanel(new FlowLayout(FlowLayout.LEFT));
		schFlowArea.add(schFlowBox);
		JLabel schFlow_label = new JLabel("Schema Flow graph layouts:");
		schFlow_label.setToolTipText("Sets which graph layout to use for schema flow.");
		general1.add(schFlow_label);
		general2.add(schFlowArea);
		
//		for (int i = 0; i < Options.biggestSize - size(); i++) {
	//		general1.add(new JLabel());
//			general2.add(new JLabel());
	//	}

		Function<Unit, Unit> fn = (Unit t) -> {
                    int a = MAX_PATH_LENGTH;
                    int b = MAX_DENOTE_ITERATIONS;
                    int d = varlen;
                    try {
                        a = Integer.parseInt(plen.getText());
                        b = Integer.parseInt(iter.getText());
                        d = Integer.parseInt(vlen.getText());
                        
                    } catch (NumberFormatException nfe) {
                        
                    }
                    continue_on_error = coeB.isSelected();
                    VALIDATE_WITH_EDS = ed.isSelected();
                    ALLOW_NULLS = nullbox.isSelected();
                    ALL_GR_PATHS = gr.isSelected();
                    ALLOW_INFINITES = jcb0.isSelected();
                    VALIDATE = jcb.isSelected();
                    
                    MAX_PATH_LENGTH = a;
                    MAX_DENOTE_ITERATIONS = b;
                    varlen = d;
                    prelude = area.getText();
                    afterlude = area2.getText();
                    
                    schema_denotation = schema_denotation_box.isSelected();
                    schema_ed = schema_ed_box.isSelected();
                    schema_graphical = schema_graphical_box.isSelected();
                    schema_tabular = schema_tabular_box.isSelected();
                    schema_textual = schema_textual_box.isSelected();
                    schema_rdf = schema_rdf_box.isSelected();
                    schema_dot = schema_dot_box.isSelected();
                    schema_check = schema_chk_box.isSelected();
                    
                    mapping_ed = mapping_ed_box.isSelected();
                    mapping_graphical = mapping_graphical_box.isSelected();
                    mapping_tabular = mapping_tabular_box.isSelected();
                    mapping_textual = mapping_textual_box.isSelected();
                    
                    query_graphical = query_graphical_box.isSelected();
                    query_textual = query_textual_box.isSelected();
                    
                    inst_graphical = inst_graphical_box.isSelected();
                    inst_tabular = inst_tabular_box.isSelected();
                    inst_textual = inst_textual_box.isSelected();
                    inst_joined = inst_joined_box.isSelected();
                    inst_gr = inst_gr_box.isSelected();
                    inst_obs = inst_obs_box.isSelected();
                    inst_rdf = inst_rdf_box.isSelected();
                    inst_adom = inst_adom_box.isSelected();
                    inst_dot = inst_dot_box.isSelected();
                    
                    schema_graph = (String) schemaBox.getSelectedItem();
                    mapping_graph = (String) mappingBox.getSelectedItem();
                    inst_graph = (String) instBox.getSelectedItem();
                    trans_graph = (String) transBox.getSelectedItem();
                    query_graph = (String) queryBox.getSelectedItem();
                    instFlow_graph = (String) instFlowBox.getSelectedItem();
                    schFlow_graph = (String) schFlowBox.getSelectedItem();
                    
                    allow_surjective = surjB.isSelected();
                    
                    transform_graphical = transform_graphical_box.isSelected();
                    transform_tabular = transform_tabular_box.isSelected();
                    transform_textual = transform_textual_box.isSelected();
                    
                    if (h2Button.isSelected()) {
                        sqlKind = SQLKIND.H2;
                    } else if (jdbcButton.isSelected()) {
                        sqlKind = SQLKIND.JDBC;
                    } else if (nativeButton.isSelected()) {
                        sqlKind = SQLKIND.NATIVE;
                    }
                    
                    jdbcUrl = jdbcField.getText();
                    jdbcClass = jdbcField2.getText();
                    
                    return Unit.unit;
                };
		JPanel pan = new JPanel(new GridLayout(1,1));
		pan.setPreferredSize(new Dimension(300, 20*Options.biggestSize));
		pan.add(new JScrollPane(generalsplit));
		return new Pair<>(pan, fn);
	}
	

}
