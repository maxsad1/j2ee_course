package ru.malykh.da.lec7.jms;

import javax.jms.BytesMessage;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import ru.malykh.da.lec7.model.entities.RecipeHolder;
import ru.malykh.da.lec7.model.requests.RecipesRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Requester
{
	private static final Logger log = LoggerFactory.getLogger( Requester.class );
	
	private JmsTemplate _jmsTemplate;
	private Destination _destination;
	
	// @formatter:off
	@Required public void setJmsTemplate( JmsTemplate jmsTemplate ) { _jmsTemplate = jmsTemplate; }
	public JmsTemplate getJmsTemplate( ) { return _jmsTemplate; }
	
	@Required public void setDestination( Destination destination ) { _destination = destination; }
	public Destination getDestination( ) { return _destination; }
	// @formatter:on
	
	public void request( )
	{
		try
		{
			RecipesRequest request = new RecipesRequest( );
			request.setType( "recipes.request" );
			request.setLimit( 10 );
			request.setHolder( new RecipeHolder( ) );
			
			ObjectMapper jsonMapper = new ObjectMapper( );
			final String json = jsonMapper.writeValueAsString( request );
			final byte[ ] jsonb = json.getBytes( "UTF-8" );
			
			_jmsTemplate.send( _destination, new MessageCreator( )
			{
				@Override
				public Message createMessage( Session session )
					throws JMSException
				{
					BytesMessage msg = session.createBytesMessage( );
					msg.writeBytes( jsonb );
					
					return msg;
				}
			} );
		}
		catch ( Exception exc )
		{
			log.info( "oops", exc );
		}
	}
}
