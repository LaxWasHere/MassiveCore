package com.massivecraft.massivecore.util;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.Map.Entry;

/**
 * The ContainerUtil provides an imaginary super class to Collection and Map.
 * In Java they do not have a common interface yet many methods are similar and exists in both.
 * This some times results in twice the amount of source code, which we aim to remedy with this utility class.
 * 
 * We take an approach where we largely see a Map as a Collection of entries.
 * The "Container" class is simply an Object.
 * The return values are auto cast generics.
 * 
 * We have also added some information gatherers related to sorting and order. 
 */
public class ContainerUtil
{
	// -------------------------------------------- //
	// IS > CORE
	// -------------------------------------------- //
	
	public static boolean isContainer(Object container)
	{
		return isCollection(container) || isMap(container);
	}
	
	public static boolean isCollection(Object container)
	{
		return container instanceof Collection<?>;
	}
	
	public static boolean isMap(Object container)
	{
		return container instanceof Map<?, ?>;
	}
	
	// -------------------------------------------- //
	// IS > BEHAVIOR
	// -------------------------------------------- //
	
	public static boolean isIndexed(Object container)
	{
		return isOrdered(container) || isSorted(container);
	}
	
	public static boolean isOrdered(Object container)
	{
		return container instanceof List<?> || container instanceof LinkedHashMap<?, ?>;
	}
	
	public static boolean isSorted(Object container)
	{
		return container instanceof SortedSet<?> || container instanceof SortedMap<?, ?>;
	}
	
	// -------------------------------------------- //
	// AS > CORE
	// -------------------------------------------- //
	
	@SuppressWarnings("unchecked")
	public static <C extends Collection<? extends Object>> C asCollection(Object container)
	{
		if ( ! isCollection(container)) return null;
		return (C)container;
	}
	
	@SuppressWarnings("unchecked")
	public static <M extends Map<? extends Object, ? extends Object>> M asMap(Object container)
	{
		if ( ! isMap(container)) return null;
		return (M)container;
	}
	
	// -------------------------------------------- //
	// METHODS > SIZE
	// -------------------------------------------- //
	
	public static boolean isEmpty(Object container)
	{
		Collection<Object> collection = asCollection(container);
		if (collection != null)
		{
			return collection.isEmpty();
		}
		
		Map<Object, Object> map = asMap(container);
		if (map != null)
		{
			return map.isEmpty();
		}
		
		throw new IllegalArgumentException();
	}
	
	public static int size(Object container)
	{
		Collection<Object> collection = asCollection(container);
		if (collection != null)
		{
			return collection.size();
		}
		
		Map<Object, Object> map = asMap(container);
		if (map != null)
		{
			return map.size();
		}
		
		throw new IllegalArgumentException();
	}
	
	// -------------------------------------------- //
	// METHODS > GET
	// -------------------------------------------- //
	
	@SuppressWarnings("unchecked")
	public static <E> Collection<E> getElements(Object container)
	{
		Collection<E> collection = asCollection(container);
		if (collection != null)
		{
			return collection;
		}
		
		Map<Object, Object> map = asMap(container);
		if (map != null)
		{
			return (Collection<E>) map.entrySet();
		}
		
		throw new IllegalArgumentException();
	}
	
	// -------------------------------------------- //
	// METHODS > SET
	// -------------------------------------------- //
	
	public static void clear(Object container)
	{
		Collection<Object> collection = asCollection(container);
		if (collection != null)
		{
			collection.clear();
			return;
		}
		
		Map<Object, Object> map = asMap(container);
		if (map != null)
		{
			map.clear();
			return;
		}
		
		throw new IllegalArgumentException();
	}
	
	public static void setElements(Object container, Iterable<? extends Object> elements)
	{
		clear(container);
		addElements(container, elements);
	}
	
	@SuppressWarnings("unchecked")
	public static boolean addElement(Object container, Object element)
	{
		Collection<Object> collection = asCollection(container);
		if (collection != null)
		{
			return collection.add(element);
		}
		
		Map<Object, Object> map = asMap(container);
		if (map != null)
		{
			Entry<Object, Object> entry = (Entry<Object, Object>)element;
			Object key = entry.getKey();
			Object after = entry.getValue();
			Object before = map.put(key, after);
			return ! MUtil.equals(after, before);
		}
		
		throw new IllegalArgumentException();
	}
	
	public static void addElements(Object container, Iterable<? extends Object> elements)
	{
		for (Object element : elements)
		{
			addElement(container, element);
		}
	}
	
}
