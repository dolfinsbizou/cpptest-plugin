package hudson.plugins.cpptest;

import com.thoughtworks.xstream.XStream;
import hudson.model.Run;
import hudson.plugins.analysis.core.*;
import hudson.plugins.cpptest.parser.Warning;

/**
 * Represents the results of the Cpptest analysis. One instance of this class
 * is persisted for each build via an XML file.
 *
 * @author Ulli Hafner
 *         <p/>
 *         NQH: adapt for Cpptest
 */
public class CpptestResult extends BuildResult {
    /**
     * Unique identifier of this class.
     */
    private static final long serialVersionUID = 2768250056765266658L;

    /*new CpptestResultAction(build, this, result)*
     * Creates a new instance of {@link CpptestResult}.
     *
     * @param build           the current build as owner of this action
     * @param defaultEncoding the default encoding to be used when reading and parsing files
     * @param result          the parsed result with all annotations
     */
    public CpptestResult(final Run<?, ?> build, final String defaultEncoding, final ParserResult result) {
        super(build, new NullBuildHistory(), result, defaultEncoding); // FIXME a null build history isn't a good idea I think, but that's the best I can do for now
    }

    /**
     * Creates a new instance of {@link CpptestResult}.
     *
     * @param build           the current build as owner of this action
     * @param defaultEncoding the default encoding to be used when reading and parsing files
     * @param result          the parsed result with all annotations
     * @param history         the plug-in history
     */
    protected CpptestResult(final Run<?, ?> build, final String defaultEncoding, final ParserResult result,
                            final BuildHistory history) {
        super(build, history, result, defaultEncoding);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure(final XStream xstream) {
        xstream.alias("warning", Warning.class);
    }

    /**
     * Returns a summary message for the summary.jelly file.
     *
     * @return the summary message
     */
    public String getSummary() {
        return ResultSummary.createSummary(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String createDeltaMessage() {
        return ResultSummary.createDeltaMessage(this);
    }

    /**
     * Returns the name of the file to store the serialized annotations.
     *
     * @return the name of the file to store the serialized annotations
     */
    @Override
    protected String getSerializationFileName() {
        return "cpptest-warnings.xml";
    }

    /**
     * {@inheritDoc}
     */
    public String getDisplayName() {
        return Messages.Cpptest_ProjectAction_Name();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<? extends ResultAction<? extends BuildResult>> getResultActionType() {
        return CpptestResultAction.class;
    }
}
