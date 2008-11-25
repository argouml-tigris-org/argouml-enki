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

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.jmi.model.ModelPackage;
import javax.jmi.model.MofPackage;
import javax.jmi.reflect.RefPackage;
import javax.jmi.xmi.MalformedXMIException;

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
import org.argouml.model.UUIDManager;
import org.argouml.model.UmlException;
import org.argouml.model.UmlFactory;
import org.argouml.model.UmlHelper;
import org.argouml.model.UseCasesFactory;
import org.argouml.model.UseCasesHelper;
import org.argouml.model.VisibilityKind;
import org.argouml.model.XmiReader;
import org.argouml.model.XmiWriter;
import org.argouml.model.mdr.MDRModelImplementation;
import org.netbeans.api.mdr.CreationFailedException;
import org.netbeans.api.mdr.MDRManager;
import org.netbeans.api.mdr.MDRepository;
import org.netbeans.api.xmi.XMIReader;
import org.netbeans.api.xmi.XMIReaderFactory;
import org.omg.uml.UmlPackage;

/**
 * The handle to find all helper and factories.
 */
public class EnkiModelImplementation implements ModelImplementation {

    /**
     * Logger.
     */
    private static final Logger LOG =
        Logger.getLogger(EnkiModelImplementation.class);

    private ModelImplementation MDRModelImplementation =
	new MDRModelImplementation();

    public ActivityGraphsFactory getActivityGraphsFactory() {
	return MDRModelImplementation.getActivityGraphsFactory();
    }

    public ActivityGraphsHelper getActivityGraphsHelper() {
	// TODO: Auto-generated method stub
	return null;
    }

    public AggregationKind getAggregationKind() {
	// TODO: Auto-generated method stub
	return null;
    }

    public ChangeableKind getChangeableKind() {
	// TODO: Auto-generated method stub
	return null;
    }

    public CollaborationsFactory getCollaborationsFactory() {
	// TODO: Auto-generated method stub
	return null;
    }

    public CollaborationsHelper getCollaborationsHelper() {
	// TODO: Auto-generated method stub
	return null;
    }

    public CommonBehaviorFactory getCommonBehaviorFactory() {
	// TODO: Auto-generated method stub
	return null;
    }

    public CommonBehaviorHelper getCommonBehaviorHelper() {
	// TODO: Auto-generated method stub
	return null;
    }

    public ConcurrencyKind getConcurrencyKind() {
	// TODO: Auto-generated method stub
	return null;
    }

    public CopyHelper getCopyHelper() {
	// TODO: Auto-generated method stub
	return null;
    }

    public CoreFactory getCoreFactory() {
	// TODO: Auto-generated method stub
	return null;
    }

    public CoreHelper getCoreHelper() {
	// TODO: Auto-generated method stub
	return null;
    }

    public DataTypesFactory getDataTypesFactory() {
	// TODO: Auto-generated method stub
	return null;
    }

    public DataTypesHelper getDataTypesHelper() {
	// TODO: Auto-generated method stub
	return null;
    }

    public DiagramInterchangeModel getDiagramInterchangeModel() {
	// TODO: Auto-generated method stub
	return null;
    }

    public DirectionKind getDirectionKind() {
	// TODO: Auto-generated method stub
	return null;
    }

    public ExtensionMechanismsFactory getExtensionMechanismsFactory() {
	// TODO: Auto-generated method stub
	return null;
    }

    public ExtensionMechanismsHelper getExtensionMechanismsHelper() {
	// TODO: Auto-generated method stub
	return null;
    }

    public Facade getFacade() {
	// TODO: Auto-generated method stub
	return null;
    }

    public MetaTypes getMetaTypes() {
	// TODO: Auto-generated method stub
	return null;
    }

    public ModelEventPump getModelEventPump() {
	// TODO: Auto-generated method stub
	return null;
    }

    public ModelManagementFactory getModelManagementFactory() {
	// TODO: Auto-generated method stub
	return null;
    }

    public ModelManagementHelper getModelManagementHelper() {
	// TODO: Auto-generated method stub
	return null;
    }

    public OrderingKind getOrderingKind() {
	// TODO: Auto-generated method stub
	return null;
    }

    public PseudostateKind getPseudostateKind() {
	// TODO: Auto-generated method stub
	return null;
    }

    public ScopeKind getScopeKind() {
	// TODO: Auto-generated method stub
	return null;
    }

    public StateMachinesFactory getStateMachinesFactory() {
	// TODO: Auto-generated method stub
	return null;
    }

    public StateMachinesHelper getStateMachinesHelper() {
	// TODO: Auto-generated method stub
	return null;
    }

    public UmlFactory getUmlFactory() {
	// TODO: Auto-generated method stub
	return null;
    }

    public UmlHelper getUmlHelper() {
	// TODO: Auto-generated method stub
	return null;
    }

    public UseCasesFactory getUseCasesFactory() {
	// TODO: Auto-generated method stub
	return null;
    }

    public UseCasesHelper getUseCasesHelper() {
	// TODO: Auto-generated method stub
	return null;
    }

    public VisibilityKind getVisibilityKind() {
	// TODO: Auto-generated method stub
	return null;
    }

    public XmiReader getXmiReader() throws UmlException {
	// TODO: Auto-generated method stub
	return null;
    }

    public XmiWriter getXmiWriter(Object model, OutputStream stream,
	    String version) throws UmlException {
	// TODO: Auto-generated method stub
	return null;
    }

    
}
