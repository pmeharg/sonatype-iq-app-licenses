package org.mike;

import org.json.JSONObject;
import org.restlet.Client;
import org.restlet.Context;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Protocol;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TryThreeJson
{
  private static final Logger LOGGER = LoggerFactory.getLogger(TryThreeJson.class);

  /**
   * Demonstrate Restlet JSON Extension
   * 
   * @param pUrl
   * @return TODO
   * @throws Exception
   */
  public JSONObject get(String pUrl) throws Exception
  {
    JSONObject returnValue = null;

    Client client = new Client(new Context(), Protocol.HTTP);
    ClientResource res = new ClientResource(pUrl);
    res.setChallengeResponse(ChallengeScheme.HTTP_BASIC, "admin", "admin123");
    res.setNext(client);

    try
    {
      returnValue = res.get(JSONObject.class);

      int code = res.getStatus().getCode();
      String description = res.getStatus().getDescription();
      LOGGER.info("GET {}: Response {}-{}", pUrl, code, description);
      LOGGER.debug("Payload:\n{}", returnValue.toString(2));

    }
    catch (ResourceException ex)
    {
      int code = ex.getStatus().getCode();
      String description = ex.getStatus().getDescription();
      LOGGER.info("GET {}: Response {}: {}", pUrl, code, description);
    }
    
    return returnValue;

  }

}