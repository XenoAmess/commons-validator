/*
 * $Header: /home/jerenkrantz/tmp/commons/commons-convert/cvs/home/cvs/jakarta-commons//validator/src/share/org/apache/commons/validator/ValidatorResults.java,v 1.4 2003/05/02 05:36:35 dgraham Exp $
 * $Revision: 1.4 $
 * $Date: 2003/05/02 05:36:35 $
 *
 * ====================================================================
 *
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 1999-2003 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The Jakarta Project", "Commons", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package org.apache.commons.validator;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <p>This contains the results of a set of 
 * validation rules processed on JavaBean.</p>
 *
 * @author David Winterfeldt
 * @author James Turner
 * @author David Graham
 * @version $Revision: 1.4 $ $Date: 2003/05/02 05:36:35 $
*/
public class ValidatorResults implements Serializable {

	/**
	 * Map of validation results.
	 */
	protected Map hResults = new HashMap();

	/** 
	 * Merge another ValidatorResults into mine.
	 */
	public void merge(ValidatorResults results) {
		this.hResults.putAll(results.hResults);
	}

	/**
	 * Add a the result of a validator action.
	 */
	public void add(Field field, String validatorName, boolean result) {
		add(field, validatorName, result, null);
	}

	/**
	 * Add a the result of a validator action.
	 */
	public void add(
		Field field,
		String validatorName,
		boolean result,
		Object value) {
            
		ValidatorResult validatorResult = null;

		if (hResults.containsKey(field.getKey())) {
			validatorResult = (ValidatorResult) hResults.get(field.getKey());
		} else {
			validatorResult = new ValidatorResult(field);
		}

		validatorResult.add(validatorName, result, value);

		hResults.put(field.getKey(), validatorResult);
	}

	/**
	 * Clear all results recorded by this object.
	 */
	public void clear() {
		hResults.clear();
	}

	/**
	 * Return <code>true</code> if there are no messages recorded
	 * in this collection, or <code>false</code> otherwise.
     * @deprecated Use isEmpty() instead.
	 */
	public boolean empty() {
		return this.isEmpty();
	}
    
    /**
     * Return <code>true</code> if there are no messages recorded
     * in this collection, or <code>false</code> otherwise.
     */
    public boolean isEmpty() {
        return this.hResults.isEmpty();
    }

	/**
	 * Gets the <code>ValidatorResult</code> associated 
	 * with the key passed in.  The key the <code>ValidatorResult</code> 
	 * is stored under is the <code>Field</code>'s getKey method.
	 *
	 * @param	key		The key generated from <code>Field</code>.
	 */
	public ValidatorResult getValidatorResult(String key) {
		return (
			(hResults.containsKey(key))
				? ((ValidatorResult) hResults.get(key))
				: null);
	}

	/**
	 * Return the set of all recorded messages, without distinction
	 * by which property the messages are associated with.  If there are
	 * no messages recorded, an empty enumeration is returned.
	 */
	public Iterator get() {
		if (hResults.isEmpty()) {
			return Collections.EMPTY_LIST.iterator();
		}

		return hResults.keySet().iterator();
		//
		//ArrayList results = new ArrayList();
		//
		//for (Iterator i =  actionItems.iterator(); i.hasNext(); ) {
		//   ActionMessageItem ami = (ActionMessageItem)i.next();
		//
		//   for (Iterator messages =  ami.getList().iterator(); messages.hasNext(); )
		//      results.add(messages.next());
		//}
		//
		//return (results.iterator());

	}

	/**
	 * Return the set of messages related to a specific property.
	 * If there are no such messages, an empty enumeration is returned.
	 *
	 * @param property Property name (or ActionMessages.GLOBAL_MESSAGE)
	 */
	//public Iterator get(String property) {

	//ActionMessageItem ami = (ActionMessageItem) messages.get(property);
	//
	//if (ami == null)
	//   return (Collections.EMPTY_LIST.iterator());
	//else
	//   return (ami.getList().iterator());

	//}

	/**
	 * Return the set of property names for which at least one message has
	 * been recorded.  If there are no messages, an empty Iterator is returned.
	 * If you have recorded global messages, the String value of
	 * <code>ActionMessages.GLOBAL_MESSAGE</code> will be one of the returned
	 * property names.
	 */
	public Iterator properties() {
		return hResults.keySet().iterator();
	}

	/**
	 * Get a <code>Map</code> of any <code>Object</code>s 
	 * returned from validation routines.
	 */
	public Map getResultValueMap() {
		Map results = new HashMap();

		for (Iterator i = hResults.keySet().iterator(); i.hasNext();) {
			String propertyKey = (String) i.next();
			ValidatorResult vr = (ValidatorResult) hResults.get(propertyKey);

			Map actions = vr.getActionMap();
			for (Iterator x = actions.keySet().iterator(); x.hasNext();) {
				String actionKey = (String) x.next();
				ValidatorResult.ResultStatus rs =
					(ValidatorResult.ResultStatus) actions.get(actionKey);

				if (rs != null) {
					Object result = rs.getResult();

					if (result != null && !(result instanceof Boolean)) {
						results.put(propertyKey, result);
					}
				}
			}
		}

		return results;
	}

}
