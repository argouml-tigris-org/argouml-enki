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

import java.io.OutputStream;

import org.apache.log4j.Logger;
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

/**
 * The handle to find all helper and factories.
 */
public class EnkiModelImplementation implements ModelImplementation {

    private ModelImplementation theMDRMI;
    
    /**
     * Create a new ModelImplementation that defers everything
     * to the MDRModelImplementation.
     *
     * @throws UmlException
     */
    public EnkiModelImplementation() throws UmlException {
	theMDRMI = new MDRModelImplementation();
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
	return theMDRMI.getModelEventPump();
    }

    public ModelManagementFactory getModelManagementFactory() {
	return theMDRMI.getModelManagementFactory();
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
	return theMDRMI.getUmlFactory();
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
	return theMDRMI.getXmiReader();
    }

    public XmiWriter getXmiWriter(Object model, OutputStream stream,
	    String version) throws UmlException {
	return theMDRMI.getXmiWriter(model, stream, version);
    }

    
}
