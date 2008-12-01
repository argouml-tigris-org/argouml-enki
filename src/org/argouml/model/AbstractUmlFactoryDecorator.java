// $Id$
// Copyright (c) 1996-2008 The Regents of the University of California. All
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

package org.argouml.model;

/**
 * The abstract Decorator for the {@link UmlFactory}.
 *
 * @author John Sichi
 */
public abstract class AbstractUmlFactoryDecorator implements UmlFactory
{
    /**
     * The component.
     */
    private final UmlFactory impl;
    
    /**
     * @param component The component to decorate.
     */
    protected AbstractUmlFactoryDecorator(UmlFactory component) {
        impl = component;
    }

    public Object buildConnection(Object connectionType,
                                  Object fromElement,
                                  Object fromStyle,
                                  Object toElement, Object toStyle,
                                  Object unidirectional, Object namespace)
        throws IllegalModelElementConnectionException {
        
        return impl.buildConnection(connectionType, fromElement,
                                    fromStyle, toElement, toStyle,
                                    unidirectional, namespace);
    }
    

    public Object buildNode(Object elementType) {
        return impl.buildNode(elementType);
    }
    
    public Object buildNode(Object elementType, Object container) {
        return impl.buildNode(elementType, container);
    }

    public boolean isConnectionType(Object connectionType) {
        return impl.isConnectionType(connectionType);
    }

    public boolean isConnectionValid(Object connectionType, Object fromElement,
                              Object toElement, boolean checkWFR) {
        return impl.isConnectionValid(connectionType, fromElement,
                                      toElement, checkWFR);
    }
    
    public boolean isContainmentValid(Object metaType, Object container) {
        return impl.isContainmentValid(metaType, container);
    }
    
    public void delete(Object elem) {
        impl.delete(elem);
    }
    
    public void deleteExtent(Object element) {
        impl.deleteExtent(element);
    }

    public boolean isRemoved(Object o) {
        return impl.isRemoved(o);
    }
}
