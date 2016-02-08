package com.fd.demo.servlets;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fd.demo.utilities.CollectionUtil;
import com.fd.demo.utilities.MapCompareByValueComparator;




// TODO: Auto-generated Javadoc
/**
 * The Class GetGlossaryServlet.
 */
@SlingServlet(paths = "/aemservices/bsr/getglossary", methods = "GET", metatype = true)
public class GetGlossaryServlet extends SlingAllMethodsServlet  {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6552063037754260210L;
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(GetGlossaryServlet.class);

	/* (non-Javadoc)
	 * @see org.apache.sling.api.servlets.SlingSafeMethodsServlet#doGet(org.apache.sling.api.SlingHttpServletRequest, org.apache.sling.api.SlingHttpServletResponse)
	 */
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException,IOException {
		
		response.setContentType("text/plain");
		String alphabet = request.getParameter("alphabet");
		logger.info("alphabet is=="+alphabet);
		String path = request.getParameter("path");
		logger.info("path is=="+path);
		String property = request.getParameter("property");
		logger.info("property is=="+property);
		try {
			List<Map<String, String>> searchResults = getMultiFieldPanelValues(request, path, property);	
			MapCompareByValueComparator mapCompare = new MapCompareByValueComparator();
			Collections.sort(searchResults, mapCompare);
			writeGlossaryToJson(request,searchResults,response);
		}
		catch(RepositoryException e) {
			logger.error("====Inside doGet====="+e.getMessage());
		}


	}

	/**
	 * Gets the multi field panel values.
	 *
	 * @param request the request
	 * @param path the path
	 * @param property the property
	 * @return the multi field panel values
	 * @throws RepositoryException the repository exception
	 */
	private List<Map<String,String>> getMultiFieldPanelValues(SlingHttpServletRequest request, String path,String property) throws RepositoryException {
		Resource resource = request.getResourceResolver().getResource(path);
		List<Map<String, String>> results = null;
		if(resource != null) {
			ValueMap map = resource.adaptTo(ValueMap.class);
			results = new ArrayList<Map<String, String>>();
			if (map != null && map.containsKey((Object)property)) {
				String[] values;
				for (String value : values = (String[])map.get(property, (Object)new String[0])) {
					try {
						JSONObject parsed = new JSONObject(value);
						HashMap<String, String> columnMap = new HashMap<String, String>();
						Iterator<String> iter = parsed.keys();
						while (iter.hasNext()) {
							String key = (String)iter.next();
							String innerValue = parsed.getString(key);
							columnMap.put(key, innerValue);
						}
						results.add(columnMap);
						continue;
					}
					catch (JSONException e) {
						logger.error(String.format("Unable to parse JSON in %s property of %s", request.getResourceResolver().getResource(path)), (Throwable)e);
					}
				}
			}
		}
		return results;
	} 
	
	private void writeGlossaryToJson(SlingHttpServletRequest request, List<Map<String, String>> searchResults, SlingHttpServletResponse response) {
		try {
			String jsonDataString="";
			int iteration = 0;
			if(!CollectionUtil.isEmpty(searchResults)) {
				Iterator it = searchResults.iterator();
				while(it.hasNext()) {
					iteration=iteration+1;
					Map <String, String> searchResultMap = (Map)it.next();
					JSONObject resultsJson = new JSONObject(searchResultMap);
					jsonDataString = jsonDataString+resultsJson.toString();
					if(iteration != searchResults.size()) {
						jsonDataString = jsonDataString+',';
					} 
				}
				jsonDataString ="{"+ "\"glossary\""+":"+" "+"["+jsonDataString+"]"+" "+"}";
				response.setCharacterEncoding("utf-8");
				response.getWriter().write(jsonDataString);
				response.getWriter().flush();
				response.getWriter().close();
			}
		}
			catch(Exception e) {
				logger.error(e.getMessage());
		}
		
	}
}
