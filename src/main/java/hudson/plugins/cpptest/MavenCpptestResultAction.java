package hudson.plugins.cpptest;

import hudson.maven.*;
import hudson.model.Run;
import hudson.model.Action;
import hudson.plugins.analysis.core.HealthDescriptor;

import java.util.List;
import java.util.Map;

/**
 * A {@link CpptestResultAction} for native maven jobs. This action
 * additionally provides result aggregation for sub-modules and for the main
 * project.
 *
 * @author Ulli Hafner
 *         <p/>
 *         NQH: adapt for Cpptest
 */
public class MavenCpptestResultAction extends CpptestResultAction implements AggregatableAction, MavenAggregatedReport {

    /**
     * The default encoding to be used when reading and parsing files.
     */
    private final String defaultEncoding;

    /**
     * Creates a new instance of <code>MavenCpptestResultAction</code>.
     *
     * @param owner            the associated build of this action
     * @param healthDescriptor health descriptor to use
     * @param defaultEncoding  the default encoding to be used when reading and parsing files
     */
    public MavenCpptestResultAction(final Run<?, ?> owner, final HealthDescriptor healthDescriptor,
                                    final String defaultEncoding) {
        super(owner, healthDescriptor);
        this.defaultEncoding = defaultEncoding;
    }

    /**
     * Creates a new instance of <code>MavenCpptestResultAction</code>.
     *
     * @param owner            the associated build of this action
     * @param healthDescriptor health descriptor to use
     * @param result           the result in this build
     * @param defaultEncoding  the default encoding to be used when reading and parsing files
     */
    public MavenCpptestResultAction(final Run<?, ?> owner, final HealthDescriptor healthDescriptor,
                                    final String defaultEncoding, final CpptestResult result) {
        super(owner, healthDescriptor, result);
        this.defaultEncoding = defaultEncoding;
    }

    /**
     * {@inheritDoc}
     */
    public MavenAggregatedReport createAggregatedAction(final MavenModuleSetBuild build, final Map<MavenModule, List<MavenBuild>> moduleBuilds) {
        return new MavenCpptestResultAction(build, getHealthDescriptor(), defaultEncoding);
    }

    /**
     * {@inheritDoc}
     */
    public Action getProjectAction(final MavenModuleSet moduleSet) {
        return new CpptestProjectAction(moduleSet);
    }

    /**
     * {@inheritDoc}
     */
    public Class<? extends AggregatableAction> getIndividualActionType() {
        return getClass();
    }

    /**
     * Called whenever a new module build is completed, to update the aggregated
     * report. When multiple builds complete simultaneously, Hudson serializes
     * the execution of this method, so this method needs not be
     * concurrency-safe.
     *
     * @param moduleBuilds Same as <tt>MavenModuleSet.getModuleBuilds()</tt> but provided
     *                     for convenience and efficiency.
     * @param newBuild     Newly completed build.
     */
    public void update(final Map<MavenModule, List<MavenBuild>> moduleBuilds, final MavenBuild newBuild) {
        CpptestResult annotationsResult = new CpptestResult(getOwner(), defaultEncoding, createAggregatedResult(moduleBuilds));
        setResult(annotationsResult);
        updateBuildHealth(newBuild, annotationsResult);
    }

    /**
     * Backward compatibility. @deprecated
     */
    @SuppressWarnings("unused")
    @Deprecated
    private transient String height;

    public String getIconFileName() {
        return null;
    }

    public String getUrlName() {
        return null;
    }

}