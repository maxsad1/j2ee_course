package ru.malykh.da.lec1.services;

import ru.malykh.da.lec1.interfaces.IActuator;
import ru.malykh.da.lec1.interfaces.ISensor;

public class IntellectualController
{
	private String _url;
	private ISensor _sensor;
	private IActuator _actuator;
	private double _limit;
	
	// @formatter:off
	public void setSensor( ISensor sensor ) { _sensor = sensor; }
	public ISensor getSensor( ) { return _sensor; }
	
	public void setActuator( IActuator actuator ) { _actuator = actuator; }
	public IActuator getActuator( ) { return _actuator; }
	
	public void setLimit( double limit ) { _limit = limit; }
	public double getLimit( ) { return _limit; }
	// @formatter:on
	
	public IntellectualController( String url )
	{
		System.out.println( String.format(
			"Создан интеллектуальный контроллер температуры в точке %s.",
			url
		) );
		
		_url = url;
	}
	
	
	public void doTheWork( )
	{
		double sv = _sensor.getTemperature( );
		
		System.out.println( String.format(
			"Интеллектуальный контроллер температуры в точке %s получил новое значение температуры %f.",
			_url, sv
		) );
		
		if ( sv > _limit )
		{
			System.out.println( String.format(
				"Интеллектуальный контроллер температуры в точке %s включил кондиционирование.",
				_url
			) );
			
			_actuator.start( );
		}
		else
		{
			System.out.println( String.format(
				"Интеллектуальный контроллер температуры в точке %s отключил кондиционирование.",
				_url
			) );
				
			_actuator.stop( );
		}
	}

}
