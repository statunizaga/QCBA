/*
 * Monotonicity Exploiting Association Rule Classification (MARC)
 *
 *     Copyright (C)2014-2017 Tomas Kliegr
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as
 *     published by the Free Software Foundation, either version 3 of the
 *     License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.kliegr.ac1;

import eu.kliegr.ac1.data.AttributeType;
import eu.kliegr.ac1.rule.PruneType;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Properties;
import java.util.logging.Logger;

public class PruneConfig extends BaseConfig {

    private final static Logger LOGGER = Logger.getLogger(PruneConfig.class.getName());
    private String rulesPath = "/home/tomas/NetBeansProjects/AC1/resources/Iris1_iris_1_0.5.xml";
    private String pruneRuleSortComparator = "CBARuleComparator";

    private PruneType pruningType = PruneType.wholeRule;

    private final Comparator ruleComparator;

    /**
     *
     * @param path
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public PruneConfig(String path) throws FileNotFoundException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        //File f = new File(path);
        InputStream input = new BufferedInputStream(
                new FileInputStream(path));
        Properties prop = new Properties();
        prop.loadFromXML(input);
        rulesPath = prop.getProperty("RulesPath");
        dataPath = prop.getProperty("TrainDataPath");
        this.setOutputPath(prop.getProperty("OutputPath"));
        pruneRuleSortComparator = prop.getProperty("PruneRuleSortComparator");
        ruleComparator = (Comparator) Class.forName("eu.kliegr.ac1.rule." + pruneRuleSortComparator).newInstance();
        pruningType = PruneType.valueOf(prop.getProperty("PruningType"));
        if (prop.getProperty("DataTypes").contains(";")) {
            csvSeparator = ";";
        } else {
            csvSeparator = ",";
        }
        attType = parseAttributeTypes(prop.getProperty("DataTypes").split(csvSeparator));
        targetAttribute = prop.getProperty("TargetAttribute");
        IDcolumnName = prop.getProperty("IDcolumnName");
    }

    /**
     *
     * @return
     */
    public Comparator getRuleComparator() {
        return ruleComparator;
    }

    /**
     *
     * @return
     */
    public String getRulesPath() {
        return rulesPath;
    }

    /**
     *
     * @return
     */
    public ArrayList<AttributeType> attributeTypes() {
        return attType;
    }

    /**
     *
     * @return
     */
    public String getOutputSummaryPath() {
        return getOutputPath("summary");
    }

    /**
     *
     * @return
     */
    public PruneType getPruningType() {
        return pruningType;
    }

}
