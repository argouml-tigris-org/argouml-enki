// $Id$
// Copyright (c) 2005-2008 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation without fee, and without a written
// agreement is hereby granted, provided that the above copyright notice
// and this paragraph appear in all copies. This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "AS
// IS", without any accompanying services from The Regents. The Regents
// does not warrant that the operation of the program will be
// uninterrupted or error-free. The end-user understands that the program
// was developed for research purposes and is advised not to rely
// exclusively on the program for any reason. IN NO EVENT SHALL THE
// UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT,
// SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS,
// ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
// THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
// SUCH DAMAGE. THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY
// WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
// PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
// CALIFORNIA HAS NO OBLIGATIONS TO PROVIDE MAINTENANCE, SUPPORT,
// UPDATES, ENHANCEMENTS, OR MODIFICATIONS.

package org.argouml.model.enki;

import java.beans.PropertyChangeListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.jmi.reflect.RefObject;
import javax.jmi.reflect.RefPackage;

import org.apache.log4j.Logger;
import org.argouml.model.AbstractModelEventPumpDecorator;
import org.argouml.model.AbstractModelManagementFactoryDecorator;
import org.argouml.model.AbstractUmlFactoryDecorator;
import org.argouml.model.AbstractXmiReaderDecorator;
import org.argouml.model.ActivityGraphsFactory;
import org.argouml.model.ActivityGraphsHelper;
import org.argouml.model.AggregationKind;
import org.argouml.model.ChangeableKind;
import org.argouml.model.CollaborationsFactory;
import org.argouml.model.CollaborationsHelper;
import org.argouml.model.CommonBehaviorFactory;
import org.argouml.model.CommonBehaviorHelper;
import org.argouml.model.ConcurrencyKind;
import org.argouml.model.CopyHelper;
import org.argouml.model.CoreFactory;
import org.argouml.model.CoreHelper;
import org.argouml.model.DataTypesFactory;
import org.argouml.model.DataTypesHelper;
import org.argouml.model.DiagramInterchangeModel;
import org.argouml.model.DirectionKind;
import org.argouml.model.ExtensionMechanismsFactory;
import org.argouml.model.ExtensionMechanismsHelper;
import org.argouml.model.Facade;
import org.argouml.model.InvalidElementException;
import org.argouml.model.MetaTypes;
import org.argouml.model.ModelEventPump;
import org.argouml.model.ModelImplementation;
import org.argouml.model.ModelManagementFactory;
import org.argouml.model.ModelManagementHelper;
import org.argouml.model.OrderingKind;
import org.argouml.model.PseudostateKind;
import org.argouml.model.ScopeKind;
import org.argouml.model.StateMachinesFactory;
import org.argouml.model.StateMachinesHelper;
import org.argouml.model.UmlException;
import org.argouml.model.UmlFactory;
import org.argouml.model.UmlHelper;
import org.argouml.model.UseCasesFactory;
import org.argouml.model.UseCasesHelper;
import org.argouml.model.VisibilityKind;
import org.argouml.model.XmiReader;
import org.argouml.model.XmiWriter;
import org.argouml.model.mdr.MDRModelImplementation;

import org.argouml.configuration.Configuration;
import org.argouml.configuration.ConfigurationKey;

import org.eigenbase.enki.mdr.EnkiMDRepository;
import org.eigenbase.enki.mdr.MDRepositoryFactory;
import org.eigenbase.enki.hibernate.HibernateMDRepository;

import org.netbeans.api.mdr.CreationFailedException;

import org.omg.uml.UmlPackage;
import org.omg.uml.foundation.core.ModelElement;
import org.omg.uml.foundation.core.Namespace;
import org.omg.uml.modelmanagement.ElementImport;
import org.omg.uml.modelmanagement.Model;
import org.omg.uml.modelmanagement.ModelManagementPackage;
import org.omg.uml.modelmanagement.Subsystem;

import org.xml.sax.InputSource;

/**
 * A {@link ModelImplementation} which integrates the <a
 * href="http://argouml-enki.tigris.org">Enki/Hibernate metadata repository</a>
 * as persistence for ArgoUML.  The approach taken is to wrap an underlying
 * {@link MDRModelImplementation} via the <a
 * href="http://www.wikipedia.org/wiki/Decorator_pattern">decorator
 * pattern</a>.
 */
public class EnkiModelImplementation implements ModelImplementation {

    /**
     * Logger.
     */
    private static final Logger LOG =
        Logger.getLogger(EnkiModelImplementation.class);

    /**
     * The Enki/Hibernate repository implementation.
     */
    private HibernateMDRepository repository;
    
    /**
     * Underlying MDR implementation being decorated.
     */
    private MDRModelImplementation theMDRMI;

    /**
     * Decorator for underlying MDR implementation component.
     */
    private ModelManagementFactory enkiModelManagementFactory;

    /**
     * Decorator for underlying MDR implementation component.
     */
    private ModelEventPump enkiModelEventPump;

    /**
     * Decorator for underlying MDR implementation component.
     */
    private UmlFactory enkiUmlFactory;

    /**
     * Whether this repository was created as part of construction
     * (as opposed to reloading an existing repository).
     */
    private boolean newRepository;

    /**
     * Extent name for storing default model instance; chosen to match {@link
     * MDRModelImplementation#MODEL_EXTENT_NAME}.
     */
    private static final String UML_MODEL_EXTENT_NAME = "model extent";

    /**
     * Configuration key for the location of Enki's storage property file.
     */
    public static final ConfigurationKey KEY_PROPFILE =
        Configuration.makeKey("enki", "propfile");

    /**
     * Create a new EnkiModelImplementation that decorates
     * an underlying MDRModelImplementation.
     *
     * @throws UmlException
     */
    public EnkiModelImplementation() throws UmlException {

        LOG.debug("Starting Enki initialization");

        // Load up properties from configured or default location.
        String valPropfile = Configuration.getString(KEY_PROPFILE);
        if (valPropfile.equals("")) {
            valPropfile = "argouml-enki.properties";
        }
        File storagePropsFile = new File(valPropfile);
        Properties storageProps = new Properties();
        try {
            storageProps.load(new FileInputStream(storagePropsFile));
        } catch (IOException e) {
            throw new UmlException(
                "Unable to load enki storage properties file", e);
        }

        // Load repository, setting some flags for options needed
        // by argouml-enki.
        HibernateMDRepository.enableInterThreadSessions(true);
        repository = (HibernateMDRepository)
            MDRepositoryFactory.newMDRepository(storageProps);
        repository.enableMultipleExtentsForSameModel();

        // Start our session.
        //
        // TODO: We currently use one very long running session for the entire
        // ArgoUML lifetime, which is not a recommended Hibernate usage
        // pattern, so we'll probably need to break it up to avoid
        // running out of memory when large numbers of objects are accessed.
        repository.beginSession();

        // Start a transaction on the session.
        //
        // TODO: We currently don't have good control over transaction
        // boundaries from the ArgoUML UI, so we leave the transaction running
        // until flushModelEvents gets called, sort of an "unpredictable
        // autocommit".  Resolving this requires discussion and changes at the
        // argouml-dev level.
        repository.beginTrans(true);
        
        LOG.debug("Enki Init - started transaction on default repository");

        // TODO: Get ArgoUML to call the real shutdown method instead
        // of having to rely on this JVM shutdown hook.
        ShutdownThread shutdownHook = new ShutdownThread(repository);
        Runtime.getRuntime().addShutdownHook(shutdownHook);

        // Create the underlying MDRModelImplementation we
        // will decorate, pointing it at our Enki repository
        // instead of the MDR instance it would normally be using.
        theMDRMI = new MDRModelImplementation(repository);
        
        // Create an extent for the default UML instance, or reload
        // the existing one from the repository if it's already there.
        UmlPackage umlPackage =
            (UmlPackage) repository.getExtent(UML_MODEL_EXTENT_NAME);
        if (umlPackage == null) {
            LOG.debug("Enki Init - Creating new UML extent");
            newRepository = true;
            try {
                umlPackage =
                    (UmlPackage) repository.createExtent(
                        UML_MODEL_EXTENT_NAME, theMDRMI.getMofPackage());
            } catch (CreationFailedException e) {
                throw new UmlException(e);
            }
            // Commit the creation of the new extent before continuing.
            repository.endTrans();
            repository.beginTrans(true);
            LOG.debug("Enki Init - Created UML extent");
        } else {
            LOG.debug("Enki Init - Reusing existing UML extent");
        }

        // TODO jvs 2-Dec-2008:  need Tom to add this method
        // to MDRModelImplementation; without it, we get a lot
        // of bogus warning on console since MDRModelImplementation
        // doesn't know about our extents.
        /*
        theMDRMI.registerExtent(umlPackage, false);
        */

        // Complete the initialization of MDRModelImplementation
        // now that the default UML package is available.
        theMDRMI.initializeFactories(umlPackage);

        // Decorate the MDRModelImplementation components where
        // we need to wedge in argouml-enki behavior.
        enkiModelManagementFactory =
            new ModelManagementFactoryEnkiImpl();
        enkiModelEventPump = new ModelEventPumpEnkiImpl();
        enkiUmlFactory = new UmlFactoryEnkiImpl();
    }

    private static class ShutdownThread
        extends Thread {
        
        private EnkiMDRepository repository;

        ShutdownThread(EnkiMDRepository repository)
        {
            this.repository = repository;
        }
        
        public void run()
        {
            try {
                repository.endTrans();
                repository.endSession();
                repository.shutdown();
            } catch (Exception ex) {
                // TODO:  Get rid of printStackTrace as part of a proper
                // shutdown call sequence from ArgoUML.
                ex.printStackTrace();
                LOG.debug("Enki Shutdown Failure - " + ex);
            }
        }
    }
    
    public ActivityGraphsFactory getActivityGraphsFactory() {
        return theMDRMI.getActivityGraphsFactory();
    }

    public ActivityGraphsHelper getActivityGraphsHelper() {
        return theMDRMI.getActivityGraphsHelper();
    }

    public AggregationKind getAggregationKind() {
        return theMDRMI.getAggregationKind();
    }

    public ChangeableKind getChangeableKind() {
        return theMDRMI.getChangeableKind();
    }

    public CollaborationsFactory getCollaborationsFactory() {
        return theMDRMI.getCollaborationsFactory();
    }

    public CollaborationsHelper getCollaborationsHelper() {
        return theMDRMI.getCollaborationsHelper();
    }

    public CommonBehaviorFactory getCommonBehaviorFactory() {
        return theMDRMI.getCommonBehaviorFactory();
    }

    public CommonBehaviorHelper getCommonBehaviorHelper() {
        return theMDRMI.getCommonBehaviorHelper();
    }

    public ConcurrencyKind getConcurrencyKind() {
        return theMDRMI.getConcurrencyKind();
    }

    public CopyHelper getCopyHelper() {
        return theMDRMI.getCopyHelper();
    }

    public CoreFactory getCoreFactory() {
        return theMDRMI.getCoreFactory();
    }

    public CoreHelper getCoreHelper() {
        return theMDRMI.getCoreHelper();
    }

    public DataTypesFactory getDataTypesFactory() {
        return theMDRMI.getDataTypesFactory();
    }

    public DataTypesHelper getDataTypesHelper() {
        return theMDRMI.getDataTypesHelper();
    }

    public DiagramInterchangeModel getDiagramInterchangeModel() {
        return theMDRMI.getDiagramInterchangeModel();
    }

    public DirectionKind getDirectionKind() {
        return theMDRMI.getDirectionKind();
    }

    public ExtensionMechanismsFactory getExtensionMechanismsFactory() {
        return theMDRMI.getExtensionMechanismsFactory();
    }

    public ExtensionMechanismsHelper getExtensionMechanismsHelper() {
        return theMDRMI.getExtensionMechanismsHelper();
    }

    public Facade getFacade() {
        return theMDRMI.getFacade();
    }

    public MetaTypes getMetaTypes() {
        return theMDRMI.getMetaTypes();
    }

    public ModelEventPump getModelEventPump() {
        return enkiModelEventPump;
    }

    public ModelManagementFactory getModelManagementFactory() {
        return enkiModelManagementFactory;
    }

    public ModelManagementHelper getModelManagementHelper() {
        return theMDRMI.getModelManagementHelper();
    }

    public OrderingKind getOrderingKind() {
        return theMDRMI.getOrderingKind();
    }

    public PseudostateKind getPseudostateKind() {
        return theMDRMI.getPseudostateKind();
    }

    public ScopeKind getScopeKind() {
        return theMDRMI.getScopeKind();
    }

    public StateMachinesFactory getStateMachinesFactory() {
        return theMDRMI.getStateMachinesFactory();
    }

    public StateMachinesHelper getStateMachinesHelper() {
        return theMDRMI.getStateMachinesHelper();
    }

    public UmlFactory getUmlFactory() {
        return enkiUmlFactory;
    }

    public UmlHelper getUmlHelper() {
        return theMDRMI.getUmlHelper();
    }

    public UseCasesFactory getUseCasesFactory() {
        return theMDRMI.getUseCasesFactory();
    }

    public UseCasesHelper getUseCasesHelper() {
        return theMDRMI.getUseCasesHelper();
    }

    public VisibilityKind getVisibilityKind() {
        return theMDRMI.getVisibilityKind();
    }

    public XmiReader getXmiReader() throws UmlException {
        return new XmiReaderEnkiImpl();
    }

    public XmiWriter getXmiWriter(Object model, OutputStream stream,
                                  String version) throws UmlException {
        return theMDRMI.getXmiWriter(model, stream, version);
    }

    /**
     * Decorator for MDR implementation of {@link XmiReader}.
     */
    private class XmiReaderEnkiImpl extends AbstractXmiReaderDecorator {

        XmiReaderEnkiImpl() throws UmlException {
            super(theMDRMI.getXmiReader());
        }
        
        public Collection parse(InputSource pIs, boolean readOnly)
            throws UmlException {

            // This is a hack to reuse existing stored profiles instead
            // of deleting and re-importing them every time ArgoUML
            // starts.  It would be problematic if the contents
            // of the profile changed during an upgrade, leaving the
            // repository stale, so it needs more thought.
            if (readOnly && (pIs.getSystemId() != null)) {
                RefPackage p = repository.getExtent(pIs.getSystemId());
                if (p != null) {
                    /*
                    // TODO jvs 1-Dec-2008:  This should be true, not false!
                    // But if I pass true, wires get crossed somewhere
                    // and the entire tree is read-only after reload.
                    // Need to investigate.
                    theMDRMI.registerExtent((UmlPackage) p, false);
                    */
                    String modelName =
                        repository.getAnnotation(pIs.getSystemId());
                    // TODO jvs 1-Dec-2008: This is special-cased for making
                    // standard profile loading work; but in general, we could
                    // be reading anything, not just models.
                    for (Object o : 
                             ((UmlPackage) p).getModelManagement().getModel().
                             refAllOfClass()) {
                        Model m = (Model) o;
                        if (m.getName().equals(modelName)) {
                            List list = new ArrayList();
                            list.add(m);
                            return list;
                        }
                    }
                }
            }

            // The decorated implementation expects to be running
            // without a transaction, so end our long-running one.
            repository.endTrans();

            Collection c;
            try {
                c = super.parse(pIs, readOnly);
            } finally {
                // Restart a new transaction before carrying on.
                repository.beginTrans(true);
            }

            // Annotate the new extent with the name of the
            // model which was imported so that we can navigate
            // from one to the other.  This wouldn't be necessary
            // if Enki/Hibernate supported multiple
            // extents for real, but for now we have to fake it.
            if ((c.size() == 1) && (pIs.getSystemId() != null)) {
                Object o = c.iterator().next();
                if (o instanceof Model) {
                    Model m = (Model) o;
                    repository.setAnnotation(pIs.getSystemId(),
                                             m.getName());
                    // Commit the annotation.
                    repository.endTrans();
                    repository.beginTrans(true);
                }
            }
            return c;
        }
    }

    /**
     * Decorator for MDR implementation of {@link UmlFactory}.
     */
    private class UmlFactoryEnkiImpl
        extends AbstractUmlFactoryDecorator {
        
        UmlFactoryEnkiImpl() {
            super(theMDRMI.getUmlFactory());
        }

        public void deleteExtent(Object element) {

            // Enki doesn't like attempts to delete
            // non-existent extents, so pre-detect them
            // and avoid calling in that case; ArgoUML
            // will catch InvalidElementException and
            // swallow it.
            
            org.omg.uml.UmlPackage extent = 
                (org.omg.uml.UmlPackage) ((RefObject) element)
                .refOutermostPackage();

            for (String extentName : repository.getExtentNames()) {
                RefPackage p = repository.getExtent(extentName);
                if ((p != null) && (p.equals(extent))) {
                    super.deleteExtent(element);
                }
            }
            throw new InvalidElementException("extent does not exist");
        }
    }

    /**
     * Decorator for MDR implementation of {@link ModelEventPump}.
     */
    private class ModelEventPumpEnkiImpl
        extends AbstractModelEventPumpDecorator {
        
        ModelEventPumpEnkiImpl() {
            super(theMDRMI.getModelEventPump());
        }
        
        public void flushModelEvents() {
            // This is necessary to avoid having the UI lock up for
            // certain operations, since super.flushModelEvents()
            // is going to wait for them to be delivered, but
            // Enki is not going to send them until end of transaction.
            
            repository.endTrans();
            super.flushModelEvents();
            repository.beginTrans(true);
        }
    }

    /**
     * Decorator for MDR implementation of {@link ModelManagementFactory}.
     */
    private class ModelManagementFactoryEnkiImpl
        extends AbstractModelManagementFactoryDecorator {

        ModelManagementFactoryEnkiImpl() {
            super(theMDRMI.getModelManagementFactory());
        }

        public Object createModel() {

            // When ArgoUML starts up, it thinks it is initializing
            // an empty repository, so it will call createModel.
            // But if we're reloading an existing persistent
            // repository, we don't want to create anything new;
            // instead, reload the existing model.
            
            if (!newRepository && theMDRMI.getUmlPackage() != null) {
                Collection models =
                    theMDRMI.getUmlPackage().getModelManagement().getModel().
                    refAllOfClass();

                // FIXME jvs 30-Nov-2008:  This is lame and will break
                // once someone happens to include one of these words
                // in a user-defined model name.  Could use the
                // annotation stuff from XmiReaderEnkiImpl,
                // but that's lame too, so need a real solution.
                for (Object obj : models) {
                    Model m = (Model) obj;
                    if ((m.getName().indexOf("Profile") == -1)
                        && (m.getName().indexOf("Standard Elements") == -1)) {
                        return m;
                    }
                }
            }
            return super.createModel();
        }
    }
}
