// $Id$
// Copyright (c) 1996-2007 The Regents of the University of California. All
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

import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * The abstract Decorator for the {@link ModelEventPump}.
 *
 * @author John Sichi
 */
public abstract class AbstractModelEventPumpDecorator
    implements ModelEventPump {

    /**
     * The component.
     */
    private final ModelEventPump impl;

    /**
     * @param component The component to decorate.
     */
    protected AbstractModelEventPumpDecorator(ModelEventPump component) {
        impl = component;
    }
    
    public void addModelEventListener(PropertyChangeListener listener,
                                      Object modelelement,
                                      String[] propertyNames) {
        impl.addModelEventListener(listener,
                                   modelelement,
                                   propertyNames);
    }
        
    public void addModelEventListener(PropertyChangeListener listener,
                                      Object modelelement,
                                      String propertyName) {
        impl.addModelEventListener(listener,
                                   modelelement,
                                   propertyName);
    }
        
    public void addModelEventListener(PropertyChangeListener listener,
                                      Object modelelement) {
        impl.addModelEventListener(listener,
                                   modelelement);
    }
        
    public void removeModelEventListener(PropertyChangeListener listener,
                                         Object modelelement,
                                         String[] propertyNames) {
        impl.removeModelEventListener(listener,
                                      modelelement,
                                      propertyNames);
    }
        
    public void removeModelEventListener(PropertyChangeListener listener,
                                         Object modelelement,
                                         String propertyName) {
        impl.removeModelEventListener(listener,
                                      modelelement,
                                      propertyName);
    }
        
    public void removeModelEventListener(PropertyChangeListener listener,
                                         Object modelelement) {
        impl.removeModelEventListener(listener,
                                      modelelement);
    }
        
    public void addClassModelEventListener(PropertyChangeListener listener,
                                           Object modelClass,
                                           String[] propertyNames) {
        impl.addClassModelEventListener(listener,
                                        modelClass,
                                        propertyNames);
    }
        
    public void addClassModelEventListener(PropertyChangeListener listener,
                                           Object modelClass,
                                           String propertyName) {
        impl.addClassModelEventListener(listener,
                                        modelClass,
                                        propertyName);
    }
        
    public void removeClassModelEventListener(PropertyChangeListener listener,
                                              Object modelClass,
                                              String[] propertyNames) {
        impl.removeClassModelEventListener(listener,
                                           modelClass,
                                           propertyNames);
    }
        
    public void removeClassModelEventListener(PropertyChangeListener listener,
                                              Object modelClass,
                                              String propertyName) {
        impl.removeClassModelEventListener(listener,
                                           modelClass,
                                           propertyName);
    }
        
    public void startPumpingEvents() {
        impl.startPumpingEvents();
    }
        
    public void stopPumpingEvents() {
        impl.stopPumpingEvents();
    }
        
    public void flushModelEvents() {
        impl.flushModelEvents();
    }
        
    public List getDebugInfo() {
        return impl.getDebugInfo();
    }
}
